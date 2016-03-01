package eurecom.fr.helloworldgae;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

public class ContactDeleteServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//resp.setContentType("text/plain");
		//resp.getWriter().println("Save contact servlet. GET method doesnt do anything.");
		resp.setContentType("text/html");
		// Let's output the basic HTML headers
		PrintWriter out = resp.getWriter();
		out.println("<html><head><title>Delete a contact</title></head><body>");
		// Get the datastore
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		// Get the entity by key
		
		try {
			datastore.delete(KeyFactory.stringToKey(req.getParameter("id")));
			out.println("Contact deleted </br> <a href='contactlist'>Go back to Contact List</a>");
		} catch (NullPointerException e) {
			// id paramenter not present in the URL
			resp.getWriter().println("<p>Deleting contact failed!</p>");
		}
		
		out.println("</body></html>");
		}
	
}
