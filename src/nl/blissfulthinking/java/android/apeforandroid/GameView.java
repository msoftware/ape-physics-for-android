package nl.blissfulthinking.java.android.apeforandroid;

import nl.blissfulthinking.java.android.ape.APEngine;
import nl.blissfulthinking.java.android.ape.CircleParticle;
import nl.blissfulthinking.java.android.ape.Vector;
import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * View that draws, takes keystrokes, etc. for a simple LunarLander game.
 * 
 * Has a mode which RUNNING, PAUSED, etc. Has a x, y, dx, dy, ... capturing the
 * current ship physics. All x/y etc. are measured with (0,0) at the lower left.
 * updatePhysics() advances the physics based on realtime. draw() renders the
 * ship, and does an invalidate() to prompt another draw() as soon as possible
 * by the system.
 */
class GameView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {
    class GameThread extends Thread {
        /*
         * State-tracking constants
         */
        public static final int STATE_LOSE = 1;
        public static final int STATE_PAUSE = 2;
        public static final int STATE_READY = 3;
        public static final int STATE_RUNNING = 4;
        public static final int STATE_WIN = 5;
        
//      private float x;
//      private float y;
     
        private int fingerX = 0;
        private int fingerY = 0;
        
//        private int speedX = 0;
//        private int speedY = 0;
        
//        private static final int SPEED = 100;
        private boolean dRight;
        private boolean dLeft;
        private boolean dUp;
        private boolean dDown;
//        
//        private int mCanvasWidth;
//        private int mCanvasHeight;
//
//        private long mLastTime;
//        private Bitmap mSnowflake;
        
        private PhysicsWorld physicsWorld = new PhysicsWorld();
        
         /** Message handler used by thread to post stuff back to the GameView */
//      private Handler mHandler;

         /** The state of the game. One of READY, RUNNING, PAUSE, LOSE, or WIN */
        private int mMode;
        /** Indicate whether the surface has been created & is ready to draw */
        private boolean mRun = false;
        /** Handle to the surface manager object we interact with */
        private SurfaceHolder mSurfaceHolder;
		private int type1won;
		private int type2won;

        public GameThread(SurfaceHolder surfaceHolder, Context context,
                Handler handler) {
            // get handles to some important objects
            mSurfaceHolder = surfaceHolder;
//          mHandler = handler;
//          mContext = context;
            
            
        	
//          x = 10;
//          y = 10;
//        	mSnowflake = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.snowflake);
        }

        /**
         * Starts the game, setting parameters for the current difficulty.
         */
        public void doStart() {
            synchronized (mSurfaceHolder) {
            	// Initialize game here!
            	physicsWorld.initWorld();
            	
//                x = 10;
//                y = 10;
            	
//              mLastTime = System.currentTimeMillis() + 100;
                setState(STATE_RUNNING);
            }
        }

        /**
         * Pauses the physics update & animation.
         */
        public void pause() {
            synchronized (mSurfaceHolder) {
                if (mMode == STATE_RUNNING) 
                	setState(STATE_PAUSE);
            }
        }

