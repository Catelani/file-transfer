package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header;

import br.com.catelani.santander.filetransfer.domain.CabecalhoPropostaAcordo;
import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.domain.TipoInterface;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordoHandler;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Valida se o Tipo de Interface ({@link CabecalhoPropostaAcordo#getTipoInterface()}) foi preenchido corretamente.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public class ValidadorTipoInterfacePropostaAcordoHandler extends ValidadorPropostaAcordoHandler {

  @Override
  public @NotNull List<ErroValidacao> validar(PropostaAcordo propostaAcordo) {
    log.debug("Validando Tipo de Proposta");
    @NotNull final List<ErroValidacao> errosValidacao = super.validar(propostaAcordo);

    if (propostaAcordo.getCabecalho().getTipoInterface() != TipoInterface.PROPOSTA_DE_ACORDO) {
      final String msg = "O tipo da interface da Proposta de Acordo deve ser Proposta de Acordo {" + TipoInterface.PROPOSTA_DE_ACORDO.toString() + "}";
      errosValidacao.add(new ErroValidacao(CodigoRetornoErro.getByCodigoErro("302"), msg));
    }

    return errosValidacao;
  }
}
