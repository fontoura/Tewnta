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

import com.cloudgarden.resource.SWTResourceManager;

/**
 * 
 * @author Gabriel Detoni
 *
 */
public class GameField implements VisualElement {
	private static final double GRAVITY_ACCELERATION = 10;
	private static final int GOAL_DEPTH = 20;
	private static final int GOAL_SIZE = 100;
	public static final String BALL_ELEMENT = "BALL";
	private final double friction_coefficient;
	private double scale_x;
	private double scale_y;
	private double width;
	private double height;
	
	private List<Wall> walls = new ArrayList<Wall>();
	private Map<String, MovingElement> elements = new HashMap<String, MovingElement>(); 
	
	public GameField(Canvas canvas, double width, double height){
		this.width = width;
		this.height = height;
		this.scale_x = ((double) ((FormData) canvas.getLayoutData()).width) / width;
		this.scale_y = ((double) ((FormData) canvas.getLayoutData()).height) / height;
		this.friction_coefficient = 10;
		createWalls();
	}

	private void createWalls() {
		try {
			// -
			addWall(new Wall(getLeftBound(), getTopBound(), getRightBound(), getTopBound(), CollisionSide.NORMAL));
			addWall(new Wall(getLeftBound(), getDownBound(), getRightBound(), getDownBound(), CollisionSide.REVERSE));
			// |
			addWall(new Wall(getLeftBound(), getTopBound(), getLeftBound(), getGoalTop(), CollisionSide.NORMAL));
			addWall(new Wall(getLeftBound(), getGoalDown(), getLeftBound(), getDownBound(), CollisionSide.NORMAL));
			// [
			addWall(new Wall(getLeftBound() - GOAL_DEPTH, getGoalTop(), getLeftBound() - GOAL_DEPTH, getGoalDown(), CollisionSide.NORMAL));
			addWall(new Wall(getLeftBound() - GOAL_DEPTH, getGoalTop(), getLeftBound(), getGoalTop(), CollisionSide.NORMAL));
			addWall(new Wall(getLeftBound() - GOAL_DEPTH, getGoalDown(), getLeftBound(), getGoalDown(), CollisionSide.REVERSE));
			
			// |
			addWall(new Wall(getRightBound(), getTopBound(), getRightBound(), getGoalTop(), CollisionSide.REVERSE));
			addWall(new Wall(getRightBound(), getGoalDown(), getRightBound(), getDownBound(), CollisionSide.REVERSE));
			// ]
			addWall(new Wall(getRightBound() + GOAL_DEPTH, getGoalTop(), getRightBound() + GOAL_DEPTH, getGoalDown(), CollisionSide.REVERSE));
			addWall(new Wall(getRightBound() + GOAL_DEPTH, getGoalTop(), getRightBound(), getGoalTop(), CollisionSide.NORMAL));
			addWall(new Wall(getRightBound() + GOAL_DEPTH, getGoalDown(), getRightBound(), getGoalDown(), CollisionSide.REVERSE));
			
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
		Color c = SWTResourceManager.getColor(10, 160, 0);
		gc.setForeground(c);
		gc.drawLine(realx(width  / 2), realy(0), realx(width / 2), realy(height));
		for (Wall wall : walls) {
			wall.draw(gc);
		}
		for (Entry<String, MovingElement> e : elements.entrySet()) {
			e.getValue().draw(gc);
		}
		gc.setForeground(old);
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
		return GOAL_DEPTH;
	}

	public double getRightBound() {
		return width - 1 - GOAL_DEPTH;
	}

	public double getTopBound() {
		return 0;
	}

	public double getDownBound() {
		return height - 1;
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
