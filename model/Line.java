package model;

import java.awt.Point;
import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Line extends PaintObject implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public Line(Color color, Point from, Point to) {
    super(color, from, to);
  }

  /**
   * 	Draws a line to the given context
   * 
   * 	@param the context to render to
   */
  @Override
public void draw(GraphicsContext g) {
    g.setStroke(color);
    g.strokeLine(super.from.getX(), super.from.getY(), super.to.getX(), super.to.getY());
  }
}
