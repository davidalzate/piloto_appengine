package co.com.nuevaera.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ViewEmpresasEvent extends GwtEvent<ViewEmpresasEventHandler>{
	
	public static Type<ViewEmpresasEventHandler> TYPE = new Type<ViewEmpresasEventHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ViewEmpresasEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ViewEmpresasEventHandler handler) {
		handler.onViewEmpresas(this);
	}

}
