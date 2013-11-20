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

	public int getFromH() {
		return fromH;
	}

	public void setFromH(int fromH) {
		this.fromH = fromH;
	}

	public int getFromM() {
		return fromM;
	}

	public void setFromM(int fromM) {
		this.fromM = fromM;
	}

	public int getToH() {
		return toH;
	}

	public void setToH(int toH) {
		this.toH = toH;
	}

	public int getToM() {
		return toM;
	}

	public void setToM(int toM) {
		this.toM = toM;
	}

	public String getDoing() {
		return doing;
	}

	public void setDoing(String doing) {
		this.doing = doing;
	}

	public String getAt() {
		return at;
	}

	public void setAt(String at) {
		this.at = at;
	}

	public List<String> getParticipants() {
		return participants;
	}

	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}
	
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
