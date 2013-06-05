package co.com.nuevaera.client.event;

import co.com.nuevaera.client.dto.EmisionDto;
import co.com.nuevaera.client.dto.EmpresaDto;

import com.google.gwt.event.shared.GwtEvent;

public class ViewAnuncioEmisionEvent extends GwtEvent<ViewAnuncioEmisionEventHandler>{
	
	public static Type<ViewAnuncioEmisionEventHandler> TYPE = new Type<ViewAnuncioEmisionEventHandler>();
	private EmisionDto emisionDto;

	public ViewAnuncioEmisionEvent(EmisionDto emisionDto) {
		this.emisionDto = emisionDto;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ViewAnuncioEmisionEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ViewAnuncioEmisionEventHandler handler) {
		handler.onViewAnuncioEmision(this);
	}

	public EmisionDto getEmisionDto() {
		return emisionDto;
	}

	public void setEmisionDto(EmisionDto emisionDto) {
		this.emisionDto = emisionDto;
	}

}
