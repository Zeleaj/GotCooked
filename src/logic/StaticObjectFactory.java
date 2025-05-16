package logic;

import container.Counter;
import container.CounterWithChoppingBoard;
import container.FoodDeliveryChannel;
import container.RollingMat;
import container.Table;
import container.Trashcan;

public class StaticObjectFactory {
	public static StaticObject createStaticObject(String name, StaticObjectDetail detail, double x, double y) {
		switch (name) {
        case "counterWithChoppingBoard":
            return new CounterWithChoppingBoard(name, detail.getStaticImageURL(), x, y, detail.isCollidable(), detail.isInteractable());
		case "counter":
			return new Counter(name,detail.getStaticImageURL(),x,y,detail.isCollidable(),detail.isInteractable());
		case "rollingMat" :
			return new RollingMat(name,detail.getStaticImageURL(),x,y,detail.isCollidable(),detail.isInteractable());
		case "table":
			return new Table(name, detail.getStaticImageURL(),x,y,detail.isCollidable(),detail.isInteractable());
		case "trashcan":
			return new Trashcan(name, detail.getStaticImageURL(),x,y,detail.isCollidable(),detail.isInteractable());
		case "foodDeliveryChannel":
			return new FoodDeliveryChannel(name,detail.getStaticImageURL(),x,y,detail.isCollidable(),detail.isInteractable());
			
        default:
            return new StaticObject(name, detail.getStaticImageURL(), x, y, detail.isCollidable(), detail.isInteractable());
    }
}}
