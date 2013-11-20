package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Subclass of JTextPane, this component highlight keywords as soon the user write them  
 * @author danielecampogiani
 * @see JTextPane
 */

public class DCEditorTextPane extends JTextPane {

	private static final long serialVersionUID = 1L;
	private String path;
	private List<String> keywords;
	private List<String> separators;

	public DCEditorTextPane(){
		super();
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				highlight();
			}
		});
	}

	public DCEditorTextPane(String text,String path, List<String> keywords,List<String> separators){
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


	/**
	 * Get the list of keywords to be highlighted
	 * @return list of keywords
	 */
	public List<String> getKeywords(){
		if(keywords==null)
			keywords=new ArrayList<String>();
		return keywords;
	}

	/**
	 * Set the keywords to be highlighted
	 * @param keywords list of keywords
	 */
	public void setKeywords(List<String> keywords){
		if (keywords!=null)
			this.keywords=keywords;
		highlight();
	}

	/**
	 * Get the list of separators
	 * @return list of separators
	 */
	public List<String> getSeparators(){
		if(separators==null)
			separators=new ArrayList<String>();
		return separators;
	}

	/**
	 * Set the separators
	 * @param separators list of separators to be set
	 */
	public void setSeparators(List<String> separators){
		if (separators!=null)
			this.separators=separators;
		highlight();
	}
	
	/**
	 * Get the path of the file
	 * @return the path of the file
	 */
	public String getFilePath(){
		return path;
	}

	/**
	 * Set the path of the file
	 * @param path the path of the file
	 */
	public void setFilePath(String path){
		if (path!=null)
			this.path = path;
	}

	/**
	 * Highlight all the keywords! (Bug on windows)
	 */
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

	/**
	 * Check if a keywords has valid separators right before and after itself
	 * @param begin index
	 * @param word the word
	 * @return boolean value
	 */
	private boolean isClear(int begin,String word){

		boolean before = (begin == 0 || getSeparators().contains(""+getText().charAt(begin-1))) ;

		boolean after =  getText().endsWith(word) || separators.contains(""+getText().charAt(begin+word.length()));

		return (before && after);

	}

}
