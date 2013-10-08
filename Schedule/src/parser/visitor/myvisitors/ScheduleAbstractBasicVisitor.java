package parser.visitor.myvisitors;

import java.util.HashMap;
import java.util.Iterator;


import parser.syntaxtree.INode;
import parser.syntaxtree.NodeChoice;
import parser.syntaxtree.NodeList;
import parser.syntaxtree.NodeListOptional;
import parser.syntaxtree.NodeOptional;
import parser.syntaxtree.NodeSequence;
import parser.syntaxtree.NodeTCF;
import parser.syntaxtree.NodeToken;
import parser.visitor.IVoidVisitor;

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
	
	protected HashMap<String, String> getPeople(){
		return this.people;
	}
	
	protected HashMap<String, String> getLocations(){
		return this.locations;
	}

	protected boolean isBeginningDateSet(){
		return beginningDateSet;
	}
	
	protected void setBeginningDateSet(boolean v){
		beginningDateSet = v;
	}
	
	protected int getBeginningDay() {
		return beginningDay;
	}

	protected void setBeginningDay(int beginningDay) {
		this.beginningDay = beginningDay;
	}

	protected int getBeginnigMonth() {
		return beginnigMonth;
	}

	protected void setBeginnigMonth(int beginnigMonth) {
		this.beginnigMonth = beginnigMonth;
	}

	protected int getBeginningYear() {
		return beginningYear;
	}

	protected void setBeginningYear(int beginningYear) {
		this.beginningYear = beginningYear;
	}
	
	protected void setFromTimeSet(boolean v){
		fromTimeSet=v;
	}
	
	protected boolean isFromTimeSet(){
		return fromTimeSet;
	}
	
	protected int getFromH() {
		return fromH;
	}

	protected void setFromH(int fromH) {
		this.fromH = fromH;
	}

	protected int getFromM() {
		return fromM;
	}

	protected void setFromM(int fromM) {
		this.fromM = fromM;
	}

	protected int getToH() {
		return toH;
	}

	protected void setToH(int toH) {
		this.toH = toH;
	}

	protected int getToM() {
		return toM;
	}

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
