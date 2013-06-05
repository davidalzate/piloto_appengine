package co.com.nuevaera.client.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class CategoriaDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final ProvidesKey<CategoriaDto> KEY_PROVIDER = new ProvidesKey<CategoriaDto>() {
		@Override
		public Object getKey(CategoriaDto item) {
			return item == null ? null : item.getIdCategoria();
		}
	};	
	
	private long idCategoria;
	private long idRestaurante;
	private String nombre;
	private String foto;
	
	public long getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}
	public long getIdRestaurante() {
		return idRestaurante;
	}
	public void setIdRestaurante(long idRestaurante) {
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
