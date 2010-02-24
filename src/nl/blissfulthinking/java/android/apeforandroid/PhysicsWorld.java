package nl.blissfulthinking.java.android.apeforandroid;

import java.util.ArrayList;

import nl.blissfulthinking.java.android.ape.APEngine;
import nl.blissfulthinking.java.android.ape.AbstractConstraint;
import nl.blissfulthinking.java.android.ape.AbstractParticle;
import nl.blissfulthinking.java.android.ape.CircleParticle;
import nl.blissfulthinking.java.android.ape.RectangleParticle;
import nl.blissfulthinking.java.android.ape.SpringConstraint;
import android.graphics.Canvas;

public class PhysicsWorld {

//	private long usedTime;

	public static final int KEY_LEFT = 1;
	public static final int KEY_RIGHT = 2;
	
	private APEngine world = new APEngine();
//	private ArrayList paintQueue = new ArrayList();
//	private RectangleParticle rotatingRect;
	
//	private int t;
	
//	private WheelParticle wheelParticleA;
//	private WheelParticle wheelParticleB;
//	
	public final void updateWorld() {
		APEngine.step();
		
		
//		Log.d("Vector Pool Data","*** Vector Pool Data ***");
//		
////		Log.d("Vector Pool Data",Vector.getPoolSize()+" Vectors in Queue");
////		
//		Log.d("Vector Pool Data",Vector.creationCount+" Vectors created through NEW");
//		Log.d("Vector Pool Data",Vector.poolUnavailableCount+" Vectors that couldn't be drawn from pool");
////		Log.d(Vector.poolUnavailableCount-Vector.creationCount+" Vectors that missed the pool creation","bleh");
//		Vector.poolUnavailableCount = 0;
//		Vector.creationCount = 0;
//		
//		Log.d("Vector Pool Data",Vector.poolCreationCount+" Vectors drawn from pool");
//		Vector.poolCreationCount = 0;
//	
//		Log.d("Vector Pool Data",Vector.poolReleaseCount+" Vectors released into pool");
//		Vector.poolReleaseCount = 0;

//		Log.d("Fixed Point Tests","int 0 = fp = "+FP.fromInt(0));
//		Log.d("Fixed Point Tests","2+7 ="+(FP.toInt(FP.fromInt(2)+FP.fromInt(7))));
//		Log.d("Fixed Point Tests","0.5+(-0.5) ="+(FP.fromFloat(0.5f)+FP.fromFloat(-0.5f)));
//		Log.d("Fixed Point Tests","int 1 = fp = "+FP.ONE);
//		Log.d("Fixed Point Tests","int(0) == 0 ? "+(FP.fromInt(0)==0));
//		int q = FP.fromFloat(-124.8f);
//		Log.d("Fixed Point Tests","intVal :"+FP.toFloat(q)+" = "+FP.toInt(q));
//		int w = FP.fromFloat(-124.2f);
//		Log.d("Fixed Point Tests","intVal :"+FP.toFloat(w)+" = "+FP.toInt(w));
//		int i = FP.fromFloat(124.8f);
//		Log.d("Fixed Point Tests","roundPos :"+FP.toFloat(i)+" = "+FP.toInt(FP.roundPosPos(i)));
//		int j = FP.fromFloat(-124.2f);
//		Log.d("Fixed Point Tests","roundPos :"+FP.toFloat(j)+" = "+FP.toInt(FP.roundPosPos(j)));
		
//		int i = FP.fromFloat(124.8f);
//		Log.d("Fixed Point Tests","FP.abs : :"+FP.toFloat(i)+" = "+FP.toInt(FP.abs(i)));
//		Log.d("Fixed Point Tests","FP.mathABS :"+FP.toFloat(i)+" = "+FP.toInt(FP.mathABS(i)));
//		int j = FP.fromFloat(124.29878967999f);
//		Log.d("Fixed Point Tests","FP.abs : :"+FP.toFloat(j)+" = "+FP.toInt(FP.abs(j)));
//		Log.d("Fixed Point Tests","FP.mathABS :"+FP.toFloat(j)+" = "+FP.toInt(FP.mathABS(j)));
//		int k = FP.fromFloat(-124.8970657667f);
//		Log.d("Fixed Point Tests","FP.abs : :"+FP.toFloat(k)+" = "+FP.toInt(FP.abs(k)));
//		Log.d("Fixed Point Tests","FP.mathABS :"+FP.toFloat(k)+" = "+FP.toInt(FP.mathABS(k)));
//		int y = FP.fromFloat(0.4679796796979f);
//		Log.d("Fixed Point Tests","FP.abs : :"+FP.toFloat(y)+" = "+FP.toInt(FP.abs(y)));
//		Log.d("Fixed Point Tests","FP.mathABS :"+FP.toFloat(y)+" = "+FP.toInt(FP.mathABS(y)));
//		int u = FP.fromFloat(0.6679676575675f);
//		Log.d("Fixed Point Tests","FP.abs : :"+FP.toFloat(u)+" = "+FP.toInt(FP.abs(u)));
//		Log.d("Fixed Point Tests","FP.mathABS :"+FP.toFloat(u)+" = "+FP.toInt(FP.mathABS(u)));
//		int ry = FP.fromFloat(-0.4587968987979f);
//		Log.d("Fixed Point Tests","FP.abs : :"+FP.toFloat(ry)+" = "+FP.toInt(FP.abs(ry)));
//		Log.d("Fixed Point Tests","FP.mathABS :"+FP.toFloat(ry)+" = "+FP.toInt(FP.mathABS(ry)));
//		int du = FP.fromFloat(-0.6575478685688f);
//		Log.d("Fixed Point Tests","FP.abs : :"+FP.toFloat(du)+" = "+FP.toInt(FP.abs(du)));
//		Log.d("Fixed Point Tests","FP.mathABS :"+FP.toFloat(du)+" = "+FP.toInt(FP.mathABS(du)));
////		
//		int duq = FP.fromDouble(327.0f);
//		int duq = FP.fromInt(32767);
//		Log.d("Fixed Point Tests","FP.abs :"+FP.toFloat(duq)+" = "+FP.toInt(FP.abs(duq)));
//		Log.d("Fixed Point Tests","FP.mathABS :"+FP.toFloat(duq)+" = "+FP.toInt(FP.mathABS(duq)));
//		
//		int duq2 = FP.fromDouble(Double.POSITIVE_INFINITY);
////		duq2--;
////		duq2--;
//		Log.d("Fixed Point Tests","FP.abs :"+FP.toInt(duq2)+" = "+FP.toInt(FP.abs(duq2)));
//		Log.d("Fixed Point Tests","FP.mathABS :"+FP.toInt(duq2)+" = "+FP.toInt(FP.mathABS(duq2)));
//		
//		rotatingRect.setRotation(rotatingRect.getRotation() + 0.03);	
		
		
//		Log.d("Screen Size","width: "+GameView.width+" height: "+GameView.height);
	}  
	
