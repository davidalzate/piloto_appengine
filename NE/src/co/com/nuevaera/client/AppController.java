package co.com.nuevaera.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;

import co.com.nuevaera.client.dto.CategoriaDto;
import co.com.nuevaera.client.dto.EmisionDto;
import co.com.nuevaera.client.dto.EmpresaDto;
import co.com.nuevaera.client.dto.RestauranteDto;
import co.com.nuevaera.client.event.ViewAnuncioEmisionEvent;
import co.com.nuevaera.client.event.ViewAnuncioEmisionEventHandler;
import co.com.nuevaera.client.event.ViewAnuncioEvent;
import co.com.nuevaera.client.event.ViewAnuncioEventHandler;
import co.com.nuevaera.client.event.ViewCategoriaEvent;
import co.com.nuevaera.client.event.ViewCategoriaEventHandler;
import co.com.nuevaera.client.event.ViewElementoEvent;
import co.com.nuevaera.client.event.ViewElementoEventHandler;
import co.com.nuevaera.client.event.ViewEmisionEvent;
import co.com.nuevaera.client.event.ViewEmisionEventHandler;
import co.com.nuevaera.client.event.ViewEmpresasEvent;
import co.com.nuevaera.client.event.ViewEmpresasEventHandler;
import co.com.nuevaera.client.event.ViewRestaurantesEvent;
import co.com.nuevaera.client.event.ViewRestaurantesEventHandler;
import co.com.nuevaera.client.presenter.AnuncioEmisionPresenter;
import co.com.nuevaera.client.presenter.AnuncioPresenter;
import co.com.nuevaera.client.presenter.CategoriaPresenter;
import co.com.nuevaera.client.presenter.ElementoPresenter;
import co.com.nuevaera.client.presenter.EmisionPresenter;
import co.com.nuevaera.client.presenter.EmpresaPresenter;
import co.com.nuevaera.client.presenter.Presenter;
import co.com.nuevaera.client.presenter.RestaurantesPresenter;
import co.com.nuevaera.client.view.AnuncioEmisionViewImpl;
import co.com.nuevaera.client.view.AnuncioViewImpl;
import co.com.nuevaera.client.view.CategoriaViewImpl;
import co.com.nuevaera.client.view.ElementoViewImpl;
import co.com.nuevaera.client.view.EmisionViewImpl;
import co.com.nuevaera.client.view.EmpresaViewImpl;
import co.com.nuevaera.client.view.RestauranteViewImpl;

public class AppController implements Presenter, ValueChangeHandler<String>{
	
	private final HandlerManager eventBus;
	private final NEServiceAsync neServiceAsync;
	private HasWidgets container;
	private RestauranteViewImpl restauranteView;
	private EmpresaViewImpl empresaView;
	private EmisionViewImpl emisionView;
	private CategoriaViewImpl categoriaView;
	private ElementoViewImpl elementoView;
	private AnuncioViewImpl anuncioView;
	private AnuncioEmisionViewImpl anuncioEmisionView;
	
	
	public AppController(NEServiceAsync neService, HandlerManager eventBus) {
		this.eventBus = eventBus;
		this.neServiceAsync = neService;
		bind();
	}
	
	private void bind(){
		
		History.addValueChangeHandler(this);
		
		eventBus.addHandler(ViewRestaurantesEvent.TYPE, new ViewRestaurantesEventHandler() {
			@Override
			public void onViewRestaurantes(ViewRestaurantesEvent event) {
				doViewRestaurantes();
			}
		});
		
		eventBus.addHandler(ViewEmpresasEvent.TYPE, new ViewEmpresasEventHandler() {
			
			@Override
			public void onViewEmpresas(ViewEmpresasEvent event) {
				doViewEmpresas();
			}
		});
		
		eventBus.addHandler(ViewEmisionEvent.TYPE, new ViewEmisionEventHandler() {
			
			@Override
			public void onViewEmisiones(ViewEmisionEvent event) {
				doViewEmisiones();
			}
		});
		
		eventBus.addHandler(ViewCategoriaEvent.TYPE, new ViewCategoriaEventHandler() {
			
			@Override
			public void onViewCategoria(ViewCategoriaEvent event) {
				doViewCategorias(event.getRestaurante());
			}
		});
		
		eventBus.addHandler(ViewElementoEvent.TYPE, new ViewElementoEventHandler() {
			
			@Override
			public void onViewElemento(ViewElementoEvent event) {
				doViewElementos(event.getCategoriaDto());
			}
		});
		
		eventBus.addHandler(ViewAnuncioEvent.TYPE, new ViewAnuncioEventHandler() {
			
			@Override
			public void onViewAnuncio(ViewAnuncioEvent event) {
				doViewAnuncios(event.getEmpresaDto());
			}
		});	
		
		eventBus.addHandler(ViewAnuncioEmisionEvent.TYPE, new ViewAnuncioEmisionEventHandler() {
			
			@Override
			public void onViewAnuncioEmision(ViewAnuncioEmisionEvent event) {
				doViewAnunciosEmision(event.getEmisionDto());
			}
		});	
		
	}
	
	private void doViewRestaurantes(){
		History.newItem("restaurantes",false);
		GWT.runAsync(new RunAsyncCallback() {
			
			@Override
			public void onSuccess() {
				if(restauranteView == null){
					restauranteView = new RestauranteViewImpl();
				}
				new RestaurantesPresenter(neServiceAsync, eventBus, restauranteView).go(container);
			}
			
			@Override
			public void onFailure(Throwable reason) {
				Window.alert("No fue posible abrir la vista de restaurantes.");
			}
		});
	}
	
	
	private void doViewEmpresas(){
		History.newItem("empresas",false);
		GWT.runAsync(new RunAsyncCallback() {
			
			@Override
			public void onSuccess() {
				if(empresaView == null){
					empresaView = new EmpresaViewImpl();
				}
				new EmpresaPresenter(neServiceAsync, eventBus, empresaView).go(container);
			}
			
			@Override
			public void onFailure(Throwable reason) {
				Window.alert("No fue posible abrir la vista de empresas.");
			}
		});
	}

