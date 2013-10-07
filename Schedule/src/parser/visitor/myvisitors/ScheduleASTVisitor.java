package parser.visitor.myvisitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


import javax.swing.tree.DefaultMutableTreeNode;


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

public class ScheduleASTVisitor implements IVoidVisitor {

	private HashMap<String, String> people;
	private HashMap<String, String> locations;
	private DefaultMutableTreeNode tree;
	private int beginningDay;
	private int beginnigMonth;
	private int beginningYear;
	private boolean beginningDateSet;
	private boolean locationSet;
	private ArrayList<String> participants;
	private int fromH;
	private int fromM;
	private boolean fromTimeSet;
	private int toH;
	private int toM;
	private String lastDoing;
	private String lastLocation;
	private boolean repeatingSet;
	private int repeatingIntervall;
	private int endingDay;
	private int endingMonth;
	private int endingYear;

	public ScheduleASTVisitor(){
		beginningDay=0;
		beginnigMonth=0;
		beginningYear=0;
		beginningDateSet=false;
		locationSet=false;
		participants = new ArrayList<String>();
		fromH=0;
		fromM=0;
		fromTimeSet=false;
		lastDoing="";
		lastLocation="";
		repeatingIntervall=0;
		repeatingSet=false;
		endingDay=0;
		endingMonth=0;
		endingYear=0;
		people = new HashMap<String, String>();
		locations = new HashMap<String, String>();
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

	public int getFromH() {
		return fromH;
	}

	public void setFromH(int fromH) {
		this.fromH = fromH;
	}

	public int getFromM() {
		return fromM;
	}

	private void setFromTimeSet(boolean v){
		fromTimeSet=v;
	}

	private boolean isFromTimeSet(){
		return fromTimeSet;
	}

	public void setFromM(int fromM) {
		this.fromM = fromM;
	}

	private ArrayList<String> getLastParticipants() {
		return participants;
	}

	private void setLocationSet(boolean v){
		locationSet = v;
	}

	private boolean isLocationSet(){
		return locationSet;
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

	private void setBeginningDateSet(boolean v){
		beginningDateSet = v;
	}

	private boolean isBeginningDateSet(){
		return beginningDateSet;
	}

	public DefaultMutableTreeNode getTree(){
		return tree;
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

	}

	@Override
	public void visit(NodeTCF n) {

	}

	@Override
	public void visit(NodeToken n) {
		// TODO Auto-generated method stub

	}

	/** Scope()
	 * 
	 * f0-> Declarations()
	 * f1-> Body()
	 */
	@Override
	public void visit(Scope n) {
		n.f0.accept(this); //Declarations
		n.f1.accept(this);

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

		DefaultMutableTreeNode app = null;
		app = new DefaultMutableTreeNode("TimeZone = "+n.f2.tokenImage);
		tree = app;

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

		people.put(n.f1.tokenImage, sub);

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
		locations.put(n.f1.tokenImage, sub);

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

		DefaultMutableTreeNode dayNode= new DefaultMutableTreeNode(getBeginningDay()+"-"+getBeginnigMonth()+"-"+getBeginningYear());

		if (tree==null){//non inizializzato da TimeZone
			tree = dayNode;
			System.out.println(tree);

		}

		else{
			tree.add(dayNode);
			System.out.println(tree);
			System.out.println(dayNode);
		}

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

		DefaultMutableTreeNode eventNode = new DefaultMutableTreeNode("from: "+getFromH()+":"+getFromM()+ " to: "+getToH()+":"+getToM());
		tree.add(eventNode);

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


		eventNode.add(new DefaultMutableTreeNode(getLastDoing()));

		if (getLastParticipants().size()>0){
			DefaultMutableTreeNode participantsNode = new DefaultMutableTreeNode("Participants");
			eventNode.add(participantsNode);
			for (String participant: getLastParticipants()){
				participantsNode.add(new DefaultMutableTreeNode(participant));
			}
		}

		if (isLocationSet()){
			eventNode.add(new DefaultMutableTreeNode("Location: "+getLastLocation()));
		}

		if (isRepeatingSet()){

			DefaultMutableTreeNode repeatingNode = new DefaultMutableTreeNode("Repeating");
			eventNode.add(repeatingNode);

			repeatingNode.add(new DefaultMutableTreeNode("Every "+getRepeatingIntervall()+ " days"));
			repeatingNode.add(new DefaultMutableTreeNode("Untill: "+getEndingDay()+"-"+getEndingMonth()+"-"+getEndingYear()));

			setRepeatingSet(false);
		}



		participants.clear();
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
		setFromTimeSet(true);
		setFromH(0);
		setFromM(0);
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
