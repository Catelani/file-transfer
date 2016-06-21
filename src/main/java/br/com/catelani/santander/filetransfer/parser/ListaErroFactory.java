package br.com.catelani.santander.filetransfer.parser;

import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;

import java.util.Collections;
import java.util.List;

/**
 * @author Kennedy Oliveira
 */
public interface ListaErroFactory {
  /**
   * Le e transforma uma lista de erros do banco em uma {@link List} de {@link CodigoRetornoErro};
   *
   * @param listaErros Lista com os erros devolvidos pelo Banco.
   * @return {@link List} de {@link CodigoRetornoErro} com os erros caso ouver, ou {@link Collections#emptyList()} caso não tiver nenhum erro.
   * @throws NullPointerException     se {@code listaErros} for nula ou vazia.
   * @throws IllegalArgumentException se {@code listaErros} tiver um tamanho diferente de 30, pois o banco sempre manda 30 caracteres.
   */
  List<CodigoRetornoErro> parseErros(String listaErros);
}