        @Override
        public void run() {
            while (mRun) {
                Canvas c = null;
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    synchronized (mSurfaceHolder) {
//                    	Log.i("state "+System.currentTimeMillis(),"state="+mMode);
//                    	if(mMode == STATE_READY) {
//                    		drawReadyScreen(c);
//                    	}
                    	if (mMode == STATE_RUNNING) {
                        	updateGame();
                        	doDraw(c);
               
                    		
                    		//TEST AREA
//                                int cycles = 100000;
//                                
//                                Vector v = Vector.getNew(0,0);
//                                int[] vector = new int[2];
//
//                                long now1 = System.currentTimeMillis();
//                                int i = 0;
//                                while(i<cycles) {
//                                	  v.x++;
//                                	
////                                    if(circleParticle instanceof CircleParticle) {
//                                        i++;
////                                    }
//                                }
//                                long timetaken1 = (System.currentTimeMillis()-now1);
//
//                                long now2 = System.currentTimeMillis();
//                                int j = 0;
//                                while(j<cycles) {
//                                	  vector[1]++;
////                                    if(circleParticle.type == AbstractParticle.TYPE_CIRCLE) {
//                                        j++;
////                                    }
//                                }
//                                long timetaken2 = (System.currentTimeMillis()-now2);
//
//                                if(timetaken1 < timetaken2) {
//                                    type1won++;
//                                }
//                                else if(timetaken2 < timetaken1){
//                                    type2won++;
//                                }
//                                
//                                i+=j;
//                                
//                                Vector.release(v);
//
//                                Log.d("access test time difference: "+(timetaken1-timetaken2),"1 favoured : "+((float)type1won/(type1won+type2won))); 
//                           //END TEST AREA 	
                            	
                            
                    	}
                    	else if(mMode == STATE_PAUSE){
                    		drawPauseScreen(c);
                    	}			
                    	else {
                    		drawReadyScreen(c);
                    	}
                    }
                } finally {
                    // do this in a finally so that if an exception is thrown
                    // during the above, we don't leave the Surface in an
                    // inconsistent state
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        private void drawPauseScreen(Canvas c) {
           	c.drawARGB(15, 0, 0, 0);
			c.drawText("PAUSED", (GameView.width/2)-10, 10, Paints.textpaint);
		}

		/**
         * Used to signal the thread whether it should be running or not.
         * Passing true allows the thread to run; passing false will shut it
         * down if it's already running. Calling start() after this was most
         * recently called with false will result in an immediate shutdown.
         * 
         * @param b true to run, false to shut down
         */
        public void setRunning(boolean b) {
            mRun = b;
        }

        public void drawReadyScreen(Canvas c) {
        	// empty canvas
        	c.drawARGB(255, 0, 0, 0);
        	
    		c.drawRect(10,10,GameView.width-10,GameView.height-10,Paints.circlePaint);
//			c.drawRect(12,12,GameView.width-12,GameView.height-12,Paints.rectanglePaint);
        	
        	int l0 = 5;
        	int l1 = 15;
			c.drawText("ape-for-android alpha testbed", 15, l0+l1, Paints.textpaint);
			c.drawText("press the menu button to start the demo", 15, l0+l1, Paints.textpaint);
			c.drawText("have fun", 15, l0+l1, Paints.textpaint);
	
		}

		/**
         * Sets the game mode. That is, whether we are running, paused, in the
         * failure state, in the victory state, etc.
         * 
         * @see #setState(int, CharSequence)
         * @param mode one of the STATE_* constants
         */
        public void setState(int mode) {
            synchronized (mSurfaceHolder) {
                setState(mode, null);
            }
        }

        /**
         * Sets the game mode. That is, whether we are running, paused, in the
         * failure state, in the victory state, etc.
         * 
         * @param mode one of the STATE_* constants
         * @param message string to add to screen or null
         */
        public void setState(int mode, CharSequence message) {
            synchronized (mSurfaceHolder) {
                mMode = mode;
            }
        }

        /* Callback invoked when the surface dimensions change. */
        public void setSurfaceSize(int width, int height) {
            // synchronized to make sure these all change atomically
            synchronized (mSurfaceHolder) {
//                mCanvasWidth = width;
//                mCanvasHeight = height;
            }
        }

        /**
         * Resumes from a pause.
         */
        public void unpause() {
            // Move the real time clock up to now
            synchronized (mSurfaceHolder) {
//                mLastTime = System.currentTimeMillis() + 100;
            }
            setState(STATE_RUNNING);
        }

        /**
         * Handles a key-down event.
         * 
         * @param keyCode the key that was pressed
         * @param msg the original event object
         * @return true
         */
        public final boolean doKeyDown(int keyCode, KeyEvent msg) {
        	boolean handled = false;
            synchronized (mSurfaceHolder) {
            	if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
            		dRight = true;
            		handled = true;
            	}
            	if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
            		dLeft = true;
            		handled = true;
            	}
            	if (keyCode == KeyEvent.KEYCODE_DPAD_UP){
            		dUp = true;
            		handled = true;
            	}
            	if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
            		dDown = true;
            		handled = true;
            	}
                return handled;
            }
        }

