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
}

