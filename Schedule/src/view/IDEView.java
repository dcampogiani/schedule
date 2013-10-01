package view;

import java.util.ArrayList;

import javax.swing.JTree;

public interface IDEView {

	public void setKeywords(ArrayList<String> keywords);
	public void setSeparators(ArrayList<String> separators);
	public void clearConsole();
	public void appendToConsole(String text);
	public void setTree(JTree tree);
	public void addNewFile(String fileName, String fileContent, String filePath);
	public void openFile();
	public void saveFile();
	public void closeCurrentFile();
}
