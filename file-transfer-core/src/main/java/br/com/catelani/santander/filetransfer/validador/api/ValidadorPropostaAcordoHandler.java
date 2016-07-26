package br.com.catelani.santander.filetransfer.validador.api;

import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public abstract class ValidadorPropostaAcordoHandler implements ValidadorPropostaAcordo {

  private ValidadorPropostaAcordoHandler nextValidadorHandler;

  @Override
  @NotNull
  public List<ErroValidacao> validar(PropostaAcordo propostaAcordo) {
    List<ErroValidacao> erros;

    if (nextValidadorHandler != null) {
      erros = nextValidadorHandler.validar(propostaAcordo);
    } else {
      erros = new ArrayList<>();
    }

    isValid(propostaAcordo).ifPresent(erros::add);

    return erros;
  }

  /**
   * Atribui um proximo {@link ValidadorPropostaAcordoHandler} para a cadeia de validações.
   *
   * @param validadorHandler Validador a ser adicionado.
   * @throws NullPointerException Se o {@code validadorHandler} for nulo.
   */
  protected void setNext(@NotNull ValidadorPropostaAcordoHandler validadorHandler) {
    Objects.requireNonNull(validadorHandler, "O ValidadorHandler não pode ser nulo!");

    if (nextValidadorHandler == null) {
      nextValidadorHandler = validadorHandler;
    } else {
      nextValidadorHandler.setNext(validadorHandler);
    }
  }

  protected Optional<ErroValidacao> isValid(PropostaAcordo propostaAcordo) {
    return Optional.empty();
  }
}
