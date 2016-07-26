package br.com.catelani.santander.filetransfer.parser;

import br.com.catelani.santander.filetransfer.domain.*;
import br.com.catelani.santander.filetransfer.util.DateUtils;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Kennedy Oliveira
 */
class CabecalhoPropostaAcordoFactoryImpl implements CabecalhoPropostaAcordoFactory {

  private ListaErroFactory listaErroFactory;

  @Inject
  public CabecalhoPropostaAcordoFactoryImpl(ListaErroFactory listaErroFactory) {
    this.listaErroFactory = listaErroFactory;
  }

  @Override
  public CabecalhoPropostaAcordo buildCabecalhoPropostaAcordo(String[] strings) {
    // inicio do processamento
    final DadosPrestador dadosPrestador = buildDadosPrestador(strings);
    final RetornoFinanceiraPA retornoFinanceira = buildRetornoFinanceira(strings);

    final String tipoRegistro = strings[0];

    final CabecalhoPropostaAcordo cabecalhoPropostaAcordo = new CabecalhoPropostaAcordo();
    cabecalhoPropostaAcordo.setTipoRegistro(TipoRegistro.getByTipoRegistro(tipoRegistro));
    cabecalhoPropostaAcordo.setDadosPrestador(dadosPrestador);
    cabecalhoPropostaAcordo.setRetornoFinanceira(retornoFinanceira);

    return cabecalhoPropostaAcordo;
  }

  @NotNull
  private RetornoFinanceiraPA buildRetornoFinanceira(String[] strings) {
    final String dataRetornoFinanceira = strings[11];
    final String horaRetornoFinanceira = strings[12];
    final String tipoRetornoFinanceira = strings[13];
    final String codigoRetornoValidacaoFinanceira = strings[14];

    final Optional<String> listaCodigoDeErros = Optional.ofNullable(strings[15]);
    // final String numeroSequencia = strings[17]; // É sempre 00001

    final RetornoFinanceiraPA retornoFinanceira = new RetornoFinanceiraPA();

    if (dataRetornoFinanceira != null)
      retornoFinanceira.setDataRetorno(DateUtils.parseDate(dataRetornoFinanceira));

    if (horaRetornoFinanceira != null)
      retornoFinanceira.setHoraRetorno(DateUtils.parseTime(horaRetornoFinanceira));

    if (tipoRetornoFinanceira != null)
      retornoFinanceira.setTipoRetornoFinanceira(TipoRetornoFinanceira.getPorCodigo(tipoRetornoFinanceira));

    if (codigoRetornoValidacaoFinanceira != null)
      retornoFinanceira.setCodigoRetornoValidacao(CodigoRetornoValidacao.getByCodigo(codigoRetornoValidacaoFinanceira));

    final List<CodigoRetornoErro> listaDeErros = listaCodigoDeErros.map(listaErroFactory::parseErros).orElse(Collections.emptyList());
    retornoFinanceira.setErros(listaDeErros);

    return retornoFinanceira;
  }

  @NotNull
  private DadosPrestador buildDadosPrestador(String[] strings) {
    final String caixaPostal = strings[2];
    final String codigoGCA = strings[3];
    final String nomePrestador = strings[4];
    final String dataRemessa = strings[5];
    final String horaRemessa = strings[6];
    final String numerLote = strings[7];
    final String codigoTipoPrestacaoServico = strings[8];
    final String quantidadeSolicitacoes = strings[9];

    final DadosPrestador dadosPrestador = new DadosPrestador();
    dadosPrestador.setCaixaPostal(caixaPostal);
    dadosPrestador.setCodigoGCA(codigoGCA);
    dadosPrestador.setNome(nomePrestador);
    dadosPrestador.setDataRemessa(DateUtils.parseDate(dataRemessa));
    dadosPrestador.setHoraRemessa(DateUtils.parseTime(horaRemessa));
    dadosPrestador.setNumeroLote(Long.parseLong(numerLote));
    dadosPrestador.setTipoPrestacaoServico(TipoPrestacaoServico.getByCodigo(codigoTipoPrestacaoServico));
    dadosPrestador.setQuantidadeSolicitacoes(Long.parseLong(quantidadeSolicitacoes));

    return dadosPrestador;
  }
}
