package it.uniroma3.tornei.model;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.*;

@Entity
public class Arbitro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arbitro_seq")
	@SequenceGenerator(name = "arbitro_seq", sequenceName = "arbitro_seq", allocationSize = 1)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@NotBlank
	@Column(nullable = false)
	private String cognome;
	
	@NotBlank
	@Size(min = 5, max = 20)
	@Column(unique = true, nullable = false)
	private String codiceAIA;
	
	@OneToMany(mappedBy = "arbitro")
	private List<Partita> partite;

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

	public String getCodiceAIA() {
		return codiceAIA;
	}

	public void setCodiceAIA(String codiceAIA) {
		this.codiceAIA = codiceAIA;
	}

	public List<Partita> getPartite() {
		return partite;
	}

	public void setPartite(List<Partita> partite) {
		this.partite = partite;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codiceAIA);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Arbitro other = (Arbitro) obj;
		return Objects.equals(codiceAIA, other.codiceAIA);
	}
}
