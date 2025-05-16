package logic;

import component.Player;
import gui.GameScreen;
import gui.SceneManager;


public class GameManager {
	private static boolean isGameEnded = false;
	private static boolean gameStarted = false;
	private static int score = 0;
	private static int timeLeft = 300;	


	// === Position Calculators ===
	public static double positionX(double playerXPos) {
		return playerXPos * SceneManager.pixelSize;
	}

	public static double positionY(double playerYPos) {
		return playerYPos * SceneManager.pixelSize + SceneManager.topPadding;
	}

	// === Movement Checker ===
	public static boolean isIllegalMove(double newPlayerXPos, double newPlayerYPos) {
		return GameScreen.getInstance().isCollision(newPlayerXPos, newPlayerYPos);
	}

	// === Game Lifecycle ===
	public static void startGame() {
		if (!gameStarted) {
			resetGame(); // Reset everything on first start
			gameStarted = true;
		}
		SceneManager.switchToGameScreen();
	}

	public static void resetGame() {
		score = 0;
		timeLeft = 300;
		isGameEnded = false;
		gameStarted = false;
		SceneManager.resetGameTimerFlag();
		Player.getInstance().reset();
	}

	public static boolean isGameEnded() {
		return isGameEnded;
	}

	public static void setIsGameEnded(boolean isEnded) {
		GameManager.isGameEnded = isEnded;
	}

	public static boolean isGameStarted() {
		return gameStarted;
	}

	public static void setGameStarted(boolean started) {
		GameManager.gameStarted = started;
	}

	// === Timer & Score ===
	public static int getScore() {
		return score;
	}

	public static void addScore(int value) {
		score += value;
		SoundManager.playServe();
	}

	public static int getTimeLeft() {
		return timeLeft;
	}

	public static void decrementTime() {
		if (timeLeft > 0) timeLeft--;
	}
}
