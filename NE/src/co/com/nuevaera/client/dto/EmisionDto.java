package co.com.nuevaera.client.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;



public class EmisionDto implements Serializable, Comparable<EmisionDto>{
	
	private long idEmision;
	private long idRestaurante;
	private boolean anuncionInicial;
	private String propiedad;

	public static final ProvidesKey<EmisionDto> KEY_PROVIDER = new ProvidesKey<EmisionDto>() {
		@Override
		public Object getKey(EmisionDto item) {
			return item == null ? null : item.getIdEmision();
		}
	};	
	
	public void setIdEmision(long idEmision){	
		this.idEmision=idEmision;
	}
	
	public long getIdEmision() {
		return this.idEmision;
	}
	public long getIdRestaurante() {
		return idRestaurante;
	}
	public void setIdRestaurante(long idRestaurante) {
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
	
	@Override
	public int compareTo(EmisionDto o) {
		return (o == null || o.propiedad == null) ? -1 : -o.propiedad
				.compareTo(propiedad);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof EmisionDto) {
			return idEmision == ((EmisionDto) o).idEmision;
		}
		return false;
	}
}
