package it.uniroma3.tornei.model;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Partita {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private LocalDateTime dataEOra;
	private String luogo;
	private Integer goalsHome;
	private Integer goalsAway;
	
	@Enumerated(EnumType.STRING)
	private StatoPartita stato;
	
	@ManyToOne
	private Torneo torneo;
	
	@ManyToOne
	private Squadra squadraCasa;
	
	@ManyToOne
	private Squadra squadraOspite;
	
	@ManyToOne
	private Arbitro arbitro;
	
	public Partita() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataEOra() {
		return dataEOra;
	}

	public void setDataEOra(LocalDateTime dataEOra) {
		this.dataEOra = dataEOra;
	}

	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public Integer getGoalsHome() {
		return goalsHome;
	}

	public void setGoalsHome(Integer goalsHome) {
		this.goalsHome = goalsHome;
	}

	public Integer getGoalsAway() {
		return goalsAway;
	}

	public void setGoalsAway(Integer goalsAway) {
		this.goalsAway = goalsAway;
	}

	public StatoPartita getStato() {
		return stato;
	}

	public void setStato(StatoPartita stato) {
		this.stato = stato;
	}

	public Torneo getTorneo() {
		return torneo;
	}

	public void setTorneo(Torneo torneo) {
		this.torneo = torneo;
	}

	public Squadra getSquadraCasa() {
		return squadraCasa;
	}

	public void setSquadraCasa(Squadra squadraCasa) {
		this.squadraCasa = squadraCasa;
	}

	public Squadra getSquadraOspite() {
		return squadraOspite;
	}

	public void setSquadraOspite(Squadra squadraOspite) {
		this.squadraOspite = squadraOspite;
	}

	public Arbitro getArbitro() {
		return arbitro;
	}

	public void setArbitro(Arbitro arbitro) {
		this.arbitro = arbitro;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataEOra, luogo, squadraCasa, squadraOspite);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Partita other = (Partita) obj;
		return Objects.equals(dataEOra, other.dataEOra) 
				&& Objects.equals(luogo, other.luogo)
				&& Objects.equals(squadraCasa, other.squadraCasa) 
				&& Objects.equals(squadraOspite, other.squadraOspite);
	}
	
	
}
