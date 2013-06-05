package co.com.nuevaera.client.presenter;

import java.util.List;

import co.com.nuevaera.client.NEServiceAsync;
import co.com.nuevaera.client.dto.ElementoDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;

public class ElementoDataProvider extends AsyncDataProvider<ElementoDto>{

	private NEServiceAsync neService;
	long idRestaurante;
	
	public ElementoDataProvider(NEServiceAsync neService, long idRestaurante) {
		this.neService = neService;
		this.idRestaurante = idRestaurante;
	}

	@Override
	protected void onRangeChanged(HasData<ElementoDto> display) {

		neService.getElementos(idRestaurante, new AsyncCallback<List<ElementoDto>>() {
			
			@Override
			public void onSuccess(List<ElementoDto> result) {
				updateRowData(0, result);
			}
			
			@Override
			public void onFailure(Throwable caught) {

			}
		});
	}
	
	
}
