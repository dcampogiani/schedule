package view;

import controller.IDEIController;

public class ScheduleView extends IDEAbstractView {

	public ScheduleView(IDEIController controller) {
		super(controller);
	}

	@Override
	protected String getLanguageName() {
		return "Schedule";
	}

	@Override
	protected String getFileExtension() {
		return "sch";
	}

}
