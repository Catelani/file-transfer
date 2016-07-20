package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header;

import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.domain.TipoPrestacaoServico;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordoHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * <p>Valida se o Tipo de Prestação de Serviço do Prestador foi preenchido corretamente.</p>
 * <p>Ele deve ser um dos tipos permitidos pelo banco:
 * <ul>
 * <li>025 (Assessoria)</li>
 * <li>026 (Advogado)</li>
 * </ul>
 * Caso não seja informado corretamente vai adicionar o código de erro {@link CodigoRetornoErro#ERRO_PREENCHIMENTO_TIPO_DE_PRESTACAO_SERVICO}
 * </p>
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public class ValidadorTipoPrestacaoServicoHandler extends ValidadorPropostaAcordoHandler {

  private static final Optional<ErroValidacao> ERRO_VALIDACAO = Optional.of(new ErroValidacao(CodigoRetornoErro.getByCodigoErro("309"), "Tipo de Prestação Nulo!"));

  @Override
  protected Optional<ErroValidacao> isValid(PropostaAcordo propostaAcordo) {
    log.debug("Validando tipo de Prestação de Serviço");
    final TipoPrestacaoServico tipoPrestacaoServico = propostaAcordo.getCabecalho().getDadosPrestador().getTipoPrestacaoServico();

    final Optional<ErroValidacao> resultadoValidacao = tipoPrestacaoServico == null ? ERRO_VALIDACAO : Optional.empty();

    log.debug("Tipo de Prestação de Serviço {}", resultadoValidacao.isPresent() ? "válido" : "inválido");

    return resultadoValidacao;
  }
}
