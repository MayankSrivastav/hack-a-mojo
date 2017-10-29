package com.util.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "FirstController", urlPatterns = "/x")
@MultipartConfig
public class firstServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// res.setContentType("text/html");//setting the content type
		//response.setContentType("text/html;charset=UTF-8");

		// Create path components to save the file
		final String path = "C:\\Users\\bimal\\workspace\\Hackathon\\resource";
		final Part filePart = request.getPart("file");
		final String fileName = getFileName(filePart);
		int val=0;
		OutputStream out = null;
		InputStream filecontent = null;
		final PrintWriter writer = response.getWriter();

		try {
			out = new FileOutputStream(new File(path + File.separator + fileName));
			filecontent = filePart.getInputStream();

			int read = 0;
			final byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			// file is create/uploaded to this path
			writer.println("New file " + fileName + " created at " + path);

			// Now give this path to Extracter module. POST
			// returns the boolean result, extracted data from PAN (after,
			// saving to the database, to
			// be displayed on Feedback form)
			request.setAttribute("array", bytes);
			Dao dao = new Dao();
			val=dao.verifyInDb(bytes);
			
			
			response.setContentType("application/json");
			// Get the printwriter object from response to write the required json object to the output stream      
			PrintWriter out1 = response.getWriter();
			// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
			out1.println(val);
			if(val==1)
				out1.print(dao);
			
			out1.flush();
			
			
			//request.getRequestDispatcher("/secondprocess").include(request, response);

		} catch (FileNotFoundException fne) {
			writer.println("You either did not specify a file to upload or are "
					+ "trying to upload a file to a protected or nonexistent " + "location.");
			writer.println("<br/> ERROR: " + fne.getMessage());
		} finally {
			if (out != null) {
				out.close();
			}
			if (filecontent != null) {
				filecontent.close();
			}
			if (writer != null) {
				writer.close();
			}
		}
	}

	private String getFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		// LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}
