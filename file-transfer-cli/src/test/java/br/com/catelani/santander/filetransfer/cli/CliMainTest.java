package br.com.catelani.santander.filetransfer.cli;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * @author Kennedy Oliveira
 */
public class CliMainTest {

  @Rule
  public SystemOutRule systemOutRule = new SystemOutRule().enableLog();

  @Test
  public void testSemNenhumaOpcaoDeveImprimirAjuda() throws Exception {
    CliMain.main(new String[0]);

    assertThat(systemOutRule.getLog(), containsString("usage: java -jar file-transfer-cli.jar"));
  }

  @Test
  public void testOpcaoNaoExistente() throws Exception {
    CliMain.main(new String[]{"--zupao"});

    assertThat(systemOutRule.getLog(), containsString("Opção \"--zupao\" não reconhecida."));
  }

  @Test
  public void testOpcaoAjuda() throws Exception {
    CliMain.main(new String[]{"--help"});

    assertThat(systemOutRule.getLog(), containsString("usage: java -jar file-transfer-cli.jar"));

  }
}