package co.com.nuevaera.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ViewEmisionEvent extends GwtEvent<ViewEmisionEventHandler>{
	
	public static Type<ViewEmisionEventHandler> TYPE = new Type<ViewEmisionEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ViewEmisionEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ViewEmisionEventHandler handler) {
		handler.onViewEmisiones(this);
	}

}
