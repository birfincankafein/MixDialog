package com.birfincankafein.mixdialogdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.birfincankafein.mixdialog.CheckItem;
import com.birfincankafein.mixdialog.CheckItemGroup;
import com.birfincankafein.mixdialog.InputItem;
import com.birfincankafein.mixdialog.MixDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mButton_Basic;
    private Button mButton_Input;
    private Button mButton_InputHeader;
    private Button mButton_Single;
    private Button mButton_SingleHeader;
    private Button mButton_Multi;
    private Button mButton_MultiHeader;
    private Button mButton_InputSingleMulti;
    private Button mButton_InputSingleMultiHeader;

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
                case R.id.button_input_header:
                    createAndShowInputHeaderDialog();
                    break;
                case R.id.button_single:
                    createAndShowSingleDialog();
                    break;
                case R.id.button_single_header:
                    createAndShowSingleHeaderDialog();
                    break;
                case R.id.button_multi:
                    createAndShowMultiDialog();
                    break;
                case R.id.button_multi_header:
                    createAndShowMultiHeaderDialog();
                    break;
                case R.id.button_input_single_multi:
                    createAndShowMixDialog();
                    break;
                case R.id.button_input_single_multi_header:
                    createAndShowMixHeaderDialog();
                    break;
            }
        }
    };

    private void createAndShowBasicDialog() {
        MixDialog mixDialog = new MixDialog.Builder(this)
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
                .build();
        mixDialog.show();
    }
    private void createAndShowInputDialog() {
        MixDialog mixDialog = new MixDialog.Builder(this)
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
                .build();
        mixDialog.show();
    }
    private void createAndShowInputHeaderDialog() {
        MixDialog mixDialog = new MixDialog.Builder(this)
                .setTitle("Basic Dialog")
                .setMessage("Hello from MixDialog!")
                .setPositiveButtonText("PositiveButton")
                .setNegativeButtonText("NegativeButton")
                .addInputItemGroup("Input Group")
                    .addInputItem("Input 1")
                    .addInputItem("Input 2", "Default Value 2")
                    .addInputItem("Input 3", "Default Value 3")
                    .setShowGroupNameAsHeader(true)
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
                .build();
        mixDialog.show();
    }
    private void createAndShowSingleDialog() {
        MixDialog mixDialog = new MixDialog.Builder(this)
                .setTitle("Basic Dialog")
                .setMessage("Hello from MixDialog!")
                .setPositiveButtonText("PositiveButton")
                .setNegativeButtonText("NegativeButton")
                .addCheckItemGroup("Check Group 1")
                    .setSingleChoice(true)
                    .addItem("Item 1")
                    .addItem("Item 2", true)
                    .addItem("Item 3", false)
                    .setOnCheckChangeListener(new CheckItemGroup.onCheckChangeListener() {
                        @Override
                        public void onCheckChange(CheckItem checkItem, boolean isChecked) {
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
                .build();
        mixDialog.show();
    }
    private void createAndShowSingleHeaderDialog() {
        MixDialog mixDialog = new MixDialog.Builder(this)
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
                        public void onCheckChange(CheckItem checkItem, boolean isChecked) {
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
                .build();
        mixDialog.show();
    }
    private void createAndShowMultiDialog() {
        MixDialog mixDialog = new MixDialog.Builder(this)
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
                        public void onCheckChange(CheckItem checkItem, boolean isChecked) {
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
                .build();
        mixDialog.show();
    }
    private void createAndShowMultiHeaderDialog() {
        MixDialog mixDialog = new MixDialog.Builder(this)
                .setTitle("Basic Dialog")
                .setMessage("Hello from MixDialog!")
                .setPositiveButtonText("PositiveButton")
                .setNegativeButtonText("NegativeButton")
                .addCheckItemGroup("Check Group 1")
                    .setSingleChoice(false)
                    .setShowGroupNameAsHeader(true)
                    .addItem("Item 1")
                    .addItem("Item 2", true)
                    .addItem("Item 3", true)
                    .setOnCheckChangeListener(new CheckItemGroup.onCheckChangeListener() {
                        @Override
                        public void onCheckChange(CheckItem checkItem, boolean isChecked) {
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
                .build();
        mixDialog.show();
    }
    private void createAndShowMixDialog() {
        MixDialog mixDialog = new MixDialog.Builder(this)
                .setTitle("Basic Dialog")
                .setMessage("Hello from MixDialog!")
                .setPositiveButtonText("PositiveButton")
                .setNegativeButtonText("NegativeButton")
                .addInputItemGroup("Input Group 1")
                    .addInputItem("Input 1-1")
                    .addInputItem("Input 1-2", "Default Value 1-2")
                    .buildWithParent()
                .addInputItemGroup("Input Group 2")
                    .addInputItem("Input 2-1")
                    .addInputItem("Input 2-2", "Default Value 2-2")
                    .buildWithParent()
                .addInputItemGroup("Input Group 3")
                    .addInputItem("Input 3-1")
                    .addInputItem("Input 3-2", "Default Value 3-2")
                    .buildWithParent()
                .addCheckItemGroup("Single Check Group 1")
                    .setSingleChoice(true)
                    .addItem("Item 1-1")
                    .addItem("Item 1-2", true)
                    .addItem("Item 1-3", false)
                    .setOnCheckChangeListener(new CheckItemGroup.onCheckChangeListener() {
                        @Override
                        public void onCheckChange(CheckItem checkItem, boolean isChecked) {
                            Toast.makeText(getApplicationContext(), "Single Check Group 1: " + checkItem.getText() + " isChecked: " + checkItem.isChecked(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .buildWithParent()
                .addCheckItemGroup("Single Check Group 2")
                    .setSingleChoice(true)
                    .addItem("Item 2-1")
                    .addItem("Item 2-2", true)
                    .addItem("Item 2-3", false)
                    .setOnCheckChangeListener(new CheckItemGroup.onCheckChangeListener() {
                        @Override
                        public void onCheckChange(CheckItem checkItem, boolean isChecked) {
                            Toast.makeText(getApplicationContext(), "Single Check Group 2: " + checkItem.getText() + " isChecked: " + checkItem.isChecked(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .buildWithParent()

                .addCheckItemGroup("Single Check Group 3")
                    .setSingleChoice(true)
                    .addItem("Item 3-1")
                    .addItem("Item 3-2", true)
                    .addItem("Item 3-3", false)
                    .setOnCheckChangeListener(new CheckItemGroup.onCheckChangeListener() {
                        @Override
                        public void onCheckChange(CheckItem checkItem, boolean isChecked) {
                            Toast.makeText(getApplicationContext(), "Single Check Group 3: " + checkItem.getText() + " isChecked: " + checkItem.isChecked(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .buildWithParent()
                .addCheckItemGroup("Multi Check Group 1")
                    .setSingleChoice(false)
                    .addItem("Item 1-1")
                    .addItem("Item 1-2", true)
                    .addItem("Item 1-3", true)
                    .setOnCheckChangeListener(new CheckItemGroup.onCheckChangeListener() {
                        @Override
                        public void onCheckChange(CheckItem checkItem, boolean isChecked) {
                            Toast.makeText(getApplicationContext(), "Multi Check Group 1: " + checkItem.getText() + " isChecked: " + checkItem.isChecked(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .buildWithParent()
                .addCheckItemGroup("Multi Check Group 2")
                    .setSingleChoice(false)
                    .addItem("Item 2-1")
                    .addItem("Item 2-2", true)
                    .addItem("Item 2-3", true)
                    .setOnCheckChangeListener(new CheckItemGroup.onCheckChangeListener() {
                        @Override
                        public void onCheckChange(CheckItem checkItem, boolean isChecked) {
                            Toast.makeText(getApplicationContext(), "Multi Check Group 2: " + checkItem.getText() + " isChecked: " + checkItem.isChecked(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .buildWithParent()
                .addCheckItemGroup("Multi Check Group 3")
                    .setSingleChoice(false)
                    .addItem("Item 3-1")
                    .addItem("Item 3-2", true)
                    .addItem("Item 3-3", true)
                    .setOnCheckChangeListener(new CheckItemGroup.onCheckChangeListener() {
                        @Override
                        public void onCheckChange(CheckItem checkItem, boolean isChecked) {
                            Toast.makeText(getApplicationContext(), "Multi Check Group 3: " + checkItem.getText() + " isChecked: " + checkItem.isChecked(), Toast.LENGTH_SHORT).show();
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
                .build();
        mixDialog.show();
    }
    private void createAndShowMixHeaderDialog() {
        MixDialog mixDialog = new MixDialog.Builder(this)
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
                .addInputItemGroup("Input Group 3")
                    .addInputItem("Input 3-1")
                    .addInputItem("Input 3-2", "Default Value 3-2")
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
                        public void onCheckChange(CheckItem checkItem, boolean isChecked) {
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
                        public void onCheckChange(CheckItem checkItem, boolean isChecked) {
                            Toast.makeText(getApplicationContext(), "Single Check Group 2: " + checkItem.getText() + " isChecked: " + checkItem.isChecked(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .buildWithParent()
                .addCheckItemGroup("Single Check Group 3")
                    .setSingleChoice(true)
                    .setShowGroupNameAsHeader(true)
                    .addItem("Item 3-1")
                    .addItem("Item 3-2", true)
                    .addItem("Item 3-3", false)
                    .setOnCheckChangeListener(new CheckItemGroup.onCheckChangeListener() {
                        @Override
                        public void onCheckChange(CheckItem checkItem, boolean isChecked) {
                            Toast.makeText(getApplicationContext(), "Single Check Group 3: " + checkItem.getText() + " isChecked: " + checkItem.isChecked(), Toast.LENGTH_SHORT).show();
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
                        public void onCheckChange(CheckItem checkItem, boolean isChecked) {
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
                        public void onCheckChange(CheckItem checkItem, boolean isChecked) {
                            Toast.makeText(getApplicationContext(), "Multi Check Group 2: " + checkItem.getText() + " isChecked: " + checkItem.isChecked(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .buildWithParent()
                .addCheckItemGroup("Multi Check Group 3")
                    .setSingleChoice(false)
                    .setShowGroupNameAsHeader(true)
                    .addItem("Item 3-1")
                    .addItem("Item 3-2", true)
                    .addItem("Item 3-3", true)
                    .setOnCheckChangeListener(new CheckItemGroup.onCheckChangeListener() {
                        @Override
                        public void onCheckChange(CheckItem checkItem, boolean isChecked) {
                            Toast.makeText(getApplicationContext(), "Multi Check Group 3: " + checkItem.getText() + " isChecked: " + checkItem.isChecked(), Toast.LENGTH_SHORT).show();
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
                .build();
        mixDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton_Basic = findViewById(R.id.button_basic);
        mButton_Basic.setOnClickListener(onClickListener);
        mButton_Input = findViewById(R.id.button_input);
        mButton_Input.setOnClickListener(onClickListener);
        mButton_InputHeader = findViewById(R.id.button_input_header);
        mButton_InputHeader.setOnClickListener(onClickListener);
        mButton_Single = findViewById(R.id.button_single);
        mButton_Single.setOnClickListener(onClickListener);
        mButton_SingleHeader = findViewById(R.id.button_single_header);
        mButton_SingleHeader.setOnClickListener(onClickListener);
        mButton_Multi = findViewById(R.id.button_multi);
        mButton_Multi.setOnClickListener(onClickListener);
        mButton_MultiHeader = findViewById(R.id.button_multi_header);
        mButton_MultiHeader.setOnClickListener(onClickListener);
        mButton_InputSingleMulti = findViewById(R.id.button_input_single_multi);
        mButton_InputSingleMulti.setOnClickListener(onClickListener);
        mButton_InputSingleMultiHeader = findViewById(R.id.button_input_single_multi_header);
        mButton_InputSingleMultiHeader.setOnClickListener(onClickListener);
    }


}
