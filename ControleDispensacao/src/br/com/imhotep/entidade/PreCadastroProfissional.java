package br.com.imhotep.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_pre_cadastro_profissional")
public class PreCadastroProfissional {
	private int idPreCadastroProfissional;
	private String nome;
	private String observacao;
	private Date proximoPlantao;
	private String telefone;
	private String unidade;
	private Date dataInsercao;
	private Date dataEfetivacao;
	private boolean cadastroEfetivado;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_pre_cadastro_profissional_id_pre_cadastro_profissional_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_pre_cadastro_profissional", unique = true, nullable = false)
	public int getIdPreCadastroProfissional() {
		return idPreCadastroProfissional;
	}
	public void setIdPreCadastroProfissional(int idPreCadastroProfissional) {
		this.idPreCadastroProfissional = idPreCadastroProfissional;
	}
	
	@Column(name="cv_nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name="tx_observacao")
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_proximo_plantao")
	public Date getProximoPlantao() {
		return proximoPlantao;
	}
	public void setProximoPlantao(Date proximoPlantao) {
		this.proximoPlantao = proximoPlantao;
	}
	
	@Column(name="cv_telefone")
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@Column(name="cv_unidade")
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_data_insercao")
	public Date getDataInsercao() {
		return dataInsercao;
	}
	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_data_efetivacao")
	public Date getDataEfetivacao() {
		return dataEfetivacao;
	}
	public void setDataEfetivacao(Date dataEfetivacao) {
		this.dataEfetivacao = dataEfetivacao;
	}
	
	@Column(name="bl_cadastro_efetivado")
	public boolean getCadastroEfetivado(){
		return cadastroEfetivado;
	}
	
	public void setCadastroEfetivado(boolean cadastroEfetivado){
		this.cadastroEfetivado = cadastroEfetivado;
	}

}
