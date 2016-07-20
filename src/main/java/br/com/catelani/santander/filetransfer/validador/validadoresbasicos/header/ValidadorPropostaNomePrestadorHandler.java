package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header;

import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordoHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static br.com.catelani.santander.filetransfer.util.StringUtils.isNullOrEmpty;

/**
 * Valida se o nome do prestador foi preenchido corretamente.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public class ValidadorPropostaNomePrestadorHandler extends ValidadorPropostaAcordoHandler {

  // erro que será retornado caso a validação falhar.
  private static final ErroValidacao ERRO_VALIDACAO = new ErroValidacao(CodigoRetornoErro.getByCodigoErro("305"), "O Nome do prestador não pode ser em branco!");

  /**
   * Tamanho maximo do nome do prestador segundo o manual do File Transfer.
   */
  private static final int TAMANHO_MAXIMO_NOME_PRESTADOR = 40;

  @Override
  protected Optional<ErroValidacao> isValid(PropostaAcordo propostaAcordo) {
    log.debug("Validando Nome do Prestador.");

    final String nomePrestador = propostaAcordo.getCabecalho().getDadosPrestador().getNome();

    // não pode ser nulo, ou somente espaços
    if (nomePrestador == null || isNullOrEmpty(nomePrestador.trim())) {
      log.debug("Nome do Prestador inválido, está em branco.", nomePrestador);
      return Optional.of(ERRO_VALIDACAO);
    }

    final int tamanhoNomePrestador = nomePrestador.length();
    if (tamanhoNomePrestador > TAMANHO_MAXIMO_NOME_PRESTADOR) {
      log.debug("Tamanho do Nome do Prestador [{}] maior que o permitido [{}].", tamanhoNomePrestador, TAMANHO_MAXIMO_NOME_PRESTADOR);

      return Optional.of(new ErroValidacao(CodigoRetornoErro.getByCodigoErro("305"),
                                           String.format("O Tamanho do Nome do Prestador [%d] excede o tamanho maximo permitido de [%d]",
                                                         tamanhoNomePrestador,
                                                         TAMANHO_MAXIMO_NOME_PRESTADOR)));
    }

    log.debug("Nome do Prestador [{}] valido.", nomePrestador);

    return super.isValid(propostaAcordo);
  }
}
