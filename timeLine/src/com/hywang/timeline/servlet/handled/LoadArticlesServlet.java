package com.hywang.timeline.servlet.handled;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class LoadArticlesServlet extends HttpServlet {

	private static final long serialVersionUID = 8048434094060691981L;

	public LoadArticlesServlet() {
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
		
		Map<String,Object> tableConfig = new HashMap<String,Object>();
		tableConfig.put("title", "My articles");
		Map<String,String> actionOptions = new HashMap<String,String>();
		actionOptions.put("listAction", "/article_list");
		actionOptions.put("createAction", "/article_list");
		actionOptions.put("updateAction", "/article_list");
		actionOptions.put("deleteAction", "/DeleteArticleServlet");
		tableConfig.put("actions", actionOptions);
		
		
		//use tree map to make sure the order of columns
		Map<String,Object> columns =new TreeMap<String,Object>();
		
		//generate all columns options
		
		
		Map<String,Map<String,String>>  columnsOptions = generateColumnsOptions();
		
		
		columns.put("ID", columnsOptions.get("ID"));
		
		columns.put("startDate",  columnsOptions.get("startDate"));
		
		columns.put("endDate",  columnsOptions.get("endDate"));
		
		columns.put("headLine",  columnsOptions.get("headLine"));
		
		columns.put("text",  columnsOptions.get("text"));
		
		columns.put("tags",  columnsOptions.get("tags"));
		
		columns.put("media",  columnsOptions.get("media"));

		columns.put("capion",  columnsOptions.get("caption"));

		columns.put("credit",  columnsOptions.get("credit"));
		
		tableConfig.put("fields", columns);
		
		JSONObject tableConfigObject = JSONObject.fromObject(tableConfig);
		  
		   	PrintWriter out = response.getWriter();
	        System.out.println(tableConfigObject.toString());
	        out.print(tableConfigObject);
	        out.flush();
	        out.close();
	}

	private Map<String, Map<String, String>> generateColumnsOptions() {
		
		Map<String,Map<String,String>>  columnsOptions =new TreeMap<String,Map<String,String>>();
		
		Map<String,String> idColumnOptions =new TreeMap<String,String>();
		Map<String,String> startColumnOptions =new HashMap<String,String>();
		Map<String,String> endColumnOptions =new HashMap<String,String>();
		Map<String,String> headColumnOptions =new HashMap<String,String>();
		Map<String,String> textColumnOptions =new HashMap<String,String>();
		Map<String,String> tagsColumnOptions =new HashMap<String,String>();
		Map<String,String> mediaColumnOptions =new HashMap<String,String>();
		Map<String,String> captionColumnOptions =new HashMap<String,String>();
		Map<String,String> creditColumnOptions =new HashMap<String,String>();
		
		idColumnOptions.put("key", "true");
		idColumnOptions.put("title", "ID");
		idColumnOptions.put("create", "false");
		idColumnOptions.put("edit", "false");
		idColumnOptions.put("list", "false");
//		idColumnOptions.put("visibility", "hidden");
		columnsOptions.put("ID", idColumnOptions);
		
	
		startColumnOptions.put("title", "Start Date");
		startColumnOptions.put("displayFormat", "yy-mm-dd");
		startColumnOptions.put("type", "date");
		columnsOptions.put("startDate", startColumnOptions);
		
		endColumnOptions.put("title", "End Date");
		endColumnOptions.put("displayFormat", "yy-mm-dd");
		endColumnOptions.put("type", "date");
		columnsOptions.put("endDate", endColumnOptions);
		
		headColumnOptions.put("title", "Header");
		headColumnOptions.put("type", "text");
		columnsOptions.put("headLine", headColumnOptions);
		
		textColumnOptions.put("list", "false");
		textColumnOptions.put("visibility", "hidden");
		textColumnOptions.put("title", "Article");
		textColumnOptions.put("type", "textarea");
		columnsOptions.put("text", textColumnOptions);
		
		tagsColumnOptions.put("list", "false");
		tagsColumnOptions.put("visibility", "hidden");
		tagsColumnOptions.put("title", "Tags");
		tagsColumnOptions.put("type", "text");
		columnsOptions.put("tags", tagsColumnOptions);
		
		mediaColumnOptions.put("list", "false");
		mediaColumnOptions.put("visibility", "hidden");
		mediaColumnOptions.put("title", "Media");
		mediaColumnOptions.put("type", "text");
		columnsOptions.put("media", mediaColumnOptions);
		
		captionColumnOptions.put("list", "false");
		captionColumnOptions.put("visibility", "hidden");
		captionColumnOptions.put("title", "Caption");
		captionColumnOptions.put("type", "text");
		columnsOptions.put("caption", captionColumnOptions);
		
		creditColumnOptions.put("list", "false");
		creditColumnOptions.put("visibility", "hidden");
		creditColumnOptions.put("title", "Credit");
		creditColumnOptions.put("type", "text");
		columnsOptions.put("credit", creditColumnOptions);
		return columnsOptions;
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	
	public static void main(String[] args) {
		 Map<String, Map<String, String>> aa=new LoadArticlesServlet().generateColumnsOptions();
	}

}
