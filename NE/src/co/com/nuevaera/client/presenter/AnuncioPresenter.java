package co.com.nuevaera.client.presenter;

import java.util.List;

import co.com.nuevaera.client.NEServiceAsync;
import co.com.nuevaera.client.dto.AnuncioDto;
import co.com.nuevaera.client.dto.CategoriaDto;
import co.com.nuevaera.client.dto.ElementoDto;
import co.com.nuevaera.client.dto.EmisionDto;
import co.com.nuevaera.client.dto.EmpresaDto;
import co.com.nuevaera.client.dto.RestauranteDto;
import co.com.nuevaera.client.event.ViewCategoriaEvent;
import co.com.nuevaera.client.view.AnuncioView;
import co.com.nuevaera.client.view.CategoriaView;
import co.com.nuevaera.client.view.ElementoView;
import co.com.nuevaera.client.view.EmisionView;
import co.com.nuevaera.client.view.RestaurantesView;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class AnuncioPresenter implements Presenter, AnuncioView.Presenter{

	private final NEServiceAsync neService;
	private final HandlerManager eventBus;
	private final AnuncioView anuncioView;
	private final AnuncioDataProvider anuncioDataProvider;
	private EmpresaDto empresa;
	
	public AnuncioPresenter(NEServiceAsync neService, HandlerManager eventBus, AnuncioView elementoView, EmpresaDto categoriaDto) {
		this.empresa = categoriaDto;
		this.neService = neService;
		this.eventBus = eventBus;
		this.anuncioView = elementoView;
		this.anuncioView.setPresenter(this);
		anuncioDataProvider = new AnuncioDataProvider(neService, categoriaDto.getIdEmpresa());
		elementoView.setDataProvider(anuncioDataProvider);
		
	}
	
	@Override
	public void go(final HasWidgets container) {
		container.clear();
		container.add(anuncioView.asWidget());
		fechAnunciosList(empresa.getIdEmpresa());
	}
	
	private void fechAnunciosList(long idEmpresa){
		
		neService.getAnuncios(idEmpresa, new AsyncCallback<List<AnuncioDto>>() {
			
			@Override
			public void onSuccess(List<AnuncioDto> anunciosDataValues) {
				anuncioView.setModelData(anunciosDataValues);
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
	public void save(AnuncioDto anuncioDto) {
		
		neService.saveAnuncio(anuncioDto, new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				anuncioView.onTaskSucefull("El anuncio se creo con exito");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				anuncioView.onTaskFail("Hubo un error, el anuncio no pudo ser guardar.");
			}
		});
		
	}

	@Override
	public void reload() {
		fechAnunciosList(empresa.getIdEmpresa());
	}

	@Override
	public void delete(AnuncioDto enuncioDto) {
		neService.deleteAnuncio(enuncioDto, new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				anuncioView.onTaskSucefull("El elemento se borro con exito");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				anuncioView.onTaskFail("Hubo un error, el elemento no pudo ser borrada.");
			}
		});
	}

	@Override
	public void loadEmpresas() {
		neService.getCategorias(empresa.getIdEmpresa(), new AsyncCallback<List<CategoriaDto>>() {
			
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

	public void startNewBlobstoreSession() {
		neService.getBlobstoreUploadUrl(new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String actionURL) {
				anuncioView.onStartBlobStoreSessionSucefull(actionURL);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				anuncioView.onStartBlobStoreSessionFail();
			}
		});
	}

	@Override
	public void getImageUrl(String key, final int widgetId) {
		neService.getImageUrl(key, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				anuncioView.onGetImageUrlSucefull(result, widgetId);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				anuncioView.onGetImageUrlFail(caught.getMessage());
			}
		});
		
	};

}
