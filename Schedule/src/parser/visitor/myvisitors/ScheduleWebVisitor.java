package parser.visitor.myvisitors;


import java.util.ArrayList;

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
import util.WebYear;

public class ScheduleWebVisitor extends ScheduleAbstractAdvancedVisitor {
	
	private int year;
	private String output;
	private WebYear webYear;
	
	public ScheduleWebVisitor(int year){
		super();
		this.year=year;
		this.output="";
		this.webYear = new WebYear();
	}

	public String getOutput(){
		return this.output;
	}
	
	private int getYear(){
		return this.year;
	}
	
	private WebYear getWebYear(){
		return this.webYear;
	}
	
	/**
	 * Scope()
	 * 
	 * f0-> Declarations()
	 * f1-> Body()
	 */
	public void visit(Scope n) {
		n.f0.accept(this); //Declarations
		n.f1.accept(this);
		
		output=getWebYear().getWebRappresentation();

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
		String sub = n.f3.tokenImage.substring(1, n.f3.tokenImage.length()-1);

		getPeople().put(n.f1.tokenImage, sub);

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
		String sub = n.f3.tokenImage.substring(1, n.f3.tokenImage.length()-1);
		getLocations().put(n.f1.tokenImage, sub);

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
		n.f0.accept(this);
		n.f1.accept(this); //Duration

		n.f2.accept(this); //Doing

		if (n.f3.present()){ //Partecipants
			Partecipants partecipants = (Partecipants)n.f3.node;
			partecipants.accept(this);

		}
		setLocationSet(false);
		if (n.f4.present()){//Location
			Location location = (Location)n.f4.node;
			location.accept(this);

		}
		if (n.f5.present()){//Repeating
			Repeating repeating = (Repeating)n.f5.node;
			repeating.accept(this);

		}
		n.f6.accept(this);
		
		
		if (getBeginningYear()==getYear()){
			
			int repeatingIntervall=0;
			String location = "";
			
			int newEndingDay = getEndingDay();
			int newEndingMonth = getEndingMonth();
			
			if (isLocationSet())
				location = getLastLocation();
			
			if (isRepeatingSet()){
				repeatingIntervall=getRepeatingIntervall();
				if (getEndingYear()>getBeginningYear()){
					newEndingDay=31;
					newEndingMonth=12; //non suluzione elegante, ma ho scelto di fare view web solo per un singolo anno quindi va bene 
					
				}
			}
			
			ArrayList<String> participantsToPass = new ArrayList<String>(); //necessario perchè poi faccio clear
			
			for (String current: getLastParticipants())
				participantsToPass.add(current);
			
			getWebYear().addEvent(getLastDoing(), getBeginningDay(), getBeginnigMonth(), getFromH(), getFromM(), getToH(), getToM(), participantsToPass, location, newEndingDay, newEndingMonth, repeatingIntervall);
			
			
		}
		
		else if (getEndingYear()==getYear() && isRepeatingSet()){
			String location = "";
			int newBeginnigDay=1;
			int newBeginningMonth=1;
			if (isLocationSet())
				location=getLastLocation();
			
			ArrayList<String> participantsToPass = new ArrayList<String>(); //necessario perchè poi faccio clear
			
			for (String current: getLastParticipants())
				participantsToPass.add(current);
			
			
			getWebYear().addEvent(getLastDoing(), newBeginnigDay, newBeginningMonth, getFromH(), getFromM(), getToH(), getToM(), participantsToPass, location, getEndingDay(), getEndingMonth(), getRepeatingIntervall());
		}

			
			setRepeatingSet(false);
		
		getLastParticipants().clear();
		
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
		
		if (!isBeginningDateSet()){
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
		String sub = n.f1.tokenImage.substring(1, n.f1.tokenImage.length()-1);
		setLastDoing(sub);

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
				String sub = token.tokenImage.substring(1, token.tokenImage.length()-1);
				getLastParticipants().add(sub);
			}
			else{
				//caso id
				getLastParticipants().add(getPeople().get(token.tokenImage));
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
		setLocationSet(true);

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
				String sub = token.tokenImage.substring(1, token.tokenImage.length()-1);
				setLastLocation(sub);
			}
			else{
				setLastLocation(getLocations().get(token.tokenImage));
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
