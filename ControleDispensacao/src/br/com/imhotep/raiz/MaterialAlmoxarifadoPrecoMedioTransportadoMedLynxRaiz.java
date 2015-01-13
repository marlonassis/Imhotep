package br.com.imhotep.raiz;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.MaterialAlmoxarifadoPrecoMedioTransportadoMedLynx;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class MaterialAlmoxarifadoPrecoMedioTransportadoMedLynxRaiz extends PadraoRaiz<MaterialAlmoxarifadoPrecoMedioTransportadoMedLynx>{
	
	public MaterialAlmoxarifadoPrecoMedioTransportadoMedLynxRaiz(){
		super();
	}
	
	public MaterialAlmoxarifadoPrecoMedioTransportadoMedLynxRaiz(MaterialAlmoxarifadoPrecoMedioTransportadoMedLynx instancia){
		setInstancia(instancia);
	}
	
	public MaterialAlmoxarifadoPrecoMedioTransportadoMedLynxRaiz(MaterialAlmoxarifadoPrecoMedioTransportadoMedLynx instancia, boolean exibirMensagemInsercao, boolean exibirMensagemAtualizacao){
		setInstancia(instancia);
		setExibeMensagemInsercao(exibirMensagemInsercao);
		setExibeMensagemAtualizacao(exibirMensagemAtualizacao);
	}
	
	@Override
	public boolean enviar() {
		Integer saldo = getInstancia().getSaldo();
		Double precoMedio = getInstancia().getPrecoMedio();
		String update = "";
		int idMaterialAlmoxarifado = getInstancia().getMaterialAlmoxarifado().getIdMaterialAlmoxarifado();
		List<MaterialAlmoxarifadoPrecoMedioTransportadoMedLynx> busca = super.getBusca("select o from MaterialAlmoxarifadoPrecoMedioTransportadoMedLynx o where o.materialAlmoxarifado.idMaterialAlmoxarifado = "+idMaterialAlmoxarifado);
		LinhaMecanica lm = new LinhaMecanica();
		lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
		lm.setIp(Constantes.IP_LOCAL);
		if(busca != null && !busca.isEmpty())
			return atualizarPrecoMedioSaldo(saldo, precoMedio, update, idMaterialAlmoxarifado, lm);
		else{
			return inserirPrecoMedioSaldo(saldo, precoMedio, idMaterialAlmoxarifado, lm);
		}
			
	}

	private boolean inserirPrecoMedioSaldo(Integer saldo, Double precoMedio,
			int idMaterialAlmoxarifado, LinhaMecanica lm) {
		String insert = "insert into tb_material_almoxarifado_preco_medio_transportado_medlynx(in_saldo_transportado, db_preco_medio_transportado, id_material_almoxarifado, dt_data_cadastro) values("+saldo+", "+precoMedio+", "+idMaterialAlmoxarifado+", now())";
		boolean executarCUD = lm.executarCUD(insert);
		if(executarCUD){
			super.mensagem("Cadastro realizado com suscesso", null, Constantes.INFO);
			novaInstancia();
		}else
			super.mensagem("Erro ao cadastrar", null, Constantes.ERROR);
		return executarCUD;
	}

	private boolean atualizarPrecoMedioSaldo(Integer saldo, Double precoMedio, String update,
			int idMaterialAlmoxarifado, LinhaMecanica lm) {
		if(saldo != 0 && precoMedio != 0d)
			update = "update tb_material_almoxarifado_preco_medio_transportado_medlynx set in_saldo_transportado = "+saldo+", db_preco_medio_transportado = "+precoMedio+" where id_material_almoxarifado = "+idMaterialAlmoxarifado;
		else
			if(saldo != 0 )
				update = "update tb_material_almoxarifado_preco_medio_transportado_medlynx set in_saldo_transportado = "+saldo+" where id_material_almoxarifado = "+idMaterialAlmoxarifado;
			else
				if(precoMedio != 0d)
					update = "update tb_material_almoxarifado_preco_medio_transportado_medlynx set db_preco_medio_transportado = "+precoMedio+" where id_material_almoxarifado = "+idMaterialAlmoxarifado;
		boolean executarCUD = lm.executarCUD(update);
		if(executarCUD){
			super.mensagem("Atualiza��o realizada com suscesso", null, Constantes.INFO);
			novaInstancia();
		}else
			super.mensagem("Erro ao cadastrar", null, Constantes.ERROR);
		return executarCUD;
	}
	
}
