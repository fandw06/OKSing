package dawei.oksing;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.SwingUtilities;

public class OKSMain {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void createAndShowGUI() {
		
		JFrame window = new JFrame("OK Sing");
		OKSGUI gui = new OKSGUI();
		window.setSize(new Dimension(600, 580));
		window.setLocation(500, 100);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setResizable(false);
		
		window.add(gui.getOKSPanel());
		window.setJMenuBar(gui.getOKSMenubar());
	}

}
