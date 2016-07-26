package br.com.catelani.santander.filetransfer.validador.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Builder para construção de um {@link ValidadorPropostaAcordo} com diversas validações encadeadas.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ValidadorBuilder {

  private ValidadorPropostaAcordoHandler validador = new ValidadorPreCondicoesPropostaAcordoHandler();

  /**
   * Adiciona um novo validador a cadeia de validações.
   *
   * @param validadores Validador a ser adicionado.
   * @return Uma referencia para {@code this} para usar a API de forma fluente.
   * @throws NullPointerException se {@code validadores} for nulo.
   */
  public ValidadorBuilder add(@NotNull ValidadorPropostaAcordoHandler... validadores) {
    for (ValidadorPropostaAcordoHandler validadorPropostaAcordoHandler : validadores) {
      validador.setNext(Objects.requireNonNull(validadorPropostaAcordoHandler));
    }

    return this;
  }

  /**
   * @return Um {@link ValidadorPropostaAcordo} com todos os validadores que foram adicionados através do método {@link #add(ValidadorPropostaAcordoHandler)} encadeados.
   */
  @NotNull
  public ValidadorPropostaAcordo build() {
    return validador;
  }
}
