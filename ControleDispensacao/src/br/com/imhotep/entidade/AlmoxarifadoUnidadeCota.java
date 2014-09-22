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
@Table(name = "tb_almoxarifado_unidade_cota")
public class AlmoxarifadoUnidadeCota {
	private int idAlmoxarifadoUnidadeCota;
	private MaterialAlmoxarifado materialAlmoxarifado;
	private Unidade unidade;
	private Integer cota;
	private Profissional profissionalCadastrante;
	private Date dataCadastro;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_almoxarifado_unidade_cota_id_almoxarifado_unidade_cota_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_almoxarifado_unidade_cota", unique = true, nullable = false)
	public int getIdAlmoxarifadoUnidadeCota() {
		return this.idAlmoxarifadoUnidadeCota;
	}
	
	public void setIdAlmoxarifadoUnidadeCota(int idAlmoxarifadoUnidadeCota){
		this.idAlmoxarifadoUnidadeCota = idAlmoxarifadoUnidadeCota;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material_almoxarifado")
	public MaterialAlmoxarifado getMaterialAlmoxarifado(){
		return materialAlmoxarifado;
	}
	
	public void setMaterialAlmoxarifado(MaterialAlmoxarifado materialAlmoxarifado){
		this.materialAlmoxarifado = materialAlmoxarifado;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade")
	public Unidade getUnidade(){
		return unidade;
	}
	
	public void setUnidade(Unidade unidade){
		this.unidade = unidade;
	}
	
	@Column(name="in_cota")
	public Integer getCota(){
		return cota;
	}
	
	public void setCota(Integer cota){
		this.cota = cota;
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
	@Column(name = "dt_data_cadastro")
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
}
