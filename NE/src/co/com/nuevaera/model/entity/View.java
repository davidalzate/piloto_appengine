package co.com.nuevaera.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class View {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key  idView;
	
	private Integer idAnuncio;
	private Integer idRestaurante;
	private boolean click;
	private Date fecha;
	private int duracionVisita;
	
	public Key getIdView() {
		return idView;
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
