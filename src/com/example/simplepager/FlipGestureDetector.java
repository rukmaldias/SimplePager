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

import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

/**
* @author Rukmal Dias (rukmaldias[at]gmail[dot]com)
*/
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
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		try {
			if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
				return false;
			}
			// right -> left
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				mListener.onRightToLeftSwipe();
			}
			// left -> right
			else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				mListener.onLeftToRightSwipe();
			}
		} catch (Exception e) {
			if (LD_LOG)
				Log.d(TAG, "-- onFling : " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return true;
	}

}
