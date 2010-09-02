package br.ufrgs.f180.gui;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import br.ufrgs.f180.elements.GameField;
import br.ufrgs.f180.elements.MovingElement;

import com.cloudgarden.resource.SWTResourceManager;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class TrailAnalyserWidget extends org.eclipse.swt.widgets.Composite
		{

	{
		// Register as a resource user - SWTResourceManager will
		// handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Group group1;
	private Canvas canvasTrail;
	private Group group2;

	/**
	 * Instance of the game field that whose trail will be plotted
	 */
	private GameField fieldInstance;
	
	/**
	 * Image where the graphic is drawn into 
	 */
	private Image paintImage;

	/**
	 * Visual element constants
	 */
	private double width;
	private double height;

	/**
	 * Auto-generated main method to display this
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void main(String[] args) {
		showGUI(null);
	}

	/**
	 * Overriding checkSubclass allows this class to extend
	 * org.eclipse.swt.widgets.Composite
	 */
	protected void checkSubclass() {
	}

	/**
	 * Auto-generated method to display this org.eclipse.swt.widgets.Composite
	 * inside a new Shell.
	 */
	public static void showGUI(GameField fieldInstance) {
		final Display display = Display.getDefault();
		final Shell shell = new Shell(display);
		final TrailAnalyserWidget inst = new TrailAnalyserWidget(shell,
				SWT.NULL);

		// Prevent Jigloo mess
		// $hide>>$
		//set up the scale
		inst.fieldInstance = fieldInstance;
		inst.updateProportions(fieldInstance);
		inst.paintImage = new Image(display, (int)inst.width, (int)inst.height);
		// $hide<<$
		
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if (size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();

		// Prevent Jigloo mess
		// $hide>>$
		display.timerExec(0, new Runnable() {
			@Override
			public void run() {
				if (!shell.isDisposed()) {
					inst.repaintLoop();
					display.timerExec(inst.getUpdateInterval(), this);
				}
			}
		});
		// $hide<<$

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * The interval between updates. Configurable in the options GUI.
	 * 
	 * @return
	 */
	protected int getUpdateInterval() {
		return 200;
	}

	/**
	 * Redraw the canvas.
	 */
	protected void repaintLoop() {
		updateTrail();
		if (canvasTrail != null) {
			canvasTrail.redraw();
		}
	}

	private void updateTrail() {
		// Prevent Jigloo mess
		// $hide>>$

		if(paintImage != null) {
			GC gc = new GC(paintImage);
			Color c = SWTResourceManager.getColor(150, 150, 150);
			gc.setForeground(c);

			// Draw borders
			gc.drawRectangle((int)fieldInstance.getLeftBound(),
					(int)fieldInstance.getDownBound(), (int)fieldInstance
							.getFieldWidth(), (int)fieldInstance
							.getFieldHeight());

			Map<String, MovingElement> elements = fieldInstance.getElements();
			for (Entry<String, MovingElement> e : elements.entrySet()) {
				drawMovingElement(gc, e.getValue());
			}
			gc.dispose(); 
		}
		// $hide<<$
	}

	public TrailAnalyserWidget(org.eclipse.swt.widgets.Composite parent,
			int style) {
		super(parent, style);
		initGUI();
	}

	private void initGUI() {
		try {
			this.setLayout(new FormLayout());
			{
				group2 = new Group(this, SWT.NONE);
				FormLayout group2Layout = new FormLayout();
				group2.setLayout(group2Layout);
				FormData group2LData = new FormData();
				group2LData.width = 421;
				group2LData.height = 242;
				group2LData.left = new FormAttachment(0, 1000, 0);
				group2LData.top = new FormAttachment(0, 1000, 58);
				group2LData.bottom = new FormAttachment(1000, 1000, 0);
				group2LData.right = new FormAttachment(1000, 1000, 0);
				group2.setLayoutData(group2LData);
				group2.setText("Trail");
				{
					canvasTrail = new Canvas(group2, SWT.DOUBLE_BUFFERED
							| SWT.NO_BACKGROUND | SWT.NO_REDRAW_RESIZE);
					FormData canvasTrailLData = new FormData();
					canvasTrailLData.width = 410;
					canvasTrailLData.height = 233;
					canvasTrailLData.top = new FormAttachment(0, 1000, 5);
					canvasTrailLData.left = new FormAttachment(0, 1000, 5);
					canvasTrailLData.right = new FormAttachment(1000, 1000, -6);
					canvasTrailLData.bottom = new FormAttachment(1000, 1000, -4);
					canvasTrail.setLayoutData(canvasTrailLData);
					canvasTrail.setBackground(SWTResourceManager.getColor(0, 0,
							0));
					canvasTrail.addControlListener(new ControlAdapter() {
						public void controlResized(ControlEvent evt) {
							updateProportions(fieldInstance);
						}
					});
					canvasTrail.addPaintListener(new PaintListener() {
						public void paintControl(PaintEvent evt) {
							if (fieldInstance != null)
								TrailAnalyserWidget.this.draw(evt.gc);
						}
					});
				}
			}
			{
				group1 = new Group(this, SWT.NONE);
				GridLayout group1Layout = new GridLayout();
				group1Layout.makeColumnsEqualWidth = true;
				group1.setLayout(group1Layout);
				FormData group1LData = new FormData();
				group1LData.width = 861;
				group1LData.height = 42;
				group1LData.left =  new FormAttachment(0, 1000, 0);
				group1LData.top =  new FormAttachment(0, 1000, 4);
				group1LData.right =  new FormAttachment(1000, 1000, 0);
				group1.setLayoutData(group1LData);
				group1.setText("Options");
			}
			this.layout();
			pack();
			this.setSize(410, 316);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(GC gc) {
		// Prevent Jigloo mess
		// $hide>>$
		Rectangle displayRect = canvasTrail.getClientArea();
	    int imageWidth = displayRect.width;
	    int imageHeight = displayRect.height;

	    // Reflect around the y axis.
		Transform transform = new Transform(gc.getDevice());
        transform.setElements(1, 0, 0, -1, 0, imageHeight);
        gc.setTransform(transform);
	    gc.drawImage(paintImage, 0, 0, (int)width, (int)height, 0, 0, imageWidth, imageHeight);
		transform.dispose();

		// $hide<<$
	}

	
	private void drawMovingElement(GC gc, MovingElement e) {
		Color old = gc.getForeground();
		Color c = SWTResourceManager.getColor(255, 128, 0);
		gc.setBackground(c);
		double radius = 15d;
		gc.fillOval((int)(e.getPosition().getX() - radius), (int)(e.getPosition().getY() - radius) , (int)(radius * 2), (int)(radius * 2) );
		gc.setBackground(old);
	}

	/**
	 * Window resizes require the scales to be adjusted. This method is
	 * responsible for that.
	 * 
	 * @param canvas
	 *            the container of this game field.
	 */
	public void updateProportions(GameField field) {
		if (field != null) {
			this.width = field.getWidth();
			this.height = field.getHeight();
		} else {
			this.width = 1;
			this.height = 1;
		}
	}
}
