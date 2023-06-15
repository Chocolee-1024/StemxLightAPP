package com.example.light.ui.mostPost;



import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import com.example.light.R;
import com.example.light.base.BaseActivity;
import com.example.light.base.MorseCodeSharedPreferences;


public class MorsePostActivity extends BaseActivity implements MorsePostContract.view{
    // Element
    private Toolbar toolbar_post;
    private Button delete_button;
    private Button post_button;
    private SeekBar speed_seekBar;
    private ImageButton abbreviation_imageButton;
    private EditText input_editText;
    private TextView now_post_code;
    private Button change_button;
    private boolean isChange = true;
    private MorsePostPresenter morsePostPresenter;
    private AbbreviationMorseCodeDialog abbreviationMorseCodeDialog;
    private MorseCodeSharedPreferences morseCodeSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse_post);

        init();
    }

    @Override
    public void init() {
        morseCodeSharedPreferences = new MorseCodeSharedPreferences(this);
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        morsePostPresenter = new MorsePostPresenter(this, cameraManager,this);
        abbreviationMorseCodeDialog = new AbbreviationMorseCodeDialog(this,this);
        morsePostPresenter.speedSeekBarChange(morseCodeSharedPreferences.getSpeed());
        setupUI();


    }

    /**設定UI*/
    private void setupUI() {
        setupToolbar();
        setupButton();
        setupEditText();
        setupSeekBar();
        setupTextView();

    }
    /**設定Toolbar*/
    private void setupToolbar() {
        toolbar_post = (Toolbar) findViewById(R.id.toolbar_post);
        toolbar_post.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar_post.setNavigationOnClickListener(this::backIconClicked);
    }
    /**設定Button*/
    private void setupButton() {
        delete_button = (Button) findViewById(R.id.post_delete_button);
        post_button = (Button) findViewById(R.id.post_post_button);
        change_button = (Button) findViewById(R.id.post_change_button);
        abbreviation_imageButton = (ImageButton)findViewById(R.id.post_abbreviation_imageButton);

        change_button.setOnClickListener(this::changeBtClicked);
        delete_button.setOnClickListener(this::deleteBtClicked);
        post_button.setOnClickListener(this::postBtClicked);
        abbreviation_imageButton.setOnClickListener(this::abbreviationBtClicked);
    }

    /**設定EditText*/
    private void setupEditText() {
        input_editText = (EditText) findViewById(R.id.post_input_edittext);
    }
    /**設定TextView*/
    private void setupTextView() {
        now_post_code = (TextView) findViewById(R.id.post_now_code);
    }
    /**設定seekBar*/
    private void setupSeekBar() {
        speed_seekBar = (SeekBar) findViewById(R.id.post_speed_seekBar);
        speed_seekBar.setOnSeekBarChangeListener(speedSeekBarChangeListener());
        speed_seekBar.setProgress(morseCodeSharedPreferences.getSpeed());
    }
    /**delete點擊*/
    private void deleteBtClicked(View view) {
        input_editText.setText("");
    }

    /**change點擊*/
    private void changeBtClicked(View view) {
        isChange = !isChange;
        if(isChange) {
            change_button.setText("燈光");
        } else {
            change_button.setText("聲音");
        }
    }


    /**dialog開啟點擊*/
    private void abbreviationBtClicked(View view) {
        abbreviationMorseCodeDialog.show();
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

    /**post點擊*/
    private void postBtClicked(View view) {
        if(delete_button.isEnabled()) {
            if (!TextUtils.isEmpty(input_editText.getText())) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input_editText.getWindowToken(), 0);
                input_editText.clearFocus();
                if(isChange) {
                    morsePostPresenter.startLighting(String.valueOf(input_editText.getText()));
                } else {
                    morsePostPresenter.startBee(String.valueOf(input_editText.getText()));
                }
                input_editText.setText("");
            } else {
                showToast("請先輸入要發送的密碼!");
            }
        } else {
            morsePostPresenter.stopPostThread();
        }

    }
    /**back點擊*/
    private void backIconClicked(View view) {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        morsePostPresenter.stopPostThread();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        morsePostPresenter.stopPostThread();
        finish();
    }

    /**取還選擇的常用MorseCode*/
    @Override
    public void getAbbreviationMorseCode(String abbreviationMorseCode) {
        input_editText.setText(abbreviationMorseCode);
        abbreviationMorseCodeDialog.dismiss();
    }
    /**設定發送按鈕的文字*/
    @Override
    public void setPostButtonText(int text) {
        post_button.setText(text);

    }
    /**設定正在發送的文字*/
    @Override
    public void setNowPostCodeText(String text) {
        now_post_code.setText(text);
    }
    /**元件是否鎖起來*/
    @Override
    public void lockComponent(boolean lock) {
        delete_button.setEnabled(lock);
        speed_seekBar.setEnabled(lock);
        abbreviation_imageButton.setEnabled(lock);
        input_editText.setEnabled(lock);
        change_button.setEnabled(lock);

    }

    /**調整速度*/
    private SeekBar.OnSeekBarChangeListener speedSeekBarChangeListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                morseCodeSharedPreferences.setSpeed(progress);
                morsePostPresenter.speedSeekBarChange(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
    }
}