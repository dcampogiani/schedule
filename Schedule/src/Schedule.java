import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import javax.swing.BoxLayout;
import javax.swing.JSplitPane;


public class Schedule {

	private JFrame frame;
	private JTextPane editor;
	private JTextArea console;
	private JTree tree;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					System.setProperty("apple.laf.useScreenMenuBar", "true");
					
					System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Schedule");

					
					Schedule window = new Schedule();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Schedule() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		JMenu mnRun = new JMenu("Run");
		menuBar.add(mnRun);
		
		JMenuItem mntmExportAsIcal = new JMenuItem("Export as .ical");
		mnRun.add(mntmExportAsIcal);
		
		JMenuItem mntmSendReminders = new JMenuItem("Send reminders");
		mnRun.add(mntmSendReminders);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		JSplitPane mainSplitPane = new JSplitPane();
		mainSplitPane.setDividerLocation(300);
		mainSplitPane.setResizeWeight(0.75);
		frame.getContentPane().add(mainSplitPane);
		
		JSplitPane leftSplitPane = new JSplitPane();
		leftSplitPane.setDividerLocation(200);
		leftSplitPane.setResizeWeight(0.75);
		leftSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		mainSplitPane.setLeftComponent(leftSplitPane);
		
		JScrollPane editorScrollPane = new JScrollPane();
		leftSplitPane.setLeftComponent(editorScrollPane);
		
		editor = new JTextPane();
		editorScrollPane.setViewportView(editor);
		
		JScrollPane consoleScollPane = new JScrollPane();
		leftSplitPane.setRightComponent(consoleScollPane);
		
		console = new JTextArea();
		consoleScollPane.setViewportView(console);
		
		JScrollPane treeScrollPane = new JScrollPane();
		mainSplitPane.setRightComponent(treeScrollPane);
		
		tree = new JTree();
		treeScrollPane.setViewportView(tree);
	}
	
	public void clearConsole(){
		console.setText("");
	}
	
	public void appendToConsole(String text){
		console.append(text+System.getProperty("line.separator"));
	}
}