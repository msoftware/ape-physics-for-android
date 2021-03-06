package nl.blissfulthinking.java.android.apeforandroid;

import nl.blissfulthinking.java.android.apeforandroid.GameView.GameThread;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

//package nl.blissfulthinking.java.android.apeforandroid;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.widget.TextView;
//
//public class AndroidAPE extends Activity {
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////      setContentView(R.layout.main);
//        TextView tv = new TextView(this);
//        tv.setText("Hello World!");
//        setContentView(tv);
//    }
//}

public class AndroidAPE extends Activity {
    private static final int MENU_PAUSE = Menu.FIRST;

    private static final int MENU_RESUME = Menu.FIRST + 1;

    private static final int MENU_START = Menu.FIRST + 2;

    private static final int MENU_STOP = Menu.FIRST + 3;

    /** A handle to the thread that's actually running the animation. */
    private GameThread mGameThread;

    /** A handle to the View in which the game is running. */
    private GameView mGameView;

    /**
     * Invoked during init to give the Activity a chance to set up its Menu.
     * 
     * @param menu the Menu to which entries may be added
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, MENU_START, 0, R.string.menu_start);
        menu.add(0, MENU_STOP, 0, R.string.menu_stop);
        menu.add(0, MENU_PAUSE, 0, R.string.menu_pause);
        menu.add(0, MENU_RESUME, 0, R.string.menu_resume);
        
        return true;
    }

    /**
     * Invoked when the user selects an item from the Menu.
     * 
     * @param item the Menu entry which was selected
     * @return true if the Menu item was legit (and we consumed it), false
     *         otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_START:
                mGameThread.doStart();
                return true;
            case MENU_STOP:
                mGameThread.setState(GameThread.STATE_LOSE);
                return true;
            case MENU_PAUSE:
                mGameThread.pause();
                return true;
            case MENU_RESUME:
                mGameThread.unpause();
                return true;
        }

        return false;
    }

    /**
     * Invoked when the Activity is created.
     * 
     * @param savedInstanceState a Bundle containing state saved from a previous
     *        execution, or null if this is a new execution
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // turn off the window's title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // tell system to use the layout defined in our XML file
        setContentView(R.layout.main);
        
//        tv.setText("Welcome to the ape-for-android testbed! Created by Michiel van den Anker");
//        tv.append("This testbed uses SnowFlake's game template; thanks!");
//        tv.append("APE was originally created by Alec Cove");
//        tv.append("First conversion to Java by Theo Galanakis");


        // get handles to the LunarView from XML, and its LunarThread
        mGameView = (GameView) findViewById(R.id.game);
        mGameThread = mGameView.getThread();

        // set up a new game
        mGameThread.setState(GameThread.STATE_READY);
//        Log.w(this.getClass().getName(), "SIS is null");
        
    	Paints p = new Paints();
    	p.init();
        

//    	
    }

    /**
     * Invoked when the Activity loses user focus.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mGameView.getThread().pause(); // pause game when Activity pauses
    }
}
