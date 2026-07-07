package it.uniroma3.tornei.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Squadra {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	private Integer annoFondazione;
	private String citta;
	
	@ManyToMany(mappedBy = "squadre")
	private List<Torneo> tornei;
	
	@OneToMany(mappedBy = "squadra", cascade = CascadeType.REMOVE)
	private List<Giocatore> giocatori;
	
	public Squadra() {
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
