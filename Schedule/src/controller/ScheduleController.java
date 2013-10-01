package controller;

import java.util.ArrayList;

import view.IDEIView;

public class ScheduleController implements IDEIController {
	
	private ArrayList<String> separators;
	private ArrayList<String> keywords;
	private IDEIView view;
	
	public ScheduleController(){
		
		this.keywords = new ArrayList<String>();
		keywords.add("timeZone");
		keywords.add("on");
		keywords.add("allDay");
		keywords.add("from");
		keywords.add("to");
		keywords.add("do");
		keywords.add("with");
		keywords.add("at");
		keywords.add("repeating");
		keywords.add("every");
		keywords.add("days");
		keywords.add("untill");
		keywords.add("person");
		keywords.add("location");
		keywords.add("weekly");
		keywords.add("monthly");
		keywords.add("yearly");
		
		
		this.separators = new ArrayList<String>();
		separators.add(" ");
		separators.add(System.getProperty("line.separator"));
		separators.add("\t");
		
	}
	
	@Override
	public ArrayList<String> getSeparators() {
		// TODO Auto-generated method stub
		if (separators==null)
			separators = new ArrayList<String>();
		return separators;
	}

	@Override
	public ArrayList<String> getKeywords() {
		// TODO Auto-generated method stub
		if (keywords==null)
			keywords = new ArrayList<String>();
		return keywords;
	}


	@Override
	public void setView(IDEIView view) {
		// TODO Auto-generated method stub
		if (view!=null)
			this.view=view;
	}


	public void souceChanged(String source) {
		view.appendToConsole("DA CONTROLLER: "+source);
		
	}

}
