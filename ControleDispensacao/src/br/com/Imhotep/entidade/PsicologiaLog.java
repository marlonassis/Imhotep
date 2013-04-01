package br.com.Imhotep.entidade;

import java.text.SimpleDateFormat;
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
@Table(name = "tb_psicologia_log")
public class PsicologiaLog {
	
	private int idPsicologiaLog;
	private Psicologia psicologia;
	private String textoOriginal;
	private String textoAlterado;
	private Date dataModificacao;
	private Profissional profissionalModificador;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_psicologia_log_id_psicologia_log_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_psicologia_log", unique = true, nullable = false)
	public int getIdPsicologiaLog() {
		return this.idPsicologiaLog;
	}
	
	public void setIdPsicologiaLog(int idPsicologiaLog){
		this.idPsicologiaLog = idPsicologiaLog;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_psicologia")
	public Psicologia getPsicologia(){
		return psicologia;
	}
	
	public void setPsicologia(Psicologia psicologia){
		this.psicologia = psicologia;
	}
	
	@Column(name = "tx_texto_original")
	public String getTextoOriginal(){
		return textoOriginal;
	}
	
	public void setTextoOriginal(String textoOriginal){
		this.textoOriginal = textoOriginal;
	}
	
	@Column(name = "tx_texto_alterado")
	public String getTextoAlterado(){
		return textoAlterado;
	}
	
	public void setTextoAlterado(String textoAlterado){
		this.textoAlterado = textoAlterado;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_modificacao")
	public Date getDataModificacao() {
		return dataModificacao;
	}
	
	public void setDataModificacao(Date dataModificacao) {
		this.dataModificacao = dataModificacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_modificador")
	public Profissional getProfissionalModificador(){
		return profissionalModificador;
	}
	
	public void setProfissionalModificador(Profissional profissionalModificador){
		this.profissionalModificador = profissionalModificador;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof PsicologiaLog))
			return false;
		
		return ((PsicologiaLog)obj).getIdPsicologiaLog() == this.idPsicologiaLog;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + dataModificacao.hashCode() + profissionalModificador.hashCode();
	}

	@Override
	public String toString() {
		return new SimpleDateFormat("dd/MM/yyyy").format(dataModificacao).concat(" - ").concat(profissionalModificador.getNome());
	}
	
}
