package co.com.nuevaera.client.view;


import java.util.List;

import co.com.nuevaera.client.dto.EmisionDto;
import co.com.nuevaera.client.dto.RestauranteDto;
import co.com.nuevaera.client.presenter.EmisionDataProvider;
import co.com.nuevaera.client.presenter.RestaurantesDataProvider;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface EmisionView extends IsWidget{
	
	public void setPresenter(Presenter listener);
	public void setModelData(List<EmisionDto> emisionesDataValues);
	public void setDataProvider(EmisionDataProvider emisionDataProvider);
	public void onTaskSucefull(String message);
	public void onTaskFail(String message);
	public void onLoadRestaurantes(List<RestauranteDto> restauranteDtos);
	
	public interface Presenter {
		public void onView();
		public void goTo(Place place);
		public void save(EmisionDto emisionDto);
		public void reload();
		public void delete(EmisionDto emisionDto);
		public void loadRestaurantes();
		public void viewAnuncios(EmisionDto emisionDto);
		
	}

}
