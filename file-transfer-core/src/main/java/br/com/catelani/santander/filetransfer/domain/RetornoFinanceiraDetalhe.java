package br.com.catelani.santander.filetransfer.domain;

import lombok.Data;

import java.util.List;

/**
 * Informações da financeira sobre uma parcela ({@link DetalhePropostaAcordo}) da proposta de acordo.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Data
public class RetornoFinanceiraDetalhe {

  // TODO Documentar
  private CodigoRetornoValidacao codigoRetornoValidacao;
  private List<CodigoRetornoErro> codigoRetornoErros;
  private String numeroAcordoRPCGerado;
  private String linhaDigitavelBoletoGerado;
}
