package br.com.catelani.santander.filetransfer.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Códigos de retorno de erro da financeira.
 *
 * @author Kennedy Oliveira
 * @since 1.0.0
 */
public enum CodigoRetornoErro {

  /*
  * Erros de preenchimento incorreto
  * */
  /**
   * Se o tipo de registro for incorreto, para Proposta de Acordo deve ser {@code 0}.
   */
  ERRO_PREENCHIMENTO_TIPO_REGISTRO("301"),
  /**
   * Se o tipo da interface não for correto, para Proposta de Acordo dee ser {@code PA} {@link TipoInterface#PROPOSTA_DE_ACORDO}.
   */
  ERRO_PREENCHIMENTO_TIPO_DE_INTERFACE("302"),
  /**
   * Se o número da caixa postal não for credenciada ao prestador.
   */
  ERRO_PREENCHIMENTO_NUMERO_CAIXA_POSTAL("303"),
  /**
   * Acontece quando o código GCA não foi preenchido com 4.
   */
  ERRO_PREENCHIMENTO_CODIGO_GCA_PRESTADOR("304"),
  /**
   * O nome do prestador não pode ser vazio.
   */
  ERRO_PREENCHIMENTO_NOME_DO_PRESTADOR("305"),
  ERRO_PREENCHIMENTO_DATA_DA_REMESSA("306"),
  ERRO_PREENCHIMENTO_HORA_DA_REMESSA("307"),
  /**
   * Se o número do lote não for númerico e maior que 0.
   */
  ERRO_PREENCHIMENTO_NUMERO_DO_LOTE("308"),
  /**
   * Prestação de serviço diferente das disponieis em {@link TipoPrestacaoServico}.
   */
  ERRO_PREENCHIMENTO_TIPO_DE_PRESTACAO_SERVICO("309"),
  /**
   * Se o Tipo de Carteira for nulo, ou se para uma negociação algum dos detalhes tiver o tipo de carteira diferente do outro. Todos devem ser iguais.
   */
  ERRO_PREENCHIMENTO_TIPO_DE_CARTEIRA("310"),
  /**
   * A Quantidade de Detalhes {@link PropostaAcordo#getDetalhes()} for diferentes de {@link DadosPrestador#getQuantidadeSolicitacoes()}.
   */
  ERRO_PREENCHIMENTO_QUATIDADE_DE_SOLICITACOES("311"),
  /**
   * O Numero de Sequencia do Cabeçalho de header for diferente de {@code 00001}
   */
  ERRO_PREENCHIMENTO_SEQUENCIA("312"),
  ERRO_PREENCHIMENTO_NUMERO_DO_CONTRATO("313"),
  /**
   * Deve estar aberta no sistema Produto (FIN / LSG)
   */
  ERRO_PREENCHIMENTO_PARCELA_NEGOCIACAO_INICIAL("314"),
  /**
   * A parcela deve estar aberta no sistema Produto (FIN / LSG) e não pode menor que a Parcela Inicial.
   */
  ERRO_PREENCHIMENTO_PARCELA_NEGOCIACAO_FINAL("315"),
  ERRO_PREENCHIMENTO_QUANTIDADE_DIAS_ATRASO_PARCELA_ACORDO("316"),
  ERRO_PREENCHIMENTO_VALOR_PRINCIPAL("317"),
  ERRO_PREENCHIMENTO_INDICADOR_CONTRATO_AJUIZADO("318"),
  ERRO_PREENCHIMENTO_VALOR_SALDO_GCA("319"),
  ERRO_PREENCHIMENTO_VALOR_SALDO_GCA_DISPENSADO("320"),
  ERRO_PREENCHIMENTO_VALOR_ACORDO_NEGOCIADO_COM_CLIENTE("321"),
  ERRO_PREENCHIMENTO_VALOR_ACORDO_NEGOCIADO_SEM_HONORARIOS("322"),
  ERRO_PREENCHIMENTO_CODIGO_MOTIVO_CAMPANHA_RPC("323"),
  ERRO_PREENCHIMENTO_INDICADOR_DE_EXCECAO_DA_CAMPANHA("324"),
  ERRO_PREENCHIMENTO_TIPO_DE_NEGOCIACAO("325"),
  ERRO_PREENCHIMENTO_VALOR_HONORARIO_MAXIMO_CAMPANHA("326"),
  ERRO_PREENCHIMENTO_DESCONTO_DE_HONORARIOS("327"),
  ERRO_PREENCHIMENTO_QUANTIDADE_TOTAL_PARCELAS_ACORDO("328"),
  ERRO_PREENCHIMENTO_SEQUENCIA_PARCELA_ACORDO("329"),
  ERRO_PREENCHIMENTO_DATA_VENCIMENTO_PARCELA_ACORDO("330"),
  ERRO_PREENCHIMENTO_VALOR_TOTAL_PARCELA_ACORDO("331"),
  ERRO_PREENCHIMENTO_VALOR_GCA_PARCELA_ACORDO("332"),
  ERRO_PREENCHIMENTO_HONORARIO_MAXIMO_PARCELA_ACORDO("333"),

  /*
  *
  *
   */
  QUANTIDADE_SOLICITACOES_HEADER_DIVERGENTE_ACORDOS_ENVIADOS("334"),
  NUMERO_DE_SEQUENCIA_FORA_DE_ORDEM("335"),
  NUMERO_DE_LOTE_FORA_DE_SEQUENCIA_ESPERADA("336"),
  CODIGO_GCA_PRESTADOR_NAO_ENCONTRADO_OU_INATIVO("337"),
  EXITE_ACORDO_PARA_PARCELA_DO_CONTRATO("338"),
  BOLETO_ATIVO_PARA_PARCELA_DO_ACORDO("339"),
  FORA_DA_ALCADA_NORMAL_DA_CAMPANHA("340"),
  QUANTIDADE_PARCELAS_ACORDO_EXCEDEU_LIMITE_DA_CAMPANHA("341"),
  DATA_VENCIMENTO_MAIOR_ATRASO_NAO_CONFERE("342"),
  SEQUENCIA_DA_PARCELA_DE_ACORDO_FORA_DE_ORDEM("343"),
  SALDO_GCA_PENDENTE_CADASTRADO_APOS_CRIACAO_DO_ACORDO("344"),
  INCOMPATIBILIDADE_COM_REGRAS_DA_CAMPANHA_RPC("345"),

  /*
  *
  * */
  ACORDO_NAO_AVALIADO_DEVIDO_A_ERRO_NO_HEADER_DA_INTERFACE("399");

  private final static Map<String, CodigoRetornoErro> mapByCodigo;

  static {
    mapByCodigo = new HashMap<>();

    for (CodigoRetornoErro codigoRetornoErro : values()) {
      mapByCodigo.put(codigoRetornoErro.codigoErro, codigoRetornoErro);
    }
  }

  private final String codigoErro;

  CodigoRetornoErro(String codigoErro) {
    this.codigoErro = codigoErro;
  }

  public static CodigoRetornoErro getByCodigoErro(String codigo) {
    return mapByCodigo.get(codigo);
  }

  @Override
  public String toString() {
    return codigoErro;
  }
}
