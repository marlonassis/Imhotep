package br.com.ControleDispensacao.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_autoriza_menu")
public class AutorizaMenu {
	private int idAutorizaMenu;
	private Especialidade especialidade;
	private Menu menu;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_autoriza_menu_id_autoriza_menu_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_autoriza_menu", unique = true, nullable = false)
	public int getIdAutorizaMenu() {
		return this.idAutorizaMenu;
	}
	
	public void setIdAutorizaMenu(int idAutorizaMenu){
		this.idAutorizaMenu = idAutorizaMenu;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_menu")
	public Menu getMenu(){
		return menu;
	}
	
	public void setMenu(Menu menu){
		this.menu = menu;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_especialidade")
	public Especialidade getEspecialidade(){
		return especialidade;
	}
	
	public void setEspecialidade(Especialidade especialidade){
		this.especialidade = especialidade;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof AutorizaMenu))
			return false;
		
		return ((AutorizaMenu)obj).getIdAutorizaMenu() == this.idAutorizaMenu;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + especialidade.hashCode() + menu.hashCode();
	}

	@Override
	public String toString() {
		return especialidade.getDescricao().concat(" - ").concat(menu.getDescricao());
	}
	
}
