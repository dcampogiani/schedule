package parser.visitor.myvisitors;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import parser.syntaxtree.AllDayDuration;
import parser.syntaxtree.Body;
import parser.syntaxtree.Day;
import parser.syntaxtree.DayDate;
import parser.syntaxtree.Declarations;
import parser.syntaxtree.Doing;
import parser.syntaxtree.Duration;
import parser.syntaxtree.Event;
import parser.syntaxtree.FromToDuration;
import parser.syntaxtree.Location;
import parser.syntaxtree.LocationDeclaration;
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

public class ScheduleSemanticCheckVisitor extends ScheduleAbstractBasicVisitor {

	private boolean error;
	private String output;
	private ArrayList<String> validTimeZones;


	public ScheduleSemanticCheckVisitor(){
		super();
		error=false;
		output="";
		validTimeZones = new ArrayList<String>();
		validTimeZones.add("Europe/Rome");
		validTimeZones.add("Europe/London");

	}

	public boolean hasError(){
		return error;
	}

	public String getOutput(){
		return output;
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
		/*if (n.f0.present()){ //TimeZoneDeclaration
			TimeZoneDeclaration tzd = (TimeZoneDeclaration)n.f0.node;
			tzd.accept(this);
			if (hasError())
				return;
		}*/
		n.f0.accept(this);
		if (hasError())
			return;
		n.f1.accept(this); //VariableDeclaration
		if (hasError())
			return;
	}

	/**
	 * TimeZoneDeclaration()
	 * 
	 * f0-> timeZone
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
		if (getPeople().containsKey(n.f1.tokenImage)){
			error=true;
			output = "Person "+ n.f1.tokenImage +" already defined.";
			return;
		}
		n.f2.accept(this);
		n.f3.accept(this);
		getPeople().put(n.f1.tokenImage, n.f3.tokenImage);

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
		if (getLocations().containsKey(n.f1.tokenImage)){
			error = true;
			output = "Location " + n.f1.tokenImage+ " already defined.";
			return;
		}
		n.f2.accept(this);
		n.f3.accept(this);
		getLocations().put(n.f1.tokenImage, n.f3.tokenImage);

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
	 * f2-> :
	 * f3-> Event()
	 */
	@Override
	public void visit(Day n) {
		n.f0.accept(this);
		setBeginningDateSet(false);
		n.f1.accept(this);
		if (hasError())
			return;
		setBeginningDateSet(true);
		n.f2.accept(this);
		n.f3.accept(this);
		if (hasError())
			return;	
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
		n.f0.accept(this);
		

		n.f1.accept(this); //Duration
		if (hasError())
			return;
		n.f2.accept(this); //Doing
		if (hasError())
			return;
		if (n.f3.present()){ //Partecipants
			Partecipants partecipants = (Partecipants)n.f3.node;
			partecipants.accept(this);
			if (hasError())
				return;
		}
		if (n.f4.present()){//Location
			Location location = (Location)n.f4.node;
			location.accept(this);
			if (hasError())
				return;
		}
		if (n.f5.present()){//Repeating
			Repeating repeating = (Repeating)n.f5.node;
			repeating.accept(this);
			if (hasError())
				return;
		}
		n.f6.accept(this);
		
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
		if (day > 31 || day < 1){
			error=true;
			output= day+ " is not a valid day.";
			return;
		}
		n.f1.accept(this);
		n.f2.accept(this);
		int month = Integer.parseInt(n.f2.tokenImage);
		if (month > 12 || month < 1){
			error=true;
			output= month+ " is not a valid month.";
			return;
		}
		n.f3.accept(this);
		n.f4.accept(this);
		int year = Integer.parseInt(n.f4.tokenImage);
		if (year > 3000 || year < 1){
			error=true;
			output= year+ " is not a valid year.";
			return;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month-1, day);
		Date newDate = calendar.getTime();

		if (isBeginningDateSet()){

			Calendar calendar2 = Calendar.getInstance();
			calendar2.set(getBeginningYear(), getBeginnigMonth()-1,getBeginningDay());
			Date beginningDate = calendar2.getTime();
			
			if (beginningDate.after(newDate) ){
				error = true;
				output=beginningDate+ " is after "+newDate+" .";
				return;
			}
		}

		else {
			setBeginningDay(day);
			setBeginnigMonth(month);
			setBeginningYear(year);
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
		setFromTimeSet(false);
		n.f0.accept(this);
		n.f1.accept(this);
		if (hasError())
			return;
		setFromTimeSet(true);
		n.f2.accept(this);
		n.f3.accept(this);
		if (hasError())
			return;
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
		
		if (isFromTimeSet()){
			if ( (getFromH()>hour) || (getFromH()==hour && getFromM()>minutes ) ){
				error=true;
				output=getFromH()+":"+getFromM()+" is after "+hour+":"+minutes+".";
				return;
			}
		}
		
		else{
			setFromH(hour);
			setFromM(minutes);
			setFromTimeSet(true);
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
				if (!getPeople().containsKey(token.tokenImage)){
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
				if (!getLocations().containsKey(token.tokenImage)){
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
			output="Can't repeat event every "+ days + " days";
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
