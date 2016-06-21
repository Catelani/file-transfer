package br.com.catelani.santander.filetransfer.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Informações do contrato referente a uma proposta.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Data
public class DadosContrato {

  /**
   * Número de contrato do financiamento.
   */
  private String numeroContrato;

  /**
   * Parcela inicial da proposta.
   */
  private int parcelaInicial;

  /**
   * Parcela final da proposta.
   */
  private int parcelaFinal;

  /**
   * <p>Data de vencimento da parcela com maior atraso.</p>
   * <p>Formato: {@code AAAAMMDD}</p>
   */
  private LocalDate dataVencimentoParcelaMaiorAtraso;

  /**
   * <p>Valor principal das parcelas do contrato negociadas.</p>
   * <p>Somatoria dos valores sem juros e outras coisas, apenas do valor das parcelas.</p>
   * <p>Utilizar apenas duas casas decimais.</p>
   */
  private BigDecimal valorTotalParcelasNegociadas;

  /**
   * Indica se o contrato está ajuizado ou não
   */
  private boolean contratoAjuizado;

  /**
   * <p>Valor do GCA do contrato.</p>
   * <p>Utilizar apenas duas casas decimais.</p>
   */
  private BigDecimal valorGCAContrato;
}
