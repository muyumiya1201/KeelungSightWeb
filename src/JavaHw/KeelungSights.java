package JavaHw;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class KeelungSights
 */
//@WebServlet("/00657124")
public class KeelungSights extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     * Name : Peggy Li
     * Date : 2019/07/02
     * Discription : Combine with HW1 and output the result with json
     */
	
    public KeelungSights() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8"); //轉換成utf-8
		response.setContentType("application/json; charset=utf-8"); //轉換成json
		response.addHeader("Access-Control-Allow-Origin", "*");
		String zone = request.getParameter("zone"); //拿到zone的參數
		//創造json
		Gson gson = new Gson();
		//抓網頁的內容
		KeelungSightsCrawler keelungSightView = new KeelungSightsCrawler();
		Sight[] sights = keelungSightView.getItems(zone);
		response.getWriter().append(gson.toJson(sights)); //印出	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
