package co.com.nuevaera.resources;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import co.com.nuevaera.client.dto.AnuncioDto;
import co.com.nuevaera.client.dto.ViewDto;
import co.com.nuevaera.context.AppContextAware;
import co.com.nuevaera.model.NuevaEraPersistCtrl;

import com.google.gson.Gson;

public class InsertAdResource extends ServerResource {
	@Get("json")
	public String handleGet() {
		String rep = null;
		try {
			String idRestaurante = getQuery().getValues("idRestaurante");

			if (null != idRestaurante) {

				String click = getQuery().getValues("click");
				String duracionVisita = getQuery().getValues("duracionVisita");
				String idAnuncio = getQuery().getValues("idAnuncio");
				String millDate = getQuery().getValues("fecha");
				Date date = new Date(Long.parseLong(millDate));
				//Date convertedFecha = formatDate(fecha) ;
				
				AppContextAware appContextAware = new AppContextAware();
				appContextAware.setIdRestaurante(new Long(idRestaurante));
				//appContextAware.setPropiedad(propiedad);
				ViewDto viewDto = new ViewDto();
				viewDto.setClick(Boolean.parseBoolean(click));
				viewDto.setDuracionVisita(Integer.parseInt(duracionVisita));
				viewDto.setFecha(date);
				viewDto.setIdAnuncio(Integer.parseInt(idAnuncio));

				
				NuevaEraPersistCtrl persistCtrl = new NuevaEraPersistCtrl();
				persistCtrl.addView(viewDto);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rep;
	}

	
	private Date formatDate(String dateString){
		Date date = null;
        //String dateString = "Nov 4, 2003 8:14 PM";

        // Get the default MEDIUM/SHORT DateFormat
        DateFormat format =
            DateFormat.getDateTimeInstance(
            DateFormat.MEDIUM, DateFormat.SHORT);

        // Parse the date
        try {
           date = format.parse(dateString);

        }
        catch(ParseException pe) {
            System.out.println("ERROR: could not parse date in string \"" +
                dateString + "\"");
        }
        return date;
	}

}
