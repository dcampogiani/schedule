import java.util.ArrayList;

import controller.IDEIController;
import controller.ScheduleController;
import view.IDEIView;
import view.ScheduleView;


public class MyMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.setProperty("apple.laf.useScreenMenuBar", "true");

		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Schedule");

		ArrayList<String> separators = new ArrayList<String>();
		separators.add(" ");
		separators.add(System.getProperty("line.separator"));
		separators.add("\t");
		separators.add("(");
		separators.add(")");
		separators.add("{");
		separators.add("}");
		
		ArrayList<String> keywords = new ArrayList<String>();
		keywords.add("for");
		keywords.add("int");
		keywords.add("var");
		
		IDEIController controller = new ScheduleController(separators, keywords);
		IDEIView window = new ScheduleView();
		controller.setView(window);
		window.setController(controller);
		
		
	}

}
