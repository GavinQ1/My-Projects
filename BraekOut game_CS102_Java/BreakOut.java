import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import sun.audio.*;
import java.util.ArrayList;


/**
 * BreakOut.java 
 *
 * @author Gavin Qi
 * Final Assignment, CMPU102, Spring 2016
 *
 *  This program is a file for the program called
 *  BreakOut in which a game is defined and the user
 *  should try to control paddle to make all bricks knocked
 *  down by the ball.
 *  
 *  A prob of 15% that a brick needs to be hit twice to break down (GRAY BRICKS)
 *  The probability of getting a gift is 15%
     Special effects
             10% -- ball smaller
             30% -- paddle longer
             20% -- paddle shorter
             20% -- ball larger
             19.999% -- ball quicker
             0.001% -- win right away
    
 */
public class BreakOut extends JPanel implements ActionListener {    
    // constant for size and color of ball
    private static final int DIAMETER = 20;
    
    // constant for color of background
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    
    // constant for milliseconds between Timer events
    private static final int MS = 15;
    
    // constant for size of window
    private static final int WINDOW_WIDTH = 680;
    private static final int WINDOW_HEIGHT = 600;
    
    // constants and variable for brick wall
    private static final int BRICK_HEIGHT = 10;
    private static final int BRICK_WIDTH = 45;
    private static final int BRICK_SEP = 10;
    private static final int ROWS = 5;
    private static final int COLS = 12;
    private static final int START_X = 10;
    private static final int START_Y = 75;    
    
    //constants for position and size of paddle
    private static final int PADDLE_HEIGHT = 10;
    private static final int PADDLE_WIDTH = PADDLE_HEIGHT * 10;   
    private static final Color PADDLE_COLOR = Color.BLACK;
    private static final int FOLAT = 80;
    private static final int LOWER_PADDLE_Y = WINDOW_HEIGHT - FOLAT;
    
    // paddle size
    private int paddleWidth = PADDLE_WIDTH;
    
    // ball size
    private int ballSize = DIAMETER;
    
    // instance variables for starting position of ball
    private int xPos = WINDOW_WIDTH/2 - DIAMETER/2; 
    private int yPos = WINDOW_HEIGHT/2 - DIAMETER/2;
    
    // instance variable for x position of paddle
    private int lowerPaddleX = WINDOW_WIDTH/2 - PADDLE_WIDTH/2;
       
    // instance variables for changing position of ball
    private int speed = 2;
    private int xChange = speed, yChange = speed;
    
    // direction of ball
    private boolean flagMovingLeft;
    private boolean flagMovingDown;
    
    // prob of drop
    private double probDrop = 0.15;
    private ArrayList<Gift> giftboxes;
    
    // instance variables for brickwall
    private Color[] brickColors = {Color.RED, Color.ORANGE, Color.YELLOW, 
        Color.GREEN, Color.BLUE};
    private Brick[][] brickWall = new Brick[ROWS][COLS];
    private int[][] bricksCenterX = new int[ROWS][COLS];
    private int[][] bricksCenterY = new int[ROWS][COLS];
    private int numBricks = ROWS * COLS;
    
    // game soundtrack
    private String bouncing;
    private String start;
    private String lost;
    private boolean lostSoundLock;
    
    // game info: score, tries left, prompt msg
    private int score;
    private int tries;
    private String msg;
    private int msgX, msgY;
    
    // game status control
    private boolean gamelock;
    private boolean gameover;
    
    
    public BreakOut(){
        this.setBackground(BACKGROUND_COLOR);
        
        // from http://soundbible.com/tags-star-wars.html
        this.bouncing = "bouncing.wav"; 
        this.start = "start.wav";
        this.lost = "lost.wav";
        
        // initialization
        this.lostSoundLock = true;
        
        this.flagMovingLeft = false;
        this.flagMovingDown = false;
        
        this.giftboxes = new ArrayList<Gift>();
        
        this.gamelock = true;
        this.gameover = false;
        this.score = 0;
        this.tries = 3;
        this.msg = "Click To Start";
        this.msgX = WINDOW_WIDTH / 2 - 80;
        this.msgY = WINDOW_HEIGHT / 2;
        
        this.addMouseListener
            (new MouseListener() {
            public void mousePressed(MouseEvent evt) {}
            public void mouseReleased(MouseEvent evt) {}
            public void mouseClicked(MouseEvent evt) { 
                if (gamelock) gamelock = false;
                if (gameover) {
                    gameover = false;
                    restart();
                }
            }
            public void mouseEntered(MouseEvent evt) {}
            public void mouseExited(MouseEvent evt) {}
        });
        
        this.addMouseMotionListener
            (new MouseMotionListener() {
            public void mouseMoved(MouseEvent me) {
                if (me.getX() <= BreakOut.this.getWidth() - paddleWidth)
                    lowerPaddleX = me.getX();
            }
            public void mouseDragged(MouseEvent me) {}
        });
        
        
        createWall();
        playSound(start);
    }
    
