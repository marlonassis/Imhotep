package br.com.Imhotep.entidade;

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

import br.com.Imhotep.enums.TipoOperacaoEnum;

@Entity
@Table(name = "tb_doacao")
public class Doacao {

	private int idDoacao;
	private Hospital hospital;
	private Material material;
	private Integer quantidade;
	private TipoOperacaoEnum tipoEntrada;
	private Date dataDoacao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_doacao_id_doacao_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_doacao", unique = true, nullable = false)
	public int getIdDoacao() {
		return idDoacao;
	}
	public void setIdDoacao(int idDoacao) {
		this.idDoacao = idDoacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_hospital")
	public Hospital getHospital() {
		return hospital;
	}
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material")
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	@Column(name = "in_quantidade")
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@Column(name = "tp_tipo_entrada")
	@Enumerated(EnumType.STRING)
	public TipoOperacaoEnum getTipoEntrada() {
		return tipoEntrada;
	}
	public void setTipoEntrada(TipoOperacaoEnum tipoEntrada) {
		this.tipoEntrada = tipoEntrada;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_doacao")
	public Date getDataDoacao() {
		return dataDoacao;
	}
	public void setDataDoacao(Date dataDoacao) {
		this.dataDoacao = dataDoacao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Doacao))
			return false;
		
		return ((Doacao)obj).getIdDoacao() == this.idDoacao;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + dataDoacao.hashCode() + hospital.hashCode() + material.hashCode();
	}

	@Override
	public String toString() {
		return "Hospital: " + hospital.getNome() + ", Material: " + material.getDescricao() + ", Quantidade: " + String.valueOf(quantidade) + ", Data: " + new SimpleDateFormat("dd/MM/yyyy").format(dataDoacao);
	}
}
