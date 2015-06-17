package ac.tsuda;

import java.io.IOException;
//import java.net.URL;
import java.util.*;

import javax.jdo.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class AddLinkDataServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req,
          HttpServletResponse resp)
          throws ServletException, IOException {
      resp.setContentType("text/plain");
      resp.getWriter().println("no url...");
  }

  @Override
  protected void doPost(HttpServletRequest req,
          HttpServletResponse resp)
          throws ServletException, IOException {
      req.setCharacterEncoding("UTF-8");
      String title = req.getParameter("title");
      String address = req.getParameter("address");
      String item = req.getParameter("item");
      String price = req.getParameter("price");
      Date date = Calendar.getInstance().getTime();
      LinkData data = new LinkData(title,address,item,price,date);
      PersistenceManagerFactory factory = PMF.get();
      PersistenceManager manager = factory.getPersistenceManager();
      try {
          manager.makePersistent(data);
      } finally {
          manager.close();
      }
      resp.sendRedirect("/history.html");
  }
}
