package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header;

import br.com.catelani.santander.filetransfer.PropostaAcordoTestUtils;
import br.com.catelani.santander.filetransfer.domain.*;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordo;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Kennedy Oliveira
 */
public class ValidadorDataHoraRemessaPrestadorHandlerTest {

  @Test
  public void testPropostaAcordoDataIncorreta() throws Exception {
    final PropostaAcordo propostaAcordo = PropostaAcordoTestUtils.createPropostaAcordo();
    final DadosPrestador dadosPrestador = propostaAcordo.getCabecalho().getDadosPrestador();
    dadosPrestador.setDataRemessa(LocalDate.now().minusDays(3));

    ValidadorPropostaAcordo validador = new ValidadorDataHoraRemessaPrestadorHandler();
    @NotNull final List<ErroValidacao> resValidacao = validador.validar(propostaAcordo);

    assertThat(resValidacao, hasSize(1));
    assertThat(resValidacao.get(0).getCodigoRetornoErro(), is(CodigoRetornoErro.ERRO_PREENCHIMENTO_DATA_DA_REMESSA));
  }

  @Test
  public void testPropostaAcordoDataCorreta() throws Exception {
    final PropostaAcordo propostaAcordo = PropostaAcordoTestUtils.createPropostaAcordo();
    final DadosPrestador dadosPrestador = propostaAcordo.getCabecalho().getDadosPrestador();
    dadosPrestador.setDataRemessa(LocalDate.now());
    dadosPrestador.setHoraRemessa(LocalTime.now());

    ValidadorPropostaAcordo validador = new ValidadorDataHoraRemessaPrestadorHandler();
    @NotNull final List<ErroValidacao> resValidacao = validador.validar(propostaAcordo);

    assertThat(resValidacao, hasSize(0));
  }

  /**
   * Esse teste é meio estranho pois não imagino os casos de uso, somente para validar algo criado na mão,
   * pois o banco já vai fazer essa validação
   */
  @Test
  public void testRetornoFinanceiraDataInvalida() throws Exception {
    final PropostaAcordo propostaAcordo = PropostaAcordoTestUtils.createPropostaAcordo();
    propostaAcordo.getCabecalho().setTipoInterface(TipoInterface.RETORNO_FINANCEIRA);

    final DadosPrestador dadosPrestador = propostaAcordo.getCabecalho().getDadosPrestador();
    dadosPrestador.setDataRemessa(LocalDate.now());
    dadosPrestador.setHoraRemessa(LocalTime.now());

    final RetornoFinanceiraPA retornoFinanceira = new RetornoFinanceiraPA();
    retornoFinanceira.setDataRetorno(LocalDate.now().minusDays(1));

    propostaAcordo.getCabecalho().setRetornoFinanceira(retornoFinanceira);

    ValidadorPropostaAcordo validador = new ValidadorDataHoraRemessaPrestadorHandler();
    @NotNull final List<ErroValidacao> resValidacao = validador.validar(propostaAcordo);

    assertThat(resValidacao, hasSize(1));
    assertThat(resValidacao.get(0).getCodigoRetornoErro(), is(CodigoRetornoErro.ERRO_PREENCHIMENTO_DATA_DA_REMESSA));
  }

  @Test
  public void testProtocoloRecimento() throws Exception {
    final PropostaAcordo propostaAcordo = PropostaAcordoTestUtils.createPropostaAcordo();
    propostaAcordo.getCabecalho().setTipoInterface(TipoInterface.PROTOCOLO_DE_RECEBIMENTO);

    ValidadorPropostaAcordo validador = new ValidadorDataHoraRemessaPrestadorHandler();
    @NotNull final List<ErroValidacao> resValidacao = validador.validar(propostaAcordo);

    assertThat(resValidacao, hasSize(0));
  }
}