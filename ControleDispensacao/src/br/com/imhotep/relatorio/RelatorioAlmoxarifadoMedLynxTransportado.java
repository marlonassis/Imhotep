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
import br.com.imhotep.entidade.extra.MedLynxTransportadoAlmoxarifado;
import br.com.imhotep.linhaMecanica.LinhaMecanica;

@ManagedBean
@ViewScoped
public class RelatorioAlmoxarifadoMedLynxTransportado extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	private List<MedLynxTransportadoAlmoxarifado> getConteudo(){
		List<MedLynxTransportadoAlmoxarifado> lista = new ArrayList<MedLynxTransportadoAlmoxarifado>();
		try {
			String sql = "select b.id_material_almoxarifado idMaterialAlmoxarifado, b.cv_descricao descricao, "+ 
							"coalesce(a.in_saldo_transportado, 0) saldoTransportado, coalesce(a.db_preco_medio_transportado, 0) precoMedio "+ 
							" from tb_material_almoxarifado b  "+
							"left join tb_material_almoxarifado_preco_medio_transportado_medlynx a on a.id_material_almoxarifado = b.id_material_almoxarifado "+ 
							"order by trim(both ' ' from lower(to_ascii(b.cv_descricao)))";
			
			
			LinhaMecanica lm = new LinhaMecanica();
			lm.setNomeBanco(LinhaMecanica.DB_BANCO_IMHOTEP);
			lm.setIp(Constantes.IP_LOCAL);
			ResultSet rs = lm.consultar(lm.utf8_to_latin1(sql));
			while (rs.next()) { 
				int idMaterialAlmoxarifado = rs.getInt("idMaterialAlmoxarifado");
				String descricao = rs.getString("descricao");
				descricao = descricao == null ? null : descricao.trim().toUpperCase();
				int saldoTransportado = rs.getInt("saldoTransportado");
				double precoMedioTransportado = rs.getDouble("precoMedio");
				MedLynxTransportadoAlmoxarifado mlt = new MedLynxTransportadoAlmoxarifado(idMaterialAlmoxarifado , descricao , saldoTransportado, 0 , precoMedioTransportado , 0);
				lista.add(mlt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;	
	}
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioAlmoxarifadoMedLynxTransportado.jasper";
		String nomeRelatorio = "RelatorioAlmoxarifadoMedLynxTransportado-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		HashMap<String, Object> map = new HashMap<String, Object>();
		super.geraRelatorio(caminho, nomeRelatorio, getConteudo(), map);
	}
	
}
