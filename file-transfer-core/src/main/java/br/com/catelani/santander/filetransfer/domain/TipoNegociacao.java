package br.com.catelani.santander.filetransfer.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Tipo de negociaçãod a proposta.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public enum TipoNegociacao {

  /**
   * Pagamento de todas as parcelas restantes do contrato.
   */
  QUITACAO("Q"),

  /**
   * Pagamento de todas as parcelas em aberto (vencidas).
   */
  ATUALIZACAO("A"),

  /**
   * <p>Significa que não será um intervalo de parcelas, por exemplo da 1 á 3, pode conter parcelas pagas entre o intervalo.</p>
   * <p>Como regra, a parcela inicial e a final deve não estar paga, as entre as duas podem estar pagas sem problemas.</p>
   * <p>A parcela inicial, deverá ser obrigatoriamente a parcela com maior atraso</p>
   */
  EVENTUAL("E");

  private final static Map<String, TipoNegociacao> mapByTipo;

  static {
    mapByTipo = new HashMap<>();

    for (TipoNegociacao tipoNegociacao : values()) {
      mapByTipo.put(tipoNegociacao.tipoNegociacao, tipoNegociacao);
    }
  }

  private final String tipoNegociacao;

  TipoNegociacao(String tipoNegociacao) {
    this.tipoNegociacao = tipoNegociacao;
  }

  /**
   * Busca um {@link TipoNegociacao} através de um {@code codigoTipo}
   *
   * @param codigoTipo Codigo tipo para buscar.
   * @return {@link TipoNegociacao}
   */
  public static TipoNegociacao getByTipo(String codigoTipo) {
    return mapByTipo.get(codigoTipo);
  }

  @Override
  public String toString() {
    return tipoNegociacao;
  }
}
