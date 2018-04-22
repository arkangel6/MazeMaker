import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;


public class MazeMaker{
	
	private static int width;
	private static int height;
	
	private static Maze maze;
	
	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();
	
	
	public static Maze generateMaze(int w, int h){
		width = w;
		height = h;
		maze = new Maze(width, height);
		for(int i = 0; i <  h; i++) {
			for(int j = 0; j < w; j++) {
				maze.getCell(i, j).setName(i + "," + j);
			}
		}
		
		for(int i = 0; i <  h; i++) {
			for(int j = 0; j < w; j++) {
				System.out.print(maze.getCell(i, j).getName() + "|");
			}
				System.out.println();
		}
		
		
		
		
		
		//select a random cell to start
		//int x = randGen.nextInt(uncheckedCells.size());
		selectNextPath(maze.getCell(0, 0));
		//call selectNextPath method with the randomly selected cell
		
		return maze;
	}

	private static void selectNextPath(Cell currentCell) {
		// mark current cell as visited
		currentCell.setBeenVisited(true);
		// check for unvisited neighbors
		
		
		if(getUnvisitedNeighbors(currentCell).size() > 0) {
			int x = randGen.nextInt(getUnvisitedNeighbors(currentCell).size());
			System.out.println("unvistedsize: "+ getUnvisitedNeighbors(currentCell).size());
			System.out.println("random: " + x);
			Cell newCell = getUnvisitedNeighbors(currentCell).get(x);
			uncheckedCells.push(newCell);
			
			
			System.out.println("NewCell: "  + newCell.getName());
			
			
			
			
			removeWalls(currentCell, newCell);
			newCell.setBeenVisited(true);
			selectNextPath(newCell);
			
		}
		else if(!(uncheckedCells.isEmpty())) {
				currentCell = uncheckedCells.pop();
				System.out.println("else: " + currentCell.getName());
				selectNextPath(currentCell);
				
			
		}
		else if(uncheckedCells.isEmpty()) {
			
			for(int i = 0; i <  maze.getHeight(); i++) {
				for(int j = 0; j < maze.getWidth(); j++) {
					
						int counter = 0;
						if(maze.getCell(i, j).hasEastWall()) {
							counter++;
						}
						if(maze.getCell(i, j).hasWestWall()) {
							counter++;
						}
						if(maze.getCell(i, j).hasNorthWall()) {
							counter++;
						}
						if(maze.getCell(i, j).hasSouthWall()) {
							counter++;
						}
						if(counter == 4) {
							selectNextPath(maze.getCell(i, j));
						}
						
					}
				}
			}
			
			
		}
		
		// if has unvisited neighbors,
			// select one at random.
			// push it to the stack
			// remove the wall between the two cells
			// make the new cell the current cell and mark it as visited
		
			//call the selectNextPath method with the current cell
			
		// if all neighbors are visited
			//if the stack is not empty
				// pop a cell from the stack
				// make that the current cell
		
				//call the selectNextPath method with the current cell
	

	private static void removeWalls(Cell c1, Cell c2) {
		
		System.out.println("c1: "+c1.getName());
		System.out.println("c2: " + c2.getName());;
		if(c1.getX() <= width && c2.getX() <= width && c1.getX() >= 0 && c2.getX() >= 0) {
		if(c1.getX() < c2.getX()) {
			//c1 to the left of c2
			c1.setEastWall(false);
			c2.setWestWall(false);
			System.out.println("removed: " + c1.getName() + " east " + c2.getName() + " west");
		}
		else if(c1.getX() > c2.getX()){
			//c1 to the right of c2
			c2.setEastWall(false);
			c1.setWestWall(false);
			System.out.println("removed: " + c1.getName() + " west " + c2.getName() + " east");
		}
		}
		
		
		if(c1.getY() <= height-1 && c2.getY() <= height-1 && c1.getY() >= 0 && c2.getY() >= 0) {
		if(c1.getY() < c2.getY()) {
			//c1 above c2
			c1.setSouthWall(false);
			c2.setNorthWall(false);
			System.out.println("removed: " + c1.getName() + " south " + c2.getName() + " north");
		}
		else if(c1.getY() > c2.getY()) {
			//c1 below c2
			c1.setNorthWall(false);
			c2.setSouthWall(false);
			System.out.println("removed: " + c1.getName() + " north " + c2.getName() + " south");
		}
	
		}
		
	}

	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		
		ArrayList<Cell> unvisited= new ArrayList<Cell>();
		
		/*if(c.getX() + 1 < width) {
		if(maze.getCell(c.getX()+1, c.getY()).hasBeenVisited() == false) {
			unvisited.add(maze.getCell(c.getX()+1, c.getY()));
			
		}
		}
		if(c.getX() - 1 > 0) {
		if(!maze.getCell(c.getX()-1, c.getY()).hasBeenVisited()== false) {
			unvisited.add(maze.getCell(c.getX()-1, c.getY()));
		}
		}
		if(c.getY() + 1 < maze.getHeight()) {
		if(maze.getCell(c.getX(), c.getY()+1).hasBeenVisited()== false) {
			unvisited.add(maze.getCell(c.getX(), c.getY()+1));
		}
		}
		if(c.getY() - 1 > 0) {
		if(maze.getCell(c.getX(), c.getY()-1).hasBeenVisited()== false) {
			unvisited.add(maze.getCell(c.getX(), c.getY()-1));
		}
		}
		*/
		if(c.getX() + 1 < width) {
			if(maze.getCell(c.getX()+1, c.getY()).hasAllWalls()) {
				unvisited.add(maze.getCell(c.getX()+1, c.getY()));
				
			}
			}
			if(c.getX() - 1 > 0) {
			if(!maze.getCell(c.getX()-1, c.getY()).hasAllWalls()) {
				unvisited.add(maze.getCell(c.getX()-1, c.getY()));
			}
			}
			if(c.getY() + 1 < maze.getHeight()) {
			if(maze.getCell(c.getX(), c.getY()+1).hasAllWalls()) {
				unvisited.add(maze.getCell(c.getX(), c.getY()+1));
			}
			}
			if(c.getY() - 1 > 0) {
			if(maze.getCell(c.getX(), c.getY()-1).hasAllWalls()) {
				unvisited.add(maze.getCell(c.getX(), c.getY()-1));
			}
			}
			
		
		
		for(int i = 0; i < unvisited.size(); i++) {
			int counter = 0;
			if(unvisited.get(i).hasEastWall()) {
				counter++;
			}
			if(unvisited.get(i).hasWestWall()) {
				counter++;
			}
			if(unvisited.get(i).hasNorthWall()) {
				counter++;
			}
			if(unvisited.get(i).hasSouthWall()) {
				counter++;
			}
			if(counter == 4) {
				
			}
			else {
				unvisited.remove(i);
			}
			
		}
		
		
		for(Cell ce : unvisited) {
			System.out.print(ce.getName() + " ");
		}
		System.out.println();
		
		
		return unvisited;
		
	}
}