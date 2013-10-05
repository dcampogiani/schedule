package parser.visitor.myvisitors;

import java.net.SocketException;
import java.net.URI;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;
import parser.syntaxtree.AllDayDuration;
import parser.syntaxtree.Body;
import parser.syntaxtree.Day;
import parser.syntaxtree.DayDate;
import parser.syntaxtree.Declarations;
import parser.syntaxtree.Doing;
import parser.syntaxtree.Duration;
import parser.syntaxtree.Event;
import parser.syntaxtree.FromToDuration;
import parser.syntaxtree.INode;
import parser.syntaxtree.Location;
import parser.syntaxtree.LocationDeclaration;
import parser.syntaxtree.NodeChoice;
import parser.syntaxtree.NodeList;
import parser.syntaxtree.NodeListOptional;
import parser.syntaxtree.NodeOptional;
import parser.syntaxtree.NodeSequence;
import parser.syntaxtree.NodeTCF;
import parser.syntaxtree.NodeToken;
import parser.syntaxtree.OthersPartecipants;
import parser.syntaxtree.Partecipant;
import parser.syntaxtree.Partecipants;
import parser.syntaxtree.PersonDeclaration;
import parser.syntaxtree.Place;
import parser.syntaxtree.Repeating;
import parser.syntaxtree.RepeatingStop;
import parser.syntaxtree.RepeatingTime;
import parser.syntaxtree.Scope;
import parser.syntaxtree.TimeEvent;
import parser.syntaxtree.TimeZoneDeclaration;
import parser.syntaxtree.VariableDeclaration;
import parser.visitor.IVoidVisitor;

public class ScheduleIcsVisitor implements IVoidVisitor {

	private String output;
	private net.fortuna.ical4j.model.Calendar icsCalendar;
	private HashMap<String, String> people;
	private HashMap<String, String> locations;
	private TimeZone timezone;
	private VTimeZone vtTimeZone;
	private boolean timeZoneset;
	private int fromH;
	private int fromM;
	private int toH;
	private int toM;
	private boolean fromTimeSet;
	private int beginningDay;
	private int beginnigMonth;
	private int beginningYear;
	private boolean beginningDateSet;
	private int endingDay;
	private int endingMonth;
	private int endingYear;
	private ArrayList<VEvent> events;
	private String lastDoing;
	private String lastLocation;
	private ArrayList<String> participants;
	private boolean repeatingSet;
	private int repeatingIntervall;

	public ScheduleIcsVisitor(){
		icsCalendar = new net.fortuna.ical4j.model.Calendar();
		icsCalendar.getProperties().add(new ProdId("-//Daniele Campogiani//Schedule 1.0//EN"));
		icsCalendar.getProperties().add(Version.VERSION_2_0);
		icsCalendar.getProperties().add(CalScale.GREGORIAN);
		output="";
		people= new HashMap<String, String>();
		locations = new HashMap<String, String>();
		timeZoneset = false;
		fromH=0;
		fromM=0;
		toH=0;
		toM=0;
		fromTimeSet=false;
		beginningDay=0;
		beginnigMonth=0;
		beginningYear=0;
		beginningDateSet=false;
		endingDay=0;
		endingMonth=0;
		endingYear=0;
		events = new ArrayList<VEvent>();
		lastDoing="";
		lastLocation="";
		participants = new ArrayList<String>();
		repeatingSet=false;
		repeatingIntervall=0;
	}

	private int getRepeatingIntervall(){
		return repeatingIntervall;
	}
	
	private void setRepeatingIntervall(int value){
		repeatingIntervall=value;
	}
	
	private boolean isRepeatingSet(){
		return repeatingSet;
	}
	
	private void setRepeatingSet(boolean v) {
		repeatingSet = v;
	}
	
	private ArrayList<String> getLastParticipants() {
		return participants;
	}
	
	private String getLastLocation() {
		return ""+lastLocation;
	}
	
	private void setLastLocation(String location){
		lastLocation=location;
	}
	
	private String getLastDoing(){
		return ""+lastDoing;
	}
	
	private void setLastDoing(String doing) {
		lastDoing=doing;
	}
	
	private ArrayList<VEvent> getEvents(){
		return events;
	}
	
	public int getBeginningDay() {
		return beginningDay;
	}

	public void setBeginningDay(int beginningDay) {
		this.beginningDay = beginningDay;
	}

	public int getBeginnigMonth() {
		return beginnigMonth;
	}

