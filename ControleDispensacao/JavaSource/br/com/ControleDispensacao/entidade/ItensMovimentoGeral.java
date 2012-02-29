package br.com.ControleDispensacao.entidade;

import java.util.Date;

import javax.persistence.CascadeType;
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
@Table(name = "tb_itens_movimento_geral")
public class ItensMovimentoGeral {
	private int idItensMovimentoGeral;
	private MovimentoGeral movimentoGeral;
	private Material material;
	private Fabricante fabricante;
	private String lote;
	private Date dataValidade;
	private Integer quantidade;
	private PrescricaoItem prescricaoItem;
	private Usuario usuarioAutorizador;
	private ItemSolicitaRemanejamento itemSolicitaRemanejamento;
	
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_itens_movimento_geral_id_itens_movimento_geral_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_itens_movimento_geral", unique = true, nullable = false)
	public int getIdItensMovimentoGeral() {
		return idItensMovimentoGeral;
	}
	public void setIdItensMovimentoGeral(int idItensMovimentoGeral) {
		this.idItensMovimentoGeral = idItensMovimentoGeral;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name = "id_movimento_geral")
	public MovimentoGeral getMovimentoGeral() {
		return movimentoGeral;
	}
	public void setMovimentoGeral(MovimentoGeral movimentoGeral) {
		this.movimentoGeral = movimentoGeral;
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
	@JoinColumn(name = "id_fabricante")
	public Fabricante getFabricante() {
		return fabricante;
	}
	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}
	
	@Column(name = "ds_lote", length = 30)
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_data_validade")
	public Date getDataValidade() {
		return dataValidade;
	}
	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}
	
	@Column(name = "in_quantidade")
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_prescricao_item")
	public PrescricaoItem getPrescricaoItem() {
		return prescricaoItem;
	}
	public void setPrescricaoItem(PrescricaoItem prescricaoItem) {
		this.prescricaoItem = prescricaoItem;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_autorizador")
	public Usuario getUsuarioAutorizador() {
		return usuarioAutorizador;
	}
	public void setUsuarioAutorizador(Usuario usuarioAutorizador) {
		this.usuarioAutorizador = usuarioAutorizador;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_item_solicita_remanejamento")
	public ItemSolicitaRemanejamento getItemSolicitaRemanejamento() {
		return itemSolicitaRemanejamento;
	}
	public void setItemSolicitaRemanejamento(ItemSolicitaRemanejamento itemSolicitaRemanejamento) {
		this.itemSolicitaRemanejamento = itemSolicitaRemanejamento;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Usuario))
			return false;
		
		return ((ItensMovimentoGeral)obj).getIdItensMovimentoGeral() == this.idItensMovimentoGeral;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + material.getDescricao().concat(lote).hashCode();
	}

	@Override
	public String toString() {
		return lote.concat(" - ").concat(material.getDescricao());
	}
}