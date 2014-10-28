package fr.minepod.bootstrap;

import java.awt.Dimension;

import javax.swing.JFrame;

import fr.minepod.launcher.LauncherGui;

public class BootstrapGui extends LauncherGui {
  public BootstrapGui() {
    super(null);
  }

  public void initGui() {
    JFrame j = new JFrame("MinePod Boostrap " + Config.bootstrapVersion);
    j.setContentPane(progress);

    progress.setStringPainted(true);
    progress.setValue(0);

    j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    j.setSize(new Dimension(400, 60));
    j.setLocationRelativeTo(null);
    j.setVisible(true);
  }
}