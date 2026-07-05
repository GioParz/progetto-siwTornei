package it.uniroma3.tornei.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.tornei.model.Torneo;
import it.uniroma3.tornei.service.TorneoService;

@Controller
public class TorneoController {
	
	@Autowired
	private TorneoService torneoService;
	
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
		
		return "tornei/show";
	}
}
