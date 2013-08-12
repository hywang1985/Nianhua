package com.hywang.timeline.actions;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hywang.timeline.entity.TimeLineNode;
import com.hywang.timeline.entity.User;
import com.hywang.timeline.services.TimlineNodeService;
import com.opensymphony.xwork2.ActionSupport;

@Controller("articleAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ArticleAction extends BaseAction {

	private static final String CREATE_SUCCESS = "create_success";

	private static final String LOAD_SUCCESS = "load_success";

	private static final String LIST_SUCCESS = "list_success";

	private static final String DELETE_SUCCESS = "delete_success";
	
	private static final String UPDATE_SUCCESS = "update_success";

	private JSONObject tableConfigObject = null;

	private JSONObject listNodes = null;

	private JSONObject deleteStatus = null;
	
	private JSONObject updateStatus = null;
	
	private TimlineNodeService nodeService;


	public TimlineNodeService getNodeService() {
		return nodeService;
	}
	
	@Autowired
	public void setNodeService(TimlineNodeService nodeService) {
		this.nodeService = nodeService;
	}

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
		Date sdate = getDateForDataPicker(startDate);
		Date edate = getDateForDataPicker(endDate);
		node.setStartDate(sdate);
		node.setEndDate(edate);
		node.setMedia(media);
		node.setHeadline(header);
		node.setText(article);
		node.setBgrImg(bgrimg);
		try {
			Object userObject = httpServletRequest.getSession().getAttribute(
					"user");
			if (userObject != null) {
				User user = (User) userObject;
				node.setAuthor(user);
				nodeService.addNode(node); //cascade update
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

	private Date getDateForDataPicker(String inputDate) {
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
	}

	public String loadArticles() {

		String returnCode = ActionSupport.ERROR;
		Map<String, Object> tableConfig = new HashMap<String, Object>();
		tableConfig.put("title", "My articles");
		Map<String, String> actionOptions = new HashMap<String, String>();
		actionOptions.put("listAction", "/article/article_list");
		actionOptions.put("createAction", "/article/article_create");
		actionOptions.put("updateAction", "/article/article_update");
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

		columns.put("caption", columnsOptions.get("caption"));

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
				User user = (User) userObject;
				Set<TimeLineNode> nodes = user.getNodes(); //lazy loading
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
					dataArray.add(JSONObject.fromObject(row).toString());
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

		return returnCode;
	}

	public String deleteArticle() {
		String returnCode = ActionSupport.ERROR;
		if (deleteStatus == null) {
			deleteStatus = new JSONObject();
		} else {
			deleteStatus.clear();
		}
		try {
			String nodeid = (String) httpServletRequest.getParameter("ID");
			if (nodeid != null) {
				int intNodeId = Integer.parseInt(nodeid);
				nodeService.deleteNodeById(intNodeId);
				deleteStatus.put("Result", "OK");
				returnCode = DELETE_SUCCESS;
			}
		} catch (Exception e) {
			logger.error(e);
			deleteStatus.put("ERROR", e.getMessage());
		} finally {
			
		}
		return returnCode;
	}
	
	public String updateArticle(){
		String returnCode = ActionSupport.ERROR; 
		if(updateStatus == null){
			updateStatus = new JSONObject();
		}else{
			updateStatus.clear();
		}
		try {
			String nodeid = (String) httpServletRequest.getParameter("ID");
//			tags, headLine, startDate, text, ID, endDate, credit, media, caption
			String tags = httpServletRequest.getParameter("tags");
			String headLine = httpServletRequest.getParameter("headLine");
			String startDate = httpServletRequest.getParameter("startDate");
			String text = httpServletRequest.getParameter("text");
			String endDate = httpServletRequest.getParameter("endDate");
			String credit = httpServletRequest.getParameter("credit");
			String media = httpServletRequest.getParameter("media");
			String caption = httpServletRequest.getParameter("caption");
			
			TimeLineNode currentNode = nodeService.getNodeByID(Integer.parseInt(nodeid),true);
		
			//reset parameters for current node.
			currentNode.setTags(tags);
			currentNode.setHeadline(headLine);
			Date sdate = getDateForJTable(startDate);
			Date edate = getDateForJTable(endDate);
			currentNode.setStartDate(sdate);
			currentNode.setEndDate(edate);
			currentNode.setMedia(media);
			currentNode.setCaption(caption);
			currentNode.setCredit(credit);
			currentNode.setText(text);
			//update the current node.
			nodeService.updateNode(currentNode);
			updateStatus.put("Result", "OK");
			returnCode = UPDATE_SUCCESS;
		} catch (Exception e) {
			logger.error(e);
			updateStatus.put("ERROR", e.getMessage());
		}finally{
			
		}
		return returnCode;
	}

	private Date getDateForJTable(String dateString) {
		Calendar time = Calendar.getInstance();
		if (dateString != null && !"".equals(dateString)) {
			String[] dateArray = dateString.split("-");
			String year = dateArray[0];
			String month = dateArray[1];
			String day = dateArray[2];
			time.clear();
			time.set(Calendar.YEAR, Integer.parseInt(year));
			time.set(Calendar.MONTH, Integer.parseInt(month));
			time.set(Calendar.DATE, Integer.parseInt(day));
		}
		return time.getTime();
	}

	public JSONObject getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(JSONObject updateStatus) {
		this.updateStatus = updateStatus;
	}

}
