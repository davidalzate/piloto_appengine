package co.com.nuevaera.client.presenter;

import java.util.List;

import co.com.nuevaera.client.NEServiceAsync;
import co.com.nuevaera.client.dto.CategoriaDto;
import co.com.nuevaera.client.dto.ElementoDto;
import co.com.nuevaera.client.dto.EmisionDto;
import co.com.nuevaera.client.dto.RestauranteDto;
import co.com.nuevaera.client.event.ViewCategoriaEvent;
import co.com.nuevaera.client.event.ViewElementoEvent;
import co.com.nuevaera.client.view.CategoriaView;
import co.com.nuevaera.client.view.EmisionView;
import co.com.nuevaera.client.view.RestaurantesView;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class CategoriaPresenter implements Presenter, CategoriaView.Presenter{

	private final NEServiceAsync neService;
	private final HandlerManager eventBus;
	private final CategoriaView categoriaView;
	private final CategoriaDataProvider categoriaDataProvider;
	private RestauranteDto restaurante;
	
	public CategoriaPresenter(NEServiceAsync neService, HandlerManager eventBus, CategoriaView emisionView, RestauranteDto restaurante) {
		this.neService = neService;
		this.eventBus = eventBus;
		this.categoriaView = emisionView;
		this.categoriaView.setPresenter(this);
		categoriaDataProvider = new CategoriaDataProvider(neService, restaurante.getIdRestaurante());
		emisionView.setDataProvider(categoriaDataProvider);
		this.restaurante = restaurante;
	}
	
	@Override
	public void go(final HasWidgets container) {
		container.clear();
		container.add(categoriaView.asWidget());
		fechCategoriasList(restaurante.getIdRestaurante());
	}
	
	private void fechCategoriasList(long idRestaurante){
		
		neService.getCategorias(idRestaurante, new AsyncCallback<List<CategoriaDto>>() {
			
			@Override
			public void onSuccess(List<CategoriaDto> restaurantesDataValues) {
				categoriaView.setModelData(restaurantesDataValues);
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
	public void save(CategoriaDto categoriaDto) {
		
		neService.saveCategoria(categoriaDto, new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				categoriaView.onTaskSucefull("La categoria se creo con exito");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				categoriaView.onTaskFail("Hubo un error, La categoria no pudo ser guardada.");
			}
		});
		
	}

	@Override
	public void reload() {
		fechCategoriasList(restaurante.getIdRestaurante());
	}

	@Override
	public void delete(CategoriaDto categoriaDto) {
		neService.deleteCategoria(categoriaDto, new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				categoriaView.onTaskSucefull("La categoria  se borro con exito");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				categoriaView.onTaskFail("Hubo un error, la categoria  no pudo ser borrada.");
			}
		});
	}

	@Override
	public void loadRestaurantes() {
		neService.getRestaurantes(new AsyncCallback<List<RestauranteDto>>() {
			
			@Override
			public void onSuccess(List<RestauranteDto> result) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	@Override
	public void viewElementos(CategoriaDto categoriaDto) {
		eventBus.fireEvent(new ViewElementoEvent(categoriaDto));
	}

	@Override
	public void startNewBlobstoreSession() {
		neService.getBlobstoreUploadUrl(new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String actionURL) {
				categoriaView.onStartBlobStoreSessionSucefull(actionURL);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				categoriaView.onStartBlobStoreSessionFail();
			}
		});
	}

	@Override
	public void getImageUrl(String key, final int widgetId) {
		neService.getImageUrl(key, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				categoriaView.onGetImageUrlSucefull(result, widgetId);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				categoriaView.onGetImageUrlFail(caught.getMessage());
			}
		});
		
	};
}
