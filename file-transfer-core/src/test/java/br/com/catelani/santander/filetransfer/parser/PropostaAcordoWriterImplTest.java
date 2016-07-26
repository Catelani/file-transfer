package br.com.catelani.santander.filetransfer.parser;

import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Kennedy Oliveira
 */
public class PropostaAcordoWriterImplTest {

  @Rule
  public TemporaryFolder tmpFolder = new TemporaryFolder();

  @Test
  public void gerarPropostaAcordo() throws Exception {
    final ListaErroFactory listaErroFactory = new ListaErroFactoryImpl();
    PropostaAcordoReader propostaAcordoReader = new PropostaAcordoReaderImpl(new DetalhePropostaFactoryImpl(listaErroFactory),
                                                                             new CabecalhoPropostaAcordoFactoryImpl(listaErroFactory));

    final InputStream arquivoProposta = ClassLoader.getSystemResourceAsStream("exemplos/ESCPA0001.dat");

    final PropostaAcordo propostaAcordo = propostaAcordoReader.parse(arquivoProposta, StandardCharsets.ISO_8859_1);

    PropostaAcordoWriter propostaAcordoWriter = new PropostaAcordoWriterImpl();

    final File arquivoSaida = tmpFolder.newFile();

    propostaAcordoWriter.gerarPropostaAcordo(propostaAcordo, Files.newOutputStream(arquivoSaida.toPath()));

    final List<String> linhasArquivoSaida = Files.readAllLines(arquivoSaida.toPath());

    assertThat(linhasArquivoSaida, hasSize(11));

    final String header = linhasArquivoSaida.get(0);
    final String expectedHeader = "0PA00010001094A1 Soluções                             20160226175334  000003402500010                                                                                                                                                                                                                                                                                                                      00001";
    final String expectedDetails1 = "2012001848891404304620151120000000000145528N00000000000161700000000000000000000000016958800000000015431800577NA000000000015270000000000000000001001201603100111000000000169588000000000001617000000000015270                                                                                                                                                                                               00002";
    final String expectedDetails2 = "2012001925870203704020151119000000000154428N00000000000155300000000000000000000000017834100000000016226900577NA000000000016072000000000000000001001201603100112000000000178341000000000001553000000000016072                                                                                                                                                                                               00003";
    final String expectedDetails3 = "2012001788310704704820151116000000000043352N00000000000000000000000000000000000000005120000000000004654500576NQ000000000004655000000000000000001001201603100115000000000051200000000000000000000000000004655                                                                                                                                                                                               00004";

    assertThat(header, is(expectedHeader));
    assertThat(linhasArquivoSaida.get(1), is(expectedDetails1));
    assertThat(linhasArquivoSaida.get(2), is(expectedDetails2));
    assertThat(linhasArquivoSaida.get(3), is(expectedDetails3));
  }
}