/******************************************************************************
 * Copyright (C) 2019 Eric Pogue.
 * 
 * This file and the Thunderbird applciation is liscensed under the 
 * BSD-3-Clause.
 * 
 * You may use any part of the file as long as you give credit in your 
 * source code.
 * 
 * This application utilizes the HttpRequest.java library developed by 
 * Eric Pogue
 * 
 *****************************************************************************/
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Container; 
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import java.util.ArrayList;
import java.util.Collections;

class ContactTile extends JPanel {
    private int red, green, blue;
    private ThunderbirdContact contactInSeat = null;
    private String pName = null;

    private Boolean isAnIsle = false;
    public void setIsle() { isAnIsle = true; }

    ContactTile() {
        super();

        // Todo: Remove everything to do with random colors.
        // Todo: Implement visually appealing colors for isles and seats.
        //AG: I made all of the background colors grey.
    }

    ContactTile(ThunderbirdContact contactInSeatIn) {
        super();
        red = 169;
        green = 169;
        blue = 169;
        contactInSeat = contactInSeatIn;

        //AG: Implemented Mouse Event for JPanels of ContactTiles so that full information of student is shown in JOPtion Window
        //    when the Tile in the grid panel is clicked for the respective student.
        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent event) {
                //if/else statement to account for the many empty seats and aisle seats.
                if(contactInSeat != null){
                    JOptionPane.showMessageDialog(null, contactInSeatIn);
                }
                //AG: I know reading this logic it seems backwards, but it works. That being said, I am going to leave like this.
                else if (isAnIsle == false) { //checks to see if desk is present but empty
                    JOptionPane.showMessageDialog(null, "Empty Desk\nNo Student Information to display.");
                }
                else{ //checks to see if user clicked on aisle space or empty spot next to prof.
                    JOptionPane.showMessageDialog(null, "You have selected part of the Aisle or an empty space.\nNo desk is here.");
                }
            }
        });
    }

    final public void SetRandomValues() {
        red = GetNumberBetween(0,255);
        green = GetNumberBetween(0,255);
        blue = GetNumberBetween(0,255);
    }

    private static int GetNumberBetween(int min, int max) {
        Random myRandom = new Random();
        return min + myRandom.nextInt(max-min+1);
    }   

     public void paintComponent(Graphics g) {
        super.paintComponent(g); 

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        if (isAnIsle) {
            g.setColor(new Color(0,0,0));
        } else {
            g.setColor(new Color(red,green,blue));
        }
        
        g.fillRect (10, 10, panelWidth-20, panelHeight-20);

        g.setColor(new Color(GetContrastingColor(red),GetContrastingColor(green),GetContrastingColor(blue)));

        final int fontSize=14;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        int stringX = (panelWidth/2)-60;
        int stringY = (panelHeight/2)+30;
        if (contactInSeat != null) {

            // ToDo: Dispay preferred name instead of first and last name.
            //AG: Displayed preferredName with new getter for preferredName
            String pName = contactInSeat.getPreferredName();
            g.drawString(pName,stringX,stringY);
        }
    }

    private static int GetContrastingColor(int colorIn) {
        return ((colorIn+128)%256);
    }
}

class ThunderbirdFrame extends JFrame implements ActionListener {
    private ArrayList<ContactTile> tileList;

    public ThunderbirdFrame() {
        setBounds(200,200,1200,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        JButton reverseView = new JButton("Reverse View");
        buttonPanel.add(reverseView);
        reverseView.addActionListener(this);

        JPanel contactGridPanel = new JPanel();
        contentPane.add(contactGridPanel, BorderLayout.CENTER);
        contactGridPanel.setLayout(new GridLayout(4,8));

        ThunderbirdModel tbM = new ThunderbirdModel();
        tbM.LoadIndex();
        tbM.LoadContactsThreaded();

        // Todo: Review ThunderbirdModel in detail and implement a multithreaded version of loading contacts.
        // Hint: Review LoadContact() and LoadContactsThreaded() in detail.
        //AG: Implemented via tutorial.

        System.out.println("Printing Model:");
        System.out.println(tbM);
        tbM.ValidateContacts();   


        tileList = new ArrayList<ContactTile>();
        for(int i=1; i<33; i++) {
            ThunderbirdContact contactInSeat = tbM.findContactInSeat(i);

            if (contactInSeat != null) {
                System.out.println(contactInSeat);
            }

            ContactTile tile = new ContactTile(contactInSeat);


            // Todo: Place all the isle seats in a array or an ArrayList instead of hard coding them.
            //AG: added seats 20 and 28 as aisle seats.
            ArrayList<ContactTile> seatArray = new ArrayList<ContactTile>();
            if ((i==4)||(i==12)||(i==20)||(i==28)||(i==31)) {
                seatArray.add(tile);
            }

            for(ContactTile aisleSpot : seatArray){
                aisleSpot.setIsle();
            }
            tileList.add(tile);
            contactGridPanel.add(tile);
        }
    }

    public void actionPerformed(ActionEvent e) {
        for(ContactTile tile : tileList) {
            // Todo: Remove randomization functionality and implement a visually appealing view of seats and isles.
            //AG: Implemented , removed

            // Todo: Implement reverse view where it looks like you are looking at the room from the back instead of the front 
            //     of the room.
            //AG: Not implemented, spent ~5 hrs on this feature alone before I threw in the towel.
        }
        System.out.println("Reverse button clicked.");
        repaint();
    }
}

// Todo: Rename the following class to Thunderbird.
// Hint: This will also require you to rename the Java file.
//AG : Implemented
public class Thunderbird {
    public static void main(String[] args) {

        // Todo: Update the following line so that it reflects the name change to Thunderbird.
        //AG: Implemented
        System.out.println("Thunderbird Starting...");

        ThunderbirdFrame myThunderbirdFrame = new ThunderbirdFrame();
        myThunderbirdFrame.setVisible(true);
    }
}