    // CODE FOR CREATING WALL
    private void createWall() {
        for (int i = 0; i < ROWS; i++){
            for (int j = 0; j < COLS; j++){
                brickWall[i][j]= new Brick
                    (START_X + j * (BRICK_SEP + BRICK_WIDTH),
                     START_Y + i * (BRICK_SEP + BRICK_HEIGHT),
                     BRICK_WIDTH,
                     BRICK_HEIGHT,
                     brickColors[i  % brickColors.length]);
                bricksCenterX[i][j] = brickWall[i][j].x + BRICK_WIDTH / 2;
                bricksCenterY[i][j] = brickWall[i][j].y + BRICK_HEIGHT / 2;
            }           
        }
    }
    
    // restart a game
    private void restart() {
        score = 0;
        tries = 3;
        gamelock = true;
        msg = "Click To Start";
        msgX = WINDOW_WIDTH / 2 - 80;
        msgY = WINDOW_HEIGHT / 2;
        numBricks = ROWS * COLS;
        createWall();
        
        reinitiate();
    }
        
    private void reinitiate() {
        giftboxes = new ArrayList<Gift>();
        paddleWidth = PADDLE_WIDTH;
        ballSize = DIAMETER;
        xChange = speed;
        yChange = speed;
        xPos = WINDOW_WIDTH / 2 - ballSize / 2; 
        yPos = WINDOW_HEIGHT / 2 - ballSize / 2;
        lostSoundLock = true;
        flagMovingLeft = false;
        flagMovingDown = false;
        playSound(start);
    }
    
    // play sounds
    private void playSound(String file) {
        try {
            runSound(file);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    // read sound file
    private void runSound(String file) throws Exception {
        InputStream in = new FileInputStream(file);
        AudioStream audioStream = new AudioStream(in);
        AudioPlayer.player.start(audioStream);
    }
    
    // INNER CLASS FOR A BRICK
    private class Brick {
        private int x, y, w, h; //position and width/height
        private Color color;
        
        private Color protectColor = Color.GRAY;
        private int protectLayer = 6;
        
        private boolean hit;
        
        private boolean hardToBreak;
        private double probHardBrick = 0.15;
        
        public Brick(int x, int y, int w, int h, Color color) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.color = color;
            this.hit = false;
            this.hardToBreak = randStiffness();
        }
        
        // decide if it is a hard brick
        private boolean randStiffness() {
            double r = Math.random();
            return r <= probHardBrick;
        }
        
        // decide if it is broken down
        private boolean ifBreak() {
            playSound(bouncing);
            if (hardToBreak) {
                hardToBreak = false;
                return false;
            }
            score++;
            numBricks--;
            if (Math.random() <= probDrop)
                giftboxes.add(new Gift(x, y));
            return true;
        }
            
        public void checkHit() {
            if (hit) return;
            if (xPos + ballSize >= x && xPos <= x + w &&
                yPos <= y + h && yPos > y) {
                flagMovingDown = true;
                hit = ifBreak();
                return;
            }
            if (xPos + ballSize >= x && xPos <= x + w &&
                yPos + ballSize >= y && yPos < y + h) {
                flagMovingDown = false;
                hit = ifBreak();
                return;
            }
            if (yPos + ballSize >= y && yPos <= y + h &&
                xPos >= x + w && xPos <= x + w + 4) {
                flagMovingLeft = false;
                hit = ifBreak();
                return;
            }
            if (yPos + ballSize >= y && yPos <= y + h &&
                xPos + ballSize <= x && xPos + ballSize >= x - 4) {
                flagMovingLeft = true;
                hit = ifBreak();
                return;
            }
        }
        
        public void drawBrick(Graphics g) {
            if (hit) return;
            if (hardToBreak) {
                g.setColor(protectColor);
                g.fillRect(x - protectLayer / 2,
                           y - protectLayer / 2,
                           w + protectLayer,
                           h + protectLayer);
            }
            g.setColor(color);
            g.fillRect(x, y, w, h);
        }
    }
    
    // inner class for a dropping gift
    private class Gift {
        private int x, y, size, inner;
        private boolean hit;
        
        public Gift(int x, int y) {
            this.x = x;
            this.y = y;
            hit = false;
            size = 26;
            inner = 6;
        }
        
        public void drawBox(Graphics g) {
            if (hit) return;
            g.setColor(Color.BLACK);
            g.fillRect(x, y, size, size);
            g.setColor(Color.WHITE);
            g.fillRect(x + inner / 2,
                       y + inner / 2,
                       size - inner,
                       size - inner);
        }
        
        public void checkHit() {
            if (hit) return;
            if (y >= LOWER_PADDLE_Y - size && y <= LOWER_PADDLE_Y &&
                x > lowerPaddleX - size &&
                x <= lowerPaddleX + paddleWidth) {
            hit = true;
                        
            double r = Math.random();
            // ball bigger
            if (r <= 0.1) { ballSize /= 3; if (ballSize == 0) ballSize = 2; } 
            // paddle longer
            else if (r > 0.1 && r <= 0.4) { 
                paddleWidth *= 2; 
                if (paddleWidth > BreakOut.this.getWidth()) 
                    paddleWidth = BreakOut.this.getWidth() - 10;
            }
            // paddle shorter
            else if (r > 0.4 && r <= 0.6) { 
                paddleWidth /= 3; 
                if (paddleWidth == 0) paddleWidth = 2;
            }
            // ball larger
            else if (r > 0.6 && r <= 0.8) { 
                ballSize *= 2; 
                if (ballSize > 200) ballSize = 200;
            }
            // ball faster
            else if (r > 0.8 && r <= 0.9999) { xChange *= 3; yChange *= 3; }
            // win right away
            else {
                for (int row = 0; row < ROWS; row++) {
                    for (int c = 0; c < COLS; c++) {
                        if (!brickWall[row][c].hit) {
                            brickWall[row][c].hit = true;
                            score += 1;
                            numBricks--;
                        }
                    }
                }
            }
            }else {
                y += 1;
            }
        }
        
    }

    
    // drawFrame called by paintComponent. Use the Graphics
    // object to draw the ball and paddles on the scene.
    public void drawFrame(Graphics g) {
        // draw bricks
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++)
                brickWall[r][c].drawBrick(g);
        }
        
