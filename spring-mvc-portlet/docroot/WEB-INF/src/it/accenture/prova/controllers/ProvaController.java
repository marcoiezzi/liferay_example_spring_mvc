package it.accenture.prova.controllers;

import java.util.List;

import it.accenture.prova.facade.GestioneUtenteProvaFacade;
import it.accenture.prova.model.UtenteProva;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller(value = "ProvaController")
@RequestMapping("VIEW")
public class ProvaController {
	
	@Autowired
	private GestioneUtenteProvaFacade facade;
	
	
	@RenderMapping
	 public String handleRenderRequest(RenderRequest request,RenderResponse response,Model model){
		List<UtenteProva> allUtenti=facade.findAllUtenti();
		model.addAttribute("allUtenti",allUtenti);
	  return "defaultRender";
	 }

}
