package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.detalhes;

import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.domain.DadosContrato;
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

import static java.util.stream.Collectors.groupingBy;

/**
 * Valida se a parcela final foi preenchida corretamente.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public class ValidadorParcelaFinalHandler extends ValidadorPropostaAcordoHandler {

  @Override
  public @NotNull List<ErroValidacao> validar(PropostaAcordo propostaAcordo) {
    log.debug("Validando Parcela Inicial e Final");
    final List<ErroValidacao> erros = super.validar(propostaAcordo);

    final Map<String, List<DetalhePropostaAcordo>> detalhesPorContrato = propostaAcordo.getDetalhes()
                                                                                       .stream()
                                                                                       .collect(groupingBy(ValidadorUtils.extractNumeroContrato()));

    detalhesPorContrato.keySet().forEach(c -> {
      final List<DetalhePropostaAcordo> detalhePropostaAcordos = detalhesPorContrato.get(c);

      validarParcelaFinalMenorQueInicial(detalhePropostaAcordos, erros);
      validarParcelaInicialEFinalDivergenteParaMesmaNegociacao(detalhePropostaAcordos, erros);
    });

    return erros;
  }

  /**
   * Valida se a Parcela Inicial e Final são iguais para todos os Detalhes de uma mesma Proposta de Acordo.
   *
   * @param detalhePropostaAcordos Lista de Detalhes de uma Proposta.
   * @param erros                  Erros de validação para adicionar novos erros.
   */
  private void validarParcelaInicialEFinalDivergenteParaMesmaNegociacao(List<DetalhePropostaAcordo> detalhePropostaAcordos, List<ErroValidacao> erros) {
    final Optional<DetalhePropostaAcordo> detalhePropostaNaoNula = detalhePropostaAcordos.stream()
                                                                                         .filter(d -> d.getDadosContrato() != null)
                                                                                         .findFirst();
    if (detalhePropostaNaoNula.isPresent()) {
      final DadosContrato dadosContrato = detalhePropostaNaoNula.get().getDadosContrato();
      final int parcelaInicial = dadosContrato.getParcelaInicial();
      final int parcelaFinal = dadosContrato.getParcelaFinal();

      detalhePropostaAcordos.stream()
                            .filter(d -> d.getDadosContrato().getParcelaInicial() != parcelaInicial || d.getDadosContrato().getParcelaFinal() != parcelaFinal)
                            .map(d -> String.format("A parcela Inicial ou Final está diferente entre os Detalhes de uma mesma Proposta de Acordo, elas deve ser iguais. [%s]", d))
                            .map(msg -> new ErroValidacao(CodigoRetornoErro.ERRO_PREENCHIMENTO_PARCELA_NEGOCIACAO_FINAL, msg))
                            .forEach(erros::add);
    }
  }

  /**
   * Verifica se algum detalhe de Proposta de Acordo tem a Parcela Final menor que a Inicial.
   *
   * @param detalhePropostaAcordos Lista de Detalhes de uma Proposta.
   * @param erros                  Erros de validação para adicionar novos erros.
   */
  private void validarParcelaFinalMenorQueInicial(List<DetalhePropostaAcordo> detalhePropostaAcordos, List<ErroValidacao> erros) {
    detalhePropostaAcordos.stream()
                          .filter(d -> d.getDadosContrato().getParcelaFinal() >= d.getDadosContrato().getParcelaInicial())
                          .map(d -> String.format("A Parcela Final Negociada está menor que a Parcela Inicial Negociada -> [%s]", d))
                          .map(msg -> new ErroValidacao(CodigoRetornoErro.ERRO_PREENCHIMENTO_PARCELA_NEGOCIACAO_FINAL, msg))
                          .forEach(erros::add);
  }
}
