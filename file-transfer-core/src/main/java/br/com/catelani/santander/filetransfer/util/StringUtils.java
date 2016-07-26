package br.com.catelani.santander.filetransfer.util;

import org.jetbrains.annotations.Contract;

/**
 * Métodos utilitários para trabalhar com {@link String}.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public class StringUtils {

  private StringUtils() {}

  /**
   * Verifica se uma {@link String} é nula ou vazia.
   *
   * @param s String a ser verificada.
   * @return True caso for nula e vazia, False do contrario.
   */
  @Contract(pure = true)
  public static boolean isNullOrEmpty(String s) {
    return s == null || s.length() == 0;
  }

  /**
   * <p>Aplica um "leftpad" na string, fazendo com que ela fique com no minimo {@code tamanho} de tamanho.</p>
   * <p>Caso a String seja menor que o tamanho passado no parametro {@code tamanho}, os "espaços" restantes serão preenchidos com o caracteres especificado em {@code charToPad}.</p>
   *
   * @param s         String para aplicar o left pad.
   * @param charToPad Caracter para preencher caso precise.
   * @param tamanho   Tamanho desejado da String final.
   * @return A String "left padded".
   */
  @Contract(pure = true)
  public static String leftPad(String s, char charToPad, int tamanho) {
    if (s == null || s.length() >= tamanho)
      return s;

    final int padsLeft = tamanho - s.length();

    final StringBuilder sb = new StringBuilder();

    for (int i = 0; i < padsLeft; i++) {
      sb.append(charToPad);
    }

    sb.append(s);

    return sb.toString();
  }
}
