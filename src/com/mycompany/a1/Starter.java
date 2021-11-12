/*********************************************************
 * Palvinder Singh
 * CSC 133 Assignment 1 
 * 
 * 
 **********************************************************/

package com.mycompany.a1;

import static com.codename1.ui.CN.*;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.io.Log;
import com.codename1.ui.Toolbar;

import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.codename1.ui.layouts.BoxLayout;
import com.codename1.io.NetworkEvent;

/**
 * Primary Class for encapsulation
 */
class Game extends Form {
	private GameWorld gw;
	public Game() {
		//Object of gameworld created
		gw = new GameWorld();
		//Gameworld instantiated
		gw.init();
		//Play method for user input
		play();
	}

	//This method ask user for input for various commands
	private void play() {
		Label myLabel = new Label("Enter a Command:");
		this.addComponent(myLabel);
		final TextField myTextField = new TextField();
		this.addComponent(myTextField);
		//final TextField quit= new TextField();
		this.show();
		myTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				boolean flag;
				//boolean visited;
				String sCommand = myTextField.getText().toString();
				myTextField.clear();
				if (sCommand.length() != 0)
					switch (sCommand.charAt(0)) {
					// Accelerate the speed of ant by small amount (tell the gameworld to
					// accelerate))
					case 'a':
						gw.accelerate();
						break;

					// Reduce the speed of ant by small amount (tell the gameworld to break)
					case 'b':
						gw.brake();
						break;

					// tell the game world to change the heading of ant by 5 degrees to left(in the
					// negative direction on compass)
					case 'l':
						gw.directLeft();
						break;

					// tell the game world to change the heading of ant by 5 degrees to right(in
					// positive direction on compass)
					case 'r':
						gw.directRight();
						break;

					// Pretend the ant has collided with food station (Tell the gameworld that his
					// collision has occured)
					// Increase the ant's food level by the capacity of the food station
					case 'f':
						gw.foodCollision();
						break;

					// Pretend the spider has gotten to the same collided with the ant. The effect
					// of colliding with a
					// spider is to decrease the health level of the ant as described above (Fade
					// the color or ant -become lighter red)
					case 'g':
						gw.antCollision();
						break;

					// Tell the gameworld the "game clock" has ticked
					// following effects: 1) Spiders update their heading as indicated above.
					// 2)All movable objects are told to update their positions according to their
					// current heading and speed
					// 3)Ant's food level is reduced by the amount indicated by its
					// foodComsumptionRate
					// 4)Elapsed time "game clock" is incremented by 1
					case 't':
						gw.tickClock();
						break;

					// display by outputting lines of text on the console describing current game/
					// ant state values
					// display should include following: 1)The number of lives left. (2) the current
					// clock value (elapsed time)
					// 3)The highest flag number the ant has reached sequentially so far (ex.
					// lastFlagReached).
					// 4)Ant's current food level (i.e. foodLevel) and (5) ant's health (i.e.
					// healthLevel)

					case 'd':
						gw.displayGame();
						break;

					// Tell the game world to output a "map" showing the current world
					case 'm':
						gw.displayMap();
						break;

					// Exit to terminate the program, but ask user for quitting
					case 'x':
						exit();
						
						break;
						
					//1st Flag Reached or Initial Flag
					case '1':
						gw.flagReaching(sCommand);
						break;
					
					//2nd Flag Reached	
					case '2':
						gw.flagReaching(sCommand);
						break;
						
					//3rd Flag Reached	
					case '3':
						gw.flagReaching(sCommand);
						break;
						
					//Final 	
					case '4':
						gw.flagReaching(sCommand);
						break;
						
					default:
						Label error = new Label("Error: Invalid Input                ");
						add(error);
						show();
					} // switch
			} // actionPerformed
			
