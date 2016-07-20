package br.com.catelani.santander.filetransfer.validador;

import br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Kennedy Oliveira
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
  ValidadorNumeroCaixaPostalPrestadorPropostaAcordoHandlerTest.class,
  ValidadorNumeroLoteHandlerTest.class,
  ValidadorNumeroSequenciaHandlerTest.class,
  ValidadorPropostaCodigoGCAPrestadorHandlerTest.class,
  ValidadorPropostaNomePrestadorHandlerTest.class,
  ValidadorQuantidadeSolicitacoesHandlerTest.class,
  ValidadorTipoPrestacaoServicoHandlerTest.class
})
public class TestValidadoresHeaderSuite {
}
