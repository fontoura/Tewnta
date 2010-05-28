package br.ufrgs.wumpus.elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

import br.ufrgs.f180.elements.MovingElement;
import br.ufrgs.f180.math.Point;

import com.cloudgarden.resource.SWTResourceManager;

/**
 * This is the Wumpus game field.
 * 
 * @author Gabriel Detoni
 * 
 */
public class GameField implements VisualElement {
	private static Logger logger = Logger.getLogger(GameField.class);

	public static final int FIELD_SIZE = 12;

	private Cell[][] cells;

	private int canvasHeight;
	private int canvasWidth;
	private double scale_x;
	private double scale_y;

	enum ValidAngle {
		FRONT, DOWN, BACK, UP;

		public static ValidAngle rotateLeft(ValidAngle v) {
			ValidAngle[] va = ValidAngle.values();
			for (int j = 0; j < va.length; j++) {
				if (va[j].equals(v)) {
					int pos = j - 1 >= 0 ? j - 1 : va.length - 1;
					return va[pos];
				}
			}
			return null;
		}

		public static ValidAngle rotateRight(ValidAngle v) {
			ValidAngle[] va = ValidAngle.values();
			for (int j = 0; j < va.length; j++) {
				if (va[j].equals(v)) {
					int pos = j + 1 < va.length ? j + 1 : 0;
					return va[pos];
				}
			}
			return null;
		}
	}

	private ValidAngle warriorAngle = ValidAngle.UP;

	/**
	 * Keeps the mouse position within the field. Used for drag and drop
	 * behavior.
	 */
	private Point mousePosition;
	private boolean displayHiddenCells;

	private Map<String, MovingElement> elements = Collections
			.synchronizedMap(new HashMap<String, MovingElement>());

	/**
	 * @return all moving elements that are present in the field. Mostly robots
	 *         and the ball.
	 */
	public Map<String, MovingElement> getElements() {
		return elements;
	}

	public GameField(Canvas canvas) {
		updateProportions(canvas);
		createField();
	}

	/**
	 * Window resizes require the scales to be adjusted. This method is
	 * responsible for that.
	 * 
	 * @param canvas
	 *            the container of this game field.
	 */
	public void updateProportions(Canvas canvas) {
		this.canvasHeight = canvas.getBounds().height - 1;
		this.canvasWidth = canvas.getBounds().width - 1;
		this.scale_x = ((double) canvasWidth) / FIELD_SIZE;
		this.scale_y = ((double) canvasHeight) / FIELD_SIZE;
	}

	/**
	 * Creates the game field.
	 */
	private void createField() {
		try {
			cells = new Cell[FIELD_SIZE][FIELD_SIZE];
			for (int i = 0; i < FIELD_SIZE; i++) {
				for (int j = 0; j < FIELD_SIZE; j++) {
					cells[i][j] = new Cell();
				}
			}

			for (int i = 0; i < (FIELD_SIZE * FIELD_SIZE / 4); i++) {
				addPit();
			}
			addWumpus();
			addTreasure();
			cells[0][0].setWarrior(true);
		} catch (Exception e) {
			logger.error("ERROR: ", e);
			;
		}
	}

	/**
	 * Add the treasure at a random position different than 0,0.
	 */
	private void addTreasure() {
		int i = 0;
		int j = 0;
		while (i + j == 0 || cells[i][j].isPit()) {
			i = new Random().nextInt(FIELD_SIZE);
			j = new Random().nextInt(FIELD_SIZE);
		}
		cells[i][j].setGold(true);
	}

	/**
	 * Add wumpus at a random position different than 0,0.
	 */
	private void addWumpus() {
		int i = 0;
		int j = 0;
		while (i + j == 0 || cells[i][j].isPit()) {
			i = new Random().nextInt(FIELD_SIZE);
			j = new Random().nextInt(FIELD_SIZE);
		}
		cells[i][j].setWumpus(true);
	}

	/**
	 * Add a pit in the field at a random position different than 0,0.
	 */
	private void addPit() {
		int i = 0;
		int j = 0;
		while (i + j == 0) {
			i = new Random().nextInt(FIELD_SIZE);
			j = new Random().nextInt(FIELD_SIZE);
		}
		cells[i][j].setPit(true);
	}

