/**
 * 
 */
package conflictanalyzer.logic;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Guido Marilli
 *
 */
public class CellColorRenderer extends DefaultTableCellRenderer {
	
	private static final long serialVersionUID = 7797671146704826169L;

	/**
	 * 
	 */
	public CellColorRenderer() {
		super();
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, 
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, 
				hasFocus, row, column);
		if(row % 2 == 0)
			cell.setBackground(Color.CYAN);
		/*String color = (String) value;
		System.out.println("Color:" + color);
		if(color.equals("green"))
			cell.setBackground(Color.GREEN);
		else if(color.equals("yellow"))
			cell.setBackground(Color.YELLOW);
		else if(color.equals("white"))
			cell.setBackground(Color.WHITE);*/
		return cell;
	}

}
