package br.com.catelani.santander.filetransfer.cli;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import java.util.Objects;

/**
 * Representa os comandos informados pela linha de comando.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public class CliCommand {

  @Getter
  private final String[] cliArgs;

  @Getter
  private final Options opcoesDisponiveis;

  @Getter
  private final CommandLine cliCommand;

  @Getter
  private boolean handled;

  /**
   * Cria um novo {@link CliCommand} com as informações da linha de comando.
   *
   * @param cliArgs           Argumentos da linha de comando.
   * @param opcoesDisponiveis Opções disponíveis.
   * @param cliCommand        Opções parseadas da linha de comando.
   * @throws NullPointerException se {@code cliArgs} ou {@code opcoesDisponiveis} ou {@code cliCommand} forem {@code null}.
   */
  public CliCommand(String[] cliArgs, Options opcoesDisponiveis, CommandLine cliCommand) {
    this.cliArgs = Objects.requireNonNull(cliArgs);
    this.opcoesDisponiveis = Objects.requireNonNull(opcoesDisponiveis);
    this.cliCommand = Objects.requireNonNull(cliCommand);
  }

  /**
   * Marca esse comando como handled, o status pode ser consultado através do método {@link #isHandled}
   */
  public void markHandled() {
    this.handled = true;
  }
}
