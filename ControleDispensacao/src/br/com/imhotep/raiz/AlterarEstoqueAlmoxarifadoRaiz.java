package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.consulta.raiz.EstoqueAlmoxarifadoConsultaRaiz;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.enums.TipoEstoqueAlmoxarifadoLog;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class AlterarEstoqueAlmoxarifadoRaiz extends PadraoRaiz<EstoqueAlmoxarifado>{
	
	private MaterialAlmoxarifado materialAlmoxarfado;
	private EstoqueAlmoxarifado estoqueAntigo;
	
	
	public List<EstoqueAlmoxarifado> getEstoqueAlmoxarifadoMaterial(){
		List<EstoqueAlmoxarifado> lista = new ArrayList<EstoqueAlmoxarifado>();
		if(getMaterialAlmoxarfado() != null){
			lista = new EstoqueAlmoxarifadoConsultaRaiz().consultarTodosEstoquesValidosMaterial(getMaterialAlmoxarfado());
		}
		return lista;
	}

	@Override
	public boolean atualizar() {
		EstoqueAlmoxarifadoLogRaiz ealr = new EstoqueAlmoxarifadoLogRaiz();
		Date dataLog = new Date();
		ealr.gerarLog(ealr.carregarLog(getEstoqueAntigo(), dataLog, TipoEstoqueAlmoxarifadoLog.A),
					  ealr.carregarLog(getInstancia(), dataLog, TipoEstoqueAlmoxarifadoLog.B) );
		return super.atualizar();
	}
	
	public MaterialAlmoxarifado getMaterialAlmoxarfado() {
		return materialAlmoxarfado;
	}

	public void setMaterialAlmoxarfado(MaterialAlmoxarifado materialAlmoxarfado) {
		this.materialAlmoxarfado = materialAlmoxarfado;
	}

	public EstoqueAlmoxarifado getEstoqueAntigo() {
		return estoqueAntigo;
	}

	public void setEstoqueAntigo(EstoqueAlmoxarifado estoqueAntigo) {
		this.estoqueAntigo = estoqueAntigo;
		setInstancia(estoqueAntigo.clone());
	}

	
}
