package com.example.light.base;

import java.util.HashMap;
import java.util.Map;

public class MorseCodeTool {
    private Map<String, String> morseCode = new HashMap<>();
    private Map<String, String> morseCodeReversed = new HashMap<>();

    public Map<String, String> getMorseCodeList() {

        // Basic Latin alphabet
        morseCode.put("a", ".-");
        morseCode.put("b", "-...");
        morseCode.put("c", "-.-.");
        morseCode.put("d", "-..");
        morseCode.put("e", ".");
        morseCode.put("f", "..-.");
        morseCode.put("g", "--.");
        morseCode.put("h", "....");
        morseCode.put("i", "..");
        morseCode.put("j", ".---");
        morseCode.put("k", "-.-");
        morseCode.put("l", ".-..");
        morseCode.put("m", "--");
        morseCode.put("n", "-.");
        morseCode.put("o", "---");
        morseCode.put("p", ".--.");
        morseCode.put("q", "--.-");
        morseCode.put("r", ".-.");
        morseCode.put("s", "...");
        morseCode.put("t", "-");
        morseCode.put("u", "..-");
        morseCode.put("v", "...-");
        morseCode.put("w", ".--");
        morseCode.put("x", "-..-");
        morseCode.put("y", "-.--");
        morseCode.put("z", "--..");

// Numbers
        morseCode.put("0", "-----");
        morseCode.put("1", ".----");
        morseCode.put("2", "..---");
        morseCode.put("3", "...--");
        morseCode.put("4", "....-");
        morseCode.put("5", ".....");
        morseCode.put("6", "-....");
        morseCode.put("7", "--...");
        morseCode.put("8", "---..");
        morseCode.put("9", "----.");

// Punctuation
        morseCode.put(".", ".-.-.-");
        morseCode.put(",", "--..--");
        morseCode.put("?", "..--..");
        morseCode.put("'", ".----.");
        morseCode.put("!", "-.-.--");
        morseCode.put("/", "-..-.");
        morseCode.put("(", "-.--.");
        morseCode.put(")", "-.--.-");
        morseCode.put("&", ".-...");
        morseCode.put(":", "---...");
        morseCode.put(";", "-.-.-.");
        morseCode.put("=", "-...-");
        morseCode.put("+", ".-.-.");
        morseCode.put("-", "-....-");
        morseCode.put("_", "..--.-");
        morseCode.put("\"", ".-..-.");
        morseCode.put("$", "...-..-");
        morseCode.put("@", ".--.-.");
        return morseCode;
    }

    public String[][] getAbbreviationMorseCode() {
        String[][] abbreviationMorseCode = {
                {"A", "某字以後"},
                {"AB", "字以前"},
                {"ARRL", "美國無線電中繼聯盟"},
                {"ABT", "大約"},
                {"ADS", "位址"},
                {"AGN", "再一次"},
                {"ANT", "天線"},
                {"BN", "……之間"},
                {"BUG", "半自動電鍵"},
                {"C", "是，好"},
                {"CBA", "呼號手冊"},
                {"CFM", "確認"},
                {"CLG", "呼叫"},
                {"CQ", "呼叫任意台站"},
                {"CUL", "再見"},
                {"CUZ", "因為"},
                {"CW", "連續波"},
                {"CX", "狀況"},
                {"CY", "抄收"},
                {"DE", "來自"},
                {"DX", "距離"},
                {"ES", "和；且"},
                {"FB", "類似於「確定」"},
                {"FCC", "（美國）聯邦通信委員會"},
                {"FER", "為了"},
                {"FREQ", "頻率"},
                {"GA", "午安；請發報"},
                {"GE", "晚安"},
                {"GM", "早安"},
                {"GND", "地面"},
                {"GUD", "好"},
                {"HI", "笑"},
                {"HR", "這裡"},
                {"HV", "有"},
                {"LID", "覆蓋"},
                {"MILS", "毫安培"},
                {"NIL", "無收信，空白"},
                {"NR", "編號，第……"},
                {"OB", "老大哥"},
                {"OC", "老伙計"},
                {"OM", "前輩，老手（男性）"},
                {"OO", "官方觀察員"},
                {"OP", "操作員"},
                {"OT", "老前輩"},
                {"OTC", "老手俱樂部"},
                {"OOTC", "資深老手俱樂部"},
                {"PSE", "請"},
                {"PWR", "功率"},
                {"QCWA", "四分之一世界無線電協會"},
                {"R", "收到；小數點"},
                {"RCVR", "接收機"},
                {"RPT", "重複；報告"},
                {"RST", "訊號報告格式"},
                {"RTTY", "無線電傳"},
                {"RX", "接收"},
                {"SAE", "回郵信"},
                {"SASE", "帶郵票的回郵信封"},
                {"SED", "說"},
                {"SEZ", "說"},
                {"SIG", "訊號"},
                {"SIGS", "訊號"},
                {"SKED", "行程表"},
                {"SN", "很快；不久的將來"},
                {"SRI", "抱歉"},
                {"STN", "台站"},
                {"TEMP", "溫度"},
                {"TMW", "明天"},
                {"TNX", "謝謝"},
                {"TU", "謝謝你"},
                {"TX", "發射"},
                {"U", "你"},
                {"UR", "你的；你是"},
                {"URS", "你的"},
                {"VY", "非常；很"},
                {"WDS", "詞"},
                {"WKD", "工作"},
                {"WL", "將會；好"},
                {"WUD", "將會"},
                {"WX", "天氣"},
                {"XMTR", "發射機"},
                {"XYL", "妻子"},
                {"YL", "女報務員"},
                {"73", "致敬"},
                {"83", "最高致敬"},
                {"88", "吻別"},
                {"99", "走開"}};

        return abbreviationMorseCode;
    }

