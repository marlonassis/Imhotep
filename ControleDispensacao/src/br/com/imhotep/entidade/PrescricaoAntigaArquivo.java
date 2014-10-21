package br.com.imhotep.entidade;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_prescricao_antiga_arquivo")
public class PrescricaoAntigaArquivo {

	private int idPrescricaoAntigaArquivo;
	private byte[] arquivo;
	private String nomeArquivo;
	private long tamanho;
	private PrescricaoAntiga prescricaoAntiga;
	private Date dataInsercao;
	private Profissional profissionalInsercao;
	
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_prescricao_antiga_arquivo_id_prescricao_antiga_arquivo_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_prescricao_antiga_arquivo", unique = true, nullable = false)
	public int getIdPrescricaoAntigaArquivo() {
		return idPrescricaoAntigaArquivo;
	}
	public void setIdPrescricaoAntigaArquivo(int idPrescricaoAntigaArquivo) {
		this.idPrescricaoAntigaArquivo = idPrescricaoAntigaArquivo;
	}
	
	@Column(name = "cv_nome_arquivo")
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	
	@Lob
	@Basic( optional=false )
	@Column(name = "by_arquivo")
	public byte[] getArquivo() {
		return arquivo;
	}
	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}
	
	@Column(name = "bi_tamanho")
	public long getTamanho() {
		return tamanho;
	}
	public void setTamanho(long tamanho) {
		this.tamanho = tamanho;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_prescricao_antiga")
	public PrescricaoAntiga getPrescricaoAntiga() {
		return prescricaoAntiga;
	}
	public void setPrescricaoAntiga(PrescricaoAntiga prescricaoAntiga) {
		this.prescricaoAntiga = prescricaoAntiga;
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
		if(!(obj instanceof PrescricaoAntigaArquivo))
			return false;
		
		return ((PrescricaoAntigaArquivo)obj).getIdPrescricaoAntigaArquivo() == this.idPrescricaoAntigaArquivo;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + nomeArquivo.hashCode() + prescricaoAntiga.hashCode();
	}

	@Override
	public String toString() {
		return nomeArquivo;
	}
}
