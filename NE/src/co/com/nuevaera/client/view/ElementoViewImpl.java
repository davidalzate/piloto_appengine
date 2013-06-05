package co.com.nuevaera.client.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import co.com.nuevaera.client.dto.CategoriaDto;
import co.com.nuevaera.client.dto.ElementoDto;
import co.com.nuevaera.client.presenter.ElementoDataProvider;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
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
import com.google.gwt.user.client.ui.CheckBox;
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

public class ElementoViewImpl extends Composite implements ElementoView{
	
	private Presenter presenter;
	private ElementoDataProvider categoriaDataProvider;

	private static ElementoViewImplUiBinder uiBinder = GWT.create(ElementoViewImplUiBinder.class);
	private SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	
	@UiField(provided = true)
	DataGrid<ElementoDto> dataGrid = new DataGrid<ElementoDto>(ElementoDto.KEY_PROVIDER);
	@UiField(provided = true) 
	SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	@UiField 
	Button btnNuevaCategoria;
	@UiField Button btnBorrarCategoria;
	@UiField Label categoriaLabel;
	
	private DialogBox nuevaCategoriaDialogBox;
	
	private long selectedIdRestaurante;

	private FormPanel fotoBigUploadFormPanel;
	private FormPanel fotoSmallUploadFormPanel;
	private Button uploadButtonBanner;
	private Button uploadButtonFondo;
	private FileUpload uploadField1;
	private FileUpload uploadField2;	
	
	private CategoriaDto categoriaDto;
	private TextBox fotoBigTextBox;
	private TextBox fotoSmallTextBox;


	interface ElementoViewImplUiBinder extends UiBinder<Widget, ElementoViewImpl> {
	}

	public ElementoViewImpl(CategoriaDto categoriaDto) {
		this.categoriaDto = categoriaDto;
		initWidget(uiBinder.createAndBindUi(this));
		
	    final MultiSelectionModel<ElementoDto> selectionModel =
	            new MultiSelectionModel<ElementoDto>(ElementoDto.KEY_PROVIDER);
	        dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
	            .<ElementoDto> createCheckboxManager());
	        
	        
		
		dataGrid.setEmptyTableWidget(new Label("Opps! :o Our DB empty !!"));
		

		pager.setDisplay(dataGrid);
		
