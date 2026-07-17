package it.uniroma3.tornei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.tornei.exception.ArbitroOccupatoException;
import it.uniroma3.tornei.exception.IncompatibilitaDataPartitaException;
import it.uniroma3.tornei.model.Commento;
import it.uniroma3.tornei.model.Partita;
import it.uniroma3.tornei.model.StatoPartita;
import it.uniroma3.tornei.model.Torneo;
import it.uniroma3.tornei.service.ArbitroService;
import it.uniroma3.tornei.service.PartitaService;
import it.uniroma3.tornei.service.SquadraService;
import it.uniroma3.tornei.service.TorneoService;
import jakarta.validation.Valid;

@Controller
public class PartitaController {
	
	private final PartitaService partitaService;
	private final SquadraService squadraService;
	private final ArbitroService arbitroService;
	private final TorneoService torneoService;
	
	public PartitaController(PartitaService partitaService, SquadraService squadraService,
			ArbitroService arbitroService, TorneoService torneoService) {
		this.partitaService = partitaService;
		this.squadraService = squadraService;
		this.arbitroService = arbitroService;
		this.torneoService = torneoService;
	}
	
	/* PER VISUALIZZAZIONE PARTITA */

	@GetMapping("/partita/{id}")
	public String getPartita(@PathVariable("id") Long id, Model model) {
		
		Partita partita = this.partitaService.getPartita(id);
		if(partita == null)
			return "redirect:/tornei";
		
		model.addAttribute("partita", partita);
		model.addAttribute("torneo", partita.getTorneo());
		model.addAttribute("nuovoCommento", new Commento());
		
		return "partite/show";
	}
	
	/* PER INSERIMENTO E SALVATAGGIO PARTITA */
	
	@GetMapping("/torneo/{torneoId}/partita/new")
	public String mostraFormPartita(@PathVariable("torneoId") Long torneoId, Model model) {
		
		Torneo torneo = this.torneoService.getTorneo(torneoId);
		if (torneo == null)
			return "redirect:/tornei";
		
		Partita partita = new Partita();
		partita.setTorneo(torneo);
		
		model.addAttribute("partita", partita);
		model.addAttribute("squadre", this.squadraService.getAllSquadre());
		model.addAttribute("arbitri", this.arbitroService.getAllArbitri());
		
		return "partite/form";
	}
	
	@PostMapping("/partite")
	public String savePartita(@Valid @ModelAttribute("partita") Partita partita,
			BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("squadre", this.squadraService.getAllSquadre());
			model.addAttribute("arbitri", this.arbitroService.getAllArbitri());
			model.addAttribute("torneo", partita.getTorneo());
			return "partite/form";
		}
		
		partita.setStato(StatoPartita.PROGRAMMATA);
		partita.setGoalsHome(0);
		partita.setGoalsAway(0);
	    
	    try {
	        this.partitaService.savePartita(partita);
	    } catch (ArbitroOccupatoException e) {
	        bindingResult.reject("partita.arbitroOccupato", e.getMessage());
	        model.addAttribute("squadre", this.squadraService.getAllSquadre());
	        model.addAttribute("arbitri", this.arbitroService.getAllArbitri());
	        model.addAttribute("torneo", partita.getTorneo());
	        return "partite/form";
	    } catch (IncompatibilitaDataPartitaException e) {
	        bindingResult.reject("partita.duplicata", e.getMessage());
	        model.addAttribute("squadre", this.squadraService.getAllSquadre());
	        model.addAttribute("arbitri", this.arbitroService.getAllArbitri());
	        model.addAttribute("torneo", partita.getTorneo());
	        return "partite/form";
	    }
		return "redirect:/torneo/" + partita.getTorneo().getId();
	}
	
	/* PER INSERIMENTO RISULTATO PARTITA */
	
	@GetMapping("/admin/partita/{id}/risultato")
	public String mostraFormRisultato(@PathVariable("id") Long id, Model model) {
		
		Partita partita = this.partitaService.getPartita(id);
		if (partita == null)
			return "redirect:/tornei";
		
		model.addAttribute("partita", partita);
		
		return "admin/partite/formRisultato";
	}
	
	@PostMapping("/admin/partita/{id}/risultato")
	public String salvaRisultato(@PathVariable("id") Long id, 
			@ModelAttribute("partita") Partita partitaModificata,
			BindingResult bindingResult, Model model) {
		
		Partita partitaOriginale = this.partitaService.getPartita(id);
		if (partitaOriginale == null) {
			return "redirect:/tornei";
		}
		
		if (partitaModificata.getGoalsHome() == null || partitaModificata.getGoalsHome() < 0) {
			bindingResult.rejectValue("goalsHome", "Inserisci un punteggio valido per la squadra di casa.");
		}
		if (partitaModificata.getGoalsAway() == null || partitaModificata.getGoalsAway() < 0) {
			bindingResult.rejectValue("goalsAway", "Inserisci un punteggio valido per la squadra ospite.");
		}
		
		if (bindingResult.hasErrors()) {
	        partitaModificata.setSquadraCasa(partitaOriginale.getSquadraCasa());
	        partitaModificata.setSquadraOspite(partitaOriginale.getSquadraOspite());
	        partitaModificata.setTorneo(partitaOriginale.getTorneo());
	        partitaModificata.setLuogo(partitaOriginale.getLuogo());
	        partitaModificata.setDataEOra(partitaOriginale.getDataEOra());
	        partitaModificata.setStato(partitaOriginale.getStato());
	        return "admin/partite/formRisultato";
	    }
	    
	    partitaOriginale.setGoalsHome(partitaModificata.getGoalsHome());
	    partitaOriginale.setGoalsAway(partitaModificata.getGoalsAway());
	    partitaOriginale.setStato(StatoPartita.TERMINATA);
	    
	    try {
	        this.partitaService.savePartita(partitaOriginale);
	    } catch (RuntimeException e) {
	        bindingResult.reject("Impossibile salvare il risultato: " + e.getMessage());
	        partitaModificata.setStato(partitaOriginale.getStato());
	        return "admin/partite/formRisultato";
	    }
	    
		return "redirect:/torneo/" + partitaOriginale.getTorneo().getId();
	}
	
	/* PER ELIMINAZIONE PARTITA */
	
	@GetMapping("/admin/partita/{id}/delete")
	public String eliminaPartita(@PathVariable("id") Long id) {
		
		Partita partita = this.partitaService.getPartita(id);
		
		if(partita != null) {
			Long torneoId = partita.getTorneo().getId();
			
			this.partitaService.deletePartita(id);
			
			return "redirect:/torneo/" + torneoId;
		}
		
		return "redirect:/tornei";
	}
}
