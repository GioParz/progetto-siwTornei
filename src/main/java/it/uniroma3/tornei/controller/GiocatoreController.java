package it.uniroma3.tornei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.tornei.model.Giocatore;
import it.uniroma3.tornei.service.GiocatoreService;

@Controller
public class GiocatoreController {
	
	@Autowired
	private GiocatoreService giocatoreService;
	
	@GetMapping("giocatore/{id}")
	public String getGiocatore(@PathVariable("id") Long id, Model model) {
		
		Giocatore giocatore = this.giocatoreService.getGiocatore(id);
		
		if (giocatore == null)
			return "redirect:/giocatori";
		
		model.addAttribute("giocatore", giocatore);
		
		return "giocatori/show";
	}
}
