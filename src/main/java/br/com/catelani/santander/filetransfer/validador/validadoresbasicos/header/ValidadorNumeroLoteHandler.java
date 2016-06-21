package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header;

import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordoHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * <p>Valida se o número de lote foi preenchido corretamente.</p>
 * <p>Se houver falha, será adicionado um {@link CodigoRetornoErro#ERRO_PREENCHIMENTO_NUMERO_DO_LOTE} </p>
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public class ValidadorNumeroLoteHandler extends ValidadorPropostaAcordoHandler {

  @Override
  protected Optional<ErroValidacao> isValid(PropostaAcordo propostaAcordo) {
    log.debug("alidando Número de Lote");

    final long numeroLote = propostaAcordo.getCabecalho().getDadosPrestador().getNumeroLote();
    if (numeroLote <= 0) {
      return Optional.of(new ErroValidacao(CodigoRetornoErro.getByCodigoErro("308"), "O Número de lote deve ser númerico e maior que 0, foi informado {" + numeroLote + "}"));
    }

    return super.isValid(propostaAcordo);
  }
}
