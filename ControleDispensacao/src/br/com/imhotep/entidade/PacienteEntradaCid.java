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
@Table(name = "tb_paciente_entrada_cid")
public class PacienteEntradaCid {
	
	private int idPacienteEntradaCid;
	private PacienteEntrada pacienteEntrada;
	private Cid cid;
	private Profissional profissionalInsercao;
	private Date dataInsercao;
	
	public PacienteEntradaCid(){
		super();
	}
	
	public PacienteEntradaCid(Cid cid, PacienteEntrada pacienteEntrada){
		this.cid = cid;
		this.pacienteEntrada = pacienteEntrada;
	}
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_paciente_entrada_cid_id_paciente_entrada_cid_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_paciente_entrada_cid", unique = true, nullable = false)
	public int getIdPacienteEntradaCid() {
		return idPacienteEntradaCid;
	}

	public void setIdPacienteEntradaCid(int idPacienteEntradaCid) {
		this.idPacienteEntradaCid = idPacienteEntradaCid;
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
	@JoinColumn(name = "id_cid")
	public Cid getCid() {
		return cid;
	}
	public void setCid(Cid cid) {
		this.cid = cid;
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
		if(!(obj instanceof PacienteEntradaCid))
			return false;
		
		return ((PacienteEntradaCid)obj).getIdPacienteEntradaCid() == this.idPacienteEntradaCid;
	}

	@Override
	public String toString() {
		return pacienteEntrada.getPaciente().getNome();
	}
}
