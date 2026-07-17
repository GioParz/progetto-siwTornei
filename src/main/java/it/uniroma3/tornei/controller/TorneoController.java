package it.uniroma3.tornei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.tornei.exception.ModificaAnnoTorneoException;
import it.uniroma3.tornei.exception.TorneoDuplicatoException;
import it.uniroma3.tornei.exception.TorneoInUsoException;
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

		model.addAttribute("tornei" ,this.torneoService.getAllTornei());
		
		return "tornei/list";
	}
	
	@GetMapping("/torneo/{id}")
	public String getTorneo(@PathVariable("id") Long id, Model model) {
		
		Torneo torneo = this.torneoService.getTorneo(id);
		if (torneo == null)
			return "redirect:/tornei";
		
		model.addAttribute("torneo", torneo);
		model.addAttribute("classifica", this.torneoService.calcolaClassifica(id));
		
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
		
		if (bindingResult.hasErrors()) {
			return "admin/tornei/form";
		}
		
		try {
			this.torneoService.saveTorneo(torneo);
		} catch (TorneoDuplicatoException e) {
			bindingResult.reject("torneo.duplicate", e.getMessage());
			return "admin/tornei/form";
		}
		
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
		
		if (bindingResult.hasErrors()) {
            return "admin/tornei/form";
        }
        
        try {
            this.torneoService.updateTorneo(id, torneoModificato);
        } catch (TorneoDuplicatoException e) {
            bindingResult.reject("torneo.duplicato", e.getMessage());
            return "admin/tornei/form";
        } catch (ModificaAnnoTorneoException e) {
            bindingResult.rejectValue("anno", "torneo.annoVincolato", e.getMessage());
            return "admin/tornei/form";
        } catch (IllegalArgumentException e) {
            return "redirect:/tornei";
        }
        
        return "redirect:/torneo/" + id;
	}
	
	/* ELIMINAZIONE TORNEO */
	
	@PostMapping("/admin/torneo/{id}/delete")
	public String eliminaTorneo(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		
		try {
			this.torneoService.deleteTorneo(id);
			redirectAttributes.addFlashAttribute("successMessage", "Torneo eliminato con successo.");
		} catch (TorneoInUsoException e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		
		return "redirect:/tornei";
	}
}
