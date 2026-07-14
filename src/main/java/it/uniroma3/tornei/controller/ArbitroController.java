package it.uniroma3.tornei.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.tornei.model.Arbitro;
import it.uniroma3.tornei.service.ArbitroService;
import jakarta.validation.Valid;

@Controller
public class ArbitroController {
	
	private final ArbitroService arbitroService;
	
	public ArbitroController(ArbitroService arbitroService) {
		this.arbitroService = arbitroService;
	}
	
	/* CONSULTA ARBITRI */

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
	
	/* INSERIMENTO NUOVI ARBITRI */
	
	@GetMapping("/admin/arbitro/new")
	public String mostraFormArbitro(Model model) {
		
		model.addAttribute("arbitro", new Arbitro());
		
		return "admin/arbitri/form";
	}
	
	@PostMapping("/admin/arbitri")
	public String saveArbitro(@Valid @ModelAttribute("arbitro") Arbitro arbitro, 
			BindingResult bindingResult) {
		
		if(bindingResult.hasErrors())
			return "admin/arbitri/form"; //torna al form mostrando gli errori
		
		if(this.arbitroService.existsByCodiceAIA(arbitro.getCodiceAIA())) {
			bindingResult.rejectValue("codiceAIA", "arbitro.duplicate");
			return "admin/arbitri/form";
		}
		
		this.arbitroService.saveArbitro(arbitro);
		
		return "redirect:/arbitri";
	}
	
	/* ELIMINAZIONE ARBITRO */
	
	@GetMapping("admin/arbitro/{id}/delete")
	public String deleteArbitro(@PathVariable("id") Long id) {
		
		this.arbitroService.deleteArbitroById(id);;
		
		return "redirect:/arbitri";
	}
}
