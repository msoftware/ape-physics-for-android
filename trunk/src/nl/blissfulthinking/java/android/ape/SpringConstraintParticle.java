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
import android.graphics.Canvas;
	
	public class SpringConstraintParticle extends RectangleParticle {
		
		private final AbstractParticle p1;
		private final AbstractParticle p2;
		
		private static final Vector p1v =  Vector.getNew(0,0);
		private static final Vector p2v =  Vector.getNew(0,0);
		public SpringConstraintParticle(AbstractParticle p1, AbstractParticle p2) {
			super(0.0f,0.0f,0.0f,0.0f,0.0f,false,1.0f,0.0f,0.0f,true);
			this.p1 = p1;
			this.p2 = p2;
			
			mass = getMass();
		}
			
		/**
		 * returns the average mass of the two connected particles
		 */
		public int getMass() {
			return FP.div((p1.mass + p2.mass),FP.TWO);
		}
		
		/**
		 * returns the average velocity of the two connected particles
		 */
		@Override
		protected final void supply_getVelocity(Vector result) {
			
			p1.supply_getVelocity(p1v);
		
			p2.supply_getVelocity(p2v);
			
			result.setTo(FP.div((p1v.x + p2v.x),FP.TWO),FP.div((p1v.y + p2v.y),FP.TWO));
		}	
		
		@Override
		public void drawParticle(Canvas c) {
			updateCornerPositions();
			
			super.drawParticle(c);
		}
		
		
		@Override
		public final void resolveCollision(Vector mtd, Vector vel, Vector n, int d, int o) {
			if (! p1.fixed) {
				p1.curr.plusEquals(mtd);
				p1.setVelocity(vel);
			}
			
			if (! p2.fixed) {
				p2.curr.plusEquals(mtd);
				p2.setVelocity(vel);
			}
		}
	}
