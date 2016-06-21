package br.com.catelani.santander.filetransfer.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Dados do Escritório de Cobrança.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Data
public class DadosPrestador {

  /**
   * Número da caixa postal do ESCOB, esse número será utilizado para geração dos nomes dos arquivos também.
   * Atualmente tem o tamanho de 4 caracteres.
   */
  private String caixaPostal;

  /**
   * Código GCA do escritório de cobrança.
   */
  private String codigoGCA;

  /**
   * Nome do Escritório de Cobrança.
   */
  private String nome;

  /**
   * Data que a remessa foi enviada ao banco, no formato {@code AAAAMMDD}.
   */
  private LocalDate dataRemessa;

  /**
   * Hora que a remessa foi enviada ao banco, no formato {@code HHMMSS}.
   */
  private LocalTime horaRemessa;

  /**
   * Número sequencial, indice do lote, deve ser mantido pelo escritório.
   * Se esse número for repetido durante o envio, a proposta será rejeitada.
   */
  private long numeroLote;

  /**
   * Tipo de prestação de serviço que o escritório de cobrança faz ao banco.
   */
  private TipoPrestacaoServico tipoPrestacaoServico;

  /**
   * <p>Total de acordos que serão enviados em uma proposta.</p>
   * <p>Esse total será utilizado pelo sistema do banco para ler as linhas, e será calculado automaticamente pela API.</p>
   */
  private long quantidadeSolicitacoes;
}
