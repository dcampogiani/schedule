package util;

import java.util.ArrayList;

public class WebDay {
	
	
	private int day;
	private ArrayList<WebEvent> events;
	
	public WebDay(int day) {
		super();
		this.day = day;
		this.events = new ArrayList<WebEvent>();
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public ArrayList<WebEvent> getEvents() {
		return events;
	}

	public void setEvents(ArrayList<WebEvent> events) {
		this.events = events;
	}

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