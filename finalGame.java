package shady_main;

import java.util.Scanner;

public class finalGame implements Runnable{
	
	Thread t;
	
	MainPlayer joel;
	
	Location currentLocation = new Hospital(joel);
	
	ThreatenCommand threatencmd = new ThreatenCommand(joel);
	InteractCommand interactcmd = new InteractCommand(joel);
	Command[] cmds = {interactcmd, threatencmd};
	ControlPanel cmdsPanel = new ControlPanel(cmds);
	
	private static finalGame instance;

	private finalGame() {
		t = new Thread(this);
		t.start();
	}

	public static synchronized finalGame getInstance() {
		if(instance == null) 
			instance = new finalGame();
		return instance;	
	}

	public Thread getThread() {
		return t;
	}
	
	@Override
	public void run() {
		
		joel = MainPlayer.getInstance();
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		try {
			Thread.sleep(10*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		currentLocation.description();
		UI.printNormal("You and Ellie go in to search for the doctors.\n");
	
		
		UI.printNormal("Speak into the phone to call out the doctors.\n");
		
		try {
			Thread.sleep(6*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(joel.DoctorsFound) {
			joel.talk();

			UI.printNormal("Choose what to do next (type first or second): ");
			cmdsPanel.printCommands();

			String chooseinput = null;
			try {
				//chooseinput = reader.readLine();
				//chooseinput = reader.nextLine();
				Thread.sleep(5*1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//if(chooseinput.equalsIgnoreCase("first")) {
				cmdsPanel.buttonWasPressed(0);
			//}
			/*else if(chooseinput.equalsIgnoreCase("second")){
				cmdsPanel.buttonWasPressed(1);
			}
			else {
				UI.printNormal("No such choice exists!.");
			}*/

			try {
				Thread.sleep(10*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			joel.finishedGame = true;
			UI.printNormal("Doctor made a cure in 2 hours!\n"
					+ "THE END\n");
			UI.printNormal("");
		}
	}

}
