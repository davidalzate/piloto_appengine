package co.com.nuevaera.client.view;



import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import co.com.nuevaera.client.dto.CategoriaDto;
import co.com.nuevaera.client.dto.RestauranteDto;
import co.com.nuevaera.client.presenter.CategoriaDataProvider;
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
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

public class CategoriaViewImpl extends Composite implements CategoriaView{
	
	private Presenter presenter;
	private CategoriaDataProvider categoriaDataProvider;

	private static CategoriaViewImplUiBinder uiBinder = GWT.create(CategoriaViewImplUiBinder.class);
	private SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	
	@UiField(provided = true)
	DataGrid<CategoriaDto> dataGrid = new DataGrid<CategoriaDto>(CategoriaDto.KEY_PROVIDER);
	@UiField(provided = true) 
	SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	@UiField 
	Button btnNuevaCategoria;
	@UiField Button btnBorrarCategoria;
	@UiField Button btnVerElementos;
	@UiField Label restauranteLabel;
	
	private DialogBox nuevaCategoriaDialogBox;
	private FormPanel bannerUploadFormPanel;
	private long selectedIdRestaurante;
	
	private Button uploadButtonBanner;
	private TextBox fotoTextBox;

	private RestauranteDto restaurante;

	interface CategoriaViewImplUiBinder extends UiBinder<Widget, CategoriaViewImpl> {
	}

	public CategoriaViewImpl(RestauranteDto restaurante) {
		initWidget(uiBinder.createAndBindUi(this));
		
	    final MultiSelectionModel<CategoriaDto> selectionModel =
	            new MultiSelectionModel<CategoriaDto>(CategoriaDto.KEY_PROVIDER);
	        dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
	            .<CategoriaDto> createCheckboxManager());
	        
	        
		
		dataGrid.setEmptyTableWidget(new Label("Opps! :o Our DB empty !!"));
		

		pager.setDisplay(dataGrid);
		
		initTableColumns(selectionModel);
		
		restauranteLabel.setText(restaurante.getNombre());
		
