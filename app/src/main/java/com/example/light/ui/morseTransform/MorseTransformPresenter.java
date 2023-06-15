package com.example.light.ui.morseTransform;


import android.util.Log;

import com.example.light.base.MorseCodeTool;

import java.util.Map;

public class MorseTransformPresenter implements MorseTransformContract.presenter {
    private MorseTransformContract.view view;
    private MorseCodeTool morseCodeTool;
    public MorseTransformPresenter(MorseTransformContract.view view) {
        this.view = view;
        morseCodeTool = new MorseCodeTool();
    }

    @Override
    public void morseCodeTransformEnglish(String inputText) {
        StringBuilder morseCodeStringBuilder = new StringBuilder();
        StringBuilder outputMorseCodeStringBuilder = new StringBuilder();

        for (String text : inputText.split("")) {
            if (!text.equals("/") && !text.equals(" ")) {
                morseCodeStringBuilder.append(text);
            } else if (text.equals(" ")) {
                if (morseCodeStringBuilder.length() > 0 && !morseCodeStringBuilder.substring(0, 1).equals(" ")) {
                    if(morseCodeTool.getEnglishToMorseCode().get(morseCodeStringBuilder.toString()) == null) {
                        outputMorseCodeStringBuilder.append("§").append(" ");
                        morseCodeStringBuilder.setLength(0);
                    } else {
                        outputMorseCodeStringBuilder.append(morseCodeTool.getEnglishToMorseCode().get(morseCodeStringBuilder.toString()));
                        morseCodeStringBuilder.setLength(0);
                    }
                }
            } else {
                if (morseCodeStringBuilder.length() > 0) {
                    String firstText = Character.toString(morseCodeStringBuilder.charAt(0));
                    if (firstText.equals("/")) {
                        morseCodeStringBuilder.setLength(0);
                    } else {
                        if(morseCodeTool.getEnglishToMorseCode().get(morseCodeStringBuilder.toString()) == null) {
                            outputMorseCodeStringBuilder.append("§").append(" ");
                            morseCodeStringBuilder.setLength(0);
                        } else {
                            outputMorseCodeStringBuilder.append(morseCodeTool.getEnglishToMorseCode().get(morseCodeStringBuilder.toString())).append(" ");
                            morseCodeStringBuilder.setLength(0);
                        }
                    }
                }
            }
        }

        if (morseCodeStringBuilder.length() > 0) {
            String firstText = Character.toString(morseCodeStringBuilder.charAt(0));
            if (!firstText.equals(" ") && !firstText.equals("/")) {
                if(morseCodeTool.getEnglishToMorseCode().get(morseCodeStringBuilder.toString()) == null) {
                    outputMorseCodeStringBuilder.append("§");
                } else {
                    outputMorseCodeStringBuilder.append(morseCodeTool.getEnglishToMorseCode().get(morseCodeStringBuilder.toString()));

                }
            }
        }

        view.getTranslationCompleted(outputMorseCodeStringBuilder.toString());
    }
    @Override
    public void englishTransformMorseCode(String inputText) {
        StringBuilder outputMorseCodeStringBuilder = new StringBuilder();
        StringBuilder checkBlankStringBuilder = new StringBuilder();
        String firstText = "";
        for(String text: inputText.split("")) {
            if(!text.equals("\n")) {
                if(checkBlankStringBuilder.length() != 0){
                    firstText = Character.toString(checkBlankStringBuilder.charAt(0));
                }
                if(text.equals(" ")) {
                    if(!firstText.equals(" ")) {
                        outputMorseCodeStringBuilder.append("/");
                        checkBlankStringBuilder.append(" ");
                    }
                } else {
                    if(morseCodeTool.getMorseCodeList().get(text) == null) {
                        outputMorseCodeStringBuilder.append("§");
                        outputMorseCodeStringBuilder.append(" ");
                    } else {
                        outputMorseCodeStringBuilder.append(morseCodeTool.getMorseCodeList().get(text));
                        outputMorseCodeStringBuilder.append(" ");
                        checkBlankStringBuilder.setLength(0);
                    }
                    firstText = "";
                }
            }
        }
        if(outputMorseCodeStringBuilder.length() != 0) {
            outputMorseCodeStringBuilder.deleteCharAt(outputMorseCodeStringBuilder.length() - 1);
            view.getTranslationCompleted(outputMorseCodeStringBuilder.toString());
        }
    }
}
