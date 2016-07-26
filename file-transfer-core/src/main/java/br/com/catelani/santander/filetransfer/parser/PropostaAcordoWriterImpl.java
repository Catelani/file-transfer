package br.com.catelani.santander.filetransfer.parser;

import br.com.catelani.santander.filetransfer.domain.*;
import br.com.catelani.santander.filetransfer.util.DateUtils;
import br.com.catelani.santander.filetransfer.util.StringUtils;
import com.univocity.parsers.fixed.FixedWidthFields;
import com.univocity.parsers.fixed.FixedWidthWriter;
import com.univocity.parsers.fixed.FixedWidthWriterSettings;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Function;

import static br.com.catelani.santander.filetransfer.util.BigDecimalUtils.formatBigDecimal;
import static br.com.catelani.santander.filetransfer.util.BooleanUtils.formatBoolean;
import static br.com.catelani.santander.filetransfer.util.StringUtils.leftPad;

/**
 * Implementação padrão da proposta de acordo, utilizando o UnivocityParsers para gerar o CSV.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
class PropostaAcordoWriterImpl implements PropostaAcordoWriter {

  private final static String CAMPO_VAZIO = "";

  // curried function pra facilitar a formatação de valores
  private final static Function<BigDecimal, String> formatBigDecimal = bigDecimal -> leftPad(formatBigDecimal(bigDecimal), '0', 15);

  /*
  * As informações dos tamanhos dos campos são fornecidas pelo banco no Manual do File Transfer.
  * */
  private static final int[] TAMANHOS_DOS_CAMPOS_CABECALHO = {1, 2, 4, 7, 40, 8, 8, 7, 3, 5, 175, 8, 8, 1, 3, 30, 85, 5};
  private static final int[] TAMANHO_DOS_CAMPOS_DE_DETALHE = {1, 2, 11, 3, 3, 8, 15, 1, 15, 15, 15, 15, 5, 1, 1, 15, 15, 3, 3, 8, 4, 15, 15, 15, 73, 3, 30, 11, 54, 20, 5};

  @Override
  public void gerarPropostaAcordo(@NotNull PropostaAcordo propostaAcordo, @NotNull OutputStream os) throws IOException {
    Objects.requireNonNull(propostaAcordo, "Proposta de acordo nula");
    Objects.requireNonNull(os, "Stream de saida nula");

    final OutputStream osNaoFechavel = new NonCloseableOutputStream(os);

    writeHeader(propostaAcordo, osNaoFechavel);
    writeDetails(propostaAcordo, osNaoFechavel);
  }

  /**
   * Escreve a linha de header na saida.
   *
   * @param propostaAcordo Proposta de acordo para pegar os dados
   * @param os             Stream de saida para escrever o arquivo.
   */
  private void writeHeader(PropostaAcordo propostaAcordo, OutputStream os) {
    final FixedWidthFields headerLengths = new FixedWidthFields(TAMANHOS_DOS_CAMPOS_CABECALHO);
    final FixedWidthWriterSettings settings = new FixedWidthWriterSettings(headerLengths);

    FixedWidthWriter csvHeaderWriter = new FixedWidthWriter(os, settings);

    try {
      final CabecalhoPropostaAcordo cabecalho = propostaAcordo.getCabecalho();
      final DadosPrestador dadosPrestador = cabecalho.getDadosPrestador();

      csvHeaderWriter.writeRow(new String[]{
          cabecalho.getTipoRegistro().toString(),
          cabecalho.getTipoInterface().toString(),
          dadosPrestador.getCaixaPostal(),
          dadosPrestador.getCodigoGCA(),
          dadosPrestador.getNome(),
          DateUtils.format(dadosPrestador.getDataRemessa()),
          DateUtils.format(dadosPrestador.getHoraRemessa()),
          String.format("%07d", dadosPrestador.getNumeroLote()), // left pad 0
          dadosPrestador.getTipoPrestacaoServico().toString(),
          String.format("%05d", dadosPrestador.getQuantidadeSolicitacoes()), // left pad 0
          CAMPO_VAZIO,
          CAMPO_VAZIO,
          CAMPO_VAZIO,
          CAMPO_VAZIO,
          CAMPO_VAZIO,
          CAMPO_VAZIO,
          CAMPO_VAZIO,
          cabecalho.getNumeroDeSequencia()
      });
    } finally {
      csvHeaderWriter.close();
    }
  }

  /**
   * Escreve os detalhes da proposta na stream de saida.
   *
   * @param propostaAcordo Proposta de acordo para pegar os dados
   * @param os             Stream de saida para escrever o arquivo.
   */
  private void writeDetails(PropostaAcordo propostaAcordo, OutputStream os) {
    final FixedWidthFields detailLengths = new FixedWidthFields(TAMANHO_DOS_CAMPOS_DE_DETALHE);
    FixedWidthWriter csvDetailWriter = null;

    try {
      csvDetailWriter = new FixedWidthWriter(os, new FixedWidthWriterSettings(detailLengths));

      for (DetalhePropostaAcordo detalhePropostaAcordo : propostaAcordo.getDetalhes()) {
        final DadosContrato dadosContrato = detalhePropostaAcordo.getDadosContrato();
        final DadosNegociacao dadosNegociacao = detalhePropostaAcordo.getDadosNegociacao();
        final ParcelaAcordo parcelaAcordo = detalhePropostaAcordo.getParcelaAcordo();

        // TODO Cachear a parte que é fixa por contrato, para evitar ficar criando muitos objetos
        csvDetailWriter.writeRow(new String[]{
            // informações do detalhe da proposta
            detalhePropostaAcordo.getTipoRegistro(),
            detalhePropostaAcordo.getTipoCarteira().toString(),
            // dados do contrato
            dadosContrato.getNumeroContrato(),
            String.format("%03d", dadosContrato.getParcelaInicial()),
            String.format("%03d", dadosContrato.getParcelaFinal()),
            DateUtils.format(dadosContrato.getDataVencimentoParcelaMaiorAtraso()),
            formatBigDecimal.apply(dadosContrato.getValorTotalParcelasNegociadas()),
            formatBoolean(dadosContrato.isContratoAjuizado()),
            formatBigDecimal.apply(dadosContrato.getValorGCAContrato()),
            // dados da negociação
            formatBigDecimal.apply(dadosNegociacao.getValorSaldoGCADispensado()),
            formatBigDecimal.apply(dadosNegociacao.getValorTotalAcordo()),
            formatBigDecimal.apply(dadosNegociacao.getValorTotalAcordoSemHonorarios()),
          StringUtils.leftPad(dadosNegociacao.getCodigoCampanhaRPC(), '0', 5),
            formatBoolean(dadosNegociacao.isIndicadorExcecaoCampanha()),
            dadosNegociacao.getTipoNegociacao().toString(),
            formatBigDecimal.apply(dadosNegociacao.getValorHonorarioMaximoCampanha()),
            formatBigDecimal.apply(dadosNegociacao.getDescontoHonorarios()),
            String.format("%03d", dadosNegociacao.getQuantidadeParcelasAcordo()),
            // parcela do acordo
            String.format("%03d", parcelaAcordo.getSequencia()),
            DateUtils.format(parcelaAcordo.getDataVencimento()),
            String.format("%04d", parcelaAcordo.getQuantidadeDiasAtraso()),
            formatBigDecimal.apply(parcelaAcordo.getValorTotal()),
            formatBigDecimal.apply(parcelaAcordo.getValorGca()),
            formatBigDecimal.apply(parcelaAcordo.getValorHonorario()),
            // campo em branco
            CAMPO_VAZIO,
            // retorno da financeira
            CAMPO_VAZIO,
            CAMPO_VAZIO,
            CAMPO_VAZIO,
            CAMPO_VAZIO,
            // campoz em branco
            CAMPO_VAZIO,
            // sequencia
            String.format("%05d", detalhePropostaAcordo.getSequencia())
        });
      }
    } finally {
      if (csvDetailWriter != null) {
        csvDetailWriter.close();
      }
    }
  }
}
