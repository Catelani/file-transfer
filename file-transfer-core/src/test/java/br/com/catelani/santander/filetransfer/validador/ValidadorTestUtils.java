package br.com.catelani.santander.filetransfer.validador;

import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;

/**
 * Métodos utilitários para teste dos {@link br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordo}
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public class ValidadorTestUtils {

  private ValidadorTestUtils() {}

  /**
   * @param erros          Iterable com os erros.
   * @param codigoEsperado Código esperado
   * @return {@link Boolean#TRUE} caso não tiver nenhum erro ou {@link Boolean#FALSE} se tiver o erro com o {@code codigoEsperado}.
   * @throws IllegalStateException caso tenha erros porém não tenha o com o {@code codigoEsperado}.
   */
  public static boolean isValid(Iterable<? extends ErroValidacao> erros, CodigoRetornoErro codigoEsperado) {
    // se não tiver erros, é valido
    if (erros == null)
      return true;

    // não tem nenhum erro
    if (codigoEsperado == null && !erros.iterator().hasNext())
      return true;

    // procuro nos erros se tem aquele erro esperado
    for (ErroValidacao erro : erros) {
      if (erro.getCodigoRetornoErro() == codigoEsperado)
        return false;
    }

    throw new IllegalStateException("Há erros porém não há o erro com código: " + codigoEsperado);
  }
}
