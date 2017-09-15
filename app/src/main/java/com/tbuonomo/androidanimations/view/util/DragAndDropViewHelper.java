package com.tbuonomo.androidanimations.view.util;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by tommy on 04/09/17.
 */

public class DragAndDropViewHelper {
  private DragAndDropListener dragAndDropListener;

  public DragAndDropViewHelper() {

  }

  public DragAndDropViewHelper setOnDragAndDropListener(DragAndDropListener dragAndDropListener) {
    this.dragAndDropListener = dragAndDropListener;
    return this;
  }

  public void applyOnView(View view) {
    view.setOnTouchListener(new DragAndDropTouchListener());
  }

  public interface DragAndDropListener {
    void onDragEnd();

    void onDragStart();
  }

  private class DragAndDropTouchListener implements View.OnTouchListener {
    private float lastX;
    private float lastY;
    private float translationX;
    private float translationY;

    @Override public boolean onTouch(View v, MotionEvent event) {
      final float x = event.getRawX();
      final float y = event.getRawY();
      switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
          translationX = v.getTranslationX();
          translationY = v.getTranslationY();
          lastX = x;
          lastY = y;
          if (dragAndDropListener != null) {
            dragAndDropListener.onDragStart();
          }
          break;

        case MotionEvent.ACTION_MOVE:
          // Calculate the distance moved
          final float dx = x - lastX;
          final float dy = y - lastY;

          translationX += dx;
          translationY += dy;

          v.setTranslationX(translationX);
          v.setTranslationY(translationY);

          // Remember this touch position for the next move event
          lastX = x;
          lastY = y;

          break;

        case MotionEvent.ACTION_UP:
          if (dragAndDropListener != null) {
            dragAndDropListener.onDragEnd();
            break;
          }
      }
      return true;
    }
  }
}
