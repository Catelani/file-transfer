package br.com.catelani.santander.filetransfer.parser;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * Decorator para impedir que a Stream de saida seja fechada pelo writer.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
class NonCloseableOutputStream extends OutputStream {
  private OutputStream delegate;

  NonCloseableOutputStream(OutputStream os) {
    Objects.requireNonNull(os, "OutpuStream null");
    this.delegate = os;
  }

  @Override
  public void write(byte[] b) throws IOException {
    delegate.write(b);
  }

  @Override
  public void write(byte[] b, int off, int len) throws IOException {
    delegate.write(b, off, len);
  }

  @Override
  public void flush() throws IOException {
    delegate.flush();
  }

  @Override
  public void close() throws IOException {
    // não faz nada
  }

  @Override
  public void write(int b) throws IOException {
    delegate.write(b);
  }
}
