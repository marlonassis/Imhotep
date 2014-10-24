package br.com.imhotep.relatorio;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.extra.AtendimentosRealizadosAGHU;
import br.com.imhotep.enums.TipoImpressaoComprovanteConsultaEnum;
import br.com.imhotep.linhaMecanica.LinhaMecanicaAGHU;

@ManagedBean
@SessionScoped
public class RelatorioComprovanteAtendimentoAGHU extends PadraoRelatorio{
	
	private static final String CARACTER_SEPARACAO_NOME_PROFISSIONAL = " / ";

	private static final long serialVersionUID = 1L;
	
	private String profissional;
	private Date dataIni;
	private Date dataFim;
	private Integer quantidadeLinhasExtras;
	private TipoImpressaoComprovanteConsultaEnum tipo = TipoImpressaoComprovanteConsultaEnum.E;
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		//Solicitação de Mudança #30
		String caminho;
		if(tipo.equals(TipoImpressaoComprovanteConsultaEnum.E))
			caminho = Constantes.DIR_RELATORIO + "RelatorioComprovanteAtendimentoRealizadosExame.jasper";
		else
			caminho = Constantes.DIR_RELATORIO + "RelatorioComprovanteAtendimentoRealizados.jasper";
		
		String nomeRelatorio = "RelatorioComprovanteAtendimentoRealizado.pdf";
		List<AtendimentosRealizadosAGHU> lista = getAtendimentos();
		Map<String, Object> map = new HashMap<String, Object>();
		String array[] = profissional.split(CARACTER_SEPARACAO_NOME_PROFISSIONAL);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String ini = sdf.format(dataIni);
		String fim = sdf.format(dataFim);
		map.put("PROFISSIONAL_NOME", array[0]);
		map.put("PROFISSIONAL_ESPECIALIDADE", array[1]);
		map.put("DATA_INI", ini);
		map.put("DATA_FIM", fim);
		map.put("CONSULTA_EXAME", getTipo().getLabel());
		
