package br.com.catelani.santander.filetransfer.parser;

import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.qualifiers.AsciiTable;
import com.google.inject.Guice;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.io.InputStream;

/**
 * @author Kennedy Oliveira
 */
public class PropostaAcordoAsciiTableWriterTest {

  @Inject
  @AsciiTable
  private PropostaAcordoWriter propostaAcordoWriter;

  @Inject
  private PropostaAcordoReader propostaAcordoReader;

  @Before
  public void setUp() throws Exception {
    Guice.createInjector(new FileTransferModule()).injectMembers(this);
  }

  @Test
  public void gerarPropostaAcordo() throws Exception {
    final InputStream arquivoProposta = ClassLoader.getSystemResourceAsStream("exemplos/ESCPA0001.dat");

    final PropostaAcordo propostaAcordo = propostaAcordoReader.parse(arquivoProposta);

    propostaAcordoWriter.gerarPropostaAcordo(propostaAcordo, System.out);
  }
}