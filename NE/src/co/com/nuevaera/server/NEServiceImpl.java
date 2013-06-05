package co.com.nuevaera.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import co.com.nuevaera.client.NEService;
import co.com.nuevaera.client.dto.AnuncioDto;
import co.com.nuevaera.client.dto.AnuncioEmisionDto;
import co.com.nuevaera.client.dto.CategoriaDto;
import co.com.nuevaera.client.dto.ElementoDto;
import co.com.nuevaera.client.dto.EmisionDto;
import co.com.nuevaera.client.dto.EmpresaDto;
import co.com.nuevaera.client.dto.RestauranteDto;
import co.com.nuevaera.client.ui.Suggestions;
import co.com.nuevaera.context.AppContextAware;
import co.com.nuevaera.model.NuevaEraPersistCtrl;
import co.com.nuevaera.shared.FieldVerifier;

import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class NEServiceImpl extends RemoteServiceServlet implements
		NEService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	@Override
	public List<RestauranteDto> getRestaurantes() {
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		return nuevaEraPersistCtrl.getRestaurantes();
	}

	@Override
	public boolean save(RestauranteDto restauranteDto) {
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		nuevaEraPersistCtrl.addRestaurante(restauranteDto);
		return true;
	}

	@Override
	public boolean delete(RestauranteDto restauranteDto) {
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		return nuevaEraPersistCtrl.deleteRestaurante(restauranteDto);
	}

	@Override
	public boolean update(RestauranteDto restauranteDto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EmpresaDto> getEmpresas() {
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		return nuevaEraPersistCtrl.getEmpresas();
	}

	@Override
	public boolean deleteEmpresa(EmpresaDto empresaDto) {
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		return nuevaEraPersistCtrl.deleteEmpresa(empresaDto);
	}

	@Override
	public boolean saveEmpresa(EmpresaDto empresaDto) {
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		nuevaEraPersistCtrl.addEmpresa(empresaDto);
		return true;
	}

	@Override
	public List<EmisionDto> getEmisiones() {
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		return nuevaEraPersistCtrl.getEmisioines();
	}

	@Override
	public boolean deleteEmision(EmisionDto emisionDto) {
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		return nuevaEraPersistCtrl.deleteEmisiones(emisionDto);
	}

	@Override
	public boolean saveEmision(EmisionDto emisionDto) {
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		nuevaEraPersistCtrl.addEmision(emisionDto);
		return true;
	}

	@Override
	public Response getSuggestions(Request req) {
		return null;
	}

	@Override
	public List<CategoriaDto> getCategorias(long idRestaurante) {
		System.out.println("Restaurante para la consulta de categoria: " + idRestaurante);
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		return nuevaEraPersistCtrl.getCategorias(idRestaurante, false);
	}

	@Override
	public boolean deleteCategoria(CategoriaDto categoriaDto) {
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		return nuevaEraPersistCtrl.deleteCategoria(categoriaDto);
	}

	@Override
	public boolean saveCategoria(CategoriaDto categoriaDto) {
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		nuevaEraPersistCtrl.addCategoria(categoriaDto);
		return true;
	}

	@Override
	public List<ElementoDto> getElementos(long idCategoria) {
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		return nuevaEraPersistCtrl.getElementos(idCategoria, false);
	}

	@Override
	public boolean deleteElemento(ElementoDto elementoDto) {
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		return nuevaEraPersistCtrl.deleteEelemento(elementoDto);
	}

	@Override
	public boolean saveElemento(ElementoDto elementoDto) {
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		nuevaEraPersistCtrl.addElemento(elementoDto);
		return true;
	}

	@Override
	public List<AnuncioDto> getAnuncios(long idEmpresa) {
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		return nuevaEraPersistCtrl.getAnuncios(idEmpresa, false);
	}

	@Override
	public boolean deleteAnuncio(AnuncioDto anuncioDto) {
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		return false;
	}

	@Override
	public boolean saveAnuncio(AnuncioDto anuncioDto) {
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		nuevaEraPersistCtrl.addAnuncio(anuncioDto);
		return true;
	}

	@Override
	public String getBlobstoreUploadUrl() {
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        return blobstoreService.createUploadUrl("/upload");
	}

	@Override
	public String getImageUrl(String encodedKey) {
		String url = null;
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Key key = KeyFactory.stringToKey(encodedKey);
			try {
				Entity result = datastore.get(key);
				url = (String)result.getProperty(UploadServlet.SERVING_URL);
			} catch (EntityNotFoundException e) {
				return null;
			}
		return url;
	}

	@Override
	public List<AnuncioDto> getAnunciosEmision(EmisionDto emision) {
		AppContextAware appContextAware = new AppContextAware();
		appContextAware.setIdRestaurante(emision.getIdRestaurante());
		appContextAware.setPropiedad(emision.getPropiedad());
		
		NuevaEraPersistCtrl persistCtrl = new NuevaEraPersistCtrl();
		return persistCtrl.getAnuncios(appContextAware);
	}

	@Override
	public List<AnuncioDto> getAllAnuncios() {
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		return nuevaEraPersistCtrl.getAnuncios(false);
	}

	@Override
	public boolean addAnuncioEmision(AnuncioEmisionDto anuncioEmisionDto) {
		boolean ret = false;
		NuevaEraPersistCtrl nuevaEraPersistCtrl = new NuevaEraPersistCtrl();
		if(null==nuevaEraPersistCtrl.getAnuncioEmision(anuncioEmisionDto.getIdAnuncio(), anuncioEmisionDto.getIdEmision())){
			nuevaEraPersistCtrl.addAnuncioEmision(anuncioEmisionDto);
			ret = true;
		}
		return ret;
	}
	
}
	

