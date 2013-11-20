package controller;

import java.util.List;

import javax.swing.JMenu;

import view.IDEIView;

public interface IDEIController {

	public List<String> getSeparators();
	public List<String> getKeywords();
	public void setView(IDEIView view);
	public void souceChanged(String source);
	public List<JMenu> getMenus();
}
