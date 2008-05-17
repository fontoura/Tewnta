package br.ufrgs.f180.elements;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Canvas;

import br.ufrgs.f180.math.Vector;

import com.cloudgarden.resource.SWTResourceManager;

/**
 * 
 * @author Gabriel Detoni
 *
 */
public class GameField implements VisualElement {
	private static final double GRAVITY_ACCELERATION = 10;
	private double scale_x;
	private double scale_y;
	private double width;
	private double height;
	private double friction_coefficient;
	

	private Map<String, MovingElement> elements = new HashMap<String, MovingElement>(); 
	
	public GameField(Canvas canvas, double width, double height){
		this.width = width;
		this.height = height;
		this.scale_x = ((double) ((FormData) canvas.getLayoutData()).width) / width;
		this.scale_y = ((double) ((FormData) canvas.getLayoutData()).height) / height;
		this.friction_coefficient = 10;
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
		Color c = SWTResourceManager.getColor(10, 100, 0);
		gc.setForeground(c);
		gc.drawRectangle(realx(0), realy(0), realx(width - 1), realy(height - 1));
		gc.drawLine(realx(width / 2), realy(0), realx(width / 2), realy(height - 1));
		for (Entry<String, MovingElement> e : elements.entrySet()) {
			e.getValue().draw(gc);
		}
		gc.setForeground(old);
	}

	public double getScale_x() {
		return scale_x;
	}

	public double getScale_y() {
		return scale_y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
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
			element.calculateCollisionWithField();
			collisors.remove(e);
			for (Entry<String, MovingElement> e2 : collisors) {
				element.calculateCollision(e2.getValue());
			}
		}
	}

	public double getLeftBound() {
		return 1;
	}

	public double getRightBound() {
		return width - 1;
	}

	public double getTopBound() {
		return 1;
	}

	public double getDownBound() {
		return height - 1;
	}
	
	public int relativeRealy(int y){
		return realy(getDownBound()) - y;	
	}

	public int relativeRealx(int x){
		return x;	
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