			//exit class if for when user input x for quit, to make sure he want to quir
			private void exit() {
				Label myLabel = new Label("Are you sure?:");
				addComponent(myLabel);
				final TextField myTextField = new TextField();
				addComponent(myTextField);
				//final TextField quit= new TextField();
				show();
				myTextField.addActionListener(new ActionListener(){
	                public void actionPerformed(ActionEvent evt) {
	                String sCommand=myTextField.getText().toString();
	                myTextField.clear();
	                if(sCommand.length() != 0)
	                      switch (sCommand.charAt(0)) {
	                         	//Y for user wants to quit
	                      	   case 'y':
	                                 gw.exitGame();
	                                 break;
	                            //N if user doesnt want to quit     
	                           case 'n':
	                        	   		myLabel.remove();
	                        	   		myTextField.remove();
	                        	   		break;
	                        	   	//Input Validation	
	                        	   default:
	                        		   Label error = new Label("Error: Invalid Input                ");
	                        		   add(error);
	                        		   show();
	                        		   break;
	                           //add code to handle rest of the commands
	                      } //switch
	                } //actionPerformed
	           } //new ActionListener()
	           );
				
			}
		} // new ActionListener()
		); // addActionListener
	} // play
}

/**
 * Holds collection of game objects and other state variables
 * 
 *
 */
class GameWorld {
	private int height =1000;
	private int width =1000;
	private int tick= 0;
	private int livesRemaining= 3;
	private ArrayList<Object> obj = new ArrayList<Object>();
	public void init() {
	/*	Container mapContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
		mapContainer.setSize(new Dimension(1000, 1000));
		TextArea dimensions = new TextArea("Enter a size");
		mapContainer.addComponent(dimensions);
		show();
	*/
		Point p1 = new Point((float) 0.0, (float) 0.0);
		Point p2= new Point ((float) 200.5, (float) 200.5);
		Point p3 = new Point((float) 700.5, (float) 800.4);
		Point p4= new Point ((float) 900.2, (float) 400.3);
		Random random= new Random();
		
		//Flag 1 / Initial Flag (int size, int color, Point location, int sequenceNumber) 
		Flags flag1 = new Flags(10, ColorUtil.BLUE, p1, 1);
	
		//Flag 2
		Flags flag2 = new Flags(10, ColorUtil.BLUE, p2, 2);
	
		//Flag 3
		Flags flag3 = new Flags(10, ColorUtil.BLUE, p3, 3);
			
		//Flag 4
		Flags flag4 = new Flags(10, ColorUtil.BLUE, p4, 4);
		
		//Ant(int size, int color, Point location, int heading, int speed,int maximumSpeed, int foodLevel, int foodConsumption,int healthLevel,int lastFlagReached)
		Ant ant= new Ant(10,ColorUtil.rgb(255, 0, 0),p1,0, 5, 50, 20, 2, 10, 1);
		
		//Spider1 (size,color,location,heading,speed)
		Point spider1Location= new Point((float) random.nextInt(1000),(float) random.nextInt(1000));
		Spider spider1= new Spider(random.nextInt(20)+1, ColorUtil.BLACK, spider1Location, random.nextInt(360), random.nextInt(10)+5);
	
		//Spider2 (size,color,location,heading,speed)
		Point spider2Location= new Point((float) random.nextInt(1000),(float) random.nextInt(1000));
		Spider spider2= new Spider(random.nextInt(20)+1, ColorUtil.BLACK, spider2Location, random.nextInt(360), random.nextInt(10)+5);
	
		//Food Station 1 (int size, int color, Point location, int capacity)
		int size1=random.nextInt(40)+10;
		Point food1Location= new Point ((float) random.nextInt(1000), (float) random.nextInt(1000));
		FoodStations foodStation1 = new FoodStations(size1,ColorUtil.GREEN,food1Location,size1);
		
		//Food Station 2(int size, int color, Point location, int capacity)
		int size2=random.nextInt(40)+10;
		Point food2Location= new Point ((float) random.nextInt(1000), (float) random.nextInt(1000));
		FoodStations foodStation2 = new FoodStations(size2,ColorUtil.GREEN,food2Location,size2);
		
		//All objects added into ArrayList
		obj.add(flag1);
		obj.add(flag2);
		obj.add(flag3);
		obj.add(flag4);
		obj.add(ant);
		obj.add(spider1);
		obj.add(spider2);
		obj.add(foodStation1);
		obj.add(foodStation2);
		
	}
	
