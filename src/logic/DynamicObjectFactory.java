package logic;

import container.Chiller;
import container.Sink;
import container.Stove;

public class DynamicObjectFactory {
	 public static AnimatedObject createDynamicObject(String name, DynamicObjectDetail detail) {
	        switch (detail.getName()) {
	            case "stove":
	                return new Stove(name, detail.getFrames(), detail.getX(), detail.getY(), detail.isInteractable(), detail.isCollidable());
	            case "sink":
	            	return new Sink(name, detail.getFrames(), detail.getX(), detail.getY(), detail.isInteractable(), detail.isCollidable());
	            case "chiller":
	            	return new Chiller(name,detail.getFrames(),detail.getX(), detail.getY(), detail.isInteractable(), detail.isCollidable() );
	            default:
	                return new AnimatedObject(name, detail.getFrames(), detail.getX(), detail.getY(), detail.isInteractable(), detail.isCollidable());
	        }
	    }
}
