package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header;

import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.domain.DadosPrestador;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.util.StringUtils;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordoHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Valida se o Código GCA do Prestador foi preenchido corretamente.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public class ValidadorPropostaCodigoGCAPrestadorHandler extends ValidadorPropostaAcordoHandler {

  @Override
  protected Optional<ErroValidacao> isValid(PropostaAcordo propostaAcordo) {
    log.debug("Validando Codigo GCA do Prestador.");

    final DadosPrestador dadosPrestador = propostaAcordo.getCabecalho().getDadosPrestador();

    if (StringUtils.isNullOrEmpty(dadosPrestador.getCodigoGCA()) || !dadosPrestador.getCodigoGCA().matches("\\d{7}") || Long.parseLong(dadosPrestador.getCodigoGCA()) == 0) {
      final String msg = String.format("O Código GCA do prestador deve ser preenchido, utilizar somente números, e ser diferente de 0. O especificado foi {%s}",
                                       dadosPrestador.getCodigoGCA());

      return Optional.of(new ErroValidacao(CodigoRetornoErro.getByCodigoErro("304"), msg));
    }

    return super.isValid(propostaAcordo);
  }
}
