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

	
	// NEED TO EXCLUDE VELOCITY CALCS BASED ON collisionResponseMode
	public final class CollisionResolver {
		
		public static final Vector tmp1 = Vector.getNew(0,0); 
		public static final Vector tmp2 = Vector.getNew(0,0); 
		public static final Vector mtd = Vector.getNew(0,0); 
		public static final Vector mtdA = Vector.getNew(0,0); 
		public static final Vector mtdB = Vector.getNew(0,0); 
		public static final Vector vnA = Vector.getNew(0,0); 
		public static final Vector vnB = Vector.getNew(0,0); 
		
		public static final void resolveParticleParticle(
				AbstractParticle pa, 
				AbstractParticle pb, 
				Vector normal, 
				int depth) {
			
			normal.supply_mult(depth,mtd);
			int te = pa.kfr + pb.kfr;
			
			// the total friction in a collision is combined but clamped to [0,1]
			int tf = FP.ONE - (pa.friction + pb.friction);
			if (tf > FP.ONE) tf = FP.ONE;
			else if (tf < 0) tf = 0;
		
			// get the total mass
			int ma = pa.mass;
			int mb = pb.mass;
			int tm = pa.mass + pb.mass;
			//MvdA have assigned a large mass to fixed particles in setFixed(boolean)
			
			// get the collision components, vn and vt
			Collision ca = pa.getComponents(normal);
			Collision cb = pb.getComponents(normal);
		 
		 	// calculate the coefficient of restitution based on the mass
			(cb.vn.supply_mult(FP.mul((te + FP.ONE),mb), tmp1).supply_plus(ca.vn.supply_mult(ma - FP.mul(te,mb), tmp2),vnA)).divEquals(tm);		
			(ca.vn.supply_mult(FP.mul((te + FP.ONE),ma), tmp1).supply_plus(cb.vn.supply_mult(mb - FP.mul(te,ma), tmp2),vnB)).divEquals(tm);
//			final Vector vnA = (cb.vn.supply_mult(FP.mul((te + FP.ONE),mb), tmp1).pool_plus(ca.vn.supply_mult(ma - FP.mul(te,mb), tmp2))).divEquals(tm);		
//			final Vector vnB = (ca.vn.supply_mult(FP.mul((te + FP.ONE),ma), tmp1).pool_plus(cb.vn.supply_mult(mb - FP.mul(te,ma), tmp2))).divEquals(tm);
//			final Vector vnA = (cb.vn.supply_mult(FP.mul((te + one),mb), tmp1).pool_plus(ca.vn.supply_mult(FP.mul(ma - te,mb), tmp2))).divEquals(tm);		
//			final Vector vnB = (ca.vn.supply_mult(FP.mul((te + one),ma), tmp1).pool_plus(cb.vn.supply_mult(FP.mul(mb - te,ma), tmp2))).divEquals(tm);
			
			ca.vt.multEquals(tf);
			cb.vt.multEquals(tf);
			
			// scale the mtd by the ratio of the masses. heavier particles move less
			mtd.supply_mult(FP.div(mb,tm),mtdA);
//			final Vector mtdB = mtd.pool_mult(FP.div(-ma,tm));
//			//TODO test
//			mtd.supply_mult(-FP.div(ma,tm),mtdB);
			mtd.supply_mult(FP.div(-ma,tm),mtdB);
			
			if (! pa.fixed) pa.resolveCollision(mtdA, vnA.plusEquals(ca.vt), normal, depth, -FP.ONE);
			if (! pb.fixed) pb.resolveCollision(mtdB, vnB.plusEquals(cb.vt), normal, depth, FP.ONE);
		}
	}

