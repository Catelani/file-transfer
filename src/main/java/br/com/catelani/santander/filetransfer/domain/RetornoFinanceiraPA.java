package br.com.catelani.santander.filetransfer.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * <p>Informações de retorno da financeira referentes a uma proposta.</p>
 * <p>Essas informações ficam em um Cabecalho.</p>
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Data
public class RetornoFinanceiraPA {

  /**
   * <p>Data do processamento da Financeira.</p>
   * <p>Formato: {@code AAAAMMDD}</p>
   */
  private LocalDate dataRetorno;

  /**
   * <p>Hora do processamento da Financeira.</p>
   * <p>Formato: {@code HHMMSS}</p>
   */
  private LocalTime horaRetorno;

  /**
   * Status geral do retorno da financeira.
   */
  private TipoRetornoFinanceira tipoRetornoFinanceira;

  /**
   * Status da validação pela financeira.
   */
  private CodigoRetornoValidacao codigoRetornoValidacao;

  /**
   * <p>Lista de erros retornados pela Financeira, este campo pode ter até 10 códigos de erros.</p>
   */
  private List<CodigoRetornoErro> erros;
}
