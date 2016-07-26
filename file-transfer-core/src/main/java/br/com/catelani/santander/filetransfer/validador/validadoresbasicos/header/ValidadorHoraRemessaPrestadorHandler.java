package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header;

import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
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
public class ValidadorHoraRemessaPrestadorHandler extends ValidadorPropostaAcordoHandler {

  @Override
  public @NotNull List<ErroValidacao> validar(PropostaAcordo propostaAcordo) {
    // TODO Implementar esse trem
    return super.validar(propostaAcordo);
  }
}
