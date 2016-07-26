package br.com.catelani.santander.filetransfer.validador.api;

import br.com.catelani.santander.filetransfer.domain.CodigoRetornoErro;
import lombok.Data;

/**
 * Representa um erro de validação por parte da API.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
@Data
public class ErroValidacao {

  private String descricao;
  private CodigoRetornoErro codigoRetornoErro;

  public ErroValidacao(CodigoRetornoErro codigoRetornoErro, String descricao) {
    this.codigoRetornoErro = codigoRetornoErro;
    this.descricao = descricao;
  }
}
