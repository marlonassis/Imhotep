package br.com.imhotep.entidade;

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
@Table(name = "tb_paciente_entrada_procedimento_saude")
public class PacienteEntradaProcedimentoSaude {
	
	private int idPacienteEntradaProcedimentoSaude;
	private PacienteEntrada pacienteEntrada;
	private ProcedimentoSaude procedimentoSaude;
	private Profissional profissionalInsercao;
	private Date dataInsercao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_paciente_entrada_procedime_id_paciente_entrada_procedime_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_paciente_entrada_procedimento_saude", unique = true, nullable = false)
	public int getIdPacienteEntradaProcedimentoSaude() {
		return idPacienteEntradaProcedimentoSaude;
	}
	public void setIdPacienteEntradaProcedimentoSaude(
			int idPacienteEntradaProcedimentoSaude) {
		this.idPacienteEntradaProcedimentoSaude = idPacienteEntradaProcedimentoSaude;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_paciente_entrada")
	public PacienteEntrada getPacienteEntrada() {
		return pacienteEntrada;
	}
	public void setPacienteEntrada(PacienteEntrada pacienteEntrada) {
		this.pacienteEntrada = pacienteEntrada;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_procedimento_saude")
	public ProcedimentoSaude getProcedimentoSaude() {
		return procedimentoSaude;
	}
	public void setProcedimentoSaude(ProcedimentoSaude procedimentoSaude) {
		this.procedimentoSaude = procedimentoSaude;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_insercao")
	public Profissional getProfissionalInsercao() {
		return profissionalInsercao;
	}
	public void setProfissionalInsercao(Profissional profissionalInsercao) {
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
		if(!(obj instanceof PacienteEntradaProcedimentoSaude))
			return false;
		
		return ((PacienteEntradaProcedimentoSaude)obj).getIdPacienteEntradaProcedimentoSaude() == this.idPacienteEntradaProcedimentoSaude;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + procedimentoSaude.hashCode() + pacienteEntrada.hashCode();
	}

	@Override
	public String toString() {
		return pacienteEntrada.getPaciente().getNome();
	}
}
