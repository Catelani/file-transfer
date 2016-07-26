package br.com.catelani.santander.filetransfer.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Dados da negociação de uma parcela de proposta de acordo.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Data
public class ParcelaAcordo {

  /**
   * Sequencia das parcelas do acordo iniciando em 1 para o mesmo contrato.
   */
  private int sequencia;

  /**
   * Data de vencimento desta parcela.
   */
  private LocalDate dataVencimento;

  /**
   * Quantidade de dias que essa parcela está em atraso.
   */
  private int quantidadeDiasAtraso;

  /**
   * <p>Valor total da parcela, deve ser maior que zero e não ultrapassar o {@link DadosNegociacao#valorTotalAcordo}.</p>
   * <p>O somatório desse campo entre todas as parcelas do acordo deve ser igual o {@link DadosNegociacao#valorTotalAcordo}.</p>
   */
  private BigDecimal valorTotal;

  /**
   * <p>Esse campo só deve ser preenchido na primeira parcela do acordo,
   * e deve ser o total do saldo GCA do contrato, {@link DadosContrato#valorGCAContrato},
   * para as outras parcelas deve ser sempre 0.</p>
   */
  private BigDecimal valorGca = BigDecimal.ZERO;

  /**
   * <p>Valor total da comissão que será repassado ao Escritório de Cobrança para cada parcela do Acordo
   * que o cliente honrar.</p>
   * <p>O somatório do Honorário de todas as parcelas do Acordo deverá
   * ser = ({@link DadosNegociacao#valorHonorarioMaximoCampanha} - {@link DadosNegociacao#descontoHonorarios}).</p>
   */
  private BigDecimal valorHonorario;
}
