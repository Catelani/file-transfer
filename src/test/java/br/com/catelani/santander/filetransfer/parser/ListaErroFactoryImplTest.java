package br.com.catelani.santander.filetransfer.parser;

import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author Kennedy Oliveira
 */
public class ListaErroFactoryImplTest {

  private ListaErroFactory listaErroFactory;

  @Before
  public void setUp() throws Exception {
    listaErroFactory = new ListaErroFactoryImpl();
  }

  @Test(expected = NullPointerException.class)
  public void parsearListaVaziaDeveLancarNullPointerException() throws Exception {
    listaErroFactory.parseErros(null);
  }

  @Test
  public void parsearErrosVazios() throws Exception {
    final List<CodigoRetornoErro> codigoRetornoErros = listaErroFactory.parseErros("000000000000");

    assertThat(codigoRetornoErros, is(emptyCollectionOf(CodigoRetornoErro.class)));
  }

  @Test
  public void parsearError() throws Exception {
    final List<CodigoRetornoErro> codigoRetornoErros = listaErroFactory.parseErros("301302303");

    assertThat(codigoRetornoErros, containsInAnyOrder(CodigoRetornoErro.getByCodigoErro("301"),CodigoRetornoErro.getByCodigoErro("302"),CodigoRetornoErro.getByCodigoErro("303")));
  }

  @Test(expected = IllegalArgumentException.class)
  public void parserErroNaoExistente() throws Exception {
    listaErroFactory.parseErros("301005302");
  }

  @Test(expected = IllegalArgumentException.class)
  public void parsearArgumentoComTamanhoMenorQueOPermitido() throws Exception {
    listaErroFactory.parseErros("30");
  }

  @Test
  public void parsearAgumentoComTamanhoMisturado() throws Exception {
    final List<CodigoRetornoErro> codigoRetornoErros = listaErroFactory.parseErros("30130");

    assertThat(codigoRetornoErros, hasSize(1));
    assertThat(codigoRetornoErros, contains(CodigoRetornoErro.getByCodigoErro("301")));
  }
}