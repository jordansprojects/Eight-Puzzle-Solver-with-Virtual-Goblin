
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.IOException;

public class GoblinPanel extends JPanel implements  ActionListener{
    BufferedImage lookingAroundAnimation[];
    BufferedImage bobbingAnimation[];
    BufferedImage earFlapAnimation[];
    BufferedImage neutralAnimation[];
    BufferedImage goblinShirt;
    final int X = -70;
    final int Y = 135;
    Timer timer = new Timer(150,this);
    int tick = 30;
    private boolean playBobbing = false;
    private boolean playLooking = false;
    private boolean playEarFlap = false;
    private boolean playNeutral = true;

    private static int count = 0;
    private final int MAX_COUNT = 4;
    private static int animationTick = 5;

    public GoblinPanel() {
        makeImageArrays();

    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        timer.start();
        g.drawImage(goblinShirt,X, 20, this);
        if(playNeutral){
            g.drawImage(neutralAnimation[count], X, Y, this);
        }
        else if(playBobbing){
            g.drawImage(bobbingAnimation[count], X, Y, this);
        }
        else if(playLooking){
            g.drawImage(lookingAroundAnimation[count], X, Y, this);

        }
        else if(playEarFlap){
            g.drawImage(earFlapAnimation[count], X, Y, this);
        }
    }
    private BufferedImage generateImage(String imgName) {

        BufferedImage bi = null;
        try {
            bi = ImageIO.read(getClass().getResource(imgName));
        }//end of try
        catch (IOException e) {
            System.out.println("No file found!!");
            e.printStackTrace();
        }//end of catch
        return bi;

    }


    public void actionPerformed(ActionEvent e) {
        count++;
        repaint();
        if (count > MAX_COUNT - 1) {
            count = 0;
        }
        tick++;
        if(playLooking ){
            animationTick = 35;
        }
        else if(playEarFlap){
            animationTick = 5;
        }else if(playBobbing){
            animationTick = 10;
        }

        if (tick > animationTick)
        {
            playBobbing = false;
            playLooking = false;
            playEarFlap = false;
            playNeutral = true;
        }
    }

    public void playBobbinAnimation(){
            tick =0;
            playBobbing = true;
            playLooking = false;
            playEarFlap = false;
            playNeutral = false;

    }
    public void playLookingAnimation(){
        tick =0;
        playBobbing = false;
        playLooking = true;
        playEarFlap = false;
        playNeutral = false;

    }
    public void playEarFlapAnimation(){
        tick =0;
        playBobbing = false;
        playLooking = false;
        playEarFlap = true;
        playNeutral = false;

    }

    private void makeImageArrays() {
        lookingAroundAnimation = new BufferedImage[4];

        lookingAroundAnimation[0] = generateImage("/goblinlookinaround1.png");
        lookingAroundAnimation[1] = generateImage("/goblinlookinaround2.png");
        lookingAroundAnimation[2] = generateImage("/goblinlookinaround3.png");
        lookingAroundAnimation[3] = generateImage("/goblinlookinaround4.png");
        bobbingAnimation = new BufferedImage[4];
        bobbingAnimation[0] = generateImage("/bobbingheadgoblin1.png");
        bobbingAnimation[1] = generateImage("/bobbingheadgoblin2.png");
        bobbingAnimation[2] = generateImage("/bobbingheadgoblin3.png");
        bobbingAnimation[3] = generateImage("/bobbingheadgoblin4.png");
        earFlapAnimation = new BufferedImage[4];
        earFlapAnimation[0] = generateImage("/laughinggoblinWithFlappingEars1.png");
        earFlapAnimation[1] = generateImage("/laughinggoblinWithFlappingEars2.png");
        earFlapAnimation[2] = generateImage("/laughinggoblinWithFlappingEars1.png");
        earFlapAnimation[3] = generateImage("/laughinggoblinWithFlappingEars2.png");
        goblinShirt = generateImage("/goblinshirt.png");
        neutralAnimation = new BufferedImage[4];
        neutralAnimation[0] = generateImage("/neutralgob1.png");
        neutralAnimation[1] = generateImage("/neutralgob2.png");
        neutralAnimation[2] = generateImage("/neutralgob3.png");
        neutralAnimation[3] = generateImage("/neutralgob4.png");

    }

}//end of GoblinPanel class