	@SuppressWarnings("static-access")
	public void initWorld() {

		
		APEngine.particles = null;
		//Remove all existing constraints from the world
		ArrayList<AbstractConstraint> contraints = (ArrayList<AbstractConstraint>) APEngine.getAllConstraints().clone();
		for(AbstractConstraint ac : contraints) {
			APEngine.removeConstraint(ac);
		}
		
		// set up the events, main loop handler, and the engine. you don't have to use
		// enterframe. you just need to call the ApeEngine.step() method wherever
		// and however your handling your program cycle.
		
		// the argument here is the deltaTime value. Higher values result in faster simulations.
		APEngine.init(FP.fromDouble((double)1/3));
		
		// SELECTIVE is better for dealing with lots of little particles colliding, 
		// as in the little rects and circles in this example
		APEngine.setCollisionResponseMode(APEngine.SELECTIVE);
//		APEngine.setCollisionResponseMode(APEngine.STANDARD);
		
		// gravity -- particles of varying masses are affected the same
//		world.setMasslessForce(Vector.getNew(0,3));
		
//		// car 
//		CircleParticle wheelParticleA = new CircleParticle(60.0f,10.0f,10.0f,false,3.0f,0.3f,0.2f);
//		wheelParticleA.setCollidable(false);
//		world.addParticle(wheelParticleA);
//		
//		CircleParticle wheelParticleB = new CircleParticle(140.0f,10.0f,10.0f,false,3.0f,0.3f,0.2f);
//		wheelParticleB.setCollidable(false);
//		world.addParticle(wheelParticleB);
//		
//		SpringConstraint wheelConnector = new SpringConstraint(wheelParticleA, wheelParticleB,1.0f);
//		wheelConnector.setCollidable(true);
//		wheelConnector.setCollisionRectWidth(FP.fromFloat(34.0f));
//		wheelConnector.setCollisionRectScale(FP.fromFloat(0.8f));
//		world.addConstraint(wheelConnector);
	
//		float size1 = 15;
//		float px1 = size1;
//		float py1 = size1+100;
//		// little boxes
//		for (int i = 0; i < 4; i++) {
//			px1 += size1;
//			py1 += size1;
//			APEngine.addParticle(new RectangleParticle(px1,py1,size1,size1,0.5f,false,3.0f+(i/10),0.001f,0.01f));
//		}
		
//		float size2 = 4.0f;
//		float px2 = size2;
//		float py2 = size2;
//		// little circles
//		for (int i = 0; i < 50; i++) {
//			px2 += size2;
//			py2 += size2;
//			APEngine.addParticle(new CircleParticle(px2+size2,py2+size2,size2,false,3.0f+(i/10),0.4f,0.002f));
//		}
//		
		int posx = 50;
		int posy = 50;
		int size = 40;
		int halfsize = 20; 
		int cornersize = 10;
		boolean collidable = true;
		CircleParticle a = new CircleParticle(posx-halfsize,posy-halfsize,cornersize,false,1.0f,0.00f,0.000f);
		world.addParticle(a);
		CircleParticle b = new CircleParticle(posx+halfsize,posy-halfsize,cornersize,false,1.0f,0.00f,0.000f);
		world.addParticle(b);
		CircleParticle c = new CircleParticle(posx+halfsize,posy+halfsize,cornersize,false,1.0f,0.00f,0.000f);
		world.addParticle(c);
		CircleParticle d = new CircleParticle(posx-halfsize,posy+halfsize,cornersize,false,1.0f,0.00f,0.000f);
		world.addParticle(d);
		SpringConstraint ab = new SpringConstraint(a,b,1.0f);
		ab.setCollidable(true);
		ab.setCollisionRectWidth(FP.fromFloat(10.0f));
		ab.setCollisionRectScale(FP.fromFloat(1.f));
		world.addConstraint(ab);
		SpringConstraint bc = new SpringConstraint(b,c,1.0f);
		bc.setCollidable(collidable);
		bc.setCollisionRectWidth(FP.fromFloat(10.0f));
		bc.setCollisionRectScale(FP.fromFloat(1.0f));
		world.addConstraint(bc);
		SpringConstraint cd = new SpringConstraint(c,d,1.0f);
		cd.setCollidable(collidable);
		cd.setCollisionRectWidth(FP.fromFloat(10.0f));
		cd.setCollisionRectScale(FP.fromFloat(1.0f));
		world.addConstraint(cd);
		SpringConstraint da = new SpringConstraint(d,a,1.0f);
		da.setCollidable(collidable);
		da.setCollisionRectWidth(FP.fromFloat(10.0f));
		da.setCollisionRectScale(FP.fromFloat(1.0f));
		world.addConstraint(da);
		SpringConstraint ac = new SpringConstraint(a,c,1.0f);
//		ac.setCollidable(true);
//		ab.setCollisionRectWidth(FP.fromFloat(4.0f));
//		ab.setCollisionRectScale(FP.fromFloat(0.9f));
		world.addConstraint(ac);
		SpringConstraint bd = new SpringConstraint(b,d,1.0f);
//		ac.setCollidable(true);
//		ab.setCollisionRectWidth(FP.fromFloat(4.0f));
//		ab.setCollisionRectScale(FP.fromFloat(0.9f));
		world.addConstraint(bd);
		
		// surfaces
		int thikness = 200;
		RectangleParticle floor = new RectangleParticle(GameView.width/2,GameView.height+(thikness/2),GameView.width+thikness,thikness,0.0f,true,1.0f,0.0f,0.00f,false);
		APEngine.addParticle(floor);
		
		RectangleParticle ceiling = new RectangleParticle(GameView.width/2,0-(thikness/2),GameView.width+thikness,thikness,0.0f,true,1.0f,0.0f,0.00f,false);
		APEngine.addParticle(ceiling);
		
		RectangleParticle leftWall = new RectangleParticle(-1-(thikness/2),GameView.height/2,thikness,GameView.height+thikness,0.0f,true,1.0f,0.0f,0.00f,false);
		APEngine.addParticle(leftWall);
		
		RectangleParticle rightWall = new RectangleParticle(GameView.width+(thikness/2),GameView.height/2,thikness,GameView.height+thikness,0.0f,true,1.0f,0.0f,0.00f,false);
		APEngine.addParticle(rightWall);
		
		RectangleParticle middleWall1 = new RectangleParticle(GameView.width/2,(GameView.height/2)+75,30,150,0.785f,true,1.0f,0.0f,0.00f,true);
		APEngine.addParticle(middleWall1);
		RectangleParticle middleWall = new RectangleParticle(GameView.width/2,(GameView.height/2)+75,30,150,2.355f,true,1.0f,0.0f,0.00f,true);
		APEngine.addParticle(middleWall);

	
		//Pendulum
		CircleParticle ceilingAnchor = new CircleParticle(GameView.width/2,0.0f,10.0f,true,1.0f,0.3f,0.0f);
//		ceilingAnchor.setCollidable(false);
		world.addParticle(ceilingAnchor);
//		RectangleParticle ceilingAnchor = new RectangleParticle(GameView.width/2,0.0f,10.0f,10.0f,0.0f,true,1.0f,0.3f,0.0f,true);
//		world.addParticle(ceilingAnchor);
//		
		
//		RectangleParticle bridgeEnd = new RectangleParticle(290.0f,10.0f,100.0f,25.0f,0.0f,true,1.0f,0.3f,0.0f,true);
//		world.addParticle(bridgeEnd);
		
		CircleParticle chain1 = new CircleParticle(GameView.width/2,70.0f,20.0f,false,1.0f,0.3f,0.0f);
		world.addParticle(chain1);
		
		CircleParticle chain2 = new CircleParticle(GameView.width/2,140.0f,20.0f,false,1.0f,0.3f,0.0f);
		world.addParticle(chain2);
		
		CircleParticle chain3 = new CircleParticle(GameView.width/2,210.0f,30.0f,false,1.0f,0.3f,0.0f);
		world.addParticle(chain3);
		
		SpringConstraint chainConnection1 = new SpringConstraint(chain1, ceilingAnchor, 0.9f);
		chainConnection1.setCollidable(true);
		chainConnection1.setCollisionRectScale(FP.fromFloat(0.8f));
		chainConnection1.setCollisionRectWidth(FP.fromFloat(5.0f));
		world.addConstraint(chainConnection1);

		SpringConstraint chainConnection2 = new SpringConstraint(chain1, chain2, 0.9f);
		chainConnection2.setCollidable(true);
		chainConnection2.setCollisionRectScale(FP.fromFloat(0.8f));
		chainConnection2.setCollisionRectWidth(FP.fromFloat(5.0f));
		world.addConstraint(chainConnection2);
		
		SpringConstraint chainConnection3 = new SpringConstraint(chain2, chain3, 0.9f);
		chainConnection3.setCollidable(true);
		chainConnection3.setCollisionRectScale(FP.fromFloat(0.8f));
		chainConnection3.setCollisionRectWidth(FP.fromFloat(5.0f));
		world.addConstraint(chainConnection3);
	}
	

