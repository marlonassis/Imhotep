package br.com.imhotep.entidade;

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
public class Estoque {
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
		if(lote != null){
			lote = lote.toUpperCase();
		}
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
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Estoque))
			return false;
		
		return ((Estoque)obj).getIdEstoque() == this.idEstoque;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + lote.hashCode();
	}

	@Override
	public String toString() {
		return "Lote: ".concat(lote).concat(" - Quantidade: ").concat(Integer.valueOf(quantidadeAtual).toString()).concat(" - Validade: ").concat(new SimpleDateFormat("dd/MM/yyyy").format(getDataValidade()));
	}
	
}
