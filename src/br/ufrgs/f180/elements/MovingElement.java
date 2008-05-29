package br.ufrgs.f180.elements;

import java.util.List;

import org.eclipse.swt.graphics.GC;

import br.ufrgs.f180.elements.Wall.CollisionSide;
import br.ufrgs.f180.math.Cartesian;
import br.ufrgs.f180.math.Vector;

public abstract class MovingElement implements VisualElement {
	protected GameField field;
	protected Cartesian position;
	protected Vector velocity;
	protected Vector force;
	protected double mass;

	public Cartesian getPosition() {
		return position;
	}

	public void setPosition(Cartesian position) {
		this.position = position;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public Vector getAcceleration() {
		// a = F / m
		Vector v = getField().getFriction(this).sum(force);
		return v.divide(mass);
	}

	public void setForce(Vector force) {
		this.force = force;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public GameField getField() {
		return field;
	}

	public void setField(GameField field) {
		this.field = field;
	}

	/**
	 * Calculates the position of the element after 1ms is passed
	 */
	public void calculatePosition(double timeElapsed) {
		// Calculates the velocity
		velocity.setX(velocity.getX()
				+ (getAcceleration().getX() * timeElapsed));
		velocity.setY(velocity.getY()
				+ (getAcceleration().getY() * timeElapsed));

		// updates the position
		position.setX(position.getX() + velocity.getX() * timeElapsed);
		position.setY(position.getY() + velocity.getY() * timeElapsed);
	}

	public void calculateCollision(MovingElement element) {

		if (this.collide(element)) {
			// Collision detected.. distributing the velocity

			// X
			double m1 = this.getMass();
			Vector v1 = this.getVelocity();
			double m2 = element.getMass();
			Vector v2 = element.getVelocity();
			Vector[] fv1 = calculateFinalVelocity(m1, m2, v1, v2, position, element.position);
			this.setVelocity(fv1[0]);
			element.setVelocity(fv1[1]);

			//FIXME: This is a workaround to prevent objects of being glued to each other 
			calculatePosition(0.005);
			element.calculatePosition(0.005);		
		}
	}

	private Vector[] calculateFinalVelocity(double m1, double m2, Vector v1,
			Vector v2, Cartesian p1, Cartesian p2) {
		double x1 = p1.getX();
		double y1 = p1.getY();
		double x2 = p2.getX();
		double y2 = p2.getY();
		double vx1 = v1.getX();
		double vy1 = v1.getY();
		double vx2 = v2.getX();
		double vy2 = v2.getY();

		double m21, dvx2, a, x21, y21, vx21, vy21, fy21, sign;

		m21 = m2 / m1;
		x21 = x2 - x1;
		y21 = y2 - y1;
		vx21 = vx2 - vx1;
		vy21 = vy2 - vy1;

		// *** I have inserted the following statements to avoid a zero divide;
		// (for single precision calculations,
		// 1.0E-12 should be replaced by a larger value). **************

		fy21 = 1.0E-12 * Math.abs(y21);
		if (Math.abs(x21) < fy21) {
			if (x21 < 0) {
				sign = -1;
			} else {
				sign = 1;
			}
			x21 = fy21 * sign;
		}

		// *** update velocities ***
		a = y21 / x21;
		dvx2 = -2 * (vx21 + a * vy21) / ((1 + a * a) * (1 + m21));
		vx2 = vx2 + dvx2;
		vy2 = vy2 + a * dvx2;
		vx1 = vx1 - m21 * dvx2;
		vy1 = vy1 - a * m21 * dvx2;

		return new Vector[] {new Vector(vx1, vy1), new Vector(vx2, vy2)};
	}

	/**
	 * Field collisions simply invert the velocity vector in the axis the
	 * collision occurred
	 * @param walls2 
	 * 
	 * @param field
	 */
	public void calculateCollisionWithWalls(List<Wall> walls) {
		for (Wall wall : walls) {
			//Vertical
			if(wall.getLeft() == wall.getRight() && position.getY() >= wall.getTop() && position.getY() <= wall.getDown()){
				if(CollisionSide.NORMAL.equals(wall.getCollisionSide())){
					if (position.getX() - getRadius() <= wall.getLeft()) {
						velocity.setX(-velocity.getX());

						//FIXME: This is a workaround to prevent objects of being glued to each other 
						calculatePosition(0.01);
					}
				}
				else{
					if (position.getX() + getRadius() >= wall.getRight()) {
						velocity.setX(-velocity.getX());

						//FIXME: This is a workaround to prevent objects of being glued to each other 
						calculatePosition(0.01);
					}
				}
			}
			//horizontal
			if(wall.getTop() == wall.getDown() && position.getX() >= wall.getLeft() && position.getX() <= wall.getRight()){
				if(CollisionSide.NORMAL.equals(wall.getCollisionSide())){
					if (position.getY() - getRadius() <= wall.getTop()) {
						velocity.setY(-velocity.getY());

						//FIXME: This is a workaround to prevent objects of being glued to each other 
						calculatePosition(0.01);
					}
				}
				else{
					if (position.getY() + getRadius() >= wall.getDown()) {
						velocity.setY(-velocity.getY());

						//FIXME: This is a workaround to prevent objects of being glued to each other 
						calculatePosition(0.01);
					}
				}
			}
			//corners
			Cartesian c1 = new Cartesian(wall.getLeft(), wall.getTop());
			Cartesian c2 = new Cartesian(wall.getRight(), wall.getDown());
			Vector vc1 = new Vector(this.position.getX() - c1.getX(), this.position.getY() - c1.getY());
			Vector vc2 = new Vector(this.position.getX() - c2.getX(), this.position.getY() - c2.getY());
			if(vc1.module() <= this.getRadius()){
				velocity = calculateFinalVelocity(this.mass, Double.MAX_VALUE, this.getVelocity(), new Vector(0, 0), this.position, c1)[0];

				//FIXME: This is a workaround to prevent objects of being glued to each other 
				calculatePosition(0.01);
			}
			else if(vc2.module() <= this.getRadius()){
				velocity = calculateFinalVelocity(this.mass, Double.MAX_VALUE, this.getVelocity(), new Vector(0, 0), this.position, c2)[0];

				//FIXME: This is a workaround to prevent objects of being glued to each other 
				calculatePosition(0.01);
			}
		}
	}

	public void rotate(double angle) {
		double rotationMatrix[][] = { { Math.cos(angle), -Math.sin(angle) },
				{ Math.sin(angle), Math.cos(angle) } };

		double vx = (velocity.getX() * rotationMatrix[0][0])
				+ (velocity.getY() * rotationMatrix[0][1]);
		double vy = (velocity.getX() * rotationMatrix[1][0])
				+ (velocity.getY() * rotationMatrix[1][1]);
		velocity = new Vector(vx, vy);

		double fx = (force.getX() * rotationMatrix[0][0])
				+ (force.getY() * rotationMatrix[0][1]);
		double fy = (force.getX() * rotationMatrix[1][0])
				+ (force.getY() * rotationMatrix[1][1]);
		force = new Vector(fx, fy);
	}

	public abstract void draw(GC gc);

	public abstract double getRadius();

	public Vector getForce() {
		return force;
	}

	public boolean collide(MovingElement element) {
		double x1 = this.getPosition().getX();
		double y1 = this.getPosition().getY();
		double x2 = element.getPosition().getX();
		double y2 = element.getPosition().getY();
		double distance = this.getRadius() + element.getRadius();
		Vector vdistance = new Vector(x2 - x1, y2 - y1);

		return vdistance.module() < distance;
	}
}
