package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.detalhes;

import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordoHandler;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static br.com.catelani.santander.filetransfer.util.StringUtils.isNullOrEmpty;

/**
 * <p>O Número do Contrato deve estar preenchido.</p>
 * <p>Caso falhe, o erro {@link CodigoRetornoErro#ERRO_PREENCHIMENTO_NUMERO_DO_CONTRATO} será adicionado a lista de erros.</p>
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public class ValidadorNumeroContratoHandler extends ValidadorPropostaAcordoHandler {

  @Override
  public @NotNull List<ErroValidacao> validar(PropostaAcordo propostaAcordo) {
    log.debug("Validando Número de Contrato");
    final List<ErroValidacao> erros = super.validar(propostaAcordo);

    propostaAcordo.getDetalhes()
                  .stream()
                  .filter(d -> d.getDadosContrato() == null || isNullOrEmpty(d.getDadosContrato().getNumeroContrato()))
                  .map(d -> String.format("Não há dados do Contrato ou Número do Contrato preenchido para o Detalhe [%s]", d))
                  .map(msg -> new ErroValidacao(CodigoRetornoErro.ERRO_PREENCHIMENTO_NUMERO_DO_CONTRATO, msg))
                  .peek(e -> log.debug("Erro validando numero de contrato: {}", e))
                  .forEach(erros::add);

    return erros;
  }
}
