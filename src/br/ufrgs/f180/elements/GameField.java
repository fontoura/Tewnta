package br.ufrgs.f180.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;

import br.ufrgs.f180.elements.Wall.CollisionSide;
import br.ufrgs.f180.math.Point;
import br.ufrgs.f180.math.Vector;
import br.ufrgs.f180.resources.GameProperties;

import com.cloudgarden.resource.SWTResourceManager;

/**
 * This object merges the both physical simulation and visual representation of
 * the game field. There are methods responsible for visual display as well as
 * methods responsible for the physical phenomena that guides the simulation.
 * 
 * It is important to notice that visual representation of the logical field
 * uses the following notation: (x, y) = (0, 0): this means the left bottom of
 * the screen. Where x will increase from left to right and y will increase from
 * bottom to top of the screen.
 * 
 * @author Gabriel Detoni
 * 
 */
public class GameField implements VisualElement {
	private static Logger logger = Logger.getLogger(GameField.class);

	/**
	 * Field physical properties. Mostly read from the game properties resource
	 * file.
	 */
	private static final double GRAVITY_ACCELERATION = 10;
	/**
	 * Field physical properties. Mostly read from the game properties resource
	 * file.
	 */
	private static final double FRICTION_COHEFICIENT = (double) GameProperties
			.getDoubleValue("field.frictionCoheficient");;

	/**
	 * Field data to draw the lines. Mostly read from the game properties
	 * resource file.
	 */
	private static final double FIELD_BORDER = (double) (GameProperties
			.getDoubleValue("field.border") * 100);
	/**
	 * Field data to draw the lines. Mostly read from the game properties
	 * resource file.
	 */
	private static final double FIELD_CENTERCIRCLE_RADIUS = (double) (GameProperties
			.getDoubleValue("field.centerCircle.radius") * 100);
	/**
	 * Field data to draw the lines. Mostly read from the game properties
	 * resource file.
	 */
	private static final double FIELD_GOALAREA_RADIUS = (double) (GameProperties
			.getDoubleValue("field.goalArea.radius") * 100);
	/**
	 * Field data to draw the lines. Mostly read from the game properties
	 * resource file.
	 */
	private static final double GOAL_DEPTH = (double) (GameProperties
			.getDoubleValue("goal.depth") * 100);
	/**
	 * Field data to draw the lines. Mostly read from the game properties
	 * resource file.
	 */
	private static final double GOAL_SIZE = (double) (GameProperties
			.getDoubleValue("goal.size") * 100);
	/**
	 * The ball is an element without an ID so this is a constant used to
	 * identify it.
	 */
	public static final String BALL_ELEMENT = "BALL";

	private double width;
	private double height;
	private Canvas canvas;

	/**
	 * Image where the graphic is drawn into
	 */
	private Image paintImage;
	GC paintImageGc;

	/**
	 * Keeps the mouse position within the field. Used for drag and drop
	 * behavior.
	 */
	private Point mousePosition;

	private List<Wall> walls = Collections
			.synchronizedList(new ArrayList<Wall>());
	private Map<String, MovingElement> elements = Collections
			.synchronizedMap(new HashMap<String, MovingElement>());

	/**
	 * @return all moving elements that are present in the field. Mostly robots
	 *         and the ball.
	 */
	public Map<String, MovingElement> getElements() {
		return elements;
	}

	public GameField(Canvas canvas, double width, double height) {
		this.width = width;
		this.height = height;
		this.canvas = canvas;
		paintImage = new Image(canvas.getDisplay(), (int) width, (int) height);
		paintImageGc = new GC(paintImage);

		createWalls();

	}

	/**
	 * Creates the walls that surround the field and prevent elements from
	 * escaping.
	 */
	private void createWalls() {
		try {
			// -
			addWall(new Wall(0, 0, width, 0, CollisionSide.NORMAL));
			addWall(new Wall(0, height, width, height, CollisionSide.REVERSE));

			// |
			addWall(new Wall(0, 0, 0, height, CollisionSide.NORMAL));
			// [
			addWall(new Wall(getLeftBound() - GOAL_DEPTH, getGoalTop(),
					getLeftBound() - GOAL_DEPTH, getGoalDown(),
					CollisionSide.BOTH));
			addWall(new Wall(getLeftBound() - GOAL_DEPTH, getGoalTop(),
					getLeftBound(), getGoalTop(), CollisionSide.BOTH));
			addWall(new Wall(getLeftBound() - GOAL_DEPTH, getGoalDown(),
					getLeftBound(), getGoalDown(), CollisionSide.BOTH));

			// |
			addWall(new Wall(width, 0, width, height, CollisionSide.REVERSE));
			// ]
			addWall(new Wall(getRightBound() + GOAL_DEPTH, getGoalTop(),
					getRightBound() + GOAL_DEPTH, getGoalDown(),
					CollisionSide.BOTH));
			addWall(new Wall(getRightBound(), getGoalTop(), getRightBound()
					+ GOAL_DEPTH, getGoalTop(), CollisionSide.BOTH));
			addWall(new Wall(getRightBound(), getGoalDown(), getRightBound()
					+ GOAL_DEPTH, getGoalDown(), CollisionSide.BOTH));

		} catch (Exception e) {
			logger.error("ERROR: ", e);
			;
		}
	}

