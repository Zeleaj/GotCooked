package gui;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

import container.Container;
import container.Counter;
import container.Dish;
import container.RollingMat;
import interfaces.Interactable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.AnimatedObject;
import logic.DynamicObjectDetail;
import logic.DynamicObjectFactory;
import logic.GameManager;
import logic.MapData;
import logic.StaticObject;
import logic.StaticObjectDetail;
import logic.StaticObjectFactory;
import logic.TaskManager;


public class GameScreen extends BaseSceneScreen{
	
	private ArrayList<AnimatedObject> dynamicObject = new ArrayList<>();
	private ArrayList<ArrayList<String>> tiles ;
	private ArrayList<ArrayList<String>> objects ;
	private HashMap<String,StaticObjectDetail> staticObjectsDetail ; 
	private HashMap<String, DynamicObjectDetail> dynamicObjectsDetail ; 
	private HashMap<String, Image> staticImageCache = new HashMap<>();
	private HashMap<String, AnimatedObject> dynamicObjectCache = new HashMap<>();
	private ArrayList<StaticObject> staticObject = new ArrayList<>();
	private MapData mapData;  
	private Interactable hitObject;
	private String playerFeedbackText = "";
	private long feedbackTimestamp = 0;
	private static final long FEEDBACK_DURATION = 2000; // 2 วิ
	Gson gson = new Gson(); // GSON object it will read json data and that data will store in ArrayList
	
	
	public static GameScreen instance;
	private GameScreen() {
		super();
	}
	public void addObject() {
		try (InputStream is = getClass().getClassLoader().getResourceAsStream("map/map1.json")) {
		    if (is == null) {
		        System.out.println("[ERROR] map1.json not found!");
		        return;
		    }
		    InputStreamReader reader = new InputStreamReader(is);
		    mapData = gson.fromJson(reader, MapData.class);
		    this.tiles = mapData.getTiles();
		    this.objects = mapData.getObjects();
		    this.staticObjectsDetail = mapData.getStaticObjectsDetail();
		    this.dynamicObjectsDetail = mapData.getDynamicObjectsDetail();

		} catch (IOException e) {
		    e.printStackTrace();
		}
		
	}
	public void preloadStaticImages() {
	    for (String tileName : staticObjectsDetail.keySet()) {
	        StaticObjectDetail detail = staticObjectsDetail.get(tileName);
	        if (detail != null) {
	            String url = detail.getStaticImageURL();
	            Image img = SceneManager.imageLoader(url);
	            staticImageCache.put(tileName, img);
	        }
	    }
	}
	public void preloadStaticObjects() {
		for (int row = 0; row < objects.size(); row++) {
		    for (int col = 0; col < objects.get(row).size(); col++) {
		        String objectName = objects.get(row).get(col);
		        if (objectName != null && !objectName.isEmpty()) {
		            StaticObjectDetail detail = staticObjectsDetail.get(objectName);
		            if (dynamicObjectsDetail.containsKey(objectName)) continue;
		            if (detail != null && detail.isCollidable()) {
		                StaticObject obj = StaticObjectFactory.createStaticObject(detail.getName(), detail, (double)col, (double)row);
		                staticObject.add(obj);
		            } // check if collidable if collidable kept in List (There is no dynamic because we search from setDATA in JSON)
		        }
		    }
		}

	}
	public void preloadDynamicObjects() {
	    // สมมติ dynamicObjectsDetail ถูกโหลดแล้ว
		for (String objectName : dynamicObjectsDetail.keySet()) {
		    DynamicObjectDetail detail = dynamicObjectsDetail.get(objectName);
		    AnimatedObject obj = DynamicObjectFactory.createDynamicObject(objectName, detail);
		    dynamicObjectCache.put(objectName, obj);
		}

	}
	
	public void drawTiles(GraphicsContext gc) {
		if (tiles == null) {
	        return;
	    }
		for (int row = 0; row < tiles.size(); row++) {
	        for (int col = 0; col < tiles.get(row).size(); col++) {
	            String tileName = tiles.get(row).get(col);
	            if (tileName != null && !tileName.isEmpty()) {
	                Image image = staticImageCache.get(tileName);
	                if (image != null) {
	                	gc.drawImage(staticImageCache.get(tileName),GameManager.positionX(col), GameManager.positionY(row));
	                }
	            }
	        }
	    }
	}
	public void drawStaticObjects(GraphicsContext gc) {
	    for (StaticObject obj : staticObject) {
	        Image image = staticImageCache.get(obj.getName());
	        if (image != null) {
	            gc.drawImage(image, GameManager.positionX(obj.getX()), GameManager.positionY(obj.getY()));
	            // ✅ ถ้า obj เป็น Counter → เช็กมี container ไหม แล้ววาดด้วย
	            if (obj instanceof Counter counter) {
	                Container c = counter.getContainerOnCounter();
	                if (c != null) {
	                	gc.setStroke(Color.RED);
	                    gc.strokeRect(GameManager.positionX(c.getX()),GameManager.positionY(c.getY()) , SceneManager.pixelSize, SceneManager.pixelSize); // 
	                }}
	            else if(obj instanceof RollingMat rollingMat) {
	            	Dish c = rollingMat.getDishOnMat();
	                if (c != null) {
	                	gc.setStroke(Color.WHITE);
	                    gc.strokeRect(GameManager.positionX(rollingMat.getX()),GameManager.positionY(rollingMat.getY()) , SceneManager.pixelSize, SceneManager.pixelSize); // 
	                }}
	            
	        } 
	       }
	    }
	

