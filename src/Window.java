
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JButton;

public class Window extends JFrame implements ActionListener {


  GamePanel eightPanel = new GamePanel( );
  GoblinPanel goblin = new GoblinPanel();
  JPanel buttonPanel = new JPanel();
  JPanel movePanel = new JPanel();
  JButton hint = new JButton("hint");
  JButton solve = new JButton("solve");
  JButton left = new JButton(" move left");
  JButton right= new JButton(" move right");
  JButton up = new JButton(" move up");
  JButton down = new JButton(" move down");
  JButton restart = new JButton("restart game");
  Listener mouseListener = new Listener();



  Window() throws UnsolveablePuzzleException {
    setDefaultCloseOperation(3);
    setSize(1000 ,600);
    goblin.setBackground(Color.GRAY);
    goblin.addMouseListener(mouseListener);
    setTitle("Eight Puzzle");

    setLocationRelativeTo(null);
    setResizable(false);

    buttonPanel.add(left);
    buttonPanel.add(up);
    buttonPanel.add(down);
    buttonPanel.add(right);

    buttonPanel.add(hint);
    buttonPanel.add(solve);
    buttonPanel.add(restart);

    hint.addActionListener(this);
    solve.addActionListener(this);
    left.addActionListener(this);
    right.addActionListener(this);
    up.addActionListener(this);
    down.addActionListener(this);
   restart.addActionListener(this);


    add(buttonPanel, BorderLayout.PAGE_END);
    add(goblin);
    add(eightPanel, BorderLayout.LINE_END);

   // add(poolballPanel);

    setVisible(true);

  }

  @Override
  public void actionPerformed (ActionEvent e){
     if(e.getSource() == hint){
       goblin.playBobbinAnimation();
       try {
         eightPanel.getHint();
       } catch (UnsolveablePuzzleException ex) {
         ex.printStackTrace();
       }
       //  System.out.println("DEBUG: hint pressed");
     }else if(e.getSource() == solve){
       goblin.playLookingAnimation();
       try {
         eightPanel.solve();
       } catch (InterruptedException ex) {
         ex.printStackTrace();
       }
       //System.out.println("DEBUG: solve pressed");
     }else if (e.getSource() == left){
       eightPanel.moveLeft();
     }else if(e.getSource() == right){
       eightPanel.moveRight();
     }else if(e.getSource() == up){
       eightPanel.moveUp();
     }else if(e.getSource() == down){
       eightPanel.moveDown();
     }else if(e.getSource() == restart){
       try {
         eightPanel.shuffleTiles();
       } catch (UnsolveablePuzzleException ex) {
         ex.printStackTrace();
       }
     }
  }



  private class Listener implements MouseListener{
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
      //System.out.println("DEBUG: MOUSE CLICKED! ");

    }
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
      //System.out.println("DEBUG: MOUSE PRESSED! ");
      goblin.playEarFlapAnimation();

    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
      System.out.println("DEBUG: MOUSE RELEASED! ");
    }
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
      //System.out.println("DEBUG: MOUSE ENTERED ");

    }
    @Override
    public void mouseExited(MouseEvent mouseEvent) {
      //System.out.println("DEBUG: MOUSE EXITED ");

    }
  }

  }//end of Window class





