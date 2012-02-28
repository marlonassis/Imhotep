package br.com.ControleDispensacao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_tipo_profissional")
public class TipoProfissional {
	private int idTipoProfissional;
	private TipoConselho tipoConselho;
	private String descricao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_tipo_profissional_id_tipo_profissional_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_tipo_profissional", unique = true, nullable = false)
	public int getIdTipoProfissional() {
		return this.idTipoProfissional;
	}
	
	public void setIdTipoProfissional(int idTipoProfissional){
		this.idTipoProfissional = idTipoProfissional;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_conselho")
	public TipoConselho getTipoConselho(){
		return tipoConselho;
	}
	
	public void setTipoConselho(TipoConselho tipoConselho){
		this.tipoConselho = tipoConselho;
	}
	
	@Column(name = "ds_descricao", length = 120)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof TipoProfissional))
			return false;
		
		return ((TipoProfissional)obj).getIdTipoProfissional() == this.idTipoProfissional;
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
