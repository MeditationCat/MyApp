package com.abilix.myapp.view.dialog;

import android.view.View;

public interface DialogViewInterface {

    interface OnBindViewListener {
        void bindView(BindViewHolder viewHolder);
    }

    interface OnViewClickListener {
        void onViewClick(BindViewHolder viewHolder, View view, CommonDialog dialog);
    }

}
