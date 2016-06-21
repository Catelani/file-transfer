package br.com.catelani.santander.filetransfer.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.Objects;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Métodos utilitarios para trabalhar com {@link BigDecimal} e as informações que vem do banco.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public class BigDecimalUtils {

  private BigDecimalUtils() {}

  /**
   * <p>Transforma um valor em um {@link BigDecimal} de acordo com as regras do banco.</p>
   * <p>O valor deve ter 15 digitos, onde os 13 primeiros são a parte inteira e os 2 ultimos a parte decimal.</p>
   *
   * @param valor Valor a ser convertido.
   * @return Se o valor for nulo ou tiver tamanho diferente de 15 retorna {@link BigDecimal#ZERO}, do contrario devolve o valor.
   */
  // TODO Arrumar um nome melhor pra isso
  @Contract(pure = true)
  public static BigDecimal parseBigDecimal(@Nullable String valor) {
    if (isNullOrEmpty(valor))
      return BigDecimal.ZERO;

    if (valor.length() != 15)
      throw new IllegalArgumentException("O valor deve ter 15 digitos, sendo os 13 primeiros os digitos significativos e os outros 2 os decimais.");

    final String parteInteira = valor.substring(0, 13);
    final String parteDecimal = valor.substring(13, valor.length());

    return new BigDecimal(String.format("%s.%s", parteInteira, parteDecimal));
  }

  /**
   * Formata um {@link BigDecimal} para uma {@link String} de acordo com as regras do banco para o File Transfer.
   *
   * @param bigDecimal {@link BigDecimal} para formatar
   * @return Uma {@link String} com o valor formatado pronto para o File Transfer.
   */
  @Contract(pure = true)
  public static String formatBigDecimal(@Nullable BigDecimal bigDecimal) {
    if (bigDecimal == null || Objects.equals(bigDecimal, BigDecimal.ZERO))
      return "0";

    final String valor = bigDecimal.setScale(2, BigDecimal.ROUND_CEILING).toString();

    return valor.replaceAll("[.,]", "");
  }
}
