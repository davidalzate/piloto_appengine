package co.com.nuevaera.model.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key id;
	
	
	private long idRestaurante;
	private String usuario;

	public Key getId() {
		return id;
	}

	public long getIdRestaurante() {
		return idRestaurante;
	}

	public void setIdRestaurante(long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
	
	
}
