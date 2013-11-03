package fr.minepod.bootstrap;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class Gui {
	private JProgressBar current = new JProgressBar(0, 100);
	private double totalBytesRead = 0.0D;
	private double totalLength = 0.0D;

	public Gui() {
		JFrame j = new JFrame("MinePod Boostrap " + Config.BootstrapVersion);
	    j.setContentPane(current);
	    
	    current.setStringPainted(true);
	    current.setValue(0);

	    j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    j.setSize(new Dimension(400, 50));
	    j.setLocationRelativeTo(null);
	    j.setVisible(true);
	}

	public void Update(int UpdateNumber) {
		current.setIndeterminate(false);
		current.setValue(UpdateNumber);
	}
	
	public void SetMax(Double fileLength) {
		this.totalLength += fileLength;
	}
	
	public void Add(int bytesRead) {
		this.totalBytesRead += bytesRead;
		Update(((int) Math.round(totalBytesRead / totalLength * 100.0D)));
	}
	
	public void SetLoading() {
		this.current.setIndeterminate(true);
	}
	
	public void Finish() {
		Update(100);
	}

}