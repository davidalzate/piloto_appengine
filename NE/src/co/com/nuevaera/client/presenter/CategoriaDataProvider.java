package co.com.nuevaera.client.presenter;

import java.util.ArrayList;
import java.util.List;

import co.com.nuevaera.client.NEServiceAsync;
import co.com.nuevaera.client.dto.CategoriaDto;
import co.com.nuevaera.client.dto.EmisionDto;
import co.com.nuevaera.client.dto.RestauranteDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

public class CategoriaDataProvider extends AsyncDataProvider<CategoriaDto>{

	private NEServiceAsync neService;
	long idRestaurante;
	
	public CategoriaDataProvider(NEServiceAsync neService, long idRestaurante) {
		this.neService = neService;
		this.idRestaurante = idRestaurante;
	}

	@Override
	protected void onRangeChanged(HasData<CategoriaDto> display) {
		updateRowData(0, new ArrayList<CategoriaDto>());
		neService.getCategorias(idRestaurante, new AsyncCallback<List<CategoriaDto>>() {
			
			@Override
			public void onSuccess(List<CategoriaDto> result) {
				updateRowData(0, result);
			}
			
			@Override
			public void onFailure(Throwable caught) {

			}
		});
	}
	
	
}
