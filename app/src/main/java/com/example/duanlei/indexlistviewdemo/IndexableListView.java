package com.example.duanlei.indexlistviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.widget.ListView;

/**
 * Created by duanlei on 15/12/23.
 */
public class IndexableListView extends ListView {

    private boolean mIsFastScrollEnabled = false;
    //用于绘制索引条
    //private IndexScroller mScroller = null;

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





}
