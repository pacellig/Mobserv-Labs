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

public class SaveContactServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
			//resp.setContentType("text/plain");
			//resp.getWriter().println("Save contact servlet. GET method doesnt do anything.");
			resp.setContentType("text/html");
			// Let's output the basic HTML headers
			PrintWriter out = resp.getWriter();
			out.println("<html><head><title>Modify a contact</title>"
					+"<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css'>"
					+"<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css'>"
					+"<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js'></script>"
					+"</head><body><div class='container'><div class='panel panel-default'><div class='panel-body'>");
			// Get the datastore
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			// Get the entity by key
			Entity contact = null;
			String name = "", phone = "", pict = "", email = "";
			try {
				contact = datastore.get(KeyFactory.stringToKey(req.getParameter("id")));
				name = (contact.getProperty("name") != null) ? (String) contact.getProperty("name") : "";
				phone = (contact.getProperty("phone") != null) ? (String) contact.getProperty("phone") : "";
				email = (contact.getProperty("email") != null) ? (String) contact.getProperty("email") : "";
				pict = (contact.getProperty("pict") != null) ? (String) contact.getProperty("pict") : "";
			} catch (EntityNotFoundException e) {
				resp.getWriter().println("<p>Creating a new contact</p>");
			} catch (NullPointerException e) {
				// id paramenter not present il the URL
				resp.getWriter().println("<p>Creating a new contact</p>");
			}
			out.println("<form action=\"save\" method=\"post\" name=\"contact\">");
			// Let's build the form
			out.println("<div class='form-group'><div class='col-3'><label>Name: </label></div><div class='col-9'><input name=\"name\" value=\"" + name + "\"/></div></div>"
				+ "<div class='form-group'><div class='col-3'><label>Phone: </label></div><div class='col-9'><input name=\"phone\" value=\"" + phone + "\"/></div></div>"
				+ "<div class='form-group'><div class='col-3'><label>Email: </label></div><div class='col-9'><input name=\"email\" value=\"" + email + "\"/></div></div>"
				+ "<div class='form-group'><div class='col-3'><label>Picture: </label></div><div class='col-9'><input name=\"pict\" value=\"" + pict + "\"/></div></div>");
			out.println("<br/><input type=\"submit\" value=\"Continue\"/></form></div></div></div></body></html>");
			}
		
		/**
		* Save a contact in the DB. The contact can be new or already existent.
		*/
		public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// Retrieve informations from the request
			String contactName = req.getParameter("name");
			String contactPhone = req.getParameter("phone");
			String contactEmail = req.getParameter("email");
			String contactPict = req.getParameter("pict");
			// Take a reference of the datastore
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			// Generate or retrieve the key associated with an existent contact
			// Create or modify the entity associated with the contact
			Entity contact;
			contact = new Entity("Contact", contactName);
			contact.setProperty("name", contactName);
			contact.setProperty("phone", contactPhone);
			contact.setProperty("email", contactEmail);
			contact.setProperty("pict", contactPict);
			// Save in the Datastore
			datastore.put(contact);
			resp.getWriter().println("<html><head></head><body>Contact " + contactName + " saved with key " +
			KeyFactory.keyToString(contact.getKey()) + "!</br>" 
			+ "<a href='contactlist'>Go back to Contact List</a></body></html>");
		}
}
