package br.com.catelani.santander.filetransfer.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Tipo do retorno da financeira.</p>
 * <p>Basicamente representa o status geral da proposta enviada.</p>
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public enum TipoRetornoFinanceira {

  /**
   * Retorno contento as validações das {@link PropostaAcordo}, para os acordos aprovados,
   * o número do acordo gerado e o Boleto da primeira parcela deste Acordo.
   */
  NORMAL("N"),
  /**
   * Retorno contento a aprovação ou recusa das Propostas de Exceção, e para os Acordos de Exceção aprovados,
   * o número do Acordo gerado e o Boleto da primeira parcela.
   */
  // TODO Entender melhor para documentar
  EXCECAO("E"),

  /**
   * Para os Acordos de Prestadores, que estiverem aprovados e ativos, retornará o Boleto da parcela seguinte, a vencer,
   * destes Acordos desde que a parcela imediatamente anterior do respectivo Acordo, já esteja paga integralmente há pelo menos 5 dias.
   */
  // TODO Entender melhor para documentar
  BOLETOS_SEGUINTES("B");

  private final static Map<String, TipoRetornoFinanceira> mapByCodigo;

  static {
    mapByCodigo = new HashMap<>();
    for (TipoRetornoFinanceira tipoRetornoFinanceira : values()) {
      mapByCodigo.put(tipoRetornoFinanceira.tipoRetornoFinanceira, tipoRetornoFinanceira);
    }
  }

  private final String tipoRetornoFinanceira;

  TipoRetornoFinanceira(String tipoRetornoFinanceira) {
    this.tipoRetornoFinanceira = tipoRetornoFinanceira;
  }

  public static TipoRetornoFinanceira getPorCodigo(String codigo) {
    return mapByCodigo.get(codigo);
  }

  /**
   * @return Tipo de Retorno da financeira que foi escrito no arquivo.
   */
  @Override
  public String toString() {
    return tipoRetornoFinanceira;
  }
}
