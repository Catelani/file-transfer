package br.com.catelani.santander.filetransfer.parser;

import br.com.catelani.santander.filetransfer.domain.DetalhePropostaAcordo;

/**
 * Factory para {@link DetalhePropostaAcordo}.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public interface DetalhePropostaFactory {

  /**
   * Gera um {@link DetalhePropostaAcordo} a partir das informações contidas no {@code informacoesAcordo} de acordo com as regras do banco.
   *
   * @param informacoesAcordo Informações do arquivo de Proposta do banco.
   * @return Um {@link DetalhePropostaAcordo}
   * @throws IllegalArgumentException Se o tamanho do parametro {@code informacoesAcordo} for diferente do total de informações do arquivo no banco.
   */
  DetalhePropostaAcordo buildDetalheProposta(String[] informacoesAcordo);
}