    public final void keyTyped(int key) {}

//	   public void keyPressed(int key) {
//			float keySpeed = 0.2;
//			
//			if (key == KEY_LEFT) {
//				wheelParticleA.setAngularVelocity(-keySpeed);
//				wheelParticleB.setAngularVelocity(-keySpeed);
//			} else if (key == KEY_RIGHT) {
//				wheelParticleA.setAngularVelocity(keySpeed);
//				wheelParticleB.setAngularVelocity(keySpeed);
//			}
//		}
//		
//		
//		public void keyReleased(int key) {
//			wheelParticleA.setAngularVelocity(0);
//			wheelParticleB.setAngularVelocity(0);
//		}
		 
		 public final void paintWorld(Canvas c) { 
			 //MvdA TODO getting the particles from the public field may be faster
			 //but I want multiple APEngines running parallel later on
//			ArrayList<AbstractParticle> particles = APEngine.getAllParticles();
//			int size = particles.size();
//			for(int i=0;i<size;i++) {
//				particles.get(i).drawParticle(c);
//			}
			AbstractParticle currentParticle = APEngine.particles;
			while(currentParticle != null) {
				currentParticle.drawParticle(c);
				currentParticle = currentParticle.next;
			}
			
			ArrayList<AbstractConstraint> consraints = APEngine.getAllConstraints();
			int size = consraints.size();
			for(int i=0;i<size;i++) {
				consraints.get(i).drawConstraint(c);
			}
			 
//			for (int i = 0; i < paintQueue.size(); i++) {
//				//TG TODO need to write code that determined the type of objects and sets their method. 
//				if (paintQueue.get(i) instanceof RectangleParticle) {
//					drawRectangleParticle(c,((RectangleParticle)paintQueue.get(i)));
////					((RectangleParticle)paintQueue.get(i)).paint(c);
//				}
//				else if (paintQueue.get(i) instanceof CircleParticle) {
//					drawCircleParticle(c,(CircleParticle)paintQueue.get(i));
////					((CircleParticle)paintQueue.get(i)).paint();
//				}	
//				else if (paintQueue.get(i) instanceof SpringConstraint) { 
////					((SpringConstraint)paintQueue.get(i)).paint();				
//				}
//			}

		}  

		public final void setGravity(int x, int y) {
			// gravity -- particles of varying masses are affected the same
			world.setMasslessForce(x,y);
		}
}

