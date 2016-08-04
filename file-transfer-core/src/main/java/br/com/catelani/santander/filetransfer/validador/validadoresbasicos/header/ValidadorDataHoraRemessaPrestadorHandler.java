package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.header;

import br.com.catelani.santander.filetransfer.domain.*;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordoHandler;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Optional;

/**
 * <p>
 * Valida se a data da remessa do prestador está preenchida corretamente, considerando que
 * a proposta será enviada na data da validação, a data deve ser o dia atual.
 * </p>
 * <p>
 * Caso seja um {@link br.com.catelani.santander.filetransfer.domain.TipoInterface#RETORNO_FINANCEIRA}, a data de remessa deve ser menor ou igual
 * a data de processamento, e maior ou igual ao dia util iediatamente anterior a data de processamento.
 * </p>
 * <p>
 * A data de processamento é o {@link RetornoFinanceiraPA#getDataRetorno()}.
 * </p>
 * <p>
 * Se ouver falha, será adicioando um {@link br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro#ERRO_PREENCHIMENTO_DATA_DA_REMESSA}.
 * </p>
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public class ValidadorDataHoraRemessaPrestadorHandler extends ValidadorPropostaAcordoHandler {

  @Override
  protected Optional<ErroValidacao> isValid(PropostaAcordo propostaAcordo) {
    log.debug("Iniciando a Validação de Data de Remessa do Prestador.");
    final TipoInterface tipoInterface = propostaAcordo.getCabecalho().getTipoInterface();

    final DadosPrestador dadosPrestador = propostaAcordo.getCabecalho().getDadosPrestador();
    final LocalDate dataRemessa = dadosPrestador.getDataRemessa();

    if (tipoInterface == TipoInterface.PROPOSTA_DE_ACORDO) {
      if (dataRemessa.isBefore(LocalDate.now())) {
        return Optional.of(new ErroValidacao(CodigoRetornoErro.getByCodigoErro("306"), "Data de remessa do prestador inválida"));
      }
    } else if (tipoInterface == TipoInterface.RETORNO_FINANCEIRA) {
      final LocalDate dataRetornoFinanceira = propostaAcordo.getCabecalho().getRetornoFinanceira().getDataRetorno();

      if (dataRemessa.isAfter(dataRetornoFinanceira)) {
        final ErroValidacao erro = new ErroValidacao(CodigoRetornoErro.getByCodigoErro("306"),
                                                     "Data de remessa inválida, está depois da data de processamento. Data remessa: [" + dataRemessa + "], data de processamento: [" + dataRetornoFinanceira + "]");
        log.warn("Erro ao validar a data", erro);
        return Optional.of(erro);
      }
    } else {
      // Não será avaliado pois não tera como validar nada, ja que o banco não retorna nenhuma informação util quando for o terceiro tipo (PROTOCOLO_RECEBIMENTO)
      log.debug("Proposta com Tipo de Interface [{}] não avaliada pelo Validador de DatahoraRemessaPrestador", tipoInterface);
    }

    return super.isValid(propostaAcordo);
  }
}
