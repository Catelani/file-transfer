package br.com.catelani.santander.filetransfer.cli.commands;

import br.com.catelani.santander.filetransfer.cli.CliCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Kennedy Oliveira
 */
@Slf4j
public class PrintAsciiTableCliCommandHandlerTest {

  @Rule
  public SystemOutRule systemOutRule = new SystemOutRule().enableLog();

  @Rule
  public TemporaryFolder tempDir = new TemporaryFolder();

  private CliCommandHandler commandHandler;

  @Before
  public void setUp() throws Exception {
    this.commandHandler = new PrintAsciiTableCliCommandHandler();
  }

  @Test
  public void testPrintAsciiTableCommand() throws Exception {
    tempDir.create();
    final File arquivoTemporario = tempDir.newFile();

    Files.copy(ClassLoader.getSystemResourceAsStream("exemplos/ESCPA0001.dat"), arquivoTemporario.toPath(), REPLACE_EXISTING);

    final Options opcoesDisponiveis = new Options().addOption("h", "Print Ascii Table");
    final String[] args = {"-h", arquivoTemporario.getAbsolutePath()};

    CommandLineParser parser = new DefaultParser();
    final CommandLine cli = parser.parse(opcoesDisponiveis, args);

    final CliCommand cliCommand = new CliCommand(args, opcoesDisponiveis, cli);
    commandHandler.handleCliCommand(cliCommand);

    assertTrue(cliCommand.isHandled());
    assertThat(systemOutRule.getLog(), allOf(containsString("Dados do Contrato"),
                                             containsString("Data Vencimento"),
                                             containsString("GCA Contrato"),
                                             containsString("Valor Parcelas"),
                                             containsString("GCA Dispensado"),
                                             containsString("Tipo de Interface"),
                                             containsString("Num. Caixa Postal")));
  }

  @Test
  public void testFaltandoCaminhoArquivo() throws Exception {
    final Options opcoesDisponiveis = new Options().addOption("h", "Print Ascii Table");
    final String[] args = {"-h"};

    CommandLineParser parser = new DefaultParser();
    final CommandLine cli = parser.parse(opcoesDisponiveis, args);

    final CliCommand cliCommand = new CliCommand(args, opcoesDisponiveis, cli);
    commandHandler.handleCliCommand(cliCommand);

    assertTrue(cliCommand.isHandled());
    assertThat(systemOutRule.getLog(), containsString("Faltando o caminho do arquivo para printar a Ascii Table"));
  }

  @Test
  public void testArquivoNaoExiste() throws Exception {
    final Options opcoesDisponiveis = new Options().addOption("h", "Print Ascii Table");
    final String arquivoEntrada = tempDir.getRoot().getAbsolutePath() + "/zupao-da-montanha";
    final String[] args = {"-h", arquivoEntrada};

    CommandLineParser parser = new DefaultParser();
    final CommandLine cli = parser.parse(opcoesDisponiveis, args);

    final CliCommand cliCommand = new CliCommand(args, opcoesDisponiveis, cli);
    commandHandler.handleCliCommand(cliCommand);

    assertTrue(cliCommand.isHandled());
    assertThat(systemOutRule.getLog(), containsString("O arquivo [" + arquivoEntrada + "] não existe"));
  }

  @Test
  public void testVariasPropostaAcordoPorArquivo() throws Exception {
    tempDir.create();
    final File arquivoTemporario = tempDir.newFile();

    log.debug("Arquivo temporario => {}", arquivoTemporario.getAbsolutePath());

    Files.copy(ClassLoader.getSystemResourceAsStream("exemplos/MULTIPLOS_PROPOSTA_ACORDO.DAT"), arquivoTemporario.toPath(), REPLACE_EXISTING);

    final Options opcoesDisponiveis = new Options().addOption("h", "Print Ascii Table");
    final String[] args = {"-h", arquivoTemporario.getAbsolutePath()};

    CommandLineParser parser = new DefaultParser();
    final CommandLine cli = parser.parse(opcoesDisponiveis, args);

    final CliCommand cliCommand = new CliCommand(args, opcoesDisponiveis, cli);
    commandHandler.handleCliCommand(cliCommand);

    assertTrue(cliCommand.isHandled());
    assertThat(systemOutRule.getLog(), allOf(containsString("Dados do Contrato"),
                                             containsString("Data Vencimento"),
                                             containsString("GCA Contrato"),
                                             containsString("Valor Parcelas"),
                                             containsString("GCA Dispensado"),
                                             containsString("Tipo de Interface"),
                                             containsString("Num. Caixa Postal")));
  }
}