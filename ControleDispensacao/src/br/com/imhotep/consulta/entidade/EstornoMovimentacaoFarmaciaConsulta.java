package br.com.imhotep.consulta.entidade;

	import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.raiz.EstornoMovimentacaoFarmaciaRaiz;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;
import br.com.remendo.gerenciador.ControleInstancia;

	@ManagedBean
	@SessionScoped
	public class EstornoMovimentacaoFarmaciaConsulta extends PadraoConsulta<MovimentoLivro> {
		
		private Estoque estoque = new Estoque();
		
		public EstornoMovimentacaoFarmaciaConsulta(){
			setOrderBy("o.dataMovimento desc");
		}
		
		@Override
		public void novaInstancia() {
			setEstoque(new Estoque());
			super.novaInstancia();
			setLista(new ArrayList<MovimentoLivro>());
			try {
				EstornoMovimentacaoFarmaciaRaiz a = (EstornoMovimentacaoFarmaciaRaiz) new ControleInstancia().procuraInstancia(EstornoMovimentacaoFarmaciaRaiz.class);
				a.setEstoquesFarmacia(new ArrayList<Estoque>());
				a.setMaterial(new Material());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void carregarResultado() {
			String sql = "";
			setConsultaGeral(new ConsultaGeral<MovimentoLivro>());
			if(getEstoque() != null && getEstoque().getIdEstoque() != 0){
				int id = getEstoque().getIdEstoque();
				String da = new SimpleDateFormat("yyyy-MM").format(new Date());
				sql = "select o from MovimentoLivro o where to_char(o.dataMovimento, 'yyyy-MM') = '"+
							da+"' and o.estoque.idEstoque = "+id+" ";
			}
			getConsultaGeral().setSqlConsultaSB(new StringBuilder(sql));
			super.carregarResultado();
		}

		public Estoque getEstoque() {
			return estoque;
		}

		public void setEstoque(Estoque estoque) {
			this.estoque = estoque;
		}
		
	}
	
