package net.test.jump;

public class GameThread extends Thread{

  boolean myShouldPause;
  static boolean myShouldStop;
  boolean myAlreadyStarted;
  JumpCanvas myJumpCanvas;

  GameThread(JumpCanvas canvas){
    myJumpCanvas = canvas;
  };

  void go(){

   if(!myAlreadyStarted){
     myAlreadyStarted = true;
     start();
    } else{
        myShouldPause = !myShouldPause;
       };
  };

  void pause(){
   myShouldPause = true;
  };

  static void requestStop(){
   myShouldStop = true;
  };

  public void run(){

   myJumpCanvas.flushKeys();
   myShouldStop = false;
   myShouldPause = false;

   while(true){

    if(myShouldStop) break;

    if(!myShouldPause){
    	myJumpCanvas.checkKeys();
	    myJumpCanvas.advance();
     };
    };
  };
};
