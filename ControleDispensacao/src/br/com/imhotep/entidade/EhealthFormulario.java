package br.com.imhotep.entidade;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import javax.persistence.Transient;

import br.com.imhotep.enums.TipoEhealthPresencaWebEnum;

@Entity
@Table(name = "tb_ehealth_formulario")
public class EhealthFormulario {
	
	private int idEhealthFormulario;
	private boolean possuiSiteProprio;
	private TipoEhealthPresencaWebEnum tipoPresencaWeb;
	private boolean informacaoInstitucionalHospital;
	private boolean informacaoServicoPrestado;
	private boolean informacaoPrevencaoCuidadosSaude;
	private boolean procedimentosEmergenciaMedica;
	private boolean enderecoEletronicoRecepcao;
	private boolean marcacaoConsulta;
	private boolean tabelaCustoServico;
	private boolean localizacaoMeiosAcesso;
	private boolean corpoClinico;
	private boolean rastreioMedicoOnline;
	private boolean consultaOnline;
	private boolean disponibilizacaoFormularioDownload;
	private boolean disponibilizacaoFormulariosOnline;
	private boolean acessibilidade;
	private String observacao;
	private Set<EhealthFormularioRedeSocial> redesSociais;
	private Set<EhealthFormularioTecnologia> tecnologias;
	private EhealthEstabelecimento ehealthEstabelecimento;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_ehealth_formulario_id_ehealth_formulario_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_ehealth_formulario", unique = true, nullable = false)
	public int getIdEhealthFormulario() {
		return idEhealthFormulario;
	}

	public void setIdEhealthFormulario(int idEhealthFormulario) {
		this.idEhealthFormulario = idEhealthFormulario;
	}

	@Column(name="bl_possui_site_proprio")
	public boolean getPossuiSiteProprio() {
		return possuiSiteProprio;
	}

	public void setPossuiSiteProprio(boolean possuiSiteProprio) {
		this.possuiSiteProprio = possuiSiteProprio;
	}

	@Column(name="tp_tipo_presenca_web")
	@Enumerated(EnumType.STRING)
	public TipoEhealthPresencaWebEnum getTipoPresencaWeb() {
		return tipoPresencaWeb;
	}

	public void setTipoPresencaWeb(TipoEhealthPresencaWebEnum tipoPresencaWeb) {
		this.tipoPresencaWeb = tipoPresencaWeb;
	}

	@Column(name="bl_informacao_institucional_hospital")
	public boolean getInformacaoInstitucionalHospital() {
		return informacaoInstitucionalHospital;
	}

	public void setInformacaoInstitucionalHospital(boolean informacaoInstitucionalHospital) {
		this.informacaoInstitucionalHospital = informacaoInstitucionalHospital;
	}

	@Column(name="bl_informacao_servico_prestado")
	public boolean getInformacaoServicoPrestado() {
		return informacaoServicoPrestado;
	}

	public void setInformacaoServicoPrestado(boolean informacaoServicoPrestado) {
		this.informacaoServicoPrestado = informacaoServicoPrestado;
	}

	@Column(name="bl_informacao_prevencao_cuidados_saude")
	public boolean getInformacaoPrevencaoCuidadosSaude() {
		return informacaoPrevencaoCuidadosSaude;
	}

	public void setInformacaoPrevencaoCuidadosSaude(boolean informacaoPrevencaoCuidadosSaude) {
		this.informacaoPrevencaoCuidadosSaude = informacaoPrevencaoCuidadosSaude;
	}

	@Column(name="bl_procedimentos_emergencia_medica")
	public boolean getProcedimentosEmergenciaMedica() {
		return procedimentosEmergenciaMedica;
	}

	public void setProcedimentosEmergenciaMedica(boolean procedimentosEmergenciaMedica) {
		this.procedimentosEmergenciaMedica = procedimentosEmergenciaMedica;
	}

	@Column(name="bl_endereco_eletronico_recepcao")
	public boolean getEnderecoEletronicoRecepcao() {
		return enderecoEletronicoRecepcao;
	}

	public void setEnderecoEletronicoRecepcao(boolean enderecoEletronicoRecepcao) {
		this.enderecoEletronicoRecepcao = enderecoEletronicoRecepcao;
	}

