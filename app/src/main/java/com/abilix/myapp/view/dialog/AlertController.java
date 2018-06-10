package com.abilix.myapp.view.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

class AlertController {

    private FragmentManager mFragmentManager;

    private String mTag;
    private int mLayoutResId;
    private View mView;
    private int mGravity;
    private int mOrientation;
    private float mWindowWidthAmount;
    private float mDimAmount;
    private boolean mCancelable;
    private int[] mSubViewResIds;

    private DialogInterface.OnClickListener mOnClickListener;
    private DialogInterface.OnCancelListener mOnCancelListener;
    private DialogInterface.OnDismissListener mOnDismissListener;
    private DialogInterface.OnKeyListener mOnKeyListener;


    public FragmentManager getFragmentManager() {
        return mFragmentManager;
    }

    public void setFragmentManager(FragmentManager mFragmentManager) {
        this.mFragmentManager = mFragmentManager;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String mTag) {
        this.mTag = mTag;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

    public void setLayoutResId(int mViewLayoutResId) {
        this.mLayoutResId = mViewLayoutResId;
    }

    public View getView() {
        return mView;
    }

    public void setView(View mView) {
        this.mView = mView;
    }

    public int getGravity() {
        return mGravity;
    }

    public void setGravity(int mGravity) {
        this.mGravity = mGravity;
    }

    public int getOrientation() {
        return mOrientation;
    }

    public void setOrientation(int mOrientation) {
        this.mOrientation = mOrientation;
    }

    public float getWindowWidthAmount() {
        return mWindowWidthAmount;
    }

    public void setWindowWidthAmount(float mWindowWidthAmount) {
        this.mWindowWidthAmount = mWindowWidthAmount;
    }

    public float getDimAmount() {
        return mDimAmount;
    }

    public void setDimAmount(float mDimAmount) {
        this.mDimAmount = mDimAmount;
    }

    public boolean ismCancelable() {
        return mCancelable;
    }

    public void setCancelable(boolean mCancelable) {
        this.mCancelable = mCancelable;
    }

    public int[] getSubViewResIds() {
        return mSubViewResIds;
    }

    public void setSubViewResIds(int[] mSubViewResIds) {
        this.mSubViewResIds = mSubViewResIds;
    }

    public static class AlertParams {
        private FragmentManager mFragmentManager;

        public String mTag = "CustomDialog";
        public int mLayoutResId = View.NO_ID;
        public View mView;
        public int mGravity = Gravity.CENTER;
        public int mOrientation = LinearLayout.VERTICAL;
        public float mWindowWidthAmount = 0.8f;
        public float mDimAmount = 0.2f;
        public boolean mCancelable = true;
        public int[] mSubViewResIds;

        public DialogInterface.OnClickListener mOnClickListener;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;

        /**
         * Interface definition for a callback to be invoked before the ListView
         * will be bound to an adapter.
         */
        public interface OnPrepareListViewListener {

            /**
             * Called before the ListView is bound to an adapter.
             * @param listView The ListView that will be shown in the dialog.
             */
            void onPrepareListView(ListView listView);
        }

        public AlertParams(Context context) {
            mContext = context;
            mCancelable = true;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void apply(android.support.v7.app.AlertController dialog) {
            if (mCustomTitleView != null) {
                dialog.setCustomTitle(mCustomTitleView);
            } else {
                if (mTitle != null) {
                    dialog.setTitle(mTitle);
                }
                if (mIcon != null) {
                    dialog.setIcon(mIcon);
                }
                if (mIconId != 0) {
                    dialog.setIcon(mIconId);
                }
                if (mIconAttrId != 0) {
                    dialog.setIcon(dialog.getIconAttributeResId(mIconAttrId));
                }
            }
            if (mMessage != null) {
                dialog.setMessage(mMessage);
            }
            if (mPositiveButtonText != null) {
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, mPositiveButtonText,
                        mPositiveButtonListener, null);
            }
            if (mNegativeButtonText != null) {
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, mNegativeButtonText,
                        mNegativeButtonListener, null);
            }
            if (mNeutralButtonText != null) {
                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, mNeutralButtonText,
                        mNeutralButtonListener, null);
            }
            // For a list, the client can either supply an array of items or an
            // adapter or a cursor
            if ((mItems != null) || (mCursor != null) || (mAdapter != null)) {
                createListView(dialog);
            }
            if (mView != null) {
                if (mViewSpacingSpecified) {
                    dialog.setView(mView, mViewSpacingLeft, mViewSpacingTop, mViewSpacingRight,
                            mViewSpacingBottom);
                } else {
                    dialog.setView(mView);
                }
            } else if (mViewLayoutResId != 0) {
                dialog.setView(mViewLayoutResId);
            }

            /*
            dialog.setCancelable(mCancelable);
            dialog.setOnCancelListener(mOnCancelListener);
            if (mOnKeyListener != null) {
                dialog.setOnKeyListener(mOnKeyListener);
            }
            */
        }

