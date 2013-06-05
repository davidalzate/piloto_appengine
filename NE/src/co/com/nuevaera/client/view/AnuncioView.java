package co.com.nuevaera.client.view;

import java.util.List;

import co.com.nuevaera.client.dto.AnuncioDto;
import co.com.nuevaera.client.dto.ElementoDto;
import co.com.nuevaera.client.dto.EmpresaDto;
import co.com.nuevaera.client.presenter.AnuncioDataProvider;
import co.com.nuevaera.client.presenter.ElementoDataProvider;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface AnuncioView extends IsWidget{
	
	public void setPresenter(Presenter listener);
	public void setModelData(List<AnuncioDto> anunciosDtoDataValues);
	public void setDataProvider(AnuncioDataProvider anuncioDataProvider);
	public void onTaskSucefull(String message);
	public void onTaskFail(String message);
	public void onLoadEmpresas(List<EmpresaDto> empresaDto);
	public void onStartBlobStoreSessionSucefull(String actionURL);
	public void onStartBlobStoreSessionFail();
	public void onGetImageUrlSucefull(String imageUrl, int widgetId);
	public void onGetImageUrlFail(String mesage);
	
	public interface Presenter {
		public void onView();
		public void goTo(Place place);
		public void save(AnuncioDto anuncioDto);
		public void reload();
		public void delete(AnuncioDto anuncioDto);
		public void loadEmpresas();
		public void startNewBlobstoreSession();
		public void getImageUrl(String key, int witgetId);		
	}

}