package assignment9;

import java.awt.Color;
import java.util.LinkedList;

import edu.princeton.cs.introcs.StdDraw;

public class Snake {

	private static final double SEGMENT_SIZE = 0.02;
	private static final double MOVEMENT_SIZE = SEGMENT_SIZE * 1.3;
	private LinkedList<BodySegment> segments;
	private double deltaX;
	private double deltaY;
	
	public Snake() {
		deltaX = 0;
		deltaY = 0;
		segments = new LinkedList<BodySegment>();
		BodySegment bs = new BodySegment(0.2, 0.5, SEGMENT_SIZE);
		segments.add(bs);
	}
	
	public void changeDirection(int direction) {
		if(direction == 1 && deltaY != -MOVEMENT_SIZE) { //up
			deltaY = MOVEMENT_SIZE;
			deltaX = 0;
		} else if (direction == 2 && deltaY != MOVEMENT_SIZE) { //down
			deltaY = -MOVEMENT_SIZE;
			deltaX = 0;
		} else if (direction == 3 && deltaX != MOVEMENT_SIZE) { //left
			deltaY = 0;
			deltaX = -MOVEMENT_SIZE;
		} else if (direction == 4 && deltaX != -MOVEMENT_SIZE) { //right
			deltaY = 0;
			deltaX = MOVEMENT_SIZE;
		}
	}
	
	/**
	 * Moves the snake by updating the position of each of the segments
	 * based on the current direction of travel
	 */
	public void move() {
		for(int i = segments.size() - 1; i > 0; i--) {
			BodySegment current = segments.get(i);
			BodySegment previous = segments.get(i - 1);
			current.setX(previous.getX());
			current.setY(previous.getY());
		}
		BodySegment head = segments.get(0);
		head.setX(head.getX() + deltaX);
		head.setY(head.getY() + deltaY);
	}
	
	/**
	 * Draws the snake by drawing each segment
	 */
	public void draw() {
		for(BodySegment bs : segments) {
			bs.draw();
		}
	}
	
	/**
	 * The snake attempts to eat the given food, growing if it does so successfully
	 * @param f the food to be eaten
	 * @return true if the snake successfully ate the food
	 */
	public boolean eatFood(Food f) {
		BodySegment head = segments.get(0);
		double distance = Math.sqrt(Math.pow(f.getX() - head.getX(), 2) + Math.pow(f.getY() - head.getY(), 2));
		if(distance <= SEGMENT_SIZE) {
			BodySegment tail = segments.get(segments.size() - 1);
			BodySegment newSegment = new BodySegment(tail.getX() - deltaX, tail.getY() - deltaY, SEGMENT_SIZE);
			segments.add(newSegment);
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if the head of the snake is in bounds
	 * @return whether or not the head is in the bounds of the window
	 */
	public boolean isInbounds() {
		BodySegment head = segments.get(0);
		double x = head.getX();
		double y = head.getY();
		if(x < 0 || x > 1 || y < 0 || y > 1) {
			StdDraw.setPenColor(Color.RED);
			StdDraw.text(0.5, 0.5, "Game Over");
			StdDraw.show();
			return false;
		}
		for(int i = 2; i < segments.size(); i++) {
			double distance = Math.sqrt(Math.pow(x - segments.get(i).getX(), 2) + Math.pow(y - segments.get(i).getY(), 2));
			if(distance <= SEGMENT_SIZE) {
				StdDraw.setPenColor(Color.RED);
				StdDraw.text(0.5, 0.5, "Game Over");
				StdDraw.show();
				return false;
			}
		}
		return true;
	}
	
	public int getLength() {
		return segments.size();
	}
}
