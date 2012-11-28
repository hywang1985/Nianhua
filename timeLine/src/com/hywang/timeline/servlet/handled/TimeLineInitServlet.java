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
import com.hywang.timeline.dao.TimlineNodeDAO;
import com.hywang.timeline.entity.TimeLineNode;
import com.hywang.timeline.utils.TimeLineNodeUtil;

public class TimeLineInitServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -238244685966592774L;

    /**
     * Constructor of the object.
     */
    public TimeLineInitServlet() {
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
        doPost(request, response);
    };

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

        TimlineNodeDAO timelineDAO = DAOFactory.getInstance().createTimelineNodeDAO();

        List<TimeLineNode> allNodes;
        JSONObject timeline = new JSONObject();
        JSONObject nodes = new JSONObject();
        JSONArray commonNodes = new JSONArray();
        Map nodeProperties = new HashMap();
		try {
			allNodes = timelineDAO.getAllNodes();
			  for (TimeLineNode node : allNodes) {
		            if (node.isStartNode()) { // if node is start node,init the properties
		                /**
		                 * "headline":"The Kitchen Sink", "type":"default", "startDate":"2011,9,1",
		                 * "text":"An example of the different kinds of stuff you can do.",
		                 **/
		                nodeProperties.clear();
		                // nodeProperties.put("endDate", handleDate(node.getEndDate()));
		                TimeLineNodeUtil.generateTimeLineNodeProperties(nodeProperties, node,true);
		                nodes.putAll(nodeProperties);
		            } else { // if the node is a common node,init every slide
		                TimeLineNodeUtil.generateTimeLineNodeProperties(nodeProperties, node,false);
		                JSONObject commonNode = JSONObject.fromObject(nodeProperties);
		                commonNodes.add(commonNode);
		            }
		            // String json = JSONSerializer.toJSON(testBean , jsonConfig).toString();
		            // System.out.println(json);

		        }

		        nodes.put("date", commonNodes);// finally add all common nodes

		        timeline.put("timeline", nodes);

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
        
        

        // JsonValueProcessor jsonProcessor = new JsDateJsonValueProcessor();
        // JsonConfig jsonConfig = new JsonConfig();
        // // 注册值处理器
        // jsonConfig.registerJsonValueProcessor(Date.class, jsonProcessor);
      
        // JsonValueProcessor jsonProcessor = new JsDateJsonValueProcessor();
        // JsonConfig jsonConfig = new JsonConfig();
        // // 注册值处理器
        // jsonConfig.registerJsonValueProcessor(Date.class, jsonProcessor);
        // JSONArray jarray= JSONArray.fromObject(allNodes, jsonConfig);

        // String path =
        // "G:\\Program Files\\Workspaces\\Myeclipse\\timeLine\\src\\com\\hywang\\timeline\\servlet\\example_json.json";
        // String path = request.getSession().getServletContext().getRealPath("/") + "/json/example_json.json";
        // String content = readFileByLines(path);
        // System.out.println(content);
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

//    public static String readFileByLines(String fileName) {
//        File file = new File(fileName);
//        StringBuffer buffer = new StringBuffer();
//        BufferedReader reader = null;
//        try {
//            System.out.println("以行为单位读取文件内容，一次读一整行：");
//            reader = new BufferedReader(new FileReader(file));
//            String tempString = null;
//            int line = 1;
//            // 一次读入一行，直到读入null为文件结束
//            while ((tempString = reader.readLine()) != null) {
//                // 显示行号
//                // System.out.println("line " + line + ": " + tempString);
//                buffer.append(tempString);
//                buffer.append("\n");
//                line++;
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e1) {
//                }
//            }
//        }
//        return buffer.toString();
//    }


//    public static void main(String[] args) {
//        String path = "G:\\Program Files\\Workspaces\\Myeclipse\\timeLine\\src\\com\\hywang\\timeline\\servlet\\example_json.json";
//        String content = readFileByLines(path);
//        System.out.println(content);
//    }

}
