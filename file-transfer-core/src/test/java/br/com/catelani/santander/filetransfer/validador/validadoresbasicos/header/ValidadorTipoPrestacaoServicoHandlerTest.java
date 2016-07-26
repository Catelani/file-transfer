package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header;

import br.com.catelani.santander.filetransfer.PropostaAcordoTestUtils;
import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.domain.TipoPrestacaoServico;
import br.com.catelani.santander.filetransfer.validador.ValidadorTestUtils;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordo;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro.ERRO_PREENCHIMENTO_TIPO_DE_PRESTACAO_SERVICO;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Kennedy Oliveira
 */
@RunWith(Parameterized.class)
public class ValidadorTipoPrestacaoServicoHandlerTest {

  @Parameterized.Parameter(0)
  public TipoPrestacaoServico tipoPrestacaoServico;

  @Parameterized.Parameter(1)
  public CodigoRetornoErro codigoRetornoErro;

  @Parameterized.Parameter(2)
  public boolean resultadoEsperado;

  @Parameterized.Parameters(name = "Teste: [{index}], Tipo Prestacao Servico: {0}, Codigo Retorno Esperado: {1}, Valido? {2}")
  public static Object[][] parameters() {
    return new Object[][]{
      {null, ERRO_PREENCHIMENTO_TIPO_DE_PRESTACAO_SERVICO, false},
      {TipoPrestacaoServico.ADVOGADO, null, true},
      {TipoPrestacaoServico.ASSESSORIA, null, true}
    };
  }

  @Test
  public void validadorNumeroLoteTest() throws Exception {
    ValidadorPropostaAcordo validador = new ValidadorTipoPrestacaoServicoHandler();

    final PropostaAcordo propostaAcordo = PropostaAcordoTestUtils.createPropostaAcordo();
    propostaAcordo.getCabecalho().getDadosPrestador().setTipoPrestacaoServico(tipoPrestacaoServico);

    final @NotNull List<ErroValidacao> res = validador.validar(propostaAcordo);

    assertThat(ValidadorTestUtils.isValid(res, codigoRetornoErro), is(resultadoEsperado));
  }
}