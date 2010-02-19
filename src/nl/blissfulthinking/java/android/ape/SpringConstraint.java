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
import nl.blissfulthinking.java.android.apeforandroid.Paints;
import android.graphics.Canvas;
	
	/**
	 * A Spring-like constraint that connects two particles
	 */
	public class SpringConstraint extends AbstractConstraint {
		
		private final AbstractParticle p1;
		private final AbstractParticle p2;
		
		private int restLen;

		private final Vector delta = Vector.getNew(0,0);
		private final Vector center = Vector.getNew(0,0);
		private final Vector dmd = Vector.getNew(0,0);
		private final Vector tmp1 = Vector.getNew(0,0);
		
		private int deltaLength;
		
		private int collisionRectWidth;
		private int collisionRectScale;
		public boolean collidable = false;
		private SpringConstraintParticle collisionRect;
		
		
		/**
		 * @param p1 The first particle this constraint is connected to.
		 * @param p2 The second particle this constraint is connected to.
		 * @param stiffness The strength of the spring. Valid values are between 0 and 1. Lower values
		 * result in softer springs. Higher values result in stiffer, stronger springs.
		 */
		public SpringConstraint(
				AbstractParticle p1, 
				AbstractParticle p2, 
				float stiffness) {
			
			super(stiffness);
			this.p1 = p1;
			this.p2 = p2;
//			checkParticlesLocation();
			
			collisionRectWidth = FP.ONE;
			collisionRectScale = FP.ONE;

			p1.curr.supply_minus(p2.curr,delta);
			deltaLength = p1.curr.distance(p2.curr);
			restLen = deltaLength; 
		}
		
		
		/**
		 * The rotational value created by the positions of the two particles attached to this
		 * SpringConstraint. You can use this property to in your own painting methods, along with the 
		 * center property.
		 */			
		public int getRotation() {
			//MvdA TODO get a good Math.atan2 replacement
			return FP.fromDouble(Math.atan2(FP.toFloat(delta.y),FP.toFloat(delta.x)));
		}
		
		/**
		 * The center position created by the relative positions of the two particles attached to this
		 * SpringConstraint. You can use this property to in your own painting methods, along with the 
		 * rotation property.
		 * 
		 * @returns A Vector representing the center of this SpringConstraint
		 */			
		public final Vector getCenter() {
			p1.curr.supply_plus(p2.curr,center);
			center.divEquals(FP.TWO);
			return center;
		}
		
		/**
		 * If the <code>collidable</code> property is true, you can set the scale of the collidible area
		 * between the two attached particles. Valid values are from 0 to 1. If you set the value to 1, then
		 * the collision area will extend all the way to the two attached particles. Setting the value lower
		 * will result in an collision area that spans a percentage of that distance.
		 */			
		public int getCollisionRectScale() {
			return collisionRectScale;
		}
				
		public void setCollisionRectScale(int scale) {
			collisionRectScale = scale;
		}		
		
		/**
		 * If the <code>collidable</code> property is true, you can set the width of the collidible area
		 * between the two attached particles. Valid values are greater than 0. If you set the value to 10, then
		 * the collision area will be 10 pixels wide. The width is perpendicular to a line connecting the two 
		 * particles
		 */				
		public int getCollisionRectWidth() {
			return collisionRectWidth;
		}
				
		public void setCollisionRectWidth(int w) {
			collisionRectWidth = w;
		}
			
		/**
		 * The <code>restLength</code> property sets the length of SpringConstraint. This value will be
		 * the distance between the two particles unless their position is altered by external forces. The
		 * SpringConstraint will always try to keep the particles this distance apart.
		 */			
		public int getRestLength() {
			return restLen;
		}
		
		public void setRestLength(int r) {
			restLen = r;
		}
	
		/**
		 * Determines if the area between the two particles is tested for collision. If this value is on
		 * you can set the <code>collisionRectScale</code> and <code>collisionRectWidth</code> properties 
		 * to alter the dimensions of the collidable area.
		 */			
		public void setCollidable(boolean b) {
			collidable = b;
			if (collidable) {
				collisionRect = new SpringConstraintParticle(p1, p2);
				orientCollisionRectangle();
			} else {
				collisionRect = null;
			}
		}
		
		/**
		 * Returns true if the passed particle is one of the particles specified in the constructor.
		 */		
		public boolean isConnectedTo(AbstractParticle p) {
			return (p == p1 || p == p2);
		}
		
		@Override
		public void resolve() {
			
			if (p1.fixed & p2.fixed) return;
			
			p1.curr.supply_minus(p2.curr,delta);
			deltaLength = p1.curr.distance(p2.curr);
			if (collidable) { 
				orientCollisionRectangle();
				
			}
			
			int diff = FP.div((deltaLength - restLen),deltaLength);
			
			delta.supply_mult(FP.mul(diff,stiffness),dmd);
	
			int invM1 = p1.invMass;
			int invM2 = p2.invMass;
			int sumInvMass = invM1 + invM2;
			
			// TODO REVIEW TO SEE IF A SINGLE FIXED PARTICLE IS RESOLVED CORRECTLY
			if (! p1.fixed) p1.curr.minusEquals(dmd.supply_mult(FP.div(invM1,sumInvMass),tmp1));
			if (! p2.fixed) p2.curr.plusEquals(dmd.supply_mult(FP.div(invM1,sumInvMass),tmp1));			
		}
			
		public RectangleParticle getCollisionRect() {
			return collisionRect;
		}
	
		private void orientCollisionRectangle() {
			//MvdA TODO ugly
			getCenter();
			int rot = getRotation();			
			
			collisionRect.curr.setTo(center.x,center.y);

			collisionRect.extents[0]=FP.mul(FP.div(deltaLength,FP.TWO), collisionRectScale);
			collisionRect.extents[1]=FP.div(collisionRectWidth,FP.TWO);
			collisionRect.setRotation(rot);
		}

		@Override
		public final void drawConstraint(Canvas c) {
			if (collidable) {
				collisionRect.drawParticle(c);
			} else {
				float X1 = FP.toFloat(p1.curr.x);
				float Y1 = FP.toFloat(p1.curr.y);
				float X2 = FP.toFloat(p2.curr.x);
				float Y2 = FP.toFloat(p2.curr.y);
				c.drawLines(new float[]{X1,Y1,X2,Y2},Paints.rectanglePaint);
			}
		}
	
//		/**
//		 * if the two particles are at the same location warn the user
//		 */
//		private void checkParticlesLocation() {
//			if (p1.curr.x == p2.curr.x && p1.curr.y == p2.curr.y) {
//				throw new Error("The two particles specified for a SpringContraint can't be at the same location");
//			}
//		}
	}
