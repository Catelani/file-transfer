package br.com.catelani.santander.filetransfer.parser;

import br.com.catelani.santander.filetransfer.domain.*;
import br.com.catelani.santander.filetransfer.util.BigDecimalUtils;
import br.com.catelani.santander.filetransfer.util.BooleanUtils;
import br.com.catelani.santander.filetransfer.util.DateUtils;
import br.com.catelani.santander.filetransfer.util.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.*;

/**
 * Factory para criação de {@link DetalhePropostaAcordo}.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
class DetalhePropostaFactoryImpl implements DetalhePropostaFactory {

  private Map<String, DetalhePropostaAcordo.DetalhesCompartilhados> detalhesCompartilhadosPorContrato = new HashMap<>();
  private ListaErroFactory listaErroFactory;

  @Inject
  public DetalhePropostaFactoryImpl(ListaErroFactory listaErroFactory) {
    this.listaErroFactory = listaErroFactory;
  }

  @Override
  public DetalhePropostaAcordo buildDetalheProposta(String[] informacoesAcordo) {
    if (informacoesAcordo.length != 31)
      throw new IllegalArgumentException("O Array de informações deve conter 31 informações!");

    final String numeroSequencia = informacoesAcordo[30];
    final DetalhePropostaAcordo.DetalhesCompartilhados detalhesCompartilhados = getDetalhesCompartilhados(informacoesAcordo);
    final ParcelaAcordo parcelaAcordo = buildParcelaAcordo(informacoesAcordo);
    final RetornoFinanceiraDetalhe retornoFinanceiraDetalhe = buildRetornoFinanceira(informacoesAcordo);

    final DetalhePropostaAcordo detalhePropostaAcordo = new DetalhePropostaAcordo(detalhesCompartilhados);

    detalhePropostaAcordo.setSequencia(Integer.parseInt(numeroSequencia));
    detalhePropostaAcordo.setRetornoFinanceiraDetalhe(retornoFinanceiraDetalhe);
    detalhePropostaAcordo.setParcelaAcordo(parcelaAcordo);

    return detalhePropostaAcordo;
  }

  @NotNull
  private DetalhePropostaAcordo.DetalhesCompartilhados getDetalhesCompartilhados(String[] informacoesAcordo) {
    final String numeroContratoFinanciado = informacoesAcordo[2];

    // pego a parte compartilhada no map e caso não tiver, eu crio uma nova
    return detalhesCompartilhadosPorContrato.computeIfAbsent(numeroContratoFinanciado, i -> buildDetalhePropostaAcordoCompartilhado(informacoesAcordo));
  }

  @NotNull
  private DetalhePropostaAcordo.DetalhesCompartilhados buildDetalhePropostaAcordoCompartilhado(String[] informacoesAcordo) {
    // final String tipoRegistro = informacoesAcordo[0]; // por enquanto é fixo, deixando aqui só pra lembrar
    final String tipoCarteira = informacoesAcordo[1];

    final DadosContrato dadosContrato = buildDadosContrato(informacoesAcordo);
    final DadosNegociacao dadosNegociacao = buildDadosNegociacao(informacoesAcordo);

    final DetalhePropostaAcordo.DetalhesCompartilhados detalhesCompartilhados = new DetalhePropostaAcordo.DetalhesCompartilhados();
    detalhesCompartilhados.setDadosNegociacao(dadosNegociacao);
    detalhesCompartilhados.setDadosContrato(dadosContrato);
    detalhesCompartilhados.setTipoCarteira(TipoCarteira.getByTipo(tipoCarteira));

    return detalhesCompartilhados;
  }

  @NotNull
  private RetornoFinanceiraDetalhe buildRetornoFinanceira(String[] informacoesAcordo) {
    final String retornoValidacaoFinanceira = informacoesAcordo[25];
    final Optional<String> listaCodigoErros = Optional.ofNullable(informacoesAcordo[26]);
    final String numeroAcordoRCPGerado = getNumeroAcordoRPCGerado(informacoesAcordo[27]);
    final String linhaDigitalBoleto = informacoesAcordo[28];

    final List<CodigoRetornoErro> listaErros = listaCodigoErros.map(listaErroFactory::parseErros).orElse(Collections.emptyList());

    final RetornoFinanceiraDetalhe retornoFinanceiraDetalhe = new RetornoFinanceiraDetalhe();

    retornoFinanceiraDetalhe.setCodigoRetornoValidacao(CodigoRetornoValidacao.getByCodigo(retornoValidacaoFinanceira));
    retornoFinanceiraDetalhe.setCodigoRetornoErros(listaErros);
    retornoFinanceiraDetalhe.setNumeroAcordoRPCGerado(numeroAcordoRCPGerado);
    retornoFinanceiraDetalhe.setLinhaDigitavelBoletoGerado(linhaDigitalBoleto);

    return retornoFinanceiraDetalhe;
  }

  /**
   * Tratamento para os numeros de acordos gerados. O Banco envia {@code 0000000} quando não gera acordo ou em branco.
   *
   * @param numeroAcordo Numero de acordo fornecido pelo banco
   * @return {@code null} caso o numero de acordo seja {@code null}, em branco ou {@code 0000000}.
   */
  private String getNumeroAcordoRPCGerado(String numeroAcordo) {
    if (StringUtils.isNullOrEmpty(numeroAcordo))
      return null;

    return numeroAcordo.matches("0*") ? null : numeroAcordo;
  }

  @NotNull
  private ParcelaAcordo buildParcelaAcordo(String[] informacoesAcordo) {
    final String sequenciaParcelaAcordo = informacoesAcordo[18];
    final String dataVencimentoParcelaAcordo = informacoesAcordo[19];
    final String quantidadeDiasAtrasoParcelaAcordo = informacoesAcordo[20];
    final String valorTotalParcelaAcordo = informacoesAcordo[21];
    final String valorGCAParcelaAcordo = informacoesAcordo[22];
    final String valorHonorarioParcelaAcordo = informacoesAcordo[23];

    final ParcelaAcordo parcelaAcordo = new ParcelaAcordo();

    parcelaAcordo.setSequencia(Integer.parseInt(sequenciaParcelaAcordo));
    parcelaAcordo.setDataVencimento(DateUtils.parseDate(dataVencimentoParcelaAcordo));
    parcelaAcordo.setQuantidadeDiasAtraso(Integer.parseInt(quantidadeDiasAtrasoParcelaAcordo));
    parcelaAcordo.setValorTotal(BigDecimalUtils.parseBigDecimal(valorTotalParcelaAcordo)); // TODO Criar forma de ler os big decimal corretamente
    parcelaAcordo.setValorGca(BigDecimalUtils.parseBigDecimal(valorGCAParcelaAcordo));
    parcelaAcordo.setValorHonorario(BigDecimalUtils.parseBigDecimal(valorHonorarioParcelaAcordo));

    return parcelaAcordo;
  }

  @NotNull
  private DadosNegociacao buildDadosNegociacao(String[] informacoesAcordo) {
    final String valorSaldoGCADispensado = informacoesAcordo[9];
    final String valorTotalNegociadoComCliente = informacoesAcordo[10];
    final String valorTotalNegociadoComClienteSemHonorarios = informacoesAcordo[11];
    final String codigoMotivoCampanhaRPC = informacoesAcordo[12];
    final String indicadorExcecaoCampanha = informacoesAcordo[13];
    final String tipoNegociacao = informacoesAcordo[14];
    final String valorHonorarioMaximoCampanha = informacoesAcordo[15];
    final String descontoHonorarios = informacoesAcordo[16];
    final String quantidadeTotalParcelasAcordo = informacoesAcordo[17];

    final DadosNegociacao dadosNegociacao = new DadosNegociacao();

    dadosNegociacao.setValorSaldoGCADispensado(BigDecimalUtils.parseBigDecimal(valorSaldoGCADispensado));
    dadosNegociacao.setValorTotalAcordo(BigDecimalUtils.parseBigDecimal(valorTotalNegociadoComCliente));
    dadosNegociacao.setValorTotalAcordoSemHonorarios(BigDecimalUtils.parseBigDecimal(valorTotalNegociadoComClienteSemHonorarios));
    dadosNegociacao.setCodigoCampanhaRPC(codigoMotivoCampanhaRPC);
    dadosNegociacao.setIndicadorExcecaoCampanha(BooleanUtils.parseBoolean(indicadorExcecaoCampanha));
    dadosNegociacao.setTipoNegociacao(TipoNegociacao.getByTipo(tipoNegociacao));
    dadosNegociacao.setValorHonorarioMaximoCampanha(BigDecimalUtils.parseBigDecimal(valorHonorarioMaximoCampanha));
    dadosNegociacao.setDescontoHonorarios(BigDecimalUtils.parseBigDecimal(descontoHonorarios));
    dadosNegociacao.setQuantidadeParcelasAcordo(Integer.parseInt(quantidadeTotalParcelasAcordo));

    return dadosNegociacao;
  }

  @NotNull
  private DadosContrato buildDadosContrato(String[] informacoesAcordo) {
    final String numeroContratoFinanciado = informacoesAcordo[2];
    final String parcelasNegociadasInicial = informacoesAcordo[3];
    final String parcelaseNegociadasFinal = informacoesAcordo[4];
    final String dataVencimentoParcelaMaiorAtraso = informacoesAcordo[5];
    final String valorPrincipalParcelasContrato = informacoesAcordo[6];
    final String indicadorContratoAjuizado = informacoesAcordo[7];
    final String valorSaldoGCAContrato = informacoesAcordo[8];

    final DadosContrato dadosContrato = new DadosContrato();

    dadosContrato.setNumeroContrato(numeroContratoFinanciado);
    dadosContrato.setParcelaInicial(Integer.parseInt(parcelasNegociadasInicial));
    dadosContrato.setParcelaFinal(Integer.parseInt(parcelaseNegociadasFinal));
    dadosContrato.setDataVencimentoParcelaMaiorAtraso(DateUtils.parseDate(dataVencimentoParcelaMaiorAtraso));
    dadosContrato.setValorTotalParcelasNegociadas(BigDecimalUtils.parseBigDecimal(valorPrincipalParcelasContrato));
    dadosContrato.setContratoAjuizado(BooleanUtils.parseBoolean(indicadorContratoAjuizado));
    dadosContrato.setValorGCAContrato(BigDecimalUtils.parseBigDecimal(valorSaldoGCAContrato));

    return dadosContrato;
  }
}
