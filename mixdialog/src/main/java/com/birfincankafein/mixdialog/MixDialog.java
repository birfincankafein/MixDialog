package com.birfincankafein.mixdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.design.widget.TextInputLayout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.List;


/**
 * MixDialog is a dialog utility that allow you to show title text, message text, input areas,
 * single and multi choice items at the same time.
 *
 * @author Metehan Toksoy
 */
public class MixDialog {
    /**
     * Mix dialog params that contains data from {@link Builder}
     */
    private final MixDialogParams mParams;

    /**
     * Created {@link android.app.AlertDialog} instance
     */
    private AlertDialog mAlertDialog = null;

    /**
     * Container for message, input, single-choice and multi-choice item views
     */
    private LinearLayout mLinearLayout_Root;

    /**
     * TextView to show message
     */
    private TextView mTextView_Message;

    /**
     * Container for {@link InputItemGroup} views
     */
    private LinearLayout mLinearLayout_InputItemContainer;

    /**
     * Container for Single and Multiple {@link CheckItemGroup} views
     */
    private LinearLayout mLinearLayout_CheckItemContainer;

    /**
     * Container for {@link KeyValueItemGroup} views
     */
    private LinearLayout mLinearLayout_KeyValueItemContainer;

    /**
     * LayoutParams for ItemGroups.
     */
    private LinearLayout.LayoutParams mLayoutParams_ItemGroups;

    /**
     * LayoutParams for each ItemGroup.
     */
    private LinearLayout.LayoutParams mLayoutParams_ItemGroup;

    /**
     * LayoutParams for each Item.
     */
    private LinearLayout.LayoutParams mLayoutParams_Items;

    /**
     * LayoutParams for each ItemGroup's title.
     */
    private LinearLayout.LayoutParams mLayoutParams_Title;

    /**
     * LayoutParams for each InputItemGroup title line's.
     */
    private LinearLayout.LayoutParams mLayoutParams_TitleLine;

