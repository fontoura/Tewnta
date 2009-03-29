package br.ufrgs.f180.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Canvas;

import br.ufrgs.f180.elements.Wall.CollisionSide;
import br.ufrgs.f180.math.Vector;
import br.ufrgs.f180.resources.GameProperties;

import com.cloudgarden.resource.SWTResourceManager;

/**
 * 
 * @author Gabriel Detoni
 *
 */
public class GameField implements VisualElement {
	private static final double GRAVITY_ACCELERATION = 10;
	private static final double FIELD_BORDER = (double)(GameProperties.getDoubleValue("field.border") * 100);
	private static final double FIELD_CENTERCIRCLE_RADIUS = (double)(GameProperties.getDoubleValue("field.centerCircle.radius") * 100);
	private static final double FIELD_GOALAREA_RADIUS = (double)(GameProperties.getDoubleValue("field.goalArea.radius") * 100);
	private static final double GOAL_DEPTH = (double)(GameProperties.getDoubleValue("goal.depth") * 100);
	private static final double GOAL_SIZE = (double)(GameProperties.getDoubleValue("goal.size") * 100);
	public static final String BALL_ELEMENT = "BALL";
	private final double friction_coefficient;
	private double scale_x;
	private double scale_y;
	private double width;
	private double height;
	
	private List<Wall> walls = new ArrayList<Wall>();
	private Map<String, MovingElement> elements = new HashMap<String, MovingElement>(); 
	
	public GameField(Canvas canvas, double width, double height){
		this.width = width - 1;
		this.height = height - 1;
		this.scale_x = ((double) ((FormData) canvas.getLayoutData()).width) / width;
		this.scale_y = ((double) ((FormData) canvas.getLayoutData()).height) / height;
		this.friction_coefficient = 10;
		createWalls();
	}

