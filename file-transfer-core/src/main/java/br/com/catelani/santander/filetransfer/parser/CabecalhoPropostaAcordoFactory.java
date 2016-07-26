package br.com.catelani.santander.filetransfer.parser;

import br.com.catelani.santander.filetransfer.domain.CabecalhoPropostaAcordo;

/**
 * @author Kennedy Oliveira
 */
public interface CabecalhoPropostaAcordoFactory {
  CabecalhoPropostaAcordo buildCabecalhoPropostaAcordo(String[] strings);
}
