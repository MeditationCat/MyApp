package com.abilix.myapp.view.dialog;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;

import java.io.Serializable;

class AlertController implements Parcelable, Serializable {

    private FragmentManager mFragmentManager;

    private int mStyle;
    private int mTheme;
    private String mTag;
    private int mLayoutResId;
    private View mView;
    private int mBackgroundColor;
    private int mGravity;
    private int mOrientation;
    private float mWindowWidthAmount;
    private float mDimAmount;
    private boolean mCancelable;
    private int[] mSubViewResIds;

    private DialogViewInterface.OnBindViewListener mOnBindViewListener;
    private DialogViewInterface.OnViewClickListener mOnViewClickListener;

    private DialogInterface.OnDismissListener mOnDismissListener;

    protected AlertController() {
    }

    protected AlertController(Parcel in) {
        mStyle = in.readInt();
        mTheme = in.readInt();
        mTag = in.readString();
        mLayoutResId = in.readInt();
        mGravity = in.readInt();
        mBackgroundColor = in.readInt();
        mOrientation = in.readInt();
        mWindowWidthAmount = in.readFloat();
        mDimAmount = in.readFloat();
        mCancelable = in.readByte() != 0;
        mSubViewResIds = in.createIntArray();
    }

    public static final Creator<AlertController> CREATOR = new Creator<AlertController>() {
        @Override
        public AlertController createFromParcel(Parcel in) {
            return new AlertController(in);
        }

        @Override
        public AlertController[] newArray(int size) {
            return new AlertController[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mStyle);
        dest.writeInt(mTheme);
        dest.writeString(mTag);
        dest.writeInt(mLayoutResId);
        dest.writeInt(mGravity);
        dest.writeInt(mBackgroundColor);
        dest.writeInt(mOrientation);
        dest.writeFloat(mWindowWidthAmount);
        dest.writeFloat(mDimAmount);
        dest.writeByte((byte) (mCancelable ? 1 : 0));
        dest.writeIntArray(mSubViewResIds);
    }

    public FragmentManager getFragmentManager() {
        return mFragmentManager;
    }

    public int getStyle() {
        return mStyle;
    }

    public int getTheme() {
        return mTheme;
    }

    public String getTag() {
        return mTag;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

    public View getView() {
        return mView;
    }

    public int getGravity() {
        return mGravity;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public int getOrientation() {
        return mOrientation;
    }

    public float getWindowWidthAmount() {
        return mWindowWidthAmount;
    }

    public float getDimAmount() {
        return mDimAmount;
    }

    public boolean isCancelable() {
        return mCancelable;
    }

    public int[] getSubViewResIds() {
        return mSubViewResIds;
    }

    public DialogViewInterface.OnBindViewListener getOnBindViewListener() {
        return mOnBindViewListener;
    }

    public DialogViewInterface.OnViewClickListener getOnViewClickListener() {
        return mOnViewClickListener;
    }

    public DialogInterface.OnDismissListener getOnDismissListener() {
        return mOnDismissListener;
    }


    public static class AlertParams {
        public FragmentManager mFragmentManager;
        public int mStyle = DialogFragment.STYLE_NORMAL;
        public int mTheme = 0;
        public String mTag = "AlertController";
        public int mViewLayoutResId = 0;
        public View mView;
        public int mGravity = Gravity.CENTER;
        public int mBackgroundColor = Color.TRANSPARENT;
        public int mOrientation = LinearLayoutManager.VERTICAL;
        public float mWindowWidthAmount = 0.8f;
        public float mDimAmount = 0.2f;
        public boolean mCancelable = true;
        public int[] mSubViewResIds;

        public DialogViewInterface.OnBindViewListener mOnBindViewListener;
        public DialogViewInterface.OnViewClickListener mOnViewClickListener;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;

        public AlertParams() {
            //
        }

        public void apply(AlertController dialog) {
            dialog.mFragmentManager = mFragmentManager;
            dialog.mStyle = mStyle;
            dialog.mTheme = mTheme;
            dialog.mTag = mTag;
            if (mViewLayoutResId != 0) {
                dialog.mLayoutResId = mViewLayoutResId;
            }
            if (mView != null) {
                dialog.mView = mView;
            }
            dialog.mGravity = mGravity;
            dialog.mBackgroundColor = mBackgroundColor;
            dialog.mOrientation = mOrientation;
            dialog.mWindowWidthAmount = mWindowWidthAmount;
            dialog.mDimAmount = mDimAmount;
            dialog.mCancelable = mCancelable;
            dialog.mSubViewResIds = mSubViewResIds;

            if (mOnBindViewListener != null) {
                dialog.mOnBindViewListener = mOnBindViewListener;
            }
            if (mOnViewClickListener != null) {
                dialog.mOnViewClickListener = mOnViewClickListener;
            }
            if (mOnDismissListener != null) {
                dialog.mOnDismissListener = mOnDismissListener;
            }
        }
    }

}
