package br.ufrgs.f180.math;
/* Copyright (C) 1998 Michael Fowler under Gnu Public License
 * http://www.gnu.org/copyleft/gpl.html
 *
 * Collision.class
 * This is the Scrollbar controlling the time.  It is also the
 * threaded object that computes the current location of the balls.
 * It tells CollisionGraph where the balls are.
 */


public class Collision {

    // The following are static functions that calculate information
	// about collisions.  AngleCalculator uses some of these.
	// Calculate center of mass location
	static public double[] cmx(double x1, double y1, double x2, double y2, double m)
	{
		return(new double[] {(m*x1+x2)/(1+m),(m*y1+y2)/(1+m)});
	}

	// Calculate center of mass velocity
	static public double[] cmv(double vx1, double vy1, double vx2, double vy2, double m)
	{
		return(new double[] {(m*vx1+vx2)/(1+m),(m*vy1+vy2)/(1+m)});
	}

	// This will find the impact parameter if the second mass is stationary.
	static public double dImpactParameter(double x1, double y1, double vx1, double vy1,
									double x2, double y2)
	{
		double m, b;
		if (vx1 == 0.0) return(x2-x1);
		m = vy1/vx1;  b = y1-m*x1;
		double x0 = (x2+m*y2-m*b)/(1+m*m);
		double y0 = m*x0+b;
		return(Math.sqrt(Math.pow(x0-x2,2)+Math.pow(y0-y2,2)));
	}

	// Calculate new velocities after a collision in the lab frame.
	static public double[][] labCollision(double vx1, double vy1, double vx2, double vy2,
									double m, double b)
	{
		double[] vcm = cmv(vx1,vy1,vx2,vy2,m);
		double[] v1 = cmCollision(vx1-vcm[0],vy1-vcm[1],m,b);
		double[] v2 = new double[] {-m*v1[0],-m*v1[1]};
		v1[0] += vcm[0];  v1[1] += vcm[1];
		v2[0] += vcm[0];  v2[1] += vcm[1];
		return( new double[][] { v1,v2 } );
	}

	// given v1, reduced mass, and reduced impact parameter,
	// returns new v1.  No need for v2 b/c v2 = -m v1 always.
	static public double[] cmCollision(double vx1, double vy1, double m, double b)
	{
		// final velocity equals initial velocity
		double vt1 = Math.sqrt(vx1*vx1+vy1*vy1);
		// initial angle is ArcTan(vy1,vx1).
		// final angle is that plus cos phi = 2 sin^2 theta-1
		double dAngleA = Math.atan2(vy1,vx1);
		if (b>0) dAngleA += Math.acos(b*b/2-1.0d);
		else dAngleA -= Math.acos(b*b/2-1.0d);
		return( new double[] { vt1*Math.cos(dAngleA),vt1*Math.sin(dAngleA) } );
	}
}
