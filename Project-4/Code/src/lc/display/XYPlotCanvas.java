package lc.display;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 * An XYPlotCanvas plots a set of X,Y points where
 * both X and Y are between 0 and 1 and the display is
 * scaled to fill the window.
 */
public class XYPlotCanvas extends JComponent {
	
	private static final long serialVersionUID = 1L;

	public XYPlotCanvas() {
		super();
		setPreferredSize(new Dimension(640, 480));
	}
	
	protected int lastx, lasty;
	
	public void addPoint(double x, double y) {
		int xi = (int)(x * getWidth());
		int yi = (int)((1.0-y) * getHeight());
		getGraphics().drawLine(lastx, lasty, xi, yi);
		lastx = xi;
		lasty = yi;
	}
	
	@Override
	public void paintComponent(Graphics g) {
	}
	
}
