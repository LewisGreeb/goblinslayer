package adventure.pkg;

public class Compass {

    // Attributes.
    private double distance;

    // Constructor.
    public Compass(int startX, int startY, int exitX, int exitY){
        double deltaY = startY - exitY;
        double deltaX = startX - exitX;
        this.distance = Math.sqrt((deltaY*deltaY) + (deltaX*deltaX));
    }

    // Methods.
    public void evaluatePos(int x, int y, int exitX, int exitY){
        double deltaY = y - exitY;
        double deltaX = x - exitX;
        double newDist = Math.sqrt((deltaY*deltaY) + (deltaX*deltaX));

        if(newDist > this.distance){
            System.out.println("The stone grows colder in your hand.");
        }else{
            System.out.println("The stone grows warmer in your hand.");
        }

        this.distance = newDist;
    }
}
