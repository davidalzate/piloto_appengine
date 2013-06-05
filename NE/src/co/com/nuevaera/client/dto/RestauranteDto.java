package co.com.nuevaera.client.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

@SuppressWarnings("serial")
public class RestauranteDto implements Serializable, Comparable<RestauranteDto> {

	private long idRestaurante;
	private String nombre;
	private String fondo;
	private String banner;

	public static final ProvidesKey<RestauranteDto> KEY_PROVIDER = new ProvidesKey<RestauranteDto>() {
		@Override
		public Object getKey(RestauranteDto item) {
			return item == null ? null : item.getIdRestaurante();
		}
	};

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

	public String getFondo() {
		return fondo;
	}

	public void setFondo(String fondo) {
		this.fondo = fondo;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	@Override
	public int compareTo(RestauranteDto o) {
		return (o == null || o.nombre == null) ? -1 : -o.nombre
				.compareTo(nombre);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof RestauranteDto) {
			return idRestaurante == ((RestauranteDto) o).idRestaurante;
		}
		return false;
	}
}
