package view;

import javax.swing.JTree;

import controller.IDEIController;

public interface IDEIView {

	public void clearConsole();
	public void appendToConsole(String text);
	public void setController(IDEIController controller);
	public String getCurrentSource();
	public void saveToFile(String content, String description, String extension);
	public JTree getTree();
}
