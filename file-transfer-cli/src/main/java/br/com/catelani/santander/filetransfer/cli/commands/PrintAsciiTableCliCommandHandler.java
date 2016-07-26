package br.com.catelani.santander.filetransfer.cli.commands;

import br.com.catelani.santander.filetransfer.cli.CliCommand;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.parser.PropostaAcordoReader;
import br.com.catelani.santander.filetransfer.parser.PropostaAcordoWriter;
import br.com.catelani.santander.filetransfer.qualifiers.AsciiTable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Printa uma Ascii Table de uma proposta de acordo.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public class PrintAsciiTableCliCommandHandler extends AbstractCliCommandHandler {

  @Inject
  private PropostaAcordoReader propostaAcordoReader;

  @Inject
  @AsciiTable
  private PropostaAcordoWriter propostaAcordoWriter;

  @Override
  public void handleCliCommand(CliCommand cliCommand) {
    log.debug("Entrando no handler de Ascii Table");

    final CommandLine cliCommandOptions = cliCommand.getCliCommand();
    if (cliCommandOptions.hasOption('h')) {
      log.debug("Identificado a opção de printar tabela como Ascii Table.");

      final Path arquivo;
      try {
        final List<String> argumentos = cliCommand.getCliCommand().getArgList();

        if (argumentos.isEmpty())
          throw new Exception("Faltando o caminho do arquivo para printar a Ascii Table");

        final String caminhoArquivo = argumentos.get(0);
        arquivo = Paths.get(caminhoArquivo);

        if (!Files.exists(arquivo)) {
          throw new Exception("O arquivo [" + arquivo + "] não existe");
        }
      } catch (Exception e) {
        log.debug("Erro", e);
        System.out.println(e.getMessage());
        cliCommand.markHandled();
        return;
      }

      GuiceUtils.getInjector().injectMembers(this);

      printAsciiTable(arquivo);

      cliCommand.markHandled();
    } else {
      log.debug("Não tinha o comando de Print Ascii Table...");
      super.handleCliCommand(cliCommand);
    }
  }

  private void printAsciiTable(Path arquivo) {
    try (InputStream is = Files.newInputStream(arquivo)) {
      final PropostaAcordo propostaAcordo = propostaAcordoReader.parse(is);

      propostaAcordoWriter.gerarPropostaAcordo(propostaAcordo, System.out);
    } catch (IOException e) {
      log.debug("Erro", e);
      System.out.println(e.getMessage());
    }
  }
}
