package br.com.catelani.santander.filetransfer.validador.api;

import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Validador de Proposta Acordo, aplica as validações e devolve uma lista de erros caso tiver algum com os mesmos códigos de erro que o banco
 * devolveria, se nenhum erro for encontrado, retorna uma lista vazia.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public interface ValidadorPropostaAcordo {

  @NotNull
  List<ErroValidacao> validar(PropostaAcordo propostaAcordo);
}
