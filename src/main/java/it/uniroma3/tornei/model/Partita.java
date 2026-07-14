package it.uniroma3.tornei.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;

@Entity
public class Partita {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@NotNull
	@Future
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(nullable = false)
	private LocalDateTime dataEOra;
	
	@NotBlank
	@NotNull
	@Column(nullable = false)
	private String luogo;
	
	@NotBlank
	@NotNull
	@Column(nullable = false)
	@Min(0)
	private Integer goalsHome;
	
	@NotBlank
	@NotNull
	@Column(nullable = false)
	@Min(0)
	private Integer goalsAway;
	
	@Enumerated(EnumType.STRING)
	private StatoPartita stato;
	
	@ManyToOne
	private Torneo torneo;
	
	@NotNull
	@ManyToOne
	@Column(nullable = true)
	private Squadra squadraCasa;
	
	@NotNull
	@ManyToOne
	@Column(nullable = true)
	private Squadra squadraOspite;
	
	//in caso di squadra eliminata
	private String squadraCasaNomeStorico;
	private String squadraOspiteNomeStorico;
	
	@NotNull
	@ManyToOne
	private Arbitro arbitro;
	
	@OneToMany(mappedBy = "partita", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Commento> commenti = new ArrayList<>();
	
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

	public String getSquadraCasaNomeStorico() {
		return squadraCasaNomeStorico;
	}

	public void setSquadraCasaNomeStorico(String squadraCasaNomeStorico) {
		this.squadraCasaNomeStorico = squadraCasaNomeStorico;
	}

	public String getSquadraOspiteNomeStorico() {
		return squadraOspiteNomeStorico;
	}

	public void setSquadraOspiteNomeStorico(String squadraOspiteNomeStorico) {
		this.squadraOspiteNomeStorico = squadraOspiteNomeStorico;
	}

	public List<Commento> getCommenti() {
		return commenti;
	}

	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
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
