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

	
	public class Vector {
		
		public int x;
		public int y;
		
		private static final Vector tmp = Vector.getNew(0,0);
		
//		public static int creationCount = 0;
//		public static int poolCreationCount = 0;
//		public static int poolUnavailableCount = 0;
//		public static int poolReleaseCount = 0;
		
		public Vector next = null;
		public static Vector available = null;
//		public static Queue<Vector> pool = new LinkedList<Vector>();
	
//		public static final Vector getNew(float px, float py) {
////			System.out.println("REQUESTING VECTOR, POOL BEFORE: "+pool.size());
////			Vector v = pool.poll();
//			if(available != null) {
//				poolCreationCount++;
//				Vector newVec = available;
//				newVec.setTo(FP.fromFloat(px),FP.fromFloat(py));
//				available = newVec.next;
//				return newVec;
//			}
//			else {
//				poolUnavailableCount++;
//				return new Vector(FP.fromFloat(px),FP.fromFloat(py));
//			}
//		}
		
		public static final Vector getNew(int px, int py) {
//			System.out.println("REQUESTING VECTOR, POOL BEFORE: "+pool.size());
//			Vector v = pool.poll();
			if(available != null) {
//				poolCreationCount++;
				Vector newVec = available;
				newVec.setTo(px,py);
				available = newVec.next;
				return newVec;
			}
			else {
//				poolUnavailableCount++;
				return new Vector(px,py);
			}
		}
		
		public static final void release(Vector v) {
//			poolReleaseCount++;
//			pool.offer(v);
			v.next = available;
			available = v;
//			System.out.println("RELEASED VECTOR, POOL AFTER: "+pool.size());
		}
	
		private Vector(int px, int py) {
//			creationCount++;
			x = px;
			y = py;
		}
		
		public final void setTo(int px, int py) {
			x = px;
			y = py;
		}
		
		public final int dot(Vector v) {
//			return x * v.x + y * v.y;
			return FP.mul(x,v.x)+FP.mul(y,v.y);
		}
		
		public final int cross(Vector v) {
//			return x * v.y - y * v.x;
			return FP.mul(x,v.y)-FP.mul(y,v.x);
		}
		
//		public final Vector plus(Vector v) {
//			return new Vector(x + v.x, y + v.y); 
//		}
		
//		//pooling_draw
//		public final Vector pool_plus(Vector v) {
//			return Vector.getNew(x + v.x, y + v.y); 
//		}
		
		public final Vector supply_plus(Vector v, Vector supplied) {
			supplied.setTo(x + v.x, y + v.y); 
			return supplied; 
		}
	
		public final Vector plusEquals(Vector v) {
			x += v.x;
			y += v.y;
			return this;
		}
		
		public final Vector plusEquals(int x, int y) {
			this.x += x;
			this.y += y;
			return this;
		}
		
//		//pooling_candidate
//		public final Vector minus(Vector v) {
//			return new Vector(x - v.x, y - v.y);    
//		}
		
//		//pooling_draw
//		public final Vector pool_minus(Vector v) {
//			return Vector.getNew(x - v.x, y - v.y);    
//		}
		
		public final Vector supply_minus(Vector v, Vector supplied) {
			supplied.setTo(x - v.x, y - v.y);    
			return supplied ;
		}
	
		public final Vector minusEquals(Vector v) {
			x -= v.x;
			y -= v.y;
			return this;
		}
	
//		//pooling_candidate
//		public final Vector mult(int s) {
//			return new Vector(x * s, y * s);
//		}
		
		public final Vector supply_mult(int s, Vector toReturn) {
			toReturn.setTo(FP.mul(x,s),FP.mul(y,s));
			return toReturn;
		}
	
//		//pooling_draw
//		public final Vector pool_mult(int s) {
//			return Vector.getNew(FP.mul(x,s),FP.mul(y,s));
//		}
	
		public final Vector multEquals(int s) {
			x = FP.mul(x,s);
			y = FP.mul(y,s);
			return this;
		}
	
//		//pooling_candidate
//		public final Vector times(Vector v) {
//			return new Vector(x * v.x, y * v.y);
//		}
		
		public final Vector divEquals(int s) {
			if (s == FP.fromInt(0)) s -= FP.fromFloat(0.0000001f);
			x = FP.div(x,s);
			y = FP.div(y,s);
//			x /= s;
//			y /= s;
			return this;
		}
		
		public final int magnitude() {
			return FP.sqrt(FP.mul(x,x)+FP.mul(y,y));
			//MvdA TODO look if this affect behaviour much
//			return Math.sqrt(x * x + y * y);
		}

		public final int distance(Vector v) {
			supply_minus(v,tmp);
			return tmp.magnitude();
		}

//		//pooling_draw
//		public final Vector pool_normalize() {
//			 int m = magnitude();
//			 if (m == 0) m = 0.0001;
//			 return pool_mult(1 / m);
//		}
		
		public final Vector supply_normalize(Vector v) {
			 int m = magnitude();
//			 if (m == 0) m = 0.0001;
			 if(m == 0) {
				 m -= FP.fromFloat(0.0001f);
			 }	 
			 return supply_mult(FP.div(1,m),v);
		}
				
		@Override
		public final String toString() {
			return (FP.toFloat(x) + " : " + FP.toFloat(y));
		}

		public static final int getPoolSize() {
			if(available != null) {
				int i = 1;
				Vector v = available; 
				while(v.next != null) {
					i++;
					v = v.next;
				}
				return i;
			}
			else {
				return 0;
			}
		}
	}
