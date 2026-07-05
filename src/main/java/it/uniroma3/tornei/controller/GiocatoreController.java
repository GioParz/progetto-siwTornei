package it.uniroma3.tornei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.tornei.model.Giocatore;
import it.uniroma3.tornei.model.RuoloGiocatore;
import it.uniroma3.tornei.model.Squadra;
import it.uniroma3.tornei.service.GiocatoreService;
import it.uniroma3.tornei.service.SquadraService;

@Controller
public class GiocatoreController {
	
	@Autowired
	private GiocatoreService giocatoreService;
	@Autowired
	private SquadraService squadraService;
	
	@GetMapping("giocatore/{id}")
	public String getGiocatore(@PathVariable("id") Long id, Model model) {
		
		Giocatore giocatore = this.giocatoreService.getGiocatore(id);
		
		if (giocatore == null)
			return "redirect:/giocatori";
		
		model.addAttribute("giocatore", giocatore);
		
		return "giocatori/show";
	}
	
	@GetMapping("/squadra/{squadraId}/giocatore/new")
	public String mostraFormGiocatore(@PathVariable("squadraId") Long squadraId, Model model) {
		//recupera la squadra attuale
		Squadra squadra = this.squadraService.getSquadra(squadraId);
		if (squadra == null)
			return "redirect:/squadre";
		Giocatore giocatore = new Giocatore();
		//imposta automaticamente la squadra del giocatore
		giocatore.setSquadra(squadra);
		
		model.addAttribute("giocatore", giocatore);
		model.addAttribute("ruoli", RuoloGiocatore.values());
		
		return "giocatori/form";
	}
	
	@PostMapping("/giocatori")
	public String saveGiocatore(@ModelAttribute("giocatore") Giocatore giocatore) {
		
		this.giocatoreService.saveGiocatore(giocatore);
		
		return "redirect:/squadra/" + giocatore.getSquadra().getId();
	}
}
