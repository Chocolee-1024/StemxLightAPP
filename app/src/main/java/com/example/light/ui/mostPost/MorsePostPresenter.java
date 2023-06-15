package com.example.light.ui.mostPost;



import android.app.Activity;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.example.light.R;
import com.example.light.base.MorseCodeTool;
import java.util.Map;


public class MorsePostPresenter implements MorsePostContract.presenter{
    private MorsePostContract.view view;
    private String cameraId;
    private CameraManager cameraManager;
    private Map<String,String> morseCodeTool;
    private int postSpeed;
    private Activity activity;
    private Thread getLightThread;
    private Thread getBeeThread;
    private MediaPlayer mediaPlayer;


    public MorsePostPresenter(MorsePostContract.view view, CameraManager cameraManager,Activity activity) {
        this.activity = activity;
        this.view = view;
        this.cameraManager = cameraManager;
        mediaPlayer = MediaPlayer.create(activity, R.raw.beep);
        mediaPlayer.setLooping(true);
        morseCodeTool = new MorseCodeTool().getMorseCodeList();
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startLighting(String inputCode) {
        getLightThread = getLightThread(inputCode);
        getLightThread.start();
    }

    @Override
    public void startBee(String inputCode) {
        getBeeThread = getBeeThread(inputCode);
        getBeeThread.start();
    }

    @Override
    public void speedSeekBarChange(int index) {
        if(index == 0) {
            postSpeed = 1;
        } else {
            postSpeed = index;
        }
    }

    @Override
    public void stopPostThread() {
        if(getLightThread != null) {
            synchronized (getLightThread) {
                getLightThread.interrupt();
                view.lockComponent(true);
                view.setNowPostCodeText("");
            }
        }
        if(getBeeThread != null) {
            synchronized (getBeeThread) {
                getBeeThread.interrupt();
                view.lockComponent(true);
                view.setNowPostCodeText("");
            }
        }
    }


    private Thread getLightThread(String inputCode) {
        return new Thread(() -> {
            activity.runOnUiThread(() -> {
                view.setPostButtonText(R.string.stop);
                view.lockComponent(false);
            });
            for (String code : inputCode.split("")) {

                if(morseCodeTool.get(code.toLowerCase()) == null) {
                    continue;
                }
                if (Thread.currentThread().isInterrupted()) { // 檢查是否有中斷請求
                    break;
                }
                activity.runOnUiThread(() -> {
                    view.setNowPostCodeText(code);
                });
                if (!code.equals(" ") && !code.equals("\n")) {
                    for (String i : morseCodeTool.get(code.toLowerCase()).split("")) {
                        if (Thread.currentThread().isInterrupted()) { // 檢查是否有中斷請求
                            break;
                        }

                        openLight();
                        if (i.equals(".")) {
                            try {
                                Thread.sleep(5000 / postSpeed); // 燈亮的時間，正常速度為 1 單位
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                e.printStackTrace();
                            }
                            closeLight();
                            try {
                                Thread.sleep(6000 / postSpeed); // 燈暗的時間，正常速度為 1 單位
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                Thread.sleep(10000 / postSpeed); // 燈亮的時間，正常速度為 3 單位
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                e.printStackTrace();
                            }
                            closeLight();
                            try {
                                Thread.sleep(6000 / postSpeed); // 燈暗的時間，正常速度為 1 單位
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                e.printStackTrace();
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(12000 / postSpeed); // 單個字符之間的間隔，正常速度為 3 倍單位
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
            activity.runOnUiThread(() -> {
                view.setPostButtonText(R.string.post);
                view.lockComponent(true);
                view.setNowPostCodeText("");
            });
        });
    }


    private Thread getBeeThread(String inputCode) {
        int postBeepSpeed =  postSpeed;
        if(postBeepSpeed - 40 >= 0) {
            postBeepSpeed = postSpeed - 40;
        }
        int finalPostBeepSpeed = postBeepSpeed;
        return new Thread(() -> {
            activity.runOnUiThread(() -> {
                view.setPostButtonText(R.string.stop);
                view.lockComponent(false);
            });
            for (String code : inputCode.split("")) {

                if(morseCodeTool.get(code.toLowerCase()) == null) {
                    continue;
                }
                if (Thread.currentThread().isInterrupted()) { // 檢查是否有中斷請求
                    break;
                }
                activity.runOnUiThread(() -> {
                    view.setNowPostCodeText(code);
                });
                if (!code.equals(" ") && !code.equals("\n")) {
                    for (String i : morseCodeTool.get(code.toLowerCase()).split("")) {
                        if (Thread.currentThread().isInterrupted()) { // 檢查是否有中斷請求
                            break;
                        }

                        playBee();
                        if (i.equals(".")) {
                            try {
                                Thread.sleep(17000 / finalPostBeepSpeed); // 燈亮的時間，正常速度為 1 單位
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                e.printStackTrace();
                            }
                            stopBee();
                            try {
                                Thread.sleep(18000 / finalPostBeepSpeed); // 燈暗的時間，正常速度為 1 單位
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                Thread.sleep(22000 / finalPostBeepSpeed); // 燈亮的時間，正常速度為 3 單位
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                e.printStackTrace();
                            }
                            stopBee();
                            try {
                                Thread.sleep(18000 / finalPostBeepSpeed); // 燈暗的時間，正常速度為 1 單位
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                e.printStackTrace();
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(24000 / finalPostBeepSpeed); // 單個字符之間的間隔，正常速度為 3 倍單位
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
            activity.runOnUiThread(() -> {
                view.setPostButtonText(R.string.post);
                view.lockComponent(true);
                view.setNowPostCodeText("");
            });
        });
    }


    private void openLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, true);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void closeLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, false);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void playBee() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(activity, R.raw.beep);
            mediaPlayer.setLooping(true);
        }
        mediaPlayer.start();
    }

    private void stopBee() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
