package it.accenture.prova.model;

import it.accenture.prova.model.common.AbstractCommonLiferayAppPOJO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="UTENTI_PROVA")
public class UtenteProva extends AbstractCommonLiferayAppPOJO implements java.io.Serializable {

	private static final long serialVersionUID = -2853248867621139481L;

	@Id
	@Column(name="ID",unique=true, nullable=false)
	private Integer id;

	@Column(name="NOME",nullable=false,length=100)
	private String nome;
	
	@Column(name="COGNOME",nullable=false,length=100)
	private String cognome;
	
	
	@Column(name="AGE")
	private Integer age;	
	
	
	// Constructors

	/** default constructor */
	public UtenteProva() {
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public Integer getAge() {
		return age;
	}


	public void setAge(Integer age) {
		this.age = age;
	}

	

	
}