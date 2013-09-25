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


public class MainView {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					System.setProperty("apple.laf.useScreenMenuBar", "true");
					
					MainView window = new MainView();
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
	public MainView() {
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
		
		JTextPane textPane = new JTextPane();
		
		JTree tree = new JTree();
		
		JTextArea textArea = new JTextArea();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
						.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tree, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(tree, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
