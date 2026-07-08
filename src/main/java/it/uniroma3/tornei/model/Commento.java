package it.uniroma3.tornei.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Commento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commento_seq")
	@SequenceGenerator(name = "commento_seq", sequenceName = "commento_seq", allocationSize = 1)
	private Long id;
	
	@Column(nullable = false, length = 1000)
	private String testo;
	
	private LocalDateTime dataCreazione;
	
	@ManyToOne
	private Partita partita;
	
	@ManyToOne
	private Utente utente;
	
	@PrePersist
	protected void onCreate() {
		this.dataCreazione = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDateTime dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Partita getPartita() {
		return partita;
	}

	public void setPartita(Partita partita) {
		this.partita = partita;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
	
}
