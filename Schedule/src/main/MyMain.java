package main;
import controller.IDEIController;
import controller.ScheduleController;

import view.IDEIView;
import view.ScheduleView;

/**
 * Main Class for Schedule
 * @author danielecampogiani
 *
 */

public class MyMain {

	public static void main(String[] args) {

		System.setProperty("apple.laf.useScreenMenuBar", "true");

		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Schedule");

		
		IDEIController controller = new ScheduleController();
		IDEIView window = new ScheduleView(controller);
		controller.setView(window);

		
	}

}
