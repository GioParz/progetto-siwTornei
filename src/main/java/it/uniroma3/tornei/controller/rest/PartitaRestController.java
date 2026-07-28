package it.uniroma3.tornei.controller.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.tornei.model.Partita;
import it.uniroma3.tornei.model.StatoPartita;
import it.uniroma3.tornei.service.PartitaService;

@RestController
@RequestMapping("/api/partite")
public class PartitaRestController {

    private final PartitaService partitaService;

    public PartitaRestController(PartitaService partitaService) {
        this.partitaService = partitaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPartita(@PathVariable Long id) {
        Partita partita = this.partitaService.getPartita(id);
        if (partita == null) {
            return ResponseEntity.notFound().build();
        }

        // Mappiamo solo i dati che servono a React per evitare riferimenti circolari JPA
        Map<String, Object> dto = new HashMap<>();
        dto.put("id", partita.getId());
        dto.put("squadraCasa", partita.getSquadraCasa() != null ? partita.getSquadraCasa().getNome() : partita.getSquadraCasaNomeStorico());
        dto.put("squadraOspite", partita.getSquadraOspite() != null ? partita.getSquadraOspite().getNome() : partita.getSquadraOspiteNomeStorico());
        
        boolean giocata = partita.getStato() != null && partita.getStato().equals(StatoPartita.TERMINATA);
        
        dto.put("golCasa", (giocata && partita.getGoalsHome() != null) ? partita.getGoalsHome().toString() : "ND");
        dto.put("golOspite", (giocata && partita.getGoalsAway() != null) ? partita.getGoalsAway().toString() : "ND");

        return ResponseEntity.ok(dto);
    }
}