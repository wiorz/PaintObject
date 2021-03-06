package model;

import java.awt.Point;
import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//@author: Ivan Ko

public class Oval extends PaintObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Oval(Color color, Point from, Point to) {
		super(color, from, to);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(GraphicsContext g) {
		g.setFill(color);
		double width = super.to.getX() - super.from.getX();
		double height = super.to.getY() - super.from.getY();
		// There are four possible situations:
		// 1. Both width and height are positive, meaning .from values are smaller than .to.
		// 2. .fromX value is smaller than .toX, height is positive.
		// 3. width is positive, but .fromY is smaller than .toY.
		// 4. Both width and height are negative.
		if (width >= 0) {
			if (height >= 0) {
				// 1.
				g.fillOval(super.from.getX(), super.from.getY(), width, height);
			} else {
				// 3.
				g.fillOval(super.from.getX(), super.to.getY(), width, Math.abs(height));
			}

		} else {
			if (height >= 0) {
				// 2.
				g.fillOval(super.to.getX(), super.from.getY(), Math.abs(width), height);
			} else {
				// 4.
				g.fillOval(super.to.getX(), super.to.getY(), Math.abs(width), Math.abs(height));
			}

		}

	}

}
