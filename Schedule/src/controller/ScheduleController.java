package controller;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultTreeModel;

import parser.ParseException;
import parser.ScheduleParser;
import parser.syntaxtree.Scope;
import parser.visitor.myvisitors.ScheduleASTVisitor;
import parser.visitor.myvisitors.ScheduleIcsVisitor;
import parser.visitor.myvisitors.ScheduleMailVisitor;
import parser.visitor.myvisitors.ScheduleSemanticCheckVisitor;
import parser.visitor.myvisitors.ScheduleWebVisitor;
import view.IDEIView;

/**
 * Implementation of IDEIController for Schedule Programming Language
 * @author danielecampogiani
 * @see IDEIController
 */

public class ScheduleController implements IDEIController {

	private ArrayList<String> separators;
	private ArrayList<String> keywords;
	private IDEIView view;
	boolean parserInit=false;
	@SuppressWarnings("unused")
	private ScheduleParser parser;
	private JFileChooser fileChooser;
	private String username;
	private String password;

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
		keywords.add("until");
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

	/**
	 * Get the FileChooser or create a new one if not existing yet
	 * @return the current filechooser
	 */
	protected JFileChooser getFileChooser(){
		if (fileChooser == null){
			fileChooser = new JFileChooser();
		}
		return fileChooser;
	}

	/**
	 * Used to get a list of keywords in the source code (to be highlighted)
	 * @return the list of keywords
	 */
	@Override
	public List<String> getSeparators() {
		if (separators==null)
			separators = new ArrayList<String>();
		return separators;
	}

	/**
	 * Used to get a list of keywords in the source code (to be highlighted)
	 * @return the list of keywords
	 */
	@Override
	public List<String> getKeywords() {
		if (keywords==null)
			keywords = new ArrayList<String>();
		return keywords;
	}

	/**
	 * Set the IDEIView associated to the controller
	 * @param view the view associated to the controller
	 */
	@Override
	public void setView(IDEIView view) {
		if (view!=null)
			this.view=view;
	}

