package co.com.nuevaera.client.presenter;

import java.util.ArrayList;
import java.util.List;

import co.com.nuevaera.client.NEServiceAsync;
import co.com.nuevaera.client.dto.RestauranteDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

public class RestaurantesDataProvider extends AsyncDataProvider<RestauranteDto>{

	private NEServiceAsync neService;
	
	public RestaurantesDataProvider(NEServiceAsync neService) {
		this.neService = neService;
	}

	@Override
	protected void onRangeChanged(HasData<RestauranteDto> display) {
		
		neService.getRestaurantes(new AsyncCallback<List<RestauranteDto>>() {
			@Override
			public void onSuccess(List<RestauranteDto> result) {
				updateRowData(0, result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
		
	}
	
	
}