	/**
	 * This method is responsible for drawing everything inside the field. It
	 * delegates to the elements to draw themselves.
	 * 
	 * This method is invoked in a fixed interval and it is not bound to the
	 * physical simulation that is expected to occur much more frequently.
	 */
	@Override
	public void draw(GC gc) {
		if (cells != null) {
			for (int i = 0; i < FIELD_SIZE; i++) {
				for (int j = 0; j < FIELD_SIZE; j++) {
					Cell c = getCell(i, j);
					if (displayHiddenCells || c.isWarrior()) {
						Rectangle r = new Rectangle((int) (scale_x * i),
								(int) (canvasHeight - (scale_y * (j + 1))),
								(int) scale_x + 1, (int) scale_y + 1);
						gc.setForeground(gc.getDevice().getSystemColor(
								SWT.COLOR_BLACK));
						gc.setBackground(SWTResourceManager.getColor(133, 126,
								108));
						gc.fillRectangle(r);
						gc.drawRectangle(r);

						CellPerception p = getCellPerception(i, j);
						if (p.isSmell()) {
							drawSmell(i, j, gc);
						}
						if (p.isFreshAir()) {
							drawFreshAir(i, j, gc);
						}

						if (c.isGold()) {
							drawGold(i, j, gc);
						}
						if (c.isPit()) {
							drawPit(i, j, gc);
						}
						if (c.isWarrior()) {
							drawWarrior(i, j, gc);
						}
						if (c.isWumpus()) {
							drawWumpus(i, j, gc);
						}
					}
				}
			}
		}

	}

	private void drawFreshAir(int i, int j, GC gc) {
		Rectangle r = new Rectangle((int) (scale_x * i) + (int) (scale_x + 1)
				/ 2, (int) (canvasHeight - (scale_y * (j + 1))) + 1,
				(int) (scale_x + 1) / 2, (int) (scale_y + 1) / 2);
		gc.setBackground(SWTResourceManager.getColor(103, 102, 80));
		gc.fillRectangle(r);
	}

	private void drawSmell(int i, int j, GC gc) {
		Rectangle r = new Rectangle((int) (scale_x * i) + 1,
				(int) (canvasHeight - (scale_y * (j + 1))) + 1,
				(int) (scale_x + 1) / 2, (int) (scale_y + 1) / 2);

		gc.setBackground(SWTResourceManager.getColor(255, 126, 108));
		gc.fillRectangle(r);
	}

