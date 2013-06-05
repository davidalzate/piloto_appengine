package co.com.nuevaera.client.presenter;

import java.util.List;

import co.com.nuevaera.client.NEServiceAsync;
import co.com.nuevaera.client.dto.AnuncioDto;
import co.com.nuevaera.client.dto.EmisionDto;
import co.com.nuevaera.client.dto.ElementoDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;

public class AnuncioEmisionDataProvider extends AsyncDataProvider<AnuncioDto>{

	private NEServiceAsync neService;
	private EmisionDto emision;
	
	public AnuncioEmisionDataProvider(NEServiceAsync neService, EmisionDto emision) {
		this.neService = neService;
		this.emision = emision;
	}

	@Override
	protected void onRangeChanged(HasData<AnuncioDto> display) {
		neService.getAnunciosEmision(emision, new AsyncCallback<List<AnuncioDto>>() {
			
			@Override
			public void onSuccess(List<AnuncioDto> result) {
				updateRowData(0, result);
			}
			
			@Override
			public void onFailure(Throwable caught) {

			}
		});
	}
	
	
}
