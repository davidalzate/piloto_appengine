package co.com.nuevaera.client.presenter;

import java.util.List;

import co.com.nuevaera.client.NEServiceAsync;
import co.com.nuevaera.client.dto.CategoriaDto;
import co.com.nuevaera.client.dto.ElementoDto;
import co.com.nuevaera.client.dto.EmisionDto;
import co.com.nuevaera.client.dto.RestauranteDto;
import co.com.nuevaera.client.event.ViewCategoriaEvent;
import co.com.nuevaera.client.view.CategoriaView;
import co.com.nuevaera.client.view.ElementoView;
import co.com.nuevaera.client.view.EmisionView;
import co.com.nuevaera.client.view.RestaurantesView;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class ElementoPresenter implements Presenter, ElementoView.Presenter{

	private final NEServiceAsync neService;
	private final HandlerManager eventBus;
	private final ElementoView elementoView;
	private final ElementoDataProvider elementoDataProvider;
	private CategoriaDto categoriaDto;
	
	public ElementoPresenter(NEServiceAsync neService, HandlerManager eventBus, ElementoView elementoView, CategoriaDto categoriaDto) {
		this.categoriaDto = categoriaDto;
		this.neService = neService;
		this.eventBus = eventBus;
		this.elementoView = elementoView;
		this.elementoView.setPresenter(this);
		elementoDataProvider = new ElementoDataProvider(neService, categoriaDto.getIdCategoria());
		elementoView.setDataProvider(elementoDataProvider);
		
	}
	
	@Override
	public void go(final HasWidgets container) {
		container.clear();
		container.add(elementoView.asWidget());
		fechCategoriasList(categoriaDto.getIdCategoria());
	}
	
	private void fechCategoriasList(long idRestaurante){
		
		neService.getElementos(idRestaurante, new AsyncCallback<List<ElementoDto>>() {
			
			@Override
			public void onSuccess(List<ElementoDto> restaurantesDataValues) {
				elementoView.setModelData(restaurantesDataValues);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
	}

	@Override
	public void onView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void goTo(Place place) {

	}

	@Override
	public void save(ElementoDto elementoDto) {
		
		neService.saveElemento(elementoDto, new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				elementoView.onTaskSucefull("El elemento se creo con exito");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				elementoView.onTaskFail("Hubo un error, el elemento no pudo ser guardada.");
			}
		});
		
	}

	@Override
	public void reload() {
		fechCategoriasList(categoriaDto.getIdCategoria());
	}

	@Override
	public void delete(ElementoDto elementoDto) {
		neService.deleteElemento(elementoDto, new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				elementoView.onTaskSucefull("El elemento se borro con exito");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				elementoView.onTaskFail("Hubo un error, el elemento no pudo ser borrada.");
			}
		});
	}

	@Override
	public void loadCategorias() {
		neService.getCategorias(categoriaDto.getIdCategoria(), new AsyncCallback<List<CategoriaDto>>() {
			
			@Override
			public void onSuccess(List<CategoriaDto> result) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	@Override
	public void startNewBlobstoreSession() {
		neService.getBlobstoreUploadUrl(new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String actionURL) {
				elementoView.onStartBlobStoreSessionSucefull(actionURL);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				elementoView.onStartBlobStoreSessionFail();
			}
		});
	}

	@Override
	public void getImageUrl(String key, final int widgetId) {
		neService.getImageUrl(key, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				elementoView.onGetImageUrlSucefull(result, widgetId);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				elementoView.onGetImageUrlFail(caught.getMessage());
			}
		});
		
	};

}
