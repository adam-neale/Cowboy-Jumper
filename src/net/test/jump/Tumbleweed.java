package net.test.jump;

import java.util.Random;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class Tumbleweed extends Sprite {

  static int WIDTH = 16;
  Random myRandom = new Random();
  boolean myJumpedOver;
  boolean myLeft;
  int myY;

  public Tumbleweed(boolean left) throws Exception{

    super(Image.createImage("/icons/tumbleweed.png"), WIDTH, WIDTH);

    myY = JumpManager.DISP_HEIGHT -WIDTH -2;
    myLeft = left;

    if(!myLeft){
      setTransform(TRANS_MIRROR);
     };

    myJumpedOver = false;
    setVisible(false);
  };

  void reset(){
    setVisible(false);
    myJumpedOver = false;
  };

  int advance(Cowboy cowboy, int tickCount, boolean left, int currentLeftBound, int currentRightBound){

    int retVal = 0;

    if((getRefPixelX() + WIDTH <= currentLeftBound) || (getRefPixelX() -WIDTH >= currentRightBound)){
      setVisible(false);
     };

    if(!isVisible()){
      int rand = getRandomInt(100);
      if(rand == 3) {

	  myJumpedOver = false;
	  setVisible(true);

	  if(myLeft){
	    setRefPixelPosition(currentRightBound, myY);
	    move(-1, 0);
	   } else{
	      setRefPixelPosition(currentLeftBound, myY);
	      move(1, 0);
	     };
      };
    } else{

      if(tickCount %2 == 0){
	      nextFrame();
       };

      if(myLeft){

	     move(-3, 0);

	     if((!myJumpedOver) && (getRefPixelX() < cowboy.getRefPixelX())){
	       myJumpedOver = true;
	       retVal = cowboy.increaseScoreThisJump();
	      };
       } else{

    	     move(3, 0);

	         if((!myJumpedOver) && (getRefPixelX() > cowboy.getRefPixelX() +Cowboy.WIDTH)) {
	           myJumpedOver = true;
	           retVal = cowboy.increaseScoreThisJump();
	          };
          };
    };

    return(retVal);
  };

  public int getRandomInt(int upper){

    int retVal = myRandom.nextInt() %upper;

    if(retVal < 0){
      retVal += upper;
     };

    return(retVal);
  };
};