        /**
         * Handles a key-up event.
         * 
         * @param keyCode the key that was pressed
         * @param msg the original event object
         * @return true if the key was handled and consumed, or else false
         */
        public final boolean doKeyUp(int keyCode, KeyEvent msg) {
        	boolean handled = false;
            synchronized (mSurfaceHolder) {
            	if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
            		dRight = false;
            		handled = true;
            	}
            	if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
            		dLeft = false;
            		handled = true;
            	}
            	if (keyCode == KeyEvent.KEYCODE_DPAD_UP){
            		dUp = false;
            		handled = true;
            	}
            	if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
            		dDown = false;
            		handled = true;
            	}
                return handled;
            }
        }
        
        public final boolean onTouchEvent(MotionEvent e) {
        	fingerX = (int) e.getX();
        	fingerY = (int) e.getY();
       	
//        	int xgrav = 0;
//        	int ygrav = 0;
//        	
//        	int action = e.getAction();
//        	if(action == MotionEvent.ACTION_MOVE) {
//	        	if(fingerX > GameView.width/2) {
//	        		xgrav = 5;
//	        	}
//	        	else {
//	        		xgrav = -5;
//	        	}
//        	}
//        	else if(action == MotionEvent.ACTION_UP) {
//        		xgrav = 0;
//        	}
//        	
//         	if(fingerY > GameView.height/2) {
//         		ygrav = 4;
//        	}
//         	else {
//        		ygrav = -4;
//         	}
        	
//        	APEngine.addParticle(new RectangleParticle(fingerX,fingerY,25,25,0.0f,false,3.0f,0.001f,0.01f,true));
			APEngine.addParticle(new CircleParticle(fingerX,fingerY,30,false,2.5f,0.1f,0.02f));
         
//			physicsWorld.setGravity(xgrav,ygrav);
//         	APEngine.addForce(FP.fromInt(xgrav),FP.fromInt(ygrav));
        	
			//change to flood, or not
        	return false;
        }
        
        

        /**
         * Draws the ship, fuel/speed bars, and background to the provided
         * Canvas.
         */
        private final void doDraw(Canvas canvas) {
        	// empty canvas
        	canvas.drawARGB(255, 0, 0, 0);
        	
        	//APE
        	physicsWorld.paintWorld(canvas);
//        	canvas.drawBitmap(mSnowflake, x, y, new Paint());
        }

        /**
         * Updates the game.
         */
        private final void updateGame() {
        	//// <DoNotRemove>
//            long now = System.currentTimeMillis();
            // Do nothing if mLastTime is in the future.
            // This allows the game-start to delay the start of the physics
            // by 100ms or whatever.
//            if (mLastTime > now) {
//            	return;
//            }
//          double elapsed = (now - mLastTime) / 1000.0;
//          mLastTime = now;
//          //// </DoNotRemove>
//          Log.d("elapsed time",""+elapsed);
            
        	//MvdA TODO could manage APE's speed here
        	
           	physicsWorld.setGravity(FP.fromFloat((-roll)/10),FP.fromFloat((-pitch)/10));
            physicsWorld.updateWorld();
            
            /*
             * Why use mLastTime, now and elapsed?
             * Well, because the frame rate isn't always constant, it could happen your normal frame rate is 25fps
             * then your char will walk at a steady pace, but when your frame rate drops to say 12fps, without elapsed
             * your character will only walk half as fast as at the 25fps frame rate. Elapsed lets you manage the slowdowns
             * and speedups!
             */
            
//            //process finger input
//            if(fingerX-5 > x) {
//            	speedX = 150;
//            }
//            else if(fingerX+5 < x){
//            	speedX = -150;
//            }
//            else {
//            	speedX = 0;
//            }
//            
//            if(fingerY-5 > y) {
//            	speedY = 150;
//            }
//            else if(fingerY+5 < y){
//            	speedY = -150;
//            }
//            else {
//            	speedY = 0;
//            }
//            
//            x += elapsed * speedX;
//            y += elapsed * speedY;
//            
//            //process d-pad input
//            if (dUp)
//            	y -= elapsed * SPEED;
//            if (dDown)
//            	y += elapsed * SPEED;
//            if (y < 0)
//            	y = 0;
//            else if (y >= mCanvasHeight - mSnowflake.getHeight())
//            	y = mCanvasHeight - mSnowflake.getHeight();
//            if (dLeft)
//            	x -= elapsed * SPEED;
//            if (dRight)
//            	x += elapsed * SPEED;
//            if (x < 0)
//            	x = 0;
//            else if (x >= mCanvasWidth - mSnowflake.getWidth())
//            	x = mCanvasWidth - mSnowflake.getWidth();
            
 
        }
    }

    /** Handle to the application context, used to e.g. fetch Drawables. */
//    private Context mContext;

    /** The thread that actually draws the animation */
    private GameThread thread;
    
    public static int width;
    public static int height;
    
	public float pitch;
	public float roll;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        
        // register to receive sensor events
        SensorManager sm=(SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);

		
        // create thread only; it's started in surfaceCreated()
        thread = new GameThread(holder, context, new Handler() {
            @Override
            public void handleMessage(Message m) {
            	// Use for pushing back messages.
            }
        });

        setFocusable(true); // make sure we get key events
    }

    /**
     * Fetches the animation thread.
     * 
     * @return the animation thread
     */
    public GameThread getThread() {
        return thread;
    }
    
    @Override  
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
       GameView.width = width;
 	   GameView.height = height;
    }

    /**
     * Standard override to get key-press events.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {
        return thread.doKeyDown(keyCode, msg);
    }

    /**
     * Standard override for key-up. We actually care about these, so we can
     * turn off the engine or stop rotating.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent msg) {
        return thread.doKeyUp(keyCode, msg);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent e) {
    	return thread.onTouchEvent(e);
    }

    /**
     * Standard window-focus override. Notice focus lost so we can pause on
     * focus lost. e.g. user switches to take a call.
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus)
        	thread.pause();
    }

    /* Callback invoked when the surface dimensions change. */
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
        thread.setSurfaceSize(width, height);
    }

    /*
     * Callback invoked when the Surface has been created and is ready to be
     * used.
     */
    public void surfaceCreated(SurfaceHolder holder) {
        // start the thread here so that we don't busy-wait in run()
        // waiting for the surface to be created
        thread.setRunning(true);
        thread.start();
    }

    /*
     * Callback invoked when the Surface has been destroyed and must no longer
     * be touched. WARNING: after this method returns, the Surface/Canvas must
     * never be touched again!
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        // we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public final void onSensorChanged(SensorEvent event) {
//		Log.i("sensor","onSensorChanged");
		if ( event.sensor.getType()==Sensor.TYPE_ORIENTATION )
		{
//			Log.i("lolo","orient azimutih, pitch, roll: "+event.values[0]+":"+event.values[1]+":"+event.values[2]);
			pitch=event.values[1];
			roll=event.values[2];
		}
	}
}