	//This method keeps track of user input of flag numbers and makes sure flag reached in order
	public void flagReaching(String sCommand) {
		String input= sCommand;
		//Parsing user key command for flags into integer value
		int inum=Integer.parseInt(input);
		int i=0;
		//Locating instance of ant in the arraylist
		while( !(obj.get(i) instanceof Ant)) {
			i++;
		}
		//Checking to make sure flags reached are in order
		if(inum > ((Ant) obj.get(i)).getLastFlagReached() && inum<((Ant) obj.get(i)).getLastFlagReached()+2) {
			((Ant) obj.get(i)).setLastFlagReached();
		}
		if(((Ant) obj.get(i)).getLastFlagReached()==4) {
			winner();
		}
	}
	
	//Winner method displays when the user has reached all flags in order
	private void winner() {
		System.out.println();
		System.out.println("------------------------------------");
		System.out.println("Congraturlation You have Won the Game");
		System.out.println("------------------------------------");
		displayGame();
		System.exit(0);
	}
	
	//This method is called when user either quits the game, or gameover when user fails to complete the game
	public void exitGame() {
		System.exit(0);
	} 
	
	//This method displays the map of the game of all objects
	public void displayMap() {
		System.out.println("Map of the game:");
		for(int i=0; i<obj.size();i++) {
			System.out.println(obj.get(i).toString());
			System.out.println();
		}

	}
	
	//This methods displays game statistics of specific attributes such as time, lives remaining
	public void displayGame() {
		int l=0;
		while (!(obj.get(l) instanceof Ant)) {
			l++;
		}
		System.out.println("Game Statistics:");
		System.out.println("");
		System.out.println("Lives Left: " + livesRemaining + " |||| Elasped Time: " +tick+ " |||| Highest Flag Reached: " + ((Ant) obj.get(l)).getLastFlagReached() + " |||| Current Food level: " + ((Ant) obj.get(l)).getFoodLevel() + " |||| Ants Health Level: " + ((Ant) obj.get(l)).getHealthLevel());
	}
	
	//Updates Heading of Spider, all moveable Objects depending on speed and heading
	//reduced ant food and elapsed clock time
	public void tickClock() {
		setTick();
		Random range= new Random();
		int pathRandom= 0;
		System.out.println();
		for (int i=0; i<obj.size(); i++) {
			//Checks for instance of Moveable
			if(obj.get(i) instanceof Moveable) {
				Moveable mobj= (Moveable) obj.get(i);
				//Checks if the object is instance of Spider
				if(obj.get(i) instanceof Spider) {
					//Random value between 0 or 1 to randomly either add or subtract 5 degrees from heading of spider
					pathRandom=range.nextInt(1);
					switch (pathRandom) {
						//Adds five degrees to the heading of spider
						case 0:
							((Moveable) obj.get(i)).setHeading(((Moveable) obj.get(i)).getHeading()+5);
							mobj.move(((Spider) obj.get(i)).getHeading() + 5);
							break;
						//Subtracts five degrees to the heading of the spider
						case 1:
							((Moveable) obj.get(i)).setHeading(((Moveable) obj.get(i)).getHeading()+5);
							mobj.move(((Spider) obj.get(i)).getHeading() - 5);
							break;
					}
					//Checks for spider being out of bound, then turns the spider around
					if(((Moveable) obj.get(i)).getLocation().getY() >=height || ((Moveable) obj.get(i)).getLocation().getY()<=0.0 || ((Moveable) obj.get(i)).getLocation().getX() >=width || ((Moveable) obj.get(i)).getLocation().getX() <=0.0 ) {
						//Subtract 90 degrees from the current outofbounds heading
						((Moveable) obj.get(i)).setHeading(((Moveable) obj.get(i)).getHeading()-90);
					
					}	
				//if instance of ant, then just call the move method		
				} else if (obj.get(i) instanceof Ant) {
					mobj= (Moveable) obj.get(i);
					mobj.move();
				}
				
			}
		}
		//Subtracts food level from ant capacity at each second
		for (int i=0; i<obj.size(); i++) {
			//Checks for lives remaining
			if(getLivesRemaining()!=0) {
				if(obj.get(i) instanceof Ant ) {
					if (((Ant) obj.get(i)).getFoodLevel()>0) {
						((Ant) obj.get(i)).setFoodLevel();
					}
					else {
						//food level is 0, acceleration is 0, then it re-initialized the objects 
						((Ant) obj.get(i)).setSpeed(0);
						obj.removeAll(obj);
						init();
						//Subtract 1 lives remaining
						setLivesRemaining();
					}
				}
			 }else {	 
				 //Once no lives are remaining, calls game over
				 gameOver();	 
			 }
		}
		System.out.println();
	}
	
