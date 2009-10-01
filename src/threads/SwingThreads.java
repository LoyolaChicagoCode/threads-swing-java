package threads;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class SwingThreads extends JFrame {
	
  static final long serialVersionUID = 123123000; 

  private JPanel contentPane;
  private JPanel jPanel1 = new JPanel();
  private JButton jButton5 = new JButton();
  private JButton jButton4 = new JButton();
  private JButton jButton3 = new JButton();
  private JButton jButton1 = new JButton();
  private GridLayout gridLayout2 = new GridLayout();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JPanel jPanel2 = new JPanel();
  private JLabel jLabel2 = new JLabel();
  private JLabel jLabel1 = new JLabel();
  private JPanel jPanel3 = new JPanel();
  private BorderLayout borderLayout2 = new BorderLayout();
  private JProgressBar jProgressBar1 = new JProgressBar();
  private GridLayout gridLayout1 = new GridLayout();

  SwingWorker worker;

  // Construct the frame
  public SwingThreads() {
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setTitle("Swing Threads Example");

	contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(borderLayout1);
    contentPane.setMinimumSize(new Dimension(1, 1));

    jButton5.setText("Clear console input");
    jButton5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	    System.out.println("button 5 pressed");
	    setText("");
      }
    });

    jButton4.setText("Interrupt (non-locking) activity");
    jButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	    System.out.println("button 4 pressed");
	    // ignore if there is no worker
	    if (worker != null) {
	      worker.interrupt();
	      worker = null;
	    }
      }
    });

    jButton3.setText("Start activity (without locking UI)");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(final ActionEvent e) {
	    System.out.println("button 3 pressed");
	    // ignore if there is already a worker
	    if (worker == null) {
	      worker = new SwingWorker() {
	        public Object construct() {
	          System.out.println("received event " + e);
	          doSomeWork(5000, jProgressBar1);
	          return null;
	        }
	        public void finished() {
	          worker = null;
	        }
	      };
	      worker.start();
	    }
      }
    });

    jButton1.setToolTipText("hello");
    jButton1.setText("Start activity (locking up UI)");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	    System.out.println("button 1 pressed");
	    doSomeWork(5000, jProgressBar1);
      }
    });

    jPanel1.setLayout(gridLayout2);
    gridLayout2.setColumns(1);
    gridLayout2.setRows(0);
    jLabel2.setBackground(Color.yellow);
    jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 14));
    jLabel2.setOpaque(true);
    jLabel2.setPreferredSize(new Dimension(0, 16));
    jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setText("Console input:");
    jPanel2.setLayout(borderLayout2);
    jPanel3.setLayout(gridLayout1);
    gridLayout1.setColumns(1);
    gridLayout1.setRows(0);
    contentPane.add(jPanel1,  BorderLayout.CENTER);
    jPanel1.add(jButton5, null);
    jPanel1.add(jButton4, null);
    jPanel1.add(jButton3, null);
    jPanel1.add(jButton1, null);
    contentPane.add(jPanel2, BorderLayout.NORTH);
    jPanel2.add(jLabel1,  BorderLayout.WEST);
    jPanel2.add(jLabel2,  BorderLayout.CENTER);
    contentPane.add(jPanel3,  BorderLayout.SOUTH);
    jPanel3.add(jProgressBar1, null);
  }

  /**
   * This method simulates an ongoing task.
   */
  public static void doSomeWork(final int amount, final JProgressBar progressBar) {
    System.out.print("Going to work for " + amount / 1000 + " seconds...");
    try {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          progressBar.setMaximum(amount);
        }
      });
      for (int i = 0; i < amount /* && ! Thread.interrupted() */; i ++) {
        final int curr = i;
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            progressBar.setValue(curr);
          }
        });
//      uncomment to make CPU-bound 
//        for (int k = 0; k < 50000; k ++) {
//          new Object();
//        }
//		comment out to make CPU-bound        
        Thread.sleep(1);
      }
      System.out.println("...done");
    } catch (Exception ex) {
      System.out.println("...interrupted");
    } finally {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          progressBar.setValue(0);
        }
      });
    }
  }

  public void setText(String text) {
    jLabel2.setText(text);
  }
}
