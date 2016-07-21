package br.com.catelani.santander.filetransfer.domain;

import lombok.Data;

/**
 * Cabeçalho de uma PA (Proposta de Acordo).
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Data
public class CabecalhoPropostaAcordo {

  /**
   * Tipo da Interface, nesse caso sera uma Proposta de Acordo
   */
  private TipoInterface tipoInterface = TipoInterface.PROPOSTA_DE_ACORDO;

  /**
   * Tipo de Registro da Interface.
   * Atualmente utilizando valor fixo de {@link TipoRegistro#CONTROLE}.
   */
  private TipoRegistro tipoRegistro;

  /**
   * Dados referentes ao escritório de cobrança.
   */
  private DadosPrestador dadosPrestador;

  /**
   * <p>Atualmente segundo o manual, é um numero fixo (00001).</p>
   * <p>Considerado como String pois no manual consta como um campo Alfanumerico.</p>
   */
  private String numeroDeSequencia = "00001";

  /**
   * Resultado da validação da financeira da proposta enviada.
   */
  private RetornoFinanceiraPA retornoFinanceira;
}
