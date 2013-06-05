package co.com.nuevaera.client.presenter;

import java.util.List;

import co.com.nuevaera.client.NEServiceAsync;
import co.com.nuevaera.client.dto.EmisionDto;
import co.com.nuevaera.client.dto.RestauranteDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

public class EmisionDataProvider extends AsyncDataProvider<EmisionDto>{

	private NEServiceAsync neService;
	
	public EmisionDataProvider(NEServiceAsync neService) {
		this.neService = neService;
	}

	@Override
	protected void onRangeChanged(HasData<EmisionDto> display) {

		neService.getEmisiones(new AsyncCallback<List<EmisionDto>>() {
			
			@Override
			public void onSuccess(List<EmisionDto> result) {
				updateRowData(0, result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	
}
