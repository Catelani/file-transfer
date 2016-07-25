package br.com.catelani.santander.filetransfer.cli.commands;

/**
 * Builder para criação de um {@link CliCommandHandler}.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public class CliCommandHandlerBuilder {

  private AbstractCliCommandHandler commandHandler;

  /**
   * <p>Adiciona o {@code commandHandler} na cadeia de comandos.</p>
   * <p>Os comandos serão validados de acordo com a ordem de inserção.</p>
   *
   * @param commandHandler Proximo {@link AbstractCliCommandHandler} da cadeia.
   * @return Uma referencia para {@code this} para uma API fluente.
   */
  public CliCommandHandlerBuilder addHandler(AbstractCliCommandHandler commandHandler) {
    if (this.commandHandler == null) {
      this.commandHandler = commandHandler;
    } else {
      this.commandHandler.setNext(commandHandler);
    }

    return this;
  }

  /**
   * @return Um {@link CliCommandHandler}.
   */
  public CliCommandHandler build() {
    return commandHandler;
  }
}
