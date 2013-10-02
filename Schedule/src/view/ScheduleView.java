package view;

import controller.IDEIController;

public class ScheduleView extends IDEAbstractView {

	public ScheduleView(IDEIController controller) {
		super(controller);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getLanguageName() {
		// TODO Auto-generated method stub
		return "Schedule";
	}

	@Override
	protected String getFileExtension() {
		// TODO Auto-generated method stub
		return "sch";
	}

}
