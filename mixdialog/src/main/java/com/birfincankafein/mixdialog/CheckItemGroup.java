package com.birfincankafein.mixdialog;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.StringRes;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * CheckItemGroup is an item group that represent with the Checkboxes inside the LinearLayout or
 * RadioGroup based on {@link CheckItemGroup#mSingleChoice}
 *
 * @author Metehan Toksoy
 */
public class CheckItemGroup implements ItemGroup {

    /**
     * Attached MixDialog instance
     */
    protected MixDialog attachedMixDialog;

    /**
     * All check items.
     */
    protected LinkedHashMap<String, CheckItem> mCheckItems;

    /**
     * Name of the this group. Can be used as title based on {@link CheckItemGroup#showGroupNameAsHeader}.
     */
    protected String groupName;

    /**
     * If true the {@link CheckItemGroup#groupName} will be shown as title.
     */
    protected boolean showGroupNameAsHeader;

    /**
     * Defines CheckItemGroup behaviour, CheckItem views and listener. If this is true,
     * only one item can be selectable for a group
     */
    protected boolean mSingleChoice;

    /**
     * Check change listener for this group
     * @see onCheckChangeListener
     */
    protected final onCheckChangeListener mListener;

    /**
     * Listener for single-choice mode
     */
    protected RadioGroup.OnCheckedChangeListener mOnCheckChangeListener_Single = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(mListener != null){
                for(CheckItem checkItem : mCheckItems.values()){
                    if(checkItem.id == checkedId){
                        mListener.onCheckChange(attachedMixDialog, checkItem, checkItem.mCheckView.isChecked());
                        break;
                    }
                }
            }
        }
    };

    /**
     * Listener for multi-choice mode
     */
    protected CompoundButton.OnCheckedChangeListener mOnCheckChangeListener_Multi = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(mListener != null){
                for(CheckItem checkItem : mCheckItems.values()){
                    if(checkItem.id == buttonView.getId()){
                        mListener.onCheckChange(attachedMixDialog, checkItem, checkItem.mCheckView.isChecked());
                        break;
                    }
                }
            }
        }
    };

    private CheckItemGroup(String groupName, boolean singleChoice, boolean showGroupNameAsHeader, LinkedHashMap<String, CheckItem> mCheckItems, onCheckChangeListener listener){
        this.groupName = groupName;
        this.mSingleChoice = singleChoice;
        this.mCheckItems = mCheckItems;
        this.mListener = listener;
        this.showGroupNameAsHeader = showGroupNameAsHeader;
    }

    /**
     * Getter for all checked items in this group.
     * @return If exists checked items, zero-length {@link List} otherwise.
     */
    public List<CheckItem> getCheckedItems() {
        List<CheckItem> checkItems = new ArrayList<>();
        for(CheckItem checkItem : mCheckItems.values()){
            if(checkItem.isChecked()){
                checkItems.add(checkItem);
            }
        }
        return checkItems;
    }

    /**
     * Getter for all check items in this group.
     * @return All check items inside this group.
     */
    @Override
    public List<CheckItem> getItems() {
        return new ArrayList<CheckItem>(mCheckItems.values());
    }

    /**
     * Getter for group name
     * @return groupName of this ItemGroup
     */
    @Override
    public String getGroupName() {
        return groupName;
    }

    /**
     * Builder for a CheckItemGroup
     */
    public static class Builder {
        /**
         * Builder of the MixDialog.
         */
        private MixDialog.Builder mixDialogBuilder;
        private LinkedHashMap<String, CheckItem> mCheckItems = new LinkedHashMap<String, CheckItem>();
        private boolean mSingleChoice = false;
        private Context mContext;
        private onCheckChangeListener listener;
        private String groupName;
        private boolean showGroupNameAsHeader = false;

        /**
         * Creates a builder for a MixDialog.
         * @param context The parent context
         * @param groupName The group name of building CheckItemGroup
         */
        public Builder(Context context, String groupName){
            this.mContext = context;
            this.groupName = groupName;
        }

        /**
         * Creates a builder for a MixDialog.
         * @param mixDialogBuilder The parent builder that will be returned with {@link Builder#buildWithParent()}
         * @param groupName The group name of building CheckItemGroup
         */
        public Builder(MixDialog.Builder mixDialogBuilder, String groupName){
            this.mContext = mixDialogBuilder.params.mContext;
            this.mixDialogBuilder = mixDialogBuilder;
            this.groupName = groupName;
        }

        /**
         * Setter for show group name as header flag.
         * @param showGroupNameAsHeader If true the {@link CheckItemGroup#groupName} will be shown as title
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setShowGroupNameAsHeader(boolean showGroupNameAsHeader){
            this.showGroupNameAsHeader = showGroupNameAsHeader;
            return this;
        }

        /**
         * Setter for single choice flag.
         * @param isSingleChoice If true only one item can be checkable at the same time for this group.
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setSingleChoice(boolean isSingleChoice){
            this.mSingleChoice = isSingleChoice;
            return this;
        }

        /**
         * Add not checked CheckItem to this group.
         * @param text CheckItem text
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addItem(String text){
            return addItem(text, false);
        }

        /**
         * Add CheckItem to this group.
         * @param text CheckItem text
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addItem(String text, boolean isChecked){
            mCheckItems.put(text, new CheckItem(text, isChecked));
            return this;
        }

        /**
         * Add CheckItem to this group.
         * @param textResourceId CheckItem text from resources
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addItem(@StringRes int textResourceId){
            return addItem(mContext.getResources().getString(textResourceId));
        }

        /**
         * Add CheckItem to this group.
         * @param textResourceId CheckItem text from resources
         * @param isChecked If true CheckItem shown as checked as default, not checked otherwise
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addItem(@StringRes int textResourceId, boolean isChecked){
            return addItem(mContext.getResources().getString(textResourceId), isChecked);
        }

        /**
         * Add not checked CheckItems to this group.
         * @param texts CheckItem texts
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addItems(String[] texts){
            for(int i=0 ; i<texts.length ; i++){
                addItem(texts[i]);
            }
            return this;
        }

        /**
         * Add CheckItems to this group.
         * @param texts CheckItem texts
         * @param isAllChecked If true all CheckItems shown as checked as default, not checked otherwise
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addItems(String[] texts, boolean isAllChecked){
            for(int i=0 ; i<texts.length ; i++){
                addItem(texts[i], isAllChecked);
            }
            return this;
        }

        /**
         * Add CheckItems to this group.
         * @param texts CheckItem texts
         * @param isChecked CheckItems checks status'
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addItems(String[] texts, boolean[] isChecked){
            for(int i=0 ; i<texts.length ; i++){
                addItem(texts[i], isChecked[i]);
            }
            return this;
        }

        /**
         * Add not checked CheckItems to this group.
         * @param textsResourceId CheckItem texts from resources
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addItems(@ArrayRes int textsResourceId){
            return addItems(mContext.getResources().getStringArray(textsResourceId));
        }

        /**
         * Add CheckItems to this group.
         * @param textsResourceId CheckItem texts from resources
         * @param isAllChecked If true all CheckItems shown as checked as default, not checked otherwise
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addItems(@ArrayRes int textsResourceId, boolean isAllChecked){
            return addItems(mContext.getResources().getStringArray(textsResourceId), isAllChecked);
        }

        /**
         * Add CheckItems to this group.
         * @param textsResourceId CheckItem texts from resources
         * @param isChecked CheckItems checks status'
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder addItems(@ArrayRes int textsResourceId, boolean[] isChecked){
            return addItems(mContext.getResources().getStringArray(textsResourceId), isChecked);
        }

        /**
         * Set listener to building CheckItemGroup
         * @param listener Listener of this CheckItemGroup
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnCheckChangeListener(onCheckChangeListener listener){
            this.listener = listener;
            return this;
        }

        /**
         * Build CheckItemGroup with these values.
         * @return created CheckItemGroup
         */
        public CheckItemGroup build(){
            return new CheckItemGroup(groupName, mSingleChoice, showGroupNameAsHeader, mCheckItems, listener);
        }

        /**
         * Build CheckItemGroup with these values and return {@link MixDialog.Builder} instance.
         * @return {@link MixDialog.Builder} object to allow for chaining of calls to set methods
         */
        public MixDialog.Builder buildWithParent(){
            return mixDialogBuilder.addCheckItemGroup(this.build());
        }
    }

    /**
     * Checked listener of the CheckItemGroup
     */
    public interface onCheckChangeListener{
        /**
         * Will be triggered when any CheckItem's check status changed.
         * @param dialog Attached MixDialog
         * @param checkItem Check status changed CheckItem
         * @param isChecked Check status of changed CheckItem
         */
        void onCheckChange(MixDialog dialog, CheckItem checkItem, boolean isChecked);
    }
}