		this.restaurante = restaurante;
	}

	public CategoriaViewImpl(String firstName) {
		
		initWidget(uiBinder.createAndBindUi(this));
		
		final SelectionModel<CategoriaDto> selectionModel = new SingleSelectionModel<CategoriaDto>(
				CategoriaDto.KEY_PROVIDER);
		dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
				.<CategoriaDto> createDefaultManager());
		
		dataGrid.setEmptyTableWidget(new Label("Opps! :o Our DB empty !!"));
		
		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0,
				true);
		pager.setDisplay(dataGrid);
		
		initTableColumns(selectionModel);
		
	}

	private void initTableColumns(
			final SelectionModel<CategoriaDto> selectionModel) {

		Column<CategoriaDto, Boolean> checkColumn = new Column<CategoriaDto, Boolean>(
				new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(CategoriaDto object) {
				// Get the value from the selection model.
				return selectionModel.isSelected(object);
			}
		};

		dataGrid.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		dataGrid.setColumnWidth(checkColumn, 40, Unit.PX);

		Column<CategoriaDto, String> nameColumn = new Column<CategoriaDto, String>(
				new EditTextCell()) {
			@Override
			public String getValue(CategoriaDto object) {
				return object.getNombre();
			}
		};

		dataGrid.addColumn(nameColumn, "Nombre");
		nameColumn
				.setFieldUpdater(new FieldUpdater<CategoriaDto, String>() {

					@Override
					public void update(int index, CategoriaDto categoria,
							String nombre) {
						categoria.setNombre(nombre);
					}

				});
		
		dataGrid.setColumnWidth(nameColumn, 20, Unit.PCT);

		Column<CategoriaDto, String> fotoColumn = new Column<CategoriaDto, String>(
				new EditTextCell()) {
			@Override
			public String getValue(CategoriaDto object) {
				return object.getFoto();
			}
		};
		
		dataGrid.addColumn(fotoColumn, "Nombre");
		nameColumn
				.setFieldUpdater(new FieldUpdater<CategoriaDto, String>() {

					@Override
					public void update(int index, CategoriaDto categoria,
							String foto) {
						categoria.setFoto(foto);
					}

				});
		
		dataGrid.setColumnWidth(fotoColumn, 20, Unit.PCT);
		// Last name.
		Column<CategoriaDto, String> restauranteColumn = new Column<CategoriaDto, String>(
				new EditTextCell()) {
			@Override
			public String getValue(CategoriaDto object) {
				return ""+object.getIdRestaurante();
			}
		};
		
		dataGrid.addColumn(restauranteColumn, "Restaurante");
		restauranteColumn.setFieldUpdater(new FieldUpdater<CategoriaDto, String>() {
			@Override
			public void update(int index, CategoriaDto object, String idRestaurante) {
				object.setIdRestaurante(Integer.parseInt(idRestaurante));
				//ContactDatabase.get().refreshDisplays();
			}
		});
		dataGrid.setColumnWidth(restauranteColumn, 20, Unit.PCT);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		this.nuevaCategoriaDialogBox = createDialogBox("Nueva categoria");
	}

	@Override
	public void setModelData(List<CategoriaDto> categoriaDataValues) {
		if(null!=categoriaDataValues){
			dataGrid.setRowData(categoriaDataValues);
			dataGrid.setPageSize(50);
			//dataGrid.setRowCount(900);
			dataGrid.redraw();
			//pager.setPageSize(50);
			presenter.loadRestaurantes();
		}else{
			Window.alert("No se encontraron datos de categorias.");
		}
		
	}

	@Override
	public void setDataProvider(CategoriaDataProvider categoriaDataProvider) {
		this.categoriaDataProvider = categoriaDataProvider;
		categoriaDataProvider.addDataDisplay(dataGrid);	
		
	}
	@UiHandler("btnNuevaCategoria")
	void onBtnNuevaCategoriaClick(ClickEvent event) {
		nuevaCategoriaDialogBox.center();
		nuevaCategoriaDialogBox.show();
	}
	
	private DialogBox createDialogBox(String title) {
		
	
		    final DialogBox dialogBox = new DialogBox();
		    dialogBox.ensureDebugId("cwDialogBox");
		    dialogBox.setText(title);

		    VerticalPanel dialogContents = new VerticalPanel();
		    dialogContents.setSpacing(4);
		    dialogBox.setWidget(dialogContents);
		    
		    //HorizontalPanel nombreRestaurantePanel = new HorizontalPanel();
		    HorizontalPanel nombreCategoriaPanel = new HorizontalPanel();
		    HorizontalPanel fotoPanel = new HorizontalPanel();
		    HorizontalPanel fotoRestauranteUploadPanel = new HorizontalPanel();
		    
		    
		    //Label nombreRestauranteLabel = new Label("Restaurabte");
		    //nombreRestauranteLabel.setWidth("75px");
		    Label nombreLabel = new Label("Nombre");
		    nombreLabel.setWidth("75px");
		    Label fotoLabel = new Label("Foto");
		    fotoLabel.setWidth("75px");

		    

		    /*SuggestionOracle  nombreRestauranteTextBox = new SuggestionOracle();*/		    
		    final TextBox nombreTextBox = new TextBox();
		    nombreTextBox.setWidth("375px");
		    fotoTextBox = new TextBox();
		    fotoTextBox.setWidth("375px");

		    
		    /*final SuggestBox suggestBox = new SuggestBox(nombreRestauranteTextBox);
		    suggestBox.addSelectionHandler(new SelectionHandler<Suggestion>() {
				
				@Override
				public void onSelection(SelectionEvent<Suggestion> event) {
					selectedIdRestaurante = ((Suggestions)event.getSelectedItem()).getId();
				}
			});*/
		    
		    this.bannerUploadFormPanel = createUploadBannerFormPanel();
		    
		    //nombreRestaurantePanel.add(nombreRestauranteLabel);
		    //nombreRestaurantePanel.add(suggestBox);
		    nombreCategoriaPanel.add(nombreLabel);
		    nombreCategoriaPanel.add(nombreTextBox);
		    fotoPanel.add(fotoLabel);
		    fotoPanel.add(fotoTextBox);
		    fotoRestauranteUploadPanel.add(new Label(""));
		    fotoRestauranteUploadPanel.add(bannerUploadFormPanel);
		    
		    //dialogContents.add(nombreRestaurantePanel);
		    dialogContents.add(nombreCategoriaPanel);
		    dialogContents.add(fotoPanel);
		    dialogContents.add(fotoRestauranteUploadPanel);
		    
		    Button aceptButton = new Button(
		        "Aceptar", new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						CategoriaDto categoriaDto = new CategoriaDto();
						categoriaDto.setIdRestaurante(restaurante.getIdRestaurante());
						categoriaDto.setNombre(nombreTextBox.getText());
						categoriaDto.setFoto(fotoTextBox.getText());
						presenter.save(categoriaDto);
						dialogBox.hide();
						//suggestBox.setText("");
						nombreTextBox.setText("");
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

	private  FormPanel createUploadBannerFormPanel() {
		final FormPanel bannerUploadFormPanel = new FormPanel();
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		FileUpload uploadField1 = new FileUpload();
		uploadButtonBanner = new Button();
		horizontalPanel.add(uploadField1);
		horizontalPanel.add(uploadButtonBanner);
		bannerUploadFormPanel.add(horizontalPanel);

		uploadButtonBanner.setText("...");
		
		uploadButtonBanner.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				bannerUploadFormPanel.submit();				
			}
		});
		bannerUploadFormPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		bannerUploadFormPanel.setMethod(FormPanel.METHOD_POST);
		uploadButtonBanner.setEnabled(false);
		uploadField1.setName("image");
 
		startNewBlobstoreSession();
 
		bannerUploadFormPanel.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				presenter.getImageUrl(event.getResults(),1);
			}
		});
		return bannerUploadFormPanel;
	}
	
	
    private void startNewBlobstoreSession() {    	
    	presenter.startNewBlobstoreSession();
    }
	
	@Override
	public void onTaskSucefull(String message) {
		presenter.reload();
	}

	@Override
	public void onTaskFail(String message) {
		Window.alert("Ocurrio un error creando la categoria..");		
	}
	
	@UiHandler("btnBorrarCategoria")
	void onBtnBorrarCategoriaClick(ClickEvent event) {
		CategoriaDto result = getSelectedCategoria();
		if(null!=result){

			presenter.delete(result);
		}
	}

	@Override
	public void onLoadRestaurantes(List<RestauranteDto> restauranteDtos) {

		
	}

	@UiHandler("btnVerElementos")
	void onBtnVerElementosClick(ClickEvent event) {
		CategoriaDto result = getSelectedCategoria();
		if(null!=result){
			presenter.viewElementos(result);
		}
	}
	
	private CategoriaDto getSelectedCategoria(){
		CategoriaDto result=null;
		Set<CategoriaDto> selectedRows = ((MultiSelectionModel<CategoriaDto>)this.dataGrid.getSelectionModel()).getSelectedSet();
		if(null!=selectedRows&&selectedRows.size()>0){
			if(selectedRows.size()==1){
				List <CategoriaDto>selectedCategorias = new ArrayList<CategoriaDto>(selectedRows);
				result = selectedCategorias.get(0);
			}else{
				Window.alert("Selecciona una sola fila, solo puedes borrar una categoria a la vez.");
			}
		}		
		return result;
	}


	@Override
	public void onStartBlobStoreSessionSucefull(String actionURL) {
        bannerUploadFormPanel.setAction(actionURL);
        uploadButtonBanner.setText("Upload");
        uploadButtonBanner.setEnabled(true);
	}

	@Override
	public void onStartBlobStoreSessionFail() {
		Window.alert("Ocurrio un error en el servicio de carga de imagenes");
	}

	@Override
	public void onGetImageUrlSucefull(String imageUrl, int widtgetId) {
			   this.fotoTextBox.setText(imageUrl);	
			   bannerUploadFormPanel.reset();
		       startNewBlobstoreSession();
		  
		       Image image = new Image();
		       image.setUrl(imageUrl);
		    
		       final PopupPanel imagePopup = new PopupPanel(true);
		       imagePopup.setWidget(image);
		    
		       imagePopup.setAnimationEnabled(true); 
		       imagePopup.setGlassEnabled(true); 
		       imagePopup.setAutoHideEnabled(true); 

		       imagePopup.center();				
	}

	@Override
	public void onGetImageUrlFail(String message) {
		Window.alert("Ocurrio un error cargando la imagen: "+message);
		
	}

	public RestauranteDto getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(RestauranteDto restaurante) {
		this.restaurante = restaurante;
	}
}