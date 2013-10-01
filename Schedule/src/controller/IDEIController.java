package controller;

import java.util.ArrayList;

import view.IDEIView;

public interface IDEIController {

	public ArrayList<String> getSeparators();
	public ArrayList<String> getKeywords();
	public void setView(IDEIView view);
	public void souceChanged(String source);
}
