package logic;

import javafx.scene.media.AudioClip;

public class SoundManager {

    private static final AudioClip walkSound = new AudioClip(ClassLoader.getSystemResource("sound/WalkingSound.mp3").toString());
    private static final AudioClip cookSound = new AudioClip(ClassLoader.getSystemResource("sound/CookingPot.mp3").toString());
    private static final AudioClip chillerSound = new AudioClip(ClassLoader.getSystemResource("sound/InteractingSound.wav").toString());
    private static final AudioClip serveSound = new AudioClip(ClassLoader.getSystemResource("sound/ServedSound.wav").toString());
    private static final AudioClip menuBgm = new AudioClip(ClassLoader.getSystemResource("sound/ThemeSong.mp3").toString());
    private static final AudioClip gameBgm = new AudioClip(ClassLoader.getSystemResource("sound/InGame.mp3").toString());

    public static boolean isGameBgmPlaying = false;
    public static boolean isMenuBgmPlaying = false;

    private static double globalVolume = 0.5; // 🔊 Default 50%

    // ========================
    // Global Volume Controller
    // ========================
    public static void setGlobalVolume(double volume) {
        globalVolume = Math.max(0, Math.min(1, volume)); // clamp 0–1

        // ตั้ง volume ให้ทุกเสียง
        menuBgm.setVolume(globalVolume);
        gameBgm.setVolume(globalVolume);
        walkSound.setVolume(globalVolume);
        cookSound.setVolume(globalVolume);
        chillerSound.setVolume(globalVolume);
        serveSound.setVolume(globalVolume);

        // ถ้ากำลังเล่น BGM อยู่ → เล่นใหม่ด้วย volume ใหม่
        if (isMenuBgmPlaying) {
            menuBgm.stop();
            menuBgm.setCycleCount(AudioClip.INDEFINITE);
            menuBgm.play();
        }
        if (isGameBgmPlaying) {
            gameBgm.stop();
            gameBgm.setCycleCount(AudioClip.INDEFINITE);
            gameBgm.play();
        }
    }

    public static double getGlobalVolume() {
        return globalVolume;
    }

    // ========================
    // Sound Effects
    // ========================
    public static void playWalk() {
        walkSound.setVolume(globalVolume);
        walkSound.play();
    }

    public static void stopPlayWalk() {
        walkSound.stop();
    }

    public static void playCook() {
        cookSound.setVolume(globalVolume);
        cookSound.play();
    }

    public static void stopCook() {
        cookSound.stop();
    }

    public static void playChillerOpen() {
        chillerSound.setVolume(globalVolume);
        chillerSound.play();
    }

    public static void playServe() {
        serveSound.setVolume(globalVolume);
        serveSound.play();
    }

    public static void stopServe() {
        serveSound.stop();
    }

    // ========================
    // Menu BGM
    // ========================
    public static void playMenuBGM() {
        if (!isMenuBgmPlaying) {
            menuBgm.setVolume(globalVolume);
            menuBgm.setCycleCount(AudioClip.INDEFINITE);
            menuBgm.play();
            isMenuBgmPlaying = true;
        }
    }

    public static void stopMenuBGM() {
        if (isMenuBgmPlaying) {
            menuBgm.stop();
            isMenuBgmPlaying = false;
        }
    }

    // ========================
    // Game BGM
    // ========================
    public static void playGameBgm() {
        if (!isGameBgmPlaying) {
            gameBgm.setVolume(globalVolume);
            gameBgm.setCycleCount(AudioClip.INDEFINITE);
            gameBgm.play();
            isGameBgmPlaying = true;
        }
    }

    public static void stopGameBGM() {
        if (isGameBgmPlaying) {
            gameBgm.stop();
            isGameBgmPlaying = false;
        }
    }
}
