package threads;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

  SwingThreads frame = new SwingThreads();
  boolean packFrame = true;

  // Construct the application
  public Main() {
    //Pack frames that have useful preferred size info, e.g. from their layout
    //Validate frames that have preset sizes
    if (packFrame)
      frame.pack();
    else
      frame.validate();

    // Center the frame
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    frame.setVisible(true);
  }

  public void setText(String text) {
    frame.setText(text);
  }

  // Main method
  static public void main(String[] args) throws Exception {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    // build the GUI and make it visible
    final Main app = new Main();

    // after the GUI is up, read lines from the console input
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
      System.out.print("Type something... > ");
      final String input = stdin.readLine().trim();
      // use invokeLater to set the label text safely to the input line
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          app.setText(input);
        }
      });
    }
  }
}
