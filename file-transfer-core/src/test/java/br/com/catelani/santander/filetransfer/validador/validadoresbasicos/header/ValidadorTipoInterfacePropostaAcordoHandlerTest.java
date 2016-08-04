package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header;

import br.com.catelani.santander.filetransfer.PropostaAcordoTestUtils;
import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.domain.TipoInterface;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordo;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Kennedy Oliveira
 */
public class ValidadorTipoInterfacePropostaAcordoHandlerTest {

  @Test
  public void testPropostaAcordoTipoInterfaceIncorreto() throws Exception {
    final PropostaAcordo propostaAcordo = PropostaAcordoTestUtils.createPropostaAcordo();
    propostaAcordo.getCabecalho().setTipoInterface(TipoInterface.PROTOCOLO_DE_RECEBIMENTO);

    ValidadorPropostaAcordo validador = new ValidadorTipoInterfacePropostaAcordoHandler();
    @NotNull final List<ErroValidacao> resValidacao = validador.validar(propostaAcordo);

    assertThat(resValidacao, hasSize(1));
    assertThat(resValidacao.get(0).getCodigoRetornoErro(), is(CodigoRetornoErro.ERRO_PREENCHIMENTO_TIPO_DE_INTERFACE));
  }

  @Test
  public void testPropostaAcordoTipoInterfaceCorreta() throws Exception {
    final PropostaAcordo propostaAcordo = PropostaAcordoTestUtils.createPropostaAcordo();
    propostaAcordo.getCabecalho().setTipoInterface(TipoInterface.PROPOSTA_DE_ACORDO);

    ValidadorPropostaAcordo validador = new ValidadorTipoInterfacePropostaAcordoHandler();
    @NotNull final List<ErroValidacao> resValidacao = validador.validar(propostaAcordo);

    assertThat(resValidacao, hasSize(0));
  }
}