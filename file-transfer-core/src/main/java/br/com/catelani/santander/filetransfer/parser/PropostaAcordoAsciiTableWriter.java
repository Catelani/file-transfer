package br.com.catelani.santander.filetransfer.parser;

import br.com.catelani.santander.filetransfer.domain.*;
import br.com.catelani.santander.filetransfer.qualifiers.AsciiTable;
import de.vandermeer.asciitable.v2.RenderedTable;
import de.vandermeer.asciitable.v2.V2_AsciiTable;
import de.vandermeer.asciitable.v2.render.V2_AsciiTableRenderer;
import de.vandermeer.asciitable.v2.render.WidthLongestWordMinCol;
import de.vandermeer.asciitable.v2.themes.V2_E_TableThemes;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>Escreve a Proposta de Acordo como uma Ascii Table.</p>
 * <p>Apenas para facilitar a leitura de humanos, pois o arquivo normal não da pra ler por conta de ser delimitado por tamanho.</p>
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
@AsciiTable
class PropostaAcordoAsciiTableWriter implements PropostaAcordoWriter {

  private static final byte[] QUEBRA_LINHA = "\n".getBytes(StandardCharsets.UTF_8);
  private static final String CAMPO_EM_BRANCO = "";

  @Override
  public void gerarPropostaAcordo(@NotNull PropostaAcordo propostaAcordo, @NotNull OutputStream os) throws IOException {
    log.debug("Iniciando geração da Ascii Table da proposta de acordo");
    log.trace("Proposta de Acordo: {}", propostaAcordo);

    Objects.requireNonNull(propostaAcordo, "A proposta de acordo não pode ser nula!");
    Objects.requireNonNull(os, "A Stream de saida não pode ser nula!");

    try {
      writeCabecalho(propostaAcordo, os);

      // duas quebras de linha pra dar espaço entre o cabeçalho e os detalhes
      os.write(QUEBRA_LINHA);
      os.write(QUEBRA_LINHA);

      writeDetalhes(propostaAcordo, os);
    } catch (IOException e) {
      log.error("Erro gerando Ascii Table da Proposta de Acordo", e);
      throw e;
    }
  }

  private V2_AsciiTableRenderer getTableRenderer() {
    V2_AsciiTableRenderer tableRenderer = new V2_AsciiTableRenderer();
    tableRenderer.setWidth(new WidthLongestWordMinCol(20));
    tableRenderer.setTheme(V2_E_TableThemes.PLAIN_7BIT.get());

    return tableRenderer;
  }

  private void writeCabecalho(PropostaAcordo propostaAcordo, OutputStream outputStream) throws IOException {
    log.debug("Escrevendo o cabeçalho");
    final V2_AsciiTable table = new V2_AsciiTable();

    // cebeçalho geral
    table.addRule();
    table.addRow(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "Cabeçalho")
         .setAlignment(new char[]{'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c'});
    table.addRule();

    // cabeçalho
    table.addRow(
      // Info dos Tipos
      "Tipo de Registro",
      "Tipo de Interface",
      // Dados do Prestador
      "Num. Caixa Postal",
      "Codigo GCA",
      "Nome do Prestador",
      "Data da Remessa",
      "Hora da Remessa",
      "Numero do Lote",
      "Tipo de Prestação de Serviço",
      "Qtd. Solicitações",
      // Retorno do Banco
      "Data do Retorno",
      "Hora do Retorno",
      "Tipo de Retorno",
      "Status",
      "Codigos de Erro",
      // Sequencia
      "Num Sequencia"
                );
    table.addRule();

    final CabecalhoPropostaAcordo cabecalho = propostaAcordo.getCabecalho();
    final DadosPrestador prestador = cabecalho.getDadosPrestador();
    final RetornoFinanceiraPA retornoFinanceira = cabecalho.getRetornoFinanceira();

    table.addRow(
      // info tipo
      String.format("%s (%s)", cabecalho.getTipoRegistro(), cabecalho.getTipoRegistro().name()),
      String.format("%s (%s)", cabecalho.getTipoInterface(), cabecalho.getTipoInterface().name()),
      // Dados do Prestador
      prestador.getCaixaPostal(),
      prestador.getCodigoGCA(),
      prestador.getNome(),
      prestador.getDataRemessa(),
      prestador.getHoraRemessa(),
      prestador.getNumeroLote(),
      prestador.getTipoPrestacaoServico(),
      prestador.getQuantidadeSolicitacoes(),
      // Retornos do Banco
      retornoFinanceira.getDataRetorno() != null ? retornoFinanceira.getDataRetorno().toString() : CAMPO_EM_BRANCO,
      retornoFinanceira.getHoraRetorno() != null ? retornoFinanceira.getHoraRetorno().toString() : CAMPO_EM_BRANCO,
      retornoFinanceira.getTipoRetornoFinanceira() != null ? retornoFinanceira.getTipoRetornoFinanceira().name() : CAMPO_EM_BRANCO,
      retornoFinanceira.getCodigoRetornoValidacao() != null ? retornoFinanceira.getTipoRetornoFinanceira().name() : CAMPO_EM_BRANCO,
      retornoFinanceira.getErros().stream().map(CodigoRetornoErro::name).collect(Collectors.joining(",")),
      // Sequencia
      cabecalho.getNumeroDeSequencia());
    table.addRule();

    final RenderedTable renderedTable = getTableRenderer().render(table);

    outputStream.write(renderedTable.toString().getBytes(StandardCharsets.UTF_8));
  }

