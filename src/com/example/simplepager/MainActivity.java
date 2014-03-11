package com.example.simplepager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	
	LinearLayout mLayout;
    GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
    
    private static final int DRCT_R_L = 100;
    private static final int DRCT_L_R = 101;
    
    @SuppressWarnings("unused")
	private static final boolean LD_LOG = true;
    @SuppressWarnings("unused")
	private static final String TAG = MainActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mLayout = (LinearLayout)findViewById(R.id.outerLayout);
		
		//-- Gesture detection
		FlipGestureDetector flpGestureDetector = new FlipGestureDetector();
		flpGestureDetector.setmListener(new FlipGestureDetector.GestureDetectorListener() {
			@Override
			public void onRightToLeftSwipe() {
				Toast.makeText(MainActivity.this, "-- Left Swipe", Toast.LENGTH_SHORT).show();
				doAnimation(DRCT_R_L);
			}
			
			@Override
			public void onLeftToRightSwipe() {
				Toast.makeText(MainActivity.this, "-- Right Swipe", Toast.LENGTH_SHORT).show();
				doAnimation(DRCT_L_R);
			}
		});
		
        gestureDetector = new GestureDetector(this, flpGestureDetector);
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        
        mLayout.setOnTouchListener(gestureListener);
        
        // -- Add the first fragment on-create
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment fragment = new TestFragment();
		ft.add(R.id.fragmentContainer, fragment, "fragment");
		ft.commit();
	}
	
	private void doAnimation(int direction) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment fragment = new TestFragment();
		Fragment oldFragment = fm.findFragmentByTag("fragment");
		
		if(direction == DRCT_R_L) {
			ft.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_in_right);
		}
		else {
			ft.setCustomAnimations(R.anim.anim_slide_out_left, R.anim.anim_slide_out_right);
		}
		
		if(oldFragment != null) {
			ft.replace(R.id.fragmentContainer, fragment, "fragment");
		}
		else {
			ft.add(R.id.fragmentContainer, fragment, "fragment");
		}
		// Start the animated transition.
		ft.commit();
	}

}
