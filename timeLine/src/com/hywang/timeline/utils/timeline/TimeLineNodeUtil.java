package com.hywang.timeline.utils.timeline;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import com.hywang.timeline.entity.TimeLineNode;
import com.hywang.timeline.entity.User;


public class TimeLineNodeUtil {
   
    public static void generateTimeLineNodeProperties(Map nodeProperties, TimeLineNode node, boolean isStart) {
        nodeProperties.clear();
        nodeProperties.put("startDate", handleDate(node.getStartDate()));
        nodeProperties.put("endDate", handleDate(node.getEndDate()));
        nodeProperties.put("headline", node.getHeadline());
        nodeProperties.put("text", node.getText());
        User author = node.getAuthor();
        String bgrImg=node.getBgrImg();
        if(author!=null){
        	nodeProperties.put("author", author.getUserName()); //the author's username will be displayed on the slider
        }
        if(bgrImg!=null && !"".equals(bgrImg)){
        	nodeProperties.put("bgrimg",bgrImg); //slider's background image
        }
        if (isStart) {
            nodeProperties.put("type", "default");
        } else {// if is a common node,need to generate asset
            JSONObject assetNode = generateAssetNode(node);
            if(assetNode!=null){
                nodeProperties.put("asset", assetNode);
            }
        }
    }
    
    private static String handleDate(Date date) {
        String stringDate = "";
        if (date != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            stringDate = c.get(Calendar.YEAR) + "," + c.get(Calendar.MONTH) + "," + c.get(Calendar.DAY_OF_MONTH);
            // jsonObject = new JSONObject().element("year", c.get(Calendar.YEAR)).element("month",
            // c.get(Calendar.MONTH)).element(
            // "day", c.get(Calendar.DAY_OF_MONTH)).element("hours", c.get(Calendar.HOUR_OF_DAY)).element("minutes",
            // c.get(Calendar.MINUTE)).element("seconds", c.get(Calendar.SECOND)).element("milliseconds",
            // c.get(Calendar.MILLISECOND));
        }
        return stringDate;
    }

    private static JSONObject generateAssetNode(TimeLineNode node) {
        JSONObject assetNode = null;
        if (node != null) {
            assetNode = new JSONObject();
            assetNode.put("media", node.getMedia());
            assetNode.put("credit", node.getCredit());
            assetNode.put("caption", node.getCaption());
        }
        return assetNode;
    }
    
}
