package br.com.catelani.santander.filetransfer.validador.validadoresbasicos;

import br.com.catelani.santander.filetransfer.domain.DetalhePropostaAcordo;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Métodos utilitarios para ajudar a criar Validadores.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public class ValidadorUtils {

  private static final @NotNull Function<DetalhePropostaAcordo, String> EXTRAIR_NUMERO_CONTRATO_PROPOSTA_ACORDO = d -> d.getDadosContrato().getNumeroContrato();

  private ValidadorUtils() {}

  /**
   * @return {@link Function} que dado um {@link DetalhePropostaAcordo} extrai o numero do Contrato.
   */
  @NotNull
  public static Function<DetalhePropostaAcordo, String> extractNumeroContrato() {
    return EXTRAIR_NUMERO_CONTRATO_PROPOSTA_ACORDO;
  }

}
