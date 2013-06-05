package co.com.nuevaera.client.presenter;

import java.util.List;

import co.com.nuevaera.client.NEServiceAsync;
import co.com.nuevaera.client.dto.AnuncioDto;
import co.com.nuevaera.client.dto.EmisionDto;
import co.com.nuevaera.client.dto.RestauranteDto;
import co.com.nuevaera.client.event.ViewAnuncioEmisionEvent;
import co.com.nuevaera.client.view.EmisionView;
import co.com.nuevaera.client.view.RestaurantesView;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class EmisionPresenter implements Presenter, EmisionView.Presenter{

	private final NEServiceAsync neService;
	private final HandlerManager eventBus;
	private final EmisionView emisionView;
	private final EmisionDataProvider emisionDataProvider;
	
	public EmisionPresenter(NEServiceAsync neService, HandlerManager eventBus, EmisionView emisionView) {
		this.neService = neService;
		this.eventBus = eventBus;
		this.emisionView = emisionView;
		this.emisionView.setPresenter(this);
		emisionDataProvider = new EmisionDataProvider(neService);
		emisionView.setDataProvider(emisionDataProvider);
	}
	
	@Override
	public void go(final HasWidgets container) {
		container.clear();
		container.add(emisionView.asWidget());
		fechEmisionesList();
	}
	
	private void fechEmisionesList(){
		neService.getEmisiones(new AsyncCallback<List<EmisionDto>>() {
			
			@Override
			public void onSuccess(List<EmisionDto> restaurantesDataValues) {
				emisionView.setModelData(restaurantesDataValues);
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
	}

	@Override
	public void onView() {
		
	}

	@Override
	public void goTo(Place place) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(EmisionDto emisionDto) {
		
		neService.saveEmision(emisionDto, new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				emisionView.onTaskSucefull("El restaurante se creo con exito");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				emisionView.onTaskFail("Hubo un error, el restaurante no pudo ser guardado.");
			}
		});
		
	}

	@Override
	public void reload() {
		fechEmisionesList();
	}

	@Override
	public void delete(EmisionDto emisionDto) {
		neService.deleteEmision(emisionDto, new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				emisionView.onTaskSucefull("El restaurante se borro con exito");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				emisionView.onTaskFail("Hubo un error, el restaurante no pudo ser borrado.");
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
	public void viewAnuncios(EmisionDto emisionDto) {
		eventBus.fireEvent(new ViewAnuncioEmisionEvent(emisionDto));
	}

}
