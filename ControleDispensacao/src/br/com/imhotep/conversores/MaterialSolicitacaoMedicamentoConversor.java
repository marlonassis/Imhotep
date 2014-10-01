package br.com.imhotep.conversores;



import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.imhotep.entidade.extra.MaterialSolicitacaoMedicamento;
import br.com.imhotep.raiz.SolicitacaoMedicamentoUnidadeSolicitacaoRaiz;
import br.com.remendo.gerenciador.ControleInstancia;

@FacesConverter(value="materialSolicitacaoMedicamentoConversor")
public class MaterialSolicitacaoMedicamentoConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
			try {
				int id = Integer.parseInt(submittedValue);
				ControleInstancia ci = new ControleInstancia();
				SolicitacaoMedicamentoUnidadeSolicitacaoRaiz raiz = 
						(SolicitacaoMedicamentoUnidadeSolicitacaoRaiz) ci.procuraInstancia(SolicitacaoMedicamentoUnidadeSolicitacaoRaiz.class);
				for(MaterialSolicitacaoMedicamento obj : raiz.getMateriaisCadastrados()){
					if(obj.getIdMaterialSolicitacaoMedicamento() == id){
						return obj;
					}
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
        return new MaterialSolicitacaoMedicamento();
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		MaterialSolicitacaoMedicamento v = (MaterialSolicitacaoMedicamento) value;
    		if(v != null)
    			return String.valueOf(v.getIdMaterialSolicitacaoMedicamento());
        }
    	
    	return "0";
    }
}
                    