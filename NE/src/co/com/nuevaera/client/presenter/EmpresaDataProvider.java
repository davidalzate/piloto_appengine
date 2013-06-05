package co.com.nuevaera.client.presenter;

import java.util.List;

import co.com.nuevaera.client.NEServiceAsync;
import co.com.nuevaera.client.dto.EmpresaDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;

public class EmpresaDataProvider extends AsyncDataProvider<EmpresaDto>{

	private NEServiceAsync neService;
	
	public EmpresaDataProvider(NEServiceAsync neService) {
		this.neService = neService;
	}

	@Override
	protected void onRangeChanged(HasData<EmpresaDto> display) {

		neService.getEmpresas(new AsyncCallback<List<EmpresaDto>>() {
			
			@Override
			public void onSuccess(List<EmpresaDto> result) {
				updateRowData(0, result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	
}
