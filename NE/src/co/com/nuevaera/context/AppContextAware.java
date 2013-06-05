package co.com.nuevaera.context;

public class AppContextAware {
	private Long idRestaurante;
	private String propiedad;
	public Long getIdRestaurante() {
		return idRestaurante;
	}
	public void setIdRestaurante(Long idRestaurante) {
		this.idRestaurante = idRestaurante;
	}
	public String getPropiedad() {
		return propiedad;
	}
	public void setPropiedad(String propiedad) {
		this.propiedad = propiedad;
	}
	
}
