package br.com.catelani.santander.filetransfer.util;

import br.com.catelani.santander.filetransfer.domain.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static br.com.catelani.santander.filetransfer.util.StringUtils.isNullOrEmpty;

/**
 * Métodos utilitários para manipulação dos nomes dos arquivos e suas regras segundo o banco.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public class NomeUtils {

  private static final Pattern REGEX_NOME_ARQUIVO_RETORNO_BANCO = Pattern.compile("ESCR([NEB])(\\d{4})[.]DAT");
  private static final Pattern REGEX_TIPO_INTERFACE_NOME_ARQUIVO = Pattern.compile("ESC(P[AR]|R[NEB])\\d{4}[.]DAT");

  private NomeUtils() {}

  /**
   * Gera um nome de arquio de acordo com as informações da {@code proposta} seguindo as regras estipuladas pelo banco.
   *
   * @param proposta Proposta com as informações para gerar o nome do arquivo.
   * @return O nome do arquio de acordo com as regras do banco.
   * @throws NullPointerException  se a {@code proposta}, o {@link PropostaAcordo#getCabecalho()} ou {@link CabecalhoPropostaAcordo#getDadosPrestador()} forem nulos.
   * @throws IllegalStateException se o {@link DadosPrestador#getCaixaPostal()} for nulo ou vazio ou não seguir as regras do banco, que deve ser 4 digitos diferentes de 0.
   */
  @NotNull
  @Contract(value = "null -> fail;", pure = true)
  public static String getNomeArquivoPropostaAcordo(@NotNull PropostaAcordo proposta) {
    Objects.requireNonNull(proposta, "A proposta não pode ser nula!");
    Objects.requireNonNull(proposta.getCabecalho(), "O Cabeçalho da proposta não pode ser nulo!");
    Objects.requireNonNull(proposta.getCabecalho().getDadosPrestador(), "Os dados do prestador não podem ser nulos!");

    final String caixaPostal = proposta.getCabecalho().getDadosPrestador().getCaixaPostal();
    final TipoInterface tipoInterface = proposta.getCabecalho().getTipoInterface();

    return getNomeArquivoPropostaAcordo(tipoInterface, caixaPostal);
  }

  /**
   * Gera um nome de arquio de acordo com as informações de {@code tipoInterface}  e {@code caixaPostal} do prestador, seguindo as regras estipuladas pelo banco.
   *
   * @return O nome do arquio de acordo com as regras do banco.
   * @throws NullPointerException  se o {@code tipoInterface} for nulo.
   * @throws IllegalStateException se o {@code caixaPostal} for nulo ou vazio ou não seguir as regras do banco, que deve ser 4 digitos diferentes de 0.
   */
  @Contract("null, _ -> fail; _, null -> fail;")
  public static String getNomeArquivoPropostaAcordo(@NotNull TipoInterface tipoInterface, @NotNull String caixaPostal) {
    Objects.requireNonNull(tipoInterface, "O tipo de interface da proposta está nulo!");

    if (isNullOrEmpty(caixaPostal))
      throw new IllegalStateException("A proposta está sem a Caixa Postal!");

    if (!caixaPostal.matches("\\d{4}") || Long.parseLong(caixaPostal) <= 0)
      throw new IllegalStateException("O código de caixa posta [" + caixaPostal + "]não está nos padrões do banco, ele deve ser 4 digitos númericos diferentes de 0.");

    return String.format("ESC%s%s.DAT", tipoInterface, caixaPostal);
  }

  /**
   * <p>Descobre o {@link TipoRetornoFinanceira} baseado no nome do arquivo.</p>
   * <p>O nome do arquivo deve seguir as regras do banco, deve ser no formato {@code ESCR[NEB]\d\d\d\d.DAT} se não uma {@link IllegalArgumentException} será lançada.</p>
   * <p>Esse método só se aplica quando o {@link TipoInterface} for {@link TipoInterface#RETORNO_FINANCEIRA}, o método {@link #getTipoInterfacePeloNomeArquivo(String)} pode ser
   * utilizado para descobrir qual é a interface, caso não seja será lançada uma {@link IllegalArgumentException}</p>
   *
   * @param nomeArquivo Nome do arquivo para validar.
   * @return {@link TipoRetornoFinanceira} relacionado aquele nome de arquivo.
   * @throws NullPointerException     se o nome do arquivo for nulo ou vazio.
   * @throws IllegalArgumentException se o nome do arquivo não estiver no formato {@code ESCR[NEB]\d\d\d\d.DAT}.
   */
  @NotNull
  @Contract(value = "null -> fail;", pure = true)
  public static TipoRetornoFinanceira getTipoRetornoFinanceiraPeloNomeArquivo(@NotNull String nomeArquivo) {
    if (isNullOrEmpty(nomeArquivo))
      throw new NullPointerException("O nome do arquivo não pode ser nulo ou vazio");

    final Matcher matcher = REGEX_NOME_ARQUIVO_RETORNO_BANCO.matcher(nomeArquivo);
    if (!matcher.find()) {
      throw new IllegalArgumentException("O nome informado não é um nome valido para um arquivo do banco!");
    }

    final String codigoTipoRetorno = matcher.group(1);
    final TipoRetornoFinanceira tipoRetorno = TipoRetornoFinanceira.getPorCodigo(codigoTipoRetorno);

    if (tipoRetorno == null)
      throw new IllegalArgumentException("Tipo de Retorno desconhecido para o código: " + codigoTipoRetorno);

    return tipoRetorno;
  }

  /**
   * <p>Descobre o {@link TipoInterface} para o nome de um arquivo do banco.</p>
   * <p>O nome do arquivo deve seguir as regras do banco, deve ser no formato {@code ESC(P[AR]|R[NEB])\d{4}[.]DAT} se não
   * uma {@link IllegalArgumentException} será lançada.</p>
   *
   * @param nomeArquivo nome do arquivo para extrair as informações.
   * @return {@link TipoInterface} baseado no nome do arquivo.
   */
  @NotNull
  @Contract(value = "null -> fail;", pure = true)
  public static TipoInterface getTipoInterfacePeloNomeArquivo(@NotNull String nomeArquivo) {
    if (isNullOrEmpty(nomeArquivo))
      throw new NullPointerException("O nome do arquivo não pode ser nulo ou vazio");

    final Matcher matcher = REGEX_TIPO_INTERFACE_NOME_ARQUIVO.matcher(nomeArquivo);
    if (!matcher.find()) {
      throw new IllegalArgumentException("O nome informado não é um nome valido para um arquivo do banco");
    }

    final String codigoTipoInterface = matcher.group(1);
    TipoInterface tipoInterface = TipoInterface.getPorCodigoTipoInterface(codigoTipoInterface);

    if (tipoInterface == null)
      throw new IllegalArgumentException("Código de Interface [" + codigoTipoInterface + "] não reconhecido!");

    return tipoInterface;
  }
}
