package br.com.catelani.santander.filetransfer.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>Detalhes da proposta de acordo.</p>
 * <p>Cada parcela negociada de um contrato deve ser representada por um unico {@link DetalhePropostaAcordo}.</p>
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public class DetalhePropostaAcordo {

  /**
   * Detalhes compartilhados da proposta.
   */
  private DetalhesCompartilhados detalhesCompartilhados;

  /**
   * Retorno da financeira sobre a parcela dessa proposta.
   */
  private RetornoFinanceiraDetalhe retornoFinanceiraDetalhe;

  /**
   * Informações da parcela acordada.
   */
  private ParcelaAcordo parcelaAcordo;

  /**
   * <p>Numero de sequencia, começando a partir de 2.</p>
   * <p>Representa a sequencia da linha no arquivo final, o 1 é o cabeçalho: {@link CabecalhoPropostaAcordo}.</p>
   */
  private int sequencia;

  public DetalhePropostaAcordo() {
    this(new DetalhesCompartilhados());
  }

  public DetalhePropostaAcordo(DetalhesCompartilhados detalhesCompartilhados) {
    this.detalhesCompartilhados = detalhesCompartilhados;
  }

  public String getTipoRegistro() {return this.detalhesCompartilhados.tipoRegistro;}

  public TipoCarteira getTipoCarteira() {return this.detalhesCompartilhados.tipoCarteira;}

  public void setTipoCarteira(TipoCarteira tipoCarteira) {this.detalhesCompartilhados.tipoCarteira = tipoCarteira; }

  public DadosContrato getDadosContrato() {return this.detalhesCompartilhados.dadosContrato;}

  public void setDadosContrato(DadosContrato dadosContrato) {this.detalhesCompartilhados.dadosContrato = dadosContrato; }

  public DadosNegociacao getDadosNegociacao() {return this.detalhesCompartilhados.dadosNegociacao;}

  public void setDadosNegociacao(DadosNegociacao dadosNegociacao) {this.detalhesCompartilhados.dadosNegociacao = dadosNegociacao; }

  public RetornoFinanceiraDetalhe getRetornoFinanceiraDetalhe() {return this.retornoFinanceiraDetalhe;}

  public void setRetornoFinanceiraDetalhe(RetornoFinanceiraDetalhe retornoFinanceiraDetalhe) {this.retornoFinanceiraDetalhe = retornoFinanceiraDetalhe; }

  public ParcelaAcordo getParcelaAcordo() {return this.parcelaAcordo;}

  public void setParcelaAcordo(ParcelaAcordo parcelaAcordo) {this.parcelaAcordo = parcelaAcordo; }

  public int getSequencia() {return this.sequencia;}

  public void setSequencia(int sequencia) {this.sequencia = sequencia; }

  @Data
  public static class DetalhesCompartilhados {

    /**
     * De acordo com o banco, essa informação é fixa no momento.
     */
    private String tipoRegistro = "2";

    /**
     * Tipo de carteira da negociação.
     */
    private TipoCarteira tipoCarteira;

    /**
     * <p>Dados do contrato referente a essa negociação</p>
     * <p>Isso sera repetido para cada {@link DetalhePropostaAcordo} que for referente ao mesmo contrato.</p>
     */
    private DadosContrato dadosContrato;

    /**
     * <p>Dados referentes a negociação com o cliente.</p>
     * <p>Será repetido para cada {@link DetalhePropostaAcordo} que for referente ao mesmo contrato.</p>
     */
    private DadosNegociacao dadosNegociacao;
  }
}
