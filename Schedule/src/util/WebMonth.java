package util;

/**
 * Class used to store information of events in a month and get corresponding web representation
 * @author danielecampogiani
 * @see WebYear WebDay WebEvent
 */

public class WebMonth {
	
	private WebDay[] days;
	private String name;
	
	
	public WebMonth(WebDay[] days, String name) {
		super();
		this.days = days;
		this.name = name;
	}

	/**
	 * Get WebDays in the WebMonth
	 * @return array of WebDay
	 * @see WebDay
	 */
	public WebDay[] getDays() {
		return days;
	}

	/**
	 * Set WebDays of the WebMonth
	 * @param days array of WebDay
	 * @see WebDay
	 */
	public void setDays(WebDay[] days) {
		this.days = days;
	}

	/**
	 * Get the name of the WebMonth
	 * @return name of the month
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the WebMonth
	 * @param name name of the month
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the web representation (HTML+CSS) of the WebMonth
	 * @return HTML and CSS as a String
	 */
	public String getWebRappresentation(){
		String result="";
		
		result+="<h1>"+ getName() +"</h1>";
		//result+= "<div class=\"mydiv\">";
		result+="<ol class=\"calendar\">";
		
		result+="<li class=\"thismonth\">";
		
		result+="<ol>";
		
		for (WebDay current : getDays()){
			result+=current.getWebRappresentation();
		}
		
		result+="</ol>";
		
		result+="</li>"; //chiuso li thismonth
		
		result+="</ol>"; //chiuso ol class calendar
		//result+="</div>";
		return result;
	}
}
