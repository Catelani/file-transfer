package br.com.catelani.santander.filetransfer.parser;

import br.com.catelani.santander.filetransfer.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Kennedy Oliveira
 */
public class PropostaAcordoReaderImplTest {
  private PropostaAcordoReader propostaAcordoReader;
  private BigDecimal ZERO = new BigDecimal("0.00");

  @Before
  public void setUp() throws Exception {
    final ListaErroFactory listaErroFactory = new ListaErroFactoryImpl();
    propostaAcordoReader = new PropostaAcordoReaderImpl(new DetalhePropostaFactoryImpl(listaErroFactory), new CabecalhoPropostaAcordoFactoryImpl(listaErroFactory));
  }

  @Test
  public void testLerPropostaSimples() throws Exception {
    final InputStream arquivoProposta = ClassLoader.getSystemResourceAsStream("exemplos/ESCPA0001.dat");

    final PropostaAcordo propostaAcordo = propostaAcordoReader.parse(arquivoProposta, StandardCharsets.ISO_8859_1);

    assertThat(propostaAcordo, is(notNullValue()));
    final CabecalhoPropostaAcordo cabecalho = propostaAcordo.getCabecalho();

    assertThat(cabecalho, is(notNullValue()));

    assertThat(cabecalho.getTipoRegistro(), is(TipoRegistro.CONTROLE));
    assertThat(cabecalho.getTipoInterface(), is(TipoInterface.PROPOSTA_DE_ACORDO));

    //
    // Validação dos dados do prestador
    //
    final DadosPrestador dadosPrestador = cabecalho.getDadosPrestador();
    assertThat(dadosPrestador, is(notNullValue()));

    assertThat("caixa postal", dadosPrestador.getCaixaPostal(), is("0001"));
    assertThat("código GCA", dadosPrestador.getCodigoGCA(), is("0001094"));
    assertThat("nome do prestador", dadosPrestador.getNome(), is("A1 Soluções"));
    // 20160226
    assertThat("data da remessa do prestador", dadosPrestador.getDataRemessa(), is(LocalDate.of(2016, 2, 26)));
    // 175334
    assertThat("hora da remessa do prestador", dadosPrestador.getHoraRemessa(), is(LocalTime.of(17, 53, 34)));
    assertThat("numero de lote", dadosPrestador.getNumeroLote(), is(34L));
    assertThat("tipo prestação de servico", dadosPrestador.getTipoPrestacaoServico(), is(TipoPrestacaoServico.ASSESSORIA));
    assertThat("Quatidade de solicitações", dadosPrestador.getQuantidadeSolicitacoes(), is(10L));

    final List<DetalhePropostaAcordo> detalhes = propostaAcordo.getDetalhes();
    assertThat(detalhes, allOf(notNullValue(), not(empty())));

    final Optional<DetalhePropostaAcordo> acordoContrato = detalhes.stream()
                                                                   .filter(d -> "20018488914".equals(d.getDadosContrato().getNumeroContrato()))
                                                                   .findFirst();

    assertThat(acordoContrato.isPresent(), is(true));

    final DetalhePropostaAcordo detalheProposta = acordoContrato.get();
    assertThat(detalheProposta.getSequencia(), is(2));

    // dados do contrato
    final DadosContrato dadosContrato = detalheProposta.getDadosContrato();
    assertThat(dadosContrato.getNumeroContrato(), is("20018488914"));
    assertThat(dadosContrato.getParcelaInicial(), is(43));
    assertThat(dadosContrato.getParcelaFinal(), is(46));
    assertThat(dadosContrato.getDataVencimentoParcelaMaiorAtraso(), is(LocalDate.of(2015, 11, 20)));
    assertThat(dadosContrato.getValorTotalParcelasNegociadas(), is(BigDecimal.valueOf(1455.28D)));
    assertThat(dadosContrato.isContratoAjuizado(), is(false));
    assertThat(dadosContrato.getValorGCAContrato(), is(BigDecimal.valueOf(16.17D)));

    // dados da negociação
    final DadosNegociacao dadosNegociacao = detalheProposta.getDadosNegociacao();
    assertThat(dadosNegociacao.getValorSaldoGCADispensado(), is(ZERO));
    assertThat(dadosNegociacao.getValorTotalAcordo(), is(BigDecimal.valueOf(1695.88D)));
    assertThat(dadosNegociacao.getValorTotalAcordoSemHonorarios(), is(BigDecimal.valueOf(1543.18D)));
    assertThat(dadosNegociacao.getCodigoCampanhaRPC(), is("00577"));
    assertThat(dadosNegociacao.isIndicadorExcecaoCampanha(), is(false));
    assertThat(dadosNegociacao.getTipoNegociacao(), is(TipoNegociacao.ATUALIZACAO));
    assertThat(dadosNegociacao.getValorHonorarioMaximoCampanha(), is(BigDecimal.valueOf(152.70D).setScale(2)));
    assertThat(dadosNegociacao.getDescontoHonorarios(), is(ZERO));
    assertThat(dadosNegociacao.getQuantidadeParcelasAcordo(), is(1));

    // parcela do acordo
    final ParcelaAcordo parcelaAcordo = detalheProposta.getParcelaAcordo();
    assertThat(parcelaAcordo.getSequencia(), is(1));
    assertThat(parcelaAcordo.getDataVencimento(), is(LocalDate.of(2016, 3, 10)));
    assertThat(parcelaAcordo.getQuantidadeDiasAtraso(), is(111));
    assertThat(parcelaAcordo.getValorTotal(), is(BigDecimal.valueOf(1695.88D)));
    assertThat(parcelaAcordo.getValorGca(), is(BigDecimal.valueOf(16.17D)));
    assertThat(parcelaAcordo.getValorHonorario(), is(BigDecimal.valueOf(152.70D).setScale(2)));
  }

