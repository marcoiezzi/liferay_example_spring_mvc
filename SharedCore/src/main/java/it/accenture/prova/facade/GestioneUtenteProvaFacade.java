package it.accenture.prova.facade;

import it.accenture.prova.dao.UtenteProvaDAO;

import java.util.List;

import it.accenture.prova.model.UtenteProva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("GestioneUtenteProvaFacade")
public class GestioneUtenteProvaFacade {
	
	@Autowired
	private UtenteProvaDAO utenteProvaDAO;

	
	public List<UtenteProva> findAllUtenti(){
		return utenteProvaDAO.findAll();
	}
}
