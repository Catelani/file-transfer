package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header;

import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordoHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

/**
 * <p>Verifica se o número de sequencia do cabeçalho é 1.</p>
 * <p>Caso falhar, sera adicionado a lista de erros o erro {@link CodigoRetornoErro#ERRO_PREENCHIMENTO_SEQUENCIA}</p>
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public class ValidadorNumeroSequenciaHandler extends ValidadorPropostaAcordoHandler {

  @Override
  protected Optional<ErroValidacao> isValid(PropostaAcordo propostaAcordo) {
    log.debug("Validando Número de Sequencia do Cabeçalho");

    final String numeroDeSequencia = propostaAcordo.getCabecalho().getNumeroDeSequencia();
    if (!Objects.equals(numeroDeSequencia, "00001")) {
      return Optional.of(new ErroValidacao(CodigoRetornoErro.getByCodigoErro("312"), "O Número de Sequencia do Cabeçalho é {" + numeroDeSequencia + "} e deveria ser {00001}."));
    }

    return super.isValid(propostaAcordo);
  }
}
