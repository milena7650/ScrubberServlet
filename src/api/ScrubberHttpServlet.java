package api;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ScrubberHttpServlet
 */
@WebServlet("/api/links")
public class ScrubberHttpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String HTML_START="<html><body>";
	public static final String HTML_END="</body></html>";
	public static final String filename = "/temp/pagelinks.ser";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScrubberHttpServlet() {        super();    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();
		
		File f = new File(filename);
		if(!f.exists()){
			out.print("Sorry, nothing to show. " );
		}
		else{
			 Scrubber myScrubber = new Scrubber();
		     myScrubber.loadFromFile(filename);
		     out.print(HTML_START + myScrubber.printLinksAsHtmlList() + HTML_END);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();    
		String urladdress = request.getParameter("urladdress");  
		if(urladdress.isEmpty())
			out.print("Sorry, nothing to show. " );
		else{
			 Scrubber myScrubber = new Scrubber();
		     myScrubber.getLinksFromUrl(urladdress);
		     if(myScrubber.saveToFile(filename))
		    	 out.print("All done. " );
		     else
		    	 out.print("Sorry, something going wrong. Try again. " );
		}
	}

}
