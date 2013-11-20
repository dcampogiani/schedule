package view;

import javax.swing.JTree;

import controller.IDEIController;

/**
 * Interface for View
 * @author danielecampogiani
 * @see IDEAbstractView ScheduleView
 */

public interface IDEIView {

	/**
	 * Clear the console
	 */
	public void clearConsole();
	
	/**
	 * Append text to console
	 * @param text the text you want to append
	 */
	public void appendToConsole(String text);
	
	/**
	 * Set the controller for the view
	 * @param controller the controller of the view
	 * @see IDEIController
	 */
	public void setController(IDEIController controller);
	
	/**
	 * Get the content of the file the user is working on
	 * @return the current source code
	 */
	public String getCurrentSource();
	
	/**
	 * Utility method to save String content into a file
	 * @param content file content
	 * @param description description of file format
	 * @param extension file extension
	 */
	public void saveToFile(String content, String description, String extension);
	
	/**
	 * Return the Tree shown in the view
	 * @return the JTree
	 */
	public JTree getTree();
}
