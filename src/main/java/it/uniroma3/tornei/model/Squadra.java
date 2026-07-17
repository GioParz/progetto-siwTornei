package it.uniroma3.tornei.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.uniroma3.tornei.validation.NotAnnoFuturo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.*;

@Entity
public class Squadra {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "squadra_seq")
	@SequenceGenerator(name = "squadra_seq", sequenceName = "squadra_seq", allocationSize = 1)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@Size(max = 2048, message = "L'URL dell'immagine è troppo lungo (max 2048 caratteri)")
	@Pattern(regexp = "(https?://.*)?", message = "Deve essere un URL valido (es. http://... o https://...) o rimanere vuoto")
	@Column(nullable = true)
	private String stemmaUrl;
	
	@NotNull
	@Min(1863)
	@NotAnnoFuturo
	private Integer annoFondazione;
	
	@NotBlank
	@Column(nullable = false)
	private String citta;
	
	@ManyToMany(mappedBy = "squadre")
	private List<Torneo> tornei;
	
	@OneToMany(mappedBy = "squadra", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Giocatore> giocatori;
	
	//metodi helper per corretta sincronizzazione
	public void addGiocatore(Giocatore giocatore) {
	    if (this.giocatori == null) {
	        this.giocatori = new ArrayList<>();
	    }
	    this.giocatori.add(giocatore);
	    giocatore.setSquadra(this); // Sincronizza il lato "ManyToOne"
	}

	public void removeGiocatore(Giocatore giocatore) {
	    if (this.giocatori != null) {
	        this.giocatori.remove(giocatore);
	        giocatore.setSquadra(null); // Rimuove temporaneamente il legame
	    }
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
	
	public Integer getAnnoFondazione() {
		return annoFondazione;
	}
	
	public void setAnnoFondazione(Integer annoFondazione) {
		this.annoFondazione = annoFondazione;
	}
	
	public String getCitta() {
		return citta;
	}
	
	public void setCitta(String citta) {
		this.citta = citta;
	}

	public List<Torneo> getTornei() {
		return tornei;
	}

	public void setTornei(List<Torneo> tornei) {
		this.tornei = tornei;
	}

	public List<Giocatore> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(List<Giocatore> giocatori) {
		this.giocatori = giocatori;
	}

	public String getStemmaUrl() {
		return stemmaUrl;
	}

	public void setStemmaUrl(String stemmaUrl) {
		this.stemmaUrl = stemmaUrl;
	}

	@Override
	public int hashCode() {
		return Objects.hash(citta, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Squadra other = (Squadra) obj;
		return Objects.equals(citta, other.citta) && Objects.equals(nome, other.nome);
	}
}
