package it.uniroma3.tornei.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.tornei.model.Commento;
import it.uniroma3.tornei.model.Credentials;
import it.uniroma3.tornei.model.Partita;
import it.uniroma3.tornei.service.CommentoService;
import it.uniroma3.tornei.service.CredentialsService;
import it.uniroma3.tornei.service.PartitaService;

@RestController
@RequestMapping("/api/commenti")
public class CommentoRestController {

    private final CommentoService commentoService;
    private final PartitaService partitaService;
    private final CredentialsService credentialsService;

    public CommentoRestController(CommentoService commentoService, PartitaService partitaService, CredentialsService credentialsService) {
        this.commentoService = commentoService;
        this.partitaService = partitaService;
        this.credentialsService = credentialsService;
    }
    
    //metodo helper per evitare che jackson vada in loop(commento->partita->commenti->partitaxognuno...) con un classico oggetto jpa "commento"
    private Map<String, Object> mappaCommento(Commento c) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", c.getId());
        map.put("testo", c.getTesto());

        if (c.getUtente() != null) {
            Map<String, Object> utenteMap = new HashMap<>();
            utenteMap.put("id", c.getUtente().getId());
            utenteMap.put("nome", c.getUtente().getNome() != null ? c.getUtente().getNome() : "");
            utenteMap.put("cognome", c.getUtente().getCognome() != null ? c.getUtente().getCognome() : "");
            if (c.getUtente().getCredentials() != null) {
                utenteMap.put("username", c.getUtente().getCredentials().getUsername());
            }
            map.put("utente", utenteMap);
        }
        
        return map; //spring trasformerà la mappa in codice json
    }

    @GetMapping("/partita/{partitaId}")
    public ResponseEntity<?> getCommenti(@PathVariable Long partitaId) {
        Partita partita = this.partitaService.getPartita(partitaId);
        if (partita == null) return ResponseEntity.notFound().build();

        List<Map<String, Object>> risposta = new ArrayList<>();
        if (partita.getCommenti() != null) {
            for (Commento c : partita.getCommenti()) {
                risposta.add(mappaCommento(c));
            }
        }
        
        return ResponseEntity.ok(risposta);
    }

    @PostMapping("/partita/{partitaId}")
    public ResponseEntity<?> aggiungiCommento(@PathVariable Long partitaId, 
    		@RequestBody Map<String, String> payload, //corpo della richiesta spedito da react
            @AuthenticationPrincipal UserDetails userDetails) {
        
    	if (userDetails == null) {
            return ResponseEntity.status(401).body("Devi autenticarti.");
        }

        String testo = payload.get("testo");
        if (testo == null || testo.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Il testo del commento non può essere vuoto.");
        }

        Partita partita = this.partitaService.getPartita(partitaId);
        Credentials creds = this.credentialsService.getCredentialsByUsername(userDetails.getUsername());

        Commento commento = new Commento();
        commento.setTesto(testo);
        commento.setPartita(partita);
        commento.setUtente(creds.getUtente());

        Commento salvato = this.commentoService.saveCommento(commento);

        return ResponseEntity.ok(mappaCommento(salvato));
    }
}