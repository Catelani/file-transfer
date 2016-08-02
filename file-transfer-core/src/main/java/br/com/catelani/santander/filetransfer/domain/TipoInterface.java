package br.com.catelani.santander.filetransfer.domain;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Tipo de interface do banco, pode ser tanto a que está sendo enviada, ou a que está sendo recebida.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public enum TipoInterface {

  /**
   * Proposta de acordo é o tipo de interface que inicia a comunicação com o banco, é enviada do ESCOB para o Banco
   * para aprovação dos acordos e geração das laminas.
   */
  PROPOSTA_DE_ACORDO("PA"),

  /**
   * Retorno de uma {@link #PROPOSTA_DE_ACORDO}, nessa interface constara as validações, acordos, laminas e todas as informações
   * pertinentes de resposta a uma {@link #PROPOSTA_DE_ACORDO}, será devolvida após o processamento da {@link #PROPOSTA_DE_ACORDO}
   */
  RETORNO_FINANCEIRA("RT"),

  /**
   * Interface que é enviada do banco para o ESCOB em resposta a um envia de {@link #PROPOSTA_DE_ACORDO}, esse tipo de interface
   * é apenas um marcador de recebimento.
   */
  PROTOCOLO_DE_RECEBIMENTO("PR");

  private final static Map<String, TipoInterface> mapByTipo;

  static {
    mapByTipo = new HashMap<>();

    for (TipoInterface tipoInterface : values()) {
      mapByTipo.put(tipoInterface.tipoInterface, tipoInterface);
    }

    // Tipos especiais de retornos da financeira
    mapByTipo.put("RN", RETORNO_FINANCEIRA);
    mapByTipo.put("RE", RETORNO_FINANCEIRA);
    mapByTipo.put("RB", RETORNO_FINANCEIRA);
  }

  private final String tipoInterface;

  TipoInterface(String tipoInterface) {
    this.tipoInterface = tipoInterface;
  }

  @Nullable
  public static TipoInterface getPorCodigoTipoInterface(@Nullable String codigo) {
    return mapByTipo.get(codigo);
  }

  /**
   * @return O tipo da interface para ser gravado nos arquivos.
   */
  @Override
  public String toString() {
    return tipoInterface;
  }
}
