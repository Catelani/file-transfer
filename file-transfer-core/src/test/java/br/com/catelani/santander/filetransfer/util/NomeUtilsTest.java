package br.com.catelani.santander.filetransfer.util;

import br.com.catelani.santander.filetransfer.domain.*;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author Kennedy Oliveira
 */
public class NomeUtilsTest {

  @Test
  public void getNomeArquivoPropostaAcordo() throws Exception {
    final PropostaAcordo propostaAcordo = new PropostaAcordo(null, Collections.emptyList());

    try {
      NomeUtils.getNomeArquivoPropostaAcordo(propostaAcordo);
      fail("Deveria ter lançado exception pois não tem cabeçalho!");
    } catch (NullPointerException e) { }

    final CabecalhoPropostaAcordo cabecalho = new CabecalhoPropostaAcordo();
    propostaAcordo.setCabecalho(cabecalho);

    try {
      NomeUtils.getNomeArquivoPropostaAcordo(propostaAcordo);
      fail("Deveria ter lançado exception pois não tem dados do prestador");
    } catch (NullPointerException e) { }

    final DadosPrestador dadosPrestador = new DadosPrestador();
    cabecalho.setDadosPrestador(dadosPrestador);

    // sem código de caixa postal, deve dar erro
    try {
      NomeUtils.getNomeArquivoPropostaAcordo(propostaAcordo);
      fail("Deveria ter lançado exception pois não tenho codigo de caixa postal");
    } catch (IllegalStateException e) { }


    // todos os dados corretos agora5
    dadosPrestador.setCaixaPostal("1234");

    final String nomeArquivoPropostaAcordo = NomeUtils.getNomeArquivoPropostaAcordo(propostaAcordo);
    assertThat(nomeArquivoPropostaAcordo, is("ESCPA1234.DAT"));
  }

  @Test
  public void getTipoRetornoFinanceiraPeloNomeArquivo() throws Exception {

    try {
      NomeUtils.getTipoRetornoFinanceiraPeloNomeArquivo("ASC123");
      fail("Deveria ter lançado exception, pois o nome é inválido");
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(), containsString("O nome informado não é um nome valido para um arquivo do banco"));
    }

    try {
      NomeUtils.getTipoRetornoFinanceiraPeloNomeArquivo("ESCRX1234.DAT");
      fail("Deveria ter lançado exception pois não é um tipo de retorno valido do banco.");
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(), containsString("O nome informado não é um nome valido para um arquivo do banco"));
    }

    final TipoRetornoFinanceira retornoNormal = NomeUtils.getTipoRetornoFinanceiraPeloNomeArquivo("ESCRN1234.DAT");
    final TipoRetornoFinanceira retornoExcecao = NomeUtils.getTipoRetornoFinanceiraPeloNomeArquivo("ESCRE1234.DAT");
    final TipoRetornoFinanceira retornoBoletosSeguintes = NomeUtils.getTipoRetornoFinanceiraPeloNomeArquivo("ESCRB1234.DAT");

    assertThat(retornoNormal, is(TipoRetornoFinanceira.NORMAL));
    assertThat(retornoExcecao, is(TipoRetornoFinanceira.EXCECAO));
    assertThat(retornoBoletosSeguintes, is(TipoRetornoFinanceira.BOLETOS_SEGUINTES));
  }

  @Test
  public void getTipoInterfacePeloNomeArquivo() throws Exception {
    try {
      NomeUtils.getTipoInterfacePeloNomeArquivo("zupao");
      fail("Deveria ter lançado exception, pois o nome não é no formato valido do banco.");
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(), containsString("O nome informado não é um nome valido para um arquivo do banco"));
    }

    try {
      NomeUtils.getTipoInterfacePeloNomeArquivo("ESCPX1234.dat");
      fail("Deveria ter lançado exception, pois o nome não é no formato valido do banco.");
    } catch (Exception e) {
      assertThat(e.getMessage(), containsString("O nome informado não é um nome valido para um arquivo do banco"));
    }

    final TipoInterface tipoPropostaAcordo = NomeUtils.getTipoInterfacePeloNomeArquivo("ESCPA1234.DAT");
    final TipoInterface tipoRetornoNormal = NomeUtils.getTipoInterfacePeloNomeArquivo("ESCRN1234.DAT");
    final TipoInterface tipoRetornoExcecao = NomeUtils.getTipoInterfacePeloNomeArquivo("ESCRE1234.DAT");
    final TipoInterface tipoRetornoBoletosSeguintes = NomeUtils.getTipoInterfacePeloNomeArquivo("ESCRB1234.DAT");
    final TipoInterface tipoProtocoleRecebimento = NomeUtils.getTipoInterfacePeloNomeArquivo("ESCPR1234.DAT");

    assertThat(tipoPropostaAcordo, is(TipoInterface.PROPOSTA_DE_ACORDO));
    assertThat(tipoRetornoNormal, is(TipoInterface.RETORNO_FINANCEIRA));
    assertThat(tipoRetornoExcecao, is(TipoInterface.RETORNO_FINANCEIRA));
    assertThat(tipoRetornoBoletosSeguintes, is(TipoInterface.RETORNO_FINANCEIRA));
    assertThat(tipoProtocoleRecebimento, is(TipoInterface.PROTOCOLO_DE_RECEBIMENTO));
  }
}