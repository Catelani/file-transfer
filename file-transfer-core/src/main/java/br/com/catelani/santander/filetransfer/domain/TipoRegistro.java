package br.com.catelani.santander.filetransfer.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Tipo de registro, atualmente utilizado apenas em PA com valor fixo 0 ("Controle");
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public enum TipoRegistro {

  /**
   * Atualmente o unico tipo disponibilizado pelo banco, e sem documentação segundo o manual do file transfer.
   */
  CONTROLE("0");

  private static final Map<String, TipoRegistro> valores;

  static {
    valores = new HashMap<>();
    for (TipoRegistro tipoRegistro : TipoRegistro.values()) {
      valores.put(tipoRegistro.tipoRegistro, tipoRegistro);
    }
  }

  private final String tipoRegistro;

  TipoRegistro(String tipoRegistro) {
    this.tipoRegistro = tipoRegistro;
  }

  /**
   * @return O Tipo de Registro a ser inserido no cabeçalho da Interface de PA.
   */
  @Override
  public String toString() {
    return tipoRegistro;
  }

  // TODO Documentar
  public static TipoRegistro getByTipoRegistro(String tipoRegistro) {
    return valores.get(tipoRegistro);
  }
}
