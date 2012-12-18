package com.hywang.timeline.actions;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hywang.timeline.DAOFactory;
import com.hywang.timeline.dao.TimlineNodeDAO;
import com.hywang.timeline.entity.TimeLineNode;
import com.hywang.timeline.entity.User;
import com.opensymphony.xwork2.ActionSupport;

public class ArticleAction extends BaseAction {

	private static final String CREATE_SUCCESS = "create_success";

	private static final String LOAD_SUCCESS = "load_success";

	private static final String LIST_SUCCESS = "list_success";

	private static final String DELETE_SUCCESS = "delete_success";

	private JSONObject tableConfigObject = null;

	private JSONObject listNodes = null;

	private JSONObject deleteStatus = null;

	public JSONObject getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(JSONObject deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public JSONObject getListNodes() {
		return listNodes;
	}

	public void setListNodes(JSONObject listNodes) {
		this.listNodes = listNodes;
	}

	public JSONObject getTableConfigObject() {
		return tableConfigObject;
	}

	private static final long serialVersionUID = 4481693444844035645L;

	public String createArticle() {
		logger.info("Creating new article...");
		String returnCode = ActionSupport.ERROR;
		String startDate = httpServletRequest.getParameter("startdate");
		String endDate = httpServletRequest.getParameter("enddate");
		String header = httpServletRequest.getParameter("header");
		String article = httpServletRequest.getParameter("article");
		String media = httpServletRequest.getParameter("media");
		String bgrimg = httpServletRequest.getParameter("bgrimg");

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
			int infectId = nodeDao.addNode(node);
			Object userObject = httpServletRequest.getSession().getAttribute(
					"user");
			if (userObject != null) {
				User user = (User) userObject;
				int userId = user.getId();
				nodeDao.updateNodeRelation(userId, infectId);
			}
			returnCode = CREATE_SUCCESS;
			logger.info("Article create success!");
		} catch (Exception e) {
			logger.error(e);
		}
		return returnCode;
	}

	public void setTableConfigObject(JSONObject tableConfigObject) {
		this.tableConfigObject = tableConfigObject;
	}

	private Date getDate(String inputDate) {
		Calendar time = Calendar.getInstance();
		if (inputDate != null && !"".equals(inputDate)) {
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

	public String loadAricles() {

		String returnCode = ActionSupport.ERROR;
		Map<String, Object> tableConfig = new HashMap<String, Object>();
		tableConfig.put("title", "My articles");
		Map<String, String> actionOptions = new HashMap<String, String>();
		actionOptions.put("listAction", "/article/article_list");
		actionOptions.put("createAction", "/article/article_create");
		actionOptions.put("updateAction", "/article/article_list");
		actionOptions.put("deleteAction", "/article/article_delete");
		tableConfig.put("actions", actionOptions);

		// use tree map to make sure the order of columns
		Map<String, Object> columns = new TreeMap<String, Object>();

		// generate all columns options

		Map<String, Map<String, String>> columnsOptions = generateColumnsOptions();

		columns.put("ID", columnsOptions.get("ID"));

		columns.put("startDate", columnsOptions.get("startDate"));

		columns.put("endDate", columnsOptions.get("endDate"));

		columns.put("headLine", columnsOptions.get("headLine"));

		columns.put("text", columnsOptions.get("text"));

		columns.put("tags", columnsOptions.get("tags"));

		columns.put("media", columnsOptions.get("media"));

		columns.put("capion", columnsOptions.get("caption"));

		columns.put("credit", columnsOptions.get("credit"));

		tableConfig.put("fields", columns);

		this.tableConfigObject = JSONObject.fromObject(tableConfig);
		returnCode = LOAD_SUCCESS;
		return returnCode;
	}

	private Map<String, Map<String, String>> generateColumnsOptions() {

		Map<String, Map<String, String>> columnsOptions = new TreeMap<String, Map<String, String>>();

		Map<String, String> idColumnOptions = new TreeMap<String, String>();
		Map<String, String> startColumnOptions = new HashMap<String, String>();
		Map<String, String> endColumnOptions = new HashMap<String, String>();
		Map<String, String> headColumnOptions = new HashMap<String, String>();
		Map<String, String> textColumnOptions = new HashMap<String, String>();
		Map<String, String> tagsColumnOptions = new HashMap<String, String>();
		Map<String, String> mediaColumnOptions = new HashMap<String, String>();
		Map<String, String> captionColumnOptions = new HashMap<String, String>();
		Map<String, String> creditColumnOptions = new HashMap<String, String>();

		idColumnOptions.put("key", "true");
		idColumnOptions.put("title", "ID");
		idColumnOptions.put("create", "false");
		idColumnOptions.put("edit", "false");
		idColumnOptions.put("list", "false");
		// idColumnOptions.put("visibility", "hidden");
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

	public String listArticles() {
		String returnCode = ActionSupport.ERROR;
		TimlineNodeDAO nodeDAO = DAOFactory.getInstance()
				.createTimelineNodeDAO();
		if (listNodes == null) {
			listNodes = new JSONObject();
		} else {
			listNodes.clear();
		}
		JSONArray dataArray = new JSONArray();
		try {
			// List<TimeLineNode> nodes= nodeDAO.getAllNodes();
			Object userObject = httpServletRequest.getSession().getAttribute(
					"user");
			if (userObject != null && userObject instanceof User) {
				int userid = ((User) userObject).getId();
				List<TimeLineNode> nodes = nodeDAO.getNodesByUserID(userid);
				listNodes.put("Result", "OK");
				// ID,startDate,endDate,headLine,text,tags,media,caption,credit
				for (TimeLineNode node : nodes) {
					Map<String, String> row = new TreeMap<String, String>();
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
					// dataArray.add(JSONObject.fromObject(row).toString());
				}
				listNodes.put("Records", dataArray);
				returnCode = LIST_SUCCESS;
			} else {
				throw new Exception(
						"invalidate user object,need to login at first");
			}
		} catch (Exception e) {
			logger.error(e);
			listNodes.put("Result", "ERROR");
			listNodes.put("Message", e.getMessage());
		}

		// PrintWriter out = response.getWriter();
		// // out.print(jarray);
		// // System.out.println(jarray.toString());
		// System.out.println(data.toString());
		// out.print(data);
		// out.flush();
		// out.close();

		return returnCode;
	}

	public String delete() {
		String returnCode = ActionSupport.ERROR;
		Object userObject = httpServletRequest.getSession()
				.getAttribute("user");
		if (deleteStatus == null) {
			deleteStatus = new JSONObject();
		} else {
			deleteStatus.clear();
		}
		try {
			String nodeid = (String) httpServletRequest.getParameter("ID");
			if (nodeid != null) {
				TimlineNodeDAO nodeDao = DAOFactory.getInstance()
						.createTimelineNodeDAO();
				int intNodeId = Integer.parseInt(nodeid);
				nodeDao.delNodeByID(intNodeId);
				if (userObject != null) {
					User user = (User) userObject;
					nodeDao.deleteNodeRelation(user.getId(), intNodeId);
				}
				deleteStatus.put("Result", "OK");
				returnCode = DELETE_SUCCESS;
				// out.print(deleteStatus);
				// out.flush();
				// out.close();
			}
		} catch (Exception e) {
			logger.error(e);
			deleteStatus.put("ERROR", e.getMessage());
			// out.print(deleteStatus);
			// out.flush();
			// out.close();
		} finally {
		}
		return returnCode;
	}

}