	public void setBeginnigMonth(int beginnigMonth) {
		this.beginnigMonth = beginnigMonth;
	}

	public int getBeginningYear() {
		return beginningYear;
	}

	public void setBeginningYear(int beginningYear) {
		this.beginningYear = beginningYear;
	}

	public int getEndingDay() {
		return endingDay;
	}

	public void setEndingDay(int endingDay) {
		this.endingDay = endingDay;
	}

	public int getEndingMonth() {
		return endingMonth;
	}

	public void setEndingMonth(int endingMonth) {
		this.endingMonth = endingMonth;
	}

	public int getEndingYear() {
		return endingYear;
	}

	public void setEndingYear(int endingYear) {
		this.endingYear = endingYear;
	}

	private void setBeginningDateSet(boolean v){
		beginningDateSet = v;
	}

	private boolean isBeginningDateSet(){
		return beginningDateSet;
	}

	private void setFromTimeSet(boolean v){
		fromTimeSet=v;
	}

	private boolean isFromTimeSet(){
		return fromTimeSet;
	}

	private void setTimeZoneSet(boolean v){
		timeZoneset=v;
	}

	private boolean isTimeZoneSet(){
		return timeZoneset;
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

	public String getOutput(){
		return output;
	}

	@Override
	public void visit(NodeChoice n) {
		n.accept(this);
		return;
	}

	@Override
	public void visit(NodeList n) {
		for (final Iterator<INode> e = n.elements(); e.hasNext();) {
			e.next().accept(this);
		}
		return;

	}

	@Override
	public void visit(NodeListOptional n) {
		if (n.present()) {
			for (final Iterator<INode> e = n.elements(); e.hasNext();) {
				e.next().accept(this);
			}
			return;
		} else
			return;

	}

	@Override
	public void visit(NodeOptional n) {
		if (n.present()) {
			n.node.accept(this);
			return;
		} else
			return;

	}

	@Override
	public void visit(NodeSequence n) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NodeTCF n) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NodeToken n) {
		// TODO Auto-generated method stub

	}

	/**
	 * Scope()
	 * 
	 * f0-> Declarations()
	 * f1-> Body()
	 */
	@Override
	public void visit(Scope n) {
		n.f0.accept(this); //Declarations
		n.f1.accept(this);
		output=icsCalendar.toString();

	}

	/**
	 * Declarations()
	 * 
	 * f0-> TimeZoneDeclaration()
	 * f1-> VariableDeclaration()
	 */
	@Override
	public void visit(Declarations n) {
		if (n.f0.present()){ //TimeZoneDeclaration
			TimeZoneDeclaration tzd = (TimeZoneDeclaration)n.f0.node;
			tzd.accept(this);
		}
		n.f1.accept(this); //VariableDeclaration

	}


	/**
	 * TimeZoneDeclaration()
	 * 
	 * f0-> timeZone
	 * f1-> =
	 * f2-> < TIMEZONE >
	 * @param n
	 */
	public void visit(TimeZoneDeclaration n) {
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);

		setTimeZoneSet(true);

		TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
		timezone = registry.getTimeZone(n.f2.tokenImage);
		vtTimeZone = timezone.getVTimeZone();

	}

	/**
	 * VariableDeclaration()
	 * 
	 * f0-> PersonDeclaration() | LocationDeclaration()
	 * @param n
	 */
	@Override
	public void visit(VariableDeclaration n) {
		n.f0.accept(this);

	}

	/**
	 * PersonDeclaration()
	 * 
	 * f0-> person
	 * f1-> < ID >
	 * f2-> =
	 * f3-> < MAIL >
	 * @param n
	 */
	@Override
	public void visit(PersonDeclaration n) {
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		people.put(n.f1.tokenImage, n.f3.tokenImage);

	}

	/**
	 * LocationDeclaration()
	 * 
	 * f0-> location
	 * f1-> < ID >
	 * f2-> =
	 * f3-> < String >
	 */
	@Override
	public void visit(LocationDeclaration n) {
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		locations.put(n.f1.tokenImage, n.f3.tokenImage);

	}

	/**
	 * Body()
	 * 
	 * f0 -> Day()
	 * 
	 */
	@Override
	public void visit(Body n) {
		n.f0.accept(this);
		
		for (VEvent current: getEvents())
			icsCalendar.getComponents().add(current);

	}

	/**
	 * Day()
	 * 
	 * f0-> on
	 * f1-> DayDate()
	 * f2-> :
	 * f3-> Event()
	 */
	@Override
	public void visit(Day n) {
		n.f0.accept(this);
		setBeginningDateSet(false);
		n.f1.accept(this);

		setBeginningDateSet(true);
		n.f2.accept(this);
		n.f3.accept(this);

	}

	/**
	 * Event()
	 * 
	 * f0-> {
	 * f1-> Duration()
	 * f2-> Doing()
	 * f3-> Partecipants()
	 * f4-> Location()
	 * f5-> Repeating
	 * f6-> }
	 */
	public void visit(Event n) {
		// TODO Auto-generated method stub
		n.f0.accept(this);
		n.f1.accept(this); //Duration

		n.f2.accept(this); //Doing

		if (n.f3.present()){ //Partecipants
			Partecipants partecipants = (Partecipants)n.f3.node;
			partecipants.accept(this);

		}
		if (n.f4.present()){//Location
			Location location = (Location)n.f4.node;
			location.accept(this);

		}
		if (n.f5.present()){//Repeating
			Repeating repeating = (Repeating)n.f5.node;
			repeating.accept(this);

		}
		n.f6.accept(this);

		java.util.Calendar startDate = new GregorianCalendar();
		if (isTimeZoneSet())
			startDate.setTimeZone(timezone);
		startDate.set(java.util.Calendar.MONTH, getBeginnigMonth()-1);
		startDate.set(java.util.Calendar.DAY_OF_MONTH, getBeginningDay());
		startDate.set(java.util.Calendar.YEAR, getBeginningYear());
		startDate.set(java.util.Calendar.HOUR_OF_DAY, getFromH());
		startDate.set(java.util.Calendar.MINUTE, getFromM());
		startDate.set(java.util.Calendar.SECOND, 0);
		
		
		java.util.Calendar endDate = new GregorianCalendar();
		if (isTimeZoneSet())
			endDate.setTimeZone(timezone);
		endDate.set(java.util.Calendar.MONTH, getBeginnigMonth()-1);
		endDate.set(java.util.Calendar.DAY_OF_MONTH, getBeginningDay());
		endDate.set(java.util.Calendar.YEAR, getBeginningYear());
		endDate.set(java.util.Calendar.HOUR_OF_DAY, getToH());
		endDate.set(java.util.Calendar.MINUTE, getToM());	
		endDate.set(java.util.Calendar.SECOND, 0);
		
		String doing = getLastDoing();
		
		DateTime start = new DateTime(startDate.getTime());
		DateTime end = new DateTime(endDate.getTime());
		VEvent event = new VEvent(start, end, doing);
		
		if (isTimeZoneSet())
			event.getProperties().add(vtTimeZone.getTimeZoneId());
	
		
		UidGenerator ug = null;
		try {
			ug = new UidGenerator("uidGen");
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Uid uid = ug.generateUid();
		event.getProperties().add(uid);
		
		for (String participant: getLastParticipants()){
			Attendee partecipante = new Attendee(URI.create(participant));
			partecipante.getParameters().add(Role.REQ_PARTICIPANT);
			partecipante.getParameters().add(new Cn(participant));
			event.getProperties().add(partecipante);
		}
		
		participants.clear();
		
		net.fortuna.ical4j.model.property.Location location = new net.fortuna.ical4j.model.property.Location( getLastLocation());
		event.getProperties().add(location);
		
		getEvents().add(event);
	}

	/**
	 * DayDate()
	 * 
	 * f0-> < INTEGER >
	 * f1-> -
	 * f2-> < INTEGER >
	 * f3-> -
	 * f4-> < INTEGER >
	 */
	@Override
	public void visit(DayDate n) {
		n.f0.accept(this);
		int day = Integer.parseInt(n.f0.tokenImage);

		n.f1.accept(this);
		n.f2.accept(this);
		int month = Integer.parseInt(n.f2.tokenImage);

		n.f3.accept(this);
		n.f4.accept(this);
		int year = Integer.parseInt(n.f4.tokenImage);
		
		if (!beginningDateSet){
			setBeginningDay(day);
			setBeginnigMonth(month);
			setBeginningYear(year);
			setBeginningDateSet(true);
		}
		
		else {
			setEndingDay(day);
			setEndingMonth(month);
			setEndingYear(year);
		}

	}

	/**
	 * Duration()
	 * 
	 * f0-> AllDayDuration() | FromToDuration()
	 */
	@Override
	public void visit(Duration n) {
		n.f0.accept(this);

	}

	/**
	 * AllDayDuration()
	 * 
	 * f0-> allDay
	 */
	@Override
	public void visit(AllDayDuration n) {
		n.f0.accept(this);
		setFromH(0);
		setFromM(0);
		setFromTimeSet(true);
		setToH(23);
		setToM(59);

	}

	/**
	 * FromToDuration()
	 * 
	 * f0-> from
	 * f1-> TimeEvent()
	 * f2-> to
	 * f3-> TimeEvent()
	 */
	@Override
	public void visit(FromToDuration n) {
		setFromTimeSet(false);
		n.f0.accept(this);
		n.f1.accept(this);
		setFromTimeSet(true);
		n.f2.accept(this);
		n.f3.accept(this);
		setFromTimeSet(false);

	}

	/**
	 * TimeEvent()
	 * 
	 * f0-> < INTEGER >
	 * f1-> :
	 * f2-> < INTEGER >
	 */
	@Override
	public void visit(TimeEvent n) {
		n.f0.accept(this);
		int hour = Integer.parseInt(n.f0.tokenImage);

		n.f1.accept(this);
		n.f2.accept(this);
		int minutes = Integer.parseInt(n.f2.tokenImage);

		
		if (!isFromTimeSet()){
			setFromH(hour);
			setFromM(minutes);
		}
		
		else{
			setToH(hour);
			setToM(minutes);
		}

	}

	/**
	 * Doing()
	 * 
	 * f0-> do
	 * f1-> < STRING >
	 */
	@Override
	public void visit(Doing n) {
		n.f0.accept(this);
		n.f1.accept(this);
		setLastDoing(n.f1.tokenImage);

	}

	/**
	 * Partecipants()
	 * 
	 * f0-> with
	 * f1-> Partecipant()
	 * f2-> OthersPartecipants()
	 */
	@Override
	public void visit(Partecipants n) {
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);

	}

	/**
	 * OthersPartecipants()
	 * 
	 * f0-> ,
	 * f1-> Partecipant()
	 */
	@Override
	public void visit(OthersPartecipants n) {
		n.f0.accept(this);
		n.f1.accept(this);

	}

	/**
	 * Partecipant()
	 * 
	 * f0-> < ID > | < MAIL >
	 */
	@Override
	public void visit(Partecipant n) {
		n.f0.accept(this);
		if (n.f0.choice instanceof NodeToken ){
			NodeToken token = (NodeToken) n.f0.choice;
			if (token.tokenImage.startsWith("\"")){
				//caso mail
				getLastParticipants().add(token.tokenImage);
			}
			else{
				//caso id
				getLastParticipants().add(people.get(token.tokenImage));
			}
		}

	}

	/**
	 * Location()
	 * 
	 * f0-> at
	 * f1-> Place()
	 */
	@Override
	public void visit(Location n) {
		n.f0.accept(this);
		n.f1.accept(this);

	}

	/**
	 * Place()
	 * 
	 * f0-> < ID > | < STRING >
	 */
	@Override
	public void visit(Place n) {
		n.f0.accept(this);
		if (n.f0.choice instanceof NodeToken){
			NodeToken token = (NodeToken)n.f0.choice;
			if (token.tokenImage.startsWith("\"")){
				setLastLocation(token.tokenImage);
			}
			else{
				setLastLocation(locations.get(token.tokenImage));
			}
		}

	}

	/**
	 * Repeating()
	 * 
	 * f0-> repeating
	 * f1-> RepeatingTime()
	 * f2-> RepeatingStop()
	 */
	@Override
	public void visit(Repeating n) {
		n.f0.accept(this);
		setRepeatingSet(true);
		n.f1.accept(this);
		n.f2.accept(this);

	}

	/**
	 * RepeatingTime()
	 * 
	 * f0-> every
	 * f1-> < INTEGER>
	 * f2-> days
	 */
	@Override
	public void visit(RepeatingTime n) {
		n.f0.accept(this);
		n.f1.accept(this);
		int days = Integer.parseInt(n.f1.tokenImage);
		setRepeatingIntervall(days);
		n.f2.accept(this);

	}

	/**
	 * RepeatingStop()
	 * 
	 * f0-> untill
	 * f1-> DayDate
	 */
	@Override
	public void visit(RepeatingStop n) {
		n.f0.accept(this);
		n.f1.accept(this);
		setBeginningDateSet(false);

	}

}