	/**
	 * Makes an element to be part of the Game field. All elements that are part
	 * of a game field will be painted together with it.
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void addElement(String id, MovingElement e) throws Exception {
		if (!overlap(id, e)) {
			e.setField(this);
			elements.put(id, e);
		} else {
			throw new Exception(
					"Elements overlap each other. Try another position.");
		}
	}

	/**
	 * Makes a wall to be part of the Game field. All elements that are part of
	 * a game field will be painted together with it.
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void addWall(Wall e) throws Exception {
		e.setField(this);
		walls.add(e);
	}

	/**
	 * Simple check to see if objects are overlapping.
	 * 
	 * @param id
	 *            the element to be tested.
	 * @param otherElement
	 *            the element to be checked for overlap.
	 * @return true if the element identified by 'id' overlaps the otherElement.
	 */
	private boolean overlap(String id, MovingElement otherElement) {
		for (Entry<String, MovingElement> iterable : elements.entrySet()) {
			if (!iterable.getKey().equals(id)
					&& otherElement.collide(iterable.getValue()))
				return true;
		}
		return false;
	}

	/**
	 * Finds an element by its id.
	 * 
	 * @param id
	 * @return the element if found. Null otherwise.
	 */
	public MovingElement getElement(String id) {
		return elements.get(id);
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
		updateImage();
		// Prevent Jigloo mess
		// $hide>>$
		if (canvas != null) {
			Rectangle displayRect = canvas.getClientArea();
			int imageWidth = displayRect.width;
			int imageHeight = displayRect.height;

			// Reflect around the y axis.
			Transform transform = new Transform(gc.getDevice());
	        transform.setElements(1, 0, 0, -1, 0, imageHeight);
	        gc.setTransform(transform);
			gc.drawImage(paintImage, 0, 0, (int) width, (int) height, 0, 0,
					imageWidth, imageHeight);
			transform.dispose();
		}
		// $hide<<$
	}

	/**
	 * Draws the elements into the buffer image
	 */
	private void updateImage() {

		// Cleanup the previous image by draw the background
		Color bg = SWTResourceManager.getColor(0, 50, 0);
		paintImageGc.setBackground(bg);
		paintImageGc.fillRectangle(paintImage
				.getBounds());

		Color c = SWTResourceManager.getColor(150, 150, 150);
		paintImageGc.setForeground(c);
		// Draw middle line and middle field
		paintImageGc.drawLine((int) getCenterX(), (int) getDownBound(),
				(int) (getCenterX()), (int) (getTopBound()));
		paintImageGc.drawOval((int) (getCenterX() - FIELD_CENTERCIRCLE_RADIUS),
				(int) (getCenterY() - FIELD_CENTERCIRCLE_RADIUS),
				(int) (FIELD_CENTERCIRCLE_RADIUS * 2),
				(int) (FIELD_CENTERCIRCLE_RADIUS * 2));

		// Draw borders
		paintImageGc.drawRectangle((int) (getLeftBound()), (int) (getDownBound()),
				(int) (getFieldWidth()), (int) (getFieldHeight()));

		// Draw goal areas
		paintImageGc.drawArc((int) (getLeftBound() - FIELD_GOALAREA_RADIUS),
				(int) (getCenterY() - FIELD_GOALAREA_RADIUS),
				(int) (FIELD_GOALAREA_RADIUS * 2),
				(int) (FIELD_GOALAREA_RADIUS * 2), 270, 180);
		paintImageGc.drawArc((int) (getRightBound() - FIELD_GOALAREA_RADIUS),
				(int) (getCenterY() - FIELD_GOALAREA_RADIUS),
				(int) (FIELD_GOALAREA_RADIUS * 2),
				(int) (FIELD_GOALAREA_RADIUS * 2), 90, 180);

		for (Wall wall : walls) {
			wall.draw(paintImageGc);
		}
		for (Entry<String, MovingElement> e : elements.entrySet()) {
			e.getValue().draw(paintImageGc);
		}
	}

