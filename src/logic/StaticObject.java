package logic;

public class StaticObject {
	    private String name;
	    private String imageUrl;
	    private boolean isCollidable;
	    private boolean isInteractable;
	    private double x;
	    private double y;
	    
	    public StaticObject(String name, String imageUrl,  double x, double y ,boolean isCollidable, boolean isInteractable) {
	        setName(name);
	        setImageUrl(imageUrl);
	        setX(x);
	        setY(y);
	        setCollidable(isCollidable);
	        setInteractable(isInteractable);
	    }

	    public String getName() { return name; }
	    public String getImageUrl() { return imageUrl; }
	    public boolean isCollidable() { return isCollidable; }
	    public boolean isInteractable() { return isInteractable; }

		public double getX() {
			return x;
		}

		public void setX(double x) {
			this.x = x;
		}

		public double getY() {
			return y;
		}

		public void setY(double y) {
			this.y = y;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public void setCollidable(boolean isCollidable) {
			this.isCollidable = isCollidable;
		}

		public void setInteractable(boolean isInteractable) {
			this.isInteractable = isInteractable;
		}

		
	    
	}


