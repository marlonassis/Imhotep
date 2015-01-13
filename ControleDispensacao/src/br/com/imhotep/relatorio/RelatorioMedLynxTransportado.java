package br.com.imhotep.relatorio;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.extra.MedLynxTransportado;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

@ManagedBean
@ViewScoped
public class RelatorioMedLynxTransportado extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	private List<MedLynxTransportado> getConteudo(){
		List<MedLynxTransportado> lista = new ArrayList<MedLynxTransportado>();
		try {
			String sql = "select b.cv_descricao descricao, b.cv_codigo_material codigoMaterial, a.* from tb_preco_medio_transportado_medlynx a  "+
							"inner join tb_material b on a.id_material = b.id_material "+
							"order by lower(to_ascii(b.cv_descricao))";
			
			
			LinhaMecanica lm = new LinhaMecanica();
			lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
			lm.setIp(Constantes.IP_LOCAL);
			ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
			while (rs.next()) { 
				String codigoMaterial = rs.getString("codigoMaterial");
				String descricao = rs.getString("descricao");
				int saldoTransportado = rs.getInt("in_saldo_transportado");
				int saldoImhotep = rs.getInt("in_saldo_imhotep");
				double precoMedioTransportado = rs.getDouble("db_preco_medio_transportado");
				double precoMedioImhotep = rs.getDouble("db_preco_medio_imhotep");
				MedLynxTransportado mlt = new MedLynxTransportado(codigoMaterial , descricao , saldoTransportado, saldoImhotep , precoMedioTransportado , precoMedioImhotep);
				lista.add(mlt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;	
	}
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioMedLynxTransportado.jasper";
		String nomeRelatorio = "RelatorioMedLynxTransportado-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		HashMap<String, Object> map = new HashMap<String, Object>();
		super.geraRelatorio(caminho, nomeRelatorio, getConteudo(), map);
	}
	
	public void gerarRelatorioDetalhado() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioMedLynxTransportadoDetalhado.jasper";
		String nomeRelatorio = "RelatorioMedLynxTransportadoDetalhado-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		HashMap<String, Object> map = new HashMap<String, Object>();
		super.geraRelatorio(caminho, nomeRelatorio, getConteudo(), map);
	}

}
