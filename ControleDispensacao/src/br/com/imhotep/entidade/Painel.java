package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_painel")
public class Painel implements Serializable {
	
	private static final long serialVersionUID = 5609912718447999672L;
	
	private int idPainel;
	private String url;
	private String descricao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_painel_id_painel_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_painel", unique = true, nullable = false)
	public int getIdPainel() {
		return idPainel;
	}
	public void setIdPainel(int idPainel) {
		this.idPainel = idPainel;
	}
	
	@Column(name = "cv_url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "cv_descricao")
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Painel))
			return false;
		
		return ((Painel)obj).getIdPainel() == this.idPainel;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + url.hashCode();
	}

	@Override
	public String toString() {
		return descricao;
	}
}
