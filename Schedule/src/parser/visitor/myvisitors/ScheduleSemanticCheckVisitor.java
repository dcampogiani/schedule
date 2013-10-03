package parser.visitor.myvisitors;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import parser.syntaxtree.AllDayDuration;
import parser.syntaxtree.Body;
import parser.syntaxtree.Day;
import parser.syntaxtree.DayDate;
import parser.syntaxtree.Declarations;
import parser.syntaxtree.Doing;
import parser.syntaxtree.Duration;
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

public class ScheduleSemanticCheckVisitor implements IVoidVisitor {

	private boolean error;
	private String output;
	private ArrayList<String> validTimeZones;
	private HashMap<String, String> people;
	private HashMap<String, String> locations;
	private boolean beginningDateSet;
	private Date beginningDate;

	public ScheduleSemanticCheckVisitor(){
		error=false;
		output="";
		validTimeZones = new ArrayList<String>();
		validTimeZones.add("Europe/Rome");
		validTimeZones.add("Europe/London");
		people = new HashMap<String, String>();
		locations = new HashMap<String, String>();
		beginningDateSet=false;
		Calendar calendar = Calendar.getInstance();
		calendar.set(1900, 0, 1);
		beginningDate = calendar.getTime();
	}

	private void setBeginningDateSet(boolean v){
		beginningDateSet = v;
	}

	private boolean isBeginningDateSet(){
		return beginningDateSet;
	}

	private void setBeginningDate(Date date){
		beginningDate=date;
	}

	private Date getBeginningDate(){
		return beginningDate;
	}

	public boolean hasError(){
		return error;
	}

	public String getOutput(){
		return output;
	}

	@Override
	public void visit(NodeChoice n) {
		n.accept(this);

	}

	@Override
	public void visit(NodeList n) {

		for (final Iterator<INode> e = n.elements(); e.hasNext();) {
			if(hasError()) return; 
			else error=false;
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
		// TODO Auto-generated method stub
		n.f0.accept(this); //Declarations
		if (hasError())
			return;
		n.f1.accept(this);
		if (hasError())
			return;
		output="Semantic Check : OK";
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
			if (hasError())
				return;
		}
		n.f1.accept(this); //VariableDeclaration
		if (hasError())
			return;
	}

	/**
	 * TimeZoneDeclaration()
	 * 
	 * f0-> timeZone()
	 * f1-> =
	 * f2-> < TIMEZONE >
	 * @param n
	 */
	@Override
	public void visit(TimeZoneDeclaration n) {
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		if (!validTimeZones.contains(n.f2.tokenImage)){
			error=true;
			output="Invalid TimeZone.";
			return;
		}

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
		if (people.containsKey(n.f1.tokenImage)){
			error=true;
			output = "Person "+ n.f1.tokenImage +" already defined.";
			return;
		}
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
		if (locations.containsKey(n.f1.tokenImage)){
			error = true;
			output = "Location " + n.f1.tokenImage+ " already defined.";
			return;
		}
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
		if (hasError())
			return;
	}

	/**
	 * Day()
	 * 
	 * f0-> on
	 * f1-> DayDate()
	 * f2-> {
	 * f3-> Duration()
	 * f4-> Doing()
	 * f5-> Partecipants()
	 * f6-> Location()
	 * f7-> Repeating()
	 * f8-> }
	 */
	@Override
	public void visit(Day n) {
		n.f0.accept(this);
		setBeginningDateSet(false);
		n.f1.accept(this); //DayDate
		if (hasError())
			return;
		setBeginningDateSet(true);
		n.f2.accept(this);
		n.f3.accept(this); //Duration
		if (hasError())
			return;
		n.f4.accept(this); //Doing
		if (hasError())
			return;
		if (n.f5.present()){ //Partecipants
			Partecipants partecipants = (Partecipants)n.f5.node;
			partecipants.accept(this);
			if (hasError())
				return;
		}
		if (n.f6.present()){//Location
			Location location = (Location)n.f6.node;
			location.accept(this);
			if (hasError())
				return;
		}
		if (n.f7.present()){//Repeating
			Repeating repeating = (Repeating)n.f6.node;
			repeating.accept(this);
			if (hasError())
				return;
		}
		n.f8.accept(this);
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
		// TODO Auto-generated method stub
		n.f0.accept(this);
		int day = Integer.parseInt(n.f0.tokenImage);
		if (day > 31 || day < 1){
			error=true;
			output= day+ "is not a valid day.";
			return;
		}
		n.f1.accept(this);
		n.f2.accept(this);
		int month = Integer.parseInt(n.f2.tokenImage);
		if (month > 12 || month < 1){
			error=true;
			output= month+ "is not a valid month.";
			return;
		}
		n.f3.accept(this);
		n.f4.accept(this);
		int year = Integer.parseInt(n.f4.tokenImage);
		if (year > 3000 || year < 1){
			error=true;
			output= year+ "is not a valid year.";
			return;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month-1, day);
		Date newDate = calendar.getTime();

		if (isBeginningDateSet()){

			if (getBeginningDate().after(newDate) ){
				error = true;
				output=getBeginningDate()+ " is after "+newDate+" .";
				return;
			}
		}

		else {
			setBeginningDate(newDate);
			setBeginningDateSet(true);
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
		// TODO Auto-generated method stub
		n.f0.accept(this);
		n.f1.accept(this);
		if (hasError())
			return;
		n.f2.accept(this);
		n.f3.accept(this);
		if (hasError())
			return;

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
		if (hour>23 || hour<0){
			error=true;
			output=hour+" is not a valid hour.";
			return;
		}
		n.f1.accept(this);
		n.f2.accept(this);
		int minutes = Integer.parseInt(n.f2.tokenImage);
		if (minutes>59 || minutes <0){
			error=true;
			output=minutes+ " are not valid minutes.";
			return;
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
		// TODO Auto-generated method stub
		n.f0.accept(this);
		n.f1.accept(this);
		if (hasError())
			return;
		n.f2.accept(this);
		if (hasError())
			return;

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
		if (hasError())
			return;
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
			}
			else{
				//caso id
				if (!people.containsKey(token.tokenImage)){
					error=true;
					output= token.tokenImage+" is not a defined person.";
					return;
				}
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
		if (hasError())
			return;
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
				//caso stringa
			}
			else{
				//caso id
				if (!locations.containsKey(token.tokenImage)){
					error=true;
					output=token.tokenImage+ " is not a defined location";
					return;
				}
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
		n.f1.accept(this);
		if (hasError())
			return;
		n.f2.accept(this);
		if (hasError())
			return;

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
		if (days<1){
			error=true;
			output="Can't repeat event every "+ days + "days";
		}
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
		if (hasError())
			return;
		setBeginningDateSet(false);
	}

}