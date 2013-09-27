package view;

import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DCEditorTextPane extends JTextPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String path;
	private ArrayList<String> keywords;

	public ArrayList<String> getKeywords(){
		if(keywords==null)
			keywords=new ArrayList<String>();
		return keywords;
	}

	public void setKeywords(ArrayList<String> keywords){
		if (keywords!=null)
			this.keywords=keywords;
		highlight();
	}

	public DCEditorTextPane(){
		super();
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				highlight();
			}
		});
	}

	public DCEditorTextPane(String text,String path, ArrayList<String> keywords){
		super();
		if (text!=null)
			setText(text);
		if (path!=null)
			this.path=path;
		if (keywords!=null)
			this.keywords=keywords;
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				highlight();
			}
		});

	}

	public String getFilePath(){
		return path;
	}

	public void setFilePath(String path){
		if (path!=null)
			this.path = path;
	}

	private void highlight(){

		Highlighter h = getHighlighter();
		h.removeAllHighlights();

		for (String keyword: getKeywords()){
			int pos = 0;
			int begin=0;
			while ( (begin = getText().indexOf(keyword, pos))>=0){
				try {
						h.addHighlight(begin, begin+keyword.length(), new DefaultHighlighter.DefaultHighlightPainter(Color.ORANGE) );
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pos+=keyword.length();
			}
		}



	}


}
