package co.com.nuevaera.client.presenter;

import java.util.List;

import co.com.nuevaera.client.NEServiceAsync;
import co.com.nuevaera.client.dto.AnuncioDto;
import co.com.nuevaera.client.dto.AnuncioEmisionDto;
import co.com.nuevaera.client.dto.EmisionDto;
import co.com.nuevaera.client.view.AnuncioEmisionView;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class AnuncioEmisionPresenter implements Presenter, AnuncioEmisionView.Presenter{
	
	
	private final NEServiceAsync neService;
	private final HandlerManager eventBus;
	private final AnuncioEmisionView anuncioEmisionView;
	private final AnuncioToEmisionDataProvider anuncioToEmisionDataProvider;
	private AnuncioEmisionDataProvider  anuncioEmisionDataProvider;
	
	private EmisionDto emisionDto;
	
	public AnuncioEmisionPresenter(NEServiceAsync neService, HandlerManager eventBus, AnuncioEmisionView anuncioEmisionView, EmisionDto emisionDto) {
		this.emisionDto = emisionDto;
		this.neService = neService;
		this.eventBus = eventBus;
		this.anuncioEmisionView = anuncioEmisionView;
		anuncioEmisionDataProvider = new AnuncioEmisionDataProvider(neService, emisionDto);
		anuncioToEmisionDataProvider = new AnuncioToEmisionDataProvider(neService);
		anuncioEmisionView.setDataProvider(anuncioEmisionDataProvider);
		anuncioEmisionView.setAnuncioToEmisionDataProvider(anuncioToEmisionDataProvider);
		this.anuncioEmisionView.setPresenter(this);
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
	public void save(AnuncioDto anuncioDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reload() {
		fechAnunciosEmisionList();
	}

	@Override
	public void delete(AnuncioDto anuncioDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(anuncioEmisionView.asWidget());
		fechAnunciosEmisionList();
	}
	
	private void fechAdAnunciosEmisionList(){
		
		neService.getAnunciosEmision(emisionDto, new AsyncCallback<List<AnuncioDto>>() {
			
			@Override
			public void onSuccess(List<AnuncioDto> anunciosDataValues) {
				anuncioEmisionView.setModelData(anunciosDataValues);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Ocurrio un error obteniendo los anuncios de la emisión.");
			}
		});
	}

	private void fechAnunciosEmisionList(){
		
		neService.getAnunciosEmision(emisionDto, new AsyncCallback<List<AnuncioDto>>() {
			
			@Override
			public void onSuccess(List<AnuncioDto> anunciosDataValues) {
				anuncioEmisionView.setModelData(anunciosDataValues);
				fechAnunciosList();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Ocurrio un error obteniendo los anuncios de la emisión.");
			}
		});
	}
	private void fechAnunciosList(){
		neService.getAllAnuncios(new AsyncCallback<List<AnuncioDto>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Ocurrio un error obteniendo los anuncios.");
				
			}

			@Override
			public void onSuccess(List<AnuncioDto> result) {
				anuncioEmisionView.setAModelData(result);
				
			}
			
		});
	}

	@Override
	public void addAnuncio(AnuncioDto anuncioDto) {
		AnuncioEmisionDto anuncioEmisionDto = new AnuncioEmisionDto();
		anuncioEmisionDto.setIdAnuncio(anuncioDto.getIdAnuncio());
		anuncioEmisionDto.setIdEmision(emisionDto.getIdEmision());
		neService.addAnuncioEmision(anuncioEmisionDto, new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean result) {
				if(result.booleanValue()){
					fechAdAnunciosEmisionList();
				}else{
					Window.alert("El anuncio ya se encuentra incluido en la emisión.");
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
}
