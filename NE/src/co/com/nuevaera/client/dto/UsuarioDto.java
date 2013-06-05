package co.com.nuevaera.client.dto;

public class UsuarioDto {
	
	private long id;
	private long idRestaurante;
	private String usuario;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
