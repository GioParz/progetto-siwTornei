package it.uniroma3.tornei.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.tornei.model.RigaClassifica;
import it.uniroma3.tornei.model.Torneo;
import it.uniroma3.tornei.service.TorneoService;
import jakarta.validation.Valid;

@Controller
public class TorneoController {
	
	private final TorneoService torneoService;
	
	public TorneoController(TorneoService torneoService) {
		this.torneoService = torneoService;
	}
	
	/* VISUALIZZAZIONE TORNEI E DETTAGLIO */
	
	@GetMapping("/tornei")
	public String getTornei(Model model) {
		
		List<Torneo> listaTornei = this.torneoService.getAllTornei();
		model.addAttribute("tornei" ,listaTornei);
		
		return "tornei/list";
	}
	
	@GetMapping("/torneo/{id}")
	public String getTorneo(@PathVariable("id") Long id, Model model) {
		
		Torneo torneo = this.torneoService.getTorneo(id);
		
		if (torneo == null)
			return "redirect:/tornei";
		
		model.addAttribute("torneo", torneo);
		
		List<RigaClassifica> classifica = this.torneoService.calcolaClassifica(id);
		model.addAttribute("classifica", classifica);
		
		return "tornei/show";
	}
	
	/* CREAZIONE NUOVO TORNEO */
	
	@GetMapping("/admin/torneo/new")
	public String mostraFormTorneo(Model model) {
		
		model.addAttribute("torneo", new Torneo());
		
		return "admin/tornei/form";
	}
	
	@PostMapping("/admin/tornei")
	public String saveTorneo(@Valid @ModelAttribute("torneo") Torneo torneo, 
			BindingResult bindingResult) {
		
		if (!bindingResult.hasErrors()) {
			if (this.torneoService.existsByNomeAndAnno(torneo.getNome(), torneo.getAnno())) {
				bindingResult.reject("torneo.duplicato");
			}
		}
		
		if (bindingResult.hasErrors()) {
			return "admin/tornei/form";
		}
		
		this.torneoService.saveTorneo(torneo);
		return "redirect:/tornei";
	}
	
	/* MODIFICA TORNEO */
	
	@GetMapping("/admin/torneo/{id}/edit")
	public String mostraFormModifica(@PathVariable("id") Long id, Model model) {
		
		Torneo torneo = this.torneoService.getTorneo(id);
		if(torneo == null)
			return "redirect:/";
		
		model.addAttribute("torneo", torneo);
		
		return "admin/tornei/form";
	}
	
	@PostMapping("/admin/torneo/{id}/edit")
	public String modificaTorneo(@PathVariable("id") Long id, 
			@Valid @ModelAttribute("torneo") Torneo torneoModificato,
			BindingResult bindingResult) {
		
		Torneo torneoOriginale = this.torneoService.getTorneo(id);
		if(torneoOriginale == null)
			return "redirect:/";
		
		if (bindingResult.hasErrors()) {
			return "admin/tornei/form";
		}
		
		torneoOriginale.setNome(torneoModificato.getNome());
		torneoOriginale.setAnno(torneoModificato.getAnno());
		torneoOriginale.setDescrizione(torneoModificato.getDescrizione());
		
		this.torneoService.saveTorneo(torneoOriginale);
		return "redirect:/torneo/" + torneoOriginale.getId();
	}
}
