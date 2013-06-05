package co.com.nuevaera.client.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import co.com.nuevaera.client.dto.AnuncioDto;
import co.com.nuevaera.client.dto.CategoriaDto;
import co.com.nuevaera.client.dto.EmisionDto;
import co.com.nuevaera.client.presenter.AnuncioDataProvider;
import co.com.nuevaera.client.presenter.AnuncioEmisionDataProvider;
import co.com.nuevaera.client.presenter.AnuncioToEmisionDataProvider;

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
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;

public class AnuncioEmisionViewImpl extends Composite implements AnuncioEmisionView {
	
	private Presenter presenter;
	private AnuncioEmisionDataProvider anuncioEmisionDataProvider;
	
	private static AnuncioEmisionViewImplUiBinder uiBinder = GWT.create(AnuncioEmisionViewImplUiBinder.class);
	private SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);

	@UiField(provided = true)
	DataGrid<AnuncioDto> dataGrid = new DataGrid<AnuncioDto>(AnuncioDto.KEY_PROVIDER);
	@UiField(provided = true) 
	SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	@UiField 
	Button btnNuevaAnuncio;
	@UiField Button btnBorrarCategoria;

	@UiField Label empresaLabel;
	@UiField AnuncioToEmisionViewImpl anunciosPanel;
	
	private EmisionDto emisionDto;
	
	private DialogBox anunciosToEmision;
	private AnuncioToEmisionDataProvider anuncioToEmisionDataProvider;

	
	interface AnuncioEmisionViewImplUiBinder extends UiBinder<Widget, AnuncioEmisionViewImpl> {
	}

	public AnuncioEmisionViewImpl(EmisionDto emisionDto) {
		this.emisionDto = emisionDto;
		initWidget(uiBinder.createAndBindUi(this));
		final MultiSelectionModel<AnuncioDto> selectionModel =
		            new MultiSelectionModel<AnuncioDto>(AnuncioDto.KEY_PROVIDER);
		        dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
		            .<AnuncioDto> createCheckboxManager());
		        
		        
			
			dataGrid.setEmptyTableWidget(new Label("Opps! :o Our DB is empty !!"));
			

			pager.setDisplay(dataGrid);
			
			initTableColumns(selectionModel);
			
			
			empresaLabel.setText(emisionDto.getPropiedad());		
			
		
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

	

		/*Column<AnuncioDto, String> fotoBigColumn = new Column<AnuncioDto, String>(
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

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		anunciosToEmision =  createAnunciosDialogBox("Anuncios");
	}

	private DialogBox createAnunciosDialogBox(String text){
	    final DialogBox dialogBox = new DialogBox();
	    dialogBox.ensureDebugId("cwDialogBox");
	    dialogBox.setText(text);
	    AnuncioToEmisionViewImpl anuncioToEmisionViewImpl  = new AnuncioToEmisionViewImpl();
	    anuncioToEmisionViewImpl.setPresenter(presenter);
	    anuncioToEmisionViewImpl.setDataProvider(anuncioToEmisionDataProvider);
	    dialogBox.add(anuncioToEmisionViewImpl);
	    dialogBox.setSize("800px","600px");
	    return dialogBox;
	}


	@Override
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

	@Override
	public void setDataProvider(AnuncioEmisionDataProvider anuncioEmisionDataProvider) {
		this.anuncioEmisionDataProvider = anuncioEmisionDataProvider;
		anuncioEmisionDataProvider.addDataDisplay(dataGrid);	
		
	}

	@Override
	public void onTaskSucefull(String message) {
		presenter.reload();
	}




	@Override
	public void onTaskFail(String message) {
		Window.alert("Ocurrio un error adicionando el anuncio.");	
	}

	@UiHandler("btnNuevaAnuncio")
	void onBtnNuevaAnuncioClick(ClickEvent event) {
		AnuncioDto anuncioDto = anunciosPanel.getSelectedAnuncio();
		presenter.addAnuncio(anuncioDto);
	}

	@Override
	public void setAnuncioToEmisionDataProvider(AnuncioToEmisionDataProvider anuncioToEmisionDataProvider) {
		this.anuncioToEmisionDataProvider = anuncioToEmisionDataProvider;
		
	}

	@Override
	public void setAModelData(List<AnuncioDto> anunciosDtoDataValues) {
		anunciosPanel.setModelData(anunciosDtoDataValues);		
	}
}
