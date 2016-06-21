package br.com.catelani.santander.filetransfer.validador.api;

import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public interface ValidadorPropostaAcordo {

  @NotNull
  List<ErroValidacao> validar(PropostaAcordo propostaAcordo);
}
