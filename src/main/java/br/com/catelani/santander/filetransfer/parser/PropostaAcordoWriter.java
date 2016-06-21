package br.com.catelani.santander.filetransfer.parser;

import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public interface PropostaAcordoWriter {

  /**
   * <p>Gera o arqiuvo de proposta e escreve na stream de saida {@code os}.</p>
   * <p><b>Obs Importante: </b> Esse método <b>não</b> fecha a stream de saida {@code os}.</p>
   *
   * @param propostaAcordo Proposta de Acordo para gerar o arquivo.
   * @param os             Stream de saida para escrever o arquivo.
   * @throws IOException          Caso tenha algum problema na Stream de Saida
   * @throws NullPointerException se a {@code propostaAcordo} ou {@code os} forem nulos.
   */
  void gerarPropostaAcordo(@NotNull PropostaAcordo propostaAcordo, @NotNull OutputStream os) throws IOException;
}
