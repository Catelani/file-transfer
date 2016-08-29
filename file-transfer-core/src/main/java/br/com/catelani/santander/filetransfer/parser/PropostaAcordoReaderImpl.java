package br.com.catelani.santander.filetransfer.parser;

import br.com.catelani.santander.filetransfer.domain.CabecalhoPropostaAcordo;
import br.com.catelani.santander.filetransfer.domain.DetalhePropostaAcordo;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import com.univocity.parsers.fixed.FixedWidthFields;
import com.univocity.parsers.fixed.FixedWidthParser;
import com.univocity.parsers.fixed.FixedWidthParserSettings;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static br.com.catelani.santander.filetransfer.util.StringUtils.isNullOrEmpty;

/**
 * Le os arquivos de Proposta de Acordo utilizando UnivocityParser.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
class PropostaAcordoReaderImpl implements PropostaAcordoReader {

  private static final char[] WINDOWS_LINE_SEPARATOR = {'\r', '\n'};
  private DetalhePropostaFactory detalhePropostaFactory;
  private CabecalhoPropostaAcordoFactory cabecalhoPropostaAcordoFactory;

  @Inject
  public PropostaAcordoReaderImpl(DetalhePropostaFactory detalhePropostaFactory, CabecalhoPropostaAcordoFactory cabecalhoPropostaAcordoFactory) {
    this.detalhePropostaFactory = detalhePropostaFactory;
    this.cabecalhoPropostaAcordoFactory = cabecalhoPropostaAcordoFactory;
  }

  @Override
  public List<PropostaAcordo> parseAll(@NotNull InputStream is) throws IOException {
    return parseAll(is, StandardCharsets.UTF_8);
  }

  @Override
  public List<PropostaAcordo> parseAll(@NotNull InputStream is, @Nullable Charset charset) throws IOException {
    Objects.requireNonNull(is, "A Stream de entrada não pode ser nula!");

    Charset charsetAtual = charset != null ? charset : StandardCharsets.UTF_8;

    final List<PropostaAcordo> propostas = new ArrayList<>();

    try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, charsetAtual))) {
      String linha;

      CabecalhoPropostaAcordo cabecalhoPropostaAcordo = null;
      List<DetalhePropostaAcordo> detalhePropostaAcordos = new ArrayList<>();

      while ((linha = bufferedReader.readLine()) != null) {
        if (isCabecalho(linha)) {
          // se for um cabeçalho, e ja tiver um, é por que está mudando de proposta de acordo, ai finalizo a proposta e reinicializo os campos
          if (cabecalhoPropostaAcordo != null) {
            propostas.add(new PropostaAcordo(cabecalhoPropostaAcordo, detalhePropostaAcordos));

            // nova lista para os detalhes da proxima Proposta Acordo
            detalhePropostaAcordos = new ArrayList<>();
          }

          // leio o cabeçalho
          cabecalhoPropostaAcordo = lerCabecalhoPropostaAcordo(linha);
        } else {
          // detalhes da proposta anterior
          detalhePropostaAcordos.addAll(lerDetalhesAcordo(new StringReader(linha)));
        }
      }

      // a ultima proposta
      propostas.add(new PropostaAcordo(cabecalhoPropostaAcordo, detalhePropostaAcordos));
    }

    return propostas;
  }

  @Contract(value = "null -> false;", pure = true)
  private boolean isCabecalho(@Nullable String linha) {
    if (isNullOrEmpty(linha) || linha.length() < 396)
      return false;

    return "00001".equals(linha.substring(395));
  }

  @Override
  public PropostaAcordo parse(@NotNull InputStream is) throws IOException {
    return parse(is, null);
  }

  @Override
  public PropostaAcordo parse(@NotNull InputStream is, Charset charset) throws IOException {
    Objects.requireNonNull(is, "A Stream de entrada não pode ser nula!");

    Charset charsetAtual = charset != null ? charset : StandardCharsets.UTF_8;

    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, charsetAtual), 1200)) {
      final String cabecalho = bufferedReader.readLine();

      if (isNullOrEmpty(cabecalho)) {
        throw new IllegalStateException("Não há nada na stream para ser lido!");
      }

      final CabecalhoPropostaAcordo cabecalhoPropostaAcordo = lerCabecalhoPropostaAcordo(cabecalho);
      final List<DetalhePropostaAcordo> detalhes = lerDetalhesAcordo(bufferedReader);

      return new PropostaAcordo(cabecalhoPropostaAcordo, detalhes);
    }
  }

  @Contract("null -> fail;")
  private List<DetalhePropostaAcordo> lerDetalhesAcordo(@NotNull Reader in) {
    Objects.requireNonNull(in, "A Stream de leitura não pode ser nula!");

    // Esses campos são fixos e fornecidos pelo Banco Santander
    final FixedWidthFields detailLengths = new FixedWidthFields(1, 2, 11, 3, 3, 8, 15, 1, 15, 15, 15, 15, 5, 1, 1, 15, 15, 3, 3, 8, 4, 15, 15, 15, 73, 3, 30, 11, 54, 20, 5);
    final FixedWidthParserSettings settings = new FixedWidthParserSettings(detailLengths);
    settings.getFormat().setLineSeparator(WINDOWS_LINE_SEPARATOR);
    final FixedWidthParser fixedWidthParser = new FixedWidthParser(settings);

    try {
      fixedWidthParser.beginParsing(in);

      String[] info;

      List<DetalhePropostaAcordo> detalhePropostaAcordos = new ArrayList<>();

      while ((info = fixedWidthParser.parseNext()) != null) {
        detalhePropostaAcordos.add(detalhePropostaFactory.buildDetalheProposta(info));
      }

      return detalhePropostaAcordos;
    } finally {
      fixedWidthParser.stopParsing();
    }
  }

  /**
   * Le as informações do Cabeçalho da Proposta a partir de uma {@link String} representando a primeira linha do arquivo.
   *
   * @param cabecalho Primeira linha do arquivo
   * @return {@link CabecalhoPropostaAcordo}
   * @throws NullPointerException Se o {@code cabecalho} for {@code null}
   */
  private CabecalhoPropostaAcordo lerCabecalhoPropostaAcordo(@NotNull String cabecalho) {
    Objects.requireNonNull(cabecalho, "O cabecalho não pode ser nulo!");

    // esses campos são fixos e fornecidos pelo Banco Santander
    final FixedWidthFields headerLengths = new FixedWidthFields(1, 2, 4, 7, 40, 8, 8, 7, 3, 5, 175, 8, 8, 1, 3, 30, 85, 5);
    final FixedWidthParserSettings settings = new FixedWidthParserSettings(headerLengths);
    final FixedWidthParser fixedWidthParser = new FixedWidthParser(settings);

    try {
      fixedWidthParser.beginParsing(new StringReader(cabecalho));

      return cabecalhoPropostaAcordoFactory.buildCabecalhoPropostaAcordo(fixedWidthParser.parseNext());
    } finally {
      fixedWidthParser.stopParsing();
    }
  }
}
