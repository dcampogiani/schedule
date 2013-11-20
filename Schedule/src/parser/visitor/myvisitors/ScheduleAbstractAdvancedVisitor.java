package parser.visitor.myvisitors;

import java.util.ArrayList;
import java.util.List;


public abstract class ScheduleAbstractAdvancedVisitor extends
		ScheduleAbstractBasicVisitor {

	private boolean timeZoneset;
	private int endingDay;
	private int endingMonth;
	private int endingYear;
	private String lastDoing;
	private String lastLocation;
	private ArrayList<String> participants;
	private boolean repeatingSet;
	private int repeatingIntervall;
	private boolean locationSet;

	
	public ScheduleAbstractAdvancedVisitor(){
		super();
		timeZoneset=false;
		endingDay=0;
		endingMonth=0;
		endingYear=0;
		lastDoing="";
		lastLocation="";
		participants = new ArrayList<String>();
		repeatingSet=false;
		locationSet=false;
	}
	
	protected void setLocationSet(boolean v){
		locationSet = v;
	}
	
	protected boolean isLocationSet(){
		return locationSet;
	}
	
	protected int getRepeatingIntervall(){
		return repeatingIntervall;
	}
	
	protected void setRepeatingIntervall(int value){
		repeatingIntervall=value;
	}
	
	protected boolean isRepeatingSet(){
		return repeatingSet;
	}
	
	protected void setRepeatingSet(boolean v) {
		repeatingSet = v;
	}
	
	protected List<String> getLastParticipants() {
		return participants;
	}
	
	protected String getLastLocation() {
		return ""+lastLocation;
	}
	
	protected void setLastLocation(String location){
		lastLocation=location;
	}
	
	protected String getLastDoing(){
		return ""+lastDoing;
	}
	
	protected void setLastDoing(String doing) {
		lastDoing=doing;
	}
	
	protected void setTimeZoneSet(boolean v){
		timeZoneset=v;
	}

	protected boolean isTimeZoneSet(){
		return timeZoneset;
	}

	protected int getEndingDay() {
		return endingDay;
	}

	protected void setEndingDay(int endingDay) {
		this.endingDay = endingDay;
	}

	protected int getEndingMonth() {
		return endingMonth;
	}

	protected void setEndingMonth(int endingMonth) {
		this.endingMonth = endingMonth;
	}

	protected int getEndingYear() {
		return endingYear;
	}

	protected void setEndingYear(int endingYear) {
		this.endingYear = endingYear;
	}
	
}