	/**
	 * Gets the center of the field.
	 * 
	 * @return horizontal axis center
	 */
	private double getCenterX() {
		return width / 2;
	}

	/**
	 * Gets the center of the field.
	 * 
	 * @return vertical axis center
	 */
	private double getCenterY() {
		return height / 2;
	}

	/**
	 * Returns the vertical position of the goal. As both goals have the same
	 * vertical position, this is a single method for both.
	 * 
	 * @return
	 */
	public double getGoalDown() {
		return (height + GOAL_SIZE) / 2;
	}

	/**
	 * Returns the vertical position of the goal. As both goals have the same
	 * vertical position, this is a single method for both.
	 * 
	 * @return
	 */
	public double getGoalTop() {
		return (height - GOAL_SIZE) / 2;
	}

	/**
	 * @return the logical field width. This value is not subject to the scale.
	 *         It will represent the actual field width regardless of the screen
	 *         size.
	 */
	public double getFieldWidth() {
		return getRightBound() - getLeftBound();
	}

	/**
	 * @return the logical field height. This value is not subject to the scale.
	 *         It will represent the actual field height regardless of the
	 *         screen size.
	 */
	public double getFieldHeight() {
		return getTopBound() - getDownBound();
	}

	/**
	 * This method is the central point of the physical simulation. It will go
	 * elements one by one and calculate its position relative to the amount
	 * time that was elapsed since the last invocation.
	 * 
	 * @param timeElapsed
	 *            the time elapsed since the latest invocation, in millis
	 */
	public void updateElementsState(double timeElapsed) {
		Set<Entry<String, MovingElement>> collisors = new HashSet<Entry<String, MovingElement>>();
		collisors.addAll(elements.entrySet());

		for (Entry<String, MovingElement> e : elements.entrySet()) {
			MovingElement element = e.getValue();
			// Robot has some special behaviors such as kick and dribble
			if (element instanceof Robot) {
				Robot r = (Robot) element;
				r.dribbleBall((Ball) elements.get(BALL_ELEMENT));
				r.kickBall((Ball) elements.get(BALL_ELEMENT));
			}
		}

		for (Entry<String, MovingElement> e : elements.entrySet()) {
			MovingElement element = e.getValue();
			element.calculatePosition(timeElapsed);
		}

		for (Entry<String, MovingElement> e : elements.entrySet()) {
			MovingElement element = e.getValue();
			element.calculateCollisionWithWalls(walls);
			// collisors.remove(e);
			for (Entry<String, MovingElement> e2 : collisors) {
				// Need to see why the collisions only work 180 deg
				if (e2 != e) {
					element.calculateCollision(e2.getValue());
				}
			}
		}
	}

	/**
	 * @return the logical field bound. This value is not subject to the scale.
	 */
	public double getLeftBound() {
		return FIELD_BORDER;
	}

	/**
	 * @return the logical field bound. This value is not subject to the scale.
	 */
	public double getRightBound() {
		return width - FIELD_BORDER;
	}

	/**
	 * @return the logical field bound. This value is not subject to the scale.
	 */
	public double getDownBound() {
		return FIELD_BORDER;
	}

	/**
	 * @return the logical field bound. This value is not subject to the scale.
	 */
	public double getTopBound() {
		return height - FIELD_BORDER;
	}

	/**
	 * This method calculates the friction force between the field and an
	 * element.
	 * 
	 * @param e
	 * @return the friction.
	 */
	public Vector getFriction(MovingElement e) {
		Vector v = new Vector(0, 0);
		if (e.getVelocity().module() > 0) {
			double cos = -e.getVelocity().getCosDirection();
			double sin = -e.getVelocity().getSinDirection();
			double module = Math.abs(e.getMass() * GRAVITY_ACCELERATION
					* FRICTION_COHEFICIENT);
			v = new Vector(module * cos, module * sin);
		}
		return v;
	}

	/**
	 * Updates the relative mouse position.
	 * 
	 * @param x
	 * @param y
	 */
	public void setMousePosition(int x, int y) {
		Rectangle displayRect = canvas.getClientArea();
		int cWidth = displayRect.width;
		int cHeight = displayRect.height;
		double scale_x = cWidth / width;
		double scale_y = cHeight / height;
		double xpos = x / scale_x;
		double ypos = (cHeight - y) / scale_y;
		this.mousePosition = new Point(xpos, ypos);
	}

	public Point getMousePosition() {
		return mousePosition;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void dispose(){
		paintImageGc.dispose();
		paintImage.dispose();
	}
}
