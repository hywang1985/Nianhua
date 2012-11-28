package com.hywang.timeline.servlet.handled;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hywang.timeline.DAOFactory;
import com.hywang.timeline.dao.TimlineNodeDAO;
import com.hywang.timeline.entity.TimeLineNode;
import com.hywang.timeline.entity.User;

public class NewArticleServlet extends HttpServlet {

	/**
     * 
     */
	private static final long serialVersionUID = -1450391996762955967L;

	/**
	 * Constructor of the object.
	 */
	public NewArticleServlet() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// response.setContentType("text/html");
		// PrintWriter out = response.getWriter();
		// out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		// out.println("<HTML>");
		// out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		// out.println("  <BODY>");
		// out.print("    This is ");
		// out.print(this.getClass());
		// out.println(", using the GET method");
		// out.println("  </BODY>");
		// out.println("</HTML>");
		// out.flush();
		// out.close();
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String startDate = request.getParameter("startdate");
		String endDate = request.getParameter("enddate");
		String header = request.getParameter("header");
		String article = request.getParameter("article");
		String media = request.getParameter("media");
		String bgrimg = request.getParameter("bgrimg");

		TimeLineNode node = new TimeLineNode();
		Date sdate = getDate(startDate);
		Date edate = getDate(endDate);
		node.setStartDate(sdate);
		node.setEndDate(edate);
		node.setMedia(media);
		node.setHeadline(header);
		node.setText(article);
		node.setBgrImg(bgrimg);
		TimlineNodeDAO nodeDao = DAOFactory.getInstance()
				.createTimelineNodeDAO();
		try {
			int infectId=nodeDao.addNode(node);
			Object userObject = request.getSession().getAttribute("user");
			if (userObject != null) {
				User user = (User) userObject;
				int userId= user.getId();
				nodeDao.updateNodeRelation(userId, infectId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = "/timeline.jsp";
//		System.out.println(url);
		response.sendRedirect(url);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

	private Date getDate(String inputDate) {
		Calendar time = Calendar.getInstance();
		if(inputDate!=null && !"".equals(inputDate)){
			String[] dateArray = inputDate.split("/");
			String day = dateArray[0];
			String month = dateArray[1];
			String year = dateArray[2];
			time.clear();
			time.set(Calendar.YEAR, Integer.parseInt(year));
			time.set(Calendar.MONTH, Integer.parseInt(month));
			time.set(Calendar.DATE, Integer.parseInt(day));
		}
		return time.getTime();
		// date.set
	}

}
