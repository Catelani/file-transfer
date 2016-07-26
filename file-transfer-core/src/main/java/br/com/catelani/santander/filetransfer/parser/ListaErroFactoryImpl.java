package br.com.catelani.santander.filetransfer.parser;

import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

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
    if (StringUtils.isNullOrEmpty(listaErros))
      throw new NullPointerException("Lista de erros vazia");

    // não tenho certeza se o banco respeita a propria regra, tem umas que sim, outras que não ¬¬
//    if (listaErros.length() != 30)
//      throw new IllegalArgumentException("Lista de erros com tamanho {" + listaErros.length() + "} deveria ser 30");

    final String[] errosTmp = listaErros.split(".{3}");

    return Arrays.stream(errosTmp)
                 .filter("000"::equals)
                 .map(this::getByCodigo)
                 .filter(Objects::nonNull) // filtro os nulos, pois podem ter código de erros ainda não cadastrados
                 .collect(toList());
  }

  private CodigoRetornoErro getByCodigo(String codigo) {
    final CodigoRetornoErro erro = CodigoRetornoErro.getByCodigoErro(codigo);

    if (erro == null)
      log.warn("Não encontrado Erro para o Código: [{}]", codigo);

    return erro;
  }
}
