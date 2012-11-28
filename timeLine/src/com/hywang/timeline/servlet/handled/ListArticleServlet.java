package com.hywang.timeline.servlet.handled;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hywang.timeline.DAOFactory;
import com.hywang.timeline.dao.TimlineNodeDAO;
import com.hywang.timeline.entity.TimeLineNode;
import com.hywang.timeline.entity.User;

public class ListArticleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4571767516967730831L;

	/**
	 */
	public ListArticleServlet() {
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

			doPost(request, response);
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
		
		TimlineNodeDAO nodeDAO=	DAOFactory.getInstance().createTimelineNodeDAO();
		JSONObject data = new JSONObject();
		JSONArray dataArray = new JSONArray();
		try {
//			List<TimeLineNode> nodes= nodeDAO.getAllNodes();
			Object userObject= request.getSession().getAttribute("user");
			if(userObject!=null && userObject instanceof User){
				int userid= ((User)userObject).getId();
				List<TimeLineNode> nodes= nodeDAO.getNodesByUserID(userid);
				data.put("Result", "OK");
				//ID,startDate,endDate,headLine,text,tags,media,caption,credit
				for(TimeLineNode node:nodes){
					Map<String,String> row =new TreeMap<String,String>();
					row.put("ID", Integer.toString(node.getID()));
					row.put("startDate", "/Date(1320259705710)/");
					row.put("endDate", "/Date(1320259705710)/");
					row.put("headLine", node.getHeadline());
					row.put("text", node.getText());
					row.put("tags", node.getTags());
					row.put("media", node.getMedia());
					row.put("caption", node.getCaption());
					row.put("credit", node.getCredit());
					System.out.println(JSONObject.fromObject(row).toString());
					dataArray.add(JSONObject.fromObject(row).toString());
//				dataArray.add(JSONObject.fromObject(row).toString());
				}
				data.put("Records", dataArray);
			}
			else{
				throw new Exception("invalidate user object,need to login at first");
			}
		} catch (Exception e) {
			e.printStackTrace();
			data.put("Result", "ERROR");
			data.put("Message", e.getMessage());
		}
		
		
		

		PrintWriter out = response.getWriter();
		// out.print(jarray);
		// System.out.println(jarray.toString());
		System.out.println(data.toString());
		out.print(data);
		out.flush();
		out.close();
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