	@Column(name="bl_marcacao_consulta")
	public boolean getMarcacaoConsulta() {
		return marcacaoConsulta;
	}

	public void setMarcacaoConsulta(boolean marcacaoConsulta) {
		this.marcacaoConsulta = marcacaoConsulta;
	}

	@Column(name="bl_tabela_custo_servico")
	public boolean getTabelaCustoServico() {
		return tabelaCustoServico;
	}

	public void setTabelaCustoServico(boolean tabelaCustoServico) {
		this.tabelaCustoServico = tabelaCustoServico;
	}

	@Column(name="bl_localizacao_meios_acesso")
	public boolean getLocalizacaoMeiosAcesso() {
		return localizacaoMeiosAcesso;
	}

	public void setLocalizacaoMeiosAcesso(boolean localizacaoMeiosAcesso) {
		this.localizacaoMeiosAcesso = localizacaoMeiosAcesso;
	}

	@Column(name="bl_corpo_clinico")
	public boolean getCorpoClinico() {
		return corpoClinico;
	}

	public void setCorpoClinico(boolean corpoClinico) {
		this.corpoClinico = corpoClinico;
	}

	@Column(name="bl_rastreio_medico_online")
	public boolean getRastreioMedicoOnline() {
		return rastreioMedicoOnline;
	}

	public void setRastreioMedicoOnline(boolean rastreioMedicoOnline) {
		this.rastreioMedicoOnline = rastreioMedicoOnline;
	}

	@Column(name="bl_consulta_online")
	public boolean getConsultaOnline() {
		return consultaOnline;
	}

	public void setConsultaOnline(boolean consultaOnline) {
		this.consultaOnline = consultaOnline;
	}

	@Column(name="bl_disponibilizacao_formulario_download")
	public boolean getDisponibilizacaoFormularioDownload() {
		return disponibilizacaoFormularioDownload;
	}

	public void setDisponibilizacaoFormularioDownload(boolean disponibilizacaoFormularioDownload) {
		this.disponibilizacaoFormularioDownload = disponibilizacaoFormularioDownload;
	}

	@Column(name="bl_disponibilizacao_formulario_online")
	public boolean getDisponibilizacaoFormulariosOnline() {
		return disponibilizacaoFormulariosOnline;
	}

	public void setDisponibilizacaoFormulariosOnline(boolean disponibilizacaoFormulariosOnline) {
		this.disponibilizacaoFormulariosOnline = disponibilizacaoFormulariosOnline;
	}
	
	@Column(name="bl_acessibilidade")
	public boolean getAcessibilidade() {
		return acessibilidade;
	}

	public void setAcessibilidade(boolean acessibilidade) {
		this.acessibilidade = acessibilidade;
	}

	@Column(name="cv_observacao")
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "ehealthFormulario")
	public Set<EhealthFormularioRedeSocial> getRedesSociais() {
		return redesSociais;
	}

	public void setRedesSociais(Set<EhealthFormularioRedeSocial> redesSociais) {
		this.redesSociais = redesSociais;
	}
	
	@Transient
	public List<EhealthFormularioRedeSocial> getRedesSociaisList() {
		if(redesSociais != null)
			return new ArrayList<EhealthFormularioRedeSocial>(redesSociais);
		return new ArrayList<EhealthFormularioRedeSocial>();
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "ehealthFormulario")
	public Set<EhealthFormularioTecnologia> getTecnologias() {
		return tecnologias;
	}

	public void setTecnologias(Set<EhealthFormularioTecnologia> tecnologias) {
		this.tecnologias = tecnologias;
	}
	
	@Transient
	public List<EhealthFormularioTecnologia> getTecnologiasList() {
		if(tecnologias != null)
			return new ArrayList<EhealthFormularioTecnologia>(tecnologias);
		return new ArrayList<EhealthFormularioTecnologia>();
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_ehealth_estabelecimento")
	public EhealthEstabelecimento getEhealthEstabelecimento() {
		return ehealthEstabelecimento;
	}
	public void setEhealthEstabelecimento(EhealthEstabelecimento ehealthEstabelecimento) {
		this.ehealthEstabelecimento = ehealthEstabelecimento;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof EhealthFormulario))
			return false;
		
		return ((EhealthFormulario)obj).getIdEhealthFormulario() == this.idEhealthFormulario;
	}

}