	//This method displays gameover screen, then quits the game
	private void gameOver() {
		
		System.out.println("----------------------------");
		System.out.println("           Game Over        ");
		System.out.println("----------------------------");
		exitGame();
	}
	
	//This method detects collision of ant with spider
	public void antCollision() {
		int k=0;
		//finds instance of Ant in arraylist
		while(!(obj.get(k) instanceof Ant)){
			k++;
		}
		
		//Checks for if ant health is not 0
		if (((Ant) obj.get(k)).getHealthLevel() !=0) {
			//Subtract 1 from health of ant
			((Ant) obj.get(k)).setHealthLevel();
			//Decrease speed by 5
			brake();
			//Change color of ant to light red
			((Ant) obj.get(k)).setColor(ColorUtil.rgb(225, 164, 164));
		//If ant's health is 0 , re-initializes the game and subtacts 1 lives remaining
		} else if (((Ant) obj.get(k)).getHealthLevel() ==0){
			obj.removeAll(obj);
			init();
			setLivesRemaining();
		}
		//If lives remaining is 0, call gameover
		if(getLivesRemaining() == 0) {
			gameOver();
		}
	}	
	
	//Ant collides with food station
	public void foodCollision() {
		Random random= new Random();
		boolean found = false;
		//Counts the total food to add to ant
		int totalFood=0;
		for (int i=0; i<obj.size(); i++) {
			//Checks for instance of Food Station and found is false
			if(obj.get(i) instanceof FoodStations && found !=true) {
				if(((FoodStations) obj.get(i)).getCapacity() !=0) {
					//Get Capacity of collided food station % save it in temporary variable
					totalFood= ((FoodStations) obj.get(i)).getCapacity();
					//Set Current collided food station to 0
					((FoodStations) obj.get(i)).setCapacity(0);
					//Fade the food station one capacity is 0
					((FoodStations) obj.get(i)).setColor(ColorUtil.rgb(144,238,144));
					found=true;
					//Checks for food station value being 0 and add another food station to object with random location and random food capacity
					if (((FoodStations) obj.get(i)).getCapacity() ==0) {
						int cap= random.nextInt(40)+10;
						Point food3Location= new Point ((float) random.nextInt(1000), (float) random.nextInt(1000));
						FoodStations foodStation3 = new FoodStations(cap,ColorUtil.GREEN,food3Location,cap);
						obj.add(foodStation3);
					}
				}
			}
		}
		//Find instance of ant
		for(int j=0;j<obj.size();j++) {
			if(obj.get(j) instanceof Ant) {
				//Adds the food station capacity to food level of ant
				((Ant) obj.get(j)).setFoodLevel(totalFood);
			}
		}
	}
	
