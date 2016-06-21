package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.detalhes;

import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordoHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public class ValidadorValorTotalAcordoClienteHandler extends ValidadorPropostaAcordoHandler {

  @Override
  public @NotNull List<ErroValidacao> validar(PropostaAcordo propostaAcordo) {
    final List<ErroValidacao> erros = super.validar(propostaAcordo);

    propostaAcordo.getDetalhes()
                  .stream()
                  .filter(d -> d.getDadosNegociacao().getValorTotalAcordoSemHonorarios().compareTo(d.getDadosNegociacao().getValorTotalAcordo()) <= 0);

    return erros;
  }
}
