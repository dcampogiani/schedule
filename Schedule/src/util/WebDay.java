package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to store information of events in a day and get corresponding web representation
 * @author danielecampogiani
 * @see WebYear WebMonth WebEvent
 */

public class WebDay {
	
	private int day;
	private List<WebEvent> events;
	
	public WebDay(int day) {
		super();
		this.day = day;
		this.events = new ArrayList<WebEvent>();
	}

	/**
	 * Get the index of the day in the month (starting from 1)
	 * @return the day number
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Set the index of the day in the month (starting from 1)
	 * @param day the day number
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * Get WebEvents in the WebDay
	 * @return list of WebEvents
	 */
	public List<WebEvent> getEvents() {
		return events;
	}

	/**
	 * Set WebEvents in the WebDay
	 * @param events list of WebEvents
	 */
	public void setEvents(List<WebEvent> events) {
		this.events = events;
	}

	/**
	 * Get the web representation (HTML+CSS) of the WebDay
	 * @return HTML and CSS as a String
	 */
	public String getWebRappresentation(){
		
		String result="";
		
		if (getEvents().isEmpty()){
			result+="<li>"+day+"</li>";
			return result;
		}
		
		else{
			result+="<li class=\"important\">"+getDay();
			result+="<ol>";
			
			for (WebEvent current: getEvents()){
				
				result+="<li>";
				
				result+=current.getWebRappresentation();
				
				result+="</li>";
				
				
				
			}
			
			result+="</ol>";
			result+="</li>";
		}
		
		return result;
		
	}
	
}
