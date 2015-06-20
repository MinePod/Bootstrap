package fr.minepod.bootstrap;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;

import org.json.simple.parser.ParseException;

import fr.minepod.launcher.CrashReport;
import fr.minepod.launcher.LaunchJar;
import fr.minepod.launcher.updater.files.DownloaderThread;
import fr.minepod.launcher.updater.versions.VersionClass;
import fr.minepod.launcher.updater.versions.VersionsUpdater;
import fr.minepod.utils.UtilsFiles;

public class Bootstrap {
  public static BootstrapGui gui;
  public static UtilsFiles utilsFiles = new UtilsFiles();

  public static void main(String[] args) {
    try {
      BootstrapConfig.setLauncherConfig();
      BootstrapConfig.setBootstrapConfig();
      BootstrapConfig.logger.setUseParentHandlers(false);

      downloadRequiredFiles();
    } catch (SecurityException | IOException | NoSuchAlgorithmException | InterruptedException
        | ParseException e) {
      CrashReport.show(e);
    }
  }

  public static void downloadRequiredFiles() throws InterruptedException, IOException,
      NoSuchAlgorithmException, ParseException {
    gui = new BootstrapGui();
    gui.initGui();

    DownloaderThread downloader =
        new DownloaderThread(gui, BootstrapConfig.logger, BootstrapConfig.bootstrapVersionUrl,
            BootstrapConfig.bootstrapVersionFile);
    downloader.start();
    downloader.join();

    String upstreamVersion = utilsFiles.readFile(BootstrapConfig.bootstrapVersionFile);
    if (BootstrapConfig.bootstrapVersion != null
        && !BootstrapConfig.bootstrapVersion.equals(upstreamVersion)) {
      JOptionPane.showMessageDialog(null, "Une nouvelle version (" + upstreamVersion
          + ") est disponible sur le site.", "Information", JOptionPane.INFORMATION_MESSAGE);

      System.exit(0);
    }

    VersionsUpdater versionsUpdater = new VersionsUpdater(BootstrapConfig.logger);
    versionsUpdater.installVersion(gui, new VersionClass(BootstrapConfig.bootstrapDataUrl,
        "unknown", "unknown", "unknown", "unknown"));

    gui.finish();
    BootstrapConfig.logger.info("Ready!");

    new LaunchJar(BootstrapConfig.launcherJar);
    System.exit(0);
  }
}
