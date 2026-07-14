package it.uniroma3.tornei.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;

@Entity
public class Torneo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@NotNull
	@Column(nullable = false)
	private String nome;
	
	@NotBlank
	@NotNull
	private Integer anno;
	
	@NotBlank
	@NotNull
	@Column(nullable = false)
	private String descrizione;
	
	@ManyToMany
	private List<Squadra> squadre;
	
	@OneToMany(mappedBy = "torneo", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Partita> partite;
	
	public Torneo() {
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
	
	public Integer getAnno() {
		return anno;
	}
	
	public void setAnno(Integer anno) {
		this.anno = anno;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Squadra> getSquadre() {
		return squadre;
	}

	public void setSquadre(List<Squadra> squadre) {
		this.squadre = squadre;
	}

	public List<Partita> getPartite() {
		return partite;
	}

	public void setPartite(List<Partita> partite) {
		this.partite = partite;
	}

	@Override
	public int hashCode() {
		return Objects.hash(anno, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Torneo other = (Torneo) obj;
		return Objects.equals(anno, other.anno) && Objects.equals(nome, other.nome);
	}
	
}
