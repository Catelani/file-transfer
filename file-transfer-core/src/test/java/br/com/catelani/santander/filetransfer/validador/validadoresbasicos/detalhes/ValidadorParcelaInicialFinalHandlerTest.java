package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.detalhes;

import br.com.catelani.santander.filetransfer.PropostaAcordoTestUtils;
import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.domain.DadosContrato;
import br.com.catelani.santander.filetransfer.domain.DetalhePropostaAcordo;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordo;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * @author Kennedy Oliveira
 */
public class ValidadorParcelaInicialFinalHandlerTest {

  @Test
  public void testParcelaInicialMaiorQueInicial() throws Exception {
    final DadosContrato dadosContrato = new DadosContrato();
    dadosContrato.setParcelaInicial(10); // invertido
    dadosContrato.setParcelaFinal(9); // invertido
    dadosContrato.setNumeroContrato("1234");

    final DetalhePropostaAcordo detalhePropostaAcordo = PropostaAcordoTestUtils.createDetalhePropostaAcordo();
    detalhePropostaAcordo.setDadosContrato(dadosContrato);

    final PropostaAcordo propostaAcordo = PropostaAcordoTestUtils.createPropostaAcordo();
    propostaAcordo.setDetalhes(Arrays.asList(detalhePropostaAcordo));

    final ValidadorPropostaAcordo validador = new ValidadorParcelaInicialFinalHandler();
    final List<ErroValidacao> resultadoValidacao = validador.validar(propostaAcordo);

    assertThat(resultadoValidacao, hasSize(1));
    assertThat(resultadoValidacao.get(0).getCodigoRetornoErro(), is(CodigoRetornoErro.ERRO_PREENCHIMENTO_PARCELA_NEGOCIACAO_FINAL));
  }

  @Test
  public void testParcelaInicialFinalDiferenteParaUmaMesmaNegociacao() throws Exception {
    final DadosContrato dadosContrato1 = new DadosContrato();
    dadosContrato1.setParcelaInicial(5);
    dadosContrato1.setParcelaFinal(6);
    dadosContrato1.setNumeroContrato("1234");

    final DadosContrato dadosContrato2 = new DadosContrato();
    dadosContrato2.setParcelaInicial(5);
    dadosContrato2.setParcelaFinal(7);
    dadosContrato2.setNumeroContrato("1234");

    final DetalhePropostaAcordo detalhePropostaAcordo1 = PropostaAcordoTestUtils.createDetalhePropostaAcordo();
    detalhePropostaAcordo1.setDadosContrato(dadosContrato1);

    final DetalhePropostaAcordo detalhePropostaAcordo2 = PropostaAcordoTestUtils.createDetalhePropostaAcordo();
    detalhePropostaAcordo2.setDadosContrato(dadosContrato2);

    final PropostaAcordo propostaAcordo = PropostaAcordoTestUtils.createPropostaAcordo();
    propostaAcordo.setDetalhes(Arrays.asList(detalhePropostaAcordo1, detalhePropostaAcordo2));

    final ValidadorPropostaAcordo validador = new ValidadorParcelaInicialFinalHandler();
    final List<ErroValidacao> resultadoValidacao = validador.validar(propostaAcordo);

    assertThat(resultadoValidacao, hasSize(1));
    assertThat(resultadoValidacao.get(0).getCodigoRetornoErro(), is(CodigoRetornoErro.ERRO_PREENCHIMENTO_PARCELA_NEGOCIACAO_FINAL));
  }

  @Test
  public void testInformacoesCorretas() throws Exception {
    final DadosContrato dadosContrato1 = new DadosContrato();
    dadosContrato1.setParcelaInicial(5);
    dadosContrato1.setParcelaFinal(6);
    dadosContrato1.setNumeroContrato("1234");

    final DadosContrato dadosContrato2 = new DadosContrato();
    dadosContrato2.setParcelaInicial(5);
    dadosContrato2.setParcelaFinal(6);
    dadosContrato2.setNumeroContrato("1234");

    final DetalhePropostaAcordo detalhePropostaAcordo1 = PropostaAcordoTestUtils.createDetalhePropostaAcordo();
    detalhePropostaAcordo1.setDadosContrato(dadosContrato1);

    final DetalhePropostaAcordo detalhePropostaAcordo2 = PropostaAcordoTestUtils.createDetalhePropostaAcordo();
    detalhePropostaAcordo2.setDadosContrato(dadosContrato2);

    final PropostaAcordo propostaAcordo = PropostaAcordoTestUtils.createPropostaAcordo();
    propostaAcordo.setDetalhes(Arrays.asList(detalhePropostaAcordo1, detalhePropostaAcordo2));

    final ValidadorPropostaAcordo validador = new ValidadorParcelaInicialFinalHandler();
    final List<ErroValidacao> resultadoValidacao = validador.validar(propostaAcordo);

    assertThat(resultadoValidacao, hasSize(0));
  }
}