package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Cargo;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class CargoRaiz extends PadraoRaiz<Cargo>{
	private String cargoFilho;
	private Cargo cargoDelete;
	private List<Cargo> cargosFilho = new ArrayList<Cargo>();
	
	public void cadastrarCargoFilho(){
		Cargo cargoFilho = new Cargo();
		cargoFilho.setCargoPai(getInstancia());
		cargoFilho.setNome(getCargoFilho());
		if(super.enviarGenerico(cargoFilho)){
			getCargosFilho().add(cargoFilho);
			setCargoFilho(null);
		}
	}
	
	public void removerCargoFilho(){
		if(super.apagarGenerico(cargoDelete)){
			getCargosFilho().remove(cargoDelete);
		}
		setCargoDelete(null);
	}
	
	@Override
	protected void aposEnviar() {
		carregarCargosFilho();
		super.aposEnviar();
	}
	
	@Override
	public void setInstancia(Cargo instancia) {
		super.setInstancia(instancia);
		carregarCargosFilho();
	}

	private void carregarCargosFilho() {
		setCargosFilho(super.getBusca("select o from Cargo o where o.cargoPai.idCargo = " + getInstancia().getIdCargo()));
	}
	
	public String getCargoFilho() {
		return cargoFilho;
	}

	public void setCargoFilho(String cargoFilho) {
		this.cargoFilho = cargoFilho;
	}

	public List<Cargo> getCargosFilho() {
		return cargosFilho;
	}

	public void setCargosFilho(List<Cargo> cargosFilho) {
		this.cargosFilho = cargosFilho;
	}

	public Cargo getCargoDelete() {
		return cargoDelete;
	}

	public void setCargoDelete(Cargo cargoDelete) {
		this.cargoDelete = cargoDelete;
	}
	
	
}
