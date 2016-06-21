package br.com.catelani.santander.filetransfer.validador.api;

import br.com.catelani.santander.filetransfer.domain.CabecalhoPropostaAcordo;
import br.com.catelani.santander.filetransfer.domain.DadosPrestador;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header.ValidadorNumeroCaixaPostalPrestadorPropostaAcordoHandler;
import br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header.ValidadorTipoInterfacePropostaAcordoHandler;
import br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header.ValidadorTipoRegistroPropostaAcordoHandler;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;

/**
 * @author Kennedy Oliveira
 */
public class TestValidadores {


  @Test
  public void testValidadorChain() throws Exception {
//    final ValidadorPropostaAcordo validador = new ValidadorBuilder().add(new ValidadorTipoInterfacePropostaAcordoHandler())
//                                                                    .add(new ValidadorTipoRegistroPropostaAcordoHandler())
//                                                                    .add(new ValidadorNumeroCaixaPostalPrestadorPropostaAcordoHandler())
//                                                                    .build();
//
//    final DadosPrestador dadosPrestador = new DadosPrestador();
//    final CabecalhoPropostaAcordo cabecalho = new CabecalhoPropostaAcordo();
//    cabecalho.setDadosPrestador(dadosPrestador);
//
//    final PropostaAcordo propostaAcordo = new PropostaAcordo(cabecalho, Collections.emptyList());
//
//    List<ErroValidacao> erros = validador.validar(propostaAcordo);
//
//    assertThat(erros, is(empty()));
  }
}