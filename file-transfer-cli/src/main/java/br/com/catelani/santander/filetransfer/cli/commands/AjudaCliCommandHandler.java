package br.com.catelani.santander.filetransfer.cli.commands;

import br.com.catelani.santander.filetransfer.cli.CliCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;

/**
 * Imprime a ajuda.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public class AjudaCliCommandHandler extends AbstractCliCommandHandler {

  /**
   * Valida se precisa mostrar a ajuda ou não.
   *
   * @param args Argumentos da linha de comando
   * @param cli  Argumentos parseados
   * @return {@code true} caso precise, {@code false} do contrário.
   */
  private static boolean precisaMostrarAjuda(String[] args, CommandLine cli) {
    return args.length == 0 || cli.getOptions().length == 0 || cli.hasOption("a");
  }

  @Override
  public void handleCliCommand(CliCommand cliCommand) {
    log.debug("Ajuda Cli Command Handler");
    if (precisaMostrarAjuda(cliCommand.getCliArgs(), cliCommand.getCliCommand())) {
      log.debug("Precisa mostrar a ajuda");

      final HelpFormatter helpFormatter = new HelpFormatter();
      helpFormatter.printHelp("java -jar file-transfer-cli.jar -[hva] <Caminho Proposta Acordo>",
                              "\nOpções:",
                              cliCommand.getOpcoesDisponiveis(),
                              "\nDesenvolvido por Kennedy Oliveira <kennedy.desenvolvimento@catelani.com.br>");

      cliCommand.markHandled();
    } else {
      log.debug("Não precisa mostrar a ajuda, indo pro proximo");
      super.handleCliCommand(cliCommand);
    }
  }
}
