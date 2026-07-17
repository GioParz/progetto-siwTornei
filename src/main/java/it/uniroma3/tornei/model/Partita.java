package it.uniroma3.tornei.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import it.uniroma3.tornei.validation.NotStessaSquadra;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.*;

@Entity
@NotStessaSquadra
public class Partita {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partita_seq")
	@SequenceGenerator(name = "partita_seq", sequenceName = "partita_seq", allocationSize = 1)
	private Long id;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(nullable = false)
	private LocalDateTime dataEOra;
	
	@NotBlank
	@Column(nullable = false)
	private String luogo;
	
	@Column(nullable = true)
	private Integer goalsHome;
	
	@Column(nullable = true)
	private Integer goalsAway;
	
	@Enumerated(EnumType.STRING)
	private StatoPartita stato;
	
	@ManyToOne
	@NotNull
	@JoinColumn(nullable = false)
	private Torneo torneo;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Squadra squadraCasa;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Squadra squadraOspite;
	
	//in caso di squadra eliminata
	@Column(nullable = true)
	private String squadraCasaNomeStorico;
	@Column(nullable = true)
	private String squadraOspiteNomeStorico;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Arbitro arbitro;
	
	@Column(nullable = true)
	private String arbitroNomeCognomeStorico;
	
	@OneToMany(mappedBy = "partita", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Commento> commenti = new ArrayList<>();

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

	public String getArbitroNomeCognomeStorico() {
		return arbitroNomeCognomeStorico;
	}

	public void setArbitroNomeCognomeStorico(String arbitroNomeCognomeStorico) {
		this.arbitroNomeCognomeStorico = arbitroNomeCognomeStorico;
	}

	@Override
	public int hashCode() {

	    String casaIdentificativo = (squadraCasa != null) ? squadraCasa.getNome() : squadraCasaNomeStorico;
	    String ospiteIdentificativo = (squadraOspite != null) ? squadraOspite.getNome() : squadraOspiteNomeStorico;
	    
	    return Objects.hash(dataEOra, luogo, casaIdentificativo, ospiteIdentificativo);
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
	        return true;
	    if (obj == null || getClass() != obj.getClass())
	        return false;
	    Partita other = (Partita) obj;
	    
	    String casaThis = (this.squadraCasa != null) ? this.squadraCasa.getNome() : this.squadraCasaNomeStorico;
	    String ospiteThis = (this.squadraOspite != null) ? this.squadraOspite.getNome() : this.squadraOspiteNomeStorico;
	    
	    String casaOther = (other.squadraCasa != null) ? other.squadraCasa.getNome() : other.squadraCasaNomeStorico;
	    String ospiteOther = (other.squadraOspite != null) ? other.squadraOspite.getNome() : other.squadraOspiteNomeStorico;
	    
	    return Objects.equals(dataEOra, other.dataEOra) 
	            && Objects.equals(luogo, other.luogo)
	            && Objects.equals(casaThis, casaOther) 
	            && Objects.equals(ospiteThis, ospiteOther);
	}
}
