package it.uniroma3.tornei.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import it.uniroma3.tornei.model.Commento;
import it.uniroma3.tornei.model.Credentials;
import it.uniroma3.tornei.model.Partita;
import it.uniroma3.tornei.service.CommentoService;
import it.uniroma3.tornei.service.CredentialsService;
import it.uniroma3.tornei.service.PartitaService;
import jakarta.validation.Valid;

@Controller
public class CommentoController {
	
	private final CommentoService commentoService;
	private final PartitaService partitaService;
	private final CredentialsService credentialsService;
	
	public CommentoController(CommentoService commentoService, PartitaService partitaService,
			CredentialsService credentialsService) {
		this.commentoService = commentoService;
		this.partitaService = partitaService;
		this.credentialsService = credentialsService;
	}
	
	/* INSERIMENTO NUOVO COMMENTO */
	
	@PostMapping("/partita/{partitaId}/commento")
	public String aggiungiCommento(@PathVariable("partitaId") Long partitaId, 
			@Valid @ModelAttribute("nuovoCommento") Commento commento,
			BindingResult bindingResult, Model model,
			@AuthenticationPrincipal UserDetails userDetails) {
		
		Partita partita = this.partitaService.getPartita(partitaId);
		if(partita == null)
			return "redirect:/";
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("partita", partita);
			model.addAttribute("torneo", partita.getTorneo());
			return "partite/show";
		}
		
		commento.setPartita(partita);
		commento.setUtente(this.credentialsService.getCredentialsByUsername(userDetails.getUsername()).getUtente());
		
		this.commentoService.saveCommento(commento);
		
		return "redirect:/partita/" + partitaId;
	}
	
	/* CANCELLAZIONE COMMENTO */
	
	@GetMapping("/commento/{id}/delete")
	public String eliminaCommento(@PathVariable("id") Long id,
			@AuthenticationPrincipal UserDetails userDetails) {
			
		Commento commento = this.commentoService.getCommento(id);
		if(commento == null)
			return "redirect:/";
		
		Credentials credentialsLoggato = this.credentialsService.getCredentialsByUsername(userDetails.getUsername());
		
		boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
		boolean isAutore = commento.getUtente().getId().equals(credentialsLoggato.getUtente().getId());
		
		if(isAdmin || isAutore) {
			this.commentoService.deleteCommento(id);
			
			return "redirect:/partita/" + commento.getPartita().getId();
		}
		
		return "error/403";
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
			@Valid @ModelAttribute("commento") Commento commentoModificato,
			BindingResult bindingResult,
			@AuthenticationPrincipal UserDetails userDetails) {
		
		Commento commentoOriginale = this.commentoService.getCommento(id);
		if(commentoOriginale == null)
			return "redirect:/";
		
		Credentials credentialsLoggato = this.credentialsService.getCredentialsByUsername(userDetails.getUsername());
		if(!commentoOriginale.getUtente().getId().equals(credentialsLoggato.getUtente().getId()))
			return "error/403";
		
		if (bindingResult.hasErrors()) {
			return "commenti/formModifica"; // Torna alla pagina di modifica mostrando l'errore
		}
		
		commentoOriginale.setTesto(commentoModificato.getTesto());
		this.commentoService.saveCommento(commentoOriginale);
		
		return "redirect:/partita/" + commentoOriginale.getPartita().getId();
	}
}
