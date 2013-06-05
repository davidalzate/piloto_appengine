package co.com.nuevaera.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ViewRestaurantesEvent extends GwtEvent<ViewRestaurantesEventHandler>{
	
	public static Type<ViewRestaurantesEventHandler> TYPE = new Type<ViewRestaurantesEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ViewRestaurantesEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ViewRestaurantesEventHandler handler) {
		handler.onViewRestaurantes(this);
	}

}
