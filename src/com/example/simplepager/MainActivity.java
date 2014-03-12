/*******************************************************************************
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*******************************************************************************/
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

/**
* @author Rukmal Dias (rukmaldias[at]gmail[dot]com)
*/
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
        FragmentManager fm = getSupportFragmentManager();
        Fragment oldFragment = fm.findFragmentByTag("fragment");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment fragment = new TestFragment();
		if(oldFragment != null) {
			ft.replace(R.id.fragmentContainer, fragment, "fragment");
		}
		else {
			ft.add(R.id.fragmentContainer, fragment, "fragment");
		}
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
