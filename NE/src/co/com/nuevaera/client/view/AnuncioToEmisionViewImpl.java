package co.com.nuevaera.client.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import co.com.nuevaera.client.dto.AnuncioDto;
import co.com.nuevaera.client.dto.CategoriaDto;
import co.com.nuevaera.client.presenter.AnuncioDataProvider;
import co.com.nuevaera.client.presenter.AnuncioToEmisionDataProvider;
import co.com.nuevaera.client.view.AnuncioEmisionView.Presenter;
import co.com.nuevaera.client.view.AnuncioViewImpl.AnuncioViewImplUiBinder;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;

public class AnuncioToEmisionViewImpl extends Composite{

	private Presenter presenter;
	
	private AnuncioToEmisionDataProvider anuncioDataProvider;

	private SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	
	@UiField(provided = true)
	DataGrid<AnuncioDto> dataGrid = new DataGrid<AnuncioDto>(AnuncioDto.KEY_PROVIDER);
	@UiField(provided = true) 
	SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	
	private static AnuncioToEmisionViewImplUiBinder uiBinder = GWT.create(AnuncioToEmisionViewImplUiBinder.class);

	interface AnuncioToEmisionViewImplUiBinder extends UiBinder<Widget, AnuncioToEmisionViewImpl> {
	}
	
	

	public AnuncioToEmisionViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		
		final MultiSelectionModel<AnuncioDto> selectionModel =
	            new MultiSelectionModel<AnuncioDto>(AnuncioDto.KEY_PROVIDER);
	        dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
	            .<AnuncioDto> createCheckboxManager());
	        
	        
		
		dataGrid.setEmptyTableWidget(new Label("Opps! :o Our DB is empty !!"));
		

		pager.setDisplay(dataGrid);
		
		initTableColumns(selectionModel);
	}

	
	private void initTableColumns(
			final SelectionModel<AnuncioDto> selectionModel) {

		Column<AnuncioDto, Boolean> checkColumn = new Column<AnuncioDto, Boolean>(
				new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(AnuncioDto object) {
				return selectionModel.isSelected(object);
			}
		};

		dataGrid.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		dataGrid.setColumnWidth(checkColumn, 40, Unit.PX);

		Column<AnuncioDto, String> duracionColumn = new Column<AnuncioDto, String>(
				new EditTextCell()) {
			@Override
			public String getValue(AnuncioDto object) {
				return ""+object.getDuracion();
			}
		};

		dataGrid.addColumn(duracionColumn, "Duración");
		duracionColumn
				.setFieldUpdater(new FieldUpdater<AnuncioDto, String>() {

					@Override
					public void update(int index, AnuncioDto categoria,
							String duracion) {
						categoria.setDuracion(Integer.parseInt(duracion));
					}

				});
		
		dataGrid.setColumnWidth(duracionColumn, 5, Unit.PCT);

	
/*
		Column<AnuncioDto, String> fotoBigColumn = new Column<AnuncioDto, String>(
				new EditTextCell()) {
			@Override
			public String getValue(AnuncioDto object) {
				return object.getFotoBig();
			}
		};
		
		dataGrid.addColumn(fotoBigColumn, "Foto grande");
		fotoBigColumn.setFieldUpdater(new FieldUpdater<AnuncioDto, String>() {
			@Override
			public void update(int index, AnuncioDto object, String fotoBig) {
				object.setFotoBig(fotoBig);
			}
		});
		dataGrid.setColumnWidth(fotoBigColumn, 20, Unit.PCT);*/
		
		Column<AnuncioDto, String> fotoSmallColumn = new Column<AnuncioDto, String>(
				new ImageCell()) {
			@Override
			public String getValue(AnuncioDto object) {
				return object.getFotoSmall();
			}
		};
		
		dataGrid.addColumn(fotoSmallColumn, "Foto pequeña");
		fotoSmallColumn.setFieldUpdater(new FieldUpdater<AnuncioDto, String>() {
			@Override
			public void update(int index, AnuncioDto object, String fotoSmall) {
				object.setFotoSmall(fotoSmall);
			}
		});
		dataGrid.setColumnWidth(fotoSmallColumn, 55, Unit.PCT);
		
		
	}


	public void setModelData(List<AnuncioDto> categoriaDataValues) {
		if(null!=categoriaDataValues){
			dataGrid.setRowData(categoriaDataValues);
			dataGrid.setPageSize(50);
			//dataGrid.setRowCount(900);
			dataGrid.redraw();
			//pager.setPageSize(50);
			//presenter.loadEmpresas();
		}else{
			Window.alert("No se encontraron datos de categorias.");
		}		
	}

	public void setDataProvider(AnuncioToEmisionDataProvider anuncioDataProvider) {
		this.anuncioDataProvider = anuncioDataProvider;
		anuncioDataProvider.addDataDisplay(dataGrid);	
		
	}	
	public void setPresenter(Presenter presenter){
		this.presenter = presenter;
	}
	
	public AnuncioDto getSelectedAnuncio(){
		AnuncioDto result=null;
		Set<AnuncioDto> selectedRows = ((MultiSelectionModel<AnuncioDto>)this.dataGrid.getSelectionModel()).getSelectedSet();
		if(null!=selectedRows&&selectedRows.size()>0){
			if(selectedRows.size()==1){
				List <AnuncioDto>selectedCategorias = new ArrayList<AnuncioDto>(selectedRows);
				result = selectedCategorias.get(0);
			}else{
				Window.alert("Selecciona una sola fila, solo puedes borrar una categoria a la vez.");
			}
		}		
		return result;
	}


	
}
