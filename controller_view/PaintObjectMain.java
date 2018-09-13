package controller_view;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Line;
import model.Oval;
import model.PaintObject;
import model.Picture;
import model.Rectangle;

/**
 * A GUI for NetPaint that has all PaintObjects drawn on it. This file also
 * represents the controller as it controls how paint objects are drawn.
 * 
 * @author Rick Mercer and Ivan Ko
 */
public class PaintObjectMain extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	// A few needed instance variables
	private ArrayList<PaintObject> allPaintObjects;
	private Canvas drawingPanel;
	private GraphicsContext gc;
	private ColorPicker colorPicker = new ColorPicker();
	private BorderPane all;
	// Use to generate a valid filepath later.
	private String[] fileNameCollection = { "01.jpg", "02.jpg", "03.jpg", "04.jpg", "05.jpg" };
	private String currentFilePath;
	// Tracks if the first mouse lick was pressed.
	private boolean clickedOnce;
	private Point anchorPoint;

	// Use this to track the current shape that will
	// be drawn and added the the ArrayList<PaintObject>
	enum CurrentPaintObject {
		LINE, RECTANGLE, OVAL, PICTURE
	}

	private CurrentPaintObject currentShape = CurrentPaintObject.LINE;

	@Override
	public void start(Stage primaryStage) throws Exception {
		drawingPanel = new Canvas(560, 460);
		clickedOnce = false;
		anchorPoint = new Point();
		all = new BorderPane();
		all.setCenter(drawingPanel);

		gc = drawingPanel.getGraphicsContext2D();

		// You don't really need this here. Consider it as a review of JavaFX drawing.
		// drawAllFourTypesOfThings();
		addBottomButtons();
		addMouseHandlers();

		allPaintObjects = new ArrayList<PaintObject>();
		colorPicker.setValue(javafx.scene.paint.Color.RED);

		Scene scene = new Scene(all, 560, 540);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// You Don't need this code.  It is provided here as a review of how 
	// to draw with JavaFX. Feel free to delete it.
	private void drawAllFourTypesOfThings() {
		// Draw a white background
		gc.setFill(javafx.scene.paint.Color.WHITE);
		gc.fillRect(0, 0, drawingPanel.getWidth(), drawingPanel.getHeight());
		// Draw some things on the canvas
		gc.setFill(javafx.scene.paint.Color.BLUE);
		gc.strokeLine(0, 0, 600, 600);

		gc.fillOval(20, 20, 100, 100);
		gc.fillRect(120, 120, 200, 50);
		String path = generateRNDImageFilePath();
		Image image = new Image(path, false);
		gc.drawImage(image, 400, 20, 100, 100);

		// Should draw just a dot.
		//		gc.strokeLine(150, 180, 150, 180);
		//		Point p1 = new Point(200, 0);
		//		Point p2 = new Point(230, 400);
		//		colorPicker.setValue(Color.RED);
		//		PaintObject currentPO = determineWhat2DrawAndAddToList(p1, p2);
		//		currentPO.draw(gc);

	}

	// Make a string of relative filepath to a random valid file 
	// that was put inside the fileNameCollection.
	private String generateRNDImageFilePath() {
		StringBuilder resultBld = new StringBuilder();
		resultBld.append("images/");
		Random rndInd = new Random();
		int fileInd = rndInd.nextInt(5);
		resultBld.append(fileNameCollection[fileInd]);
		return resultBld.toString();
	}

	private void addBottomButtons() {
		GridPane bottomPanel = new GridPane();
		bottomPanel.setAlignment(Pos.CENTER);
		bottomPanel.setPadding(new Insets(0, 10, 15, 10));
		bottomPanel.setHgap(15);
		bottomPanel.setVgap(10);

		Button lineB = new Button("Line");
		Button rectB = new Button("Rectangle");
		Button ovalB = new Button("Oval");
		Button picB = new Button("Picture");

		lineB.setMaxWidth(Double.MAX_VALUE);
		rectB.setMaxWidth(Double.MAX_VALUE);
		ovalB.setMaxWidth(Double.MAX_VALUE);
		picB.setMaxWidth(Double.MAX_VALUE);

		bottomPanel.add(lineB, 0, 0);
		bottomPanel.add(rectB, 1, 0);
		bottomPanel.add(ovalB, 2, 0);
		bottomPanel.add(picB, 3, 0);
		bottomPanel.add(colorPicker, 4, 0);

		// Add button eventHandlers
		lineB.setOnAction(event -> {
			currentShape = CurrentPaintObject.LINE;
			System.out.println(currentShape);
		});
		rectB.setOnAction(event -> {
			currentShape = CurrentPaintObject.RECTANGLE;
			System.out.println(currentShape);
		});
		ovalB.setOnAction(event -> {
			currentShape = CurrentPaintObject.OVAL;
			System.out.println(currentShape);
		});
		picB.setOnAction(event -> {
			currentShape = CurrentPaintObject.PICTURE;
			System.out.println(currentShape);
		});

		all.setBottom(bottomPanel);
		BorderPane.setAlignment(all.getBottom(), Pos.TOP_CENTER);
	}

	// Mouse events:
	// 1. Only listen to setOnMouseMove/Exit if clickedOnce is true.
	// 2. Can only set clickedOnce state through setOnMouseClicked
	// 3. doubleclick without moving the mouse draws a dot.
	// 4. redraw everything in allPaintObjects everytime the mouse moves (and clickedOnce is true)
	// 5. Add the final drawn item to allPaintObjects when clickOnce is true and got clicked again.
	//
	// !!! Important to track anchorPoint - where the base/start point of the paintObject.
	// Upon mouseExit, temporary set the "to" point to the point of exit, BUT without adding to 
	// allPaintObjects as it's not finalized by the second click.
	private void addMouseHandlers() {

		drawingPanel.setOnMouseClicked(event -> {
			// If there has been one click already, meaning this click
			// is the second click, thus finalizing the draw.
			if (clickedOnce) {
				clickedOnce = false;
				Point point2 = new Point((int) event.getX(), (int) event.getY());
				PaintObject currentPO = determineWhat2Draw(anchorPoint, point2);
				allPaintObjects.add(currentPO);
				currentPO.draw(gc);
			} else {
				clickedOnce = true;
				anchorPoint = new Point((int) event.getX(), (int) event.getY());
				// Important to set the filePath here. 
				currentFilePath = generateRNDImageFilePath();
				// Check if there was a double click.
				if (event.getClickCount() == 2) {
					clickedOnce = false;
					Point point2 = new Point((int) event.getX(), (int) event.getY());
					PaintObject currentPO = determineWhat2Draw(anchorPoint, point2);
					allPaintObjects.add(currentPO);
					currentPO.draw(gc);
				}
			}
		});

		drawingPanel.setOnMouseMoved(event -> {
			if (clickedOnce) {
				// Redraw everything upon mouse move
				drawAllPaintObjectsWithRefresh();
				Point point2 = new Point((int) event.getX(), (int) event.getY());
				PaintObject currentPO = determineWhat2Draw(anchorPoint, point2);
				currentPO.draw(gc);
			}
		});

	}

	private PaintObject determineWhat2Draw(Point from, Point to) {
		PaintObject result;
		if(currentShape == CurrentPaintObject.LINE) {
			result = new Line(colorPicker.getValue(), from, to);
		} else if(currentShape == CurrentPaintObject.RECTANGLE) {
			result = new Rectangle(colorPicker.getValue(), from, to);
		} else if(currentShape == CurrentPaintObject.OVAL) {
			result = new Oval(colorPicker.getValue(), from, to);
		} else {
			result = new Picture(from, to, currentFilePath);
		}
		
		return result;

	}

	private void drawAllPaintObjectsWithRefresh() {
		// Refresh screen first
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, 560, 460);
		// Reset the current color.
		gc.setFill(colorPicker.getValue());
		for (PaintObject po : allPaintObjects) {
			po.draw(gc);
		}
	}
}