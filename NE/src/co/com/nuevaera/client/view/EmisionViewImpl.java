package co.com.nuevaera.client.view;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import co.com.nuevaera.client.dto.CategoriaDto;
import co.com.nuevaera.client.dto.EmisionDto;
import co.com.nuevaera.client.dto.RestauranteDto;
import co.com.nuevaera.client.presenter.EmisionDataProvider;
import co.com.nuevaera.client.ui.SuggestionOracle;
import co.com.nuevaera.client.ui.Suggestions;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
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
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;


public class EmisionViewImpl extends Composite implements EmisionView{
	
	private Presenter presenter;
	private EmisionDataProvider emisionDataProvider;

	private static EmisionViewImplUiBinder uiBinder = GWT.create(EmisionViewImplUiBinder.class);
	private SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	
	@UiField(provided = true)
	DataGrid<EmisionDto> dataGrid = new DataGrid<EmisionDto>(EmisionDto.KEY_PROVIDER);
	@UiField(provided = true) 
	SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	@UiField 
	Button btnNuevaEmision;
	@UiField Button btnBorrarEmision;
	@UiField Button button;
	
	private DialogBox nuevaEmisionDialogBox;
	


	interface EmisionViewImplUiBinder extends UiBinder<Widget, EmisionViewImpl> {
	}