	//This method changes the direction of ant 5 degrees to the right
	public void directRight() {
		int j=0;
		while (!(obj.get(j) instanceof Ant)) {
			j++;
		}
		//Changes direction of ant to right on command
		((Ant) obj.get(j)).directionRight();
	}

	//This method changes the direction of ant 5 degrees to the left
	public void directLeft() {
		int j=0;
		while (!(obj.get(j) instanceof Ant)) {
			j++;
		}
		//Changes direction of ant to left on command
		((Ant) obj.get(j)).directionLeft();
	}

	//This method decreases acceleration of the ant by 5
	public void brake() {
		if (((Ant) obj.get(4)).getSpeed() > 0 &&  ((Ant) obj.get(4)).getFoodLevel() !=0) {
			((Moveable) obj.get(4)).setSpeedDec();		
		}
	}

	//This method increases acceleration of the any by 5
	public void accelerate(){
		int k=0;
		//Find ant object
		while (!(obj.get(k) instanceof Ant)) {
			k++;
		}
		//Checks if the health is lower, to account to maximum speed being limited to health of ant
		if (((Ant) obj.get(k)).getSpeed() < ((Ant) obj.get(k)).getMaximumSpeed()) {
			int acceleration= (((Ant) obj.get(k)).getMaximumSpeed()-((Ant) obj.get(k)).getHealthLevel());
			((Ant) obj.get(k)).getSpeed(acceleration);
		}
	}

	//Getter method for lives remaining
	private int getLivesRemaining() {
		return livesRemaining;
	}

	//Subract the lives remaining value by 1
	private void setLivesRemaining() {
		this.livesRemaining--;
	}

	//Getter method for tick that calculates seconds
	private int getTick() {
		return tick;
	}

	//Adds tick each time the commands is entered
	private void setTick() {
		this.tick = tick+1;
	}

}

/**
 * Main Class that sets the game object
 */
public class Starter {

	private Form current;
	private Resources theme;

	public void init(Object context) {
		// use two network threads instead of one
		updateNetworkThreadCount(2);

		theme = UIManager.initFirstTheme("/theme");

		// Enable Toolbar on all Forms by default
		Toolbar.setGlobalToolbar(true);

		// Pro only feature, uncomment if you have a pro subscription
		Log.bindCrashProtection(true);

		addNetworkErrorListener(err -> {
			// prevent the event from propagating
			err.consume();
			if (err.getError() != null) {
				Log.e(err.getError());
			}
			Log.sendLogAsync();
			Dialog.show("Connection Error",
					"There was a networking error in the connection to " + err.getConnectionRequest().getUrl(), "OK",
					null);
		});
	}

	public void start() {
		if (current != null) {
			current.show();
			return;
		}
		//Make new object of Game
		new Game();
	}

	public void stop() {
		current = getCurrentForm();
		if (current instanceof Dialog) {
			((Dialog) current).dispose();
			current = getCurrentForm();
		}
	}

	public void destroy() {

	}
}

/**
 * Abstract class GameObjects for Fixed Class and Movable Class
 **/
abstract class GameObjects {
	private int size;
	private int color;
	private Point location;

	//Contructor of GameObjects
	public GameObjects(int size, int color, Point location) {
		this.size=size;
		this.color=color;
		this.location=location;
	}
	
	//getter method for location
	public Point getLocation() {
		return location;
	}
	
	//Setter method for color
	public void setColor(int color) {
		this.color = color;
	}
	
	//setter method for location
	public void setLocation(Point location) {
		this.location = location;
	}

	//getter method color
	public int getColor() {
		return color;
	}

	//getter method of size
	public int getSize() {
		return size;
	}

	//setter method of size 
	public void setSize(int size) {
		this.size = size;
	}
	
	//To string method 
	public String toString() {
		return "Location:" + getLocation().getX() + ", " + getLocation().getY();
	}

}

/**
 * Abstract Fixed class that extends GameObjects
 **/
