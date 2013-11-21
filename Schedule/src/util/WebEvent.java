package util;

import java.util.ArrayList;
import java.util.List;

public class WebEvent {
	
	int fromH;
	int fromM;
	int toH;
	int toM;
	private String doing;
	private String at;
	private List<String> participants;
	
	
	public WebEvent(int fromH, int fromM, int toH, int toM, String doing,
			String at, List<String> participants) {
		super();
		this.fromH = fromH;
		this.fromM = fromM;
		this.toH = toH;
		this.toM = toM;
		this.doing = doing;
		if (at!=null)
			this.at = at;
		else 
			this.at="";
		if (participants!=null)
			this.participants = participants;
		else 
			this.participants = new ArrayList<String>();
	}

	/**
	 * Get the beginning time of the WebEvent (hours)
	 * @return beginning time (hours)
	 */
	public int getFromH() {
		return fromH;
	}

	/**
	 * Set the beginning time of the WebEvent (hours)
	 * @param fromH beginning time (hours)
	 */
	public void setFromH(int fromH) {
		this.fromH = fromH;
	}

	/**
	 * Get the beginning time of the WebEvent (minutes)
	 * @return beginning time (minutes)
	 */
	public int getFromM() {
		return fromM;
	}

	/**
	 * Set the beginning time of the WebEvent (minutes)
	 * @param fromM beginning time (minutes)
	 */
	public void setFromM(int fromM) {
		this.fromM = fromM;
	}

	/**
	 * Get the ending time of the WebEvent (hours)
	 * @return ending time (hours)
	 */
	public int getToH() {
		return toH;
	}

	/**
	 * Set the ending time of the WebEvent (hours)
	 * @param toH ending time (hours)
	 */
	public void setToH(int toH) {
		this.toH = toH;
	}

	/**
	 * Get the ending time of the WebEvent (minutes)
	 * @return ending time (minutes)
	 */
	public int getToM() {
		return toM;
	}

	/**
	 * Set the ending time of the WebEvent (minutes)
	 * @param toM ending time (minutes)
	 */
	public void setToM(int toM) {
		this.toM = toM;
	}

	/**
	 * Get the WebEvent subject
	 * @return subject of the WebEvent
	 */
	public String getDoing() {
		return doing;
	}

	/**
	 * Set the WebEvent subject
	 * @param doing WebEvent subject
	 */
	public void setDoing(String doing) {
		this.doing = doing;
	}

	/**
	 * Get WebEvent location
	 * @return WebEvent location
	 */
	public String getAt() {
		return at;
	}

	/**
	 * Set WebEvent location
	 * @param at WebEvent location
	 */
	public void setAt(String at) {
		this.at = at;
	}

	/**
	 * Get participants of the WebEvent
	 * @return list of participants
	 */
	public List<String> getParticipants() {
		return participants;
	}

	/**
	 * Set participants of the WebEvent
	 * @param participants list of participants
	 */
	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}

	/**
	 * Get the web representation (HTML+CSS) of the WebDay
	 * @return HTML and CSS as a String
	 */
	public String getWebRappresentation(){
		String result="";
		
		result+=getFromH()+":"+getFromM()+"-"+getToH()+":"+getToM()+"<br/>";
		result+=getDoing()+"<br/>";
		
		if (!getAt().equals(""))
			result+="At:"+getAt()+"<br/>";
		
		if (!getParticipants().isEmpty()){
			result+="with:<br/>";
			
			for (String current:getParticipants())
				result+=current+"<br/>";
		}
		
		return result;
	}
	
}
