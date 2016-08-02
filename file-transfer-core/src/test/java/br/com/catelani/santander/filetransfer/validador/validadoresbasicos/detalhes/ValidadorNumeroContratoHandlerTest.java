package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.detalhes;

import br.com.catelani.santander.filetransfer.PropostaAcordoTestUtils;
import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.domain.DadosContrato;
import br.com.catelani.santander.filetransfer.domain.DetalhePropostaAcordo;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordo;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

/**
 * @author Kennedy Oliveira
 */
public class ValidadorNumeroContratoHandlerTest {

  @Test
  public void testNumeroContratoVazio() throws Exception {
    final PropostaAcordo propostaAcordo = PropostaAcordoTestUtils.createPropostaAcordo();

    final DadosContrato dadosContrato = new DadosContrato();

    final DetalhePropostaAcordo detalhePropostaAcordo = new DetalhePropostaAcordo();
    detalhePropostaAcordo.setDadosContrato(dadosContrato);

    propostaAcordo.setDetalhes(Arrays.asList(detalhePropostaAcordo));

    ValidadorPropostaAcordo validador = new ValidadorNumeroContratoHandler();
    final List<ErroValidacao> validacao = validador.validar(propostaAcordo);

    assertThat(validacao, hasSize(1));

    final ErroValidacao erroValidacao = validacao.get(0);
    assertThat(erroValidacao.getCodigoRetornoErro(), is(CodigoRetornoErro.ERRO_PREENCHIMENTO_NUMERO_DO_CONTRATO));
  }

  @Test
  public void testVariosDetalhesDeveAdicionarUmErroParaCada() throws Exception {
    final PropostaAcordo propostaAcordo = PropostaAcordoTestUtils.createPropostaAcordo();

    final List<DetalhePropostaAcordo> detalhes = IntStream.range(0, 10)
                                                          .mapToObj(i -> PropostaAcordoTestUtils.createDetalhePropostaAcordo())
                                                          .map(d -> {
                                                            d.setDadosContrato(new DadosContrato());
                                                            return d;
                                                          })
                                                          .collect(Collectors.toList());

    propostaAcordo.setDetalhes(detalhes);

    ValidadorPropostaAcordo validador = new ValidadorNumeroContratoHandler();
    final List<ErroValidacao> validacao = validador.validar(propostaAcordo);

    assertThat(validacao, hasSize(10));

    for (ErroValidacao erroValidacao : validacao) {
      assertThat(erroValidacao.getCodigoRetornoErro(), is(CodigoRetornoErro.ERRO_PREENCHIMENTO_NUMERO_DO_CONTRATO));
    }
  }

  @Test
  public void testDetalhesPreenchidosCorretamente() throws Exception {
    final DadosContrato dadosContrato1 = new DadosContrato();
    dadosContrato1.setNumeroContrato("12345");

    final DetalhePropostaAcordo detalhePropostaAcordo1 = PropostaAcordoTestUtils.createDetalhePropostaAcordo();
    detalhePropostaAcordo1.setDadosContrato(dadosContrato1);

    final DadosContrato dadosContrato2 = new DadosContrato();
    dadosContrato2.setNumeroContrato("12345");

    final DetalhePropostaAcordo detalhePropostaAcordo2 = PropostaAcordoTestUtils.createDetalhePropostaAcordo();
    detalhePropostaAcordo2.setDadosContrato(dadosContrato2);

    final PropostaAcordo propostaAcordo = PropostaAcordoTestUtils.createPropostaAcordo();
    propostaAcordo.setDetalhes(Arrays.asList(detalhePropostaAcordo1, detalhePropostaAcordo2));

    ValidadorPropostaAcordo validador = new ValidadorNumeroContratoHandler();
    final List<ErroValidacao> validacao = validador.validar(propostaAcordo);

    assertThat(validacao, hasSize(0));
  }
}