abstract class Fixed extends GameObjects {
	//Constructor for fixed with extended field from GameObjects
	public Fixed(int size, int color, Point location) {
		super(size, color, location);

	}

}

/**
 * Flag Class that extends the fixed class
 **/
class Flags extends Fixed {
	private int sequenceNumber;

	//Constructor method for specific flag fields and extended field from GameObjects
	public Flags(int size, int color, Point location, int sequenceNumber) {
		super(size, color, location);
		this.sequenceNumber = sequenceNumber;

	}

	//Getter method for sequence number
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	
	//Empty body setter method of color, so it does not override the original set value for flags
	public void setColor(int color){}
	
	//Returns the location same as the original set location of flags
	public Point getLocation() {

		return super.getLocation();
	}
	
	//To String  method that display all flag objects with its stats
	public String toString() {
		return "Flag: loc=" + Math.round(getLocation().getX()*10.0)/10.0 + ", " + Math.round(getLocation().getY()*10.0)/10.0 + " color= [" + ColorUtil.red(getColor()) + ", " + ColorUtil.green(getColor()) + ", " + ColorUtil.blue(getColor()) + "] " + "size = " + getSize() + " SeqNum= " + getSequenceNumber();
		
	}
}

/**
 * FoodStations Class extends the fixed abstract class
 **/
class FoodStations extends Fixed {
	private int capacity;

	//Constructor of food station with certain partent fields and capacity for its food capacity
	public FoodStations(int size, int color, Point location, int capacity) {
		super(size, color, location);
		this.capacity = capacity;
	}

	//Setter method for capacity
	public void setCapacity(int finalCapacity) {
		capacity= finalCapacity;
		
	}

	//getter method for capacity
	public int getCapacity() {
		return capacity;
	}
	
	//To string method that prints all stats of Food Station objects
	public String toString() {
		return "FoodStation: loc=" + Math.round(getLocation().getX()*10.0)/10.0 + ", " + Math.round(getLocation().getY()*10.0)/10.0 + " color= [" + ColorUtil.red(getColor()) + ", " + ColorUtil.green(getColor()) + ", " + ColorUtil.blue(getColor()) + "] " + "size = " + getSize() + " Capacity= " + getCapacity();
		
	}
}

/**
 * Abstract Movable Class that extends GameObjects
 **/
abstract class Moveable extends GameObjects {
	private int heading;
	private int speed;

	//Moveable Constructor with added fields of moveable objects 
	public Moveable(int size, int color, Point location, int heading, int speed) {
		super(size, color, location);
		this.heading = heading;
		this.speed = speed;
	}

	//Move class that changes the value of location depending on speed and heading of Spider objects
	public void move(int value) {
		double theta = (Math.toRadians(90 - value));
		Point newLocation = new Point(((float) Math.cos(theta) * getSpeed()) + getLocation().getX(), ((float) Math.sin(theta) * getSpeed()) + getLocation().getY());
		setLocation(newLocation);
		
	}
	

	//Getter method for heading
	public int getHeading() {
		return heading;
	}
	
	//Setter method for heading
	public void setHeading(int heading) {
		this.heading = heading;
	}

	
	//Getter method for speed
	public int getSpeed() {
		return speed;
	}
	
	//Setter for decreasing speed during braking
	public void setSpeedDec() {
		speed-=5;
	}
	
	
	//Setter method for setting speed to 0 when food level is 0 or ant health is 0
	public void setSpeed(int speed) {
		speed=0;
	}
	
	//Accelerates the speed
	public void getSpeed(int acceleration) {
		speed=speed + 5;
		
	}

	//Move method for ant object and determining new location
	public void move() {
		double theta = (Math.toRadians(90 - heading));
		Point newLocation = new Point(((float) Math.cos(theta) * getSpeed()) + getLocation().getX(), ((float) Math.sin(theta) * getSpeed()) + getLocation().getY());
		setLocation(newLocation);
	}
}

