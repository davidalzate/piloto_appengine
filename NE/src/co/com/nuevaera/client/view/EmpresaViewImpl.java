package co.com.nuevaera.client.view;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import co.com.nuevaera.client.dto.EmpresaDto;
import co.com.nuevaera.client.presenter.EmpresaDataProvider;

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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

public class EmpresaViewImpl extends Composite implements EmpresaView{
	
	private Presenter presenter;
	private EmpresaDataProvider empresaDataProvider;

	private static EmpresaViewImplUiBinder uiBinder = GWT.create(EmpresaViewImplUiBinder.class);
	private SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	
	@UiField(provided = true)
	DataGrid<EmpresaDto> dataGrid = new DataGrid<EmpresaDto>(EmpresaDto.KEY_PROVIDER);
	@UiField(provided = true) 
	SimplePager pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	@UiField 
	Button btnNuevaEmpresa;
	@UiField Button btnBorrarEmpresa;
	@UiField Button btnVerAnuncios;
	
	private DialogBox nuevoEmpresaDialogBox;

	interface EmpresaViewImplUiBinder extends UiBinder<Widget, EmpresaViewImpl> {
	}

	public EmpresaViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		
	    final MultiSelectionModel<EmpresaDto> selectionModel =
	            new MultiSelectionModel<EmpresaDto>(EmpresaDto.KEY_PROVIDER);
	        dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
	            .<EmpresaDto> createCheckboxManager());
	        
	        
		
		dataGrid.setEmptyTableWidget(new Label("Opps! :o Our DB empty !!"));
		

		pager.setDisplay(dataGrid);
		
		initTableColumns(selectionModel);
		
		nuevoEmpresaDialogBox = createDialogBox("Nuevo empresa");
		
	}

	public EmpresaViewImpl(String firstName) {
		
		initWidget(uiBinder.createAndBindUi(this));
		
		final SelectionModel<EmpresaDto> selectionModel = new SingleSelectionModel<EmpresaDto>(
				EmpresaDto.KEY_PROVIDER);
		dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
				.<EmpresaDto> createDefaultManager());
		
		dataGrid.setEmptyTableWidget(new Label("Opps! :o Our DB empty !!"));
		
		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0,
				true);
		pager.setDisplay(dataGrid);
		
		initTableColumns(selectionModel);
		
	}

	private void initTableColumns(
			final SelectionModel<EmpresaDto> selectionModel) {

		Column<EmpresaDto, Boolean> checkColumn = new Column<EmpresaDto, Boolean>(
				new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(EmpresaDto object) {
				// Get the value from the selection model.
				return selectionModel.isSelected(object);
			}
		};

		dataGrid.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
		dataGrid.setColumnWidth(checkColumn, 40, Unit.PX);

		Column<EmpresaDto, String> nameColumn = new Column<EmpresaDto, String>(
				new EditTextCell()) {
			@Override
			public String getValue(EmpresaDto object) {
				return object.getNombre();
			}
		};

		dataGrid.addColumn(nameColumn, "Nombre");
		nameColumn
				.setFieldUpdater(new FieldUpdater<EmpresaDto, String>() {

					@Override
					public void update(int index, EmpresaDto empesa,
							String nombre) {
						empesa.setNombre(nombre);
					}

				});
		dataGrid.setColumnWidth(nameColumn, 20, Unit.PCT);	

	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setModelData(List<EmpresaDto> empresasDataValues) {
		if(null!=empresasDataValues){
			dataGrid.setRowData(empresasDataValues);
			dataGrid.setPageSize(50);
			//dataGrid.setRowCount(900);
			dataGrid.redraw();
			//pager.setPageSize(50);
		}else{
			Window.alert("No se encontraron datos de empresas.");
		}
		
	}

	@Override
	public void setDataProvider(EmpresaDataProvider empresasDataProvider) {
		this.empresaDataProvider = empresasDataProvider;
		empresasDataProvider.addDataDisplay(dataGrid);	
		
	}
	@UiHandler("btnNuevaEmpresa")
	void onBtnNuevaEmpresaClick(ClickEvent event) {
		nuevoEmpresaDialogBox.center();
		nuevoEmpresaDialogBox.show();
	}
	
	private DialogBox createDialogBox(String title) {
		
	
		    final DialogBox dialogBox = new DialogBox();
		    dialogBox.ensureDebugId("cwDialogBox");
		    dialogBox.setText(title);

		    VerticalPanel dialogContents = new VerticalPanel();
		    dialogContents.setSpacing(4);
		    dialogBox.setWidget(dialogContents);
		    
		    HorizontalPanel nombreEmpresaPanel = new HorizontalPanel();
		    
		    
		    Label nombreEmpresaLabel = new Label("Nombre");
		    nombreEmpresaLabel.setWidth("75px");
		    
		    final TextBox nombreEmpresaTextBox = new TextBox();
		    nombreEmpresaTextBox.setWidth("375px");

		    nombreEmpresaPanel.add(nombreEmpresaLabel);
		    nombreEmpresaPanel.add(nombreEmpresaTextBox);
		    
		    dialogContents.add(nombreEmpresaPanel);
		    
		    Button aceptButton = new Button(
		        "Aceptar", new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						EmpresaDto empresaDto = new EmpresaDto();
						empresaDto.setNombre(nombreEmpresaTextBox.getText());
						presenter.save(empresaDto);
						dialogBox.hide();
						nombreEmpresaTextBox.setText("");
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
		Window.alert("Ocurrio un error creando la empresa.");		
	}
	
	@UiHandler("btnBorrarEmpresa")
	void onBtnBorrarEmpresaClick(ClickEvent event) {
		EmpresaDto result = getSeletectEmpresa();
		if(null!=result){
			presenter.delete(result);			
		}
	}

	@UiHandler("btnVerAnuncios")
	void onBtnVerAnunciosClick(ClickEvent event) {
		EmpresaDto result = getSeletectEmpresa();
		if(null!=result){		
			presenter.viewAnuncios(result);
		}
	}
	
	private EmpresaDto getSeletectEmpresa(){
		EmpresaDto result = null;
		Set<EmpresaDto> selectedRows = ((MultiSelectionModel<EmpresaDto>)this.dataGrid.getSelectionModel()).getSelectedSet();
		if(null!=selectedRows&&selectedRows.size()>0){
			if(selectedRows.size()==1){
				List <EmpresaDto>selectedEmpresas = new ArrayList<EmpresaDto>(selectedRows);
				result = selectedEmpresas.get(0);
			}else{
				Window.alert("Selecciona una sola fila, solo puedes borrar una empresa a la vez.");
			}
		}		
		return result;
	}
}

