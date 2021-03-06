	
	package Robot;
	/* 
	 * Automatically generated code
	 * eindopdracht DES
	 * Judith van Stegeren and Mirjam van Nahmen
	 * 
	 */

	/* 
	 * assumptions:
	 * this week only one brick, no bluetooth
	*/

	//sensors
	import lejos.nxt.Button;
	import lejos.nxt.LightSensor;
	import lejos.nxt.SensorPort;
	import lejos.nxt.UltrasonicSensor;
	import lejos.nxt.TouchSensor;
	import lejos.nxt.ColorSensor;
	import lejos.nxt.TemperatureSensor;

	//actuators
	import lejos.nxt.Motor;
	import lejos.nxt.NXTRegulatedMotor;
	import lejos.nxt.MotorPort;
	import lejos.nxt.LCD;
	import lejos.nxt.Sound;
	import lejos.nxt.addon.RCXMotor;

	//for bluetooth:
	import java.io.DataInputStream;
	import java.io.DataOutputStream;
	import lejos.nxt.comm.BTConnection;
	import lejos.nxt.comm.Bluetooth;
	import javax.bluetooth.RemoteDevice;
	import java.io.IOException;
	import java.util.ListIterator;

	//misc
	import java.util.Random;
	import lejos.util.Delay;

	
	public class Main{
	
	//Uncalibrated constants for the Lightsensorvalues
	public static int BRIGHT = 40;
	public static int DARK = 30;
	
	//public variables 
	public static State current;
	private static ListIterator<Integer> it;
	
	//maak een enum van de beginstates
		public enum State {
		S1,
		S2,
		S3,
		S4
		}
		
	//definieer lijst van endstates
	static State[] endStates = {State.S4};
	
	//specificeer alle equipment
	//standaard equipment op Robot Slave
	private static RCXMotor tempArm;
	private static NXTRegulatedMotor lamp;
	private static ColorSensor colorsens;
	private static UltrasonicSensor sonic;
	private static TemperatureSensor temp;
	
	//bluetooth draadje
	private static BTfunctionality btThread;
	
		
	public static void main(String[] args){
		//initialiseer alle equipment
		tempArm = new RCXMotor(MotorPort.A);
		lamp = Motor.C;
		colorsens = new ColorSensor(SensorPort.S1);
		sonic = new UltrasonicSensor(SensorPort.S2);
		temp = new TemperatureSensor(SensorPort.S3);
		
		//zet de robot in de beginstate
		current = State.S1;
		
		//opstart-info
		LCD.drawString("EndGameRobot",0,1);
		LCD.drawString("Judith & Mirjam",0,2);
		Button.waitForAnyPress();

		//start de loop of doom
		while(!inEndState())
		{
			execute(current);
		}
		execute(current); //execute Endstate
	}

	
	//make methods for every state seperately
	public static void S1()
	{
		//execute all actions of this state
		LCD.drawString("S1",0,3);
		LCD.drawString("Waiting...",0,0);
		LCD.refresh();
		
		BTConnection btc = Bluetooth.waitForConnection();
		
		LCD.clear();
		LCD.drawString("Connected",0,0);
		LCD.refresh();	
		
		DataInputStream dis = btc.openDataInputStream();
		DataOutputStream dos = btc.openDataOutputStream();
		
		//BTfunctionality btThread = new BTfunctionality("SlaveReader",dis,dos);
		btThread = new BTfunctionality("SlaveReader",dis,dos);
		btThread.start();
		

		//leg de huidige tijd vast voor alle transitions met een timeoutcondition
		long starttime = System.currentTimeMillis();

		//when done, wait for a trigger for a transition
		boolean transitionTaken = false; 
		while(!transitionTaken){	
			if((true
			)){
				current = State.S2;
				transitionTaken = true;
			}
		}
	}
	
	public static void S2()
	{
		//execute all actions of this state
		LCD.drawString("S2",0,3);
		

		//leg de huidige tijd vast voor alle transitions met een timeoutcondition
		long starttime = System.currentTimeMillis();

		//when done, wait for a trigger for a transition
		boolean transitionTaken = false; 
		while(!transitionTaken){	
			if((lejos.robotics.Colors.Color.RED
			)||(lejos.robotics.Colors.Color.BLUE 
			)||(lejos.robotics.Colors.Color.GREEN
			)){
				current = State.S3;
				transitionTaken = true;
			}else if(
(starttime + 10000 <= System.currentTimeMillis()
			)){
				current = State.S4;
				transitionTaken = true;
			}
		}
	}
	
	public static void S3()
	{
		//execute all actions of this state
		LCD.drawString("S3",0,3);
		Sound.beepSequenceUp();
		btThread.write(400);
		

		//leg de huidige tijd vast voor alle transitions met een timeoutcondition
		long starttime = System.currentTimeMillis();

		//when done, wait for a trigger for a transition
		boolean transitionTaken = false; 
		while(!transitionTaken){	
			if((starttime + 1000 <= System.currentTimeMillis()
			)){
				current = State.S2;
				transitionTaken = true;
			}
		}
	}
	
	public static void S4()
	{
		//execute all actions of this state
		LCD.drawString("S4",0,3);
		Sound.beepSequenceUp();
		Sound.beepSequenceUp();
		
	}

	public static void execute(State s)
	{
		switch(s){
		case S1:
			S1();
			break;
		case S2:
			S2();
			break;
		case S3:
			S3();
			break;
		case S4:
			S4();
			break;
			default:
				break;
		}
	}	

	public static boolean inEndState()
	{
		for (State s : endStates) 
			if (s.equals(current)) 
				return true;
	
		return false;
	}


	
	
	/**
	 * Returns a pseudo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimum value
	 * @param max Maximum value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {
	
	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();
	
	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	
	    return randomNum;
	}
	
	//TODO: sonar stubfunction
	public static int getLastSonarData()
	{
		return 255;
	}
		 
}	
