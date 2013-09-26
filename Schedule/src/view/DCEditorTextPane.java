package view;

import javax.swing.JTextPane;

public class DCEditorTextPane extends JTextPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String path;
	
	public DCEditorTextPane(){
		super();
	}
	
	public DCEditorTextPane(String text,String path){
		super();
		if (text!=null)
			setText(text);
		if (path!=null)
			this.path=path;
		
	}

	public String getFilePath(){
		return path;
	}
	
	public void setFilePath(String path){
		if (path!=null)
			this.path = path;
	}
}
