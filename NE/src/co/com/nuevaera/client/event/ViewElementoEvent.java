package co.com.nuevaera.client.event;

import co.com.nuevaera.client.dto.CategoriaDto;

import com.google.gwt.event.shared.GwtEvent;

public class ViewElementoEvent extends GwtEvent<ViewElementoEventHandler>{
	
	public static Type<ViewElementoEventHandler> TYPE = new Type<ViewElementoEventHandler>();

	private CategoriaDto categoriaDto;


	public ViewElementoEvent(CategoriaDto categoriaDto) {
		this.categoriaDto = categoriaDto;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ViewElementoEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ViewElementoEventHandler handler) {
		handler.onViewElemento(this);
	}

	public CategoriaDto getCategoriaDto() {
		return categoriaDto;
	}

	public void setCategoriaDto(CategoriaDto categoriaDto) {
		this.categoriaDto = categoriaDto;
	}

}
