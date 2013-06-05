package co.com.nuevaera.client.dto;

import java.io.Serializable;


public class AnuncioEmisionDto implements Serializable{
	private long idEmision;
	private long idAnuncio;
	private long idAnuncioEmision;

	public long getIdAnuncioEmision() {
		return idAnuncioEmision;
	}
	public void setIdAnuncioEmision(long idAnuncioEmision) {
		this.idAnuncioEmision = idAnuncioEmision;
	}
	public long getIdEmision() {
		return idEmision;
	}
	public void setIdEmision(long idEmision) {
		this.idEmision = idEmision;
	}
	public long getIdAnuncio() {
		return idAnuncio;
	}
	public void setIdAnuncio(long idAnuncio) {
		this.idAnuncio = idAnuncio;
	}
}
