package br.com.ControleDispensacao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_tipo_conselho")
public class TipoConselho {
	private int idTipoConselho;
	private String descricao;
	private String sigla;
	
	@Id
	@GeneratedValue
	@Column(name = "id_tipo_conselho")
	public int getIdTipoConselho() {
		return this.idTipoConselho;
	}
	
	public void setIdTipoConselho(int idTipoConselho){
		this.idTipoConselho = idTipoConselho;
	}
	
	@Column(name = "ds_descricao")
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "ds_sigla", length=10)
	public String getSigla() {
		return this.sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	@Transient
	public String getDescricaoSigla(){
		return descricao.concat(" - ").concat(sigla);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof TipoConselho))
			return false;
		
		return ((TipoConselho)obj).getIdTipoConselho() == this.idTipoConselho;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + descricao.hashCode();
	}

	@Override
	public String toString() {
		return descricao;
	}
}
