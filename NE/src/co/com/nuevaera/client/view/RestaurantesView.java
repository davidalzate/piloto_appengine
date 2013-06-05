package co.com.nuevaera.client.view;


import java.util.List;

import co.com.nuevaera.client.dto.RestauranteDto;
import co.com.nuevaera.client.presenter.RestaurantesDataProvider;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface RestaurantesView extends IsWidget{
	
	public void setPresenter(Presenter listener);
	public void setModelData(List<RestauranteDto> restaurantesDataValues);
	public void setDataProvider(RestaurantesDataProvider restaurantesDataProvider);
	public void onTaskSucefull(String message);
	public void onTaskFail(String message);
	public void onStartBlobStoreSessionSucefull(String actionURL);
	public void onStartBlobStoreSessionFail();
	public void onGetImageUrlSucefull(String imageUrl, int widgetId);
	public void onGetImageUrlFail(String mesage);
	
	public interface Presenter {
		public void onView();
		public void goTo(Place place);
		public void save(RestauranteDto restauranteDto);
		public void reload();
		public void delete(RestauranteDto restauranteDto);
		public void viewCateogoria(RestauranteDto restauranteDto);
		public void startNewBlobstoreSession();
		public void getImageUrl(String key, int witgetId);
	}

}
