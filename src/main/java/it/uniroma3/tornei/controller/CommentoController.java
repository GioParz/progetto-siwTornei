package it.uniroma3.tornei.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.tornei.model.Commento;
import it.uniroma3.tornei.model.Credentials;
import it.uniroma3.tornei.model.Partita;
import it.uniroma3.tornei.service.CommentoService;
import it.uniroma3.tornei.service.CredentialsService;
import it.uniroma3.tornei.service.PartitaService;
import it.uniroma3.tornei.service.UtenteService;

@Controller
public class CommentoController {
	
	private final CommentoService commentoService;
	private final PartitaService partitaService;
	private final CredentialsService credentialsService;
	
	public CommentoController(CommentoService commentoService, PartitaService partitaService,
			UtenteService utenteService, CredentialsService credentialsService) {
		this.commentoService = commentoService;
		this.partitaService = partitaService;
		this.credentialsService = credentialsService;
	}
	
	/* INSERIMENTO NUOVO COMMENTO */
	
	@PostMapping("/partita/{partitaId}/commento")
	public String aggiungiCommento(@PathVariable("partitaId") Long partitaId, 
			@RequestParam("testo") String testo,
			@AuthenticationPrincipal UserDetails userDetails) {
		
		Partita partita = this.partitaService.getPartita(partitaId);
		
		if(partita != null && testo != null && !testo.trim().isEmpty()) {
			
			Commento commento = new Commento();
			commento.setTesto(testo);
			commento.setPartita(partita);
			Credentials credentialsLoggato = this.credentialsService.getCredentialsByUsername(userDetails.getUsername());
			commento.setUtente(credentialsLoggato.getUtente());
			
			this.commentoService.saveCommento(commento);
		}
		
		return "redirect:/partita/" + partitaId;
	}
	
	/* CANCELLAZIONE COMMENTO */
	
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
	
	/* MODIFICA COMMENTO */
	
	@GetMapping("/commento/{id}/edit")
	public String mostraFormModifica(@PathVariable("id") Long id, 
			@AuthenticationPrincipal UserDetails userDetails, Model model) {
		
		Commento commento = this.commentoService.getCommento(id);
		
		if (commento == null)
			return "redirect:/";
		
		Credentials credentialsLoggato = this.credentialsService.getCredentialsByUsername(userDetails.getUsername());
		if(!commento.getUtente().getId().equals(credentialsLoggato.getUtente().getId()))
			return "error/403";
		
		model.addAttribute("commento", commento);
		
		return "commenti/formModifica";
	}
	
	@PostMapping("/commento/{id}/edit")
	public String modificaCommento(@PathVariable("id") Long id,
			@ModelAttribute("commento") Commento commentoModificato,
			@AuthenticationPrincipal UserDetails userDetails) {
		
		Commento commentoOriginale = this.commentoService.getCommento(id);
		if(commentoOriginale == null)
			return "redirect:/";
		
		Credentials credentialsLoggato = this.credentialsService.getCredentialsByUsername(userDetails.getUsername());
		//gli utenti possono modificare solo i propri commenti
		if(!commentoOriginale.getUtente().getId().equals(credentialsLoggato.getUtente().getId()))
			return "error/403";
		
		//se nulla è stato modificato non c'è bisogno di salvare il commento
		if(commentoOriginale.getTesto().trim().equals(commentoModificato.getTesto().trim()))
			return "redirect:/partita/" + commentoOriginale.getPartita().getId();
		
		commentoOriginale.setTesto(commentoModificato.getTesto());
		this.commentoService.saveCommento(commentoOriginale);
		
		return "redirect:/partita/" + commentoOriginale.getPartita().getId();
	}
}
