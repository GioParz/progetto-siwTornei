package it.uniroma3.tornei.model;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.*;

@Entity
public class Giocatore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "giocatore_seq")
	@SequenceGenerator(name = "giocatore_seq", sequenceName = "giocatore_seq", allocationSize = 1)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@NotBlank
	@Column(nullable = false)
	private String cognome;
	
	@Size(max = 2048, message = "L'URL dell'immagine è troppo lungo (max 2048 caratteri)")
	@Pattern(regexp = "(https?://.*)?", message = "Deve essere un URL valido (es. http://... o https://...) o rimanere vuoto")
	@Column(nullable = true)
	private String fotoUrl;
	
	@NotNull
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascita;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private RuoloGiocatore ruolo;
	
	@NotNull
	@Column(nullable = false)
	private Integer altezza;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Squadra squadra;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public RuoloGiocatore getRuolo() {
		return ruolo;
	}

	public void setRuolo(RuoloGiocatore ruolo) {
		this.ruolo = ruolo;
	}

	public Integer getAltezza() {
		return altezza;
	}

	public void setAltezza(Integer altezza) {
		this.altezza = altezza;
	}

	public Squadra getSquadra() {
		return squadra;
	}

	public void setSquadra(Squadra squadra) {
		this.squadra = squadra;
	}

	public String getFotoUrl() {
		return fotoUrl;
	}

	public void setFotoUrl(String fotoUrl) {
		this.fotoUrl = fotoUrl;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cognome, dataNascita, ruolo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Giocatore other = (Giocatore) obj;
		return Objects.equals(cognome, other.cognome) 
				&& Objects.equals(dataNascita, other.dataNascita)
				&& Objects.equals(ruolo, other.ruolo);
	}
}
