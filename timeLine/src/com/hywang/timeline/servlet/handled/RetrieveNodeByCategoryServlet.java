package com.hywang.timeline.servlet.handled;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hywang.timeline.DAOFactory;
import com.hywang.timeline.entity.TimeLineNode;
import com.hywang.timeline.utils.TimeLineNodeUtil;

public class RetrieveNodeByCategoryServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -1243180715719136556L;

    /**
     * Constructor of the object.
     */
    public RetrieveNodeByCategoryServlet() {
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
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
     * This method is called when a form has its tag value method equals to post.
     * 
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<TimeLineNode> tlNodes = DAOFactory.getInstance().createTimelineNodeDAO().getNodesByCategory(1);
        Map nodeProperties = new HashMap();
        JSONObject timeline = new JSONObject();
        JSONObject nodes = new JSONObject();
        JSONArray commonNodes = new JSONArray();
        for (TimeLineNode node : tlNodes) {
            nodeProperties.clear();
            if (node.isStartNode()) { // if node is start node,init the properties
                /**
                 * "headline":"The Kitchen Sink", "type":"default", "startDate":"2011,9,1",
                 * "text":"An example of the different kinds of stuff you can do.",
                 **/
                // nodeProperties.put("endDate", handleDate(node.getEndDate()));
                TimeLineNodeUtil.generateTimeLineNodeProperties(nodeProperties, node,true);
                nodes.putAll(nodeProperties);
            }else if(node.getCate().getId()==1){
                TimeLineNodeUtil.generateTimeLineNodeProperties(nodeProperties, node,false);
                JSONObject commonNode = JSONObject.fromObject(nodeProperties);
                commonNodes.add(commonNode);
            }
            
//            else { // if the node is a common node,init every slide
//                TimeLineNodeUtils.generateTimeLineNodeProperties(nodeProperties, node,false);
//                JSONObject commonNode = JSONObject.fromObject(nodeProperties);
//                commonNodes.add(commonNode);
//            }
            // String json = JSONSerializer.toJSON(testBean , jsonConfig).toString();
            // System.out.println(json);

        }
        if(!commonNodes.isEmpty()){
            nodes.put("date", commonNodes);// finally add all common nodes
        }

        timeline.put("timeline", nodes);
        
        PrintWriter out = response.getWriter();
        System.out.println(timeline.toString());
        // out.print(jarray);
        // System.out.println(jarray.toString());
        out.print(timeline);
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
