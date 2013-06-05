package co.com.nuevaera.client.presenter;

import java.util.List;

import co.com.nuevaera.client.NEServiceAsync;
import co.com.nuevaera.client.dto.EmpresaDto;
import co.com.nuevaera.client.event.ViewAnuncioEvent;
import co.com.nuevaera.client.view.EmpresaView;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class EmpresaPresenter implements Presenter, EmpresaView.Presenter{

	private final NEServiceAsync neService;
	private final HandlerManager eventBus;
	private final EmpresaView empresaView;
	private final EmpresaDataProvider empresaDataProvider;
	
	public EmpresaPresenter(NEServiceAsync neService, HandlerManager eventBus, EmpresaView empresaView) {
		this.neService = neService;
		this.eventBus = eventBus;
		this.empresaView = empresaView;
		this.empresaView.setPresenter(this);
		empresaDataProvider = new EmpresaDataProvider(neService);
		empresaView.setDataProvider(empresaDataProvider);
	}
	
	@Override
	public void go(final HasWidgets container) {
		container.clear();
		container.add(empresaView.asWidget());
		fechRestaurantesList();
	}
	
	private void fechRestaurantesList(){
		neService.getEmpresas(new AsyncCallback<List<EmpresaDto>>() {
			
			@Override
			public void onSuccess(List<EmpresaDto> empresaDtoDataValues) {
				empresaView.setModelData(empresaDtoDataValues);
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
	public void save(EmpresaDto empresaDto) {
		
		neService.saveEmpresa(empresaDto, new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				empresaView.onTaskSucefull("La empresa se creo con exito");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				empresaView.onTaskFail("Hubo un error, la empresa  no pudo ser guardado.");
			}
		});
		
	}

	@Override
	public void reload() {
		fechRestaurantesList();
	}

	@Override
	public void delete(EmpresaDto empresaDto) {
		neService.deleteEmpresa(empresaDto, new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				empresaView.onTaskSucefull("La empresa  se borro con exito");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				empresaView.onTaskFail("Hubo un error, la empresa  no pudo ser borrado.");
			}
		});
	}

	@Override
	public void viewAnuncios(EmpresaDto empresaDto) {
		eventBus.fireEvent(new ViewAnuncioEvent(empresaDto));
	}

}