    public Map<String, String> getEnglishToMorseCode() {


        morseCodeReversed.put(".-", "a");
        morseCodeReversed.put("-...", "b");
        morseCodeReversed.put("-.-.", "c");
        morseCodeReversed.put("-..", "d");
        morseCodeReversed.put(".", "e");
        morseCodeReversed.put("..-.", "f");
        morseCodeReversed.put("--.", "g");
        morseCodeReversed.put("....", "h");
        morseCodeReversed.put("..", "i");
        morseCodeReversed.put(".---", "j");
        morseCodeReversed.put("-.-", "k");
        morseCodeReversed.put(".-..", "l");
        morseCodeReversed.put("--", "m");
        morseCodeReversed.put("-.", "n");
        morseCodeReversed.put("---", "o");
        morseCodeReversed.put(".--.", "p");
        morseCodeReversed.put("--.-", "q");
        morseCodeReversed.put(".-.", "r");
        morseCodeReversed.put("...", "s");
        morseCodeReversed.put("-", "t");
        morseCodeReversed.put("..-", "u");
        morseCodeReversed.put("...-", "v");
        morseCodeReversed.put(".--", "w");
        morseCodeReversed.put("-..-", "x");
        morseCodeReversed.put("-.--", "y");
        morseCodeReversed.put("--..", "z");

        morseCodeReversed.put("-----", "0");
        morseCodeReversed.put(".----", "1");
        morseCodeReversed.put("..---", "2");
        morseCodeReversed.put("...--", "3");
        morseCodeReversed.put("....-", "4");
        morseCodeReversed.put(".....", "5");
        morseCodeReversed.put("-....", "6");
        morseCodeReversed.put("--...", "7");
        morseCodeReversed.put("---..", "8");
        morseCodeReversed.put("----.", "9");

        morseCodeReversed.put(".-.-.-", ".");
        morseCodeReversed.put("--..--", ",");
        morseCodeReversed.put("..--..", "?");
        morseCodeReversed.put(".----.", "'");
        morseCodeReversed.put("-.-.--", "!");
        morseCodeReversed.put("-..-.", "/");
        morseCodeReversed.put("-.--.", "(");
        morseCodeReversed.put("-.--.-", ")");
        morseCodeReversed.put(".-...", "&");
        morseCodeReversed.put("---...", ":");
        morseCodeReversed.put("-.-.-.", ";");
        morseCodeReversed.put("-...-", "=");
        morseCodeReversed.put(".-.-.", "+");
        morseCodeReversed.put("-....-", "-");
        morseCodeReversed.put("..--.-", "_");
        morseCodeReversed.put(".-..-.", "\"");
        morseCodeReversed.put("...-..-", "$");
        morseCodeReversed.put(".--.-.", "@");
        return morseCodeReversed;
    }

}
