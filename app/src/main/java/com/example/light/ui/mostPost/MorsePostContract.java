package com.example.light.ui.mostPost;

import com.example.light.base.BaseView;

public interface MorsePostContract {
    interface view extends BaseView {
        void getAbbreviationMorseCode(String abbreviationMorseCode);
        void setPostButtonText(int text);
        void setNowPostCodeText(String text);
        void lockComponent(boolean lock);
    }

    interface presenter {
        void startLighting(String inputCode);
        void startBee(String inputCode);
        void speedSeekBarChange(int index);
        void stopPostThread();
    }
    interface adapter {

    }
}
