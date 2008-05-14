package br.ufrgs.f180.elements;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Canvas;

import com.cloudgarden.resource.SWTResourceManager;

public class GameField implements VisualElement {
	private double scale_x;
	private double scale_y;
	private double width;
	private double height;

	private ArrayList<MovingElement> elements = new ArrayList<MovingElement>(); 
	
	public GameField(Canvas canvas, double width, double height){
		this.width = width;
		this.height = height;
		this.scale_x = ((double) ((FormData) canvas.getLayoutData()).width) / width;
		this.scale_y = ((double) ((FormData) canvas.getLayoutData()).height) / height;
	}

	/**
	 * Makes an element to be part of the Game field. All elements that are part of a game field 
	 * will be painted together with it.
	 * @param e
	 */
	public void addElement(MovingElement e){
		e.setField(this);
		elements.add(e);
	}
	
	@Override
	public void draw(GC gc) {
		Color old = gc.getForeground();
		Color c = SWTResourceManager.getColor(10, 180, 0);
		gc.setForeground(c);
		gc.drawRectangle(realx(0), realy(0), realx(width - 1), realy(height - 1));
		gc.drawLine(realx(width / 2), realy(0), realx(width / 2), realy(height - 1));
		for (MovingElement e : elements) {
			e.draw(gc);
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
		ArrayList<MovingElement> collisors = new ArrayList<MovingElement>(elements);
		for (MovingElement e : elements) {
			e.calculatePosition(timeElapsed);
			e.calculateCollisionWithField();
			collisors.remove(e);
			for (MovingElement e2 : collisors) {
				e.calculateCollision(e2);
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
}
