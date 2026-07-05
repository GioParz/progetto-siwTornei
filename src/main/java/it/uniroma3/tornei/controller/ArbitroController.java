package it.uniroma3.tornei.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.tornei.model.Arbitro;
import it.uniroma3.tornei.service.ArbitroService;

@Controller
public class ArbitroController {
	
	@Autowired
	private ArbitroService arbitroService;
	
	@GetMapping("/arbitri")
	public String getArbitri(Model model) {
		
		List<Arbitro> listaArbitri = this.arbitroService.getAllArbitri();
		model.addAttribute("arbitri", listaArbitri);
		
		return "arbitri/list";
	}
	
	@GetMapping("/arbitro/{id}")
	public String getArbitro(@PathVariable("id") Long id, Model model) {
		
		Arbitro arbitro = this.arbitroService.getArbitroById(id);
		
		if (arbitro == null)
			return "redirect:/arbitri";
		
		model.addAttribute("arbitro", arbitro);
		
		return "arbitri/show";
	}
	
	@GetMapping("/arbitro/new")
	public String mostraFormArbitro(Model model) {
		
		model.addAttribute("arbitro", new Arbitro());
		
		return "arbitri/form";
	}
	
	@PostMapping("/arbitri")
	public String saveArbitro(@ModelAttribute("arbitro") Arbitro arbitro) {
		
		this.arbitroService.saveArbitro(arbitro);
		
		return "redirect:/arbitri";
	}
}
