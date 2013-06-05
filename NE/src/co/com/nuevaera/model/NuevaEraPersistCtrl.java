package co.com.nuevaera.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import co.com.nuevaera.client.dto.AnuncioDto;
import co.com.nuevaera.client.dto.AnuncioEmisionDto;
import co.com.nuevaera.client.dto.CategoriaDto;
import co.com.nuevaera.client.dto.ElementoDto;
import co.com.nuevaera.client.dto.EmisionDto;
import co.com.nuevaera.client.dto.EmpresaDto;
import co.com.nuevaera.client.dto.RestauranteDto;
import co.com.nuevaera.client.dto.UsuarioDto;
import co.com.nuevaera.client.dto.ViewDto;
import co.com.nuevaera.context.AppContextAware;
import co.com.nuevaera.model.entity.Anuncio;
import co.com.nuevaera.model.entity.AnuncioEmision;
import co.com.nuevaera.model.entity.Categoria;
import co.com.nuevaera.model.entity.Elemento;
import co.com.nuevaera.model.entity.Emision;
import co.com.nuevaera.model.entity.Empresa;
import co.com.nuevaera.model.entity.Restaurante;
import co.com.nuevaera.model.entity.Usuario;
import co.com.nuevaera.model.entity.View;

public class NuevaEraPersistCtrl {

	
	
