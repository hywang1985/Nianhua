package com.hywang.timeline.servlet.handled;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.hywang.timeline.DAOFactory;
import com.hywang.timeline.dao.TimlineNodeDAO;
import com.hywang.timeline.entity.User;

public class DeleteArticleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3009713064171993020L;

	/**
	 * Constructor of the object.
	 */
	public DeleteArticleServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Object userObject= request.getSession().getAttribute("user");
		BufferedReader reader =null;
		PrintWriter out = response.getWriter();
		JSONObject status = new JSONObject();
	    try {
	         reader = request.getReader();
	         String linepara = reader.readLine();
	         System.out.println(linepara);
	        if(linepara!=null && !"".equals(linepara)){
	        	String nodeid = linepara.split("=")[1];
	        	if(nodeid!=null){
	        		TimlineNodeDAO nodeDao=	DAOFactory.getInstance().createTimelineNodeDAO();
	        		int intNodeId = Integer.parseInt(nodeid);
					nodeDao.delNodeByID(intNodeId);
	        		if(userObject!=null){
	        			User user= (User)userObject;
	        			nodeDao.deleteNodeRelation(user.getId(), intNodeId);
	        		}
	        		status.put("Result", "OK");
					out.print(status);
					out.flush();
					out.close();
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
			status.put("ERROR", e.getMessage());
			out.print(status);
			out.flush();
			out.close();
		}finally{
			reader.close();
		}
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
