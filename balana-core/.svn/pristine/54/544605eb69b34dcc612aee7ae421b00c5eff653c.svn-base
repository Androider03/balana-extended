/**
 * 
 */
package conflictanalyzer.support;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.JFreeChart;

/**
 * @author Guido Marilli
 *
 */
public class DemoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
    private List<JFreeChart> charts;

    /**
     * Creates a <code>DemoPanel</code> object.
     * @param layoutmanager
     */
    public DemoPanel(java.awt.LayoutManager layoutmanager) {
        super(layoutmanager);
        charts = new ArrayList<JFreeChart>();
    }
    
    /**
     * Adds a chart to the <code>DemoPanel</code>.
     * @param jfreechart the chart to add
     */
    public void addChart(JFreeChart jfreechart) {
        charts.add(jfreechart);
    }

    /**
     * @return an array of the chart objects contained in the <code>DemoPanel</code>
     */
    public JFreeChart[] getCharts() {
        int i = charts.size();
        JFreeChart ajfreechart[] = new JFreeChart[i];
        for (int j = 0; j < i; j++)
            ajfreechart[j] = (JFreeChart) charts.get(j);

        return ajfreechart;
    }

}