	public void drawDynamicObjects(GraphicsContext gc) {
		for (AnimatedObject obj :  dynamicObjectCache.values()) {
	        Image frame = obj.getCurrentFrame(); // assume function
	        gc.drawImage(frame,GameManager.positionX(obj.getxPosition()),GameManager.positionY(obj.getyPosition()));
	    }
	}
	public void addDynamicObject() {
	    for (String objectName : mapData.getDynamicObjectsDetail().keySet()) {
	        DynamicObjectDetail detail = mapData.getDynamicObjectsDetail().get(objectName);
	        AnimatedObject obj = DynamicObjectFactory.createDynamicObject(objectName, detail);
	        dynamicObject.add(obj);
	    }
	}

	public boolean isCollision(double targetX, double targetY) {
	    // ขนาดของ player (1 tile)
	    double playerWidth = 0.85;
	    double playerHeight = 0.85;

	    // hitbox ของ player
	    double playerLeft = targetX;
	    double playerRight = targetX + playerWidth;
	    double playerTop = targetY;
	    double playerBottom = targetY + playerHeight;

	 // ✅ 1️⃣ ตรวจ collision กับ STATIC OBJECTS (loop จาก object instance)
	    for (StaticObject obj : staticObject) {
	        if (!obj.isCollidable()) continue;

	        double objLeft = obj.getX();
	        double objRight = objLeft + 1.0;
	        double objTop = obj.getY();
	        double objBottom = objTop + 1.0;

	        if (playerRight > objLeft && playerLeft < objRight &&
	            playerBottom > objTop && playerTop < objBottom) {
	            if (obj instanceof Interactable) {
	                setHitObject((Interactable)obj); // ✅ set เฉพาะ interactable object
	            }
	            return true;
	        }
	    }

	    // ✅ 2️⃣ ตรวจ collision กับ DYNAMIC OBJECTS
	    for (AnimatedObject obj : dynamicObject) {
	        if (!obj.getIsCollidable()) continue;

	        double objLeft = obj.getxPosition();
	        double objRight = objLeft + 1.0;
	        double objTop = obj.getyPosition();
	        double objBottom = objTop + 1.0;

	        if (playerRight > objLeft && playerLeft < objRight &&
	            playerBottom > objTop && playerTop < objBottom) {
	    	    setHitObject(obj);
	            return true;
	        }
	    }

	    setHitObject(null);
	    return false; // ไม่ชนอะไรเลย
	}
	public void setFeedback(String text) {
	        playerFeedbackText = text;
	        feedbackTimestamp = System.currentTimeMillis();
	    }
	private void drawCenteredLine(GraphicsContext gc, String line, double y, double maxWidth) {
	    Text textNode = new Text(line);
	    textNode.setFont(gc.getFont());
	    double textWidth = textNode.getLayoutBounds().getWidth();

	    double canvasWidth = getWidth(); // หรือ SceneManager.getWidth();
	    double x = (canvasWidth - textWidth) / 2;

	    gc.fillText(line, x, y);
	}

	private void drawWrappedText(GraphicsContext gc, String text, double yStart, double maxWidth) {
	    String[] words = text.split(" ");
	    StringBuilder line = new StringBuilder();
	    double lineHeight = 22;
	    double y = yStart;

	    for (String word : words) {
	        String testLine = line + (line.length() == 0 ? "" : " ") + word;

	        Text textNode = new Text(testLine);
	        textNode.setFont(gc.getFont());
	        double testWidth = textNode.getLayoutBounds().getWidth();

	        if (testWidth > maxWidth && line.length() > 0) {
	            drawCenteredLine(gc, line.toString(), y, maxWidth);
	            y += lineHeight;
	            line = new StringBuilder(word);
	        } else {
	            line = new StringBuilder(testLine);
	        }
	    }

	    if (line.length() > 0) {
	        drawCenteredLine(gc, line.toString(), y, maxWidth);
	    }
	}

	@Override
	public void render(GraphicsContext gc) {		
		 drawTiles(gc);
		    drawStaticObjects(gc);
		    drawDynamicObjects(gc);
		    gc.setFill(Color.ORANGE);
	        gc.fillRect(0, 0, 480, 14); //fill rect 
		    long now = System.currentTimeMillis();

		    if (!playerFeedbackText.isEmpty() && now - feedbackTimestamp <= FEEDBACK_DURATION) {
		        double alpha = 1.0 - (now - feedbackTimestamp) / (double) FEEDBACK_DURATION;
		        gc.setGlobalAlpha(alpha);
		        gc.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		        gc.setFill(Color.WHITE);

		        // คำนวณขนาดและเริ่มวาด
		        double maxWidth = 300; // เว้นขอบซ้าย-ขวาอย่างละ 20 px
		        double startY = 70;

		        drawWrappedText(gc, playerFeedbackText, startY, maxWidth); // yStart = 60, maxWidth = 300
		        gc.setGlobalAlpha(1.0);
		    }
	}

	@Override
	public void onEnter() {
	    preloadStaticObjects();

	}

	@Override
	public void reset() {		
		 // ✅ Reset container บน counter ทุกตัว
	    for (StaticObject obj : staticObject) {
	        if (obj instanceof Counter counter) {
	            counter.setContainerOnCounter(null); // หรือ counter.reset() ถ้ามี method
	        }
	        if (obj instanceof RollingMat rollingMat) {
	        	rollingMat.setDishOnMat(null);
	        }
	    }
	    TaskManager.finishCurrentTask();
        TaskManager.generateTask(); // ✅ สร้าง Task ใหม่ทันที
	    render(getGraphicsContext2D());
	}
	
	public static GameScreen getInstance() {
		if(GameScreen.instance == null) {
			GameScreen.instance = new GameScreen();
		}
		return GameScreen.instance;
	}
	public Interactable getHitObject() {
		return hitObject;
	}
	public void setHitObject(Interactable hitObject) {
		this.hitObject = hitObject;
	}
	
	
	
}
