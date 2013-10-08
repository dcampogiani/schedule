package view;
import java.awt.Component;
import java.awt.Insets;
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

import controller.IDEIController;

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
import java.awt.Font;



public abstract class IDEAbstractView implements IDEIView{

	private JFrame frame;
	private JTextArea console;
	private JTree tree;
	private JTabbedPane tabbedPane;
	private JFileChooser fileChooser;
	protected JMenuBar menuBar;

	protected IDEIController controller;

	/**
	 * Create the application.
	 */
	public IDEAbstractView(IDEIController controller) {
		this.controller=controller;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1500, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setJMenuBar(getMenuBar());

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
		consoleScollPane.setViewportView(getConsole());

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		leftSplitPane.setLeftComponent(tabbedPane);

		JScrollPane treeScrollPane = new JScrollPane();
		mainSplitPane.setRightComponent(treeScrollPane);

		tree = getJTree();
		treeScrollPane.setViewportView(tree);

		frame.setVisible(true);
	}

	protected JFileChooser getFileChooser(){
		if (fileChooser == null){
			fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(getLanguageName()+" source code", getFileExtension());
			fileChooser.setFileFilter(filter);

		}
		return fileChooser;
	}

	protected JTree getJTree(){
		if (tree==null)
			tree = new JTree();
			tree.setVisible(false);
		return tree;
	}

	protected JTextArea getConsole(){
		if (console==null){
			console = new JTextArea();
			console.setWrapStyleWord(true);
			console.setToolTipText("Console");
			console.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
			console.setEditable(false);
			console.setForeground(Color.WHITE);
			console.setBackground(Color.DARK_GRAY);
			console.setMargin( new Insets(5,10,0,10));
		}
		return console;
	}

	protected JMenuBar getMenuBar(){
		if (menuBar==null){
			menuBar = new JMenuBar();


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

			for (JMenu current : controller.getMenus() )
				menuBar.add(current);

		}
		return menuBar;
	}

	protected abstract String getLanguageName();

	protected  abstract String getFileExtension();

	private void currentTextChanged(String text){
		controller.souceChanged(text);
	}

	public void setController(IDEIController controller){
		if (controller!=null)
			this.controller=controller;
	}

	public JTree getTree(){
		return getJTree();
	}
	
	public void clearConsole(){
		console.setText("");
	}

	public void appendToConsole(String text){
		console.append(text+System.getProperty("line.separator"));
	}

	protected void addNewFile(String fileName, String fileContent, String filePath){
		JScrollPane scrollPane = new JScrollPane();
		if ( fileName==null || fileName.equals(""))
			fileName = "New File";
		tabbedPane.addTab(fileName, scrollPane);
		if ( fileContent==null || fileContent.equals(""))
			fileContent = "";
		if (filePath==null || filePath.equals(""))
			filePath = "";

		DCEditorTextPane editor = new DCEditorTextPane(fileContent,filePath,controller.getKeywords(),controller.getSeparators());
		editor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				DCEditorTextPane editor = (DCEditorTextPane)arg0.getComponent();
				String text = editor.getText();
				currentTextChanged(text);
			}
		});
		scrollPane.setViewportView(editor);

		tabbedPane.setSelectedComponent(scrollPane);
		editor.requestFocus();
		currentTextChanged(editor.getText());
	}

	protected void openFile(){

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

	protected void saveFile(){

		FileNameExtensionFilter filter = new FileNameExtensionFilter(getLanguageName()+" source code", getFileExtension());
		getFileChooser().setFileFilter(filter);

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
				if (!path.endsWith(getFileExtension()))
					path+="."+getFileExtension();
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
				e.printStackTrace();
				appendToConsole("Errore nel salvataggio del file:");
				appendToConsole(e.toString());
			}
		}
	}

	protected void closeCurrentFile(){
		tabbedPane.remove(tabbedPane.getSelectedComponent());
	}

	public String getCurrentSource(){

		Component component = tabbedPane.getSelectedComponent();
		if (component==null)
			return null;
		JScrollPane scroll = (JScrollPane)component;
		JViewport viewport = (JViewport) scroll.getViewport();
		DCEditorTextPane editor = (DCEditorTextPane)viewport.getView();
		return editor.getText();

	}

	public void saveToFile(String content, String description, String extension){
		Boolean aborted = false;
		File file = null;
		FileNameExtensionFilter filter = new FileNameExtensionFilter(description, extension);
		getFileChooser().setFileFilter(filter);

		int returnValue = getFileChooser().showSaveDialog(frame);
		aborted = true;
		if (returnValue == JFileChooser.APPROVE_OPTION){
			String path = getFileChooser().getSelectedFile().getAbsolutePath();
			if (!path.endsWith(extension))
				path+="."+extension;
			file = new File(path);
			aborted = false;

		}

		if (aborted)
			return;

		int response = JOptionPane.OK_OPTION;
		if (file.exists())
			response = JOptionPane.showConfirmDialog(null,"Confirm Overwrite?","Existing file",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
		if (response!= JOptionPane.CANCEL_OPTION){
			try {
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
				out.print(content);
				out.flush();
				out.close();
				appendToConsole("Salvato "+ file.getName()+ " in "+ file.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
				appendToConsole("Errore nel salvataggio del file:");
				appendToConsole(e.toString());
			}
		}




	}


}