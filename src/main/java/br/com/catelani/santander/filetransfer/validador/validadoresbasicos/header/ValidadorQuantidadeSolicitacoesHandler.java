package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header;

import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordoHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * <p>Verifica se a quantidade de solicitações informadas está de acordo com as informações de detalhes da proposta.</p>
 * <p>A Quantidade de solicitações deve ser maior que 0, e ser igual a quantiade de {@link PropostaAcordo#getDetalhes()}.</p>
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public class ValidadorQuantidadeSolicitacoesHandler extends ValidadorPropostaAcordoHandler {

  private static final Optional<ErroValidacao> ERRO_VALIDACAO_QUANTIDADE_ZERADA = Optional.of(new ErroValidacao(CodigoRetornoErro.getByCodigoErro("311"),
                                                                                                                "Deve haver mais que 0 solicitações!"));

  @Override
  protected Optional<ErroValidacao> isValid(PropostaAcordo propostaAcordo) {
    log.debug("Validando quantidade de solicitações.");
    final long quantidadeSolicitacoes = propostaAcordo.getCabecalho().getDadosPrestador().getQuantidadeSolicitacoes();

    if (quantidadeSolicitacoes == 0) {
      return ERRO_VALIDACAO_QUANTIDADE_ZERADA;
    }

    final int totalDetalhes = propostaAcordo.getDetalhes().size();
    if (quantidadeSolicitacoes != totalDetalhes) {
      final String msg = String.format(
          "O Total de detalhes deve ser igual a quantidade de solicitações do Cabeçalho da Proposta! Quantidade do Cabeçalho: {%d} - Total de Detalhes: { %d",
          quantidadeSolicitacoes,
          totalDetalhes);

      return Optional.of(new ErroValidacao(CodigoRetornoErro.getByCodigoErro("334"), msg));
    }

    return super.isValid(propostaAcordo);
  }
}
