package co.com.nuevaera.client.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import co.com.nuevaera.client.dto.AnuncioDto;
import co.com.nuevaera.client.dto.CategoriaDto;
import co.com.nuevaera.client.dto.ElementoDto;
import co.com.nuevaera.client.dto.EmpresaDto;
import co.com.nuevaera.client.presenter.AnuncioDataProvider;
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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

public class AnuncioViewImpl extends Composite implements AnuncioView{
	
	private Presenter presenter;
	private AnuncioDataProvider anuncioDataProvider;

	private static AnuncioViewImplUiBinder uiBinder = GWT.create(AnuncioViewImplUiBinder.class);
	private SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	
	@UiField(provided = true)
	DataGrid<AnuncioDto> dataGrid = new DataGrid<AnuncioDto>(AnuncioDto.KEY_PROVIDER);
	@UiField(provided = true) 
	SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	@UiField 
	Button btnNuevaCategoria;
	@UiField Button btnBorrarCategoria;

	@UiField Label empresaLabel;
	
	private DialogBox nuevaCategoriaDialogBox;
	
	private long selectedIdRestaurante;
	
	private EmpresaDto empresaDto;

	
	private FormPanel bannerUploadFormPanel;
	private FormPanel fondoUploadFormPanel;
	private Button uploadButtonBanner;
	private Button uploadButtonFondo;
	private TextBox fotoBigTextBox;
	private TextBox fotoSmallTextBox;
	private FileUpload uploadField1;
	private FileUpload uploadField2;	
	

	interface AnuncioViewImplUiBinder extends UiBinder<Widget, AnuncioViewImpl> {
	}

	public AnuncioViewImpl(EmpresaDto empresaDto) {
		this.empresaDto = empresaDto;
		initWidget(uiBinder.createAndBindUi(this));
		
	    final MultiSelectionModel<AnuncioDto> selectionModel =
	            new MultiSelectionModel<AnuncioDto>(AnuncioDto.KEY_PROVIDER);
	        dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
	            .<AnuncioDto> createCheckboxManager());
	        
	        
		
		dataGrid.setEmptyTableWidget(new Label("Opps! :o Our DB is empty !!"));
		

		pager.setDisplay(dataGrid);
		
		initTableColumns(selectionModel);
		
		
		empresaLabel.setText(empresaDto.getNombre());

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
		
		dataGrid.setColumnWidth(duracionColumn, 20, Unit.PCT);

	

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
		dataGrid.setColumnWidth(fotoBigColumn, 20, Unit.PCT);
		
		Column<AnuncioDto, String> fotoSmallColumn = new Column<AnuncioDto, String>(
				new EditTextCell()) {
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
		dataGrid.setColumnWidth(fotoSmallColumn, 20, Unit.PCT);
		
		
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		nuevaCategoriaDialogBox = createDialogBox("Nuevo anuncio");
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
	public void setDataProvider(AnuncioDataProvider anuncioDataProvider) {
		this.anuncioDataProvider = anuncioDataProvider;
		anuncioDataProvider.addDataDisplay(dataGrid);	
		
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
		    
		    
		    
		    HorizontalPanel duracionPanel = new HorizontalPanel();
		    HorizontalPanel fotoBigPanel = new HorizontalPanel();
		    HorizontalPanel fotoBigUploadPanel = new HorizontalPanel();
		    HorizontalPanel fotoSmallPanel = new HorizontalPanel();
		    HorizontalPanel fotoSmallUploadPanel = new HorizontalPanel();

		    
		    		    
		    Label duracionLabel = new Label("Duración");
		    duracionLabel.setWidth("75px");
		    
		    Label fotoBigLabel = new Label("Foto grande");
		    fotoBigLabel.setWidth("75px");

		    Label fotoSmallLabel = new Label("Foto pequeña");
		    fotoSmallLabel.setWidth("75px");
		    
		    
		    final TextBox duracionTextBox = new TextBox();
		    duracionTextBox.setWidth("375px");
		    
		    fotoBigTextBox = new TextBox();
		    fotoBigTextBox.setWidth("375px");
		    
		    fotoSmallTextBox = new TextBox();
		    fotoSmallTextBox.setWidth("375px");
		    
		    
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
		    
		    duracionPanel.add(duracionLabel);
		    duracionPanel.add(duracionTextBox);
		    fotoBigPanel.add(fotoBigLabel);
		    fotoBigPanel.add(fotoBigTextBox);
		    fotoSmallPanel.add(fotoSmallLabel);
		    fotoSmallPanel.add(fotoSmallTextBox);
		    fotoBigUploadPanel.add(new Label(""));
		    fotoBigUploadPanel.add(bannerUploadFormPanel);
		    fotoSmallUploadPanel.add(new Label(""));
		    fotoSmallUploadPanel.add(fondoUploadFormPanel);
		    
		    
		    dialogContents.add(duracionPanel);
		    dialogContents.add(fotoBigPanel);
		    dialogContents.add(fotoBigUploadPanel);
		    dialogContents.add(fotoSmallPanel);
		    dialogContents.add(fotoSmallUploadPanel);
		    
		    
		    Button aceptButton = new Button(
		        "Aceptar", new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						AnuncioDto anuncioDto = new AnuncioDto();
						anuncioDto.setIdEmpresa(empresaDto.getIdEmpresa());
						anuncioDto.setDuracion(Integer.parseInt(duracionTextBox.getText()));
						anuncioDto.setFotoBig(fotoBigTextBox.getText());
						anuncioDto.setFotoSmall(fotoSmallTextBox.getText());

						presenter.save(anuncioDto);
						dialogBox.hide();

					    duracionTextBox.setText("");
					    fotoBigTextBox.setText("");
					    fotoSmallTextBox.setText("");
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
		Window.alert("Ocurrio un error creando el elemento..");		
	}
	
	@UiHandler("btnBorrarCategoria")
	void onBtnBorrarCategoriaClick(ClickEvent event) {
		Set<AnuncioDto> selectedRows = ((MultiSelectionModel<AnuncioDto>)this.dataGrid.getSelectionModel()).getSelectedSet();
		if(null!=selectedRows&&selectedRows.size()>0){
			if(selectedRows.size()==1){
				List <AnuncioDto>selectedAnuncios = new ArrayList<AnuncioDto>(selectedRows);
				AnuncioDto categoriaDto = selectedAnuncios.get(0);
				presenter.delete(categoriaDto);
			}else{
				Window.alert("Selecciona una sola fila, solo puedes borrar un anuncio a la vez.");
			}
		}
	}

	@Override
	public void onLoadEmpresas(List<EmpresaDto> empresasDtos) {

		
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
			   this.fotoBigTextBox.setText(imageUrl);	
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
			   this.fotoSmallTextBox.setText(imageUrl);	
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
		Window.alert("Ocurrio un error cargando la imagen: "+message);
		
	}
}
