public class Coordinate {

  /**
   * Constructor of Coordinate
   * @param x coordinate of x-axis
   * @param y coordinate of y-axis
   */
  public Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  int x;
  int y;

  /**
   * A function check if two coordinate are equal
   * @param obj another object to be compared with this coordinate
   * @return true if it is also a Coordinate object and has same x and y
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Coordinate) {
      Coordinate other = (Coordinate) obj;
      return other.x == this.x && other.y == this.y;
    }
    return false;
  }
}
