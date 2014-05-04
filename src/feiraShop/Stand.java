package feiraShop;

public class Stand {
	private String name;
	private double X;	
	private double width;
	private double Y;
	private double height;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getX() {
		return X;
	}
	public void setX(double x) {
		X = x;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getY() {
		return Y;
	}
	public void setY(double y) {
		Y = y;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getEndX(){
		return this.X + this.getWidth();
	}
	public double getEndY(){
		return this.Y + this.getHeight();
	}
	public double getCenterX(){
		return this.getX()+(this.getWidth()/2);
	} 
	public double getCenterY(){
		return this.getY() + (this.getHeight()/2);
	}		
}