	public EmisionViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		
	    final MultiSelectionModel<EmisionDto> selectionModel =
	            new MultiSelectionModel<EmisionDto>(EmisionDto.KEY_PROVIDER);
	        dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
	            .<EmisionDto> createCheckboxManager());
	        
	        
		
		dataGrid.setEmptyTableWidget(new Label("Opps! :o Our DB empty !!"));
		

		pager.setDisplay(dataGrid);
		
		initTableColumns(selectionModel);
		
		nuevaEmisionDialogBox = createDialogBox("Nueva emisión");
		
	}

	public EmisionViewImpl(String firstName) {
		
		initWidget(uiBinder.createAndBindUi(this));
		
		final SelectionModel<EmisionDto> selectionModel = new SingleSelectionModel<EmisionDto>(
				EmisionDto.KEY_PROVIDER);
		dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
				.<EmisionDto> createDefaultManager());
		
		dataGrid.setEmptyTableWidget(new Label("Opps! :o Our DB empty !!"));
		
		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0,
				true);
		pager.setDisplay(dataGrid);
		
		initTableColumns(selectionModel);
		
	}

	private void initTableColumns(
			final SelectionModel<EmisionDto> selectionModel) {

		Column<EmisionDto, Boolean> checkColumn = new Column<EmisionDto, Boolean>(
				new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(EmisionDto object) {
				// Get the value from the selection model.
				return selectionModel.isSelected(object);
			}
		};

		dataGrid.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		dataGrid.setColumnWidth(checkColumn, 40, Unit.PX);

		Column<EmisionDto, String> propiedadColumn = new Column<EmisionDto, String>(
				new EditTextCell()) {
			@Override
			public String getValue(EmisionDto object) {
				return object.getPropiedad();
			}
		};

		dataGrid.addColumn(propiedadColumn, "Propiedad");
		propiedadColumn
				.setFieldUpdater(new FieldUpdater<EmisionDto, String>() {

					@Override
					public void update(int index, EmisionDto emision,
							String propiedad) {
						emision.setPropiedad(propiedad);
					}

				});
		
		dataGrid.setColumnWidth(propiedadColumn, 20, Unit.PCT);

		// Last name.
		Column<EmisionDto, String> restauranteColumn = new Column<EmisionDto, String>(
				new EditTextCell()) {
			@Override
			public String getValue(EmisionDto object) {
				return ""+object.getIdRestaurante();
			}
		};
		
		dataGrid.addColumn(restauranteColumn, "Restaurante");
		restauranteColumn.setFieldUpdater(new FieldUpdater<EmisionDto, String>() {
			@Override
			public void update(int index, EmisionDto object, String idRestaurante) {
				object.setIdRestaurante(Integer.parseInt(idRestaurante));
				//ContactDatabase.get().refreshDisplays();
			}
		});
		dataGrid.setColumnWidth(restauranteColumn, 20, Unit.PCT);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setModelData(List<EmisionDto> emisionesDataValues) {
		if(null!=emisionesDataValues){
			dataGrid.setRowData(emisionesDataValues);
			dataGrid.setPageSize(50);
			//dataGrid.setRowCount(900);
			dataGrid.redraw();
			//pager.setPageSize(50);
			presenter.loadRestaurantes();
		}else{
			Window.alert("No se encontraron datos de emisiones.");
		}
		
	}

	@Override
	public void setDataProvider(EmisionDataProvider emisionDataProvider) {
		this.emisionDataProvider = emisionDataProvider;
		emisionDataProvider.addDataDisplay(dataGrid);	
		
	}
	@UiHandler("btnNuevaEmision")
	void onBtnNuevaEmisionClick(ClickEvent event) {
		nuevaEmisionDialogBox.center();
		nuevaEmisionDialogBox.show();
	}
	
	private DialogBox createDialogBox(String title) {
		
	
		    final DialogBox dialogBox = new DialogBox();
		    dialogBox.ensureDebugId("cwDialogBox");
		    dialogBox.setText(title);

		    VerticalPanel dialogContents = new VerticalPanel();
		    dialogContents.setSpacing(4);
		    dialogBox.setWidget(dialogContents);
		    
		    HorizontalPanel nombreRestaurantePanel = new HorizontalPanel();
		    HorizontalPanel propiedadBarPanel = new HorizontalPanel();
		    
		    
		    Label nombreRestauranteLabel = new Label("Restaurabte");
		    nombreRestauranteLabel.setWidth("75px");
		    Label propiedadLabel = new Label("Propiedad");
		    propiedadLabel.setWidth("75px");

		    
		   // final TextBox nombreRestauranteTextBox = new TextBox();
		   // nombreRestauranteTextBox.setWidth("375px");
		    SuggestionOracle  nombreRestauranteTextBox = new SuggestionOracle();		    
		    final TextBox propiedadTextBox = new TextBox();
		    propiedadTextBox.setWidth("375px");

		    nombreRestaurantePanel.add(nombreRestauranteLabel);
		    final SuggestBox suggestBox = new SuggestBox(nombreRestauranteTextBox);
		    suggestBox.addSelectionHandler(new SelectionHandler<Suggestion>() {
				
				@Override
				public void onSelection(SelectionEvent<Suggestion> event) {
					// TODO Auto-generated method stub
					Window.alert(""+((Suggestions)event.getSelectedItem()).getId());
				}
			});
		   
		    nombreRestaurantePanel.add(suggestBox);
		    propiedadBarPanel.add(propiedadLabel);
		    propiedadBarPanel.add(propiedadTextBox);
		    
		    dialogContents.add(nombreRestaurantePanel);
		    dialogContents.add(propiedadBarPanel);
		    
		    Button aceptButton = new Button(
		        "Aceptar", new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						EmisionDto emisionDto = new EmisionDto();
						//emisionDto.setIdRestaurante(Integer.parseInt(suggestBox.getSuggestOracle().Text()));
						emisionDto.setPropiedad(propiedadTextBox.getText());
						presenter.save(emisionDto);
						dialogBox.hide();
						suggestBox.setText("");
						propiedadTextBox.setText("");
					}
			});
		    
		    Button closeButton = new Button(
			        "Cancelar", new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							 dialogBox.hide();
						}
				});
		    
		    HorizontalPanel bottomBar = new HorizontalPanel();
		    bottomBar.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		    bottomBar.setSpacing(10);
		    bottomBar.add(aceptButton);
		    bottomBar.add(closeButton);
		    
		    dialogContents.add(bottomBar);
	        dialogContents.setCellHorizontalAlignment(bottomBar, HasHorizontalAlignment.ALIGN_RIGHT);

		    
		return dialogBox;
	}

	@Override
	public void onTaskSucefull(String message) {
		presenter.reload();
	}

	@Override
	public void onTaskFail(String message) {
		Window.alert("Ocurrio un error creando la emisión..");		
	}
	
	@UiHandler("btnBorrarEmision")
	void onBtnBorrarEmisionClick(ClickEvent event) {
		Set<EmisionDto> selectedRows = ((MultiSelectionModel<EmisionDto>)this.dataGrid.getSelectionModel()).getSelectedSet();
		if(null!=selectedRows&&selectedRows.size()>0){
			if(selectedRows.size()==1){
				List <EmisionDto>selectedEmisiones = new ArrayList<EmisionDto>(selectedRows);
				EmisionDto emisionDto = selectedEmisiones.get(0);
				presenter.delete(emisionDto);
			}else{
				Window.alert("Selecciona una sola fila, solo puedes borrar una emisión a la vez.");
			}
		}
	}

	@Override
	public void onLoadRestaurantes(List<RestauranteDto> restauranteDtos) {

		
	}

	@UiHandler("button")
	void onButtonClick(ClickEvent event) {
		EmisionDto result = getSelectedEmision();
		if(null!=result){
			presenter.viewAnuncios(result);
		}		
	}
	
	private EmisionDto getSelectedEmision(){
		EmisionDto result=null;
		Set<EmisionDto> selectedRows = ((MultiSelectionModel<EmisionDto>)this.dataGrid.getSelectionModel()).getSelectedSet();
		if(null!=selectedRows&&selectedRows.size()>0){
			if(selectedRows.size()==1){
				List <EmisionDto>selectedCategorias = new ArrayList<EmisionDto>(selectedRows);
				result = selectedCategorias.get(0);
			}else{
				Window.alert("Selecciona una sola fila, solo puedes borrar una categoria a la vez.");
			}
		}		
		return result;
	}
}