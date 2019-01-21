package net.test.jump;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class JumpCanvas extends javax.microedition.lcdui.game.GameCanvas{

  static int GROUND_HEIGHT = 32;
  static int CORNER_X;
  static int CORNER_Y;
  static int DISP_WIDTH;
  static int DISP_HEIGHT;
  static int FONT_HEIGHT;
  static Font FONT;
  static int SCORE_WIDTH;
  static int TIME_WIDTH;
  Display myDisplay;
  Jump myJump;
  JumpManager myManager;
  static boolean myGameOver;
  int myScore = 0;
  int myInitialGameTicks = 950;
  int myOldGameTicks = myInitialGameTicks;
  int myGameTicks = myOldGameTicks;
  boolean myInitialized;
  static String myInitialString = "1:00";
  String myTimeString = myInitialString;

  static void setGameOver(){
    myGameOver = true;
    GameThread.requestStop();
  };

  static boolean getGameOver(){
    return(myGameOver);
  };

  public JumpCanvas(Jump midlet){

    super(false);

    myDisplay = Display.getDisplay(midlet);
    myJump = midlet;
  };

  void start(){

    myGameOver = false;

    myDisplay.setCurrent(this);
    repaint();
  };

  void reset(){

    myManager.reset();

    myScore = 0;
    myGameOver = false;
    myGameTicks = myInitialGameTicks;
    myOldGameTicks = myInitialGameTicks;

    repaint();
  };

  void flushKeys(){
    getKeyStates();
  };

  public void paint(Graphics g){

    if(!myInitialized){

      CORNER_X = g.getClipX();
      CORNER_Y = g.getClipY();

      DISP_WIDTH = g.getClipWidth();
      DISP_HEIGHT = g.getClipHeight();

      FONT = g.getFont();
      FONT_HEIGHT = FONT.getHeight();

      SCORE_WIDTH = FONT.stringWidth("Score: 000");
      TIME_WIDTH = FONT.stringWidth("Time: " + myInitialString);

      myInitialized = true;
    };

    g.setColor(0xffffff);
    g.fillRect(CORNER_X, CORNER_Y, DISP_WIDTH, DISP_HEIGHT);
    g.setColor(0x0000ff00);
    g.fillRect(CORNER_X, CORNER_Y +DISP_HEIGHT -GROUND_HEIGHT, DISP_WIDTH, DISP_HEIGHT);

    try{

      if(myManager == null){
	      myManager = new JumpManager(CORNER_X, CORNER_Y +FONT_HEIGHT *2, DISP_WIDTH, DISP_HEIGHT -FONT_HEIGHT *2 -GROUND_HEIGHT);
       };

      myManager.paint(g);
     } catch(Exception e){
        errorMsg(g, e);
       };

    g.setColor(0);
    g.setFont(FONT);
    g.drawString("Score: " +myScore, (DISP_WIDTH -SCORE_WIDTH) /2, DISP_HEIGHT +5 -GROUND_HEIGHT, g.TOP |g.LEFT);
    g.drawString("Time: " +formatTime(), (DISP_WIDTH -TIME_WIDTH) /2, CORNER_Y +FONT_HEIGHT, g.TOP |g.LEFT);

    if(myGameOver){

      myJump.setNewCommand();

      g.setColor(0xffffff);
      g.fillRect(CORNER_X, CORNER_Y, DISP_WIDTH, FONT_HEIGHT *2 + 1);

      int goWidth = FONT.stringWidth("Game Over");

      g.setColor(0);
      g.setFont(FONT);
      g.drawString("Game Over", (DISP_WIDTH -goWidth) /2, CORNER_Y +FONT_HEIGHT, g.TOP |g.LEFT);
    };
  };

  public String formatTime(){

    if(myGameTicks /16 +1 != myOldGameTicks){

      myTimeString = "";
      myOldGameTicks = (myGameTicks /16) +1;

      int smallPart = myOldGameTicks %60;
      int bigPart = myOldGameTicks /60;

      myTimeString += bigPart + ":";

      if(smallPart /10 < 1) myTimeString += "0";

      myTimeString += smallPart;
    };

    return(myTimeString);
  };

  void advance(){

    myGameTicks--;
    myScore += myManager.advance(myGameTicks);

    if(myGameTicks == 0) setGameOver();

    try{
      paint(getGraphics());
      flushGraphics();
     } catch(Exception e){errorMsg(e)};

    synchronized(this){
      try{
	      wait(1);
       } catch(Exception e){};
    };
  };

  public void checkKeys(){

    if(!myGameOver){

      int keyState = getKeyStates();

      if((keyState & LEFT_PRESSED) != 0) myManager.setLeft(true);
      if((keyState & RIGHT_PRESSED) != 0) myManager.setLeft(false);
      if((keyState & UP_PRESSED) != 0) myManager.jump();
    };
  };

  void errorMsg(Exception e){
    errorMsg(getGraphics(), e);
    flushGraphics();
  };

  void errorMsg(Graphics g, Exception e){

    if(e.getMessage() == null) {
      errorMsg(g, e.getClass().getName());
     } else {
         errorMsg(g, e.getClass().getName() +":" +e.getMessage());
        };
  };

  void errorMsg(Graphics g, String msg){

    g.setColor(0xffffff);
    g.fillRect(CORNER_X, CORNER_Y, DISP_WIDTH, DISP_HEIGHT);

    int msgWidth = FONT.stringWidth(msg);

    g.setColor(0x00ff0000);
    g.setFont(FONT);
    g.drawString(msg, (DISP_WIDTH -msgWidth) /2, (DISP_HEIGHT -FONT_HEIGHT) /2, g.TOP |g.LEFT);

    myGameOver = true;
  };
};
