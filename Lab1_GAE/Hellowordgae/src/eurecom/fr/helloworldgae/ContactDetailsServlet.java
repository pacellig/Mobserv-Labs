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

public class ContactDetailsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (req.getParameter("id") == null) {
			resp.getWriter().println("ID cannot be empty!");
			return;
		}
		// Get the datastore
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		// Get the entity by key
		Entity contact = null;
		try {
			contact = datastore.get(KeyFactory.stringToKey(req.getParameter("id")));
		} catch (EntityNotFoundException e) {
			resp.getWriter().println("Sorry, no contact for the given key");
			return;
		}
		// Let's output the basic HTML headers
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		out.println("<html><head><title>Contacts list</title>"
				+"<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css'>"
				+"<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css'>"
				+"<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js'></script>"
				+"</head><body><div class='container'>");

		out.println(" <div class='panel panel-primary'><div class='panel-body'>"
		    +"<b>"+contact.getProperty("name")
		+"</b></div><div class='panel-footer'><img src=\""
		
		+ contact.getProperty("pict")
		+ "\" alt=\"Profile picture\" border=1/>");
	out.println("</br>Phone: " + contact.getProperty("phone") + "</br>"
		+ "Email: <a href=\"mailto:"
		+ contact.getProperty("email") + "\">"
		+ contact.getProperty("email") + "</a>"
		+ "</br>");
	out.println("<a href=\"save?id=" + req.getParameter("id")
		+ "\">Modify</a></br>"
		+ "<a href=\"delete?id=" + req.getParameter("id")
		+ "\">Delete Contact</a></div></div></div></body></html>");
	}
}
