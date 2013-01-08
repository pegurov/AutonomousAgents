/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.MVector;
import Model.Vehicle;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

/**
 *
 * @author pashathebeast
 */
public class MovementPanel extends javax.swing.JPanel {
    ArrayList<Vehicle> preys;
    Vehicle hunter;
    Timer m_timer;
    public static Point mouseLocation;
    MVector desiredVector;
    
    //MVector location;
    //MVector speed;
    int width;
    int height;
    float hue, saturation, luminance;
    Random r;
    /**
     * Creates new form MovementPanel
     */
    public MovementPanel() {
        initComponents();
        preys = new ArrayList();
        m_timer = new Timer(30, new TimerAction());
        r = new Random();
        desiredVector = new MVector();
        

    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);   
        
        Graphics2D g2D = (Graphics2D)g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        saturation = 0.9f;    //1.0 for brilliant, 0.0 for dull
        luminance = 1f;       //1.0 for brighter, 0.0 for black
        
        g2D.setColor(Color.black);
        if (hunter != null) {
            g2D.draw(hunter.getVehicleShape());
        }

        //for (Vehicle m : preys){
        for (int i = 0; i < preys.size(); i++) {
            g2D.setColor(Color.getHSBColor(preys.get(i).getHue(), saturation, luminance));
            g2D.fill(preys.get(i).getVehicleShape());
        }        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonAction = new javax.swing.JButton();
        labelAmount = new javax.swing.JLabel();
        buttonPlayPause = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        buttonAction.setLabel("Action");
        buttonAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionActionPerformed(evt);
            }
        });

        buttonPlayPause.setText("play/pause");
        buttonPlayPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPlayPauseActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(buttonAction)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(buttonPlayPause))
                    .add(labelAmount))
                .addContainerGap(605, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(labelAmount)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 509, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(buttonAction)
                    .add(buttonPlayPause))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonActionActionPerformed
        preys.clear();
        height = this.getHeight();
        width = this.getWidth();
        
        hunter = new Vehicle(new MVector(r.nextInt(width), r.nextInt(height)), new MVector(width, height));
        hunter.setScale(15);
        hunter.setMaxSpeed(5);
        hunter.setMaxForce(0.3);
        
        // add 10 preys
        for (int i = 0; i < 100; i++) {
            Vehicle v = new Vehicle(new MVector(r.nextInt(width), r.nextInt(height)), new MVector(width, height)); 
            v.setMaxSpeed(5);
            v.setMaxForce(2);
            preys.add(v);
        }
        
        labelAmount.setText("Population size: " + preys.size());
        if (!m_timer.isRunning()) {
            m_timer.start();
        }

    }//GEN-LAST:event_buttonActionActionPerformed

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        MovementPanel.mouseLocation = this.getMousePosition();
        if (this.getMousePosition() != null) {
            //labelAmount.setText(String.format("(%d,%d)", (int)this.getMousePosition().getX(), (int)this.getMousePosition().getY()));
        }
        
    }//GEN-LAST:event_formMouseMoved

    private void buttonPlayPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPlayPauseActionPerformed
        if (!m_timer.isRunning()) {
            m_timer.start();
        }
        else{
            m_timer.stop();
        }
    }//GEN-LAST:event_buttonPlayPauseActionPerformed

        class TimerAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            int preyToHunt = 1;
            double distance = 10000;
            MVector distVector;
            for (int i = 0; i < preys.size(); i++) {
                preys.get(i).Wander();
                distVector = new MVector(hunter.getLocation().getX() - preys.get(i).getLocation().getX(), hunter.getLocation().getY() - preys.get(i).getLocation().getY());
                if (distVector.getLength() < distance) {
                    distance = distVector.getLength();
                    preyToHunt = i;
                }
            }
            
            hunter.Seek(new MVector(preys.get(preyToHunt).getLocation().getX(),preys.get(preyToHunt).getLocation().getY()));
            
            if (hunter.getVehicleShape().intersects(preys.get(preyToHunt).getVehicleShape().getBounds2D())) {
                preys.remove(preyToHunt);
                labelAmount.setText("Population size: " + preys.size());
            }

            repaint();
            //Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
            //for (Vehicle m : preys){
                //m.Arrive(new MVector((int)MovementPanel.mouseLocation.getX(), (int)MovementPanel.mouseLocation.getY()));
                //m.Wander();
                //System.out.print(String.format("(%d,%d)", (int)desiredVector.getX(), (int)desiredVector.getY()));
            //}
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAction;
    private javax.swing.JButton buttonPlayPause;
    private javax.swing.JLabel labelAmount;
    // End of variables declaration//GEN-END:variables
}
