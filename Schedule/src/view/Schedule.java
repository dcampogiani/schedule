package view;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JSplitPane;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;

import javax.swing.JTabbedPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Schedule {

	private JFrame frame;
	private JTextArea console;
	private JTree tree;
	private JTabbedPane tabbedPane;
	private JFileChooser fileChooser;
	private ArrayList<String> keyword;
	private ArrayList<String> separators;

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
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addNewFile(null, null,null);
			}
		});
		mnFile.add(mntmNew);

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});

		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFile();
			}
		});
		mnFile.add(mntmOpen);
		mnFile.add(mntmSave);

		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeCurrentFile();
			}
		});
		mnFile.add(mntmClose);

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

		JScrollPane consoleScollPane = new JScrollPane();
		leftSplitPane.setRightComponent(consoleScollPane);


		console = new JTextArea();
		console.setEditable(false);
		console.setForeground(Color.RED);
		console.setBackground(Color.LIGHT_GRAY);
		consoleScollPane.setViewportView(console);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		leftSplitPane.setLeftComponent(tabbedPane);

		JScrollPane treeScrollPane = new JScrollPane();
		mainSplitPane.setRightComponent(treeScrollPane);

		tree = new JTree();
		treeScrollPane.setViewportView(tree);
		
		frame.setVisible(true);
	}

	private JFileChooser getFileChooser(){
		if (fileChooser == null){
			fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Schedule source code", "sch");
			fileChooser.setFileFilter(filter);

		}
		return fileChooser;
	}

	private ArrayList<String> getKeywords(){
		if (keyword==null)
			keyword = new ArrayList<String>();
		return keyword;
	}

	public void setKeywords(ArrayList<String> keywords){
		if (keywords!=null)
			this.keyword=keywords;
	}

	private ArrayList<String> getSeparators(){
		if (separators==null)
			separators = new ArrayList<String>();
		return separators;
	}

	public void setSeparators(ArrayList<String> separators){
		if (separators!=null)
			this.separators=separators;
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

	public void addNewFile(String fileName, String fileContent, String filePath){
		JScrollPane scrollPane = new JScrollPane();
		if ( fileName==null || fileName.equals(""))
			fileName = "New File";
		tabbedPane.addTab(fileName, scrollPane);
		if ( fileContent==null || fileContent.equals(""))
			fileContent = "";
		if (filePath==null || filePath.equals(""))
			filePath = "";

		DCEditorTextPane editor = new DCEditorTextPane(fileContent,filePath,getKeywords(),getSeparators());
		editor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				currentTextChanged();
			}
		});
		scrollPane.setViewportView(editor);

		tabbedPane.setSelectedComponent(scrollPane);
		editor.requestFocus();
	}

	public void openFile(){
		
		int returnvalue = getFileChooser().showOpenDialog(frame);
		File file = null;
		String fileString = null;
		if (returnvalue == JFileChooser.APPROVE_OPTION){
			file = getFileChooser().getSelectedFile();
			
			for (int i=0;i<tabbedPane.getComponentCount();i++){
				JScrollPane scroll = (JScrollPane)tabbedPane.getComponent(i);
				JViewport viewport = (JViewport) scroll.getViewport();
				DCEditorTextPane editor = (DCEditorTextPane)viewport.getView();
				if (editor.getFilePath().equals(file.getAbsolutePath())){
					tabbedPane.setSelectedIndex(i);
					return;
				}
			}
			
			StringBuffer fileBuffer;
			String line;
			try{
				FileReader in = new FileReader(file);
				BufferedReader dis = new BufferedReader(in);
				fileBuffer = new StringBuffer();
				while ((line = dis.readLine())!= null) {
					fileBuffer.append(line+System.getProperty("line.separator"));
				}
				in.close();
				fileString= fileBuffer.toString();
				addNewFile(file.getName(), fileString, file.getAbsolutePath());
				appendToConsole("Aperto file "+file.getAbsolutePath());
			}catch(IOException e){
				appendToConsole("File not found:");
				appendToConsole(e.toString());
			}
		}

	}

	public void saveFile(){

		JScrollPane scrollPane = (JScrollPane) tabbedPane.getSelectedComponent();
		JViewport viewport = scrollPane.getViewport();
		DCEditorTextPane editor = (DCEditorTextPane)viewport.getView();
		File file = null;
		Boolean aborted = false;

		if (editor.getFilePath()==null || editor.getFilePath().equals("")){
			int returnValue = getFileChooser().showSaveDialog(frame);
			aborted = true;
			if (returnValue == JFileChooser.APPROVE_OPTION){
				String path = getFileChooser().getSelectedFile().getAbsolutePath();
				if (!path.endsWith(".sch"))
					path+=".sch";
				file = new File(path);
				editor.setFilePath(file.getAbsolutePath());
				aborted = false;

			}
		}

		else
			file = new File(editor.getFilePath());

		if (aborted)
			return;

		int response = JOptionPane.OK_OPTION;
		if (file.exists())
			response = JOptionPane.showConfirmDialog(null,"Confirm Overwrite?","Existing file",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
		if (response!= JOptionPane.CANCEL_OPTION){
			try {
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
				out.print(editor.getText());
				out.flush();
				out.close();
				appendToConsole("Salvato "+ file.getName()+ " in "+ file.getAbsolutePath());
				tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), file.getName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				appendToConsole("Errore nel salvataggio del file:");
				appendToConsole(e.toString());
			}
		}
	}

	public void closeCurrentFile(){
		tabbedPane.remove(tabbedPane.getSelectedComponent());
	}

	private void currentTextChanged(){
		
	}
}