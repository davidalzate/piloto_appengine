package co.com.nuevaera.server;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


@SuppressWarnings("serial")
public class UploadServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(UploadServlet.class.getName());

    public static final String SERVING_URL = "servingUrl";
	public static final String CREATED_AT = "createdAt";
	public static final String OWNER_ID = "ownerId";
	
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();    

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
        BlobKey blobKey = blobs.get("image");
       
        if (blobKey == null) {

        } else {

        	ImagesService imagesService = ImagesServiceFactory.getImagesService();
        	String imageUrl = imagesService.getServingUrl(blobKey);

        	
        	
        	Entity uploadedImage = new Entity("UploadedImage");
        	uploadedImage.setProperty("blobKey", blobKey);
        	uploadedImage.setProperty(CREATED_AT, new Date());
        	//uploadedImage.setProperty(OWNER_ID, user.getUserId());
        	
        	// Highly unlikely we'll ever search on this property
        	uploadedImage.setUnindexedProperty(SERVING_URL, imageUrl);
        	
        	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        	datastore.put(uploadedImage);
        	
        	String keyString = KeyFactory.keyToString(uploadedImage.getKey());
            res.sendRedirect("/upload?uploadedImageKey=" + keyString);            		
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	
    	String uploadedImageKey = req.getParameter("uploadedImageKey");
    	resp.setHeader("Content-Type", "text/html");
    	
    	// This is a bit hacky, but it'll work. We'll use this key in an Async service to
    	// fetch the image and image information
    	resp.getWriter().println(uploadedImageKey);
    	
    }
}