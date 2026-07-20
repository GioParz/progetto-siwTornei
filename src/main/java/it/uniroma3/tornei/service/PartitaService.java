package it.uniroma3.tornei.service;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.tornei.exception.ArbitroOccupatoException;
import it.uniroma3.tornei.exception.IncompatibilitaDataPartitaException;
import it.uniroma3.tornei.model.Arbitro;
import it.uniroma3.tornei.model.Partita;
import it.uniroma3.tornei.model.Squadra;
import it.uniroma3.tornei.repository.PartitaRepository;

@Service
@Transactional(readOnly = true)
public class PartitaService {
	
	private final PartitaRepository partitaRepository;
	
	public PartitaService(PartitaRepository partitaRepository) {
		this.partitaRepository = partitaRepository;
	}

	public Partita getPartita(Long id) {
		return this.partitaRepository.findById(id).orElse(null);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Partita savePartita(Partita partita) {
        
		//controllo arbitro occupato
        if(partita.getArbitro() != null) {
        	boolean arbitroImpegnato = this.partitaRepository.existsByArbitroAndDataEOra(
        			partita.getArbitro(), partita.getDataEOra());
        	
        	//se l'arbitro è occupato assicuriamoci che non sia per la partita attuale
        	if(arbitroImpegnato) {
        		if(partita.getId() == null || !isStessaPartitaInQuellaData(partita.getId(), partita.getArbitro(), partita.getDataEOra()))
        			throw new ArbitroOccupatoException(partita.getArbitro().getNome(), partita.getArbitro().getCognome());
        	}
        }
        
        //controllo squadre impegnate
        LocalDateTime inizioGiorno = partita.getDataEOra().toLocalDate().atStartOfDay();
        LocalDateTime fineGiorno = partita.getDataEOra().toLocalDate().atTime(23, 59, 59);
        
        if (partita.getSquadraCasa() != null) {
            boolean casaImpegnata = this.partitaRepository.isSquadraImpegnata(partita.getSquadraCasa(), inizioGiorno, fineGiorno);
            if (casaImpegnata && (partita.getId() == null || !isSquadraImpegnataDallaPartitaStessa(partita.getId(), partita.getSquadraCasa(), partita.getDataEOra()))) {
                throw new IncompatibilitaDataPartitaException();
            }
        }
        
        if (partita.getSquadraOspite() != null) {
            boolean ospiteImpegnato = this.partitaRepository.isSquadraImpegnata(partita.getSquadraOspite(), inizioGiorno, fineGiorno);
            if (ospiteImpegnato && (partita.getId() == null || !isSquadraImpegnataDallaPartitaStessa(partita.getId(), partita.getSquadraOspite(), partita.getDataEOra()))) {
                throw new IncompatibilitaDataPartitaException();
            }
        }
        
        // controllo anno torneo
        if (partita.getTorneo() != null && 
            partita.getDataEOra().getYear() != partita.getTorneo().getAnno()) {
            throw new IncompatibilitaDataPartitaException();
        }

        return this.partitaRepository.save(partita);
    }
	
	// Metodi helper privati per gestire la fase di EDIT
	private boolean isStessaPartitaInQuellaData(Long partitaId, Arbitro arbitro, LocalDateTime dataEOra) {
	    Partita esistente = this.partitaRepository.findById(partitaId).orElse(null);
	    return esistente != null && Objects.equals(esistente.getArbitro(), arbitro) && esistente.getDataEOra().equals(dataEOra);
	}

	private boolean isSquadraImpegnataDallaPartitaStessa(Long partitaId, Squadra squadra, LocalDateTime dataEOra) {
	    Partita esistente = this.partitaRepository.findById(partitaId).orElse(null);
	    if (esistente == null) return false;

	    boolean isCasa = esistente.getSquadraCasa() != null && esistente.getSquadraCasa().equals(squadra);
	    boolean isOspite = esistente.getSquadraOspite() != null && esistente.getSquadraOspite().equals(squadra);

	    boolean stessoGiorno = esistente.getDataEOra().toLocalDate().equals(dataEOra.toLocalDate());

	    return stessoGiorno && (isCasa || isOspite);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void deletePartita(Long id) {
		this.partitaRepository.deleteById(id);
	}
}