/**
 * Ant class that extends the Moveable abstract class and implements iSteerable
 */
class Ant extends Moveable implements iSteerable {
	//Max Speed limit of Ant
	private int maximumSpeed;
	//How Hungry the Ant is 
	private int foodLevel;
	//Amount of food ant needs to consume with each clock ticks
	private int foodConsumption;
	//Level of the Ant
	private int healthLevel;
	//Indicates Sequence number of last flag that ant has reached in increasing order
	private int lastFlagReached;
	
	//Constructor method for ant 
	public Ant(int size, int color, Point location, int heading, int speed,int maximumSpeed, int foodLevel, int foodConsumption,int healthLevel,int lastFlagReached) {
		super(size, color, location, heading, speed);
		this.maximumSpeed=maximumSpeed;
		this.foodLevel=foodLevel;
		this.foodConsumption=foodConsumption;
		this.healthLevel=healthLevel;
		this.lastFlagReached=lastFlagReached;
		
	}
	
	//Set foodlevel after ant collides with foodstation
	public void setFoodLevel(int totalFood) {
		foodLevel=foodLevel+totalFood;
		
	}

	//Getter method for maximum speed
	public int getMaximumSpeed() {
		return maximumSpeed;
	}
	
	//Getter method for Food level of Ant
	public int getFoodLevel() {
		return foodLevel;
	}
	
	//Setter method of food level that decreases speed every second
	public void setFoodLevel() {
		foodLevel = foodLevel-foodConsumption;
	}
	
	//Getter method for food Consumption
	public int getFoodConsumption() {
		return foodConsumption;
	}

	//Getter method of health of ant
	public int getHealthLevel() {
		return healthLevel;
	}
	
	//Setter method for decreasing 1 from health of ant with spider
	public void setHealthLevel() {
		this.healthLevel = healthLevel-1;
	}
	
	//Getter method of lastFlagReached
	public int getLastFlagReached() {
		return lastFlagReached;
	}
	
	//Setter method for last flag Reached
	public void setLastFlagReached() {
		this.lastFlagReached = lastFlagReached+1;
	}
	
	//Direction left part of interface that moves 5 degrees to the left
	public void directionLeft() {
		super.setHeading((int) ((getHeading()-5)));	
	}
	
	//Direction right part of interface that move 5 degree to the right
	public void directionRight() {
		super.setHeading((int) ((getHeading()+5)));
	}
	
	//To String method that display all stats of ant object
	public String toString() {
		return "Ant: loc=" + Math.round(getLocation().getX()*10.0)/10.0 + ", " + Math.round(getLocation().getY()*10.0)/10.0 + " color= [" + ColorUtil.red(getColor()) + ", " + ColorUtil.green(getColor()) + ", " + ColorUtil.blue(getColor()) + "] " + "size = " + getSize() + " heading= " + getHeading() + " speed= " + getSpeed() + " maxSpeed= " + getMaximumSpeed() + " foodConsumptionRate= " + getFoodConsumption();	
	}
}


/**
 * Spider Class Extends the Moveable
**/
class Spider extends Moveable  {
	//Constructor method of Spider
	public Spider(int size, int color, Point location, int heading, int speed) {
		super(size, color, location, heading, speed);
		
	}
	
	//Empty setter method of color
	public void setColor(int color){}
	
	//To String method for printing stats of Spider Object
	public String toString() {
		return "Spider: loc= " + Math.round(getLocation().getX()*10.0)/10.0 + ", " + Math.round(getLocation().getY()*10.0)/10.0 + " color= [" + ColorUtil.red(getColor()) + ", " + ColorUtil.green(getColor()) + ", " + ColorUtil.blue(getColor()) + "] " + "size = " + getSize() + " heading= " + getHeading() + " speed= " + getSpeed();	
	}
}

/**
 * Interface class iSteerable
 **/
interface iSteerable {
	//Direction left of the ant
	public void directionLeft();
	//Direction right of the ant
	public void directionRight();
}