  private void writeDetalhes(PropostaAcordo propostaAcordo, OutputStream outputStream) throws IOException {
    log.debug("Escrevendo os detalhes");
    if (propostaAcordo.getDetalhes() == null || propostaAcordo.getDetalhes().isEmpty()) {
      log.debug("Não há detalhes de propostas...");
      return;
    }

    // Configuração de Alinhamento Centralizado para o cabeçalho geral
    final char[] alinhamentoCentral = {'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c', 'c'};

    final V2_AsciiTable table = new V2_AsciiTable();
    table.addRule();
    table.addRow(null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 "Detalhes").setAlignment(alinhamentoCentral);
    table.addRule();
    table.addRow(getCabecalhoGeral()).setAlignment(alinhamentoCentral);
    table.addRule();
    table.addRow(getCabecalho());
    table.addRule();

    for (DetalhePropostaAcordo detalhePropostaAcordo : propostaAcordo.getDetalhes()) {
      final DadosContrato dadosContrato = detalhePropostaAcordo.getDadosContrato();
      final DadosNegociacao negociacao = detalhePropostaAcordo.getDadosNegociacao();
      final ParcelaAcordo parcela = detalhePropostaAcordo.getParcelaAcordo();
      final RetornoFinanceiraDetalhe retorno = detalhePropostaAcordo.getRetornoFinanceiraDetalhe();

      table.addRow(
        // Informação Basica
        detalhePropostaAcordo.getTipoRegistro(),
        String.format("%s (%s)", detalhePropostaAcordo.getTipoCarteira(), detalhePropostaAcordo.getTipoCarteira().name()),
        // Dados do Contrato
        dadosContrato.getNumeroContrato(),
        dadosContrato.getParcelaInicial(),
        dadosContrato.getParcelaFinal(),
        dadosContrato.getDataVencimentoParcelaMaiorAtraso(),
        dadosContrato.getValorTotalParcelasNegociadas(),
        dadosContrato.isContratoAjuizado() ? "Sim" : "Não",
        dadosContrato.getValorGCAContrato(),
        // Dados da negociação
        negociacao.getValorSaldoGCADispensado(),
        negociacao.getValorTotalAcordo(),
        negociacao.getValorTotalAcordoSemHonorarios(),
        negociacao.getCodigoCampanhaRPC(),
        negociacao.isIndicadorExcecaoCampanha() ? "Sim" : "Não",
        negociacao.getTipoNegociacao(),
        negociacao.getValorHonorarioMaximoCampanha(),
        negociacao.getDescontoHonorarios(),
        negociacao.getQuantidadeParcelasAcordo(),
        // Dados da parcela
        parcela.getSequencia(),
        parcela.getDataVencimento(),
        parcela.getQuantidadeDiasAtraso(),
        parcela.getValorTotal(),
        parcela.getValorGca(),
        parcela.getValorHonorario(),
        // retorno do banco
        retorno.getCodigoRetornoValidacao() != null ? String.format("%s (%s)", retorno.getCodigoRetornoValidacao(), retorno.getCodigoRetornoValidacao().name()) : CAMPO_EM_BRANCO,
        !retorno.getCodigoRetornoErros().isEmpty() ? retorno.getCodigoRetornoErros().stream().map(CodigoRetornoErro::toString).collect(Collectors.joining(",")) : CAMPO_EM_BRANCO,
        retorno.getNumeroAcordoRPCGerado() != null ? retorno.getNumeroAcordoRPCGerado() : CAMPO_EM_BRANCO,
        retorno.getLinhaDigitavelBoletoGerado() != null ? retorno.getLinhaDigitavelBoletoGerado() : CAMPO_EM_BRANCO,
        // sequencia
        String.format("%05d", detalhePropostaAcordo.getSequencia())
                  );
      table.addRule();
    }

    final RenderedTable renderedTable = getTableRenderer().render(table);
    outputStream.write(renderedTable.toString().getBytes(StandardCharsets.UTF_8));
  }

  private Object[] getCabecalhoGeral() {
    return new Object[]{
      null,
      "Informações do File Transfer",
      null,
      null,
      null,
      null,
      null,
      null,
      "Dados do Contrato",
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      "Dados da Negociação",
      null,
      null,
      null,
      null,
      null,
      null,
      "Parcela do Acordo",
      null,
      null,
      null,
      "Retorno do Banco",
      "Num. Sequencia"};
  }

  private Object[] getCabecalho() {
    return new Object[]{
      // informação Basica
      "Tipo de Registro",
      "Tipo de Carteira",
      // Dados do contrato
      "Num. Contrato",
      "Parcelas Inicial",
      "Parcelas Final",
      "Data Vencimento Parc Maior Atraso",
      "Valor Parcelas",
      "Contrato Ajuizado?",
      "GCA Contrato",
      // Dados da Negociação
      "GCA Dispensado",
      "Valor Acordo Cliente",
      "Valor Acordo sem Honorários",
      "Código RPC",
      "Exceção",
      "Tipo Negociação",
      "Val. Máximo Honorário",
      "Desconto Honorário",
      "Total de Parcelas Acordo",
      // Parcela do Acordo
      "Seq. Parcela Acordo",
      "Data Vencimento",
      "Qtd. Dias Atraso",
      "Valor Total",
      "Valor GCA",
      "Valor Honorário",
      // Retorno do Banco
      "Cod Retorno Validação",
      "Códigos de Erro",
      "Acordo RPC",
      "Linha Digitavel",
      // Sequencia
      "Num. Sequencia"
    };
  }
}
