package br.com.imhotep.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_procedimento_saude")
public class ProcedimentoSaude {
	
	private int idProcedimentoSaude;
	private String nome;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_procedimento_saude_id_procedimento_saude_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_procedimento_saude", unique = true, nullable = false)
	public int getIdProcedimentoSaude() {
		return idProcedimentoSaude;
	}
	public void setIdProcedimentoSaude(int idProcedimentoSaude) {
		this.idProcedimentoSaude = idProcedimentoSaude;
	}
	
	@Column(name = "cv_nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof ProcedimentoSaude))
			return false;
		
		return ((ProcedimentoSaude)obj).getIdProcedimentoSaude() == this.idProcedimentoSaude;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + nome.hashCode();
	}

	@Override
	public String toString() {
		return nome;
	}
}
