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
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author Kennedy Oliveira
 */
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Thread)
public class AsciiTableWriterBenchmark {

  @Inject
  @AsciiTable
  private PropostaAcordoWriter propostaAcordoWriter;

  @Inject
  private PropostaAcordoReader propostaAcordoReader;

  private PropostaAcordo propostaAcordo;

  @Param({"exemplos/ESCPA0001.dat", "exemplos/ESCPA0001_100.dat"})
  public String caminhoArquivo;

  @Setup
  public void setup() throws IOException {
    Guice.createInjector(new FileTransferModule()).injectMembers(this);
    final InputStream arquivoProposta = ClassLoader.getSystemResourceAsStream(caminhoArquivo);
    this.propostaAcordo = propostaAcordoReader.parse(arquivoProposta);
  }

  @Benchmark
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  public String average() throws IOException {
    return gerarAsciiTable();
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public String throughput() throws IOException {
    return gerarAsciiTable();
  }

  @Benchmark
  @BenchmarkMode(Mode.SampleTime)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  public String sampleTime() throws IOException {
    return gerarAsciiTable();
  }

  private String gerarAsciiTable() throws IOException {
    final ByteArrayOutputStream saida = new ByteArrayOutputStream();
    propostaAcordoWriter.gerarPropostaAcordo(propostaAcordo, saida);
    return new String(saida.toByteArray());
  }

  // runner
  public static void main(String[] args) throws RunnerException {
    new Scanner(System.in).next();
    final Options options = new OptionsBuilder()
      .include(AsciiTableWriterBenchmark.class.getSimpleName())
//      .addProfiler(CompilerProfiler.class)
      .build();

    new Runner(options).run();
  }
}
