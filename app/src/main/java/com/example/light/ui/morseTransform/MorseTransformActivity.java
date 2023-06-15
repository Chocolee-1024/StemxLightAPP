package com.example.light.ui.morseTransform;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.light.R;
import com.example.light.base.BaseActivity;

public class MorseTransformActivity extends BaseActivity implements MorseTransformContract.view{
    private LinearLayout two_linearlayout;
    private LinearLayout three_linearlayout;
    private EditText input_editText;
    private EditText output_editText;
    private Button change_button;
    private Button dot_button;
    private Button slash_button;
    private Button dash_button;
    private ImageButton  blank_button;
    private ImageButton delete_imageButton;
    private ImageButton enter_imageButton;
    private Toolbar transform_toolbar;
    private TextView down_textView;
    private TextView top_textView;
    private boolean isChange = true;
    private MorseTransformPresenter morseTransformPresenter;
    private StringBuilder morseCodeBuilder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse_transform);
        init();
    }

    @Override
    public void init() {
        showDialog("當找不到此摩斯密碼時，會顯示§");
        setupUI();
        morseTransformPresenter = new MorseTransformPresenter(this);
    }
    /**設定UI*/
    private void setupUI() {
        setupToolbar();
        setupTextView();
        setupEditText();
        setupButton();
        setupLinearlayout();

    }

    /**設定Toolbar*/
    private void setupToolbar() {
        transform_toolbar = (Toolbar) findViewById(R.id.transform_toolbar);
        transform_toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        transform_toolbar.setNavigationOnClickListener(this::backIconClicked);
    }

    /**設定EditText*/
    private void setupEditText() {
        input_editText = (EditText) findViewById(R.id.transform_input_edittext);
        output_editText = (EditText) findViewById(R.id.transform_output_edittext);
        input_editText.setEnabled(false);
    }

    /**設定TextView*/
    private void setupTextView() {
        down_textView = (TextView) findViewById(R.id.transform_down_textView);
        top_textView = (TextView) findViewById(R.id.transform_top_textView);
        top_textView.setText("輸入");
        down_textView.setText("輸出");
    }
    /**設定Linearlayout*/
    private void setupLinearlayout() {
        two_linearlayout = (LinearLayout) findViewById(R.id.transform_two_linearLayout);
        three_linearlayout = (LinearLayout) findViewById(R.id.transform_three_linearLayout);
    }

    /**設定Button*/
    private void setupButton() {
        change_button = (Button) findViewById(R.id.transform_change_button);
        dot_button = (Button) findViewById(R.id.transform_dot_button);
        slash_button = (Button) findViewById(R.id.transform_slash_button);
        dash_button = (Button) findViewById(R.id.transform_dash_button);
        blank_button = (ImageButton) findViewById(R.id.transform_blank_button);
        delete_imageButton = (ImageButton) findViewById(R.id.transform_delete_imageButton);
        enter_imageButton = (ImageButton) findViewById(R.id.transform_enter_imageButton);
        change_button.setText("摩斯");
        blank_button.setOnClickListener(this::blankIconClicked);
        change_button.setOnClickListener(this::changeIconClicked);
        dash_button.setOnClickListener(this::dashIconClicked);
        slash_button.setOnClickListener(this::slashIconClicked);
        dot_button.setOnClickListener(this::dotIconClicked);
        enter_imageButton.setOnClickListener(this::enterIconClicked);
        delete_imageButton.setOnClickListener(this::deleteIconClicked);
        input_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == 0) {
                    // 隱藏軟鍵盤
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(input_editText.getWindowToken(), 0);

                    // 不換行
                    input_editText.setSingleLine();

                    return true;
                }
                return false;
            }
        });
        delete_imageButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                morseCodeBuilder.setLength(0);
                input_editText.setText("");
                output_editText.setText("");
                return true;
            }
        });
    }

    private void changeIconClicked(View view) {
        isChange = !isChange;
        if(isChange) {
            change_button.setText("摩斯");
            two_linearlayout.setVisibility(View.VISIBLE);
            three_linearlayout.setVisibility(View.VISIBLE);
            input_editText.setEnabled(false);
        } else {
            change_button.setText("英文");
            three_linearlayout.setVisibility(View.GONE);
            input_editText.setEnabled(true);
        }
        morseCodeBuilder.setLength(0);
        input_editText.setText("");
        output_editText.setText("");
    }

    /**dash點擊*/
    private void dashIconClicked(View view) {
        morseCodeBuilder.append("-");
        input_editText.setText(morseCodeBuilder);
    }

    /**slash點擊*/
    private void slashIconClicked(View view) {
        morseCodeBuilder.append("/");
        input_editText.setText(morseCodeBuilder);
    }

    /**dot點擊*/
    private void dotIconClicked(View view) {
        morseCodeBuilder.append(".");
        input_editText.setText(morseCodeBuilder);
    }

    /**blank點擊*/
    private void blankIconClicked(View view) {
        morseCodeBuilder.append(" ");
        input_editText.setText(morseCodeBuilder);
    }

    /**enter點擊*/
    private void enterIconClicked(View view) {
        if(isChange) {
            morseTransformPresenter.morseCodeTransformEnglish(String.valueOf(input_editText.getText()));
        } else {
            if(!String.valueOf(input_editText.getText()).equals("")) {
                morseTransformPresenter.englishTransformMorseCode(String.valueOf(input_editText.getText()));
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input_editText.getWindowToken(), 0);
                input_editText.clearFocus();
            }


        }
    }

    /**delete點擊*/
    private void deleteIconClicked(View view) {
        if(morseCodeBuilder.length() != 0) {
            morseCodeBuilder.deleteCharAt(morseCodeBuilder.length() - 1);
            input_editText.setText(morseCodeBuilder);
        }
        if(!isChange) {
            StringBuilder text = new StringBuilder();
            text.append(input_editText.getText().toString());
            if(text.length() != 0) {
                input_editText.setText(text.deleteCharAt(text.length() - 1));
                input_editText.setSelection(text.length());
                Log.e("TAG", "deleteIconClicked: " + output_editText.getText() );

            }
        }
    }


    @Override
    public void getTranslationCompleted(String outputText) {
        output_editText.setText(outputText);
    }


    /**點擊螢幕，關閉鍵盤*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusedView = getCurrentFocus();
        if (focusedView != null) {
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }
    /**back點擊*/
    private void backIconClicked(View view) {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}