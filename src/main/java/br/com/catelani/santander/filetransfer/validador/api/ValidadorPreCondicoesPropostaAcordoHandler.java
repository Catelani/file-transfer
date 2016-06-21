package br.com.catelani.santander.filetransfer.validador.api;

import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * Valida as pré condições para que os outros validadores possam funcionar corretamente.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public class ValidadorPreCondicoesPropostaAcordoHandler extends ValidadorPropostaAcordoHandler {

  @Override
  public @NotNull List<ErroValidacao> validar(PropostaAcordo propostaAcordo) {
    log.debug("Validando pré condições da Proposta de Acordo.");

    Objects.requireNonNull(propostaAcordo, "Proposta de acordo não pode ser nula!");
    Objects.requireNonNull(propostaAcordo.getCabecalho(), "Cabeçalho da proposta de acordo não pode ser nulo!");
    Objects.requireNonNull(propostaAcordo.getCabecalho().getDadosPrestador(), "Dados do prestador do Cabeçalho da Proposta de Acordo não pode ser nulo!");
    Objects.requireNonNull(propostaAcordo.getDetalhes(), "Detalhes da proposta não podem ser nulos!");

    return super.validar(propostaAcordo);
  }
}