        private void createListView(final android.support.v7.app.AlertController dialog) {
            final ListView listView = (ListView) mInflater.inflate(dialog.mListLayout, null);
            final ListAdapter adapter;

            if (mIsMultiChoice) {
                if (mCursor == null) {
                    adapter = new ArrayAdapter<CharSequence>(
                            mContext, dialog.mMultiChoiceItemLayout, android.R.id.text1, mItems) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            if (mCheckedItems != null) {
                                boolean isItemChecked = mCheckedItems[position];
                                if (isItemChecked) {
                                    listView.setItemChecked(position, true);
                                }
                            }
                            return view;
                        }
                    };
                } else {
                    adapter = new CursorAdapter(mContext, mCursor, false) {
                        private final int mLabelIndex;
                        private final int mIsCheckedIndex;

                        {
                            final Cursor cursor = getCursor();
                            mLabelIndex = cursor.getColumnIndexOrThrow(mLabelColumn);
                            mIsCheckedIndex = cursor.getColumnIndexOrThrow(mIsCheckedColumn);
                        }

                        @Override
                        public void bindView(View view, Context context, Cursor cursor) {
                            CheckedTextView text = (CheckedTextView) view.findViewById(
                                    android.R.id.text1);
                            text.setText(cursor.getString(mLabelIndex));
                            listView.setItemChecked(cursor.getPosition(),
                                    cursor.getInt(mIsCheckedIndex) == 1);
                        }

                        @Override
                        public View newView(Context context, Cursor cursor, ViewGroup parent) {
                            return mInflater.inflate(dialog.mMultiChoiceItemLayout,
                                    parent, false);
                        }

                    };
                }
            } else {
                final int layout;
                if (mIsSingleChoice) {
                    layout = dialog.mSingleChoiceItemLayout;
                } else {
                    layout = dialog.mListItemLayout;
                }

                if (mCursor != null) {
                    adapter = new SimpleCursorAdapter(mContext, layout, mCursor,
                            new String[] { mLabelColumn }, new int[] { android.R.id.text1 });
                } else if (mAdapter != null) {
                    adapter = mAdapter;
                } else {
                    adapter = new android.support.v7.app.AlertController.CheckedItemAdapter(mContext, layout, android.R.id.text1, mItems);
                }
            }

            if (mOnPrepareListViewListener != null) {
                mOnPrepareListViewListener.onPrepareListView(listView);
            }

            /* Don't directly set the adapter on the ListView as we might
             * want to add a footer to the ListView later.
             */
            dialog.mAdapter = adapter;
            dialog.mCheckedItem = mCheckedItem;

            if (mOnClickListener != null) {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        mOnClickListener.onClick(dialog.mDialog, position);
                        if (!mIsSingleChoice) {
                            dialog.mDialog.dismiss();
                        }
                    }
                });
            } else if (mOnCheckboxClickListener != null) {
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        if (mCheckedItems != null) {
                            mCheckedItems[position] = listView.isItemChecked(position);
                        }
                        mOnCheckboxClickListener.onClick(
                                dialog.mDialog, position, listView.isItemChecked(position));
                    }
                });
            }

            // Attach a given OnItemSelectedListener to the ListView
            if (mOnItemSelectedListener != null) {
                listView.setOnItemSelectedListener(mOnItemSelectedListener);
            }

            if (mIsSingleChoice) {
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            } else if (mIsMultiChoice) {
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            }
            dialog.mListView = listView;
        }
    }

}
