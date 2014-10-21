package br.com.imhotep.entidade;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.imhotep.enums.TipoStatusEnum;

@Entity
@Table(name = "tb_estoque_centro_cirurgico")
public class EstoqueCentroCirurgico {
	private int idEstoqueCentroCirurgico;
	private Material material;
	private String lote;
	private TipoStatusEnum bloqueado;
	private Date dataValidade;
	private String motivoBloqueio;
	private Integer quantidadePadrao;
	private Fabricante fabricante;
	private Date dataCadastro;
	private Date dataBloqueio;
	private Profissional profissionalCadastro;
	private Profissional profissionalBloqueio;
	private List<EstoqueCentroCirurgicoLivro> estoqueCentroCirurgicoLivroList;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_estoque_centro_cirurgico_id_estoque_centro_cirurgico_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_estoque_centro_cirurgico", unique = true, nullable = false)
	public int getIdEstoqueCentroCirurgico() {
		return idEstoqueCentroCirurgico;
	}
	public void setIdEstoqueCentroCirurgico(int idEstoqueCentroCirurgico) {
		this.idEstoqueCentroCirurgico = idEstoqueCentroCirurgico;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material")
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	@Column(name = "cv_lote")
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	
	@Column(name = "tp_bloqueado")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getBloqueado() {
		return bloqueado;
	}
	public void setBloqueado(TipoStatusEnum bloqueado) {
		this.bloqueado = bloqueado;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_validade")
	public Date getDataValidade() {
		return dataValidade;
	}
	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}
	
	@Column(name = "cv_motivo_bloqueio")
	public String getMotivoBloqueio() {
		return motivoBloqueio;
	}
	public void setMotivoBloqueio(String motivoBloqueio) {
		this.motivoBloqueio = motivoBloqueio;
	}
	
	@Column(name = "in_quantidade_padrao")
	public Integer getQuantidadePadrao() {
		return quantidadePadrao;
	}
	public void setQuantidadePadrao(Integer quantidadePadrao) {
		this.quantidadePadrao = quantidadePadrao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_fabricante")
	public Fabricante getFabricante() {
		return fabricante;
	}
	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_bloqueio")
	public Date getDataBloqueio() {
		return dataBloqueio;
	}
	public void setDataBloqueio(Date dataBloqueio) {
		this.dataBloqueio = dataBloqueio;
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
	@JoinColumn(name = "id_profissional_cadastro")
	public Profissional getProfissionalCadastro() {
		return profissionalCadastro;
	}
	public void setProfissionalCadastro(Profissional profissionalCadastro) {
		this.profissionalCadastro = profissionalCadastro;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_bloqueio")
	public Profissional getProfissionalBloqueio() {
		return profissionalBloqueio;
	}
	public void setProfissionalBloqueio(Profissional profissionalBloqueio) {
		this.profissionalBloqueio = profissionalBloqueio;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estoqueCentroCirurgico")
	public List<EstoqueCentroCirurgicoLivro> getEstoqueCentroCirurgicoLivroList() {
		return this.estoqueCentroCirurgicoLivroList;
	}

	public void setEstoqueCentroCirurgicoLivroList(List<EstoqueCentroCirurgicoLivro> estoqueCentroCirurgicoLivroList) {
		this.estoqueCentroCirurgicoLivroList = estoqueCentroCirurgicoLivroList;
	}
	
	@Transient
	public String getDescricaoGeral(){
		if(getMaterial() != null){
			String descricao = getMaterial().getDescricao();
			descricao = descricao.concat("; Lote: ").concat(lote);
			descricao = descricao.concat("; Validade: ").concat(new SimpleDateFormat("dd/MM/yyyy").format(dataValidade));
			descricao = descricao.concat("; Fabricante: ").concat(fabricante.getDescricao());
			return descricao;
		}
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof EstoqueCentroCirurgico))
			return false;
		
		return ((EstoqueCentroCirurgico)obj).getIdEstoqueCentroCirurgico() == this.idEstoqueCentroCirurgico;
	}
	
	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + lote.hashCode();
	}
	
	@Override
	public String toString() {
		return lote;
	}
}