    /**
     * Dummy dialog listener for AlertDialog buttons. Allows that auto handle buttons' visibility
     * by {@link AlertDialog}
     */
    private final Dialog.OnClickListener listener = new Dialog.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialog, int which) {}
    };

    /**
     * {@link AlertDialog}'s Positive Button's click listener. Allows that dismiss or not dismiss
     * the AlertDialog based on developer's choice.
     * @see onDialogEventListener
     */
    private final View.OnClickListener mClickListener_PositiveButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mParams.mListener != null){
                if(mParams.mListener.onDialogButtonClick(ButtonType.POSITIVE, getCurrentInstance())){
                    mAlertDialog.dismiss();
                }
            }
            else {
                mAlertDialog.dismiss();
            }
        }
    };

    /**
     * {@link AlertDialog}'s Negative Button's click listener. Allows that dismiss or not dismiss
     * the AlertDialog based on developer's choice.
     * @see onDialogEventListener
     */
    private final View.OnClickListener mClickListener_NegativeButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mParams.mListener != null){
                if(mParams.mListener.onDialogButtonClick(ButtonType.NEGATIVE, getCurrentInstance())){
                    mAlertDialog.dismiss();
                }
            }
            else {
                mAlertDialog.dismiss();
            }

        }
    };


    /**
     * {@link AlertDialog}'s Neutral Button's click listener. Allows that dismiss or not dismiss
     * the AlertDialog based on developer's choice.
     * @see onDialogEventListener
     */
    private final View.OnClickListener mClickListener_NeutralButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mParams.mListener != null){
                if(mParams.mListener.onDialogButtonClick(ButtonType.NEUTRAL, getCurrentInstance())){
                    mAlertDialog.dismiss();
                }
            }
            else {
                mAlertDialog.dismiss();
            }
        }
    };

    /**
     * Private constructor to force developer to use {@link Builder}
     * @param params MixDialog params generated by {@link Builder}
     * @see Builder
     */
    private MixDialog(MixDialogParams params) {
        this.mParams = params;


        /*
         * @TODO Make ItemGroup's margin values customizable
         */
        mLayoutParams_ItemGroups = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams_ItemGroups.topMargin = (int) mParams.mContext.getResources().getDimension(R.dimen.margin_top_groups);

        /*
         * @TODO Make ItemGroup title view's margin values customizable
         */
        mLayoutParams_Title = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        /*
         * @TODO Make InputItemGroup title view line's margin values customizable
         */
        mLayoutParams_TitleLine = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) mParams.mContext.getResources().getDimension(R.dimen.height_title_line));
        mLayoutParams_TitleLine.topMargin = (int) mParams.mContext.getResources().getDimension(R.dimen.margin_top_title_line);
        mLayoutParams_TitleLine.rightMargin = (int) mParams.mContext.getResources().getDimension(R.dimen.margin_end_title_line);

        mLayoutParams_ItemGroup = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams_ItemGroup.topMargin = (int) mParams.mContext.getResources().getDimension(R.dimen.margin_top_group);

        mLayoutParams_Items = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayoutParams_Items.topMargin = (int) mParams.mContext.getResources().getDimension(R.dimen.margin_top_group_items);

        mLinearLayout_Root = (LinearLayout) LayoutInflater.from(mParams.mContext).inflate(R.layout.layout_dialog, null);
        mTextView_Message = mLinearLayout_Root.findViewById(R.id.textView_dialog_message);
        mLinearLayout_InputItemContainer = mLinearLayout_Root.findViewById(R.id.linearLayout_input_items);
        mLinearLayout_CheckItemContainer = mLinearLayout_Root.findViewById(R.id.linearLayout_check_items);
        mLinearLayout_KeyValueItemContainer = mLinearLayout_Root.findViewById(R.id.linearLayout_keyvalue_items);

        /*
         * Setting message to it's view.
         * @TODO Make message view customizable.
         */
        if(mParams.mMessage != null){
            mTextView_Message.setText(mParams.mMessage);
        }
        else{
            mTextView_Message.setVisibility(View.GONE);
        }

        /*
         * If any InputItemGroup created, show it!
         */
        if(mParams.mKeyValueItemGroups != null && !mParams.mKeyValueItemGroups.isEmpty()){
            /*
             * Setting margin to all KeyValueItemGroup view's container
             */
            ((LinearLayout.LayoutParams) mLinearLayout_KeyValueItemContainer.getLayoutParams()).topMargin = (int) mParams.mContext.getResources().getDimension(R.dimen.margin_top_groups);
            createKeyValueGroupItems();
        }
        else{
            mLinearLayout_KeyValueItemContainer.setVisibility(View.GONE);
        }

        if(mParams.mInputItemGroups != null && !mParams.mInputItemGroups.isEmpty()){
            ((LinearLayout.LayoutParams) mLinearLayout_InputItemContainer.getLayoutParams()).topMargin = (int) mParams.mContext.getResources().getDimension(R.dimen.margin_top_groups);
            createInputGroupItems();
        }
        else{
            mLinearLayout_InputItemContainer.setVisibility(View.GONE);
        }

        if(mParams.mSingleChoiceItemGroups != null && !mParams.mSingleChoiceItemGroups.isEmpty()){
            ((LinearLayout.LayoutParams) mLinearLayout_CheckItemContainer.getLayoutParams()).topMargin = (int) mParams.mContext.getResources().getDimension(R.dimen.margin_top_groups);
            createSingleChoiceItems();
        }

        if(mParams.mMultiChoiceItemGroups != null && !mParams.mMultiChoiceItemGroups.isEmpty()){
            ((LinearLayout.LayoutParams) mLinearLayout_CheckItemContainer.getLayoutParams()).topMargin = (int) mParams.mContext.getResources().getDimension(R.dimen.margin_top_groups);
            createMultiChoiceItems();
        }

        if( (mParams.mMultiChoiceItemGroups == null || mParams.mMultiChoiceItemGroups.isEmpty()) && (mParams.mSingleChoiceItemGroups == null || mParams.mSingleChoiceItemGroups.isEmpty())){
            mLinearLayout_CheckItemContainer.setVisibility(View.GONE);
        }
    }

    /**
     * Show MixDialog.
     */
    public void show(){
        if(mAlertDialog != null && mAlertDialog.isShowing()){
            mAlertDialog.hide();
        }

        mAlertDialog = (mParams.mTheme == -1 ? new AlertDialog.Builder(mParams.mContext) : new AlertDialog.Builder(mParams.mContext, mParams.mTheme))
                .setTitle(mParams.mTitle)
                .setCustomTitle(mParams.mCustomTitleView)
                .setIcon(mParams.mIcon)
                .setPositiveButton(mParams.mPositiveButtonText, listener)
                .setNeutralButton(mParams.mNeutralButtonText, listener)
                .setNegativeButton(mParams.mNegativeButtonText, listener)
                .setCancelable(mParams.mCancelable)
                .setView(mLinearLayout_Root)
                .create();
        mAlertDialog.show();

        Button mButton_Positive = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        if(mButton_Positive != null){
            mButton_Positive.setOnClickListener(mClickListener_PositiveButton);
        }

        Button mButton_Negative = mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        if(mButton_Negative != null){
            mButton_Negative.setOnClickListener(mClickListener_NegativeButton);
        }

        Button mButton_Neutral = mAlertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        if(mButton_Neutral != null){
            mButton_Neutral.setOnClickListener(mClickListener_NeutralButton);
        }
    }

    /**
     * Hides MixDialog
     */
    public void hide(){
        if(mAlertDialog != null && mAlertDialog.isShowing()){
            mAlertDialog.hide();
        }
    }

    /**
     * Gets created {@link AlertDialog} instance. Changing from the outside is not recommended.
     * @return Created {@link AlertDialog} instance
     */
    public AlertDialog getAlertDialog() {
        return mAlertDialog;
    }

    /**
     * Create and add views for all KeyValueItemGroups.
     */
    private void createKeyValueGroupItems() {
        for (KeyValueItemGroup keyValueItemGroup : mParams.mKeyValueItemGroups.values()) {
            if (!keyValueItemGroup.mKeyValueItems.isEmpty()) {
                keyValueItemGroup.attachedMixDialog = this;
                LinearLayout.LayoutParams mLayoutParams_TextViewKey = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mLayoutParams_TextViewKey.weight = keyValueItemGroup.mKeyValueRatio;
                LinearLayout.LayoutParams mLayoutParams_TextViewValue = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mLayoutParams_TextViewValue.weight = 1;

                LinearLayout mLinearLayout_KeyValueItemGroup = new LinearLayout(mParams.mContext);
                mLinearLayout_KeyValueItemGroup.setOrientation(LinearLayout.VERTICAL);

                if (keyValueItemGroup.showGroupNameAsHeader) {
                    createHeaderView(keyValueItemGroup.groupName, mLinearLayout_KeyValueItemGroup);
                }
                for(String keyValueItemKey : keyValueItemGroup.mKeyValueItems.keySet()){
                    KeyValueItem keyValueItem = keyValueItemGroup.mKeyValueItems.get(keyValueItemKey);
                    LinearLayout mLinearLayout_KeyValueItem = new LinearLayout(mParams.mContext);
                    mLinearLayout_KeyValueItem.setOrientation(LinearLayout.HORIZONTAL);

                    TextView mTextView_Key = new TextView(mParams.mContext);
                    mTextView_Key.setText(keyValueItemKey);
                    mTextView_Key.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.START);
                    mTextView_Key.setTypeface(Typeface.DEFAULT_BOLD);

                    TextView mTextView_Value = new TextView(mParams.mContext);
                    mTextView_Value.setText(keyValueItem.mValue);
                    mTextView_Key.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.START);

                    keyValueItem.mTextView_Key = mTextView_Key;
                    keyValueItem.mTextView_Value = mTextView_Value;

                    mLinearLayout_KeyValueItem.addView(mTextView_Key, mLayoutParams_TextViewKey);
                    mLinearLayout_KeyValueItem.addView(mTextView_Value, mLayoutParams_TextViewValue);

                    mLinearLayout_KeyValueItemGroup.addView(mLinearLayout_KeyValueItem, mLayoutParams_Items);
                }

                mLinearLayout_KeyValueItemContainer.addView(mLinearLayout_KeyValueItemGroup, mLayoutParams_ItemGroups);
            }
        }
    }

    /**
     * Create and add views for all InputGroupItems.
     */
    private void createInputGroupItems() {
        /*
         * LayoutParams for each InputItem's EditText
         */
        TextInputLayout.LayoutParams mLayoutParams_EditText = new TextInputLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for (InputItemGroup inputItemGroup : mParams.mInputItemGroups.values()) {
            if (!inputItemGroup.mInputItems.isEmpty()) {
                inputItemGroup.attachedMixDialog = this;
                LinearLayout mLinearLayout_InputItemGroup = new LinearLayout(mParams.mContext);
                mLinearLayout_InputItemGroup.setOrientation(LinearLayout.VERTICAL);
                if (inputItemGroup.showGroupNameAsHeader) {
                    createHeaderView(inputItemGroup.groupName, mLinearLayout_InputItemGroup);
                }

                for (String inputItemHint : inputItemGroup.mInputItems.keySet()) {
                    InputItem inputItem = inputItemGroup.mInputItems.get(inputItemHint);
                    TextInputLayout textInputLayout = new TextInputLayout(mParams.mContext);
                    EditText editText = new EditText(mParams.mContext);
                    editText.setHint(inputItem.mHint);
                    editText.setText(inputItem.mValue);
                    inputItem.mEditText = editText;
                    inputItem.mTextInputLayout = textInputLayout;
                    textInputLayout.addView(editText, mLayoutParams_EditText);
                    mLinearLayout_InputItemGroup.addView(textInputLayout, mLayoutParams_Items);
                }
                mLinearLayout_InputItemContainer.addView(mLinearLayout_InputItemGroup, mLayoutParams_ItemGroups);
            }
        }
    }

    /**
     * Create and add views for all multi-choice CheckItemGroups.
     */
    private void createMultiChoiceItems() {
        for (CheckItemGroup checkItemGroup : mParams.mMultiChoiceItemGroups.values()) {
            if (!checkItemGroup.mCheckItems.isEmpty()) {
                checkItemGroup.attachedMixDialog = this;
                LinearLayout mLinearLayout_CheckItemGroup = new LinearLayout(mParams.mContext);
                mLinearLayout_CheckItemGroup.setOrientation(LinearLayout.VERTICAL);
                if (checkItemGroup.showGroupNameAsHeader) {
                    createHeaderView(checkItemGroup.groupName, mLinearLayout_CheckItemGroup);
                }

                LinearLayout mLinearLayout_CheckGroup = new LinearLayout(mParams.mContext);
                mLinearLayout_CheckGroup.setOrientation(LinearLayout.VERTICAL);
                for (CheckItem checkItem : checkItemGroup.mCheckItems.values()) {
                    CheckBox mCheckbox = new CheckBox(mParams.mContext);
                    mCheckbox.setId(checkItem.id);
                    mCheckbox.setText(checkItem.mText);
                    mCheckbox.setChecked(checkItem.mChecked);
                    mCheckbox.setOnCheckedChangeListener(checkItemGroup.mOnCheckChangeListener_Multi);
                    checkItem.mCheckView = mCheckbox;
                    mLinearLayout_CheckGroup.addView(mCheckbox, mLayoutParams_Items);
                }

                mLinearLayout_CheckItemGroup.addView(mLinearLayout_CheckGroup, mLayoutParams_ItemGroup);
                mLinearLayout_CheckItemContainer.addView(mLinearLayout_CheckItemGroup, mLayoutParams_ItemGroups);
            }
        }
    }

    /**
     * Create and add views for all single-choice CheckItemGroups.
     */
    private void createSingleChoiceItems() {
        for (CheckItemGroup checkItemGroup : mParams.mSingleChoiceItemGroups.values()) {
            if (!checkItemGroup.mCheckItems.isEmpty()) {
                checkItemGroup.attachedMixDialog = this;
                LinearLayout mLinearLayout_CheckItemGroup = new LinearLayout(mParams.mContext);
                mLinearLayout_CheckItemGroup.setOrientation(LinearLayout.VERTICAL);
                if (checkItemGroup.showGroupNameAsHeader) {
                    createHeaderView(checkItemGroup.groupName, mLinearLayout_CheckItemGroup);
                }

                RadioGroup mRadioGroup = new RadioGroup(mParams.mContext);
                mRadioGroup.setOrientation(RadioGroup.VERTICAL);
                for (CheckItem checkItem : checkItemGroup.mCheckItems.values()) {
                    RadioButton mRadioButton = new RadioButton(mParams.mContext);
                    mRadioButton.setId(checkItem.id);
                    mRadioButton.setText(checkItem.mText);
                    mRadioButton.setChecked(checkItem.mChecked);
                    checkItem.mCheckView = mRadioButton;
                    mRadioGroup.addView(mRadioButton, mLayoutParams_Items);
                }
                mRadioGroup.setOnCheckedChangeListener(checkItemGroup.mOnCheckChangeListener_Single);
                mLinearLayout_CheckItemGroup.addView(mRadioGroup, mLayoutParams_ItemGroup);
                mLinearLayout_CheckItemContainer.addView(mLinearLayout_CheckItemGroup, mLayoutParams_ItemGroups);
            }
        }
    }

    /**
     * Create title view and add it to the layout
     * @param title title string
     * @param mLinearLayout_Container layout to add title view and line
     */
    private void createHeaderView(String title, LinearLayout mLinearLayout_Container) {
        TextView mTextView_InputHeader = new TextView(mParams.mContext);
        mTextView_InputHeader.setText(title);
        View headerLine = new View(mParams.mContext);
        headerLine.setBackgroundResource(R.color.header_line);
        mLinearLayout_Container.addView(mTextView_InputHeader, mLayoutParams_Title);
        mLinearLayout_Container.addView(headerLine, mLayoutParams_TitleLine);
    }

    /**
     * Getter for current instance for especially listener.
     * @return current instance of MixDialog
     */
    private MixDialog getCurrentInstance(){
        return this;
    }

    /**
     * Getter for InputItem in given group name
     * @param groupName group name of the InputItem
     * @param hint hint text of the InputItem
     * @return InputItem with given group name and hint, null if not found
     */
    @Nullable
    public InputItem getInputItem(String groupName, String hint){
        if(mParams.mInputItemGroups.containsKey(groupName)){
            InputItemGroup inputItemGroup = mParams.mInputItemGroups.get(groupName);
            if(inputItemGroup.mInputItems.containsKey(hint)){
                return inputItemGroup.mInputItems.get(hint);
            }
        }
        return null;
    }

    /**
     * Getter for InputItemGroup in given group name
     * @param groupName group name of the InputItems
     * @return InputItemGroup with given group name, null if not found
     */
    @Nullable
    public InputItemGroup getInputItemGroup(String groupName){
        if(mParams.mInputItemGroups.containsKey(groupName)){
            return mParams.mInputItemGroups.get(groupName);
        }
        return null;
    }

    /**
     * Getter for single-choice CheckItem in given group name
     * @param groupName group name of the CheckItem
     * @param text text of the CheckItem
     * @return CheckItem from single-choice list with given group name and text, null if not found
     */
    @Nullable
    public CheckItem getSingleCheckItem(String groupName, String text){
        if(mParams.mSingleChoiceItemGroups.containsKey(groupName)){
            CheckItemGroup checkItemGroup = mParams.mSingleChoiceItemGroups.get(groupName);
            if(checkItemGroup.mCheckItems.containsKey(text)){
                return checkItemGroup.mCheckItems.get(text);
            }
        }
        return null;
    }

    /**
     * Getter for single-choice CheckItemGroup in given group name
     * @param groupName group name of the CheckItem
     * @return CheckItemGroup from single-choice list with given group name, null if not found
     */
    @Nullable
    public CheckItemGroup getSingleCheckItemGroup(String groupName){
        if(mParams.mSingleChoiceItemGroups.containsKey(groupName)){
            return mParams.mSingleChoiceItemGroups.get(groupName);
        }
        return null;
    }

    /**
     * Getter for checked item from single-choice list in given group name
     * @param groupName group name to search for checked one.
     * @return checked CheckItem from single-choice list with given group name and text, null if not found.
     */
    @Nullable
    public CheckItem getSingleCheckedItem(String groupName){
        if(mParams.mSingleChoiceItemGroups.containsKey(groupName)){
            CheckItemGroup checkItemGroup = mParams.mSingleChoiceItemGroups.get(groupName);
            return checkItemGroup.getCheckedItems().get(0);
        }
        return null;
    }

    /**
     * Getter for multi-choice CheckItem in given group name
     * @param groupName group name of the CheckItem
     * @param text text of the CheckItem
     * @return CheckItem from multi-choice list with given group name and text, null if not found
     */
    @Nullable
    public CheckItem getMultiCheckItem(String groupName, String text){
        if(mParams.mMultiChoiceItemGroups.containsKey(groupName)){
            CheckItemGroup checkItemGroup = mParams.mMultiChoiceItemGroups.get(groupName);
            if(checkItemGroup.mCheckItems.containsKey(text)){
                return checkItemGroup.mCheckItems.get(text);
            }
        }
        return null;
    }

    /**
     * Getter for multi-choice CheckItemGroup in given group name
     * @param groupName group name of the CheckItem
     * @return CheckItemGroup from multi-choice list with given group name, null if not found
     */
    @Nullable
    public CheckItemGroup getMultiCheckItemGroup(String groupName){
        if(mParams.mMultiChoiceItemGroups.containsKey(groupName)){
            return mParams.mMultiChoiceItemGroups.get(groupName);
        }
        return null;
    }

    /**
     * Getter for checked items from multi-choice list in given group name
     * @param groupName group name to search for checked ones.
     * @return checked CheckItems from multi-choice list with given group name and text, null if not found.
     */
    @Nullable
    public List<CheckItem> getMultiCheckedItems(String groupName){
        if(mParams.mMultiChoiceItemGroups.containsKey(groupName)){
            return mParams.mMultiChoiceItemGroups.get(groupName).getCheckedItems();
        }
        return null;
    }

    /**
     * Getter for KeyValueItem in given group name
     * @param groupName group name of the KeyValueItem
     * @param key key of the KeyValueItem
     * @return KeyValueItem from KeyValueItemGroups list with given group name and key, null if not found
     */
    @Nullable
    public KeyValueItem getKeyValueItem(String groupName, String key){
        if(mParams.mKeyValueItemGroups.containsKey(groupName)){
            KeyValueItemGroup keyValueItemGroup = mParams.mKeyValueItemGroups.get(groupName);
            if(keyValueItemGroup.mKeyValueItems.containsKey(key)){
                return keyValueItemGroup.mKeyValueItems.get(key);
            }
        }
        return null;
    }

    /**
     * Getter for KeyValueItemGroup in given group name
     * @param groupName group name of the KeyValueItems
     * @return KeyValueItemGroup with given group name, null if not found
     */
    @Nullable
    public KeyValueItemGroup getKeyValueItemGroup(String groupName){
        if(mParams.mKeyValueItemGroups.containsKey(groupName)){
            return mParams.mKeyValueItemGroups.get(groupName);
        }
        return null;
    }

    /**
     * Updates MixDialog's message text
     * @param newMessage MixDialog's message body text
     */
    public void updateMessage(String newMessage){
        if(mTextView_Message != null)
            this.mTextView_Message.setText(newMessage);
    }

    /**
     * Builder for a MixDialog
     */
    public static class Builder{
        protected MixDialogParams params;

        /**
         * Creates a builder for a MixDialog.
         * @param context The parent context
         */
        public Builder(@NonNull Context context){
            this(context, -1);
        }

        /**
         * Creates a builder for a MixDialog.
         * @param context The parent context
         * @param themeResId Theme resource that will be used as AlertDialog theme.
         */
        public Builder(@NonNull Context context, @StyleRes int themeResId){
            this.params = new MixDialogParams();
            params.mContext = context;
            params.mTheme = themeResId;
        }

        /**
         * Create a builder from existing params. Private for internal access
         * @param params Builder params of the builder
         */
        private Builder(MixDialogParams params){
            this.params = params;
        }

        /**
         * Creates Builder instance from existing MixDialog.
         * @param dialog Existing dialog
         * @return new Builder from MixDialog
         */
        public Builder fromMixDialog(MixDialog dialog){
            return new Builder(dialog.mParams);
        }

        /**
         * Setter for the title.
         * @param title Title displayed in the {@link AlertDialog}
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setTitle(@Nullable CharSequence title){
            params.mTitle = title;
            return this;
        }

        /**
         * Setter for the title from resources.
         * @param titleResourceId Title text's resource id displayed in the {@link AlertDialog}
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setTitle(@StringRes int titleResourceId){
            return setTitle(params.mContext.getResources().getString(titleResourceId));
        }

        /**
         * Setter for the message.
         * @param message Message text displayed in the {@link AlertDialog}
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setMessage(@Nullable CharSequence message){
            params.mMessage = message;
            return this;
        }

        /**
         * Setter for the message from resources.
         * @param messageResourceId Message text's resource id displayed in the {@link AlertDialog}
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setMessage(@StringRes int messageResourceId){
            return setMessage(params.mContext.getResources().getString(messageResourceId));
        }

        /**
         * Setter for the custom title view.
         * @param customTitleView title view displayed in the {@link AlertDialog}
         * @return This Builder object to allow for chaining of calls to set methods
         * @see AlertDialog.Builder#setCustomTitle(View)
         */
        public Builder setCustomTitle(@Nullable View customTitleView) {
            params.mCustomTitleView = customTitleView;
            return this;
        }

        /**
         * Setter for the icon.
         * @param icon {@link Drawable} icon to be used in the title.
         * @return This Builder object to allow for chaining of calls to set methods
         * @see AlertDialog.Builder#setIcon(Drawable)
         */
        public Builder setIcon(@Nullable Drawable icon) {
            params.mIcon = icon;
            return this;
        }

        /**
         * Setter for the icon from resources.
         * @param iconId {@link Drawable} icon resource to be used in the title.
         * @return This Builder object to allow for chaining of calls to set methods
         * @see AlertDialog.Builder#setIcon(int)
         */
        public Builder setIcon(@DrawableRes int iconId) {
            return setIcon(params.mContext.getResources().getDrawable(iconId));
        }

        /**
         * Setter for an icon attribute.
         * @param attrId ID of a theme attribute that points to a drawable resource.
         * @return This Builder object to allow for chaining of calls to set methods
         * @see AlertDialog.Builder#setIconAttribute(int)
         */
        public Builder setIconAttribute(@AttrRes int attrId) {
            TypedValue out = new TypedValue();
            params.mContext.getTheme().resolveAttribute(attrId, out, true);
            return this.setIcon(out.resourceId);
        }

        /**
         * Add button to the dialog with default button texts.
         * @param buttonType Type of the button to add
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addButton(ButtonType buttonType){
            switch (buttonType){
                case POSITIVE:
                    return setPositiveButtonText(android.R.string.ok);
                case NEGATIVE:
                    return setNegativeButtonText(android.R.string.cancel);
                default:
                    return setNeutralButtonText(R.string.dialog_button_neutral);
            }
        }

        /**
         * Add button to the dialog.
         * @param buttonType Type of the button to add
         * @param buttonText Button text
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addButton(ButtonType buttonType, @Nullable CharSequence buttonText){
            switch (buttonType){
                case POSITIVE:
                    return setPositiveButtonText(buttonText);
                case NEGATIVE:
                    return setNegativeButtonText(buttonText);
                default:
                    return setNeutralButtonText(buttonText);
            }
        }

        /**
         * Add button to the dialog.
         * @param buttonType Type of the button to add
         * @param buttonTextResourceId Button text from resources
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addButton(ButtonType buttonType, @StringRes int buttonTextResourceId){
            switch (buttonType){
                case POSITIVE:
                    return setPositiveButtonText(buttonTextResourceId);
                case NEGATIVE:
                    return setNegativeButtonText(buttonTextResourceId);
                default:
                    return setNeutralButtonText(buttonTextResourceId);
            }
        }

        /**
         * Setter for the positive button of the dialog.
         * @param positiveButtonText Button text
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setPositiveButtonText(@Nullable CharSequence positiveButtonText){
            params.mPositiveButtonText = positiveButtonText;
            return this;
        }

        /**
         * Setter for the positive button of the dialog.
         * @param positiveButtonTextResourceId Button text from resources
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setPositiveButtonText(@StringRes int positiveButtonTextResourceId){
            return setPositiveButtonText(params.mContext.getResources().getString(positiveButtonTextResourceId));
        }

        /**
         * Setter for the negative button of the dialog.
         * @param negativeButtonText Button text
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setNegativeButtonText(@Nullable CharSequence negativeButtonText){
            params.mNegativeButtonText = negativeButtonText;
            return this;
        }

        /**
         * Setter for the negative button of the dialog.
         * @param negativeButtonTextResourceId Button text from resources
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setNegativeButtonText(@StringRes int negativeButtonTextResourceId){
            return setNegativeButtonText(params.mContext.getResources().getString(negativeButtonTextResourceId));
        }

        /**
         * Setter for the neutral button of the dialog.
         * @param neutralButtonText Button text
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setNeutralButtonText(@Nullable CharSequence neutralButtonText) {
            params.mNeutralButtonText = neutralButtonText;
            return this;
        }

        /**
         * Setter for the neutral button of the dialog.
         * @param neutralButtonResourceId Button text from resources
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setNeutralButtonText(@StringRes int neutralButtonResourceId) {
            return setNeutralButtonText(params.mContext.getResources().getString(neutralButtonResourceId));
        }

        /**
         * Setter for cancelable attribute.  Default is true.
         *
         * @param cancelable Set true for make dialog closable by touching outside
         * @return This Builder object to allow for chaining of calls to set methods
         * @see AlertDialog.Builder#setCancelable(boolean)
         */
        public Builder setCancelable(boolean cancelable) {
            params.mCancelable = cancelable;
            return this;
        }

        /**
         * Setter for MixDialog event listener.
         * @param listener Event listener
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnDialogEventListener(onDialogEventListener listener){
            params.mListener = listener;
            return this;
        }

        /**
         * Add InputItemGroup to the Dialog.
         * @return This Builder object to allow for chaining of calls to set methods
         * @see InputItemGroup
         */
        public Builder addInputItemGroup(InputItemGroup inputItemGroup){
            params.mInputItemGroups.put(inputItemGroup.groupName, inputItemGroup);
            return this;
        }

        /**
         * Add InputItemGroup to the Dialog.
         * @return Builder for InputItemGroup to allow for chaining of calls to set methods.
         * You should call {@link InputItemGroup.Builder#buildWithParent()} for continue to {@link MixDialog.Builder}
         * @see InputItemGroup
         * @see InputItemGroup.Builder
         */
        public InputItemGroup.Builder addInputItemGroup(String groupName){
            return new InputItemGroup.Builder(this, groupName);
        }

        /**
         * Add CheckItemGroup to the Dialog.
         * @return This Builder object to allow for chaining of calls to set methods
         * @see CheckItemGroup
         */
        public Builder addCheckItemGroup(CheckItemGroup checkItemGroup){
            if(checkItemGroup.mSingleChoice) {
                params.mSingleChoiceItemGroups.put(checkItemGroup.groupName, checkItemGroup);
            }
            else{
                params.mMultiChoiceItemGroups.put(checkItemGroup.groupName, checkItemGroup);
            }
            return this;
        }

        /**
         * Add CheckItemGroup to the Dialog.
         * @return Builder for CheckItemGroup to allow for chaining of calls to set methods.
         * You should call {@link CheckItemGroup.Builder#buildWithParent()} for continue to {@link MixDialog.Builder}
         * @see CheckItemGroup
         * @see CheckItemGroup.Builder
         */
        public CheckItemGroup.Builder addCheckItemGroup(String groupName){
            return new CheckItemGroup.Builder(this, groupName);
        }

        /**
         * Add KeyValueItemGroup to the Dialog.
         * @return This Builder object to allow for chaining of calls to set methods
         * @see InputItemGroup
         */
        public Builder addKeyValueItemGroup(KeyValueItemGroup keyValueItemGroup){
            params.mKeyValueItemGroups.put(keyValueItemGroup.groupName, keyValueItemGroup);
            return this;
        }

        /**
         * Add KeyValueItemGroup to the Dialog.
         * @return Builder for InputItemGroup to allow for chaining of calls to set methods.
         * You should call {@link KeyValueItemGroup.Builder#buildWithParent()} for continue to {@link MixDialog.Builder}
         * @see KeyValueItemGroup
         * @see KeyValueItemGroup.Builder
         */
        public KeyValueItemGroup.Builder addKeyValueItemGroup(String groupName){
            return new KeyValueItemGroup.Builder(this, groupName);
        }

        /**
         * Builds MixDialog with these values.
         * @return
         */
        public MixDialog build(){
            return new MixDialog(params);
        }
    }

    /**
     * Button types of MixDialog
     */
    public enum ButtonType{
        /**
         * Button type of the MixDialog
         * @see AlertDialog#BUTTON_POSITIVE
         */
        POSITIVE,

        /**
         * Button type of the MixDialog
         * @see AlertDialog#BUTTON_NEGATIVE
         */
        NEGATIVE,

        /**
         * Button type of the MixDialog
         * @see AlertDialog#BUTTON_NEUTRAL
         */
        NEUTRAL
    }

    /**
     * Dialog event listener of the MixDialog
     */
    public interface onDialogEventListener {
        /**
         * Will be triggered when any button clicked.
         * @param buttonType Button that causes this event
         * @param dialog The MixDialog instance.
         * @return true to dismiss the dialog, false otherwise
         */
        boolean onDialogButtonClick(ButtonType buttonType, MixDialog dialog);
    }
}
