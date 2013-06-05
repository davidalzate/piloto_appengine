package co.com.nuevaera.client.view;


import java.util.List;

import co.com.nuevaera.client.dto.EmpresaDto;
import co.com.nuevaera.client.presenter.EmpresaDataProvider;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface EmpresaView extends IsWidget{
	
	public void setPresenter(Presenter listener);
	public void setModelData(List<EmpresaDto> empresasDataValues);
	public void setDataProvider(EmpresaDataProvider empresaDataProvider);
	public void onTaskSucefull(String message);
	public void onTaskFail(String message);
	
	public interface Presenter {
		public void onView();
		public void goTo(Place place);
		public void save(EmpresaDto empresaDto);
		public void reload();
		public void delete(EmpresaDto empresaDto);
		public void viewAnuncios(EmpresaDto empresaDto);
	}

}
