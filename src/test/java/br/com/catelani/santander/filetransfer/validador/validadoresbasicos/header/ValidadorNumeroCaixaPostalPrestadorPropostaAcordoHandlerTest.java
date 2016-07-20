package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header;

import br.com.catelani.santander.filetransfer.PropostaAcordoTestUtils;
import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.validador.ValidadorTestUtils;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordo;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro.ERRO_PREENCHIMENTO_NUMERO_CAIXA_POSTAL;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Kennedy Oliveira
 */
@RunWith(Parameterized.class)
public class ValidadorNumeroCaixaPostalPrestadorPropostaAcordoHandlerTest {

  @Parameterized.Parameter(0)
  public String caixaPostal;

  @Parameterized.Parameter(1)
  public CodigoRetornoErro codigoRetornoErro;

  @Parameterized.Parameter(2)
  public boolean resultadoEsperado;

  @Parameterized.Parameters(name = "Teste: [{index}], Caixa Postal: {0}, Codigo Retorno Esperado: {1}, Valido? {2}")
  public static Object[][] parameters() {
    return new Object[][]{
      {null, ERRO_PREENCHIMENTO_NUMERO_CAIXA_POSTAL, false},
      {"abcd", ERRO_PREENCHIMENTO_NUMERO_CAIXA_POSTAL, false},
      {"0036", null, true},
      {"00365", ERRO_PREENCHIMENTO_NUMERO_CAIXA_POSTAL, false},
    };
  }

  @Test
  public void validarNumeroCaixaPostalPrestador() throws Exception {
    ValidadorPropostaAcordo validador = new ValidadorNumeroCaixaPostalPrestadorPropostaAcordoHandler();

    final PropostaAcordo pa = PropostaAcordoTestUtils.createPropostaAcordo();

    pa.getCabecalho().getDadosPrestador().setCaixaPostal(caixaPostal);

    @NotNull final List<ErroValidacao> resValidacao = validador.validar(pa);

    assertThat(ValidadorTestUtils.isValid(resValidacao, codigoRetornoErro), is(resultadoEsperado));
  }
}