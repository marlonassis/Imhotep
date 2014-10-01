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
		String caminho = Constantes.DIR_RELATORIO + "RelatorioComprovanteAtendimentoRealizados.jasper";
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ini = sdf.format(dataIni);
		String fim = sdf.format(dataFim);
		String nomeProfissionalSemCrm = null;
		String array[] = null;
		if(profissional != null && !profissional.isEmpty()){
			array = profissional.split(CARACTER_SEPARACAO_NOME_PROFISSIONAL);
			nomeProfissionalSemCrm = array[0].contains("-") ? array[0].split("-")[1] : array[0];
		}
		String sql = "select prontuario, pacienteNome, cartaosus, nascimento, logradouro, "
						+ "numero, complemento, bairro, cep, municipio, crm, uf from agh.temp_compro_consul "
						+"where "+
						(nomeProfissionalSemCrm != null ? 
						"lower(profissional) = lower('"+nomeProfissionalSemCrm+"') and lower(especialidade) = lower('"+array[1]+"') and "
						: ""
						)
						+" cast(dataConsulta as date) between cast('"+ini+"' as date) and cast('"+fim+"' as date) "
						+"order by lower(pacienteNome)";
		
		List<AtendimentosRealizadosAGHU> res = new ArrayList<AtendimentosRealizadosAGHU>();
		LinhaMecanicaAGHU lma = new LinhaMecanicaAGHU();
		List<Object[]> resultado = lma.getListaResultado(sql);
		
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
			
			AtendimentosRealizadosAGHU atendimento = new AtendimentosRealizadosAGHU(prontuario, nome, cartaoSus, dataNascimento, logradouro, 
																					numero, complemento, bairro, cep, municipio, crm, uf);
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
