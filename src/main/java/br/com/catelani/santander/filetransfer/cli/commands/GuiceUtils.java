package br.com.catelani.santander.filetransfer.cli.commands;

import br.com.catelani.santander.filetransfer.parser.FileTransferModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;

/**
 * Métodos utilitários para manipulação do Guice.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Slf4j
class GuiceUtils {

  private static Injector injector;

  private GuiceUtils() {}

  /**
   * <p><b>Esse método NÃO é thread-safe!</b></p>
   * <p>É um detalhe de implementação, só deve ser utilizado pelo modulo de linha de comando da biblioteca, pois ele é single thread e nesse caso não teria problemas.</p>
   *
   * @return {@link Injector}
   */
  public static Injector getInjector() {
    if (injector == null) {
      log.debug("Iniciando o Google Guice...");
      injector = Guice.createInjector(new FileTransferModule());
    }

    return injector;
  }
}
