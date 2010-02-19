/*
APE (Actionscript Physics Engine) is an AS3 open source 2D physics engine
Copyright 2006, Alec Cove 

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

Contact: nl.blissfulthinking.java.android.ape@cove.org

Converted to Java by Theo Galanakis theo.galanakis@hotmail.com

Optimized for Android by Michiel van den Anker michiel.van.den.anker@gmail.com

*/
package nl.blissfulthinking.java.android.ape;

import nl.blissfulthinking.java.android.apeforandroid.FP;

	
	public final class CollisionDetector {
		
		public static final Vector collisionNormal = Vector.getNew(0,0);
		public static final Vector mult_temp = Vector.getNew(0,0);
		public static final Vector d = Vector.getNew(0,0);
		public static final Vector vertex = Vector.getNew(0,0);
		
		/**
		 * Tests the collision between two objects. If there is a collision it is passed off
		 * to the CollisionResolver class to resolve the collision.
		 */	
		public static final void test(AbstractParticle objA, AbstractParticle objB) {
			
			if (objA.fixed && objB.fixed) return;
		
			//MvdA TODO faster or not?
			if (objA instanceof CircleParticle) {
				if(objB instanceof CircleParticle) {
					testCirclevsCircle((CircleParticle)objA, (CircleParticle)objB);
				}
				else {
					testOBBvsCircle((RectangleParticle)objB, (CircleParticle)objA);
				}
			}
			else {
				if(objB instanceof CircleParticle) {
					testOBBvsCircle((RectangleParticle)objA, (CircleParticle)objB);
				}
				else {
					testOBBvsOBB((RectangleParticle)objA, (RectangleParticle)objB);
				}
			}

//			// circle to circle
//			if (objA instanceof CircleParticle && objB instanceof CircleParticle) {
//				testCirclevsCircle((CircleParticle)objA, (CircleParticle)objB);
//	
//			// rectangle to circle - two ways
//			} else if (objA instanceof RectangleParticle && objB instanceof CircleParticle) {
//				testOBBvsCircle((RectangleParticle)objA, (CircleParticle)objB);
//				
//			} else if (objA instanceof CircleParticle && objB instanceof RectangleParticle)  {
//				testOBBvsCircle((RectangleParticle)objB, (CircleParticle)objA);		
//			}			// rectangle to rectangle
//			else if (objA instanceof RectangleParticle && objB instanceof RectangleParticle) {
//				testOBBvsOBB((RectangleParticle)objA, (RectangleParticle)objB);
//			}
		}
	
		/**
		 * Tests the collision between two RectangleParticles (aka OBBs). If there is a collision it
		 * determines its axis and depth, and then passes it off to the CollisionResolver for handling.
		 */
		private static final void testOBBvsOBB(RectangleParticle ra, RectangleParticle rb) {
			int collisionDepth = FP.MAX_VALUE;
			
			for (int i = 0; i < 2; i++) {
		
				final Vector axisA = ra.axes[i];
				int depthA = testIntervals(ra.getProjection(axisA), rb.getProjection(axisA));
			    if (depthA == 0) {
			    	return;
			    }
				
			    final Vector axisB = rb.axes[i];
			    int depthB = testIntervals(ra.getProjection(axisB), rb.getProjection(axisB));
			    if (depthB == 0) {
			    	return;
			    }
			    
			    int absA = FP.abs(depthA);
			    int absB = FP.abs(depthB);
			    
			    if (absA < FP.abs(collisionDepth) || absB < FP.abs(collisionDepth)) {
			    	if(absA < absB) {
			    		collisionNormal.setTo(axisA.x,axisA.y);
			    		collisionDepth = depthA;
			    	}
			    	else {
			    		collisionNormal.setTo(axisB.x,axisB.y);
			    		collisionDepth = depthB;
			    	}
			    }
			}
			CollisionResolver.resolveParticleParticle(ra, rb, collisionNormal, collisionDepth);
		}		
	
		/**
		 * Tests the collision between a RectangleParticle (aka an OBB) and a CircleParticle. 
		 * If there is a collision it determines its axis and depth, and then passes it off 
		 * to the CollisionResolver for handling.
		 */
		private static final void testOBBvsCircle(RectangleParticle ra, CircleParticle ca) {
			int collisionDepth = FP.MAX_VALUE;

			int depth1;
			int depth2;
			
			Vector boxAxis = ra.axes[0];
			int depth = testIntervals(ra.getProjection(boxAxis), ca.getProjection(boxAxis));
			if (depth == 0) {
				return;
			}
	
			if (FP.abs(depth) < FP.abs(collisionDepth)) {
				collisionNormal.setTo(boxAxis.x,boxAxis.y);
				collisionDepth = depth;
			}
			depth1 = depth;
				
			boxAxis = ra.axes[1];
			depth = testIntervals(ra.getProjection(boxAxis), ca.getProjection(boxAxis));
			if (depth == 0) {
				return;
			}
	
			if (FP.abs(depth) < FP.abs(collisionDepth)) {
				collisionNormal.setTo(boxAxis.x,boxAxis.y);
				collisionDepth = depth;
			}
			depth2 = depth;
			
			// determine if the circle's center is in a vertex region
			int r = ca.radius;
			if (FP.abs(depth1) < r && FP.abs(depth2) < r) {
	
				closestVertexOnOBB(ca.curr, ra);
	
				// get the distance from the closest vertex on rect to circle center
				vertex.supply_minus(ca.curr,collisionNormal);
				
				int mag = collisionNormal.magnitude();
				collisionDepth = r - mag;
	
				if (collisionDepth > 0) {
					// there is a collision in one of the vertex regions
					collisionNormal.divEquals(mag);
				} else {
					// ra is in vertex region, but is not colliding
					return;
				}
			}
			CollisionResolver.resolveParticleParticle(ra, ca, collisionNormal, collisionDepth);
		}
	
		/**
		 * Tests the collision between two CircleParticles. If there is a collision it 
		 * determines its axis and depth, and then passes it off to the CollisionResolver
		 * for handling.
		 */	
		private static final void testCirclevsCircle(CircleParticle ca, CircleParticle cb) {
			int depthX = testIntervals(ca.getIntervalX(), cb.getIntervalX());
			if (depthX == 0) return;
			
			int depthY = testIntervals(ca.getIntervalY(), cb.getIntervalY());
			if (depthY == 0) return;
			
			ca.curr.supply_minus(cb.curr,collisionNormal);
			int mag = collisionNormal.magnitude();
			int collisionDepth = (ca.radius + cb.radius) - mag;
			
			if (collisionDepth > 0) {
				collisionNormal.divEquals(mag);
				CollisionResolver.resolveParticleParticle(ca, cb, collisionNormal, collisionDepth);
			}
		}
	
		/**
		 * Returns 0 if intervals do not overlap. Returns smallest depth if they do.
		 */
		private static final int testIntervals(Interval intervalA, Interval intervalB) {	
			if (intervalA.max < intervalB.min) return 0;
			if (intervalB.max < intervalA.min) return 0;
			
			int lenA = intervalB.max - intervalA.min;
			int lenB = intervalB.min - intervalA.max;
			
			return (FP.abs(lenA) < FP.abs(lenB)) ? lenA : lenB;
		}
		
		/**
		 * Returns the location of the closest vertex on r to point p
		 */
		private static final Vector closestVertexOnOBB(Vector p, RectangleParticle r) {
	
			p.supply_minus(r.curr,d);
			
			vertex.setTo(r.curr.x, r.curr.y);
	
			for (int i = 0; i < 2; i++) {
				int dist = d.dot(r.axes[i]);
	
				if (dist >= 0) dist = r.extents[i];
				else if (dist < 0) dist = -r.extents[i];
	
				vertex.plusEquals(r.axes[i].supply_mult(dist, mult_temp));	
			}
			return vertex;
		}
	}
