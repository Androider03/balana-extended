/**
 * 
 */
package conflictanalyzer.support;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.Document;

/**
 * @author Guido Marilli
 * Inserts a text area into a JTable's cell in order to be able to show completely long contents.
 */
public class LargeContentCellRenderer extends JTextArea implements TableCellRenderer {
	
	private static final long serialVersionUID = -1112078091026325351L;

	/**
	 * Default constructor.
	 */
	public LargeContentCellRenderer() {
		setLineWrap(true);
		setWrapStyleWord(true);
	}

	/**
	 * @param text
	 */
	public LargeContentCellRenderer(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param doc
	 */
	public LargeContentCellRenderer(Document doc) {
		super(doc);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param rows
	 * @param columns
	 */
	public LargeContentCellRenderer(int rows, int columns) {
		super(rows, columns);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param text
	 * @param rows
	 * @param columns
	 */
	public LargeContentCellRenderer(String text, int rows, int columns) {
		super(text, rows, columns);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param doc
	 * @param text
	 * @param rows
	 * @param columns
	 */
	public LargeContentCellRenderer(Document doc, String text, int rows, int columns) {
		super(doc, text, rows, columns);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		setText((String) value);  //or something in value, like value.getNote()...
		setSize(table.getColumnModel().getColumn(column).getWidth(),
				getPreferredSize().height);
		if (table.getRowHeight(row) != getPreferredSize().height) {
			table.setRowHeight(row, getPreferredSize().height);
		}
		return this;
	}

}
