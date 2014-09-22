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
@Table(name = "tb_estoque")
public class Estoque implements Serializable{
	private static final long serialVersionUID = -6495593978691300777L;
	
	private int idEstoque;
	private Fabricante fabricante; 
	private Material material;
	private Unidade unidade;
	private String lote;
	private Date dataValidade;
	private int quantidadeAtual;
	private boolean bloqueado;
	private String motivoBloqueio;
	private Date dataInclusao;
	private Usuario usuarioInclusao;
	private Date dataBloqueio;
	private Usuario usuarioBloqueio;
	private TipoBloqueioLoteEnum tipoBloqueio;
	private boolean lock;
	private String codigoBarras;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_estoque_id_estoque_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_estoque", unique = true, nullable = false)
	public int getIdEstoque() {
		return idEstoque;
	}
	public void setIdEstoque(int idEstoque) {
		this.idEstoque = idEstoque;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_fabricante")
	public Fabricante getFabricante() {
		return fabricante;
	}
	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material")
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade")
	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
	@Column(name = "cv_lote")
	public String getLote() {
		if(lote != null){
			lote = lote.toUpperCase();
		}
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
	@JoinColumn(name = "id_usuario_inclusao")
	public Usuario getUsuarioInclusao() {
		return usuarioInclusao;
	}
	public void setUsuarioInclusao(Usuario usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
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
	@JoinColumn(name = "id_usuario_bloqueio")
	public Usuario getUsuarioBloqueio() {
		return usuarioBloqueio;
	}
	public void setUsuarioBloqueio(Usuario usuarioBloqueio) {
		this.usuarioBloqueio = usuarioBloqueio;
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
	public String getLoteMaterialDescricao(){
		if(getMaterial() != null && getLote() != null){
			return getLote().concat(" - ").concat(getMaterial().getDescricao());
		}
		return "Erro ao procurar o nome do material e lote";
	}

	@Transient
	public String getDescricaoEstoqueCentroCirurgico(){
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (bloqueado ? 1231 : 1237);
		result = prime * result
				+ ((codigoBarras == null) ? 0 : codigoBarras.hashCode());
		result = prime * result
				+ ((dataBloqueio == null) ? 0 : dataBloqueio.hashCode());
		result = prime * result
				+ ((dataInclusao == null) ? 0 : dataInclusao.hashCode());
		result = prime * result
				+ ((dataValidade == null) ? 0 : dataValidade.hashCode());
		result = prime * result
				+ ((fabricante == null) ? 0 : fabricante.hashCode());
		result = prime * result + idEstoque;
		result = prime * result + (lock ? 1231 : 1237);
		result = prime * result + ((lote == null) ? 0 : lote.hashCode());
		result = prime * result
				+ ((material == null) ? 0 : material.hashCode());
		result = prime * result
				+ ((motivoBloqueio == null) ? 0 : motivoBloqueio.hashCode());
		result = prime * result + quantidadeAtual;
		result = prime * result
				+ ((tipoBloqueio == null) ? 0 : tipoBloqueio.hashCode());
		result = prime * result + ((unidade == null) ? 0 : unidade.hashCode());
		result = prime * result
				+ ((usuarioBloqueio == null) ? 0 : usuarioBloqueio.hashCode());
		result = prime * result
				+ ((usuarioInclusao == null) ? 0 : usuarioInclusao.hashCode());
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
		Estoque other = (Estoque) obj;
		if (bloqueado != other.bloqueado)
			return false;
		if (codigoBarras == null) {
			if (other.codigoBarras != null)
				return false;
		} else if (!codigoBarras.equals(other.codigoBarras))
			return false;
		if (dataBloqueio == null) {
			if (other.dataBloqueio != null)
				return false;
		} else if (!dataBloqueio.equals(other.dataBloqueio))
			return false;
		if (dataInclusao == null) {
			if (other.dataInclusao != null)
				return false;
		} else if (!dataInclusao.equals(other.dataInclusao))
			return false;
		if (dataValidade == null) {
			if (other.dataValidade != null)
				return false;
		} else if (!dataValidade.equals(other.dataValidade))
			return false;
		if (fabricante == null) {
			if (other.fabricante != null)
				return false;
		} else if (!fabricante.equals(other.fabricante))
			return false;
		if (idEstoque != other.idEstoque)
			return false;
		if (lock != other.lock)
			return false;
		if (lote == null) {
			if (other.lote != null)
				return false;
		} else if (!lote.equals(other.lote))
			return false;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		if (motivoBloqueio == null) {
			if (other.motivoBloqueio != null)
				return false;
		} else if (!motivoBloqueio.equals(other.motivoBloqueio))
			return false;
		if (quantidadeAtual != other.quantidadeAtual)
			return false;
		if (tipoBloqueio != other.tipoBloqueio)
			return false;
		if (unidade == null) {
			if (other.unidade != null)
				return false;
		} else if (!unidade.equals(other.unidade))
			return false;
		if (usuarioBloqueio == null) {
			if (other.usuarioBloqueio != null)
				return false;
		} else if (!usuarioBloqueio.equals(other.usuarioBloqueio))
			return false;
		if (usuarioInclusao == null) {
			if (other.usuarioInclusao != null)
				return false;
		} else if (!usuarioInclusao.equals(other.usuarioInclusao))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Lote: ".concat(lote).concat(" - Quantidade: ").concat(Integer.valueOf(quantidadeAtual).toString()).concat(" - Validade: ").concat(new SimpleDateFormat("dd/MM/yyyy").format(getDataValidade()));
	}
	
}
