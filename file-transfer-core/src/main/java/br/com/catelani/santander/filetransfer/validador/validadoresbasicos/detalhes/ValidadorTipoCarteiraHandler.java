package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.detalhes;

import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.domain.DetalhePropostaAcordo;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordoHandler;
import br.com.catelani.santander.filetransfer.validador.validadoresbasicos.ValidadorUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * <p>Valida se o tipo de Carteira está preenchido corretamente.</p>
 * <p>Caso não, sera adicionado o erro {@link CodigoRetornoErro#ERRO_PREENCHIMENTO_TIPO_DE_CARTEIRA}.</p>
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public class ValidadorTipoCarteiraHandler extends ValidadorPropostaAcordoHandler {

  @Override
  public @NotNull List<ErroValidacao> validar(PropostaAcordo propostaAcordo) {
    log.debug("Iniciando validação de Tipo de Carteira");
    final List<ErroValidacao> erros = super.validar(propostaAcordo);

    final List<DetalhePropostaAcordo> detalhes = propostaAcordo.getDetalhes();

    validarTipoCarteiraNulo(detalhes, erros);

    validarTipoCardeiraDivergenteEntreOMesmoContrato(detalhes, erros);

    return erros;
  }

  /**
   * Valido se todos os tipos de Carteira de um mesmo contrato estão preenchidos com o mesmo tipo, pois não podem estar divergentes
   *
   * @param detalhes Detalhes dos contratos
   * @param erros    Lista de erros para adicionar erros de validação
   */
  private void validarTipoCardeiraDivergenteEntreOMesmoContrato(List<DetalhePropostaAcordo> detalhes, List<ErroValidacao> erros) {
    final Map<String, List<DetalhePropostaAcordo>> detalhesPorContrato = detalhes.stream().collect(groupingBy(ValidadorUtils.extractNumeroContrato()));
    final Set<String> contratos = detalhesPorContrato.keySet();
    contratos.forEach(c -> {
      final List<DetalhePropostaAcordo> detalhesDoContrato = detalhesPorContrato.get(c);

      if (detalhesDoContrato.isEmpty()) {
        log.debug("Detalhes do contrato vazio para a chave {}", c);
      } else {
        final Optional<DetalhePropostaAcordo> min = detalhesDoContrato.stream().min(comparing(DetalhePropostaAcordo::getSequencia));
        final DetalhePropostaAcordo primeiraPropostaAcordo = min.get();

        final List<DetalhePropostaAcordo> detalhesErroTipoCarteira = detalhesDoContrato.stream()
                                                                                       .filter(d -> d.getTipoCarteira() != primeiraPropostaAcordo.getTipoCarteira())
                                                                                       .collect(toList());

        detalhesErroTipoCarteira.stream()
                                .map(d -> String.format(
                                    "O Detalhe %s está com o Tipo de Cardeira divergente dos outros Tipos de Carteira da Proposta, todos os detalhes referentes a mesma proposta devem ter o mesmo tipo de carteira!",
                                    d))
                                .map(msg -> new ErroValidacao(CodigoRetornoErro.ERRO_PREENCHIMENTO_TIPO_DE_CARTEIRA, msg))
                                .forEach(erros::add);
      }
    });
  }

  /**
   * Valida se alguns dos detahes de uma negociação está com o tipo divergente com as outras do mesmo contrato. Todas devem ter o mesmo tipo de Carteira.
   *
   * @param erros    Lista de erros para adicionar erros de validação
   * @param detalhes Detalhes dos contratos para validar.
   */
  private void validarTipoCarteiraNulo(List<DetalhePropostaAcordo> detalhes, List<ErroValidacao> erros) {// filtro os que estão sem Tipo de Carteira, pois esse campo é obrigatório.
    detalhes.stream()
            .filter(d -> d.getTipoCarteira() == null)
            .map(d -> String.format("O Detalhe %s está sem um Tipo de Carteira!", d))
            .map(msg -> new ErroValidacao(CodigoRetornoErro.ERRO_PREENCHIMENTO_TIPO_DE_CARTEIRA, msg))
            .forEach(erros::add);
  }
}
