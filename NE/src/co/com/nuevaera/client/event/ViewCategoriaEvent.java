package co.com.nuevaera.client.event;

import co.com.nuevaera.client.dto.RestauranteDto;

import com.google.gwt.event.shared.GwtEvent;

public class ViewCategoriaEvent extends GwtEvent<ViewCategoriaEventHandler>{
	
	public static Type<ViewCategoriaEventHandler> TYPE = new Type<ViewCategoriaEventHandler>();
	private RestauranteDto restaurante;



	public ViewCategoriaEvent(RestauranteDto restaurante) {
		this.restaurante = restaurante;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ViewCategoriaEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ViewCategoriaEventHandler handler) {
		handler.onViewCategoria(this);
	}

	public RestauranteDto getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(RestauranteDto restaurante) {
		this.restaurante = restaurante;
	}

}