	/**
	 * Called whenever the user change the source code
	 * Used here to do a syntax and semantic check and show the result on the console
	 * @param source the new source code
	 */
	@Override
	public void souceChanged(String source) {
		view.clearConsole();
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
			ScheduleSemanticCheckVisitor semanticVisitor = new ScheduleSemanticCheckVisitor();
			scope.accept(semanticVisitor);
			view.appendToConsole("Semantic Check:");
			if (semanticVisitor.hasError()){
				view.appendToConsole(semanticVisitor.getOutput());
				view.getTree().setVisible(false);
			}
			else {
				view.appendToConsole("OK");
				ScheduleASTVisitor astVisitor = new ScheduleASTVisitor();
				scope.accept(astVisitor);
				view.getTree().setModel(new DefaultTreeModel(astVisitor.getTree()));
				view.getTree().setVisible(true);
			}
		} catch (ParseException e) {
			view.appendToConsole(e.getMessage() );
		} 
	}

	/**
	 * Used to set menus in the view
	 * In schedule I set an JMenuItem for each visitor
	 * @return list of JMenu to be displayed in the view
	 */
	@Override
	public List<JMenu> getMenus() {

		ArrayList<JMenu> result = new ArrayList<JMenu>();
		JMenu mnRun = new JMenu("Run");
		result.add(mnRun);

		JMenuItem mntmExportAsIcal = new JMenuItem("Export as .ics");
		mntmExportAsIcal.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mntmExportAsIcal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				generateIcall();
			}
		});
		mnRun.add(mntmExportAsIcal);

		JMenuItem mntmSendReminders = new JMenuItem("Send reminders");
		mntmSendReminders.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mntmSendReminders.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendMails();
			}
		});
		mnRun.add(mntmSendReminders);

		JMenuItem mntWeb = new JMenuItem("Create Web View");
		mntWeb.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mntWeb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				generateWebView();

			}
		});
		mnRun.add(mntWeb);

		return result;

	}

	/**
	 * Do a syntax and semantic check and if the source is valid generate the corresponding iCalendar file and save into a file
	 */
	private void generateIcall(){
		view.clearConsole();
		StringReader reader = new StringReader(view.getCurrentSource());
		BufferedReader buff = new BufferedReader(reader);

		if(!parserInit)
			parser= new ScheduleParser(buff);
		else 
			ScheduleParser.ReInit(buff);
		parserInit=true;
		try {
			Scope scope = ScheduleParser.Scope();
			ScheduleSemanticCheckVisitor semanticVisitor = new ScheduleSemanticCheckVisitor();
			scope.accept(semanticVisitor);
			if (semanticVisitor.hasError())
				view.appendToConsole(semanticVisitor.getOutput());

			ScheduleIcsVisitor icsVisitor = new ScheduleIcsVisitor();
			scope.accept(icsVisitor);
			saveToFile(icsVisitor.getOutput(), "iCalendar", "ics");
		} catch (ParseException e) {
			view.appendToConsole(e.getMessage() );
		} 

	}

	/**
	 * Do a syntax and semantic check and if the source is valid sent mails to attenders
	 */
	@SuppressWarnings("deprecation")
	private void sendMails(){
		view.clearConsole();
		StringReader reader = new StringReader(view.getCurrentSource());
		BufferedReader buff = new BufferedReader(reader);

		if(!parserInit)
			parser= new ScheduleParser(buff);
		else 
			ScheduleParser.ReInit(buff);
		parserInit=true;
		try {
			final Scope scope = ScheduleParser.Scope();
			ScheduleSemanticCheckVisitor semanticVisitor = new ScheduleSemanticCheckVisitor();
			scope.accept(semanticVisitor);
			if (semanticVisitor.hasError())
				view.appendToConsole(semanticVisitor.getOutput());
			if (username==null || password == null || username.equals("") || password.equals("")){

				JTextField userField = new JTextField();
				JPasswordField passwordField = new JPasswordField();
				final JComponent[] inputs = new JComponent[] {
						new JLabel("Mail"),
						userField,
						new JLabel("Password"),
						passwordField
				};
				JOptionPane.showMessageDialog(null, inputs, "Gmail Settings", JOptionPane.PLAIN_MESSAGE);

				username=userField.getText();
				password=passwordField.getText();
			}

			final ScheduleMailVisitor mailVisitor = new ScheduleMailVisitor(username, password);
			Thread t = new Thread(){
				@Override
				public void run() {
					scope.accept(mailVisitor);
					if (mailVisitor.hasError())
						view.appendToConsole(mailVisitor.getError());
					else 
						JOptionPane.showMessageDialog(null, "Mails Sent");};
			};
			t.start();

		} catch (ParseException e) {
			view.appendToConsole(e.getMessage() );
		}
	}

	/**
	 * Do a syntax and semantic check and if the source is valid create the corrisponding web view (HTML + CSS)
	 */
	private void generateWebView(){
		view.clearConsole();
		StringReader reader = new StringReader(view.getCurrentSource());
		BufferedReader buff = new BufferedReader(reader);

		if(!parserInit)
			parser= new ScheduleParser(buff);
		else 
			ScheduleParser.ReInit(buff);
		parserInit=true;
		try {
			Scope scope = ScheduleParser.Scope();
			ScheduleSemanticCheckVisitor semanticVisitor = new ScheduleSemanticCheckVisitor();
			scope.accept(semanticVisitor);
			if (semanticVisitor.hasError())
				view.appendToConsole(semanticVisitor.getOutput());

			int year;


			JTextField yearField = new JTextField();
			final JComponent[] inputs = new JComponent[] {
					new JLabel("Choose Year"),
					yearField,
			};
			JOptionPane.showMessageDialog(null, inputs, "Web View Settings", JOptionPane.PLAIN_MESSAGE);

			year = Integer.parseInt(yearField.getText());

			ScheduleWebVisitor webVisitor = new ScheduleWebVisitor(year);
			scope.accept(webVisitor);
			saveToFile(webVisitor.getOutput(), "HTML file", "html");


		} catch (ParseException e) {
			view.appendToConsole(e.getMessage() );
		}
	}

	/**
	 * Utility method to save String content into a file
	 * @param content file content
	 * @param description description of file format
	 * @param extension file extension
	 */
	private void saveToFile(String content, String description, String extension){
		view.saveToFile(content, description, extension);
	}

}
