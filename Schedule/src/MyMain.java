
import controller.IDEIController;
import controller.ScheduleController;
import view.IDEIView;
import view.ScheduleView;


public class MyMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.setProperty("apple.laf.useScreenMenuBar", "true");

		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Schedule");

		
		IDEIController controller = new ScheduleController();
		IDEIView window = new ScheduleView();
		controller.setView(window);
		window.setController(controller);
		
		
	}

}
