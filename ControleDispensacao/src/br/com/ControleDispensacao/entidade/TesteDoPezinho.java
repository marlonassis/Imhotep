package br.com.ControleDispensacao.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ControleDispensacao.enums.TipoMotivosRejeicaoAmostraTestePezinhoEnum;
import br.com.ControleDispensacao.enums.TipoSexoEnum;
import br.com.ControleDispensacao.enums.TipoStatusEnum;

@Entity
@Table(name = "tb_teste_do_pezinho")
public class TesteDoPezinho {

	private int idTesteDoPezinho;
	private Integer amostra;
	private Boolean amostraValida;
	private TipoStatusEnum transfusao;
	private TipoStatusEnum prematuro;
	private String motivoAmostraInvalida;
	private Profissional profissionalCadastrante;
	private Date dataCadastro;
	private Date dataPku;
	private Date dataHb;
	private Date dataTsh;
	private Integer irt;
	private String hemoglobinas;
	private Integer tsh;
	private Integer pku;
	private UnidadeSaude unidadeSaude;
	private String telefone;
	private Cidade cidade;
	private String endereco;
	private Integer numeroCasa;
	private String nomeMae;
	private TipoStatusEnum tipoGemelar;
	private String gemelar;
	private TipoSexoEnum sexo;
	private Date dataNascimento;
	private String nome;
	private String tipo;
	private Integer numeroExame;
	private Long lote;
	private Date dataColeta;
	private Date dataResultado;
	private Integer numeroAmostra;
	private TipoMotivosRejeicaoAmostraTestePezinhoEnum motivoRejeicao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_teste_do_pezinho_id_teste_do_pezinho_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_teste_do_pezinho", unique = true, nullable = false)
	public int getIdTesteDoPezinho() {
		return idTesteDoPezinho;
	}
	public void setIdTesteDoPezinho(int idTesteDoPezinho) {
		this.idTesteDoPezinho = idTesteDoPezinho;
	}
	
	@Column(name = "int_amostra")
	public Integer getAmostra() {
		return amostra;
	}
	public void setAmostra(Integer amostra) {
		this.amostra = amostra;
	}
	
	@Column(name = "cv_motivo_amostra_invalida")
	public String getMotivoAmostraInvalida() {
		return motivoAmostraInvalida;
	}
	public void setMotivoAmostraInvalida(String motivoAmostraInvalida) {
		this.motivoAmostraInvalida = motivoAmostraInvalida;
	}
	
	@Column(name = "bo_amostra_valida", nullable=false )
	public Boolean getAmostraValida() {
		return amostraValida;
	}
	public void setAmostraValida(Boolean amostraValida) {
		this.amostraValida = amostraValida;
	}
	
	@Column(name = "cv_transfusao")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getTransfusao() {
		return transfusao;
	}
	public void setTransfusao(TipoStatusEnum transfusao) {
		this.transfusao = transfusao;
	}
	
