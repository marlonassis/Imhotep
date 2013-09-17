package br.com.imhotep.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_autoriza_painel_profissional")
public class AutorizaPainelProfissional implements Serializable {
	private static final long serialVersionUID = 2304474896114749280L;
	
	private int idAutorizaPainelProfissional;
	private Profissional profissional;
	private Painel painel;
	private Profissional profissionalInsercao;
	private Date dataInsercao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_autoriza_painel_profission_id_autoriza_painel_profission_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_autoriza_painel_profissional", unique = true, nullable = false)
	public int getIdAutorizaPainelProfissional() {
		return this.idAutorizaPainelProfissional;
	}
	
	public void setIdAutorizaPainelProfissional(int idAutorizaPainelProfissional){
		this.idAutorizaPainelProfissional = idAutorizaPainelProfissional;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_painel")
	public Painel getPainel(){
		return painel;
	}
	
	public void setPainel(Painel painel){
		this.painel = painel;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional")
	public Profissional getProfissional(){
		return profissional;
	}
	
	public void setProfissional(Profissional profissional){
		this.profissional = profissional;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_insercao")
	public Profissional getProfissionalInsercao(){
		return profissionalInsercao;
	}
	
	public void setProfissionalInsercao(Profissional profissionalInsercao){
		this.profissionalInsercao = profissionalInsercao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_insercao")
	public Date getDataInsercao() {
		return dataInsercao;
	}
	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof AutorizaPainelProfissional))
			return false;
		
		return ((AutorizaPainelProfissional)obj).getIdAutorizaPainelProfissional() == this.idAutorizaPainelProfissional;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + profissional.hashCode() + painel.hashCode();
	}

	@Override
	public String toString() {
		return profissional.getNome().concat(" - ").concat(painel.getDescricao());
	}
	
}
