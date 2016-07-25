package br.com.catelani.santander.filetransfer.cli;

import br.com.catelani.santander.filetransfer.cli.commands.AjudaCliCommandHandler;
import br.com.catelani.santander.filetransfer.cli.commands.CliCommandHandler;
import br.com.catelani.santander.filetransfer.cli.commands.CliCommandHandlerBuilder;
import br.com.catelani.santander.filetransfer.cli.commands.PrintAsciiTableCliCommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import org.jetbrains.annotations.NotNull;

/**
 * Classe principal para a linha de comando
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public class Main {

  public static void main(String[] args) throws ParseException {
    final Options options = getOptions();

    try {
      CommandLineParser parser = new DefaultParser();
      final CommandLine cli = parser.parse(options, args);

      handleCli(args, options, cli);
    } catch (UnrecognizedOptionException e) {
      System.out.printf("Opção \"%s\" não reconhecida.", e.getOption());
    } catch (ParseException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Processa as opções passadas na linha de comando
   *
   * @param args    Argumentos da linha de comando
   * @param options Opções disponíveis
   * @param cli     Argumentos parseados da linha de comando
   */
  private static void handleCli(String[] args, Options options, CommandLine cli) {
    final CliCommand cliCommand = new CliCommand(args, options, cli);
    final CliCommandHandler commandHandler = new CliCommandHandlerBuilder().addHandler(new AjudaCliCommandHandler())
                                                                           .addHandler(new PrintAsciiTableCliCommandHandler())
                                                                           .build();

    commandHandler.handleCliCommand(cliCommand);
  }

  /**
   * @return A lista de opções atualmente disponíveis.
   */
  @NotNull
  private static Options getOptions() {
    final Option asciiTable = Option.builder("h")
                                    .desc("Imprime uma Ascii Table de uma proposta de acordo.")
                                    .longOpt("ascii-table")
                                    .build();

    final Option ajuda = Option.builder("a")
                               .desc("Imprime uma ajuda.")
                               .longOpt("ajuda")
                               .build();

    final Option validar = Option.builder("v")
                                 .longOpt("validar")
                                 .desc("Valida uma proposta de acordo")
                                 .build();

    final Options options = new Options();
    options.addOption(asciiTable);
    options.addOption(validar);
    options.addOption(ajuda);

    return options;
  }
}
