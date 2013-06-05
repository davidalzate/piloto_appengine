package co.com.nuevaera.client.view;

import java.util.List;

import co.com.nuevaera.client.dto.ElementoDto;
import co.com.nuevaera.client.presenter.ElementoDataProvider;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface ElementoView extends IsWidget{
	
	public void setPresenter(Presenter listener);
	public void setModelData(List<ElementoDto> elementosDtoDataValues);
	public void setDataProvider(ElementoDataProvider elementoDataProvider);
	public void onTaskSucefull(String message);
	public void onTaskFail(String message);
	public void onLoadCategorias(List<ElementoDto> elementosDtos);
	public void onStartBlobStoreSessionSucefull(String actionURL);
	public void onStartBlobStoreSessionFail();
	public void onGetImageUrlSucefull(String imageUrl, int widgetId);
	public void onGetImageUrlFail(String mesage);
	
	public interface Presenter {
		public void onView();
		public void goTo(Place place);
		public void save(ElementoDto elementoDto);
		public void reload();
		public void delete(ElementoDto elementoDto);
		public void loadCategorias();
		public void startNewBlobstoreSession();
		public void getImageUrl(String key, int witgetId);		
	}

}