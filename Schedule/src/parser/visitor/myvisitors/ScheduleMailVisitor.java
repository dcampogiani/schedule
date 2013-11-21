package parser.visitor.myvisitors;

import java.io.IOException;
import java.net.SocketException;
import java.net.URI;
import java.util.GregorianCalendar;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Recur;
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
import net.fortuna.ical4j.model.property.RRule;
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
import util.MailSender;

/**
 * Send mail to participants and attach iCalendar file
 * @author danielecampogiani
 * @see ScheduleAbstractAdvancedVisitor
 */

public class ScheduleMailVisitor extends ScheduleAbstractAdvancedVisitor {

	private String username;
	private String password;
	private TimeZone timezone;
	private VTimeZone vtTimeZone;
	private boolean error;
	private String output;

	public ScheduleMailVisitor(String username, String password){
		super();
		error =false;
		output ="";
		this.username = username;
		this.password = password;
	}

	/**
	 * Check if there were errors sending mails
	 * @return boolean value
	 */
	public boolean hasError(){
		return error;
	}

	/**
	 * Get the error message
	 * @return error message
	 */
	public String getError(){
		return output;
	}

	/**

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

	}

	/**
	 * Declarations()
	 * 
	 * f0-> TimeZoneDeclaration()
	 * f1-> VariableDeclaration()
	 */
	@Override
	public void visit(Declarations n) {
		n.f0.accept(this);
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

		if (isLocationSet()){
			net.fortuna.ical4j.model.property.Location location = new net.fortuna.ical4j.model.property.Location( getLastLocation());
			event.getProperties().add(location);
		}

		if (isRepeatingSet()){

			java.util.Calendar stopDate = new GregorianCalendar();
			stopDate.set(java.util.Calendar.MONTH, getEndingMonth()-1);
			stopDate.set(java.util.Calendar.DAY_OF_MONTH, getEndingDay());
			stopDate.set(java.util.Calendar.YEAR, getEndingYear());
			stopDate.set(java.util.Calendar.HOUR_OF_DAY, getToH());
			stopDate.set(java.util.Calendar.MINUTE, getToM());	
			stopDate.set(java.util.Calendar.SECOND, 0);

			DateTime stop = new DateTime(stopDate.getTime());

			Recur recur = new Recur(Recur.DAILY,null);
			recur.setUntil(stop);
			recur.setInterval(getRepeatingIntervall());
			RRule rule = new RRule(recur);
			event.getProperties().add(rule);

			setRepeatingSet(false);
		}


		net.fortuna.ical4j.model.Calendar icsCalendar = new net.fortuna.ical4j.model.Calendar();
		icsCalendar.getProperties().add(new ProdId("-//Daniele Campogiani//schedule 1.0//EN"));
		icsCalendar.getProperties().add(Version.VERSION_2_0);
		icsCalendar.getProperties().add(CalScale.GREGORIAN);
		icsCalendar.getComponents().add(event);

		String body = "See details in attachment"+System.getProperty("line.separator")+System.getProperty("line.separator")+ "Sent from Schedule";
		String subject ="Remember: "+getLastDoing()+ " on " + getBeginningDay()+"/"+getBeginnigMonth()+"/"+getBeginningYear();

		try {
			sendMail(username, password, getLastParticipants(), subject, body, "event.ics", icsCalendar.toString());
		} catch (AddressException e) {
			error = true;
			output=e.getMessage();
			e.printStackTrace();
		} catch (MessagingException e) {
			error = true;
			output=e.getMessage();
		} catch (IOException e) {
			error = true;
			output=e.getMessage();
		}

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
	 * f0-> until
	 * f1-> DayDate
	 */
	@Override
	public void visit(RepeatingStop n) {
		n.f0.accept(this);
		n.f1.accept(this);
		setBeginningDateSet(false);

	}

	/**
	 * Send mail according to given arguments
	 * @param username gmail username ex: "name.surname@gmail.com"
	 * @param password gmail password
	 * @param receivers list of receivers as mail ex: "name.surname@mail.com"
	 * @param subject mail subject
	 * @param body mail body
	 * @param attachmentName name of attachment 
	 * @param attachmentContent content of the attachment
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws IOException
	 */
	private void  sendMail(String username, String password, List<String> receivers, String subject, String body, String attachmentName, String attachmentContent) throws AddressException, MessagingException, IOException{

		MailSender.sendMail(username, password, receivers, subject, body, attachmentName, attachmentContent);
	}

}
