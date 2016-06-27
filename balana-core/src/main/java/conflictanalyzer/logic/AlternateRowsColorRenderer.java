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
 * Renderer to alternate table rows' colors.
 */
public class AlternateRowsColorRenderer extends DefaultTableCellRenderer {
	
	private static final long serialVersionUID = 628575524422064840L;
	
	private DefaultTableCellRenderer DEFAULT_RENDERER =  new DefaultTableCellRenderer();
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
    		boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, 
        		hasFocus, row, column);
        if (row%2 == 0)
            c.setBackground(Color.WHITE);
        else c.setBackground(new Color(225, 240, 255));          
        return c;
    }

}
