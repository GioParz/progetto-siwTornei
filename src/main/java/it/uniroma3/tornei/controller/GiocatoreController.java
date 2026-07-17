package it.uniroma3.tornei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.tornei.exception.GiocatoreDuplicatoException;
import it.uniroma3.tornei.model.Giocatore;
import it.uniroma3.tornei.model.Squadra;
import it.uniroma3.tornei.service.GiocatoreService;
import it.uniroma3.tornei.service.SquadraService;
import jakarta.validation.Valid;

@Controller
public class GiocatoreController {
	
	private final GiocatoreService giocatoreService;
	private final SquadraService squadraService;
	
	public GiocatoreController(GiocatoreService giocatoreService, SquadraService squadraService) {
		this.giocatoreService = giocatoreService;
		this.squadraService = squadraService;
	}
	
	/* DETTAGLIO GIOCATORE */
	
	@GetMapping("/giocatore/{id}")
	public String getGiocatore(@PathVariable("id") Long id, Model model) {
		
		Giocatore giocatore = this.giocatoreService.getGiocatore(id);
		if (giocatore == null)
			return "redirect:/giocatori";
		
		model.addAttribute("giocatore", giocatore);
		
		return "giocatori/show";
	}
	
	/* INSERIMENTO NUOVO GIOCATORE */
	
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
		
		return "giocatori/form";
	}
	
	@PostMapping("/giocatori")
	public String saveGiocatore(@Valid @ModelAttribute("giocatore") Giocatore giocatore,
			BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			if (giocatore.getSquadra() != null)
				model.addAttribute("squadra", this.squadraService.getSquadra(giocatore.getSquadra().getId()));
			
			return "giocatori/form";
		}
		
		try {
			this.giocatoreService.saveGiocatore(giocatore);
		} catch (GiocatoreDuplicatoException e) {
			bindingResult.reject("giocatore.duplicato", e.getMessage());
			model.addAttribute("squadra", giocatore.getSquadra());
			return "giocatori/form";
		}
		
		return "redirect:/squadra/" + giocatore.getSquadra().getId();
	}
	
	/* MODIFICA GIOCATORE */
	
	@GetMapping("/admin/giocatore/{id}/edit")
	public String mostraFormModifica(@PathVariable("id") Long id, Model model) {
		
		Giocatore giocatore = this.giocatoreService.getGiocatore(id);
		if(giocatore == null)
			return "redirect:/";
		
		model.addAttribute("giocatore", giocatore);
		model.addAttribute("squadre", this.squadraService.getAllSquadre());
		
		return "admin/giocatori/formModifica";
	}
	
	@PostMapping("/admin/giocatore/{id}/edit")
	public String modificaGiocatore(@PathVariable("id") Long id,
			@Valid @ModelAttribute("giocatore") Giocatore giocatoreModificato,
			BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("squadre", this.squadraService.getAllSquadre());
			return "admin/giocatori/formModifica";
		}
		
		try {
			Giocatore aggiornato = this.giocatoreService.updateGiocatore(id, giocatoreModificato);
			if (aggiornato == null) {
				return "redirect:/";
			}
			
			return "redirect:/squadra/" + aggiornato.getSquadra().getId();
		} catch (GiocatoreDuplicatoException e) {
			bindingResult.reject("giocatore.duplicato", e.getMessage());
			model.addAttribute("squadre", this.squadraService.getAllSquadre());
			return "admin/giocatori/formModifica";
		}
	}
}
