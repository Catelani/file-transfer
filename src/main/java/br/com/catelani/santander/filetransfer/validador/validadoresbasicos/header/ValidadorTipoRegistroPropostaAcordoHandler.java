package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header;

import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.domain.TipoRegistro;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordoHandler;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public class ValidadorTipoRegistroPropostaAcordoHandler extends ValidadorPropostaAcordoHandler {

  // erro caso a validação falhe.
  private static final ErroValidacao ERRO_VALIDACAO = new ErroValidacao(CodigoRetornoErro.getByCodigoErro("301"),
                                                                        String.format("A interface deve ter o tipo de registro como Controle {%s}.",
                                                                                      TipoRegistro.CONTROLE.toString()));

  @Override
  public @NotNull List<ErroValidacao> validar(PropostaAcordo propostaAcordo) {
    log.debug("Validando Tipo de Registro");
    final List<ErroValidacao> errosValidacao = super.validar(propostaAcordo);

    if (propostaAcordo.getCabecalho().getTipoRegistro() != TipoRegistro.CONTROLE) {
      errosValidacao.add(ERRO_VALIDACAO);
    }

    return errosValidacao;
  }
}
