package site.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import site.search.SiteSearcher;

/**
 * Servlet implementation class SuggestServlet
 */
@WebServlet("/SuggestServlet")
public class SuggestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SuggestServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		
		String query= request.getParameter("query");
		List<String> suggestions = new ArrayList<String>();
		new SiteSearcher().suggest(query, suggestions);
		
		try 
		{
			JSONArray sugtArr = new JSONArray();
		
			for (String suggestion : suggestions)
			{
				JSONObject obj = new JSONObject();
				obj.put("title", suggestion);
				sugtArr.put(obj);
			}
			
			JSONObject res = new JSONObject();
			res.put("results", sugtArr);
			
			response.getWriter().write(res.toString());
			response.getWriter().close();
			return ;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
