package parser.visitor.myvisitors;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract subclass of ScheduleAbstractBasicVisitor
 * Provide more getters/setters useful for concrete visitors
 * @author danielecampogiani
 * @see ScheduleAbstractBasicVisitor ScheduleASTVisitor ScheduleIcsVisitor ScheduleMailVisitor ScheduleWebVisitor
 */

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
	
	/**
	 * Mark the LocationSet as set
	 * @param v boolean value
	 */
	protected void setLocationSet(boolean v){
		locationSet = v;
	}
	
	/**
	 * Check if LocationSet is set
	 * @return boolean value
	 */
	protected boolean isLocationSet(){
		return locationSet;
	}
	
	/**
	 * Get the repeating interval (in days) useful only if RepeatinInterval is set
	 * @return repeating interval (in days)
	 */
	protected int getRepeatingIntervall(){
		return repeatingIntervall;
	}
	
	/**
	 * Set the repeating interval
	 * @param value repeating interval (in days)
	 */
	protected void setRepeatingIntervall(int value){
		repeatingIntervall=value;
	}
	
	/**
	 * Check if repeating interval is set
	 * @return boolean value
	 */
	protected boolean isRepeatingSet(){
		return repeatingSet;
	}
	
	/**
	 * Mark repeating as set
	 * @param v boolean value
	 */
	protected void setRepeatingSet(boolean v) {
		repeatingSet = v;
	}
	
	/**
	 * Get the list of participants in the current event
	 * @return list of participants
	 */
	protected List<String> getLastParticipants() {
		return participants;
	}
	
	/**
	 * Get the location of the current event
	 * @return location of the current interval
	 */
	protected String getLastLocation() {
		return ""+lastLocation;
	}
	
	/**
	 * Set the location of the current event
	 * @param location location of the current event
	 */
	protected void setLastLocation(String location){
		lastLocation=location;
	}
	
	/**
	 * Get the subject of the current event
	 * @return subject of the current event
	 */
	protected String getLastDoing(){
		return ""+lastDoing;
	}
	
	/**
	 * Set the subject of the current event
	 * @param doing subject of the current event
	 */
	protected void setLastDoing(String doing) {
		lastDoing=doing;
	}
	
	/**
	 * Mark the timeZone as set
	 * @param v boolean value
	 */
	protected void setTimeZoneSet(boolean v){
		timeZoneset=v;
	}

	/**
	 * Check if timeZone is set
	 * @return boolean value
	 */
	protected boolean isTimeZoneSet(){
		return timeZoneset;
	}

	/**
	 * Get the ending day of the current event
	 * @return ending day of the current event
	 */
	protected int getEndingDay() {
		return endingDay;
	}

	/**
	 * Set the ending day of the current event
	 * @param endingDay ending day of the current event
	 */
	protected void setEndingDay(int endingDay) {
		this.endingDay = endingDay;
	}

	/**
	 * Get the ending month of the current event
	 * @return the ending day of the current event
	 */
	protected int getEndingMonth() {
		return endingMonth;
	}

	/**
	 * Set the ending month of the current event
	 * @param endingMonth ending month of the current event
	 */
	protected void setEndingMonth(int endingMonth) {
		this.endingMonth = endingMonth;
	}

	/**
	 * Get the ending year of the current event
	 * @return the ending day of the current event
	 */
	protected int getEndingYear() {
		return endingYear;
	}

	/**
	 * Set the ending year of the current event
	 * @param endingYear ending year of the current event
	 */
	protected void setEndingYear(int endingYear) {
		this.endingYear = endingYear;
	}
	
}
