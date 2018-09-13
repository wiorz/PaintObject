package Demos;

// The compile time errors in the file will exist until the first step is
// taken to complete this project: Complete the PaintObject inheritance hierarchy.
//
import java.awt.Point;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Line;
import model.Oval;
import model.PaintObject;
import model.Picture;
import model.Rectangle;

/**
 * This class is a test for your inheritance hierarchy. 
 * It has a hard-coded Vector<PaintObject> and a method 
 * to draw all of those PaintObjects on a Canvas that has
 * been added as the center of a BorderPane. There are no
 * Events here. Just run this program after you finish 
 * the PaintObject inheritance hierarchy.
 *
 * @author Rick Mercer
 */
public class TestPaintHierarchy extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    ArrayList<PaintObject> allPaintObjects = createVectorOfPaintObjects();
    BorderPane all = new BorderPane();
    Canvas canvas = new Canvas(800, 550);
    all.setCenter(canvas);
    drawAllPaintObects(allPaintObjects, canvas);

    Scene scene = new Scene(all, 800, 550);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void drawAllPaintObects(ArrayList<PaintObject> allPaintObjects, Canvas canvas) {
    GraphicsContext gc = canvas.getGraphicsContext2D();
    for (PaintObject po : allPaintObjects) {
		po.draw(gc);
	}
  }

  private ArrayList<PaintObject> createVectorOfPaintObjects() {
    // All of these PaintObject objects must be constructed with two Point objects.
    // The first point could be to the upper left or to the lower right
    ArrayList<PaintObject> allPaintObjects = new ArrayList<>();
    // Create some Line objects, where any line must be drawn between the two Points
    // in the provided color.
    PaintObject a = new Line(Color.RED, new Point(10, 100), new Point(500, 100));
    PaintObject b = new Line(Color.CYAN, new Point(250, 150), new Point(250, 5));
    PaintObject c = new Line(Color.GREEN, new Point(255, 10), new Point(255, 145));
    PaintObject d = new Line(Color.BLACK, new Point(245, 145), new Point(245, 10));
    PaintObject oneMore = new Line(Color.BLACK, new Point(0, 0), new Point(245, 145));
    PaintObject anOther = new Line(Color.GRAY, new Point(500, 0), new Point(255, 145));
    allPaintObjects.add(a);
    allPaintObjects.add(b);
    allPaintObjects.add(c);
    allPaintObjects.add(d);
    allPaintObjects.add(oneMore);
    allPaintObjects.add(anOther);

    // Construct five Rectangle objects
    // First Point(200, 200) is above and the left of the second point
    PaintObject e = new Rectangle(Color.PINK, new Point(200, 200), new Point(350, 500));
    // First Point (150, 300) is below and to the right of the second point
    PaintObject f = new Rectangle(Color.CYAN, new Point(150, 300), new Point(100, 100));
    PaintObject g = new Rectangle(Color.GREEN, new Point(400, 200), new Point(420, 250));
    PaintObject h = new Rectangle(Color.BLUE, new Point(500, 380), new Point(360, 420));
    PaintObject i = new Rectangle(Color.RED, new Point(380, 520), new Point(540, 450));
    allPaintObjects.add(e);
    allPaintObjects.add(f);
    allPaintObjects.add(g);
    allPaintObjects.add(h);
    allPaintObjects.add(i);
//
    // Construct some Oval objects
    // First Point is above and the left of the 2nd point
    PaintObject j = new Oval(Color.CYAN, new Point(520, 20), new Point(620, 220));
    // First Point is below and to the right of the 2nd point
    PaintObject k = new Oval(Color.GREEN, new Point(730, 220), new Point(670, 20));
    PaintObject l = new Oval(Color.RED, new Point(760, 320), new Point(660, 220));
    // Another circle with upper left to the other side
    PaintObject m = new Oval(Color.BLUE, new Point(600, 350), new Point(700, 400));
    allPaintObjects.add(j);
    allPaintObjects.add(k);
    allPaintObjects.add(l);
    allPaintObjects.add(m);
//
    // Construct several Pictures
    // First Point is above and the left of the 2nd point
     PaintObject n = new Picture(new Point(2, 2), new Point(50, 50), "images/01.jpg");
     // First Point is above and the left of the 2nd point
     // The Picture objects to adjust values if the first point is below 
     // and to the right of the 2nd point.
    PaintObject o = new Picture(new Point(220, 280), new Point(140, 140), "images/01.jpg");
    PaintObject p = new Picture(new Point(600, 330), new Point(760, 450), "images/01.jpg");
    allPaintObjects.add(n);
    allPaintObjects.add(o);
    allPaintObjects.add(p);

    return allPaintObjects;
  }
}