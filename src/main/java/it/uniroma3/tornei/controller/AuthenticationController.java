package it.uniroma3.tornei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.tornei.model.Credentials;
import it.uniroma3.tornei.model.RuoloUtente;
import it.uniroma3.tornei.model.Utente;
import it.uniroma3.tornei.service.CredentialsService;

@Controller
public class AuthenticationController {
	
	private final CredentialsService credentialsService;
	
	public AuthenticationController(CredentialsService credentialsService) {
		this.credentialsService = credentialsService;
	}
	
	@GetMapping("/login")
	public String mostraFormLogin(Model model) {
		return "authentication/login";
	}
	
	@GetMapping("/register")
	public String mostraFormRegistrazione(Model model) {
		
		model.addAttribute("utente", new Utente());
		model.addAttribute("credentials", new Credentials());
		
		return "authentication/register";
	}
	
	@PostMapping("/register")
	public String registraUtente(@ModelAttribute("utente") Utente utente,
			BindingResult utenteBindingResult,
			@ModelAttribute("credentials") Credentials credentials,
			BindingResult credentialsBindingResult) {
		
		if(!utenteBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			
			credentials.setUtente(utente);
			credentials.setRuolo(RuoloUtente.USER);
			
			this.credentialsService.saveCredentials(credentials);
			
			return "redirect:/login";
		}
		
		return "authentication/register";
	}
}