	@Column(name = "cv_prematuro")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getPrematuro() {
		return prematuro;
	}
	public void setPrematuro(TipoStatusEnum prematuro) {
		this.prematuro = prematuro;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_cadastrante")
	public Profissional getProfissionalCadastrante() {
		return profissionalCadastrante;
	}
	public void setProfissionalCadastrante(Profissional profissionalCadastrante) {
		this.profissionalCadastrante = profissionalCadastrante;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ts_data_cadastro")
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ts_data_pku")
	public Date getDataPku() {
		return dataPku;
	}
	public void setDataPku(Date dataPku) {
		this.dataPku = dataPku;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ts_data_hb")
	public Date getDataHb() {
		return dataHb;
	}
	public void setDataHb(Date dataHb) {
		this.dataHb = dataHb;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ts_data_tsh")
	public Date getDataTsh() {
		return dataTsh;
	}
	public void setDataTsh(Date dataTsh) {
		this.dataTsh = dataTsh;
	}
	
	@Column(name = "in_irt")
	public Integer getIrt() {
		return irt;
	}
	public void setIrt(Integer irt) {
		this.irt = irt;
	}
	
	@Column(name = "cv_hemoglobinas")
	public String getHemoglobinas() {
		return hemoglobinas;
	}
	public void setHemoglobinas(String hemoglobinas) {
		this.hemoglobinas = hemoglobinas;
	}
	
	@Column(name = "in_tsh")
	public Integer getTsh() {
		return tsh;
	}
	public void setTsh(Integer tsh) {
		this.tsh = tsh;
	}
	
	@Column(name = "in_pku")
	public Integer getPku() {
		return pku;
	}
	public void setPku(Integer pku) {
		this.pku = pku;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_saude")
	public UnidadeSaude getUnidadeSaude() {
		return unidadeSaude;
	}
	public void setUnidadeSaude(UnidadeSaude unidadeSaude) {
		this.unidadeSaude = unidadeSaude;
	}
	
	@Column(name = "cv_telefone")
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cidade")
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	@Column(name = "cv_endereco")
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	@Column(name = "in_numero_casa")
	public Integer getNumeroCasa() {
		return numeroCasa;
	}
	public void setNumeroCasa(Integer numeroCasa) {
		this.numeroCasa = numeroCasa;
	}
	
	@Column(name = "cv_nome_mae")
	public String getNomeMae() {
		return nomeMae;
	}
	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}
	
	@Column(name = "cv_tipo_gemelar")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getTipoGemelar() {
		return tipoGemelar;
	}
	public void setTipoGemelar(TipoStatusEnum tipoGemelar) {
		this.tipoGemelar = tipoGemelar;
	}
	
	@Column(name = "cv_gemelar")
	public String getGemelar() {
		return gemelar;
	}
	public void setGemelar(String gemelar) {
		this.gemelar = gemelar;
	}
	
	@Column(name = "cv_sexo")
	@Enumerated(EnumType.STRING)
	public TipoSexoEnum getSexo() {
		return sexo;
	}
	public void setSexo(TipoSexoEnum sexo) {
		this.sexo = sexo;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ts_data_nascimento")
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	@Column(name = "cv_nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "cv_tipo")
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	@Column(name = "in_numero_exame")
	public Integer getNumeroExame() {
		return numeroExame;
	}
	public void setNumeroExame(Integer numeroExame) {
		this.numeroExame = numeroExame;
	}
	
	@Column(name = "in_lote")
	public Long getLote() {
		return lote;
	}
	public void setLote(Long lote) {
		this.lote = lote;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ts_data_coleta")
	public Date getDataColeta() {
		return dataColeta;
	}
	public void setDataColeta(Date dataColeta) {
		this.dataColeta = dataColeta;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ts_data_resultado")
	public Date getDataResultado() {
		return dataResultado;
	}
	public void setDataResultado(Date dataResultado) {
		this.dataResultado = dataResultado;
	}
	
	@Column(name = "in_numero_amostra")
	public Integer getNumeroAmostra() {
		return numeroAmostra;
	}
	public void setNumeroAmostra(Integer numeroAmostra) {
		this.numeroAmostra = numeroAmostra;
	}

	@Column(name = "tp_motivo_rejeicao")
	@Enumerated(EnumType.STRING)
	public TipoMotivosRejeicaoAmostraTestePezinhoEnum getMotivoRejeicao() {
		return motivoRejeicao;
	}
	public void setMotivoRejeicao(TipoMotivosRejeicaoAmostraTestePezinhoEnum motivoRejeicao) {
		this.motivoRejeicao = motivoRejeicao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof TesteDoPezinho))
			return false;
		
		return ((TesteDoPezinho)obj).getIdTesteDoPezinho() == this.idTesteDoPezinho;
	}

	@Override
	public String toString() {
		return nome;
	}
}
