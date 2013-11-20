package controller;

import java.util.List;

import javax.swing.JMenu;

import view.IDEIView;

/**
 * Interface for Controller
 * @author danielecampogiani
 * @see ScheduleController
 */

public interface IDEIController {

	/**
	 * Used to get a list of separators in the source code (such as \n \t and space)
	 * @return the List of separators
	 */
	public List<String> getSeparators();
	
	/**
	 * Used to get a list of keywords in the source code (to be highlighted)
	 * @return the list of keywords
	 */
	public List<String> getKeywords();
	
	/**
	 * Set the IDEIView associated to the controller
	 * @param view the view associated to the controller
	 */
	public void setView(IDEIView view);
	
	/**
	 * Called whenever the user change the source code
	 * @param source the new source code
	 */
	public void souceChanged(String source);
	
	/**
	 * Used to set menus in the view
	 * @return list of JMenu to be displayed in the view
	 */
	public List<JMenu> getMenus();
}
