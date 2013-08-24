package br.com.imhotep.entidade;

import java.io.Serializable;
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
@Table(name = "tb_avaliacao_psicologica_log")
public class AvaliacaoPsicologicaLog implements Serializable {
	private static final long serialVersionUID = 1596783492749555059L;
	
	private int idAvaliacaoPsicologicaLog;
	private AvaliacaoPsicologica avaliacaoPsicologica;
	private String textoOriginal;
	private String textoAlterado;
	private Date dataModificacao;
	private Profissional profissionalModificador;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_avaliacao_psicologica_log_id_avaliacao_psicologica_log_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_avaliacao_psicologica_log", unique = true, nullable = false)
	public int getIdAvaliacaoPsicologicaLog() {
		return this.idAvaliacaoPsicologicaLog;
	}
	
	public void setIdAvaliacaoPsicologicaLog(int idAvaliacaoPsicologicaLog){
		this.idAvaliacaoPsicologicaLog = idAvaliacaoPsicologicaLog;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_avaliacao_psicologica")
	public AvaliacaoPsicologica getAvaliacaoPsicologica(){
		return avaliacaoPsicologica;
	}
	
	public void setAvaliacaoPsicologica(AvaliacaoPsicologica avaliacaoPsicologica){
		this.avaliacaoPsicologica = avaliacaoPsicologica;
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
		if(!(obj instanceof AvaliacaoPsicologicaLog))
			return false;
		
		return ((AvaliacaoPsicologicaLog)obj).getIdAvaliacaoPsicologicaLog() == this.idAvaliacaoPsicologicaLog;
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
