package br.com.catelani.santander.filetransfer.cli.commands;

import br.com.catelani.santander.filetransfer.cli.CliCommand;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

/**
 * @author Kennedy Oliveira
 */
public class AjudaCliCommandHandlerTest {
  private CliCommandHandler commandHandler;

  @Rule
  public SystemOutRule systemOutRule = new SystemOutRule().enableLog();

  @Before
  public void setUp() throws Exception {
    this.commandHandler = new AjudaCliCommandHandler();
  }

  @Test
  public void testComFlagajuda() throws Exception {
    final Options opcoesDisponiveis = new Options().addOption("a", "Ajuda");
    final String[] args = {"-a"};

    CommandLineParser parser = new DefaultParser();
    final CommandLine cli = parser.parse(opcoesDisponiveis, args);

    final CliCommand cliCommand = new CliCommand(args, opcoesDisponiveis, cli);
    commandHandler.handleCliCommand(cliCommand);

    assertTrue(cliCommand.isHandled());
    assertThat(systemOutRule.getLog(), containsString("java -jar file-transfer-cli.jar"));
  }

  @Test
  public void testSemFlagAjuda() throws Exception {
    final Options opcoesDisponiveis = new Options().addOption("z", "Zupa");
    final String[] args = {"-z"};

    CommandLineParser parser = new DefaultParser();
    final CommandLine cli = parser.parse(opcoesDisponiveis, args);

    final CliCommand cliCommand = new CliCommand(args, opcoesDisponiveis, cli);
    commandHandler.handleCliCommand(cliCommand);

    assertFalse(cliCommand.isHandled());
    assertThat(systemOutRule.getLog(), not(containsString("java -jar file-transfer-cli.jar")));
  }
}