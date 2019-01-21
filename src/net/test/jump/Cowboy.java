package net.test.jump;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class Cowboy extends Sprite{

  static int WIDTH = 32;
  static int HEIGHT = 48;
  static int[] FRAME_SEQUENCE = {3, 2, 1, 2};
  int myInitialX;
  int myInitialY;
  int myNoJumpInt = -6;
  int myIsJumping = myNoJumpInt;
  int myScoreThisJump = 0;

  public Cowboy(int initialX, int initialY) throws Exception{

    super(Image.createImage("/icons/cowboy.png"), WIDTH, HEIGHT);

    myInitialX = initialX;
    myInitialY = initialY;

    defineReferencePixel(WIDTH /2, 0);
    setRefPixelPosition(myInitialX, myInitialY);
    setFrameSequence(FRAME_SEQUENCE);
  };

  int checkCollision(Tumbleweed tumbleweed){

    int retVal = 0;

    if(collidesWith(tumbleweed, true)){
      retVal = 1;
      tumbleweed.reset();
     };

   return(retVal);
 };

  void reset(){

   myIsJumping = myNoJumpInt;
   setRefPixelPosition(myInitialX, myInitialY);
   setFrameSequence(FRAME_SEQUENCE);
   myScoreThisJump = 0;

   setTransform(TRANS_NONE);
  };

  void advance(int tickCount, boolean left){

    if(left){
      setTransform(TRANS_MIRROR);
      move(-1, 0);
     } else{
         setTransform(TRANS_NONE);
         move(1, 0);
        };

    if(tickCount % 3 == 0){
      if(myIsJumping == myNoJumpInt){
  	    nextFrame();
       } else{

	        myIsJumping++;
	        if(myIsJumping < 0){

	          setRefPixelPosition(getRefPixelX(), getRefPixelY() -(2 << -myIsJumping));
	         } else{

	            if(myIsJumping != -myNoJumpInt - 1) {
	              setRefPixelPosition(getRefPixelX(), getRefPixelY() +(2 << myIsJumping));
	             } else{

                   myIsJumping = myNoJumpInt;
	                 setRefPixelPosition(getRefPixelX(), myInitialY);

                   setFrameSequence(FRAME_SEQUENCE);

                   myScoreThisJump = 0;
	                };
	           };
         };
    };
  };

  void jump(){

    if(myIsJumping == myNoJumpInt){

      myIsJumping++;

      setFrameSequence(null);
      setFrame(0);
     };
  };

  int increaseScoreThisJump(){

    if(myScoreThisJump == 0){
      myScoreThisJump++;
     } else{
         myScoreThisJump *= 2;
        };

    return(myScoreThisJump);
  };
};
