package rpc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DBConnection connection = DBConnectionFactory.getConnection();
		
		try {
			HttpSession session = request.getSession(false); 
			JSONObject obj = new JSONObject(); 
			if(session != null) {
				String userId = session.getAttribute("user_id").toString(); 
				obj.put("status", "OK").put("user_id", userId).put("name", connection.getFullName(userId));
				
			}else {
				obj.put("status", "Invalid Session"); 
				response.setStatus(403); 
			}
			RpcHelper.writeJsonObject(response, obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DBConnection connection = DBConnectionFactory.getConnection();
		try {
			JSONObject input = RpcHelper.readJSONObject(request);
			String userId = input.getString("user_id");
			String password = input.getString("password");
			System.out.println("user_id  :" + userId); 
			System.out.println("password :" + password ); 
			JSONObject obj = new JSONObject(); 
			if(connection.verifyLogin(userId, password)) {
				HttpSession session1 = request.getSession();
				if(session1 != null ) System.out.println("session created!");
				session1.setAttribute("user_id", userId); 
				session1.setMaxInactiveInterval(600);
				obj.put("status", "OK").put("user_id", userId).put("name", connection.getFullName(userId));
				
			}else {
				obj.put("status", "User Doesn't exist"); 
				response.setStatus(401); 
			}
			RpcHelper.writeJsonObject(response, obj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

}
