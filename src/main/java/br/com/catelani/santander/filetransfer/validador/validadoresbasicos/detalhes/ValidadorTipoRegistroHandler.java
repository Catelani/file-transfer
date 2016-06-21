package br.com.catelani.santander.filetransfer.validador.validadoresbasicos.detalhes;

import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import br.com.catelani.santander.filetransfer.domain.DetalhePropostaAcordo;
import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.validador.api.ErroValidacao;
import br.com.catelani.santander.filetransfer.validador.api.ValidadorPropostaAcordoHandler;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * <p>Valida se o Tipo de Registro da proposta foi preenchido corretamente.</p>
 * <p>Caso não, o erro {@link CodigoRetornoErro#ERRO_PREENCHIMENTO_TIPO_REGISTRO} será adicionado para cada detalhe com preenchimento incorreto.</p>
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
public class ValidadorTipoRegistroHandler extends ValidadorPropostaAcordoHandler {

  @Override
  public @NotNull List<ErroValidacao> validar(PropostaAcordo propostaAcordo) {
    final List<ErroValidacao> erros = super.validar(propostaAcordo);

    final List<DetalhePropostaAcordo> detalhes = propostaAcordo.getDetalhes();
    final List<DetalhePropostaAcordo> detalhesComErro = detalhes.stream()
                                                                .filter(d -> !Objects.equals(d.getTipoRegistro(), "2"))
                                                                .collect(toList());

    detalhesComErro.stream()
                   .map(d -> new ErroValidacao(CodigoRetornoErro.ERRO_PREENCHIMENTO_TIPO_REGISTRO, "O Detalhe " + d.toString() + " está com o Tipo de Registro diferente de 2."))
                   .forEach(erros::add);

    return erros;
  }
}
