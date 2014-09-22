package br.com.imhotep.linhaMecanica.atualizador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.imhotep.linhaMecanica.LinhaMecanica;

public class AtualizadorEstoqueMedicamentoVencidoLM{
	
	private static final String ip = "200.133.41.8";
	private static final String DB_BANCO_IMHOTEP = "db_imhotep";
	
	public static void main(String[] args) {
		try {
			LinhaMecanica lm = new LinhaMecanica();
			lm.setNomeBanco(DB_BANCO_IMHOTEP);
			lm.setIp(ip);
			ResultSet rs = lm.consultar("select * from tb_estoque where dt_data_validade < now() and in_quantidade_atual > 0 order by dt_data_validade");
			while (rs.next()) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(rs.getTimestamp("dt_data_validade"));
				calendar.add(Calendar.SECOND, 1);
				int idEstoque = rs.getInt("id_estoque");
				int quantidadeAtual = rs.getInt("in_quantidade_atual");
				String insertML = "INSERT INTO tb_movimento_livro( "+
								            "id_tipo_movimento, "+ 
								            "dt_data_movimento, id_usuario_movimentacao, id_estoque, in_quantidade_movimentacao, "+ 
								            "in_quantidade_atual) "+
								    "VALUES (31, '"+new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())+" 00:00:00', 1627,  "+idEstoque+
								            ", "+quantidadeAtual+", "+quantidadeAtual+");";
				String updateEstoque = "update tb_estoque set in_quantidade_atual = 0 where id_estoque = "+idEstoque;
				
				System.out.println(insertML);
				System.out.println(updateEstoque);
				
				lm.executarCUD(insertML);
				lm.executarCUD(updateEstoque);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void posicaoEstoquesVencidos(){
		String[] qtds = {"587;32", 
				"526;180", 
				"673;3", 
				"539;194", 
				"567;100", 
				"685;30", 
				"601;37", 
				"531;227", 
				"499;255", 
				"706;20", 
				"616;138", 
				"469;0", 
				"552;175", 
				"681;30", 
				"533;17", 
				"434;0", 
				"792;200", 
				"846;762", 
				"439;5", 
				"901;19", 
				"621;460", 
				"722;3", 
				"495;749", 
				"746;5", 
				"844;205", 
				"541;417", 
				"626;122", 
				"605;38", 
				"864;210", 
				"481;216", 
				"566;500", 
				"951;114", 
				"624;360", 
				"453;43", 
				"782;17", 
				"680;3", 
				"657;22", 
				"774;49", 
				"777;36", 
				"956;59", 
				"738;525", 
				"725;86", 
				"983;235", 
				"441;871", 
				"475;25", 
				"485;83", 
				"577;53", 
				"781;44", 
				"899;23", 
				"1082;198", 
				"468;50", 
				"486;4", 
				"595;64", 
				"571;88", 
				"547;554", 
				"506;9", 
				"553;96", 
				"1079;43", 
				"568;1", 
				"578;45", 
				"1153;184", 
				"898;388", 
				"716;1", 
				"1136;40", 
				"647;321", 
				"1063;14", 
				"687;1478", 
				"646;19", 
				"1143;723", 
				"581;29", 
				"1120;540", 
				"573;15", 
				"433;416", 
				"1133;20", 
				"455;5", 
				"560;36", 
				"438;1471", 
				"542;4", 
				"536;170", 
				"561;321", 
				"702;11", 
				"480;37", 
				"534;31", 
				"679;290", 
				"669;56"};
				for(String item : qtds){
					String[] itens = item.split(";");
					String sql = "update tb_estoque set in_quantidade_atual = "+itens[1]+" where id_estoque = "+itens[0];
					System.out.println(sql);
//					lm.executarCUD(sql);
				}
	}
	
	
}
