package com.example.duanlei.indexlistviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SectionIndexer;

/**
 * Author: duanlei
 * Date: 2015-12-28
 * 索引条
 */
public class IndexScroller {
    //索引条的宽度
    private float mIndexbarWidth;

    //索引条距离右侧边缘的距离
    private float mIndexbarMargin;

    //在中心显示的预览文本四周的距离
    private float mPreviewPadding;

    //当前屏幕密度除以160
    private float mDensity;

    private float mScaleDensity;

    private float mAlphaRate;

    private int mState = STATE_HIDDEN;

    private int mListViewHeight;
    private int mListViewWidth;

    private int mCurrentSection = -1;
    private boolean mIsIndexing = false;
    private ListView mListView = null;
    private SectionIndexer mIndexer = null;
    private String[] mSections = null;
    private RectF mIndexbarRect;

    private static final int STATE_HIDDEN = 0;
    private static final int STATE_SHOWING = 1;
    private static final int STATE_SHOWN = 2;
    private static final int STATE_HIDING = 3;


    public IndexScroller(Context context, ListView listView) {
        mDensity = context.getResources().getDisplayMetrics().density;
        mScaleDensity = context.getResources().getDisplayMetrics().scaledDensity;

        mListView = listView;

        setAdapter(mListView.getAdapter());

        mIndexbarWidth = 20 * mDensity;
        mIndexbarMargin = 18 * mDensity;
        mPreviewPadding = 5 * mDensity;
    }

    public void setAdapter(Adapter adapter) {
        if (adapter instanceof SectionIndexer) {
            mIndexer = (SectionIndexer) adapter;
            mSections = (String[]) mIndexer.getSections();

        }
    }

    public void draw(Canvas canvas) {
        // 绘制索引条，包括索引条的背景和文本
        // 绘制预览文本的背景

        if (mState == STATE_HIDDEN) {
            return;
        }

        Paint indexbarPaint = new Paint();
        indexbarPaint.setColor(Color.BLACK);
        indexbarPaint.setAlpha((int) (64 * mAlphaRate));

        canvas.drawRoundRect(mIndexbarRect, 5 * mDensity,
                5 * mDensity, indexbarPaint);

        if (mSections != null && mSections.length > 0) {
            if (mCurrentSection >= 0) {
                Paint previewPaint = new Paint();
                previewPaint.setColor(Color.BLACK);
                previewPaint.setAlpha(96);

                Paint previewTextPaint = new Paint();
                previewTextPaint.setColor(Color.WHITE);
                previewTextPaint.setTextSize(50 * mScaleDensity);

                float previewTextWidth = previewTextPaint.measureText(
                        mSections[mCurrentSection]
                );

                float previewSize =
                        2 * mPreviewPadding + previewTextPaint.descent()
                                - previewTextPaint.ascent();

                //预览文本背景区域
                RectF previewRect = new RectF(
                        (mListViewWidth - previewSize) / 2,
                        (mListViewHeight - previewSize) / 2,
                        (mListViewWidth - previewSize) / 2 + previewSize,
                        (mListViewHeight - previewSize) / 2 + previewSize);

                //绘制背景
                canvas.drawRoundRect(previewRect,
                        5 * mDensity,
                        5 * mDensity,
                        previewPaint);

                canvas.drawText(
                        mSections[mCurrentSection],
                        previewRect.left + (previewSize - previewTextWidth) / 2 - 1,
                        previewRect.top + mPreviewPadding - previewTextPaint.ascent() + 1,
                        previewTextPaint
                );
            }
        }

        //设置索引的绘制属性

        Paint indexPaint = new Paint();
        indexbarPaint.setColor(Color.WHITE);
        indexbarPaint.setAlpha((int) (255 * mAlphaRate));
        indexbarPaint.setTextSize(12 * mScaleDensity);
        float sectionHeight = (mIndexbarRect.height() - 2 * mIndexbarMargin)
                / mSections.length;
        float paddingTop = (sectionHeight - (indexPaint.descent()
                - indexPaint.ascent())) / 2;

        for (int i = 0; i < mSections.length; i++) {
            float paddingLeft = (mIndexbarWidth -
                    indexPaint.measureText(mSections[i])) / 2;

            canvas.drawText(
                    mSections[i], mIndexbarRect.left + paddingLeft,
                    mIndexbarRect.top + mIndexbarMargin + sectionHeight * i +
                            paddingTop - indexPaint.ascent(), indexPaint);
        }

    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        mListViewWidth = w;
        mListViewHeight = h;

        mIndexbarRect = new RectF(w - mIndexbarMargin - mIndexbarWidth
                , mIndexbarMargin
                , w - mIndexbarMargin
                , h - mIndexbarMargin);
    }


}
