import java.util.ArrayList;

import view.Schedule;


public class MyMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.setProperty("apple.laf.useScreenMenuBar", "true");

		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Schedule");


		Schedule window = new Schedule();

		ArrayList<String> separators = new ArrayList<String>();
		separators.add(" ");
		separators.add(System.getProperty("line.separator"));
		separators.add("\t");
		separators.add("(");
		separators.add(")");
		separators.add("{");
		separators.add("}");
		window.setSeparators(separators);

		ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("for");
		keywords.add("int");
		keywords.add("var");
		window.setKeywords(keywords);
	}

}
