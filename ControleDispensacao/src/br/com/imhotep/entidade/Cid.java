package br.com.imhotep.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_cid")
public class Cid {
	
	private int idCid;
	private String nome;
	private String codigo;
	private String versao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_cid_10_id_cid_10_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_cid", unique = true, nullable = false)
	public int getIdCid() {
		return idCid;
	}
	public void setIdCid(int idCid) {
		this.idCid = idCid;
	}
	
	@Column(name = "cv_nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "cv_codigo")
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(name = "cv_versao")
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	
	@Transient
	public String getCodigoNome(){
		if(nome != null && codigo != null){
			if(nome.length() > 30){
				return codigo.concat(" - ").concat(nome.substring(0,30).concat("..."));
			}
			return codigo.concat(" - ").concat(nome);
		}
		return "";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Cid))
			return false;
		
		return ((Cid)obj).getIdCid() == this.idCid;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + nome.hashCode();
	}

	@Override
	public String toString() {
		return nome;
	}
}
