package co.com.nuevaera.client;

import co.com.nuevaera.client.dto.AnuncioDto;
import co.com.nuevaera.client.dto.AnuncioEmisionDto;
import co.com.nuevaera.client.dto.CategoriaDto;
import co.com.nuevaera.client.dto.ElementoDto;
import co.com.nuevaera.client.dto.EmisionDto;
import co.com.nuevaera.client.dto.EmpresaDto;
import co.com.nuevaera.client.dto.RestauranteDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;

import java.util.List;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface NEServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	
	void getRestaurantes(AsyncCallback<List<RestauranteDto>> callback)
			throws IllegalArgumentException;

	void save(RestauranteDto restauranteDto, AsyncCallback<Boolean> callback);

	void delete(RestauranteDto restauranteDto, AsyncCallback<Boolean> callback);

	void update(RestauranteDto restauranteDto, AsyncCallback<Boolean> callback);
	
	void getEmpresas(AsyncCallback<List<EmpresaDto>> callback)
			throws IllegalArgumentException;

	void deleteEmpresa(EmpresaDto empresaDto, AsyncCallback<Boolean> callback);

	void saveEmpresa(EmpresaDto empresaDto, AsyncCallback<Boolean> callback);

	void getEmisiones(AsyncCallback<List<EmisionDto>> callback);

	void deleteEmision(EmisionDto emisionDto, AsyncCallback<Boolean> callback);

	void saveEmision(EmisionDto emisionDto, AsyncCallback<Boolean> callback);

	void getSuggestions(Request req, AsyncCallback<Response> callback);

	void getCategorias(long idRestaurante,
			AsyncCallback<List<CategoriaDto>> callback);

	void deleteCategoria(CategoriaDto categoriaDto,
			AsyncCallback<Boolean> callback);

	void saveCategoria(CategoriaDto categoriaDto,
			AsyncCallback<Boolean> callback);

	void getElementos(long idElemento, AsyncCallback<List<ElementoDto>> callback);

	void deleteElemento(ElementoDto elementoDto, AsyncCallback<Boolean> callback);

	void saveElemento(ElementoDto elementoDto, AsyncCallback<Boolean> callback);

	void getAnuncios(long idElemento, AsyncCallback<List<AnuncioDto>> callback);

	void deleteAnuncio(AnuncioDto anuncioDto, AsyncCallback<Boolean> callback);

	void saveAnuncio(AnuncioDto anuncioDto, AsyncCallback<Boolean> callback);

	void getBlobstoreUploadUrl(AsyncCallback<String> callback);

	void getImageUrl(String key, AsyncCallback<String> callback);

	void getAnunciosEmision(EmisionDto emision,
			AsyncCallback<List<AnuncioDto>> callback);

	void getAllAnuncios(AsyncCallback<List<AnuncioDto>> callback);

	void addAnuncioEmision(AnuncioEmisionDto anuncioEmisionDto,	AsyncCallback<Boolean> callback);	

}
