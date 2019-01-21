package net.test.jump;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class Jump extends MIDlet implements CommandListener{

  private Command myExitCommand = new Command("Exit", Command.EXIT, 99);
  private Command myGoCommand = new Command("Go", Command.SCREEN, 1);
  private Command myPauseCommand = new Command("Pause", Command.SCREEN, 1);
  private Command myNewCommand = new Command("Play Again", Command.SCREEN, 1);
  JumpCanvas myCanvas;
  GameThread myGameThread;

  public Jump(){

   myCanvas = new JumpCanvas(this);

   myCanvas.addCommand(myExitCommand);
   myCanvas.addCommand(myGoCommand);
   myCanvas.setCommandListener(this);
  };

  void setNewCommand(){
   myCanvas.removeCommand(myPauseCommand);
   myCanvas.removeCommand(myGoCommand);
   myCanvas.addCommand(myNewCommand);
  };

  void setGoCommand(){
   myCanvas.removeCommand(myPauseCommand);
   myCanvas.removeCommand(myNewCommand);
   myCanvas.addCommand(myGoCommand);
  };

  void setPauseCommand(){
   myCanvas.removeCommand(myNewCommand);
   myCanvas.removeCommand(myGoCommand);
   myCanvas.addCommand(myPauseCommand);
  };

  public void startApp() throws MIDletStateChangeException{
   myGameThread = new GameThread(myCanvas);
   myCanvas.start();
  };

  public void destroyApp(boolean unconditional) throws MIDletStateChangeException{

   myGameThread.requestStop();

   myGameThread = null;
   myCanvas = null;

   System.gc();
  };

  public void pauseApp(){
   setGoCommand();
   myGameThread.pause();
  };

  public void commandAction(Command c, Displayable s){

    if(c == myGoCommand){
      myCanvas.removeCommand(myGoCommand);
      myCanvas.addCommand(myPauseCommand);
      myGameThread.go();
     } else if(c == myPauseCommand) {
         myCanvas.removeCommand(myPauseCommand);
         myCanvas.addCommand(myGoCommand);
         myGameThread.go();
        } else if(c == myNewCommand){

            myCanvas.removeCommand(myNewCommand);
            myCanvas.addCommand(myGoCommand);

            myGameThread.requestStop();
            myGameThread = new GameThread(myCanvas);

            System.gc();
            myCanvas.reset();
           } else if(c == myExitCommand){
              try {
               destroyApp(false);
	             notifyDestroyed();
              } catch (MIDletStateChangeException ex){};
             };
  };
};
