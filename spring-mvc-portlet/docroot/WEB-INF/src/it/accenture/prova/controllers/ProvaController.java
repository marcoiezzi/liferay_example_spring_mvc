package it.accenture.prova.controllers;

import it.accenture.prova.facade.GestioneUtenteProvaFacade;
import it.accenture.prova.model.UtenteProva;
import it.accenture.prova.utils.D3PieConverter;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

@Controller(value = "ProvaController")
@RequestMapping("VIEW")
public class ProvaController {
	
	@Autowired
	private GestioneUtenteProvaFacade facade;
		
	@RenderMapping
	 public String handleRenderRequest(RenderRequest request,RenderResponse response,Model model){
	  return "defaultRender";
	 }

	@ResourceMapping("utenteResource")
	public View drawAgeDIstributionPie(){
		List<UtenteProva> allUtenti=facade.findAllUtenti();
		List <D3PieConverter> converter = new ArrayList<D3PieConverter>();
		int countLessThanFifty=0;
		int countMoreEqualsFifty=0;
		
		for (UtenteProva utente:allUtenti){
			if (utente.getAge().intValue()>=50){
				countMoreEqualsFifty++;
			}else{
				countLessThanFifty++;
			}
		}
		
	    MappingJacksonJsonView view = new MappingJacksonJsonView();
	    D3PieConverter conv1=new D3PieConverter();
	    D3PieConverter conv2=new D3PieConverter();
	    conv1.setLabel("Age less than 50");
	    conv1.setValue(countLessThanFifty);
	    conv1.setColor("#0c6197");
	    conv2.setLabel("Age more or equals than 50");
	    conv2.setValue(countMoreEqualsFifty);
	    conv2.setColor("#90c469");
	    converter.add(conv1);
	    converter.add(conv2);
	    view.addStaticAttribute("utenti", converter);
	    return view;
	}
	
}
