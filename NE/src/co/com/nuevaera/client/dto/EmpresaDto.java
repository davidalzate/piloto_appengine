package co.com.nuevaera.client.dto;

import java.io.Serializable;

import com.google.gwt.view.client.ProvidesKey;

public class EmpresaDto  implements Serializable, Comparable<EmpresaDto>{

	private long idEmpresa;
	private String nombre;
	
	public static final ProvidesKey<EmpresaDto> KEY_PROVIDER = new ProvidesKey<EmpresaDto>() {
		@Override
		public Object getKey(EmpresaDto item) {
			return item == null ? null : item.getIdEmpresa();
		}
	};	
	
	public long getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
	
	@Override
	public int compareTo(EmpresaDto o) {
		return (o == null || o.nombre == null) ? -1 : -o.nombre
				.compareTo(nombre);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof RestauranteDto) {
			return idEmpresa == ((EmpresaDto) o).idEmpresa;
		}
		return false;
	}
}
