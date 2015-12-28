package com.example.duanlei.indexlistviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by duanlei on 15/12/23.
 */
public class IndexableListView extends ListView {

    private boolean mIsFastScrollEnabled = false;
    //用于绘制索引条
    private IndexScroller mScroller = null;

    private GestureDetector mGestureDetector;


    public IndexableListView(Context context) {
        super(context);
    }

    public IndexableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndexableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFastScrollEnabled() {
        return super.isFastScrollEnabled();
    }

    @Override
    public void setFastScrollEnabled(boolean enabled) {
        //super.setFastScrollEnabled(enabled);

        mIsFastScrollEnabled = enabled;

        if (mIsFastScrollEnabled) {
            if (mScroller == null) {
                mScroller = new IndexScroller(getContext(), this);
            }
        } else {
            if (mScroller != null) {
               // mScroller.hide();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mScroller != null) {
            //绘制右侧索引条
            mScroller.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        // 如果mScroller自己来处理触摸事件，该方法返回true
//        if (mScroller != null && mScroller.onTouchEvent(ev)) {
//            return true;
//        }

        if (mGestureDetector == null) {
            //使用手势处理触摸事件
            mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    //显示右侧的索引条
//                    mScroller.show();
                    return super.onFling(e1, e2, velocityX, velocityY);
                }
            });
        }

        mGestureDetector.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        if (mScroller != null) {
            mScroller.setAdapter(adapter);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mScroller != null) {
            mScroller.onSizeChanged(w, h, oldw, oldh);
        }

    }
}
