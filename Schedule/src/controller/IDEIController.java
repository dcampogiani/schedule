package controller;

import java.util.ArrayList;

import javax.swing.JMenu;

import view.IDEIView;

public interface IDEIController {

	public ArrayList<String> getSeparators();
	public ArrayList<String> getKeywords();
	public void setView(IDEIView view);
	public void souceChanged(String source);
	public ArrayList<JMenu> getMenus();
}
