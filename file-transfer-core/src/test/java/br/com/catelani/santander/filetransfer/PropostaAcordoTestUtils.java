package br.com.catelani.santander.filetransfer;

import br.com.catelani.santander.filetransfer.domain.CabecalhoPropostaAcordo;
import br.com.catelani.santander.filetransfer.domain.DadosPrestador;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;

import java.util.ArrayList;

/**
 * Métodos utilitários para facilitar os testes com {@link br.com.catelani.santander.filetransfer.domain.PropostaAcordo}
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public class PropostaAcordoTestUtils {

  /**
   * @return Uma proposta acordo com todos os relacionamentos criados e vazios.
   */
  public static PropostaAcordo createPropostaAcordo() {
    final DadosPrestador dadosPrestador = new DadosPrestador();

    final CabecalhoPropostaAcordo cabecalho = new CabecalhoPropostaAcordo();
    cabecalho.setDadosPrestador(dadosPrestador);

    return new PropostaAcordo(cabecalho, new ArrayList<>());
  }
}
