package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header;

import br.com.catelani.santander.filetransfer.PropostaAcordoTestUtils;
import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.domain.DetalhePropostaAcordo;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.validador.ValidadorTestUtils;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordo;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;
import java.util.stream.IntStream;

import static br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro.ERRO_PREENCHIMENTO_QUATIDADE_DE_SOLICITACOES;
import static br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro.QUANTIDADE_SOLICITACOES_HEADER_DIVERGENTE_ACORDOS_ENVIADOS;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Kennedy Oliveira
 */
@RunWith(Parameterized.class)
public class ValidadorQuantidadeSolicitacoesHandlerTest {

  @Parameterized.Parameter(0)
  public int quantidadeSolicitacoes;

  @Parameterized.Parameter(1)
  public int quantidadeDetalhes;

  @Parameterized.Parameter(2)
  public CodigoRetornoErro codigoRetornoErro;

  @Parameterized.Parameter(3)
  public boolean resultadoEsperado;

  @Parameterized.Parameters(name = "Teste: [{index}], Quantidade de Solicitações: {0}, Quatidade de Detalhes: {1}, Codigo Retorno Esperado: {2}, Valido? {3}")
  public static Object[][] parameters() {
    return new Object[][]{
      {0, 0, ERRO_PREENCHIMENTO_QUATIDADE_DE_SOLICITACOES, false},
      {1, 3, QUANTIDADE_SOLICITACOES_HEADER_DIVERGENTE_ACORDOS_ENVIADOS, false},
      {1, 1, null, true},
      {1000, 1000, null, true}
    };
  }

  @Test
  public void testValidarQuantidadeSolicitacoes() throws Exception {
    ValidadorPropostaAcordo validador = new ValidadorQuantidadeSolicitacoesHandler();

    final PropostaAcordo propostaAcordo = PropostaAcordoTestUtils.createPropostaAcordo();
    propostaAcordo.getCabecalho().getDadosPrestador().setQuantidadeSolicitacoes(quantidadeSolicitacoes);

    // adiciono o total de detalhes de acordo com o parametro
    IntStream.range(0, quantidadeDetalhes).mapToObj(i -> new DetalhePropostaAcordo(null)).forEach(propostaAcordo.getDetalhes()::add);

    final @NotNull List<ErroValidacao> res = validador.validar(propostaAcordo);

    assertThat(ValidadorTestUtils.isValid(res, codigoRetornoErro), is(resultadoEsperado));
  }
}