	public ArrayList <EmisionDto> getEmisioines(){
		ArrayList <EmisionDto> emisionesDto = new ArrayList<EmisionDto>();
		
		EmisionDto emisionDto = null;
		EntityManager em = EMF.get().createEntityManager();

		try{
			Query  query = em.createQuery("select from " + Emision.class.getName() + " r ");
			List<Emision> listaEmisiones = query.getResultList();
			for (Iterator <Emision>iterator = listaEmisiones.iterator(); iterator
					.hasNext();) {
				Emision emision =  iterator.next();
				 emisionDto = new EmisionDto();
				 emisionDto.setIdEmision(emision.getIdEmision().getId());
				 emisionDto.setIdRestaurante(emision.getIdRestaurante());
				 emisionDto.setPropiedad(emision.getPropiedad());
				 emisionesDto.add(emisionDto);
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return emisionesDto;
	}	
	
	
	public long addEmpresa(EmpresaDto empresaDto){
		EntityManager em = EMF.get().createEntityManager();
		Empresa empresa = new Empresa();
		try{
			empresa.setNombre(empresaDto.getNombre());
			em.persist(empresa);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return empresa.getIdEmpresa().getId();
	}
	
	
	
	public void addView(ViewDto viewDto){
		EntityManager em = EMF.get().createEntityManager();
		View view = new View();
		try{
			view.setClick(viewDto.isClick());
			view.setIdAnuncio(viewDto.getIdAnuncio());
			view.setIdRestaurante(viewDto.getIdRestaurante());
			view.setDuracionVisita(viewDto.getDuracionVisita());
			view.setFecha(viewDto.getFecha());			
			em.persist(view);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
	}
	
	
	public ArrayList <EmpresaDto> getEmpresas(){
		ArrayList <EmpresaDto> empresasDtos = new ArrayList<EmpresaDto>();
		
		EmpresaDto empresaDto = null;
		EntityManager em = EMF.get().createEntityManager();

		try{
			Query  query = em.createQuery("select from " + Empresa.class.getName() + " r ");
			List<Empresa> listEmpresas = query.getResultList();
			for (Iterator <Empresa>iterator = listEmpresas.iterator(); iterator
					.hasNext();) {
				Empresa empresa =  iterator.next();
				 empresaDto = new EmpresaDto();
				 empresaDto.setIdEmpresa(empresa.getIdEmpresa().getId());
				 empresaDto.setNombre(empresa.getNombre());
				 empresasDtos.add(empresaDto);
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return empresasDtos;
	}	
	
	public boolean deleteEmpresa(EmpresaDto empresaDto){
		boolean result = false;
		EntityManager em = EMF.get().createEntityManager();
		try{
			Query  query = em.createQuery("select from " + Empresa.class.getName() + " r where r.idEmpresa = :id");
			query.setParameter("id", empresaDto.getIdEmpresa());
			query.setMaxResults(1);
			Empresa empresa = (Empresa)query.getSingleResult();
			 if(null!=empresa){
				 em.remove(empresa);
				 result = true;
			 }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return result;
	}
	
	
	public boolean deleteCategoria(CategoriaDto categoriaDto){
		boolean result = false;
		EntityManager em = EMF.get().createEntityManager();
		try{
			Query  query = em.createQuery("select from " + Categoria.class.getName() + " r where r.idCategoria = :id");
			query.setParameter("id", categoriaDto.getIdCategoria());
			query.setMaxResults(1);
			Categoria categoria = (Categoria)query.getSingleResult();
			 if(null!=categoria){
				 em.remove(categoria);
				 result = true;
			 }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return result;
	}
	
	public boolean deleteEmisiones(EmisionDto emisionDto){
		boolean result = false;
		EntityManager em = EMF.get().createEntityManager();
		try{
			Query  query = em.createQuery("select from " + Emision.class.getName() + " r where r.idEmision = :id");
			query.setParameter("id", emisionDto.getIdEmision());
			query.setMaxResults(1);
			Emision emision = (Emision)query.getSingleResult();
			 if(null!=emision){
				 em.remove(emision);
				 result = true;
			 }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return result;
	}
	
	public boolean deleteEelemento(ElementoDto elementoDto){
		boolean result = false;
		EntityManager em = EMF.get().createEntityManager();
		try{
			Query  query = em.createQuery("select from " + Elemento.class.getName() + " r where r.idElemento = :id");
			query.setParameter("id", elementoDto.getIdElemento());
			query.setMaxResults(1);
			Elemento elemento = (Elemento)query.getSingleResult();
			 if(null!=elemento){
				 em.remove(elemento);
				 result = true;
			 }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return result;
	}
	
	public ArrayList <RestauranteDto> getRestaurantes(){
		ArrayList <RestauranteDto> restauranteDtos = new ArrayList<RestauranteDto>();
		
		RestauranteDto restauranteDto = null;
		EntityManager em = EMF.get().createEntityManager();

		try{
			Query  query = em.createQuery("select from " + Restaurante.class.getName() + " r ");
			List<Restaurante> listRestaurantes = query.getResultList();
			for (Iterator <Restaurante>iterator = listRestaurantes.iterator(); iterator
					.hasNext();) {
				Restaurante restaurante =  iterator.next();
				 restauranteDto = new RestauranteDto();
				 restauranteDto.setNombre(restaurante.getNombre());
				 restauranteDto.setBanner(restaurante.getBanner());
				 restauranteDto.setFondo(restaurante.getFondo());
				 restauranteDto.setIdRestaurante(restaurante.getIdRestaurante().getId());
				 restauranteDtos.add(restauranteDto);
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return restauranteDtos;
	}	
	public RestauranteDto getRestaurante(Long idRestaurante, boolean lazy){
		RestauranteDto restauranteDto = null;
		EntityManager em = EMF.get().createEntityManager();

		try{
			Query  query = em.createQuery("select from " + Restaurante.class.getName() + " r where r.idRestaurante = :id");
			query.setParameter("id", idRestaurante);
			query.setMaxResults(1);
			Restaurante restaurante = (Restaurante)query.getSingleResult();
			 if(null!=restaurante){
				 restauranteDto = new RestauranteDto();
				 restauranteDto.setNombre(restaurante.getNombre());
				 restauranteDto.setBanner(restaurante.getBanner());
				 restauranteDto.setFondo(restaurante.getFondo());
				 restauranteDto.setIdRestaurante(idRestaurante);
			 }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return restauranteDto;
	}
	
	public boolean deleteRestaurante(RestauranteDto restauranteDto){
		boolean result = false;
		EntityManager em = EMF.get().createEntityManager();
		try{
			Query  query = em.createQuery("select from " + Restaurante.class.getName() + " r where r.idRestaurante = :id");
			query.setParameter("id", restauranteDto.getIdRestaurante());
			query.setMaxResults(1);
			Restaurante restaurante = (Restaurante)query.getSingleResult();
			 if(null!=restaurante){
				 em.remove(restaurante);
				 result = true;
			 }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return result;
	}
	
	public long addRestaurante(RestauranteDto restauranteDto){
		EntityManager em = EMF.get().createEntityManager();
		Restaurante restaurante = new Restaurante();
		try{
			restaurante.setNombre(restauranteDto.getNombre());
			restaurante.setFondo(restauranteDto.getFondo());
			restaurante.setBanner(restauranteDto.getBanner());
			em.persist(restaurante);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return restaurante.getIdRestaurante().getId();
	}
	


	public long addCategoria(CategoriaDto categoriaDto){
		EntityManager em = EMF.get().createEntityManager();
		Categoria categoria = new Categoria();
		try{
			categoria.setNombre(categoriaDto.getNombre());
			categoria.setIdRestaurante(categoriaDto.getIdRestaurante());
			categoria.setFoto(categoriaDto.getFoto());
			em.persist(categoria);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return categoria.getIdCategoria().getId();
	}	
	
	public ArrayList<CategoriaDto> getCategorias(Long idRestaurante, boolean lazy){
		ArrayList<CategoriaDto> categorias = new ArrayList<CategoriaDto>();
		CategoriaDto categoriaDto = null;
		EntityManager em = EMF.get().createEntityManager();

		try{	
			Query  query = em.createQuery("select from " + Categoria.class.getName() + " r where r.idRestaurante = :id");
			query.setParameter("id", idRestaurante);
			List<Categoria> categoriasList = query.getResultList();
			for (Iterator<Categoria> iterator = categoriasList.iterator(); iterator.hasNext();) {
				Categoria categoria = iterator.next();
				 categoriaDto = new CategoriaDto();
				 categoriaDto.setIdCategoria(categoria.getIdCategoria().getId());
				 categoriaDto.setNombre(categoria.getNombre());
				 categoriaDto.setFoto(categoria.getFoto());
				 categoriaDto.setIdRestaurante(idRestaurante);
				 categorias.add(categoriaDto);				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return categorias;
	}

	public ArrayList<AnuncioDto> getAnuncios(Long idEmpresa, boolean lazy){
		ArrayList<AnuncioDto> anuncios = new ArrayList<AnuncioDto>();
		AnuncioDto anuncioDto = null;
		EntityManager em = EMF.get().createEntityManager();

		try{	
			Query  query = em.createQuery("select from " + Anuncio.class.getName() + " r where r.idEmpresa = :id");
			query.setParameter("id", idEmpresa);
			List<Anuncio> categoriasList = query.getResultList();
			for (Iterator<Anuncio> iterator = categoriasList.iterator(); iterator.hasNext();) {
				Anuncio anuncio = iterator.next();
				 anuncioDto = new AnuncioDto();
				 anuncioDto.setDuracion(anuncio.getDuracion());
				 anuncioDto.setFotoBig(anuncio.getFotoBig());
				 anuncioDto.setFotoSmall(anuncio.getFotoSmall());
				 anuncioDto.setIdAnuncio(anuncio.getIdAnuncio().getId());
				 anuncioDto.setIdEmpresa(anuncio.getIdEmpresa());
				 anuncios.add(anuncioDto);				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return anuncios;
	}
	
	public ArrayList<AnuncioDto> getAnuncios(boolean lazy){
		ArrayList<AnuncioDto> anuncios = new ArrayList<AnuncioDto>();
		AnuncioDto anuncioDto = null;
		EntityManager em = EMF.get().createEntityManager();

		try{	
			Query  query = em.createQuery("select from " + Anuncio.class.getName() + " r ");
			List<Anuncio> categoriasList = query.getResultList();
			for (Iterator<Anuncio> iterator = categoriasList.iterator(); iterator.hasNext();) {
				Anuncio anuncio = iterator.next();
				 anuncioDto = new AnuncioDto();
				 anuncioDto.setDuracion(anuncio.getDuracion());
				 anuncioDto.setFotoBig(anuncio.getFotoBig());
				 anuncioDto.setFotoSmall(anuncio.getFotoSmall());
				 anuncioDto.setIdAnuncio(anuncio.getIdAnuncio().getId());
				 anuncioDto.setIdEmpresa(anuncio.getIdEmpresa());
				 anuncios.add(anuncioDto);				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return anuncios;
	}
	
	public CategoriaDto getCategoria(Long idCategoria){
		CategoriaDto categoriaDto = null;
		EntityManager em = EMF.get().createEntityManager();
		try{
			Query  query = em.createQuery("select from " + Categoria.class.getName() + " r where r.idCategoria = :id");
			query.setParameter("id", idCategoria);
			query.setMaxResults(1);
			Categoria categoria = (Categoria)query.getSingleResult();
			 if(null!=categoria){
				 categoriaDto = new CategoriaDto();
				 categoriaDto.setNombre(categoria.getNombre());
				 categoriaDto.setFoto(categoria.getFoto());
				 categoriaDto.setIdRestaurante(idCategoria);
			 }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return categoriaDto;
	}
	
	public long addElemento(ElementoDto elementoDto){
		EntityManager em = EMF.get().createEntityManager();
		Elemento elemento = new Elemento();
		try{
			elemento.setIdCategoria(elementoDto.getIdCategoria());			
			elemento.setNombre(elementoDto.getNombre());
			elemento.setDescripcionCorta(elementoDto.getDescripcionCorta());
			elemento.setDescripcionLarga(elementoDto.getDescripcionLarga());
			elemento.setDisponible(elementoDto.isDisponible());
			elemento.setFotoBig(elementoDto.getFotoBig());
			elemento.setFotoSmall(elementoDto.getFotoSmall());
			elemento.setPrecio(elementoDto.getPrecio());
			em.persist(elemento);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return elemento.getIdElemento().getId();
	}
	
	public long addUser(UsuarioDto usuarioDto){
		EntityManager em = EMF.get().createEntityManager();
		Usuario usuario = new Usuario();
		try{
			usuario.setIdRestaurante(usuarioDto.getIdRestaurante());
			usuario.setUsuario(usuarioDto.getUsuario());
			em.persist(usuario);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return usuario.getId().getId();
	}	
	
	public UsuarioDto getUsuario(String usuario){
		UsuarioDto usuarioDto = null;
		EntityManager em = EMF.get().createEntityManager();
		try{
			Query  query = em.createQuery("select from " + Usuario.class.getName() + " r where r.usuario = '"+usuario+"'");
			//query.setParameter("usuario", usuario);
			query.setMaxResults(1);
			Usuario usuarioTmp = (Usuario)query.getSingleResult();
			 if(null!=usuarioTmp){
				 usuarioDto = new UsuarioDto();
				 usuarioDto.setIdRestaurante(usuarioTmp.getIdRestaurante());
				 usuarioDto.setUsuario(usuario);
			 }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return usuarioDto;
	}
	
	
	public ArrayList<UsuarioDto> getUsuarios(){
		ArrayList<UsuarioDto> usuariosDtos = new ArrayList<UsuarioDto>();
		
		EntityManager em = EMF.get().createEntityManager();
		try{
			Query  query = em.createQuery("select from " + Usuario.class.getName() + " r ");
			List<Usuario> listUsuarios = query.getResultList();
			for (Iterator<Usuario> iterator = listUsuarios.iterator(); iterator
					.hasNext();) {
				Usuario usuario =  iterator.next();
				UsuarioDto usuarioDto = new UsuarioDto();
				usuarioDto.setId(usuario.getId().getId());
				usuarioDto.setIdRestaurante(usuario.getIdRestaurante());
				usuarioDto.setUsuario(usuario.getUsuario());
				usuariosDtos.add(usuarioDto);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return usuariosDtos;
	}
	
	public ArrayList<ElementoDto> getElementos(Long idCategoria, boolean lazy){
		ArrayList<ElementoDto> elementoDtos = new ArrayList<ElementoDto>();
		ElementoDto elementoDto = null;
		EntityManager em = EMF.get().createEntityManager();
		try{
			Query  query = em.createQuery("select from " + Elemento.class.getName() + " r where r.idCategoria = :id");
			query.setParameter("id", idCategoria);
			
			List<Elemento> listElementos = query.getResultList();
			
			for (Iterator<Elemento> iterator = listElementos.iterator(); iterator
					.hasNext();) {
				Elemento elemento =  iterator.next();
				 elementoDto = new ElementoDto();
				 elementoDto.setIdElemento(elemento.getIdElemento().getId());
				 elementoDto.setNombre(elemento.getNombre());
				 elementoDto.setDescripcionCorta(elemento.getDescripcionCorta());
				 elementoDto.setDescripcionLarga(elemento.getDescripcionLarga());
				 elementoDto.setDisponible(elemento.isDisponible());
				 elementoDto.setFotoBig(elemento.getFotoBig());
				 elementoDto.setFotoSmall(elemento.getFotoSmall());
				 elementoDto.setIdCategoria(elemento.getIdCategoria());
				 elementoDto.setPrecio(elemento.getPrecio());
				 elementoDtos.add(elementoDto);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return elementoDtos;
	}
	public ElementoDto getElemento(Long idElemento){
		ElementoDto elementoDto = null;
		EntityManager em = EMF.get().createEntityManager();
		try{
			Query  query = em.createQuery("select from " + Elemento.class.getName() + " r where r.idElemento = :id");
			query.setParameter("id", idElemento);
			query.setMaxResults(1);
			Elemento elemento = (Elemento)query.getSingleResult();
			 if(null!=elemento){
				 elementoDto = new ElementoDto();
				 elementoDto.setIdElemento(elemento.getIdElemento().getId());
				 elementoDto.setNombre(elemento.getNombre());
				 elementoDto.setDescripcionCorta(elemento.getDescripcionCorta());
				 elementoDto.setDescripcionLarga(elemento.getDescripcionLarga());
				 elementoDto.setDisponible(elemento.isDisponible());
				 elementoDto.setFotoBig(elementoDto.getFotoBig());
				 elementoDto.setFotoSmall(elemento.getFotoSmall());
				 elementoDto.setIdCategoria(elemento.getIdCategoria());
				 elementoDto.setPrecio(elemento.getPrecio());				 
			 }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return elementoDto;
	}
	
	public long addAnuncio(AnuncioDto anuncioDto){
		EntityManager em = EMF.get().createEntityManager();
		Anuncio anuncio = new Anuncio();
		try{
			anuncio.setIdEmpresa(anuncioDto.getIdEmpresa());
			anuncio.setFotoSmall(anuncioDto.getFotoSmall());
			anuncio.setFotoBig(anuncioDto.getFotoBig());
			anuncio.setDuracion(anuncioDto.getDuracion());

			em.persist(anuncio);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return anuncio.getIdAnuncio().getId();
	}
	
	public AnuncioDto getAnuncio(Long idAnuncio){
		AnuncioDto anuncioDto = null;
		EntityManager em = EMF.get().createEntityManager();

		try{
			Query  query = em.createQuery("select from " + Anuncio.class.getName() + " a where a.idAnuncio = :id");
			query.setParameter("id", idAnuncio);
			query.setMaxResults(1);
			Anuncio anuncio = (Anuncio)query.getSingleResult();
			 if(null!=anuncio){
				 anuncioDto = new AnuncioDto();
				 anuncioDto.setIdAnuncio(idAnuncio);
				 anuncioDto.setFotoBig(anuncio.getFotoBig());
				 anuncioDto.setFotoSmall(anuncio.getFotoSmall());
			 }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return anuncioDto;
	}
	
	public ArrayList<AnuncioDto> getAnuncios(AppContextAware context){
		ArrayList<AnuncioDto> anuncioDtos = new ArrayList<AnuncioDto>();

		EntityManager em = EMF.get().createEntityManager();

		try{
			Query  queryEmision;
			String queryFilter;
			
			if(null!=context.getPropiedad()){
				queryFilter = "where a.idRestaurante = :id and propiedad = :property";
				queryEmision = em.createQuery("select from " + Emision.class.getName() + " a " + queryFilter);
				queryEmision.setParameter("id", context.getIdRestaurante());
				queryEmision.setParameter("property", context.getPropiedad());
			}else{
				queryFilter = "where a.idRestaurante = :id";
				queryEmision = em.createQuery("select from " + Emision.class.getName() + " a " + queryFilter);
				queryEmision.setParameter("id", context.getIdRestaurante());				
			}
			queryEmision.setMaxResults(1);
			Emision emision = (Emision)queryEmision.getSingleResult();
			
			if(null!=emision){
				AnuncioDto anuncioDto = new AnuncioDto();
				Query queryAnuncios = em.createQuery("Select  from "+ AnuncioEmision.class.getName()+ " e  where e.idEmision = :id");
				queryAnuncios.setParameter("id", emision.getIdEmision().getId());
				List<AnuncioEmision> anuncioEmisionList = queryAnuncios.getResultList();
				
				for (Iterator<AnuncioEmision> iterator = anuncioEmisionList.iterator(); iterator
						.hasNext();) {
					AnuncioEmision anuncioEmision = iterator.next();
					Query  query = em.createQuery("select from " + Anuncio.class.getName() + " a where a.idAnuncio = :idA");
					query.setParameter("idA", anuncioEmision.getIdAnuncio());
					query.setMaxResults(1);
					Anuncio anuncio = (Anuncio)query.getSingleResult();
					 if(null!=anuncio){
						 anuncioDto = new AnuncioDto();
						 anuncioDto.setIdAnuncio(anuncio.getIdAnuncio().getId());
						 anuncioDto.setFotoBig(anuncio.getFotoBig());
						 anuncioDto.setFotoSmall(anuncio.getFotoSmall());
						 anuncioDto.setDuracion(anuncio.getDuracion());
						 anuncioDtos.add(anuncioDto);
					 }					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return anuncioDtos;
	}
	
	public long addEmision(EmisionDto emisionDto){
		EntityManager em = EMF.get().createEntityManager();
		Emision emision = new Emision();
		try{
			emision.setIdRestaurante(emisionDto.getIdRestaurante());
			emision.setPropiedad(emisionDto.getPropiedad());
			em.persist(emision);			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return emision.getIdEmision().getId();
	}
	
	public long addAnuncioEmision(AnuncioEmisionDto anuncioEmisionDto){
		EntityManager em = EMF.get().createEntityManager();
		AnuncioEmision anuncioEmision = new AnuncioEmision();
		try{
			anuncioEmision.setIdAnuncio(anuncioEmisionDto.getIdAnuncio());
			anuncioEmision.setIdEmision(anuncioEmisionDto.getIdEmision());
			em.persist(anuncioEmision);			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return anuncioEmision.getIdAnuncioEmision().getId();
	}	
	
	
	
	public AnuncioEmisionDto getAnuncioEmision(Long idAnuncio, Long idEmision){
		AnuncioEmisionDto anuncioDto = null;
		EntityManager em = EMF.get().createEntityManager();

		try{
			Query  query = em.createQuery("select from " + AnuncioEmision.class.getName() + " a where a.idAnuncio = :idA and a.idEmision = :idE");
			query.setParameter("idA", idAnuncio);
			query.setParameter("idE", idEmision);
			query.setMaxResults(1);
			AnuncioEmision anuncio = (AnuncioEmision)query.getSingleResult();
			 if(null!=anuncio){
				 anuncioDto = new AnuncioEmisionDto();
				 anuncioDto.setIdAnuncio(idAnuncio);
				 anuncioDto.setIdEmision(idEmision);
				 anuncioDto.setIdAnuncioEmision(anuncio.getIdAnuncioEmision().getId());
			 }
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
		return anuncioDto;
	}
}
