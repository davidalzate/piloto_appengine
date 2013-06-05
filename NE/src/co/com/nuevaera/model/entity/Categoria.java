package co.com.nuevaera.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key idCategoria;

	private Long idRestaurante;
	private String nombre;
	private String foto;
	
	public Key getIdCategoria() {
		return idCategoria;
	}
	
	public Long getIdRestaurante() {
		return idRestaurante;
	}
	public void setIdRestaurante(Long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
}