  @Test
  public void parseRespostaPropostaAcordo() throws Exception {
    final InputStream arquivoProposta = ClassLoader.getSystemResourceAsStream("exemplos/ESCRN0001_27022016.dat");

    final PropostaAcordo propostaAcordo = propostaAcordoReader.parse(arquivoProposta, StandardCharsets.ISO_8859_1);

    assertThat(propostaAcordo, is(notNullValue()));

    final CabecalhoPropostaAcordo cabecalho = propostaAcordo.getCabecalho();
    final RetornoFinanceiraPA retornoFinanceiraCabecalho = cabecalho.getRetornoFinanceira();

    assertThat(retornoFinanceiraCabecalho.getDataRetorno(), is(LocalDate.of(2016, 2, 26)));
    assertThat(retornoFinanceiraCabecalho.getHoraRetorno(), is(LocalTime.of(23, 0, 13)));
    assertThat(retornoFinanceiraCabecalho.getTipoRetornoFinanceira(), is(TipoRetornoFinanceira.NORMAL));
    assertThat(retornoFinanceiraCabecalho.getCodigoRetornoValidacao(), is(CodigoRetornoValidacao.HEADER_CORRETO));
    assertThat(retornoFinanceiraCabecalho.getErros(), is(Collections.emptyList()));

    final List<DetalhePropostaAcordo> detalhes = propostaAcordo.getDetalhes();

    final Optional<DetalhePropostaAcordo> acordoContrato = detalhes.stream()
                                                                   .filter(d -> "20018488914".equals(d.getDadosContrato().getNumeroContrato()))
                                                                   .findFirst();

    final DetalhePropostaAcordo detalheProposta = acordoContrato.get();

    assertThat(detalheProposta.getSequencia(), is(2));

    // dados do contrato
    final DadosContrato dadosContrato = detalheProposta.getDadosContrato();
    assertThat(dadosContrato.getNumeroContrato(), is("20018488914"));
    assertThat(dadosContrato.getParcelaInicial(), is(43));
    assertThat(dadosContrato.getParcelaFinal(), is(46));
    assertThat(dadosContrato.getDataVencimentoParcelaMaiorAtraso(), is(LocalDate.of(2015, 11, 20)));
    assertThat(dadosContrato.getValorTotalParcelasNegociadas(), is(BigDecimal.valueOf(1455.28D)));
    assertThat(dadosContrato.isContratoAjuizado(), is(false));
    assertThat(dadosContrato.getValorGCAContrato(), is(BigDecimal.valueOf(16.17D)));

    // dados da negociação
    final DadosNegociacao dadosNegociacao = detalheProposta.getDadosNegociacao();
    assertThat(dadosNegociacao.getValorSaldoGCADispensado(), is(ZERO));
    assertThat(dadosNegociacao.getValorTotalAcordo(), is(BigDecimal.valueOf(1695.88D)));
    assertThat(dadosNegociacao.getValorTotalAcordoSemHonorarios(), is(BigDecimal.valueOf(1543.18D)));
    assertThat(dadosNegociacao.getCodigoCampanhaRPC(), is("00577"));
    assertThat(dadosNegociacao.isIndicadorExcecaoCampanha(), is(false));
    assertThat(dadosNegociacao.getTipoNegociacao(), is(TipoNegociacao.ATUALIZACAO));
    assertThat(dadosNegociacao.getValorHonorarioMaximoCampanha(), is(BigDecimal.valueOf(152.70D).setScale(2)));
    assertThat(dadosNegociacao.getDescontoHonorarios(), is(ZERO));
    assertThat(dadosNegociacao.getQuantidadeParcelasAcordo(), is(1));

    // parcela do acordo
    final ParcelaAcordo parcelaAcordo = detalheProposta.getParcelaAcordo();
    assertThat(parcelaAcordo.getSequencia(), is(1));
    assertThat(parcelaAcordo.getDataVencimento(), is(LocalDate.of(2016, 3, 10)));
    assertThat(parcelaAcordo.getQuantidadeDiasAtraso(), is(111));
    assertThat(parcelaAcordo.getValorTotal(), is(BigDecimal.valueOf(1695.88D)));
    assertThat(parcelaAcordo.getValorGca(), is(BigDecimal.valueOf(16.17D)));
    assertThat(parcelaAcordo.getValorHonorario(), is(BigDecimal.valueOf(152.70D).setScale(2)));

    // retorno da financeira
    final RetornoFinanceiraDetalhe retornoFinanceira = detalheProposta.getRetornoFinanceiraDetalhe();
    assertThat(retornoFinanceira.getCodigoRetornoValidacao(), is(CodigoRetornoValidacao.ACORDO_APROVADO));
    assertThat(retornoFinanceira.getCodigoRetornoErros(), is(empty()));
    assertThat(retornoFinanceira.getNumeroAcordoRPCGerado(), is("00010121045"));
    assertThat(retornoFinanceira.getLinhaDigitavelBoletoGerado(), is("03399504617690018488891410001027267290000169588"));
  }

