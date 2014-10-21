package br.com.imhotep.entidade.extra;

public class SolicitacoesDevolucoesProfissionalItem {
	
	private int idUnico;
	private String codigo;
	private String descricao;
	private int quantidadeSolicitada;
	private String justificativa;
	private String status;
	
	public SolicitacoesDevolucoesProfissionalItem(){
		super();
	}
	
	public SolicitacoesDevolucoesProfissionalItem(int idUnico, String codigo,
			String descricao, int quantidadeSolicitada, String justificativa,
			String status) {
		super();
		this.idUnico = idUnico;
		this.codigo = codigo;
		this.descricao = descricao;
		this.quantidadeSolicitada = quantidadeSolicitada;
		this.justificativa = justificativa;
		this.status = status;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public int getQuantidadeSolicitada() {
		return quantidadeSolicitada;
	}
	public void setQuantidadeSolicitada(int quantidadeSolicitada) {
		this.quantidadeSolicitada = quantidadeSolicitada;
	}
	public int getIdUnico() {
		return idUnico;
	}
	public void setIdUnico(int idUnico) {
		this.idUnico = idUnico;
	}
	public String getJustificativa() {
		return justificativa;
	}
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
