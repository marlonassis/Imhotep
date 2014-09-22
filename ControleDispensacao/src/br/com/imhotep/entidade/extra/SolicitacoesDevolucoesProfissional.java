package br.com.imhotep.entidade.extra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.com.imhotep.comparador.SolicitacaoDevolucoesProfissionalItemComparador;

public class SolicitacoesDevolucoesProfissional {
	private int idUnico;
	private Date data;
	private String unidade;
	private String status;
	private String codigo;
	private String justificativa;
	private String tipo;
	private List<SolicitacoesDevolucoesProfissionalItem> itens = new ArrayList<SolicitacoesDevolucoesProfissionalItem>();
	
	public SolicitacoesDevolucoesProfissional(){
		super();
	}
	
	public SolicitacoesDevolucoesProfissional(int idUnico, Date data,
			String unidade, String status, String codigo,
			String justificativa, String tipo,
			List<SolicitacoesDevolucoesProfissionalItem> itens) {
		super();
		this.idUnico = idUnico;
		this.data = data;
		this.unidade = unidade;
		this.status = status;
		this.codigo = codigo;
		this.justificativa = justificativa;
		this.tipo = tipo;
		this.itens = itens;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<SolicitacoesDevolucoesProfissionalItem> getItens() {
		if(itens != null && !itens.isEmpty()){
			Collections.sort(itens, new SolicitacaoDevolucoesProfissionalItemComparador());
		}
		return itens;
	}
	public void setItens(List<SolicitacoesDevolucoesProfissionalItem> itens) {
		this.itens = itens;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public int getIdUnico() {
		return idUnico;
	}
	public void setIdUnico(int idUnico) {
		this.idUnico = idUnico;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + idUnico;
		result = prime * result + ((itens == null) ? 0 : itens.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((unidade == null) ? 0 : unidade.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SolicitacoesDevolucoesProfissional other = (SolicitacoesDevolucoesProfissional) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (idUnico != other.idUnico)
			return false;
		if (itens == null) {
			if (other.itens != null)
				return false;
		} else if (!itens.equals(other.itens))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (unidade == null) {
			if (other.unidade != null)
				return false;
		} else if (!unidade.equals(other.unidade))
			return false;
		return true;
	}
	public String getJustificativa() {
		return justificativa;
	}
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
