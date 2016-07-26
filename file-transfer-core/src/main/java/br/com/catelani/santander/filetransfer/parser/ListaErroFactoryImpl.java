package br.com.catelani.santander.filetransfer.parser;

import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static br.com.catelani.santander.filetransfer.util.StringUtils.isNullOrEmpty;

/**
 * Factory para ler e identificar os erros retornados pelo banco.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
class ListaErroFactoryImpl implements ListaErroFactory {

  @Override
  public List<CodigoRetornoErro> parseErros(String listaErros) {
    if (isNullOrEmpty(listaErros))
      throw new NullPointerException("Lista de erros vazia");

    if (listaErros.length() < 3)
      throw new IllegalArgumentException("A lista de erros deve ter pelomenos 3 caracteres");

    final int totalErros = listaErros.length() / 3;

    List<CodigoRetornoErro> codigoRetornoErros = new ArrayList<>(totalErros);

    for (int i = 0; i < totalErros; i++) {
      // não da pra cortar o erro, teoricamente não deveria cair aqui, por conta da divisão anterior, mas só por precaução
      if (listaErros.length() < totalErros * 3) break;

      final String codigoErro = listaErros.substring(i * 3, (i + 1) * 3);

      if (codigoDeErroValido(codigoErro)) {
        codigoRetornoErros.add(getByCodigo(codigoErro));
      }
    }

    return codigoRetornoErros;
  }

  /**
   * Tenta recuperar um {@link CodigoRetornoErro} a partir do um código.
   *
   * @param codigo Código para procurar.
   * @return Um {@link CodigoRetornoErro}
   * @throws IllegalArgumentException Se não encontrar um {@link CodigoRetornoErro}
   */
  private CodigoRetornoErro getByCodigo(String codigo) {
    final CodigoRetornoErro erro = CodigoRetornoErro.getByCodigoErro(codigo);

    if (erro == null) {
      log.warn("Não encontrado Erro para o Código: [{}]", codigo);
      throw new IllegalArgumentException("Código de erro [" + codigo + "] desconhecido.");
    }

    return erro;
  }

  /**
   * <p>Verifica se o código passado é valido.</p>
   * <p>O Banco envia como código em branco o valor {@code 000}, e caso seja esse o código, não é um erro válido.</p>
   *
   * @param codigoErro Código de erro para validar.
   * @return {@code true} caso sejá diferente de {@code 000}
   */
  private boolean codigoDeErroValido(String codigoErro) {
    return codigoErro != null && codigoErro.length() == 3 && !Objects.equals("000", codigoErro);
  }
}
