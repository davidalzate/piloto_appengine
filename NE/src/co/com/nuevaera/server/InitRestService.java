package co.com.nuevaera.server;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import co.com.nuevaera.resources.AdResource;
import co.com.nuevaera.resources.CategoryResource;
import co.com.nuevaera.resources.ElementResource;
import co.com.nuevaera.resources.IdentityResource;
import co.com.nuevaera.resources.InsertAdResource;
import co.com.nuevaera.resources.SendMailResource;
import co.com.nuevaera.resources.UserResource;


public class InitRestService extends Application {
	
	
	  @Override
	public Restlet createInboundRoot() {
		  
		  
			Router router = new Router(getContext());

			router.attach("/nuevaera/identityresource",IdentityResource.class);
			router.attach("/nuevaera/categoryresource",CategoryResource.class);
			router.attach("/nuevaera/elementresource",ElementResource.class);
			router.attach("/nuevaera/adresource",AdResource.class);
			router.attach("/nuevaera/insertadresource",InsertAdResource.class);
			router.attach("/nuevaera/sendmailresource",SendMailResource.class);
			router.attach("/nuevaera/userresource",UserResource.class);
			return router;
			
	}

}
