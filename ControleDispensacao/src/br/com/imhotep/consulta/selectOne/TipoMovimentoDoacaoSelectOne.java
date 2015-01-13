package br.com.imhotep.consulta.selectOne;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.TipoMovimento;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class TipoMovimentoDoacaoSelectOne extends ConsultaGeral<TipoMovimento> {
	
	public List<TipoMovimento> getItens(){
		String idsDoacao =  Constantes.ID_TIPO_MOVIMENTO_DOACAO_ENTRADA + "," + Constantes.ID_TIPO_MOVIMENTO_DOACAO_SAIDA;
		StringBuilder stringB = new StringBuilder("select o from TipoMovimento o where o.idTipoMovimento in (" + idsDoacao + ")");
		return new ArrayList<TipoMovimento>(super.consulta(stringB, null));
	}
	
}
