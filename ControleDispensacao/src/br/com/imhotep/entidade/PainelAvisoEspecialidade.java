package br.com.imhotep.entidade;

import java.io.Serializable;

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
@Table(name = "tb_painel_aviso_especialidade")
public class PainelAvisoEspecialidade implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int idPainelAvisoEspecialidade;
	private PainelAviso painelAviso;
	private Especialidade especialidade;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_painel_aviso_especialidade_id_painel_aviso_especialidade_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_painel_aviso_especialidade", unique = true, nullable = false)
	public int getIdPainelAvisoEspecialidade() {
		return idPainelAvisoEspecialidade;
	}
	public void setIdPainelAvisoEspecialidade(int idPainelAvisoEspecialidade) {
		this.idPainelAvisoEspecialidade = idPainelAvisoEspecialidade;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_painel_aviso")
	public PainelAviso getPainelAviso() {
		return painelAviso;
	}
	public void setPainelAviso(PainelAviso painelAviso) {
		this.painelAviso = painelAviso;
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
		if(!(obj instanceof PainelAvisoEspecialidade))
			return false;
		
		return ((PainelAvisoEspecialidade)obj).getIdPainelAvisoEspecialidade() == this.idPainelAvisoEspecialidade;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + painelAviso.hashCode() + especialidade.hashCode();
	}

	@Override
	public String toString() {
		return painelAviso.toString();
	}
}
