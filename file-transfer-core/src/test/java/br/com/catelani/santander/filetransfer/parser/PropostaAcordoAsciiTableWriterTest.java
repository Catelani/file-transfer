package br.com.catelani.santander.filetransfer.parser;

import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.qualifiers.AsciiTable;
import com.google.inject.Guice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import javax.inject.Inject;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author Kennedy Oliveira
 */
@RunWith(Parameterized.class)
public class PropostaAcordoAsciiTableWriterTest {

  @Parameter
  public String caminhoArquivoProposta;

  @Inject
  @AsciiTable
  private PropostaAcordoWriter propostaAcordoWriter;

  @Inject
  private PropostaAcordoReader propostaAcordoReader;

  @Parameters(name = "Gerando AsciiTable com arquivo \"{0}\"")
  public static Iterable<? extends String> data() {
    return Arrays.asList("exemplos/hf/erro/ESCPA0036.DAT", "exemplos/hf/erro/ESCRN0036.DAT", "exemplos/hf/erro/ESCPR0036.DAT");
  }

  @Before
  public void setUp() throws Exception {
    Guice.createInjector(new FileTransferModule()).injectMembers(this);
  }

  @Test
  public void testGerarAsciiTable() throws Exception {
    final InputStream arquivoProposta = ClassLoader.getSystemResourceAsStream(caminhoArquivoProposta);

    final PropostaAcordo propostaAcordo = propostaAcordoReader.parse(arquivoProposta);

    propostaAcordoWriter.gerarPropostaAcordo(propostaAcordo, System.out);
  }
}