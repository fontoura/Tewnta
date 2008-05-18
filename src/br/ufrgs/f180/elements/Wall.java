package br.ufrgs.f180.elements;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;

import com.cloudgarden.resource.SWTResourceManager;


public class Wall implements VisualElement {

	public enum CollisionSide{
		NORMAL,
		REVERSE;
	};
	
	private GameField field;
	private final double top;
	private final double down;
	private final double left;
	private final double right;
	private final CollisionSide collisionSide; 
	
	public Wall(double left, double top, double right, double down, CollisionSide collisionSide){
		this.top = top;
		this.down = down;
		this.left = left;
		this.right = right;
		this.collisionSide = collisionSide;
	}
	
	@Override
	public void draw(GC gc) {
		Color old = gc.getForeground();
		Color c = SWTResourceManager.getColor(10, 60, 200);
		gc.setForeground(c);
		gc.drawLine(realx(left), realy(top), realx(right), realy(down));
		gc.setForeground(old);
	}

	@Override
	public int realx(double x) {
		return (int)(x * field.getScale_x());
	}

	@Override
	public int realy(double y) {
		return (int)(y * field.getScale_y());
	}

	public double getTop() {
		return top;
	}

	public double getDown() {
		return down;
	}

	public double getLeft() {
		return left;
	}

	public double getRight() {
		return right;
	}

	public GameField getField() {
		return field;
	}

	public void setField(GameField field) {
		this.field = field;
	}

	public CollisionSide getCollisionSide() {
		return collisionSide;
	}

}
