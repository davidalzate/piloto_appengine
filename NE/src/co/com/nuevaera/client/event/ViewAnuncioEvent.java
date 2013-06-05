package co.com.nuevaera.client.event;

import co.com.nuevaera.client.dto.EmpresaDto;

import com.google.gwt.event.shared.GwtEvent;

public class ViewAnuncioEvent extends GwtEvent<ViewAnuncioEventHandler>{
	
	public static Type<ViewAnuncioEventHandler> TYPE = new Type<ViewAnuncioEventHandler>();

	private EmpresaDto empresaDto;

	public ViewAnuncioEvent(EmpresaDto empresaDto) {
		this.empresaDto = empresaDto;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ViewAnuncioEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ViewAnuncioEventHandler handler) {
		handler.onViewAnuncio(this);
	}

	public EmpresaDto getEmpresaDto() {
		return empresaDto;
	}

	public void setEmpresaDto(EmpresaDto empresaDto) {
		this.empresaDto = empresaDto;
	}

}
