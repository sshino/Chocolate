package ac.tsuda;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ImportServlet extends HttpServlet {

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession();
		boolean found = false;

		try {
			String password = request.getParameter("password");
			String user = request.getParameter("user");

			Class.forName("org.apache.derby.jdbc.ClientDriver");
			String driverURL = "jdbc:derby://localhost:1527/shohin";
			Connection con = DriverManager.getConnection(driverURL, "db", "db");
			Statement stmt = con.createStatement();
			String sql = "select * from T_USER where USER_NAME=? and PASSWORD=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				found = true;
			}

			String nextJsp;
			if (!found) {
				session.invalidate();
				nextJsp = "/importFailed.html";
				RequestDispatcher dispatcher = request
						.getRequestDispatcher(nextJsp);
				dispatcher.forward(request, response);
			} else {
				session.setAttribute("user", user);
				sql = "select * from T_CHOCO";
				rs = stmt.executeQuery(sql);
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				while (rs.next()) {
					Map<String, Object> record = new HashMap<String, Object>();
					record.put("id", new Integer(rs.getInt("CHOCO_ID")));
					record.put("co", rs.getString("CHOCO_CO"));
					record.put("name", rs.getString("CHOCO_NAME"));
					record.put("price", new Integer(rs.getInt("CHOCO_PRICE")));
					list.add(record);
				}
				rs.close();
				stmt.close();
				con.close();

				request.setAttribute("data", list);

				RequestDispatcher rd = request
						.getRequestDispatcher("/ChocoListFromDB.html");
				rd.forward(request, response);

			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}
