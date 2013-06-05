package co.com.nuevaera.client.view;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import co.com.nuevaera.client.dto.RestauranteDto;
import co.com.nuevaera.client.presenter.RestaurantesDataProvider;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;


public class RestauranteViewImpl extends Composite implements RestaurantesView{
	
	private Presenter presenter;
	private RestaurantesDataProvider restaurantesDataProvider;

	private static RestauranteViewImplUiBinder uiBinder = GWT.create(RestauranteViewImplUiBinder.class);
	private SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	
	@UiField(provided = true)
	DataGrid<RestauranteDto> dataGrid = new DataGrid<RestauranteDto>(RestauranteDto.KEY_PROVIDER);
	@UiField(provided = true) 
	SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	@UiField 
	Button btnNuevoRestaurante;
	@UiField Button btnBorrarRestaurante;
	@UiField Button btnVerCategoria;
	
	private DialogBox nuevoRestauranteDialogBox;
	
	private long selectedIdRestaurante;
	private FormPanel bannerUploadFormPanel;
	private FormPanel fondoUploadFormPanel;
	private Button uploadButtonBanner;
	private Button uploadButtonFondo;
	private TextBox bannerRestauranteTextBox;
	private TextBox fondoRestauranteTextBox;
	private FileUpload uploadField1;
	private FileUpload uploadField2;
	

	interface RestauranteViewImplUiBinder extends UiBinder<Widget, RestauranteViewImpl> {
	}

	public RestauranteViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		
	    final MultiSelectionModel<RestauranteDto> selectionModel = new MultiSelectionModel<RestauranteDto>(RestauranteDto.KEY_PROVIDER);
	    final SingleSelectionModel<RestauranteDto> singleSelectionModel = new SingleSelectionModel<RestauranteDto>(RestauranteDto.KEY_PROVIDER);
	    
	        dataGrid.setSelectionModel(singleSelectionModel, DefaultSelectionEventManager
	            .<RestauranteDto> createDefaultManager());
	        
	        
		
		dataGrid.setEmptyTableWidget(new Label("Opps! :o Our DB empty !!"));
		

		pager.setDisplay(dataGrid);
		
