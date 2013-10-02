package view;

import javax.swing.JTree;

import controller.IDEIController;

public interface IDEIView {

	public void clearConsole();
	public void appendToConsole(String text);
	public void clearTree();
	public void setTree(JTree tree);
	public void addNewFile(String fileName, String fileContent, String filePath);
	public void openFile();
	public void saveFile();
	public void closeCurrentFile();
	public void setController(IDEIController controller);
}
