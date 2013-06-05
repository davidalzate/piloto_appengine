package co.com.nuevaera.client.view;

import java.util.List;

import co.com.nuevaera.client.dto.AnuncioDto;
import co.com.nuevaera.client.presenter.AnuncioDataProvider;
import co.com.nuevaera.client.presenter.AnuncioEmisionDataProvider;
import co.com.nuevaera.client.presenter.AnuncioToEmisionDataProvider;


import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface AnuncioEmisionView extends IsWidget{
	
	public void setPresenter(Presenter listener);
	public void setModelData(List<AnuncioDto> anunciosDtoDataValues);
	public void setAModelData(List<AnuncioDto> anunciosDtoDataValues);
	public void setDataProvider(AnuncioEmisionDataProvider anuncioDataProvider);
	public void setAnuncioToEmisionDataProvider(AnuncioToEmisionDataProvider anuncioDataProvider);
	
	public void onTaskSucefull(String message);
	public void onTaskFail(String message);
	
	public interface Presenter {
		public void onView();
		public void goTo(Place place);
		public void save(AnuncioDto anuncioDto);
		public void reload();
		public void addAnuncio(AnuncioDto anuncioDto);
		public void delete(AnuncioDto anuncioDto);
	}
}
