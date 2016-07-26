package br.com.catelani.santander.filetransfer.cli.commands;

import br.com.catelani.santander.filetransfer.cli.CliCommand;
import org.jetbrains.annotations.NotNull;

/**
 * Contrato para tratar comandos da linha de comando.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public interface CliCommandHandler {

  /**
   * Executa os comandos de acordo com os parametros no {@code cliCommand}.
   *
   * @param cliCommand {@link CliCommand} com as informações da linha de comando.
   */
  void handleCliCommand(@NotNull CliCommand cliCommand);
}
