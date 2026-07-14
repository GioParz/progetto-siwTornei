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
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;

@Entity
public class Giocatore {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@NotNull
	@Column(nullable = false)
	private String nome;
	
	@NotBlank
	@NotNull
	@Column(nullable = false)
	private String cognome;
	
	@NotBlank
	@NotNull
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascita;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private RuoloGiocatore ruolo;
	
	@NotBlank
	@NotNull
	@Column(nullable = false)
	private Integer altezza;
	
	@ManyToOne
	@Column(nullable = true)
	private Squadra squadra;
	
	public Giocatore() {
	}

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