		super.geraRelatorio(caminho, nomeRelatorio, lista, map );
	}

	private List<AtendimentosRealizadosAGHU> getAtendimentos(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ini = sdf.format(dataIni);
		String fim = sdf.format(dataFim);
		String nomeProfissionalSemCrm = null;
		String array[] = null;
		if(profissional != null && !profissional.isEmpty()){
			array = profissional.split(CARACTER_SEPARACAO_NOME_PROFISSIONAL);
			nomeProfissionalSemCrm = array[0].contains("-") ? array[0].split("-")[1] : array[0];
		}
		
		//Solicitação de Mudança #30
		List<AtendimentosRealizadosAGHU> res = new ArrayList<AtendimentosRealizadosAGHU>();
		LinhaMecanicaAGHU lma = new LinhaMecanicaAGHU();
		List<Object[]> resultado;
		
		String sql;
		if( tipo.getLabel().equals(TipoImpressaoComprovanteConsultaEnum.E.getLabel()) ){
			sql =  "SELECT DISTINCT paciente.prontuario, paciente.nome AS pacientenome, paciente.nro_cartao_saude AS cartaosus, paciente.dt_nascimento AS nascimento, "					
					+ "endereco.logradouro, endereco.nro_logradouro AS numero, endereco.compl_logradouro AS complemento, endereco.bairro, endereco.cep,"
					+ "cidade.nome AS municipio,  qualificacao.nro_reg_conselho AS crm, cidade.uf_sigla AS uf, condatend.descricao as condicaoAtendimento, "
					+ "case when condatend.descricao = 'RECONSULTA' then 'INTERNO' when condatend.descricao = 'PRIMEIRA CONSULTA' then 'NUCAR' else 'EXTERNO' end tipoPaciente, "
					+ "con.dt_consulta AS dataconsulta, pessoa.nome AS profissional, esp.nome_especialidade AS especialidade "+
				  "FROM agh.aac_consultas con "+
				   "JOIN agh.aac_grade_agendamen_consultas grade ON con.grd_seq = grade.seq "+
				   "JOIN agh.agh_especialidades esp ON grade.esp_seq = esp.seq "+
				   "JOIN agh.agh_equipes eqp ON grade.eqp_seq = eqp.seq "+
				   "JOIN agh.aip_pacientes paciente ON paciente.codigo = con.pac_codigo "+
				   "LEFT JOIN agh.aip_enderecos_pacientes endereco ON paciente.codigo = endereco.pac_codigo "+
				   "LEFT JOIN agh.aip_bairros bairro ON bairro.codigo = endereco.bcl_bai_codigo "+
				   "LEFT JOIN agh.aip_logradouros logradouro ON logradouro.codigo = endereco.bcl_clo_lgr_codigo "+
				   "LEFT JOIN agh.aip_cidades cidade ON logradouro.cdd_codigo = cidade.codigo AND endereco.bcl_clo_lgr_codigo IS NOT NULL OR endereco.cdd_codigo = cidade.codigo AND endereco.cdd_codigo IS NOT NULL "+
				   "LEFT JOIN agh.rap_servidores serv ON grade.pre_ser_matricula = serv.matricula AND grade.pre_ser_vin_codigo = serv.vin_codigo "+
				   "LEFT JOIN agh.rap_pessoas_fisicas pessoa ON serv.pes_codigo = pessoa.codigo "+
				   "LEFT JOIN agh.rap_qualificacoes qualificacao ON qualificacao.pes_codigo = pessoa.codigo "+
				   "LEFT JOIN agh.aac_forma_agendamentos formaagen on (con.fag_caa_seq = formaagen.caa_seq and con.fag_tag_seq = formaagen.tag_seq and  con.fag_pgd_seq = formaagen.pgd_seq ) "+
				   "LEFT JOIN agh.aac_condicao_atendimentos condatend on (formaagen.caa_seq = condatend.seq) "+
				  "WHERE con.ret_seq in (9, 10) and "+
				  		(nomeProfissionalSemCrm != null ? 
							"lower(pessoa.nome) = lower('"+nomeProfissionalSemCrm+"') and lower(esp.nome_especialidade) = lower('"+array[1]+"') and "
							: ""
							)
							+" cast(con.dt_consulta as abstime) between cast('"+ini+"' as abstime) and cast('"+fim+"' as abstime) "+
				  "ORDER BY paciente.nome, con.dt_consulta ";
			
			resultado = lma.getListaResultado(sql, 14);
		}
		else{
			sql = "select prontuario, pacienteNome, cartaosus, nascimento, logradouro, "
							+ "numero, complemento, bairro, cep, municipio, crm, uf from agh.temp_compro_consul "
							+"where "+
							(nomeProfissionalSemCrm != null ? 
							"lower(profissional) = lower('"+nomeProfissionalSemCrm+"') and lower(especialidade) = lower('"+array[1]+"') and "
							: ""
							)
							+" cast(dataConsulta as date) between cast('"+ini+"' as date) and cast('"+fim+"' as date) "
							+"order by lower(pacienteNome)";
			
			resultado = lma.getListaResultado(sql);
		}
	
		for(Object[] o : resultado){
			Integer prontuario = (Integer) o[0];
			String nome = String.valueOf(o[1]);
			Long cartaoSus = (Long) o[2];
			Date dataNascimento = (Date) o[3];
			String logradouro = String.valueOf(o[4]);
			Integer numero = Integer.getInteger(String.valueOf(o[5]));
			String complemento = String.valueOf(o[6]);
			String bairro = String.valueOf(o[7]);
			Integer cep = (Integer) o[8];
			String municipio = String.valueOf(o[9]);
			String crm = String.valueOf(o[10]);
			String uf = String.valueOf(o[11]);
			
			//Solicitação de Mudança #30
			String condicao = String.valueOf((tipo.getLabel().equals(TipoImpressaoComprovanteConsultaEnum.E.getLabel())?String.valueOf(o[12]):""));
			//Requisito Funcional #39
			String tipoPaciente = String.valueOf((tipo.getLabel().equals(TipoImpressaoComprovanteConsultaEnum.E.getLabel())?String.valueOf(o[13]):""));
			
			AtendimentosRealizadosAGHU atendimento = new AtendimentosRealizadosAGHU(prontuario, nome, cartaoSus, dataNascimento, logradouro, 
																					numero, complemento, bairro, cep, municipio, crm, uf, condicao, tipoPaciente);
			res.add(atendimento);
		}
		
		adicionarLinhasExtras(res);
		
		return res;
	}

	private void adicionarLinhasExtras(List<AtendimentosRealizadosAGHU> res) {
		if(quantidadeLinhasExtras != null)
			for(int i = 0; i < quantidadeLinhasExtras; i++){
				AtendimentosRealizadosAGHU atendimento = new AtendimentosRealizadosAGHU();
				res.add(atendimento);
			}
	}
	
	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getProfissional() {
		return profissional;
	}

	public void setProfissional(String profissional) {
		this.profissional = profissional;
	}

	public Integer getQuantidadeLinhasExtras() {
		return quantidadeLinhasExtras;
	}

	public void setQuantidadeLinhasExtras(Integer quantidadeLinhasExtras) {
		this.quantidadeLinhasExtras = quantidadeLinhasExtras;
	}

	public TipoImpressaoComprovanteConsultaEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoImpressaoComprovanteConsultaEnum tipo) {
		this.tipo = tipo;
	}
	
}