	private void createWalls() {
		try {
			// -
			addWall(new Wall(0, 0, width, 0, CollisionSide.NORMAL));
			addWall(new Wall(0, height, width, height, CollisionSide.REVERSE));
			
			// |
			addWall(new Wall(0, 0, 0, height, CollisionSide.NORMAL));
			// [
			addWall(new Wall(getLeftBound() - GOAL_DEPTH, getGoalTop(), getLeftBound() - GOAL_DEPTH, getGoalDown(), CollisionSide.BOTH));
			addWall(new Wall(getLeftBound() - GOAL_DEPTH, getGoalTop(), getLeftBound(), getGoalTop(), CollisionSide.BOTH));
			addWall(new Wall(getLeftBound() - GOAL_DEPTH, getGoalDown(), getLeftBound(), getGoalDown(), CollisionSide.BOTH));
			
			// |
			addWall(new Wall(width, 0, width, height, CollisionSide.REVERSE));
			// ]
			addWall(new Wall(getRightBound() + GOAL_DEPTH, getGoalTop(), getRightBound() + GOAL_DEPTH, getGoalDown(), CollisionSide.BOTH));
			addWall(new Wall(getRightBound(), getGoalTop(), getRightBound() + GOAL_DEPTH, getGoalTop(), CollisionSide.BOTH));
			addWall(new Wall(getRightBound(), getGoalDown(), getRightBound() + GOAL_DEPTH, getGoalDown(), CollisionSide.BOTH));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Makes an element to be part of the Game field. All elements that are part of a game field 
	 * will be painted together with it.
	 * @param e
	 * @throws Exception 
	 */
	public void addElement(String id, MovingElement e) throws Exception{
		if(!overlap(id, e)){
			e.setField(this);
			elements.put(id, e);
		}
		else {
			throw new Exception("Elements overlap each other. Try another position.");
		}
	}

	/**
	 * Makes a wall to be part of the Game field. All elements that are part of a game field 
	 * will be painted together with it.
	 * @param e
	 * @throws Exception 
	 */
	public void addWall(Wall e) throws Exception{
		e.setField(this);
		walls.add(e);
	}
	
	private boolean overlap(String id, MovingElement e) {
		for (Entry<String, MovingElement> iterable : elements.entrySet()) {
			if(!iterable.getKey().equals(id) && e.collide(iterable.getValue())) return true;
		}
		return false;
	}

	public MovingElement getElement(String id){
		return elements.get(id);
	}

	@Override
	public void draw(GC gc) {
		Color old = gc.getForeground();
		Color c = SWTResourceManager.getColor(150, 150, 150);
		gc.setForeground(c);
		//Draw middle line and middle field
		gc.drawLine(realx(getCenterX()), realy(getTopBound()), realx(getCenterX()), realy(getDownBound()));
		gc.drawOval(realx(getCenterX() - FIELD_CENTERCIRCLE_RADIUS), realy(getCenterY() - FIELD_CENTERCIRCLE_RADIUS), realx(FIELD_CENTERCIRCLE_RADIUS * 2), realy(FIELD_CENTERCIRCLE_RADIUS * 2));

		//Draw borders
		gc.drawRectangle(realx(getLeftBound()), realy(getTopBound()), realx(getFieldWidth()), realy(getFieldHeight()));

		//Draw goal areas
		gc.drawArc(realx(getLeftBound() - FIELD_GOALAREA_RADIUS), realy(getCenterY() - FIELD_GOALAREA_RADIUS), realx(FIELD_GOALAREA_RADIUS * 2), realy(FIELD_GOALAREA_RADIUS * 2), 270, 180);
		gc.drawArc(realx(getRightBound() - FIELD_GOALAREA_RADIUS), realy(getCenterY() - FIELD_GOALAREA_RADIUS), realx(FIELD_GOALAREA_RADIUS * 2), realy(FIELD_GOALAREA_RADIUS * 2), 90, 180);

		for (Wall wall : walls) {
			wall.draw(gc);
		}
		for (Entry<String, MovingElement> e : elements.entrySet()) {
			e.getValue().draw(gc);
		}
		gc.setForeground(old);
	}

	private double getCenterX() {
		return width / 2;
	}

	private double getCenterY() {
		return height / 2;
	}

	public double getGoalDown() {
		return (height + GOAL_SIZE) / 2;
	}

	public double getGoalTop() {
		return (height - GOAL_SIZE) / 2;
	}

	public double getScale_x() {
		return scale_x;
	}

	public double getScale_y() {
		return scale_y;
	}

	public double getFieldWidth() {
		return getRightBound() - getLeftBound();
	}

	public double getFieldHeight() {
		return getDownBound() - getTopBound();
	}

	@Override
	public int realx(double x) {
		return (int)(x * scale_x);
	}

	@Override
	public int realy(double y) {
		return (int)(y * scale_y);
	}

	public void updateElementsState(double timeElapsed){
		Set<Entry<String, MovingElement>> collisors = new HashSet<Entry<String,MovingElement>>();
		collisors.addAll(elements.entrySet());
		
		for (Entry<String, MovingElement> e : elements.entrySet()) {
			MovingElement element = e.getValue();
			element.calculatePosition(timeElapsed);
			element.calculateCollisionWithWalls(walls);
			collisors.remove(e);
			for (Entry<String, MovingElement> e2 : collisors) {
				element.calculateCollision(e2.getValue());
			}
		}
	}

	public double getLeftBound() {
		return FIELD_BORDER;
	}

	public double getRightBound() {
		return width - 1 - FIELD_BORDER;
	}

	public double getTopBound() {
		return FIELD_BORDER;
	}

	public double getDownBound() {
		return height - 1 - FIELD_BORDER;
	}
	
	public int relativeRealy(int y){
		return realy(getDownBound()) - y;	
	}

	public int relativeRealx(int x){
		return realx(getLeftBound() + x);	
	}

	public Vector getFriction(MovingElement e) {
		Vector v = new Vector(0, 0);
		if(e.getVelocity().module() > 0){
			double cos = -e.getVelocity().getCosDirection();
			double sin = -e.getVelocity().getSinDirection();
			double module = Math.abs(e.getMass() * GRAVITY_ACCELERATION * friction_coefficient); 
			v = new Vector(module * cos, module * sin);
		}
		return v;
	}
}
