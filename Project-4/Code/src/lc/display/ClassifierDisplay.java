package lc.display;

import java.awt.Dimension;

import javax.swing.JFrame;

public class ClassifierDisplay extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public ClassifierDisplay(String title) {
		setTitle(title);
		canvas = new XYPlotCanvas();
		canvas.setPreferredSize(new Dimension(640, 480));
		add(canvas);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	protected XYPlotCanvas canvas;
	protected int lastx, lasty;
	
	public void addPoint(double x, double y) {
		int xx = (int)(x * getWidth());
		int yy = (int)((1.0-y) * getHeight());
		canvas.getGraphics().drawLine(lastx, lasty, xx, yy);
		lastx = xx;
		lasty = yy;
	}

}
