package com.example.light.ui.morseTransform;

import com.example.light.base.BaseView;

public interface MorseTransformContract {
    interface view extends BaseView {
        void getTranslationCompleted(String outputText);
    }

    interface presenter {
        void morseCodeTransformEnglish(String inputText);
        void englishTransformMorseCode(String inputText);
    }
}
