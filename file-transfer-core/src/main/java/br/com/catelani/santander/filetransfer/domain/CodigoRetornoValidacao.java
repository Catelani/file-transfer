package br.com.catelani.santander.filetransfer.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Códigos de retorno de validação da financeira.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public enum CodigoRetornoValidacao {

  //
  // Validações do Header
  //
  HEADER_CORRETO("100"),
  /**
   * Qualquer erro que tiver no header, será devolvido esse erro, e a interface inteira será desconsiderada.
   * Caso isso aconteça, todos os detalhes voltarão com o código de retorno da validação {@code 203} {@link #ACORDO_RECUSADO} e na
   * lista de códigos de erro vai estar {@code 399} {@link CodigoRetornoErro#ACORDO_NAO_AVALIADO_DEVIDO_A_ERRO_NO_HEADER_DA_INTERFACE}.
   */
  HEADER_COM_ERRO("101"),

  //
  // Validações da proposta
  //
  ACORDO_APROVADO("200"),
  ACORDO_COM_ERRO_DE_PREENCHIMENTO("201"),
  ACORDO_PENDENTE_DE_APROVACAO("202"),
  ACORDO_RECUSADO("203"),
  ACORDO_DEVOLVIDO_DECURSO_DE_PRAZO("204");

  private final static Map<String, CodigoRetornoValidacao> mapByCodigoRetorno;

  static {
    mapByCodigoRetorno = new HashMap<>();

    for (CodigoRetornoValidacao codigoRetornoValidacao : values()) {
      mapByCodigoRetorno.put(codigoRetornoValidacao.codigoString, codigoRetornoValidacao);
    }
  }

  private final String codigoString;

  CodigoRetornoValidacao(String codigoString) {
    this.codigoString = codigoString;
  }

  // TODO Documentar
  public static CodigoRetornoValidacao getByCodigo(String codigo) {
    return mapByCodigoRetorno.get(codigo);
  }

  @Override
  public String toString() {
    return codigoString;
  }
}
