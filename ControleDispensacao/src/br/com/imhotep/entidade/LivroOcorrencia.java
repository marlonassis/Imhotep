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
@Table(name = "tb_livro_ocorrencia")
public class LivroOcorrencia {

	private int idLivroOcorrencia;
	private Unidade unidadeOcorrencia;
	private String assunto;
	private String texto;
	private Profissional profissionalOcorrencia;
	private Date dataCadastro;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_livro_ocorrencia_id_livro_ocorrencia_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_livro_ocorrencia", unique = true, nullable = false)
	public int getIdLivroOcorrencia() {
		return idLivroOcorrencia;
	}
	public void setIdLivroOcorrencia(int idLivroOcorrencia) {
		this.idLivroOcorrencia = idLivroOcorrencia;
	}
	
	@Column(name = "cv_assunto")
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	
	@Column(name = "tx_texto")
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_cadastro")
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_ocorrencia")
	public Unidade getUnidadeOcorrencia() {
		return unidadeOcorrencia;
	}
	public void setUnidadeOcorrencia(Unidade unidadeOcorrencia) {
		this.unidadeOcorrencia = unidadeOcorrencia;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_ocorrencia")
	public Profissional getProfissionalOcorrencia() {
		return profissionalOcorrencia;
	}
	public void setProfissionalOcorrencia(Profissional profissionalOcorrencia) {
		this.profissionalOcorrencia = profissionalOcorrencia;
	}
	
}
