package br.com.catelani.santander.filetransfer.util;

import org.jetbrains.annotations.Contract;

import java.util.Objects;

/**
 * Classe utilitaria para facilitar interação com {@link Boolean}.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public class BooleanUtils {

  private final static String[] BOOLEAN_TRUE = new String[]{"S", "s"};
  private final static String[] BOOLEAN_FALSE = new String[]{"N", "n"};

  private BooleanUtils() {}

  /**
   * <p>Parseia uma String representando um boolean dentro das regras do Banco.</p>
   * <p>Se não for nenhuma das opções será considerado false.</p>
   * <p>Se for nulo ou vazio, também será considerado false.</p>
   *
   * @param bool String representando o boolean.
   * @return Um {@link Boolean}
   */
  @Contract(value = "null -> false", pure = true)
  public static boolean parseBoolean(String bool) {
    if (StringUtils.isNullOrEmpty(bool))
      return false;

    for (String booleanValue : BOOLEAN_TRUE) {
      if (Objects.equals(bool, booleanValue)) {
        return true;
      }
    }

    for (String booleanValue : BOOLEAN_FALSE) {
      if (Objects.equals(bool, booleanValue)) {
        return false;
      }
    }

    return false;
  }

  /**
   * Formata um {@link Boolean} no formato do banco.
   *
   * @param b Boolean a ser formatado
   * @return Uma {@link String} representando o boolean de acordo com as regras do banco.
   */
  @Contract(pure = true)
  public static String formatBoolean(boolean b) {
    return b ? "S" : "N";
  }
}
