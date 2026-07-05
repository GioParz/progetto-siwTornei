package it.uniroma3.tornei.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.tornei.model.Squadra;
import it.uniroma3.tornei.service.SquadraService;

@Controller
public class SquadraController {
	
	@Autowired
	private SquadraService squadraService;
	
	@GetMapping("/squadre")
	public String getSquadre(Model model) {
		
		List<Squadra> listaSquadre = this.squadraService.getAllSquadre();
		model.addAttribute("squadre", listaSquadre);
		
		return "squadre/list";
	}
	
	@GetMapping("/squadra/{id}")
	public String getSquadra(@PathVariable("id") Long id, Model model) {
		
		Squadra squadra = this.squadraService.getSquadra(id);
		
		if (squadra == null)
			return "redirect:/squadre";
		
		model.addAttribute("squadra", squadra);
		
		return "squadre/show";
	}
	
	@GetMapping("/squadra/new")
	public String mostraFormSquadra(Model model) {
		
		model.addAttribute("squadra", new Squadra());
		
		return "squadre/form";
	}
	
	@PostMapping("/squadre")
	public String saveSquadra(@ModelAttribute("squadra") Squadra squadra) {
		
		this.squadraService.saveSquadra(squadra);
		
		return "redirect:/squadre";
	}
}
