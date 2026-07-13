package it.uniroma3.tornei.model;

import java.util.Objects;

public class RigaClassifica {
	
	private String nomeSquadra;
	private Integer partiteGiocate;
	private Integer punti;
	private Integer golFatti;
	private Integer golSubiti;
	private Boolean ritirata;
	
	public RigaClassifica(String nomeSquadra) {
        this.nomeSquadra = nomeSquadra;
        this.partiteGiocate = 0;
        this.punti = 0;
        this.golFatti = 0;
        this.golSubiti = 0;
        this.ritirata = false;
    }

    public void aggiungiPartita(int golFattiPartita, int golSubitiPartita, int puntiGuadagnati) {
        this.partiteGiocate++;
        this.punti += puntiGuadagnati;
        this.golFatti += golFattiPartita;
        this.golSubiti += golSubitiPartita;
    }

	public String getNomeSquadra() {
		return nomeSquadra;
	}

	public void setNomeSquadra(String nomeSquadra) {
		this.nomeSquadra = nomeSquadra;
	}

	public Integer getPartiteGiocate() {
		return partiteGiocate;
	}

	public void setPartiteGiocate(Integer partiteGiocate) {
		this.partiteGiocate = partiteGiocate;
	}

	public Integer getPunti() {
		return punti;
	}

	public void setPunti(Integer punti) {
		this.punti = punti;
	}

	public Integer getGolFatti() {
		return golFatti;
	}

	public void setGolFatti(Integer golFatti) {
		this.golFatti = golFatti;
	}

	public Integer getGolSubiti() {
		return golSubiti;
	}

	public void setGolSubiti(Integer golSubiti) {
		this.golSubiti = golSubiti;
	}

	public Boolean isRitirata() {
		return ritirata;
	}

	public void setRitirata(Boolean ritirata) {
		this.ritirata = ritirata;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nomeSquadra);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		RigaClassifica other = (RigaClassifica) obj;
		return Objects.equals(nomeSquadra, other.nomeSquadra);
	}
 
}
