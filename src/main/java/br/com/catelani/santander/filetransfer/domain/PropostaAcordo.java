package br.com.catelani.santander.filetransfer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Proposta de acordo.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
public class PropostaAcordo {

  /**
   * Informações gerais da proposta de acordo.
   */
  private CabecalhoPropostaAcordo cabecalho;

  /**
   * Proposta de acordos.
   */
  private List<DetalhePropostaAcordo> detalhes;

  /**
   * <p>Método para facil recuperação do Código de Retorno do Banco para essa proposta.</p>
   * <p>Esse método é a mesma coisa que chamar o {@link RetornoFinanceiraPA#getCodigoRetornoValidacao()}.</p>
   *
   * @return O Código de Retorno do Banco com o status dessa proposta.
   */
  public CodigoRetornoValidacao getCodigoRetornoValidacao() {
    return cabecalho != null && cabecalho.getRetornoFinanceira() != null ? cabecalho.getRetornoFinanceira().getCodigoRetornoValidacao() : null;
  }
}

