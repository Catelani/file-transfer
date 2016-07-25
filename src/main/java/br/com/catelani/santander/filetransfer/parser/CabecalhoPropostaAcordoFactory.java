package br.com.catelani.santander.filetransfer.parser;

import br.com.catelani.santander.filetransfer.domain.CabecalhoPropostaAcordo;

/**
 * Factory para {@link CabecalhoPropostaAcordo}
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public interface CabecalhoPropostaAcordoFactory {

  /**
   * Gera um {@link CabecalhoPropostaAcordo} a partir das informações extraidas das {@code strings}
   *
   * @param strings Informações do cabeçalho das propostas.
   * @return {@link CabecalhoPropostaAcordo}
   */
  CabecalhoPropostaAcordo buildCabecalhoPropostaAcordo(String[] strings);
}
