package br.com.catelani.santander.filetransfer.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Tipo de carteira do acordo.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public enum TipoCarteira {

  GARANTIA("01"),
  CDC("02"),
  SALDO_REMANESCENTE("03"),
  PREJUIZO("04"),
  POS_JUDICIAL("05"),
  FINDOS("06"),
  IPVA("07"),
  DOLAR("08");

  private final static Map<String, TipoCarteira> mapByTipo;

  static {
    mapByTipo = new HashMap<>();

    for (TipoCarteira tipoCarteira : values()) {
      mapByTipo.put(tipoCarteira.tipoCarteira, tipoCarteira);
    }
  }

  private final String tipoCarteira;

  TipoCarteira(String tipoCarteira) {
    this.tipoCarteira = tipoCarteira;
  }

  // TODO Documentar
  public static TipoCarteira getByTipo(String tipo) {
    return mapByTipo.get(tipo);
  }

  /**
   * @return Tipo de carteira a ser escrito nos arquivos de proposta.
   */
  @Override
  public String toString() {
    return tipoCarteira;
  }
}
