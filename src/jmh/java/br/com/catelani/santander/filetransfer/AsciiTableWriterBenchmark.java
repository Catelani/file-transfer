package br.com.catelani.santander.filetransfer;

import br.com.catelani.santander.filetransfer.domain.PropostaAcordo;
import br.com.catelani.santander.filetransfer.parser.FileTransferModule;
import br.com.catelani.santander.filetransfer.parser.PropostaAcordoReader;
import br.com.catelani.santander.filetransfer.parser.PropostaAcordoWriter;
import br.com.catelani.santander.filetransfer.qualifiers.AsciiTable;
import com.google.inject.Guice;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * @author Kennedy Oliveira
 */
@State(Scope.Thread)
public class AsciiTableWriterBenchmark {

  @Inject
  @AsciiTable
  private PropostaAcordoWriter propostaAcordoWriter;

  @Inject
  private PropostaAcordoReader propostaAcordoReader;

  private PropostaAcordo propostaAcordo;

  @Setup
  public void setup() throws IOException {
    Guice.createInjector(new FileTransferModule()).injectMembers(this);
    final InputStream arquivoProposta = ClassLoader.getSystemResourceAsStream("exemplos/ESCPA0001.dat");
    this.propostaAcordo = propostaAcordoReader.parse(arquivoProposta);
  }

  @Benchmark
  @BenchmarkMode({Mode.AverageTime, Mode.Throughput})
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  @Warmup(iterations = 20)
  @Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
  @Fork(1)
  public String asciiTable() throws IOException {
    final ByteArrayOutputStream saida = new ByteArrayOutputStream();
    propostaAcordoWriter.gerarPropostaAcordo(propostaAcordo, saida);
    return new String(saida.toByteArray());
  }

  // runner
  public static void main(String[] args) throws RunnerException {
    final Options options = new OptionsBuilder()
      .include(AsciiTableWriterBenchmark.class.getSimpleName())
//      .addProfiler(CompilerProfiler.class)
      .build();

    new Runner(options).run();
  }
}
