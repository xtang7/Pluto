package rpc;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.mysql.MySQLConnection;
import entity.Machine;

/**
 * Servlet implementation class CheckAvalibity
 */
@WebServlet("/checkavalibity")
public class CheckAvalibity extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckAvalibity() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONArray array = new JSONArray();

		MySQLConnection conn = new MySQLConnection();
		try {
			Set<Machine> availMachines = conn.getAvailMachine();
			for(Machine mach : availMachines) {
				mach.printAllInfo(); 
				JSONObject obj = mach.toJSONObject();
				array.put(obj);
			}
			 
			RpcHelper.writeJsonArray(response, array); 
		} finally {
			conn.close();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
