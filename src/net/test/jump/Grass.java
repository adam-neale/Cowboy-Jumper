package net.test.jump;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class Grass extends TiledLayer{

  static int TILE_WIDTH = 20;
  static int[] FRAME_SEQUENCE = {2, 3, 2, 4};
  static int COLUMNS;
  static int CYCLE = 5;
  static int TOP_Y;
  int mySequenceIndex = 0;
  int myAnimatedTileIndex;

  static int setColumns(int screenWidth){
   COLUMNS = (screenWidth /20 +1) *3;
   return(COLUMNS);
  };

  public Grass() throws Exception{

   super(setColumns(JumpCanvas.DISP_WIDTH), 1, Image.createImage("/icons/grass.png"), TILE_WIDTH, TILE_WIDTH);

   TOP_Y = JumpManager.DISP_HEIGHT -TILE_WIDTH;
   setPosition(0, TOP_Y);
   myAnimatedTileIndex = createAnimatedTile(2);

   for(int i = 0; i < COLUMNS; i++){

    if(i %CYCLE == 0 || i %CYCLE == 2){
	    setCell(i, 0, myAnimatedTileIndex);
     } else {
	       setCell(i, 0, 1);
        };
    };
  };

  void reset(){

   setPosition(-TILE_WIDTH *CYCLE, TOP_Y);
   mySequenceIndex = 0;
   setAnimatedTile(myAnimatedTileIndex, FRAME_SEQUENCE[mySequenceIndex]);
  };

  void advance(int tickCount){

   if(tickCount %2 == 0) {
     mySequenceIndex++;
     mySequenceIndex %= 4;
     setAnimatedTile(myAnimatedTileIndex, FRAME_SEQUENCE[mySequenceIndex]);
    };
  };
};
