package br.com.imhotep.entidade;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.persistence.Transient;

import br.com.imhotep.enums.TipoBloqueioLoteEnum;

@Entity
@Table(name = "tb_estoque_almoxarifado")
public class EstoqueAlmoxarifado implements Serializable {

	private static final long serialVersionUID = 6593907596416924213L;
	private int idEstoqueAlmoxarifado;
	private FabricanteAlmoxarifado fabricanteAlmoxarifado; 
	private MaterialAlmoxarifado materialAlmoxarifado;
	private String lote;
	private Date dataValidade;
	private int quantidadeAtual;
	private boolean bloqueado;
	private String motivoBloqueio;
	private Date dataInclusao;
	private Profissional profissionalInclusao;
	private Date dataBloqueio;
	private Profissional profissionalBloqueio;
	private TipoBloqueioLoteEnum tipoBloqueio;
	private boolean lock;
	private Date dataLock;
	private String codigoBarras;
	
	public EstoqueAlmoxarifado(){
		super();
	}
	
	public EstoqueAlmoxarifado(EstoqueAlmoxarifado estoqueAlmoxarifado) {
		super();
		this.idEstoqueAlmoxarifado = estoqueAlmoxarifado.idEstoqueAlmoxarifado;
		this.fabricanteAlmoxarifado = estoqueAlmoxarifado.fabricanteAlmoxarifado;
		this.materialAlmoxarifado = estoqueAlmoxarifado.materialAlmoxarifado;
		this.lote = estoqueAlmoxarifado.lote;
		this.dataValidade = estoqueAlmoxarifado.dataValidade;
		this.quantidadeAtual = estoqueAlmoxarifado.quantidadeAtual;
		this.bloqueado = estoqueAlmoxarifado.bloqueado;
		this.motivoBloqueio = estoqueAlmoxarifado.motivoBloqueio;
		this.dataInclusao = estoqueAlmoxarifado.dataInclusao;
		this.profissionalInclusao = estoqueAlmoxarifado.profissionalInclusao;
		this.dataBloqueio = estoqueAlmoxarifado.dataBloqueio;
		this.profissionalBloqueio = estoqueAlmoxarifado.profissionalBloqueio;
		this.tipoBloqueio = estoqueAlmoxarifado.tipoBloqueio;
		this.lock = estoqueAlmoxarifado.lock;
		this.dataLock = estoqueAlmoxarifado.dataLock;
		this.codigoBarras = estoqueAlmoxarifado.codigoBarras;
	}
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_estoque_almoxarifado_id_estoque_almoxarifado_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_estoque_almoxarifado", unique = true, nullable = false)
	public int getIdEstoqueAlmoxarifado() {
		return idEstoqueAlmoxarifado;
	}
	public void setIdEstoqueAlmoxarifado(int idEstoqueAlmoxarifado) {
		this.idEstoqueAlmoxarifado = idEstoqueAlmoxarifado;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_fabricante_almoxarifado")
	public FabricanteAlmoxarifado getFabricanteAlmoxarifado() {
		return fabricanteAlmoxarifado;
	}
	public void setFabricanteAlmoxarifado(FabricanteAlmoxarifado fabricanteAlmoxarifado) {
		this.fabricanteAlmoxarifado = fabricanteAlmoxarifado;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material_almoxarifado")
	public MaterialAlmoxarifado getMaterialAlmoxarifado() {
		return materialAlmoxarifado;
	}
	public void setMaterialAlmoxarifado(MaterialAlmoxarifado materialAlmoxarifado) {
		this.materialAlmoxarifado = materialAlmoxarifado;
	}
	
	@Column(name = "cv_lote")
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		if(lote != null)
			lote = lote.toUpperCase().trim();
		this.lote = lote;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_validade")
	public Date getDataValidade() {
		return dataValidade;
	}
	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}
	
	@Column(name = "in_quantidade_atual")
	public int getQuantidadeAtual() {
		return quantidadeAtual;
	}
	public void setQuantidadeAtual(int quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}
	
	@Column(name = "bl_bloqueado")
	public boolean getBloqueado() {
		return bloqueado;
	}
	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_lock")
	public Date getDataLock() {
		return dataLock;
	}
	public void setDataLock(Date dataLock) {
		this.dataLock = dataLock;
	}
	
	@Column(name = "bl_lock")
	public boolean getLock() {
		return lock;
	}
	public void setLock(boolean lock) {
		this.lock = lock;
	}
	
	@Column(name = "cv_motivo_bloqueio")
	public String getMotivoBloqueio() {
		return motivoBloqueio;
	}
	public void setMotivoBloqueio(String motivoBloqueio) {
		this.motivoBloqueio = motivoBloqueio;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_inclusao")
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_inclusao")
	public Profissional getProfissionalInclusao() {
		return profissionalInclusao;
	}
	public void setProfissionalInclusao(Profissional profissionalInclusao) {
		this.profissionalInclusao = profissionalInclusao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_bloqueio")
	public Date getDataBloqueio() {
		return dataBloqueio;
	}
	public void setDataBloqueio(Date dataBloqueio) {
		this.dataBloqueio = dataBloqueio;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_bloqueio")
	public Profissional getProfissionalBloqueio() {
		return profissionalBloqueio;
	}
	public void setProfissionalBloqueio(Profissional profissionalBloqueio) {
		this.profissionalBloqueio = profissionalBloqueio;
	}
	
	@Column(name = "tp_tipo_bloqueio")
	@Enumerated(EnumType.STRING)
	public TipoBloqueioLoteEnum getTipoBloqueio() {
		return tipoBloqueio;
	}
	public void setTipoBloqueio(TipoBloqueioLoteEnum tipoBloqueio) {
		this.tipoBloqueio = tipoBloqueio;
	}
	
	@Column(name="cv_codigo_barras")
	public String getCodigoBarras() {
		return codigoBarras;
	}
	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}
	
	@Transient
	public String getDescricaoResumidaMaterial(){
		String descricao = "";
		descricao += getMaterialAlmoxarifado().getDescricaoUnidadeMaterial() + " - ";
		
		descricao += ((getLote() == null || getLote().isEmpty()) ? "(" + getIdEstoqueAlmoxarifado() + ")"  : getLote()) + " - ";
		
		if(getFabricanteAlmoxarifado() != null){
			descricao += getFabricanteAlmoxarifado().getDescricao() + " - ";
		}
		if(getDataValidade() != null){
			descricao += new SimpleDateFormat("dd/MM/yyyy").format(getDataValidade()) + " - ";
		}
		
		descricao += getQuantidadeAtual();
		
		return descricao;
	}
	
	@Transient
	public String getDescricaoResumida(){
		String descricao = "";
		if(getLote() != null){
			descricao += getLote() + " - ";
		}
		if(getFabricanteAlmoxarifado() != null){
			descricao += getFabricanteAlmoxarifado().getDescricao() + " - ";
		}
		if(getDataValidade() != null){
			descricao += new SimpleDateFormat("dd/MM/yyyy").format(getDataValidade()) + " - ";
		}
		
		descricao += getQuantidadeAtual();
		
		return descricao;
	}
	
	@Transient
	public EstoqueAlmoxarifado clone(){
		return new EstoqueAlmoxarifado(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codigoBarras == null) ? 0 : codigoBarras.hashCode());
		result = prime * result
				+ ((dataInclusao == null) ? 0 : dataInclusao.hashCode());
		result = prime
				* result
				+ ((fabricanteAlmoxarifado == null) ? 0
						: fabricanteAlmoxarifado.hashCode());
		result = prime * result + idEstoqueAlmoxarifado;
		result = prime
				* result
				+ ((materialAlmoxarifado == null) ? 0 : materialAlmoxarifado
						.hashCode());
		result = prime
				* result
				+ ((profissionalInclusao == null) ? 0 : profissionalInclusao
						.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstoqueAlmoxarifado other = (EstoqueAlmoxarifado) obj;
		if (codigoBarras == null) {
			if (other.codigoBarras != null)
				return false;
		} else if (!codigoBarras.equals(other.codigoBarras))
			return false;
		if (dataInclusao == null) {
			if (other.dataInclusao != null)
				return false;
		} else if (!dataInclusao.equals(other.dataInclusao))
			return false;
		if (fabricanteAlmoxarifado == null) {
			if (other.fabricanteAlmoxarifado != null)
				return false;
		} else if (!fabricanteAlmoxarifado.equals(other.fabricanteAlmoxarifado))
			return false;
		if (idEstoqueAlmoxarifado != other.idEstoqueAlmoxarifado)
			return false;
		if (materialAlmoxarifado == null) {
			if (other.materialAlmoxarifado != null)
				return false;
		} else if (!materialAlmoxarifado.equals(other.materialAlmoxarifado))
			return false;
		if (profissionalInclusao == null) {
			if (other.profissionalInclusao != null)
				return false;
		} else if (!profissionalInclusao.equals(other.profissionalInclusao))
			return false;
		return true;
	}
	
	
	
	
}
