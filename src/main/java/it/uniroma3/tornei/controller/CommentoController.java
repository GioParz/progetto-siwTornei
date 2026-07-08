package it.uniroma3.tornei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.tornei.model.Commento;
import it.uniroma3.tornei.model.Partita;
import it.uniroma3.tornei.service.CommentoService;
import it.uniroma3.tornei.service.PartitaService;

@Controller
public class CommentoController {
	
	@Autowired
	private CommentoService commentoService;
	@Autowired
	private PartitaService partitaService;
	
	@PostMapping("/partita/{partitaId}/commento")
	public String aggiungiCommento(@PathVariable("partitaId") Long partitaId, @RequestParam("testo") String testo) {
		
		Partita partita = this.partitaService.getPartita(partitaId);
		
		if(partita != null && testo != null && !testo.trim().isEmpty()) {
			
			Commento commento = new Commento();
			commento.setTesto(testo);
			commento.setPartita(partita);
			
			this.commentoService.saveCommento(commento);
		}
		
		return "redirect:/partita/" + partitaId;
	}
	
	@GetMapping("/commento/{id}/delete")
	public String eliminaCommento(@PathVariable("id") Long id) {
			
		Commento commento = this.commentoService.getCommento(id);
		Long partitaId = null;
		
		if(commento != null) {
			partitaId = commento.getPartita().getId();
			this.commentoService.deleteCommento(id);
		}
		
		return partitaId != null ? "redirect:/partita/" + partitaId : "redirect:/tornei";
	}
}
