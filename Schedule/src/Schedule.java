import java.awt.EventQueue;

import javax.swing.BorderFactory;
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
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


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
		frame.setBounds(100, 100, 1500, 800);
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

		Border emptyBorder = BorderFactory.createEmptyBorder();

		JSplitPane mainSplitPane = new JSplitPane();
		mainSplitPane.setDividerLocation(1200);
		mainSplitPane.setResizeWeight(0.75);
		mainSplitPane.setBorder(emptyBorder);
		frame.getContentPane().add(mainSplitPane);

		JSplitPane leftSplitPane = new JSplitPane();
		leftSplitPane.setDividerLocation(550);
		leftSplitPane.setResizeWeight(0.75);
		leftSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		leftSplitPane.setBorder(emptyBorder);
		mainSplitPane.setLeftComponent(leftSplitPane);

		JScrollPane editorScrollPane = new JScrollPane();
		leftSplitPane.setLeftComponent(editorScrollPane);

		editor = new JTextPane();
		editor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				//appendToConsole("premuto "+arg0.getKeyChar());
			}
		});
		editor.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		editorScrollPane.setViewportView(editor);

		JScrollPane consoleScollPane = new JScrollPane();
		leftSplitPane.setRightComponent(consoleScollPane);

		console = new JTextArea();
		console.setEditable(false);
		console.setForeground(Color.RED);
		console.setBackground(Color.LIGHT_GRAY);
		consoleScollPane.setViewportView(console);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(console, popupMenu);

		JMenuItem mntmClear = new JMenuItem("Clear");
		mntmClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearConsole();
			}
		});
		popupMenu.add(mntmClear);

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

	public void setTree(JTree tree){
		this.tree=tree;
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}