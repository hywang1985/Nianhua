package com.hywang.timeline.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Timeline Node represent every node on the timeline,hywang
 * 
 * 
 * <!-- Event --> <li>
 * <time>1978,5,11</time> <!-- Event Date --> <time>1978,8,11</time> <!-- End of Event (optional) -->
 * <h3>Headline Goes Here</h3> <!-- Headline --> <article> <!-- Main Text -->
 * <p>
 * The main text goes here. You can have p tags too.
 * </p>
 * </article> <figure> <img src="../taylor/final.jpg"> <!-- Media, can also be a link to youtube video etc (optional)
 * --> <cite>John Doe/Millennium Magazine</cite> <!-- Credit for media (optional) --> <figcaption>Caption goes
 * here.</figcaption> <!-- Caption for media (optional) --> </figure></li>
 * 
 * As shown,the basic property of the node could be: startTime--the time the event node started. endTime--the time the
 * event node ended. Header--the event node title header Article--The articale content of the node Figure-This is a
 * standalone model which represent the object contains of another set of attributes.See Figure.java *
 * **/
public class TimeLineNode implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3041922509808897758L;

	private int ID;

    private Date startDate; // just use Java.utils.Date currently

    private Date endDate;

    private String headline;

    private String text;

    private boolean isStartNode;

    private String tags;
    
    private String media;
    
    private String credit;
    
    private String caption;
    
    private User author; //many to one,as many side
    
    private String bgrImg; //node's background img

    public String getBgrImg() {
		return handelNull(bgrImg);
	}

	public void setBgrImg(String bgrImg) {
		this.bgrImg = bgrImg;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public String getHeadline() {
        return handelNull(headline);
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getText() {
        return handelNull(text);
    }

    public void setText(String text) {
        this.text = text;
    }


    public boolean getIsStartNode() {
        return isStartNode;
    }

    public void setIsStartNode(boolean isStartNode) {
        this.isStartNode = isStartNode;
    }

    public String getTags() {
         return handelNull(tags);
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    
    public String getMedia() {
        return handelNull(media);
    }

    
    public void setMedia(String media) {
        this.media = media;
    }

    
    public String getCredit() {
        return handelNull(credit);
    }

    
    public void setCredit(String credit) {
        this.credit = credit;
    }

    
    public String getCaption() {
        return handelNull(caption);
    }

    
    public void setCaption(String caption) {
        this.caption = caption;
    }
    
    private String handelNull(String value){
    	if( value==null|| value.isEmpty()){ //A specific case is: the value is null, it will be stored into database with content "null"
    		return "";
    	}
    	return value;
    }

}