  @Test
  public void lerRetornoComErro() throws Exception {
    final InputStream arquivoProposta = ClassLoader.getSystemResourceAsStream("exemplos/hf/erro/ESCRN0036.DAT");

    final PropostaAcordo propostaAcordo = propostaAcordoReader.parse(arquivoProposta, StandardCharsets.ISO_8859_1);

    assertThat(propostaAcordo, is(notNullValue()));

    final CabecalhoPropostaAcordo cabecalho = propostaAcordo.getCabecalho();
    final RetornoFinanceiraPA retornoFinanceiraCabecalho = cabecalho.getRetornoFinanceira();

    assertThat(retornoFinanceiraCabecalho.getDataRetorno(), is(LocalDate.of(2016, 7, 22)));
    assertThat(retornoFinanceiraCabecalho.getHoraRetorno(), is(LocalTime.of(23, 5, 58)));
    assertThat(retornoFinanceiraCabecalho.getTipoRetornoFinanceira(), is(TipoRetornoFinanceira.NORMAL));
    assertThat(retornoFinanceiraCabecalho.getCodigoRetornoValidacao(), is(CodigoRetornoValidacao.HEADER_COM_ERRO));
    assertThat(retornoFinanceiraCabecalho.getErros(), is(not(Collections.emptyList())));
    assertThat(retornoFinanceiraCabecalho.getErros(), hasSize(2));
    assertThat(retornoFinanceiraCabecalho.getErros(), contains(CodigoRetornoErro.getByCodigoErro("304"), CodigoRetornoErro.getByCodigoErro("303")));

    final List<DetalhePropostaAcordo> detalhes = propostaAcordo.getDetalhes();
    assertThat(detalhes, hasSize(1));

    final DetalhePropostaAcordo detalheProposta = detalhes.get(0);
    assertThat(detalheProposta.getSequencia(), is(2));

    // dados do contrato
    final DadosContrato dadosContrato = detalheProposta.getDadosContrato();
    assertThat(dadosContrato.getNumeroContrato(), is("20021472358"));
    assertThat(dadosContrato.getParcelaInicial(), is(28));
    assertThat(dadosContrato.getParcelaFinal(), is(28));
    assertThat(dadosContrato.getDataVencimentoParcelaMaiorAtraso(), is(LocalDate.of(2016, 6, 28))); // 20160628
    assertThat(dadosContrato.getValorTotalParcelasNegociadas(), is(BigDecimal.valueOf(527.85D)));
    assertThat(dadosContrato.isContratoAjuizado(), is(false));
    assertThat(dadosContrato.getValorGCAContrato(), is(BigDecimal.valueOf(18.63D)));

    // dados da negociação
    final DadosNegociacao dadosNegociacao = detalheProposta.getDadosNegociacao();
    assertThat(dadosNegociacao.getValorSaldoGCADispensado(), is(ZERO));
    assertThat(dadosNegociacao.getValorTotalAcordo(), is(BigDecimal.valueOf(527.85D)));
    assertThat(dadosNegociacao.getValorTotalAcordoSemHonorarios(), is(BigDecimal.valueOf(479.86D)));
    assertThat(dadosNegociacao.getCodigoCampanhaRPC(), is("00615"));
    assertThat(dadosNegociacao.isIndicadorExcecaoCampanha(), is(false));
    assertThat(dadosNegociacao.getTipoNegociacao(), is(TipoNegociacao.ATUALIZACAO));
    assertThat(dadosNegociacao.getValorHonorarioMaximoCampanha(), is(BigDecimal.valueOf(47.99)));
    assertThat(dadosNegociacao.getDescontoHonorarios(), is(ZERO));
    assertThat(dadosNegociacao.getQuantidadeParcelasAcordo(), is(1));

    // parcela do acordo
    final ParcelaAcordo parcelaAcordo = detalheProposta.getParcelaAcordo();
    assertThat(parcelaAcordo.getSequencia(), is(1));
    assertThat(parcelaAcordo.getDataVencimento(), is(LocalDate.of(2016, 7, 25)));
    assertThat(parcelaAcordo.getQuantidadeDiasAtraso(), is(24));
    assertThat(parcelaAcordo.getValorTotal(), is(BigDecimal.valueOf(527.85D)));
    assertThat(parcelaAcordo.getValorGca(), is(BigDecimal.valueOf(18.63D)));
    assertThat(parcelaAcordo.getValorHonorario(), is(BigDecimal.valueOf(47.99D)));

    // retorno da financeira
    final RetornoFinanceiraDetalhe retornoFinanceira = detalheProposta.getRetornoFinanceiraDetalhe();
    assertThat(retornoFinanceira.getCodigoRetornoValidacao(), is(CodigoRetornoValidacao.ACORDO_COM_ERRO_DE_PREENCHIMENTO));
    assertThat(retornoFinanceira.getCodigoRetornoErros(), is(not(empty())));
    assertThat(retornoFinanceira.getCodigoRetornoErros(), contains(CodigoRetornoErro.getByCodigoErro("319"),
                                                                   CodigoRetornoErro.getByCodigoErro("314"),
                                                                   CodigoRetornoErro.getByCodigoErro("342"),
                                                                   CodigoRetornoErro.getByCodigoErro("317"),
                                                                   CodigoRetornoErro.getByCodigoErro("340")));
    assertThat(retornoFinanceira.getNumeroAcordoRPCGerado(), is("00000000000"));
    assertThat(retornoFinanceira.getLinhaDigitavelBoletoGerado(), is(nullValue()));
  }
}