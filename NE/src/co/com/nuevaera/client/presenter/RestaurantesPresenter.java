package co.com.nuevaera.client.presenter;

import java.util.List;

import co.com.nuevaera.client.NEServiceAsync;
import co.com.nuevaera.client.dto.RestauranteDto;
import co.com.nuevaera.client.event.ViewCategoriaEvent;
import co.com.nuevaera.client.view.RestaurantesView;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class RestaurantesPresenter implements Presenter, RestaurantesView.Presenter{

	private final NEServiceAsync neService;
	private final HandlerManager eventBus;
	private final RestaurantesView restaurantesView;
	private final RestaurantesDataProvider restaurantesDataProvider;
	
	public RestaurantesPresenter(NEServiceAsync neService, HandlerManager eventBus, RestaurantesView restaurantesView) {
		this.neService = neService;
		this.eventBus = eventBus;
		this.restaurantesView = restaurantesView;
		this.restaurantesView.setPresenter(this);
		restaurantesDataProvider = new RestaurantesDataProvider(neService);
		restaurantesView.setDataProvider(restaurantesDataProvider);
	}
	
	@Override
	public void go(final HasWidgets container) {
		container.clear();
		container.add(restaurantesView.asWidget());
		fechRestaurantesList();
	}
	
	private void fechRestaurantesList(){
		neService.getRestaurantes(new AsyncCallback<List<RestauranteDto>>() {
			
			@Override
			public void onSuccess(List<RestauranteDto> restaurantesDataValues) {
				restaurantesView.setModelData(restaurantesDataValues);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(RestauranteDto restauranteDto) {
		
		neService.save(restauranteDto, new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				restaurantesView.onTaskSucefull("El restaurante se creo con exito");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				restaurantesView.onTaskFail("Hubo un error, el restaurante no pudo ser guardado.");
			}
		});
		
	}

	@Override
	public void reload() {
		fechRestaurantesList();
	}

	@Override
	public void delete(RestauranteDto restauranteDto) {
		neService.delete(restauranteDto, new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				restaurantesView.onTaskSucefull("El restaurante se borro con exito");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				restaurantesView.onTaskFail("Hubo un error, el restaurante no pudo ser borrado.");
			}
		});
	}

	@Override
	public void viewCateogoria(RestauranteDto restaurante) {
		eventBus.fireEvent(new ViewCategoriaEvent(restaurante));
	}

	@Override
	public void startNewBlobstoreSession() {
		neService.getBlobstoreUploadUrl(new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String actionURL) {
				restaurantesView.onStartBlobStoreSessionSucefull(actionURL);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				restaurantesView.onStartBlobStoreSessionFail();
			}
		});
	}

	@Override
	public void getImageUrl(String key, final int widgetId) {
		neService.getImageUrl(key, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				restaurantesView.onGetImageUrlSucefull(result, widgetId);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				restaurantesView.onGetImageUrlFail(caught.getMessage());
			}
		});
		
	};

}
