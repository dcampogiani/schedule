package parser.visitor.myvisitors;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import parser.syntaxtree.INode;
import parser.syntaxtree.NodeChoice;
import parser.syntaxtree.NodeList;
import parser.syntaxtree.NodeListOptional;
import parser.syntaxtree.NodeOptional;
import parser.syntaxtree.NodeSequence;
import parser.syntaxtree.NodeTCF;
import parser.syntaxtree.NodeToken;
import parser.visitor.IVoidVisitor;

/**
 * Abstract implementation of IVoidVisitor
 * Common visit (such as NodeChoice, NodeList, NodeListOptional, NodeOptional, NodeSequence, NodeTCF and NodeToken) were realized
 * Also this class provides some getters and setters useful for concrete subclasses
 * @author danielecampogiani
 * @see IVoidVisitor ScheduleAbstractAdvancedVisitor ScheduleSemanticCheckVisitor
 */

public abstract class ScheduleAbstractBasicVisitor implements IVoidVisitor {

	private HashMap<String, String> people;
	private HashMap<String, String> locations;
	private boolean beginningDateSet;
	private int beginningDay;
	private int beginnigMonth;
	private int beginningYear;
	private int fromH;
	private int fromM;
	private int toH;
	private int toM;
	private boolean fromTimeSet;


	public ScheduleAbstractBasicVisitor(){
		people = new HashMap<String, String>();
		locations = new HashMap<String, String>();
		beginningDateSet=false;
		fromH=0;
		fromM=0;
		toH=0;
		toM=0;
		fromTimeSet=false;
		beginningDay=0;
		beginnigMonth=0;
		beginningYear=0;
	}

	/**
	 * Get map of people <id,mail>
	 * @return map of people
	 */
	protected Map<String, String> getPeople(){
		return this.people;
	}

	/**
	 * Get map of locations <id,String>
	 * @return map of location
	 */
	protected Map<String, String> getLocations(){
		return this.locations;
	}

	/**
	 * Check if beginningDate is set in the current event
	 * @return boolean value
	 */
	protected boolean isBeginningDateSet(){
		return beginningDateSet;
	}

	/**
	 * Mark beginning date as set in the current event
	 * @param v boolean value
	 */
	protected void setBeginningDateSet(boolean v){
		beginningDateSet = v;
	}

	/**
	 * Get the beginning day of the current event
	 * @return the beginning day of the current event
	 */
	protected int getBeginningDay() {
		return beginningDay;
	}

	/**
	 * Set the beginning day of the current day
	 * @param beginningDay the begging day of the current event
	 */
	protected void setBeginningDay(int beginningDay) {
		this.beginningDay = beginningDay;
	}

	/**
	 * Get the beginning month of the current event
	 * @return the beginning month of the current month
	 */
	protected int getBeginnigMonth() {
		return beginnigMonth;
	}

	/**
	 * Set the beginning month of the current month
	 * @param beginnigMonth the beginning month of the current event
	 */
	protected void setBeginnigMonth(int beginnigMonth) {
		this.beginnigMonth = beginnigMonth;
	}

	/**
	 * Get the beginning year of the current event
	 * @return the beginning year of the current event
	 */
	protected int getBeginningYear() {
		return beginningYear;
	}

	/**
	 * Set the beginning year of the current event
	 * @param beginningYear beginning year of the current event
	 */
	protected void setBeginningYear(int beginningYear) {
		this.beginningYear = beginningYear;
	}

	/**
	 * Mark the FromTime as set, this value is set whenever the user specify the duration in the "from X to Y" way
	 * @param v boolean value
	 */
	protected void setFromTimeSet(boolean v){
		fromTimeSet=v;
	}

	/**
	 * Check if FromTime is set
	 * @return boolean value
	 */
	protected boolean isFromTimeSet(){
		return fromTimeSet;
	}

	/**
	 * Get the beginning time (hours) of the current event
	 * @return the beginning time (hours) of the current event
	 */
	protected int getFromH() {
		return fromH;
	}

	/**
	 * Set the beginning time (hours) of the current event
	 * @param fromH the beginning time (hours) of the current event
	 */
	protected void setFromH(int fromH) {
		this.fromH = fromH;
	}

	/**
	 * Get the beginning time (minutes) of the current event
	 * @return the beginning time (minutes) of the current event
	 */
	protected int getFromM() {
		return fromM;
	}

	/**
	 * Get the beginning time (minutes) of the current event
	 * @param fromM the beginning time (minutes) of the current event
	 */
	protected void setFromM(int fromM) {
		this.fromM = fromM;
	}

	/**
	 * Get the ending time (hours) of the current event
	 * @return the ending time (hours) of the current event
	 */
	protected int getToH() {
		return toH;
	}

	/**
	 * Set the ending time (hours) of the current event
	 * @param toH the ending time (hours) of the current event
	 */
	protected void setToH(int toH) {
		this.toH = toH;
	}

	/**
	 * Get the ending time (minutes) of the current event
	 * @return the ending time (minutes) of the current event
	 */
	protected int getToM() {
		return toM;
	}

	/**
	 * Set the ending time (minutes) of the current event
	 * @param toM the ending time (minutes) of the current event
	 */
	protected void setToM(int toM) {
		this.toM = toM;
	}

	@Override
	public void visit(NodeChoice n) {
		n.accept(this);

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

	}

}
