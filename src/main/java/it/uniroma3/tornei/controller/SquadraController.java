package it.uniroma3.tornei.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.tornei.model.Squadra;
import it.uniroma3.tornei.service.SquadraService;
import jakarta.validation.Valid;

@Controller
public class SquadraController {
	
	private final SquadraService squadraService;
	
	public SquadraController(SquadraService squadraService) {
		this.squadraService = squadraService;
	}
	
	/* VISUALIZZAZIONE LISTA E DETTAGLIO SQUADRE */

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
	
	/* INSERIMENTO NUOVA SQUADRA */
	
	@GetMapping("/admin/squadra/new")
	public String mostraFormSquadra(Model model) {
		
		model.addAttribute("squadra", new Squadra());
		
		return "admin/squadre/form";
	}
	
	@PostMapping("/admin/squadre")
	public String saveSquadra(@Valid @ModelAttribute("squadra") Squadra squadra,
			BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "admin/squadre/form";
		}
		
		this.squadraService.saveSquadra(squadra);
		return "redirect:/squadre";
	}
	
	/* ELIMINAZIONE SQUADRA */
	
	@GetMapping("/admin/squadra/{id}/delete")
	public String eliminaSquadra(@PathVariable("id") Long id) {
		
		this.squadraService.deleteSquadra(id);
		
		return "redirect:/squadre";
	}
	
	/* MODIFICA SQUADRA */
	
	@GetMapping("/admin/squadra/{id}/edit")
	public String mostraFormModifica(@PathVariable("id") Long id, Model model) {
		
		Squadra squadra = this.squadraService.getSquadra(id);
		if(squadra == null)
			return "redirect:/";
		
		model.addAttribute("squadra", squadra);
		
		return "admin/squadre/form";
	}
	
	@PostMapping("/admin/squadra/{id}/edit")
	public String modificaSquadra(@PathVariable("id") Long id, 
			@Valid @ModelAttribute("squadra") Squadra squadraModificata,
			BindingResult bindingResult) {
		
		Squadra squadraOriginale = this.squadraService.getSquadra(id);
		if(squadraOriginale == null)
			return "redirect:/";
		
		if (bindingResult.hasErrors()) {
			return "admin/squadre/form";
		}
		
		squadraOriginale.setNome(squadraModificata.getNome());
		squadraOriginale.setAnnoFondazione(squadraModificata.getAnnoFondazione());
		squadraOriginale.setCitta(squadraModificata.getCitta());
		
		this.squadraService.saveSquadra(squadraOriginale);
		return "redirect:/squadra/" + squadraOriginale.getId();
	}
}
