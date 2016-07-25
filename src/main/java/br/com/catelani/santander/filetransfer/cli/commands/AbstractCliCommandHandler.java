package br.com.catelani.santander.filetransfer.cli.commands;

import br.com.catelani.santander.filetransfer.cli.CliCommand;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Classe base para a implementação de {@link CliCommandHandler} usando Chain of Responsability.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public class AbstractCliCommandHandler implements CliCommandHandler {

  /**
   * Proximo handler na cadeia
   */
  private AbstractCliCommandHandler next;

  /**
   * Atribui o proximo {@link AbstractCliCommandHandler} para a cadeira de tratamento.
   *
   * @param next {@link AbstractCliCommandHandler}
   */
  protected void setNext(AbstractCliCommandHandler next) {
    log.debug("Setando proximo handler da cadeia {}", next);
    Objects.requireNonNull(next, "O proximo handler na cadeia não pode ser nulo!");

    if (this.next == null) {
      this.next = next;
    } else {
      this.next.setNext(next);
    }
  }

  @Override
  public void handleCliCommand(CliCommand cliCommand) {
    log.debug("Chamando proximo handler da cadeia");
    Objects.requireNonNull(cliCommand, "O Comando para ser tratado não pode ser nulo!");

    if (this.next != null) {
      this.next.handleCliCommand(cliCommand);
    } else {
      log.debug("Não há proximo handler para ser chamado");
    }
  }
}
