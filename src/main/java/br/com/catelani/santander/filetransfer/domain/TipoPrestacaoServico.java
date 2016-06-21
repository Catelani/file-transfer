package br.com.catelani.santander.filetransfer.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Tipo da prestação de serviço para o banco.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public enum TipoPrestacaoServico {

  ASSESSORIA("025"),
  ADVOGADO("026");

  private final static Map<String, TipoPrestacaoServico> mapPorCodigoTipoPrestacao;

  static {
    mapPorCodigoTipoPrestacao = new HashMap<>();
    for (TipoPrestacaoServico tipoPrestacaoServico : values()) {
      mapPorCodigoTipoPrestacao.put(tipoPrestacaoServico.tipoPrestacaoServico, tipoPrestacaoServico);
    }
  }

  private final String tipoPrestacaoServico;

  TipoPrestacaoServico(String tipoPrestacaoServico) {
    this.tipoPrestacaoServico = tipoPrestacaoServico;
  }

  public static TipoPrestacaoServico getByCodigo(String codigo) {
    return mapPorCodigoTipoPrestacao.get(codigo);
  }

  /**
   * @return O tipo de prestação de serviço a ser escrito nos arquivos.
   */
  @Override
  public String toString() {
    return tipoPrestacaoServico;
  }
}
