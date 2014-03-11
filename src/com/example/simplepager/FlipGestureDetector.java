package com.example.simplepager;

import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class FlipGestureDetector extends SimpleOnGestureListener {
	
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    
    public static interface GestureDetectorListener {
    	public void onRightToLeftSwipe();
    	public void onLeftToRightSwipe();
    }
    
    private GestureDetectorListener mListener;
    
    private static final boolean LD_LOG = true;
    private static final String TAG = FlipGestureDetector.class.getName();
	
	public GestureDetectorListener getmListener() {
		return mListener;
	}

	public void setmListener(GestureDetectorListener mListener) {
		this.mListener = mListener;
	}

	@Override
     public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		try {
			if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
				return false;
			}
             // right -> left
             if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && 
            		 Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            	 mListener.onRightToLeftSwipe();
             }	
             // left -> right
             else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && 
            		 Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            	 mListener.onLeftToRightSwipe();
             }
		} catch (Exception e) {
             if(LD_LOG) Log.d(TAG, "-- onFling : " + e.getMessage());
		}
		return false;
     }

     @Override
     public boolean onDown(MotionEvent e) {
    	 return true;
     }

}
