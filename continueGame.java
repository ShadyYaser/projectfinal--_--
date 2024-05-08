package shady_main;

import java.util.Scanner;

public class continueGame implements Runnable{
	
	Scanner scanner = new Scanner(System.in);
	Thread t;
	MainPlayer joel;
	
	Location currentLocation;
	
	MedkitItem medkit;
	FlashlightItem flashlight;
	
	private static continueGame instance;
	
	private continueGame() {
		joel = MainPlayer.getInstance();
		medkit = new MedkitItem("Medkit", joel);
		flashlight = new FlashlightItem("Flashlight", joel);
		currentLocation = new AbandonedHouse(joel);
		t = new Thread(this);
		t.start();
	}
	
	public static synchronized continueGame getInstance() {
		if(instance == null) 
			instance = new continueGame();
		return instance;	
	}
	
	public Thread getThread() {
		return t;
	}
	
	private String readUserInput() {
	    return scanner.nextLine();
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(10*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UI.printNormal("");
		
		currentLocation.description();
		
		UI.printNormal("\n" + "Enter look around to search the house: ");
		
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		
		String input = "dummy";
		try {
			input = this.readUserInput();
			Thread.sleep(5*1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(input.equalsIgnoreCase("look around")) {
			currentLocation.look();
			UI.printNormal("");
			input = "dummy";
		}

		flashlight.makeItem();
		UI.printNormal("");
		medkit.makeItem();
		UI.printNormal("");
		
		try {
			Thread.sleep(5000);//simulate 5 seconds for infected to show up
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		UI.printNormal("Joel sees in an infected creature in the house.\n"
				+ "He pulls out his gun and shoots him in the head.\n"
				+ "Joel and Ellie continue to search for rooms to get some sleep in.");

		try {
			Thread.sleep(5000); //simulate 5 secs for swarm of infected to come due to gunshot sound
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		UI.printNormal("A swarm of infected came heard the gunshots and entered the house.\n"
				+ "They are heading towards Ellie.\n"
				+ "Choose what to do; act quickly!");

		joel.ellieNeedsHelp = true;
		UI.printNormal("");
		joel.talk();

		UI.printNormal("1. Attack infected\n"
				+ "2. Evade infected");
		UI.printNormal("");
		
		try {
			input = this.readUserInput();
			
			Thread.sleep(5*1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int no = Integer.parseInt(input);
		if(no == 1) {
			joel.setStrategy(new AttackStrategy(joel)); //set strategy for joel, attack by default
			joel.combatStrategy(); //execute strategy

		}
		else if(no == 2) {
			joel.setStrategy(new EvadeStrategy(joel));
			joel.combatStrategy();

		}

		if(joel.getHealth() < 100) { //if joel takes damage
			try {
				Thread.sleep(5*1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				joel.useItem("Medkit");// he heals
		}
		
		UI.printNormal("");
		UI.printNormal("Joel and Ellie escape the house safely. They find an abandoned horse and use it to get \n"
				+ "to St. Mary Hospital.");

		currentLocation.next();//changing state to Scene 3: Hospital
		joel.setLocation(currentLocation); //setting joel to next location
	}

}
