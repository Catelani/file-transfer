package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header;

import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.domain.DadosPrestador;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordoHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static br.com.catelani.santander.filetransfer.util.StringUtils.isNullOrEmpty;

/**
 * Valida se o Número da Caixa Postal do prestador foi preenchido corretamente, porém não verifica se o número existe,
 * pois somente o banco pode fazer essa validação.
 * Se houver falha, será adicionado um {@link CodigoRetornoErro#ERRO_PREENCHIMENTO_NUMERO_CAIXA_POSTAL}.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public class ValidadorNumeroCaixaPostalPrestadorPropostaAcordoHandler extends ValidadorPropostaAcordoHandler {

  @Override
  protected Optional<ErroValidacao> isValid(PropostaAcordo propostaAcordo) {
    log.debug("Validando Numero Caixa Postal");
    final DadosPrestador dadosPrestador = propostaAcordo.getCabecalho().getDadosPrestador();

    final String caixaPostal = dadosPrestador.getCaixaPostal();

    if (isNullOrEmpty(caixaPostal) || !caixaPostal.matches("\\d{4}")) {
      log.debug("Caixa Postal {} inválida", caixaPostal);
      return Optional.of(new ErroValidacao(CodigoRetornoErro.getByCodigoErro("303"),
                                           "O Código da caixa postal do prestador não pode ser nulo e deve ter 4 digitos númericos. Código atual é: " + caixaPostal));
    }

    log.debug("Caixa Postal {} válida", caixaPostal);

    return super.isValid(propostaAcordo);
  }
}
