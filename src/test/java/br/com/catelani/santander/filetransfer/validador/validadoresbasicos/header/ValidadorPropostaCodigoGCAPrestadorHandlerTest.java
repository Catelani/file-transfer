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

import static br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro.ERRO_PREENCHIMENTO_CODIGO_GCA_PRESTADOR;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Kennedy Oliveira
 */
@RunWith(Parameterized.class)
public class ValidadorPropostaCodigoGCAPrestadorHandlerTest {

  @Parameterized.Parameter(0)
  public String codigoGca;

  @Parameterized.Parameter(1)
  public CodigoRetornoErro codigoRetornoErro;

  @Parameterized.Parameter(2)
  public boolean resultadoEsperado;

  @Parameterized.Parameters(name = "Teste: [{index}], Número Sequencia: {0}, Codigo Retorno Esperado: {1}, Valido? {2}")
  public static Object[][] parameters() {
    return new Object[][]{
      {null, ERRO_PREENCHIMENTO_CODIGO_GCA_PRESTADOR, false},
      {"00000", ERRO_PREENCHIMENTO_CODIGO_GCA_PRESTADOR, false},
      {"zupao", ERRO_PREENCHIMENTO_CODIGO_GCA_PRESTADOR, false},
      {"1234567", null, true}
    };
  }

  @Test
  public void testValidadorCodigoGCA() throws Exception {
    ValidadorPropostaAcordo validador = new ValidadorPropostaCodigoGCAPrestadorHandler();

    final PropostaAcordo propostaAcordo = PropostaAcordoTestUtils.createPropostaAcordo();
    propostaAcordo.getCabecalho().getDadosPrestador().setCodigoGCA(codigoGca);

    final @NotNull List<ErroValidacao> res = validador.validar(propostaAcordo);

    assertThat(ValidadorTestUtils.isValid(res, codigoRetornoErro), is(resultadoEsperado));
  }
}