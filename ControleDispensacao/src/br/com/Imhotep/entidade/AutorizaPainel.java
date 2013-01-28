package br.com.Imhotep.entidade;

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
@Table(name = "tb_autoriza_painel")
public class AutorizaPainel {
	private int idAutorizaPainel;
	private Painel painel;
	private Especialidade especialidade;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_autoriza_painel_id_autoriza_painel_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_autoriza_painel", unique = true, nullable = false)
	public int getIdAutorizaPainel() {
		return idAutorizaPainel;
	}
	public void setIdAutorizaPainel(int idAutorizaPainel) {
		this.idAutorizaPainel = idAutorizaPainel;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_painel")
	public Painel getPainel() {
		return painel;
	}
	public void setPainel(Painel painel) {
		this.painel = painel;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_especialidade")
	public Especialidade getEspecialidade() {
		return especialidade;
	}
	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof AutorizaPainel))
			return false;
		
		return ((AutorizaPainel)obj).getIdAutorizaPainel() == this.idAutorizaPainel;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + painel.hashCode() + especialidade.hashCode();
	}

	@Override
	public String toString() {
		return especialidade.getDescricao().concat(" - ").concat(painel.getUrl());
	}
}
