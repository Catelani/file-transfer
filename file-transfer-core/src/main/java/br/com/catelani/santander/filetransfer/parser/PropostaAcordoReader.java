package br.com.catelani.santander.filetransfer.parser;

import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Leitor de Proposta de Acordos.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public interface PropostaAcordoReader {

  /**
   * <p>Le o arquivo vindo de uma Stream e parseia para gerar um {@link PropostaAcordo}.</p>
   * <p>O {@code is} não precisa ser necessariamente de um arquivo, desde que represente os dados corretamente.</p>
   * <p>O Charset utilizado para ler o {@code is} é o {@link StandardCharsets#UTF_8}.</p>
   * <p>Esse método é o mesmo que chamar o {@link #parse(InputStream, Charset)} passando {@code null} no parametro {@code charset}.</p>
   *
   * @param is {@link InputStream} do arquivo file transfer do banco.
   * @return Um {@link PropostaAcordo} baseado nos dados do arquivo.
   * @throws IllegalStateException Se não conseguir ler nada da Stream de Entrada {@code is}
   * @throws IOException Caso der algum problema na leitura da Stream de Entrada {@code is}
   */
  PropostaAcordo parse(@NotNull InputStream is) throws IOException;

  /**
   * <p>Le o arquivo vindo de uma Stream e parseia para gerar um {@link PropostaAcordo}.</p>
   * <p>O {@code is} não precisa ser necessariamente de um arquivo, desde que represente os dados corretamente.</p>
   *
   * @param is      {@link InputStream} do arquivo file transfer do banco.
   * @param charset Charset a ser utilizado na leitura do arquivo, caso nenhum seja informado o padrão é {@link StandardCharsets#UTF_8}
   * @return Um {@link PropostaAcordo} baseado nos dados do arquivo.
   * @throws IllegalStateException Se não conseguir ler nada da Stream de Entrada {@code is}
   * @throws IOException Caso der algum problema na leitura da Stream de Entrada {@code is}
   */
  PropostaAcordo parse(@NotNull InputStream is, @Nullable Charset charset) throws IOException;
}
