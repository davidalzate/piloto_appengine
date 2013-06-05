package co.com.nuevaera.client.view;


import java.util.List;

import co.com.nuevaera.client.dto.CategoriaDto;
import co.com.nuevaera.client.dto.RestauranteDto;
import co.com.nuevaera.client.presenter.CategoriaDataProvider;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface CategoriaView extends IsWidget{
	
	public void setPresenter(Presenter listener);
	public void setModelData(List<CategoriaDto> categoriasDataValues);
	public void setDataProvider(CategoriaDataProvider categoriasDataProvider);
	public void onTaskSucefull(String message);
	public void onTaskFail(String message);
	public void onLoadRestaurantes(List<RestauranteDto> restauranteDtos);
	public void onStartBlobStoreSessionSucefull(String actionURL);
	public void onStartBlobStoreSessionFail();
	public void onGetImageUrlSucefull(String imageUrl, int widgetId);
	public void onGetImageUrlFail(String mesage);
	
	public interface Presenter {
		public void onView();
		public void goTo(Place place);
		public void save(CategoriaDto categoriaDto);
		public void reload();
		public void delete(CategoriaDto categoriaDto);
		public void loadRestaurantes();
		public void viewElementos(CategoriaDto categoria);
		public void startNewBlobstoreSession();
		public void getImageUrl(String key, int witgetId);
	}

}
