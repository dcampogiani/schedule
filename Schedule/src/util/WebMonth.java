package util;

public class WebMonth {
	
	private WebDay[] days;
	private String name;
	
	
	public WebMonth(WebDay[] days, String name) {
		super();
		this.days = days;
		this.name = name;
	}

	public WebDay[] getDays() {
		return days;
	}

	public void setDays(WebDay[] days) {
		this.days = days;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