	private void doViewEmisiones(){
		History.newItem("emisiones",false);
		GWT.runAsync(new RunAsyncCallback() {
			
			@Override
			public void onSuccess() {
				if(emisionView == null){
					emisionView = new EmisionViewImpl();
				}
				new EmisionPresenter(neServiceAsync, eventBus, emisionView).go(container);
			}
			
			@Override
			public void onFailure(Throwable reason) {
				Window.alert("No fue posible abrir la vista de emisiones.");
			}
		});
	}	
	
	private void doViewCategorias(final RestauranteDto restaurante){
		History.newItem("categorias",false);
		//System.out.println("xxxx");
		GWT.runAsync(new RunAsyncCallback() {

			@Override
			public void onSuccess() {
				System.out.println("yyyy");
				if(categoriaView == null){
					categoriaView = new CategoriaViewImpl(restaurante);
				}else{
					categoriaView.setRestaurante(restaurante);
				}
				new CategoriaPresenter(neServiceAsync, eventBus, categoriaView, restaurante).go(container);
			}
			
			@Override
			public void onFailure(Throwable reason) {
				Window.alert("No fue posible abrir la vista de categorias.");
			}
		});
	}
	
	private void doViewElementos(final CategoriaDto categoria){
		if (null!=categoria) {
			History.newItem("elementos",false);
			GWT.runAsync(new RunAsyncCallback() {

				@Override
				public void onSuccess() {
					if(elementoView == null){
						elementoView = new ElementoViewImpl(categoria);
					}else{
						elementoView.setCategoriaDto(categoria);
					}
					new ElementoPresenter(neServiceAsync, eventBus, elementoView, categoria).go(container);
				}
				
				@Override
				public void onFailure(Throwable reason) {
					Window.alert("No fue posible abrir la vista de elementos.");
				}
			});			
		}
	}
	
	
	private void doViewAnuncios(final EmpresaDto empresaDto){
		if (null!=empresaDto) {
			History.newItem("anuncios",false);
			GWT.runAsync(new RunAsyncCallback() {

				@Override
				public void onSuccess() {
					if(anuncioView == null){
						anuncioView = new AnuncioViewImpl(empresaDto);
					}
					new AnuncioPresenter(neServiceAsync, eventBus, anuncioView, empresaDto).go(container);
				}
				
				@Override
				public void onFailure(Throwable reason) {
					Window.alert("No fue posible abrir la vista de elementos.");
				}
			});		
		}
	}
	
	private void doViewAnunciosEmision(final EmisionDto emisionDto){
		if (null!=emisionDto) {
			History.newItem("anunciosEmision",false);
			GWT.runAsync(new RunAsyncCallback() {

				@Override
				public void onSuccess() {
					if(anuncioView == null){
						anuncioEmisionView = new AnuncioEmisionViewImpl(emisionDto);
					}
					new AnuncioEmisionPresenter(neServiceAsync, eventBus, anuncioEmisionView, emisionDto).go(container);
				}
				
				@Override
				public void onFailure(Throwable reason) {
					Window.alert("No fue posible abrir la vista de elementos.");
				}
			});			
		}
	}
	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		System.out.println("ValueChangeEvent");
		String token = event.getValue();
		if(null!=token){
			if(token.equalsIgnoreCase("restaurantes")){
				GWT.runAsync(new RunAsyncCallback() {
					
					@Override
					public void onSuccess() {
						if (null==restauranteView){
							restauranteView = new  RestauranteViewImpl();
						}
						new RestaurantesPresenter(neServiceAsync, eventBus, restauranteView).go(container);
					}
					
					@Override
					public void onFailure(Throwable reason) {
						Window.alert("No se pudo instanciar la vista de restaurantes");
					}
				});
			}else if(token.equalsIgnoreCase("empresas")){
				GWT.runAsync(new RunAsyncCallback() {
					
					@Override
					public void onSuccess() {
						if (null==empresaView){
							empresaView = new  EmpresaViewImpl();
						}
						new EmpresaPresenter(neServiceAsync, eventBus, empresaView).go(container);
					}
					
					@Override
					public void onFailure(Throwable reason) {
						Window.alert("No se pudo instanciar la vista de empresas");
					}
				});
			}else if(token.equalsIgnoreCase("emisiones")){
				GWT.runAsync(new RunAsyncCallback() {
					
					@Override
					public void onSuccess() {
						if(emisionView == null){
							emisionView = new EmisionViewImpl();
						}
						new EmisionPresenter(neServiceAsync, eventBus, emisionView).go(container);
					}
					
					@Override
					public void onFailure(Throwable reason) {
						Window.alert("No fue posible abrir la vista de emisiones.");
					}
				});
			}else if(token.equalsIgnoreCase("categorias")){
				System.out.println("categorias");
				//doViewCategorias(null);
			}else if(token.equalsIgnoreCase("elementos")){
				doViewElementos(null);
			}else if(token.equalsIgnoreCase("anuncios")){
				doViewAnuncios(null);
			}else if(token.equalsIgnoreCase("anunciosEmision")){
				doViewAnunciosEmision(null);
			}
		}
	}

	@Override
	public void go(HasWidgets container) {
		this.container = container;
		if("".equalsIgnoreCase(History.getToken())){
			History.newItem("restaurantes");
		}else{
			History.fireCurrentHistoryState();
		}
	}
	

}