	private void drawWumpus(int i, int j, GC gc) {
		InputStream s = ClassLoader
				.getSystemResourceAsStream("br/ufrgs/wumpus/elements/wumpus.bmp");
		Image im = new Image(gc.getDevice(), s);
		Rectangle r = new Rectangle((int) (scale_x * i),
				(int) (canvasHeight - (scale_y * (j + 1))), (int) scale_x + 1,
				(int) scale_y + 1);
		gc.drawImage(im, 0, 0, 90, 90, r.x + 1, r.y + 1, r.width - 1,
				r.height - 1);
		im.dispose();
		try {
			s.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}

	private void drawWarrior(int i, int j, GC gc) {
		String resourceName = "br/ufrgs/wumpus/elements/warrior";
		switch (warriorAngle) {
		case FRONT:
			resourceName += "F";
			break;
		case BACK:
			resourceName += "B";
			break;
		case DOWN:
			resourceName += "D";
			break;
		}
		resourceName += ".gif";
		InputStream s = ClassLoader
		.getSystemResourceAsStream(resourceName);
		Image im = new Image(gc.getDevice(), s);;
		
		Rectangle r = new Rectangle((int) (scale_x * i),
				(int) (canvasHeight - (scale_y * (j + 1))), (int) scale_x + 1,
				(int) scale_y + 1);
		gc.drawImage(im, 0, 0, 31, 31, r.x + 1, r.y + 1, r.width - 1,
				r.height - 1);
		im.dispose();
		try {
			s.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}

	private void drawPit(int i, int j, GC gc) {
		InputStream s = ClassLoader
				.getSystemResourceAsStream("br/ufrgs/wumpus/elements/pit.bmp");
		Image im = new Image(gc.getDevice(), s);
		Rectangle r = new Rectangle((int) (scale_x * i),
				(int) (canvasHeight - (scale_y * (j + 1))), (int) scale_x + 1,
				(int) scale_y + 1);
		gc.drawImage(im, 0, 0, 96, 96, r.x + 1, r.y + 1, r.width - 1,
				r.height - 1);
		im.dispose();
		try {
			s.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}

	private void drawGold(int i, int j, GC gc) {
		Rectangle r = new Rectangle((int) (scale_x * i) + (int) (scale_x + 1)
				/ 2, (int) (canvasHeight - (scale_y * (j + 1)))
				+ (int) (scale_y + 1) / 2, (int) (scale_x + 1) / 2,
				(int) (scale_y + 1) / 2);
		gc.setBackground(SWTResourceManager.getColor(255, 255, 0));
		gc.fillRectangle(r);
	}

	/**
	 * @return the horizontal proportion of the field relative to the canvas
	 *         where it is being drawn. This is used to draw elements correctly.
	 */
	@Override
	public int scalex(double x) {
		return (int) (x * scale_x);
	}

	/**
	 * @return the vertical proportion of the field relative to the canvas where
	 *         it is being drawn. This is used to draw elements correctly.
	 */
	@Override
	public int scaley(double y) {
		return (int) (y * scale_y);
	}

	/**
	 * This method translates a logical position in the field to a real position
	 * in the screen.
	 * 
	 * @return the screen position. This is used to draw elements correctly.
	 */
	@Override
	public int realx(double x) {
		return (int) (x * scale_x);
	}

	/**
	 * This method translates a logical position in the field to a real position
	 * in the screen.
	 * 
	 * @return the screen position. This is used to draw elements correctly.
	 */
	@Override
	public int realy(double y) {
		return canvasHeight - (int) (y * scale_y);
	}

	/**
	 * Updates the relative mouse position.
	 * 
	 * @param x
	 * @param y
	 */
	public void setMousePosition(int x, int y) {
		double xpos = x / scale_x;
		double ypos = (canvasHeight - y) / scale_y;
		// if(xpos <= getLeftBound()) xpos = getLeftBound();
		// if(xpos >= getRightBound()) xpos = getRightBound();
		// if(ypos <= getTopBound()) ypos = getTopBound();
		// if(ypos >= getDownBound()) ypos = getDownBound();
		this.mousePosition = new Point(xpos, ypos);
	}

	public Point getMousePosition() {
		return mousePosition;
	}

	public CellPerception getCellPerception(int i, int j) {
		CellPerception p = new CellPerception();
		for (int l = -1; l <= 1; l++) {
			Cell c = getCell(i, j + l);
			// A pit will cause the fresh air perception
			if (c.isPit()) {
				p.setFreshAir(true);
			}
			// Wumpus will cause bad smell
			if (c.isWumpus()) {
				p.setSmell(true);
			}
		}

		for (int l = -1; l <= 1; l++) {
			Cell c = getCell(i + l, j);
			// A pit will cause the fresh air perception
			if (c.isPit()) {
				p.setFreshAir(true);
			}
			// Wumpus will cause bad smell
			if (c.isWumpus()) {
				p.setSmell(true);
			}
		}

		Cell c = getCell(i, j);
		if (c.isGold()) {
			p.setShine(true);
		}
		return p;
	}

	/**
	 * Makes the field infinite.
	 * 
	 * @param k
	 * @param l
	 * @return
	 */
	private Cell getCell(int k, int l) {
		// Fix the -1 values
		if (k < 0)
			k = FIELD_SIZE - 1;
		if (l < 0)
			l = FIELD_SIZE - 1;

		int i = k % FIELD_SIZE;
		int j = l % FIELD_SIZE;
		return cells[i][j];
	}

	public void moveWarriorForward() {
		for (int i = 0; i < FIELD_SIZE; i++) {
			for (int j = 0; j < FIELD_SIZE; j++) {
				Cell c = getCell(i, j);
				if (c.isWarrior()) {
					int i2 = i;
					int j2 = j;
					switch (warriorAngle) {
					case FRONT:
						i2++;
						break;
					case BACK:
						i2--;
						break;
					case DOWN:
						j2--;
						break;
					case UP:
						j2++;
						break;
					}
					c.setWarrior(false);
					getCell(i2, j2).setWarrior(true);
					return;
				}
			}
		}

	}

	public void rotateWarriorLeft() {
		warriorAngle = ValidAngle.rotateLeft(warriorAngle);
	}

	public void rotateWarriorRight() {
		warriorAngle = ValidAngle.rotateRight(warriorAngle);
	}

	public void setDisplayHiddenCells(boolean selection) {
		displayHiddenCells = selection;
	}

}
