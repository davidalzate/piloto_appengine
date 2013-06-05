package co.com.nuevaera.client.dto;

import java.util.Date;

public class ViewDto {
	
	private Integer idView;
	private Integer idAnuncio;
	private Integer idRestaurante;
	private boolean click;
	private Date fecha;
	private int duracionVisita;
	
	public Integer getIdView() {
		return idView;
	}
	public void setIdView(Integer idView) {
		this.idView = idView;
	}
	public Integer getIdAnuncio() {
		return idAnuncio;
	}
	public void setIdAnuncio(Integer idAnuncio) {
		this.idAnuncio = idAnuncio;
	}
	public Integer getIdRestaurante() {
		return idRestaurante;
	}
	public void setIdRestaurante(Integer idRestaurante) {
		this.idRestaurante = idRestaurante;
	}
	public boolean isClick() {
		return click;
	}
	public void setClick(boolean click) {
		this.click = click;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getDuracionVisita() {
		return duracionVisita;
	}
	public void setDuracionVisita(int duracionVisita) {
		this.duracionVisita = duracionVisita;
	}

}
