package view;

import java.util.ArrayList;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DCEditorTextPane extends JTextPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String path;
	private ArrayList<String> keywords;
	private ArrayList<String> separators;

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

	public ArrayList<String> getSeparators(){
		if(separators==null)
			separators=new ArrayList<String>();
		return separators;
	}

	public void setSeparators(ArrayList<String> separators){
		if (separators!=null)
			this.separators=separators;
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

	public DCEditorTextPane(String text,String path, ArrayList<String> keywords,ArrayList<String> separators){
		super();
		if (text!=null)
			setText(text);
		if (path!=null)
			this.path=path;
		if (keywords!=null)
			this.keywords=keywords;
		if (separators!=null)
			this.separators=separators;
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				highlight();
			}
		});
		highlight();
		setBackground(Color.WHITE);
		setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
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

					if( isClear(begin, keyword) )
						h.addHighlight(begin, begin+keyword.length(), new DefaultHighlighter.DefaultHighlightPainter(Color.LIGHT_GRAY) );
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				pos+=keyword.length();
			}
		}

	}

	private boolean isClear(int begin,String word){

		boolean before = (begin == 0 || getSeparators().contains(""+getText().charAt(begin-1))) ;

		boolean after =  getText().endsWith(word) || separators.contains(""+getText().charAt(begin+word.length()));

		return (before && after);

	}


}
