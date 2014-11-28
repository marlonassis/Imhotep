package br.com.imhotep.consulta.autoComplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.linhaMecanica.LinhaMecanicaAGHU;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class AGHUProfissionalComprovanteAutoComplete  extends ConsultaGeral<String>{

	public Collection<String> autoComplete(String nome) {
		nome = nome.trim();
		String sql = getSqlConsultaMedicoAGHU(nome);
		LinhaMecanicaAGHU lma = new LinhaMecanicaAGHU();
		List<Object[]> listaResultado = lma.getListaResultado(sql, 3);
		Collection<String> res = new ArrayList<String>();
		for(Object[] o : listaResultado){
			String item = String.valueOf(o[0]).concat(" / ").concat(String.valueOf(o[1]));
			String crm = String.valueOf(o[2]);
			if(crm != null && !crm.isEmpty() && !crm.equals("null"))
				item = crm + "-" + item;
			res.add(item);
		}
		
		if(res.size() > 0)
			return res;
		else{
			List<String> l = new ArrayList<String>();
			l.add("Não encontrado");
			return l;
		}
	}
	
	private String getSqlConsultaMedicoAGHU(String nome){
		String sql = "SELECT distinct pessoa.nome AS profissional, esp.nome_especialidade AS especialidade, qualificacao.nro_reg_conselho AS crm "+
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
				  "WHERE con.ret_seq in (9, 10) and pessoa.nome ilike '%"+nome+"%' "+
				  "ORDER BY pessoa.nome";
		return sql;
	}
	
}