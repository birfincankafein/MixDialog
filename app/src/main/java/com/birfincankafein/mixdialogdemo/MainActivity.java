package com.birfincankafein.mixdialogdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.birfincankafein.mixdialog.CheckItem;
import com.birfincankafein.mixdialog.CheckItemGroup;
import com.birfincankafein.mixdialog.InputItem;
import com.birfincankafein.mixdialog.KeyValueItem;
import com.birfincankafein.mixdialog.KeyValueItemGroup;
import com.birfincankafein.mixdialog.MixDialog;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button_basic:
                    createAndShowBasicDialog();
                    break;
                case R.id.button_input:
                    createAndShowInputDialog();
                    break;
                case R.id.button_single_header:
                    createAndShowSingleHeaderDialog();
                    break;
                case R.id.button_keyvalue:
                    createAndShowKeyValue();
                    break;
                case R.id.button_multi:
                    createAndShowMultiDialog();
                    break;
                case R.id.button_input_single_multi_header:
                    createAndShowInputSingleMultiHeaderDialog();
                    break;
                case R.id.button_input_single_multi_keyvalueheader:
                    createAndShowInputSingleMultiKeyValueHeaderDialog();
                    break;
            }
        }
    };

    private void createAndShowBasicDialog() {
        new MixDialog.Builder(this)
                .setTitle("Basic Dialog")
                .setMessage("Hello from MixDialog!")
                .setPositiveButtonText("PositiveButton")
                .setNegativeButtonText("NegativeButton")
                .setNeutralButtonText("NeutralButton")
                .setCancelable(false)
                .setOnDialogEventListener(new MixDialog.onDialogEventListener() {
                    @Override
                    public boolean onDialogButtonClick(MixDialog.ButtonType buttonType, MixDialog dialog) {
                        return true;
                    }

                })
                .build().show();
    }

    private void createAndShowInputDialog() {
        new MixDialog.Builder(this)
                .setTitle("Basic Dialog")
                .setMessage("Hello from MixDialog!")
                .setPositiveButtonText("PositiveButton")
                .setNegativeButtonText("NegativeButton")
                .addInputItemGroup("InputGroup")
                    .addInputItem("Input 1")
                    .addInputItem("Input 2", "Default Value 2")
                    .buildWithParent()
                .setCancelable(false)
                .setOnDialogEventListener(new MixDialog.onDialogEventListener() {
                    @Override
                    public boolean onDialogButtonClick(MixDialog.ButtonType buttonType, MixDialog dialog) {
                        boolean retVal = true;
                        if(buttonType.equals(MixDialog.ButtonType.POSITIVE)) {
                            InputItem inputItem1 = dialog.getInputItem("InputGroup", "Input 1");
                            InputItem inputItem2 = dialog.getInputItem("InputGroup", "Input 2");
                            if (inputItem1 != null){
                                if(inputItem1.getValue().isEmpty()) {
                                    inputItem1.setError("Input 1 cannot be empty!");
                                    retVal = false;
                                }
                                else{
                                    inputItem1.clearError();
                                }
                            }
                            if (inputItem2 != null){
                                if(inputItem2.getValue().isEmpty() || inputItem2.getValue().equals("Default Value 2")) {
                                    inputItem2.setError("Input 2 must be changed!");
                                    retVal = false;
                                }
                                else{
                                    inputItem2.clearError();
                                }
                            }
                        }
                        return retVal;
                    }
                })
                .build().show();
    }

    private void createAndShowSingleHeaderDialog() {
        new MixDialog.Builder(this)
                .setTitle("Basic Dialog")
                .setMessage("Hello from MixDialog!")
                .setPositiveButtonText("PositiveButton")
                .setNegativeButtonText("NegativeButton")
                .addCheckItemGroup("Check Group 1")
                    .setSingleChoice(true)
                    .setShowGroupNameAsHeader(true)
                    .addItem("Item 1")
                    .addItem("Item 2", true)
                    .addItem("Item 3", false)
                    .setOnCheckChangeListener(new CheckItemGroup.onCheckChangeListener() {
                        @Override
                        public void onCheckChange(MixDialog dialog, CheckItem checkItem, boolean isChecked) {
                            Toast.makeText(getApplicationContext(), "CheckItem: " + checkItem.getText() + " isChecked: " + checkItem.isChecked(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .buildWithParent()
                .setCancelable(false)
                .setOnDialogEventListener(new MixDialog.onDialogEventListener() {
                    @Override
                    public boolean onDialogButtonClick(MixDialog.ButtonType buttonType, MixDialog dialog) {
                        boolean retVal = true;
                        if(buttonType.equals(MixDialog.ButtonType.POSITIVE)) {
                            CheckItem checkedItem = dialog.getSingleCheckedItem("Check Group 1");
                            CheckItem checkItem1 = dialog.getSingleCheckItem("Check Group 1", "Item 1");
                            if (checkedItem != null) {
                                if (checkedItem.getText().equals("Item 2")) {
                                    Toast.makeText(getApplicationContext(), "Check Item cannot be Item 2", Toast.LENGTH_SHORT).show();
                                    retVal = false;
                                }
                            }
                            if (checkItem1 != null) {
                                if (!checkItem1.isChecked()) {
                                    Toast.makeText(getApplicationContext(), "Check Item must be Item 1", Toast.LENGTH_SHORT).show();
                                    retVal = false;
                                }
                            }
                        }
                        return retVal;
                    }
                })
                .build().show();
    }

    private void createAndShowKeyValue(){
        new MixDialog.Builder(this)
                .setTitle("Basic Dialog")
                .setMessage("Hello from MixDialog!")
                .setPositiveButtonText("PositiveButton")
                .setNegativeButtonText("NegativeButton")
                .addKeyValueItemGroup("KeyValue Group 1")
                    .setShowGroupNameAsHeader(true)
                    .addKeyValueItem("Key 1", "Value 1")
                    .addKeyValueItem("Key 2", "Value 2")
                    .addKeyValueItem("Key 3", "Value 3")
                    .buildWithParent()
                .setCancelable(false)
                .setOnDialogEventListener(new MixDialog.onDialogEventListener() {
                    @Override
                    public boolean onDialogButtonClick(MixDialog.ButtonType buttonType, MixDialog dialog) {
                        return true;
                    }
                })
                .build().show();
    }

    private void createAndShowMultiDialog() {
        new MixDialog.Builder(this)
                .setTitle("Basic Dialog")
                .setMessage("Hello from MixDialog!")
                .setPositiveButtonText("PositiveButton")
                .setNegativeButtonText("NegativeButton")
                .addCheckItemGroup("Check Group 1")
                    .setSingleChoice(false)
                    .addItem("Item 1")
                    .addItem("Item 2", true)
                    .addItem("Item 3", true)
                    .setOnCheckChangeListener(new CheckItemGroup.onCheckChangeListener() {
                        @Override
                        public void onCheckChange(MixDialog dialog, CheckItem checkItem, boolean isChecked) {
                            Toast.makeText(getApplicationContext(), "CheckItem: " + checkItem.getText() + " isChecked: " + checkItem.isChecked(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .buildWithParent()
                .setCancelable(false)
                .setOnDialogEventListener(new MixDialog.onDialogEventListener() {
                    @Override
                    public boolean onDialogButtonClick(MixDialog.ButtonType buttonType, MixDialog dialog) {
                        boolean retVal = true;
                        if(buttonType.equals(MixDialog.ButtonType.POSITIVE)) {
                            List<CheckItem> checkedItems = dialog.getMultiCheckedItems("Check Group 1");
                            CheckItem checkItem1 = dialog.getMultiCheckItem("Check Group 1", "Item 1");
                            CheckItem checkItem2 = dialog.getMultiCheckItem("Check Group 1", "Item 2");
                            if (checkedItems != null) {
                                if (checkedItems.contains(checkItem2)) {
                                    Toast.makeText(getApplicationContext(), "Check Item cannot be Item 2", Toast.LENGTH_SHORT).show();
                                    retVal = false;
                                }
                            }
                            if (checkItem1 != null) {
                                if (!checkItem1.isChecked()) {
                                    Toast.makeText(getApplicationContext(), "Check Item must be Item 1", Toast.LENGTH_SHORT).show();
                                    retVal = false;
                                }
                            }
                        }
                        return retVal;
                    }
                })
                .build().show();
    }
    private void createAndShowInputSingleMultiHeaderDialog() {
        new MixDialog.Builder(this)
                .setTitle("Basic Dialog")
                .setMessage("Hello from MixDialog!")
                .setPositiveButtonText("PositiveButton")
                .setNegativeButtonText("NegativeButton")
                .addInputItemGroup("Input Group 1")
                    .addInputItem("Input 1-1")
                    .addInputItem("Input 1-2", "Default Value 1-2")
                    .setShowGroupNameAsHeader(true)
                    .buildWithParent()
                .addInputItemGroup("Input Group 2")
                    .addInputItem("Input 2-1")
                    .addInputItem("Input 2-2", "Default Value 2-2")
                    .setShowGroupNameAsHeader(true)
                    .buildWithParent()
                .addCheckItemGroup("Single Check Group 1")
                    .setSingleChoice(true)
                    .setShowGroupNameAsHeader(true)
                    .addItem("Item 1-1")
                    .addItem("Item 1-2", true)
                    .addItem("Item 1-3", false)
                    .setOnCheckChangeListener(new CheckItemGroup.onCheckChangeListener() {
                        @Override
                        public void onCheckChange(MixDialog dialog, CheckItem checkItem, boolean isChecked) {
                            Toast.makeText(getApplicationContext(), "Single Check Group 1: " + checkItem.getText() + " isChecked: " + checkItem.isChecked(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .buildWithParent()
                .addCheckItemGroup("Single Check Group 2")
                    .setSingleChoice(true)
                    .setShowGroupNameAsHeader(true)
                    .addItem("Item 2-1")
                    .addItem("Item 2-2", true)
                    .addItem("Item 2-3", false)
                    .setOnCheckChangeListener(new CheckItemGroup.onCheckChangeListener() {
                        @Override
                        public void onCheckChange(MixDialog dialog, CheckItem checkItem, boolean isChecked) {
                            Toast.makeText(getApplicationContext(), "Single Check Group 2: " + checkItem.getText() + " isChecked: " + checkItem.isChecked(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .buildWithParent()
                .addCheckItemGroup("Multi Check Group 1")
                    .setSingleChoice(false)
                    .setShowGroupNameAsHeader(true)
                    .addItem("Item 1-1")
                    .addItem("Item 1-2", true)
                    .addItem("Item 1-3", true)
                    .setOnCheckChangeListener(new CheckItemGroup.onCheckChangeListener() {
                        @Override
                        public void onCheckChange(MixDialog dialog, CheckItem checkItem, boolean isChecked) {
                            Toast.makeText(getApplicationContext(), "Multi Check Group 1: " + checkItem.getText() + " isChecked: " + checkItem.isChecked(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .buildWithParent()
                .addCheckItemGroup("Multi Check Group 2")
                    .setSingleChoice(false)
                    .setShowGroupNameAsHeader(true)
                    .addItem("Item 2-1")
                    .addItem("Item 2-2", true)
                    .addItem("Item 2-3", true)
                    .setOnCheckChangeListener(new CheckItemGroup.onCheckChangeListener() {
                        @Override
                        public void onCheckChange(MixDialog dialog, CheckItem checkItem, boolean isChecked) {
                            Toast.makeText(getApplicationContext(), "Multi Check Group 2: " + checkItem.getText() + " isChecked: " + checkItem.isChecked(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .buildWithParent()
                .setCancelable(false)
                .setOnDialogEventListener(new MixDialog.onDialogEventListener() {
                    @Override
                    public boolean onDialogButtonClick(MixDialog.ButtonType buttonType, MixDialog dialog) {
                        return true;
                    }
                })
                .build().show();
    }

    private void createAndShowInputSingleMultiKeyValueHeaderDialog(){
        new MixDialog.Builder(this)
                .setTitle("Basic Dialog")
                .setMessage("Hello from MixDialog!")
                .setPositiveButtonText("PositiveButton")
                .setNegativeButtonText("NegativeButton")
                .addKeyValueItemGroup("Key Value Group")
                    .setShowGroupNameAsHeader(true)
                    .setKeyValueWidthRatio(1.5f)
                    .addKeyValueItem("Item 1:", "false")
                    .addKeyValueItem("Item 2: ", "true")
                    .addKeyValueItem("Item 3: ", "false")
                    .buildWithParent()
                .addInputItemGroup("Input Group")
                    .setShowGroupNameAsHeader(true)
                    .addInputItem("Input 1")
                    .addInputItem("Input 2", "Default Value 2")
                    .buildWithParent()
                .addCheckItemGroup("Single Check Group")
                    .setShowGroupNameAsHeader(true)
                    .setSingleChoice(true)
                    .addItem("Single Item 1")
                    .addItem("Single Item 2", true)
                    .addItem("Single Item 3", false)
                    .setOnCheckChangeListener(new CheckItemGroup.onCheckChangeListener() {
                        @Override
                        public void onCheckChange(MixDialog dialog, CheckItem checkItem, boolean isChecked) {
                            dialog.updateMessage("Single Choice Item Checked");
                            CheckItemGroup checkItemGroup = dialog.getSingleCheckItemGroup("Single Check Group");
                            KeyValueItemGroup keyValueItemGroup = dialog.getKeyValueItemGroup("Key Value Group");
                            if(checkItemGroup != null && keyValueItemGroup != null){
                                List<CheckItem> checkItems = checkItemGroup.getItems();
                                List<KeyValueItem> keyValueItems = keyValueItemGroup.getItems();
                                for(int i=0 ; i<checkItems.size() ; i++){
                                    keyValueItems.get(i).setValue(Boolean.toString(checkItems.get(i).isChecked()));
                                }
                            }
                        }
                    })
                    .buildWithParent()
                .addCheckItemGroup("Multi Check Group")
                    .setShowGroupNameAsHeader(true)
                    .setSingleChoice(false)
                    .addItem("Multi Item 1")
                    .addItem("Multi Item 2", true)
                    .addItem("Multi Item 3", false)
                    .setOnCheckChangeListener(new CheckItemGroup.onCheckChangeListener() {
                        @Override
                        public void onCheckChange(MixDialog dialog, CheckItem checkItem, boolean isChecked) {
                            dialog.updateMessage("Multi Choice Item Checked");
                            CheckItemGroup checkItemGroup = dialog.getMultiCheckItemGroup("Multi Check Group");
                            KeyValueItemGroup keyValueItemGroup = dialog.getKeyValueItemGroup("Key Value Group");
                            if(checkItemGroup != null && keyValueItemGroup != null){
                                List<CheckItem> checkItems = checkItemGroup.getItems();
                                List<KeyValueItem> keyValueItems = keyValueItemGroup.getItems();
                                for(int i=0 ; i<checkItems.size() ; i++){
                                    keyValueItems.get(i).setValue(Boolean.toString(checkItems.get(i).isChecked()));
                                }
                            }
                        }
                    })
                    .buildWithParent()
                .setCancelable(false)
                .setOnDialogEventListener(new MixDialog.onDialogEventListener() {
                    @Override
                    public boolean onDialogButtonClick(MixDialog.ButtonType buttonType, MixDialog dialog) {
                        return true;
                    }
                })
                .build().show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_basic).setOnClickListener(onClickListener);
        findViewById(R.id.button_keyvalue).setOnClickListener(onClickListener);
        findViewById(R.id.button_input).setOnClickListener(onClickListener);
        findViewById(R.id.button_single_header).setOnClickListener(onClickListener);
        findViewById(R.id.button_multi).setOnClickListener(onClickListener);
        findViewById(R.id.button_input_single_multi_header).setOnClickListener(onClickListener);
        findViewById(R.id.button_input_single_multi_keyvalueheader).setOnClickListener(onClickListener);
    }


}
