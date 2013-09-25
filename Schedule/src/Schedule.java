import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.Color;


public class Schedule {

	private JFrame frame;
	private JTextArea console;
	private JTextPane editor;
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
		
		JScrollPane scrollPaneConsole = new JScrollPane();
		
		JScrollPane scrollPaneEditor = new JScrollPane();
		
		JScrollPane scrollPaneTree = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneEditor, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
						.addComponent(scrollPaneConsole, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPaneTree, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPaneTree, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPaneEditor, GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPaneConsole, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);

		tree = new JTree();
		scrollPaneTree.setViewportView(tree);
		
		editor = new JTextPane();
		scrollPaneEditor.setViewportView(editor);
		
		console = new JTextArea();
		console.setForeground(Color.RED);
		console.setEditable(false);
		scrollPaneConsole.setViewportView(console);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	public void clearConsole(){
		console.setText("");
	}
	
	public void appendToConsole(String text){
		console.append(text+System.getProperty("line.separator"));
	}
}