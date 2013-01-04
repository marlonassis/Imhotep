package br.com.ControleDispensacao.auxiliar;

import java.util.ArrayList;  
import java.util.List;  

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
  
@ManagedBean(name="autoCompleteBean")
@SessionScoped
class AutoCompleteBean {  
  
    private String text;  
      
    public String getText() {  
        return text;  
    }  
    public void setText(String text) {  
        this.text = text;  
    }  
      
    public List<String> complete(String query) {  
        List<String> results = new ArrayList<String>();  
          
        for (int i = 0; i < 10; i++) {  
            results.add(query + i);  
        }  
          
        return results;  
    }     
}  