		initTableColumns(selectionModel);
		
	}



	private void initTableColumns(
			final SelectionModel<RestauranteDto> selectionModel) {

		/*Column<RestauranteDto, Boolean> checkColumn = new Column<RestauranteDto, Boolean>(
				new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(RestauranteDto object) {
				selectedIdRestaurante = object.getIdRestaurante();
				selectionModel.setSelected(object, false);
				return selectionModel.isSelected(object);
			}
		};

		dataGrid.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		dataGrid.setColumnWidth(checkColumn, 40, Unit.PX);*/

		Column<RestauranteDto, String> nameColumn = new Column<RestauranteDto, String>(
				new TextCell()) {
			@Override
			public String getValue(RestauranteDto object) {
				return object.getNombre();
			}
		};

		dataGrid.addColumn(nameColumn, "Nombre");
		nameColumn.setFieldUpdater(new FieldUpdater<RestauranteDto, String>() {

					@Override
					public void update(int index, RestauranteDto restaurante,
							String nombre) {
						restaurante.setNombre(nombre);
					}

				});
		dataGrid.setColumnWidth(nameColumn, 20, Unit.PCT);

		// Last name.
		Column<RestauranteDto, String> bannerColumn = new Column<RestauranteDto, String>(
				new TextCell()) {
			@Override
			public String getValue(RestauranteDto object) {
				return object.getBanner();
			}
		};
		
		dataGrid.addColumn(bannerColumn, "Banner");
		bannerColumn.setFieldUpdater(new FieldUpdater<RestauranteDto, String>() {
			@Override
			public void update(int index, RestauranteDto object, String banner) {
				object.setBanner(banner);
				//ContactDatabase.get().refreshDisplays();
			}
		});
		dataGrid.setColumnWidth(bannerColumn, 20, Unit.PCT);
		
		Column<RestauranteDto, String> backGroundColumn = new Column<RestauranteDto, String>(
				new TextCell()) {
			@Override
			public String getValue(RestauranteDto object) {
				return object.getFondo();
			}
		};
		
		dataGrid.addColumn(backGroundColumn, "Fondo");
		backGroundColumn.setFieldUpdater(new FieldUpdater<RestauranteDto, String>() {
			@Override
			public void update(int index, RestauranteDto object, String backGround) {
				object.setBanner(backGround);
				//ContactDatabase.get().refreshDisplays();
			}
		});
		dataGrid.setColumnWidth(backGroundColumn, 20, Unit.PCT);		

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		nuevoRestauranteDialogBox = createDialogBox("Nuevo restaurante");
	}

	@Override
	public void setModelData(List<RestauranteDto> restaurantesDataValues) {
		if(null!=restaurantesDataValues){
			dataGrid.setRowData(restaurantesDataValues);
			dataGrid.setPageSize(50);
			//dataGrid.setRowCount(900);
			dataGrid.redraw();
			//pager.setPageSize(50);
		}else{
			Window.alert("No se encontraron datos de restaurantes");
		}
		
	}

	@Override
	public void setDataProvider(RestaurantesDataProvider restaurantesDataProvider) {
		this.restaurantesDataProvider = restaurantesDataProvider;
		restaurantesDataProvider.addDataDisplay(dataGrid);	
		
	}
	@UiHandler("btnNuevoRestaurante")
	void onBtnNuevoRestauranteClick(ClickEvent event) {
		nuevoRestauranteDialogBox.center();
		nuevoRestauranteDialogBox.show();
	}
	
	private DialogBox createDialogBox(String title) {
		
	
		    final DialogBox dialogBox = new DialogBox();
		    dialogBox.ensureDebugId("cwDialogBox");
		    dialogBox.setText(title);

		    VerticalPanel dialogContents = new VerticalPanel();
		    dialogContents.setSpacing(4);
		    dialogBox.setWidget(dialogContents);
		    
		    HorizontalPanel nombreRestaurantePanel = new HorizontalPanel();
		    HorizontalPanel bannerRestauranteUploadPanel = new HorizontalPanel();
		    HorizontalPanel bannerRestauranteBarPanel = new HorizontalPanel();
		    HorizontalPanel fondoRestauranteUploadPanel = new HorizontalPanel();
		    HorizontalPanel fondoRestaurantePanel = new HorizontalPanel();
		    
		    
		    Label nombreRestauranteLabel = new Label("Nombre");
		    nombreRestauranteLabel.setWidth("75px");
		    
		    Label bannerRestauranteLabel = new Label("URL/Banner");
		    bannerRestauranteLabel.setWidth("75px");
		    
		    Label bannerRestauranteFileLabel = new Label("");
		    bannerRestauranteFileLabel.setWidth("75px");
		    
		    Label fondoRestauranteLabel = new Label("URL/Fondo");
		    fondoRestauranteLabel.setWidth("75px");
		    
		    Label fondoRestauranteFileLabel = new Label("");
		    fondoRestauranteFileLabel.setWidth("75px");
		    
		    
		    final TextBox nombreRestauranteTextBox = new TextBox();
		    nombreRestauranteTextBox.setWidth("375px");
		    bannerRestauranteTextBox = new TextBox();
		    bannerRestauranteTextBox.setWidth("375px");
		    bannerRestauranteTextBox.setFocus(true);
		    fondoRestauranteTextBox = new TextBox();
		    fondoRestauranteTextBox.setWidth("375px");
		    
		    createUploadBannerFormPanel();
		    createUploadFondoFormPanel();

		    nombreRestaurantePanel.add(nombreRestauranteLabel);
		    nombreRestaurantePanel.add(nombreRestauranteTextBox);
		    bannerRestauranteBarPanel.add(bannerRestauranteLabel);
		    bannerRestauranteBarPanel.add(bannerRestauranteTextBox);
		    bannerRestauranteUploadPanel.add(fondoRestauranteFileLabel);
		    bannerRestauranteUploadPanel.add(bannerUploadFormPanel);
		    fondoRestaurantePanel.add(fondoRestauranteLabel);
		    fondoRestaurantePanel.add(fondoRestauranteTextBox);
		    fondoRestauranteUploadPanel.add(bannerRestauranteFileLabel);
		    fondoRestauranteUploadPanel.add(fondoUploadFormPanel);
		    
		    dialogContents.add(nombreRestaurantePanel);
		    dialogContents.add(bannerRestauranteBarPanel);
		    dialogContents.add(bannerRestauranteUploadPanel);
		    dialogContents.add(fondoRestaurantePanel);
		    dialogContents.add(fondoRestauranteUploadPanel);
		    
		    Button aceptButton = new Button(
		        "Aceptar", new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						RestauranteDto restauranteDto = new RestauranteDto();
						restauranteDto.setNombre(nombreRestauranteTextBox.getText());
						restauranteDto.setBanner(bannerRestauranteTextBox.getText());
						restauranteDto.setFondo(fondoRestauranteTextBox.getText());
						presenter.save(restauranteDto);
						dialogBox.hide();
						nombreRestauranteTextBox.setText("");
						bannerRestauranteTextBox.setText("");
						fondoRestauranteTextBox.setText("");
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

	private  void createUploadBannerFormPanel() {
		bannerUploadFormPanel = new FormPanel();
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		uploadField1 = new FileUpload();
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
		
	}
	
	private void createUploadFondoFormPanel() {
		fondoUploadFormPanel = new FormPanel();
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		uploadField2 = new FileUpload();
		uploadButtonFondo = new Button();
		horizontalPanel.add(uploadField2);
		horizontalPanel.add(uploadButtonFondo);
		fondoUploadFormPanel.add(horizontalPanel);

		uploadButtonFondo.setText("...");
		
		uploadButtonFondo.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				fondoUploadFormPanel.submit();				
			}
		});
		fondoUploadFormPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		fondoUploadFormPanel.setMethod(FormPanel.METHOD_POST);
		uploadButtonFondo.setEnabled(false);
		uploadField2.setName("image");
 
		startNewBlobstoreSession();
 
		fondoUploadFormPanel.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				presenter.getImageUrl(event.getResults(),2);
			}
		});
		
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
		Window.alert("Ocurrio un error creando el restaurante.");		
	}
	
	@UiHandler("btnBorrarRestaurante")
	void onBtnBorrarRestauranteClick(ClickEvent event) {
		Set<RestauranteDto> selectedRows = ((MultiSelectionModel<RestauranteDto>)this.dataGrid.getSelectionModel()).getSelectedSet();
		RestauranteDto selectedRestaurante = getSeletedRestaurante();
		if(null!=selectedRestaurante){
				presenter.delete(selectedRestaurante);
		}
	}

	@UiHandler("btnVerCategoria")
	void onBtnVerCategoriaClick(ClickEvent event) {
		RestauranteDto selectedRestaurante = getSeletedRestaurante();
		System.out.println("Restaurante click: " + selectedRestaurante.getIdRestaurante());
		if(null!=selectedRestaurante){
			presenter.viewCateogoria(selectedRestaurante);
		}
		
	}
	
	private RestauranteDto getSeletedRestaurante(){

		/*if(null!=selectedRows&&selectedRows.size()>0){
			if(selectedRows.size()==1){
				List <RestauranteDto>selectedRestaurantes = new ArrayList<RestauranteDto>(selectedRows);
				result = selectedRestaurantes.get(0);
				
			}else{
				Window.alert("Selecciona una sola fila, solo puedes borrar un restaurante a la vez.");
			}
		}*/
		return ((SingleSelectionModel<RestauranteDto>)this.dataGrid.getSelectionModel()).getSelectedObject();
	}

	@Override
	public void onStartBlobStoreSessionSucefull(String actionURL) {
        bannerUploadFormPanel.setAction(actionURL);
        fondoUploadFormPanel.setAction(actionURL);
        uploadButtonFondo.setText("Upload");
        uploadButtonFondo.setEnabled(true);
        uploadButtonBanner.setText("Upload");
        uploadButtonBanner.setEnabled(true);
	}

	@Override
	public void onStartBlobStoreSessionFail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetImageUrlSucefull(String imageUrl, int widtgetId) {
		if(widtgetId==1){
			   this.bannerRestauranteTextBox.setText(imageUrl);	
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
		}else{
			   this.fondoRestauranteTextBox.setText(imageUrl);	
			   fondoUploadFormPanel.reset();
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
		
	       
	}

	@Override
	public void onGetImageUrlFail(String message) {
		
	}
}
