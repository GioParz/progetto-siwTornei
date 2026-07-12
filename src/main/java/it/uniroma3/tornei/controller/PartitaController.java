package it.uniroma3.tornei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.tornei.model.Partita;
import it.uniroma3.tornei.model.StatoPartita;
import it.uniroma3.tornei.model.Torneo;
import it.uniroma3.tornei.service.ArbitroService;
import it.uniroma3.tornei.service.PartitaService;
import it.uniroma3.tornei.service.SquadraService;
import it.uniroma3.tornei.service.TorneoService;

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
	public String savePartita(@ModelAttribute("partita") Partita partita) {
		
		partita.setStato(StatoPartita.PROGRAMMATA);
		partita.setGoalsHome(0);
		partita.setGoalsAway(0);
		partita.setSquadraCasaNomeStorico(partita.getSquadraCasa().getNome());
		partita.setSquadraOspiteNomeStorico(partita.getSquadraOspite().getNome());
		this.partitaService.savePartita(partita);
		
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
			@RequestParam("goalsHome") Integer goalsHome, 
			@RequestParam("goalsAway") Integer goalsAway) {
		
		Partita partita = this.partitaService.getPartita(id);
		if (partita != null) {
			partita.setGoalsHome(goalsHome);
			partita.setGoalsAway(goalsAway);
			partita.setStato(StatoPartita.TERMINATA);
			
			this.partitaService.savePartita(partita);
			
			return "redirect:/torneo/" + partita.getTorneo().getId();
		}
		
		return "redirect:/tornei";
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
