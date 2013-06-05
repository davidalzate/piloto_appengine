package co.com.nuevaera.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;


@Entity
public class Emision {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key idEmision;
	
	private Long idRestaurante;
	private boolean anuncionInicial;
	private String propiedad;
	
	public Key getIdEmision() {
		return idEmision;
	}
	public Long getIdRestaurante() {
		return idRestaurante;
	}
	public void setIdRestaurante(Long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}
	public boolean isAnuncionInicial() {
		return anuncionInicial;
	}
	public void setAnuncionInicial(boolean anuncionInicial) {
		this.anuncionInicial = anuncionInicial;
	}
	public String getPropiedad() {
		return propiedad;
	}
	public void setPropiedad(String propiedad) {
		this.propiedad = propiedad;
	}
	
}
