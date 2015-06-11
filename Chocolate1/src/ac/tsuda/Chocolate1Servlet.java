package ac.tsuda;

import java.io.*;
import java.util.*;

import javax.jdo.*;
import javax.servlet.http.*;
import java.io.IOException;

@SuppressWarnings("serial")
public class Chocolate1Servlet extends HttpServlet {
	public void doGet(HttpServletRequest req,
            HttpServletResponse resp)
            throws IOException {
        PersistenceManagerFactory factory = PMF.get();
        PersistenceManager manager = factory.getPersistenceManager();
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        req.setCharacterEncoding("utf-8");
        String param1 = req.getParameter("id");
        PrintWriter out = resp.getWriter();
        List<LinkData> list = null;
        if (param1 == null || param1 ==""){
            String query = "select from " + LinkData.class.getName();
            try {
                list = (List<LinkData>)manager.newQuery(query).execute();
            } catch(JDOObjectNotFoundException e){}
        } else {
            try {
                LinkData data = (LinkData)manager.getObjectById(LinkData.class,Long.parseLong(param1));
                list = new ArrayList();
                list.add(data);
            } catch(JDOObjectNotFoundException e){}
        }
        String res = "[";
        if (list != null){
            for(LinkData data:list){
                res += "{id:" + data.getId() + ",address:'" + data.getAddress() + "',title:'" +
                    data.getTitle() + "',date:'" + data.getDatetime() +
                    "',phone:'"+ data.getPhone() +
                    "',mail:'"+ data.getMail() + "'},";
            }
        }
        res += "]";
        out.println(res);
        manager.close();
    }
}