        // draw giftboxes
        for (Gift gift : giftboxes)
            gift.drawBox(g);
        
        // draw score board
        scoreBoard(g);
        
        // draw the paddle
        g.setColor(PADDLE_COLOR);     
        g.fillRect(lowerPaddleX, LOWER_PADDLE_Y, paddleWidth, PADDLE_HEIGHT);
        
        if (gamelock) {
            g.setFont(new Font("default", Font.BOLD, 26));
            g.setColor(Color.BLACK);
            g.drawString(msg, msgX, msgY);
            return;
        }
        
        // draw the ball
        g.setColor(Color.RED);       
        g.fillOval(xPos, yPos, ballSize, ballSize);
        
        // decide direction of ball
        changeDirection();
        
        // when you miss the ball
        if (yPos >= this.getHeight()) {
            msg = "YOU MISS (Click to continue)";
            msgX = WINDOW_WIDTH / 2 - 180;
            gamelock = true;
            tries--;
            if (lostSoundLock) {
                playSound(lost);
                lostSoundLock = false;
            }
            reinitiate();
        }
        // gameover and you lose
        if (tries == 0) {
            gameover = true;
            msg = "GAME OVER! (Click to continue)";
        }
                   
        // WRITE THE CHECK FOR NO BRICKS REMAINING.
        if (numBricks == 0) {
            gameover = true;
            msgX = WINDOW_WIDTH / 2 - 300;
            
            int finalScore = score * tries;
            g.setFont(new Font("default", Font.BOLD, 26));
            g.setColor(Color.BLACK);
            g.drawString("You Won! -- Final Score:   " + finalScore +
                         "   (Click To Continue)", msgX, msgY-60);
            
            xChange = 0;
            yChange = 0;
        }        
    }
    
    // CODE for score and tries left
    private void scoreBoard(Graphics g) {
        g.setFont(new Font("default", Font.BOLD, 16));
        g.setColor(Color.BLACK);
        g.drawString("score: " + score, 10, 20);
        g.drawString("tries left: " + tries, getWidth()-100, 20);
    }

    private void changeDirection() {
        // ball at left of scene
        if (xPos <= 0)
            flagMovingLeft = false;
        // ball at top of scene, let it go off scene
        if (yPos <= 0) 
            flagMovingDown = true;        
        // ball at right of scene
        if (xPos + ballSize >= this.getWidth())
            flagMovingLeft = true;
        // paddle
        if (yPos >= LOWER_PADDLE_Y - ballSize && yPos <= LOWER_PADDLE_Y &&
            xPos > lowerPaddleX - ballSize &&
            xPos <= lowerPaddleX + paddleWidth) {          
            flagMovingDown = false;
            playSound(bouncing);
        }
        
        // check if each brick is hit
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) 
                brickWall[r][c].checkHit();
        }
        
        // check if a gift is hit
        for (Gift gift : giftboxes)
            gift.checkHit();
        
        // ball direction modification
        if (flagMovingLeft)
            xPos -= xChange;
        else 
            xPos += xChange;        
        if (flagMovingDown) 
            yPos += yChange;
        else 
            yPos -= yChange;
    }
    
    public void actionPerformed(ActionEvent evt) {
        // The only events possible in this version are 
        // ActionEvents generated by the frameTimer
        repaint();
    }
    
    // Called when program started and every time repaint() is called.
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawFrame(g);
    }
    
    
    // starting point for execution
    public static void main(String[] args) {
        
        // Create JFrame to hold a JPanel
        JFrame window = new JFrame("BreakOut!");
        
        // Create an item of 'this' type (JPanel)
        BreakOut drawingArea = new BreakOut();
        // Create and start timer, connected to 'this' JPanel
        Timer frameTimer = new Timer(MS, drawingArea);
        frameTimer.start();
        
        // set the drawingArea JPanel to be the content pane of JFrame window
        window.setContentPane(drawingArea);
        
        // Set up JFrame before exiting main method.
        window.pack();
        window.setLocation(100,50);
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);
        drawingArea.requestFocusInWindow();
        
    } // end main
    
}
