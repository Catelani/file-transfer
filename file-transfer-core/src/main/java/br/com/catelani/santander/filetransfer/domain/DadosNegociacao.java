package br.com.catelani.santander.filetransfer.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>Dados gerais da negociação com o cliente.</p>
 * <p>Será repetido para cada uma das parcelas negociadas de um mesmo contrato.</p>
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
// TODO Documentar
@Data
public class DadosNegociacao {


  private BigDecimal valorSaldoGCADispensado;
  private BigDecimal valorTotalAcordo;
  private BigDecimal valorTotalAcordoSemHonorarios;
  private String codigoCampanhaRPC;
  private boolean indicadorExcecaoCampanha;
  /**
   * Tipo da negociação com o Cliente.
   */
  private TipoNegociacao tipoNegociacao;

  private BigDecimal valorHonorarioMaximoCampanha;
  /**
   * Deve ser menor ou igual que o {@link #valorHonorarioMaximoCampanha}.
   */
  private BigDecimal descontoHonorarios;
  private int quantidadeParcelasAcordo;
}
