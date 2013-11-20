package view;

import controller.IDEIController;

/**
 * Concrete Implementation of IDEIView, subclass of IDEAbstractView
 * @author danielecampogiani
 * @see IDEIView IDEAbstractView
 */

public class ScheduleView extends IDEAbstractView {

	public ScheduleView(IDEIController controller) {
		super(controller);
	}

	/**
	 * Language name is "Schedule"
	 * @return "Schedule"
	 */
	@Override
	protected String getLanguageName() {
		return "Schedule";
	}

	/**
	 * File extension is "sch"
	 * @return "sch"
	 */
	@Override
	protected String getFileExtension() {
		return "sch";
	}

}
