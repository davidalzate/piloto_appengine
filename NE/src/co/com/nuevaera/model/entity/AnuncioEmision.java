package co.com.nuevaera.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class AnuncioEmision {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key idAnuncioEmision;
	
	private Long idEmision;
	private Long idAnuncio;
	
	public Key getIdAnuncioEmision() {
		return idAnuncioEmision;
	}
	public Long getIdEmision() {
		return idEmision;
	}
	public void setIdEmision(Long idEmision) {
		this.idEmision = idEmision;
	}
	public Long getIdAnuncio() {
		return idAnuncio;
	}
	public void setIdAnuncio(Long idAnuncio) {
		this.idAnuncio = idAnuncio;
	}
	
}
