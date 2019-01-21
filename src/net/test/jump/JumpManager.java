package net.test.jump;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class JumpManager extends javax.microedition.lcdui.game.LayerManager{

  static int CANVAS_X;
  static int CANVAS_Y;
  static int DISP_WIDTH;
  static int DISP_HEIGHT;
  Cowboy myCowboy;
  Tumbleweed[] myLeftTumbleweeds;
  Tumbleweed[] myRightTumbleweeds;
  Grass myGrass;
  boolean myLeft;
  int myCurrentLeftX;

  void setLeft(boolean left){
   myLeft = left;
  };

  public JumpManager(int x, int y, int width, int height){

    CANVAS_X = x;
    CANVAS_Y = y;

    DISP_WIDTH = width;
    DISP_HEIGHT = height;

    myCurrentLeftX = Grass.CYCLE *Grass.TILE_WIDTH;

    setViewWindow(0, 0, DISP_WIDTH, DISP_HEIGHT);
  };

  void reset(){

    if(myGrass != null){
      myGrass.reset();
     };

    if(myCowboy != null){
      myCowboy.reset();
     };

    if(myLeftTumbleweeds != null) {
      for(int i = 0; i < myLeftTumbleweeds.length; i++) {
	     myLeftTumbleweeds[i].reset();
      };
     };

    if(myRightTumbleweeds != null) {
      for(int i = 0; i < myRightTumbleweeds.length; i++) {
      	myRightTumbleweeds[i].reset();
      };
     };

    myLeft = false;
    myCurrentLeftX = Grass.CYCLE *Grass.TILE_WIDTH;
  };

  public void paint(Graphics g) throws Exception {

    if(myCowboy == null){
      myCowboy = new Cowboy(myCurrentLeftX +DISP_WIDTH /2, DISP_HEIGHT -Cowboy.HEIGHT -2);
      append(myCowboy);
     };

    if(myLeftTumbleweeds == null){

      myLeftTumbleweeds = new Tumbleweed[2];

      for(int i = 0; i < myLeftTumbleweeds.length; i++) {
    	 myLeftTumbleweeds[i] = new Tumbleweed(true);
	     append(myLeftTumbleweeds[i]);
      };
     };

    if(myRightTumbleweeds == null){

      myRightTumbleweeds = new Tumbleweed[2];

      for(int i = 0; i < myRightTumbleweeds.length; i++) {
       myRightTumbleweeds[i] = new Tumbleweed(false);
	     append(myRightTumbleweeds[i]);
      };
     };

    if(myGrass == null){
      myGrass = new Grass();
      append(myGrass);
    };

    setViewWindow(myCurrentLeftX, 0, DISP_WIDTH, DISP_HEIGHT);
    paint(g, CANVAS_X, CANVAS_Y);
  };

  void wrap(){

    if(myCurrentLeftX %(Grass.TILE_WIDTH *Grass.CYCLE) == 0){
      if(myLeft){
	      myCowboy.move(Grass.TILE_WIDTH *Grass.CYCLE, 0);
      	myCurrentLeftX += (Grass.TILE_WIDTH *Grass.CYCLE);

	      for(int i = 0; i < myLeftTumbleweeds.length; i++) {
	       myLeftTumbleweeds[i].move(Grass.TILE_WIDTH *Grass.CYCLE, 0);
	      };

    	 for(int i = 0; i < myRightTumbleweeds.length; i++) {
	      myRightTumbleweeds[i].move(Grass.TILE_WIDTH *Grass.CYCLE, 0);
	     };
      } else{

	       myCowboy.move(-Grass.TILE_WIDTH *Grass.CYCLE, 0);
	       myCurrentLeftX -= (Grass.TILE_WIDTH *Grass.CYCLE);

	       for(int i = 0; i < myLeftTumbleweeds.length; i++) {
	        myLeftTumbleweeds[i].move(-Grass.TILE_WIDTH *Grass.CYCLE, 0);
	       };

	       for(int i = 0; i < myRightTumbleweeds.length; i++) {
	        myRightTumbleweeds[i].move(-Grass.TILE_WIDTH *Grass.CYCLE, 0);
	       };
        };
    };
  };

  int advance(int gameTicks){

    int retVal = 0;

    if(myLeft){
      myCurrentLeftX--;
     } else {
         myCurrentLeftX++;
        };

    myGrass.advance(gameTicks);
    myCowboy.advance(gameTicks, myLeft);

    for(int i = 0; i < myLeftTumbleweeds.length; i++){
      retVal += myLeftTumbleweeds[i].advance(myCowboy, gameTicks, myLeft, myCurrentLeftX, myCurrentLeftX +DISP_WIDTH);
      retVal -= myCowboy.checkCollision(myLeftTumbleweeds[i]);
    };

    for(int i = 0; i < myLeftTumbleweeds.length; i++){
      retVal += myRightTumbleweeds[i].advance(myCowboy, gameTicks, myLeft, myCurrentLeftX, myCurrentLeftX +DISP_WIDTH);
      retVal -= myCowboy.checkCollision(myRightTumbleweeds[i]);
    };

    wrap();
    return(retVal);
  };

  void jump(){
   myCowboy.jump();
  };
};