		initTableColumns(selectionModel);
		
		
		categoriaLabel.setText(categoriaDto.getNombre());
	}

	public ElementoViewImpl(String firstName) {
		
		initWidget(uiBinder.createAndBindUi(this));
		
		final SelectionModel<ElementoDto> selectionModel = new SingleSelectionModel<ElementoDto>(
				ElementoDto.KEY_PROVIDER);
		dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
				.<ElementoDto> createDefaultManager());
		
		dataGrid.setEmptyTableWidget(new Label("Opps! :o Our DB empty !!"));
		
		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0,
				true);
		pager.setDisplay(dataGrid);
		
		initTableColumns(selectionModel);
		
	}

	private void initTableColumns(
			final SelectionModel<ElementoDto> selectionModel) {

		Column<ElementoDto, Boolean> checkColumn = new Column<ElementoDto, Boolean>(
				new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(ElementoDto object) {
				return selectionModel.isSelected(object);
			}
		};

		dataGrid.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		dataGrid.setColumnWidth(checkColumn, 40, Unit.PX);

		Column<ElementoDto, String> nameColumn = new Column<ElementoDto, String>(
				new EditTextCell()) {
			@Override
			public String getValue(ElementoDto object) {
				return object.getNombre();
			}
		};

		dataGrid.addColumn(nameColumn, "Nombre");
		nameColumn
				.setFieldUpdater(new FieldUpdater<ElementoDto, String>() {

					@Override
					public void update(int index, ElementoDto categoria,
							String nombre) {
						categoria.setNombre(nombre);
					}

				});
		
		dataGrid.setColumnWidth(nameColumn, 20, Unit.PCT);

		Column<ElementoDto, String> precioColumn = new Column<ElementoDto, String>(
				new EditTextCell()) {
			@Override
			public String getValue(ElementoDto object) {
				return object.getPrecio();
			}
		};
		
		dataGrid.addColumn(precioColumn, "Precio");
		nameColumn
				.setFieldUpdater(new FieldUpdater<ElementoDto, String>() {

					@Override
					public void update(int index, ElementoDto categoria,
							String precio) {
						categoria.setPrecio(precio);
					}

				});
		
		dataGrid.setColumnWidth(precioColumn, 20, Unit.PCT);

		Column<ElementoDto, String> fotoBigColumn = new Column<ElementoDto, String>(
				new EditTextCell()) {
			@Override
			public String getValue(ElementoDto object) {
				return object.getFotoBig();
			}
		};
		
		dataGrid.addColumn(fotoBigColumn, "Foto grande");
		fotoBigColumn.setFieldUpdater(new FieldUpdater<ElementoDto, String>() {
			@Override
			public void update(int index, ElementoDto object, String fotoBig) {
				object.setFotoBig(fotoBig);
			}
		});
		dataGrid.setColumnWidth(fotoBigColumn, 20, Unit.PCT);
		
		Column<ElementoDto, String> fotoSmallColumn = new Column<ElementoDto, String>(
				new EditTextCell()) {
			@Override
			public String getValue(ElementoDto object) {
				return object.getFotoSmall();
			}
		};
		
		dataGrid.addColumn(fotoSmallColumn, "Foto pequeña");
		fotoSmallColumn.setFieldUpdater(new FieldUpdater<ElementoDto, String>() {
			@Override
			public void update(int index, ElementoDto object, String fotoSmall) {
				object.setFotoSmall(fotoSmall);
			}
		});
		dataGrid.setColumnWidth(fotoSmallColumn, 20, Unit.PCT);
		
		Column<ElementoDto, String> descripcionLargaColumn = new Column<ElementoDto, String>(
				new EditTextCell()) {
			@Override
			public String getValue(ElementoDto object) {
				return object.getDescripcionLarga();
			}
		};
		
		dataGrid.addColumn(descripcionLargaColumn, "Descripción larga");
		descripcionLargaColumn.setFieldUpdater(new FieldUpdater<ElementoDto, String>() {
			@Override
			public void update(int index, ElementoDto object, String descripcionLarga) {
				object.setDescripcionLarga(descripcionLarga);
			}
		});
		dataGrid.setColumnWidth(descripcionLargaColumn, 20, Unit.PCT);	
		
		Column<ElementoDto, String> descripcionCortaColumn = new Column<ElementoDto, String>(
				new EditTextCell()) {
			@Override
			public String getValue(ElementoDto object) {
				return object.getDescripcionCorta();
			}
		};
		
		dataGrid.addColumn(descripcionCortaColumn, "Descripción corta");
		descripcionCortaColumn.setFieldUpdater(new FieldUpdater<ElementoDto, String>() {
			@Override
			public void update(int index, ElementoDto object, String descripcionCorta) {
				object.setDescripcionCorta(descripcionCorta);
			}
		});
		dataGrid.setColumnWidth(descripcionCortaColumn, 20, Unit.PCT);	
		
		Column<ElementoDto, Boolean> disponibleColumn = new Column<ElementoDto, Boolean>(
				new CheckboxCell()) {
			@Override
			public Boolean getValue(ElementoDto object) {
				return Boolean.valueOf(object.isDisponible());
			}
		};		
		
		dataGrid.addColumn(disponibleColumn, "Disponible");
		disponibleColumn.setFieldUpdater(new FieldUpdater<ElementoDto, Boolean>() {
			@Override
			public void update(int index, ElementoDto object, Boolean disponible) {
				object.setDisponible(disponible);
			}
		});
		dataGrid.setColumnWidth(descripcionCortaColumn, 20, Unit.PCT);			
		
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		nuevaCategoriaDialogBox = createDialogBox("Nuevo elemento");
	}

	@Override
	public void setModelData(List<ElementoDto> categoriaDataValues) {
		if(null!=categoriaDataValues){
			dataGrid.setRowData(categoriaDataValues);
			dataGrid.setPageSize(50);
			//dataGrid.setRowCount(900);
			dataGrid.redraw();
			//pager.setPageSize(50);
			presenter.loadCategorias();
		}else{
			Window.alert("No se encontraron datos de categorias.");
		}
		
	}

	@Override
	public void setDataProvider(ElementoDataProvider categoriaDataProvider) {
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
		    
		    
		    HorizontalPanel nombreElementoPanel = new HorizontalPanel();
		    HorizontalPanel nombreCategoriaPanel = new HorizontalPanel();
		    HorizontalPanel precioPanel = new HorizontalPanel();
		    HorizontalPanel fotoBigPanel = new HorizontalPanel();
		    HorizontalPanel fotoBigUploadPanel = new HorizontalPanel();
		    HorizontalPanel fotoSmallPanel = new HorizontalPanel();
		    HorizontalPanel fotoSmallUploadPanel = new HorizontalPanel();

		    HorizontalPanel disponiblePanel = new HorizontalPanel();
		    HorizontalPanel descripcionLargaPanel = new HorizontalPanel();
		    HorizontalPanel descripcionCortaPanel = new HorizontalPanel();
		    
		    
		    		    
		    Label nombreElementoLabel = new Label("Nombre");
		    nombreElementoLabel.setWidth("75px");
		    
		    /*Label nombreCategoriaLabel = new Label("Categoria");
		    nombreCategoriaLabel.setWidth("75px");*/

		    Label precioLabel = new Label("Precio");
		    precioLabel.setWidth("75px");
		    
		    Label fotoBigLabel = new Label("Foto grande");
		    fotoBigLabel.setWidth("75px");

		    Label fotoSmallLabel = new Label("Foto pequeña");
		    fotoSmallLabel.setWidth("75px");
		    
		    Label disponibleLabel = new Label("Disponible");
		    disponibleLabel.setWidth("75px");

		    Label descripcionLargaLabel = new Label("Descrip. larga");
		    descripcionLargaLabel.setWidth("75px");
		    
		    Label descripcionCortaLabel = new Label("Descrip. corta");
		    descripcionCortaLabel.setWidth("75px");
		    
		    final TextBox nombreElementoTextBox = new TextBox();
		    nombreElementoTextBox.setWidth("375px");
		    
		    /*final TextBox nombreCategoriaTextBox = new TextBox();
		    nombreCategoriaTextBox.setText(categoriaDto.getNombre());
		    nombreCategoriaTextBox.setWidth("375px");*/
		 
		    final TextBox precioTextBox = new TextBox();
		    precioTextBox.setWidth("375px");
		    
		    fotoBigTextBox = new TextBox();
		    fotoBigTextBox.setWidth("375px");
		    
		    fotoSmallTextBox = new TextBox();
		    fotoSmallTextBox.setWidth("375px");
		    
		    final CheckBox disponibleChecktBox = new CheckBox();
		    disponibleChecktBox.setWidth("375px");

		    final TextBox descripcionLargaTextBox = new TextBox();
		    descripcionLargaTextBox.setWidth("375px");	
		    
		    final TextBox descripcionCortaTextBox = new TextBox();
		    descripcionLargaTextBox.setWidth("375px");
		    
		   /* SuggestionOracle  nombreRestauranteTextBox = new SuggestionOracle();		    
		    final TextBox nombreTextBox = new TextBox();
		    nombreTextBox.setWidth("375px");
		    final TextBox fotoTextBox = new TextBox();
		    fotoTextBox.setWidth("375px");

		    
		    final SuggestBox suggestBox = new SuggestBox(nombreRestauranteTextBox);
		    suggestBox.addSelectionHandler(new SelectionHandler<Suggestion>() {
				
				@Override
				public void onSelection(SelectionEvent<Suggestion> event) {
					selectedIdRestaurante = ((Suggestions)event.getSelectedItem()).getId();
				}
			});*/
		    
		    createUploadBannerFormPanel();
		    createUploadFondoFormPanel();
		    
		    
		    nombreElementoPanel.add(nombreElementoLabel);
		    nombreElementoPanel.add(nombreElementoTextBox);
		    //nombreCategoriaPanel.add(nombreCategoriaLabel);
		    //nombreCategoriaPanel.add(nombreCategoriaTextBox);
		    precioPanel.add(precioLabel);
		    precioPanel.add(precioTextBox);
		    fotoBigPanel.add(fotoBigLabel);
		    fotoBigPanel.add(fotoBigTextBox);
		    fotoBigUploadPanel.add(new Label(""));
		    fotoBigUploadPanel.add(fotoBigUploadFormPanel);
		    fotoSmallPanel.add(fotoSmallLabel);
		    fotoSmallPanel.add(fotoSmallTextBox);
		    fotoSmallUploadPanel.add(new Label(""));
		    fotoSmallUploadPanel.add(fotoSmallUploadFormPanel);
		    disponiblePanel.add(disponibleLabel);
		    disponiblePanel.add(disponibleChecktBox);
		    descripcionLargaPanel.add(descripcionLargaLabel);
		    descripcionLargaPanel.add(descripcionLargaTextBox);
		    descripcionCortaPanel.add(descripcionCortaLabel);
		    descripcionCortaPanel.add(descripcionCortaTextBox);
		    
		    dialogContents.add(nombreElementoPanel);
		    //dialogContents.add(nombreCategoriaPanel);
		    dialogContents.add(precioPanel);
		    dialogContents.add(fotoBigPanel);
		    dialogContents.add(fotoBigUploadPanel);
		    dialogContents.add(fotoSmallPanel);
		    dialogContents.add(fotoSmallUploadPanel);
		    dialogContents.add(disponiblePanel);
		    dialogContents.add(descripcionLargaPanel);
		    dialogContents.add(descripcionCortaPanel);
		    
		    
		    
		    Button aceptButton = new Button(
		        "Aceptar", new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						ElementoDto elementoDto = new ElementoDto();
						elementoDto.setNombre(nombreElementoTextBox.getText());
						elementoDto.setIdCategoria(categoriaDto.getIdCategoria());
						elementoDto.setPrecio(precioTextBox.getText());
						elementoDto.setFotoBig(fotoBigTextBox.getText());
						elementoDto.setFotoSmall(fotoSmallTextBox.getText());
						elementoDto.setDisponible(disponibleChecktBox.isChecked());
						elementoDto.setDescripcionLarga(descripcionLargaTextBox.getText());
						elementoDto.setDescripcionCorta(descripcionCortaTextBox.getText());

						presenter.save(elementoDto);
						dialogBox.hide();

					    nombreElementoTextBox.setText("");
					    //nombreCategoriaTextBox.setText("");
					    precioTextBox.setText("");
					    fotoBigTextBox.setText("");
					    fotoSmallTextBox.setText("");
					    disponibleChecktBox.setText("");
					    descripcionLargaTextBox.setText("");
					    descripcionCortaTextBox.setText("");
						nombreElementoTextBox.setText("");
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
		fotoBigUploadFormPanel = new FormPanel();
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		uploadField1 = new FileUpload();
		uploadButtonBanner = new Button();
		horizontalPanel.add(uploadField1);
		horizontalPanel.add(uploadButtonBanner);
		fotoBigUploadFormPanel.add(horizontalPanel);

		uploadButtonBanner.setText("...");
		
		uploadButtonBanner.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				fotoBigUploadFormPanel.submit();				
			}
		});
		fotoBigUploadFormPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		fotoBigUploadFormPanel.setMethod(FormPanel.METHOD_POST);
		uploadButtonBanner.setEnabled(false);
		uploadField1.setName("image");
 
		startNewBlobstoreSession();
 
		fotoBigUploadFormPanel.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				presenter.getImageUrl(event.getResults(),1);
			}
		});
		
	}
	
	private void createUploadFondoFormPanel() {
		fotoSmallUploadFormPanel = new FormPanel();
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		uploadField2 = new FileUpload();
		uploadButtonFondo = new Button();
		horizontalPanel.add(uploadField2);
		horizontalPanel.add(uploadButtonFondo);
		fotoSmallUploadFormPanel.add(horizontalPanel);

		uploadButtonFondo.setText("...");
		
		uploadButtonFondo.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				fotoSmallUploadFormPanel.submit();				
			}
		});
		fotoSmallUploadFormPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		fotoSmallUploadFormPanel.setMethod(FormPanel.METHOD_POST);
		uploadButtonFondo.setEnabled(false);
		uploadField2.setName("image");
 
		startNewBlobstoreSession();
 
		fotoSmallUploadFormPanel.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			
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
		Window.alert("Ocurrio un error creando el elemento..");		
	}
	
	@UiHandler("btnBorrarCategoria")
	void onBtnBorrarCategoriaClick(ClickEvent event) {
		Set<ElementoDto> selectedRows = ((MultiSelectionModel<ElementoDto>)this.dataGrid.getSelectionModel()).getSelectedSet();
		if(null!=selectedRows&&selectedRows.size()>0){
			if(selectedRows.size()==1){
				List <ElementoDto>selectedCategorias = new ArrayList<ElementoDto>(selectedRows);
				ElementoDto categoriaDto = selectedCategorias.get(0);
				presenter.delete(categoriaDto);
			}else{
				Window.alert("Selecciona una sola fila, solo puedes borrar un elemento a la vez.");
			}
		}
	}

	@Override
	public void onLoadCategorias(List<ElementoDto> restauranteDtos) {

		
	}


	@Override
	public void onStartBlobStoreSessionSucefull(String actionURL) {
        fotoBigUploadFormPanel.setAction(actionURL);
        fotoSmallUploadFormPanel.setAction(actionURL);
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
			   this.fotoBigTextBox.setText(imageUrl);	
			   fotoBigUploadFormPanel.reset();
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
			   this.fotoSmallTextBox.setText(imageUrl);	
			   fotoSmallUploadFormPanel.reset();
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
		Window.alert("Ocurrio un error cargando la imagen: "+message);
		
	}

	public CategoriaDto getCategoriaDto() {
		return categoriaDto;
	}

	public void setCategoriaDto(CategoriaDto categoriaDto) {
		this.categoriaDto = categoriaDto;
	}
}
