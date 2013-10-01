package controller;

import java.util.ArrayList;

import view.IDEIView;

public class ScheduleController implements IDEIController {
	
	private ArrayList<String> separators;
	private ArrayList<String> keywords;
	private IDEIView view;
	
	public ScheduleController(ArrayList<String> separators, ArrayList<String> keywords){
		if (separators!=null)
			this.separators = separators;
		if (keywords!=null)
			this.keywords=keywords;
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
