package controller;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import parser.ParseException;
import parser.ScheduleParser;
import parser.syntaxtree.Scope;
import view.IDEIView;

public class ScheduleController implements IDEIController {

	private ArrayList<String> separators;
	private ArrayList<String> keywords;
	private IDEIView view;
	boolean parserInit=false;
	private ScheduleParser parser;

	public ScheduleController(){

		this.keywords = new ArrayList<String>();
		keywords.add("timeZone");
		keywords.add("on");
		keywords.add("allDay");
		keywords.add("from");
		keywords.add("to");
		keywords.add("do");
		keywords.add("with");
		keywords.add("at");
		keywords.add("repeating");
		keywords.add("every");
		keywords.add("days");
		keywords.add("untill");
		keywords.add("person");
		keywords.add("location");
		keywords.add("weekly");
		keywords.add("monthly");
		keywords.add("yearly");


		this.separators = new ArrayList<String>();
		separators.add(" ");
		separators.add(System.getProperty("line.separator"));
		separators.add("\t");

	}

	@Override
	public ArrayList<String> getSeparators() {
		// TODO Auto-generated method stub
		if (separators==null)
			separators = new ArrayList<String>();
		return separators;
	}

	@Override
	public ArrayList<String> getKeywords() {
		// TODO Auto-generated method stub
		if (keywords==null)
			keywords = new ArrayList<String>();
		return keywords;
	}

	@Override
	public void setView(IDEIView view) {
		// TODO Auto-generated method stub
		if (view!=null)
			this.view=view;
	}

	public void souceChanged(String source) {
		view.clearConsole();
		//view.appendToConsole("DA CONTROLLER: "+source);
		StringReader reader = new StringReader(source);
		BufferedReader buff= new BufferedReader(reader);
		if(!parserInit)
			parser= new ScheduleParser(buff);
		else 
			ScheduleParser.ReInit(buff);
		parserInit=true;
		try {
			Scope scope = ScheduleParser.Scope();
			view.appendToConsole("Sintassi OK");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			view.appendToConsole(e.getMessage() );
		} 
	}


	public ArrayList<JMenu> getMenus() {
		// TODO Auto-generated method stub

		ArrayList<JMenu> result = new ArrayList<JMenu>();
		JMenu mnRun = new JMenu("Run");
		result.add(mnRun);

		JMenuItem mntmExportAsIcal = new JMenuItem("Export as .ical");
		mntmExportAsIcal.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mntmExportAsIcal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateIcall();
			}
		});

		mnRun.add(mntmExportAsIcal);

		JMenuItem mntmSendReminders = new JMenuItem("Send reminders");
		mntmSendReminders.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mntmSendReminders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendMails();
			}
		});
		mnRun.add(mntmSendReminders);

		return result;

	}

	private void generateIcall(){
	}

	private void sendMails(){
	}
}
