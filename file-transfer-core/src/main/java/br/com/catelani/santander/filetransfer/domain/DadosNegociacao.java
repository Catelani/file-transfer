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
@Data
public class DadosNegociacao {

  /**
   * Valor do Saldo GCA que foi dispensado pelo ESCOB.
   */
  private BigDecimal valorSaldoGCADispensado;

  /**
   * Valor total do acordo incluindo honorarios.
   */
  private BigDecimal valorTotalAcordo;

  /**
   * Valor total sem os honorarios.
   */
  private BigDecimal valorTotalAcordoSemHonorarios;

  /**
   * Código da Campanha RPC, esse codigo é fornecido pelo banco no formato de uma tabela, que tem regras
   * de quantidade de dias em atraso, quantidade de parcelas, etc cada um representando uma campanha RPC com seu
   * respectivo código.
   */
  private String codigoCampanhaRPC;

  // TODO Documentar
  private boolean indicadorExcecaoCampanha;

  /**
   * Tipo da negociação com o Cliente.
   */
  private TipoNegociacao tipoNegociacao;

  /**
   * Valor maximo que pode ser considerado como honorario, esse valor será calculado através dos mesmos parametros
   * do item {@link #codigoCampanhaRPC}, na tabela que o banco fornece ao ESCOB constará as informações de honorarios maximo.
   */
  private BigDecimal valorHonorarioMaximoCampanha;

  /**
   * Deve ser menor ou igual que o {@link #valorHonorarioMaximoCampanha}.
   */
  private BigDecimal descontoHonorarios;

  /**
   * Quantidade de parcelas do pagamento do cliente, em quantas vezes será parcelado o acordo que será feito e não quantas
   * parcelas ele está pagando (em atraso ou não)
   */
  private int quantidadeParcelasAcordo;
}
