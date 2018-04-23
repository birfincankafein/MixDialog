package com.birfincankafein.mixdialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import java.util.LinkedHashMap;

public class MixDialogParams {
    protected Context mContext;
    protected int mTheme = -1;
    protected CharSequence mTitle;
    protected CharSequence mMessage;
    protected View mCustomTitleView;
    protected Drawable mIcon;
    protected CharSequence mPositiveButtonText;
    protected CharSequence mNegativeButtonText;
    protected CharSequence mNeutralButtonText;
    protected boolean mCancelable = true;
    protected MixDialog.onDialogEventListener mListener;
    protected LinkedHashMap<String, InputItemGroup> mInputItemGroups = new LinkedHashMap<>();
    protected LinkedHashMap<String, CheckItemGroup> mSingleChoiceItemGroups = new LinkedHashMap<>();
    protected LinkedHashMap<String, CheckItemGroup> mMultiChoiceItemGroups = new LinkedHashMap<>();
}
