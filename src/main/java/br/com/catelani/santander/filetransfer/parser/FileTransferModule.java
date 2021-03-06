package br.com.catelani.santander.filetransfer.parser;

import br.com.catelani.santander.filetransfer.qualifiers.AsciiTable;
import com.google.inject.AbstractModule;

/**
 * Modulo que relaciona todas as dependencias a sua implementações padrões.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public class FileTransferModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ListaErroFactory.class).to(ListaErroFactoryImpl.class);
    bind(CabecalhoPropostaAcordoFactory.class).to(CabecalhoPropostaAcordoFactoryImpl.class);
    bind(DetalhePropostaFactory.class).to(DetalhePropostaFactoryImpl.class);
    bind(PropostaAcordoReader.class).to(PropostaAcordoReaderImpl.class);
    bind(PropostaAcordoWriter.class).to(PropostaAcordoWriterImpl.class);

    // Implementação alternativa que gera ascii tables
    bind(PropostaAcordoWriter.class).annotatedWith(AsciiTable.class).to(PropostaAcordoAsciiTableWriter.class);
  }
}
