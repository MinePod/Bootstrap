package fr.minepod.bootstrap;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;

import org.json.simple.parser.ParseException;

import fr.minepod.launcher.CrashReport;
import fr.minepod.launcher.DownloaderThread;
import fr.minepod.launcher.LaunchJar;
import fr.minepod.launcher.VersionClass;
import fr.minepod.launcher.VersionsManager;
import fr.minepod.utils.UtilsFiles;

public class Bootstrap {
  public static BootstrapGui gui;

  public static void main(String[] args) throws IOException {
    Config.setConfig();

    downloadRequiredFiles();
  }

  public static void downloadRequiredFiles() {
    try {
      gui = new BootstrapGui();
      gui.initGui();

      DownloaderThread downloader =
          new DownloaderThread(Config.logger, Config.bootstrapVersionUrl,
              Config.bootstrapVersionFile);
      downloader.start();
      downloader.join();

      String upstreamVersion = UtilsFiles.readFile(Config.bootstrapVersionFile);
      if (!Config.bootstrapVersion.equals(upstreamVersion)
          && Config.bootstrapVersion != Langage.DEVELOPMENTVERSION.toString()) {
        JOptionPane.showMessageDialog(null, "Une nouvelle version (" + upstreamVersion
            + ") est disponible sur le site.", "Information", JOptionPane.INFORMATION_MESSAGE);

        System.exit(0);
      }

      VersionsManager versionsManager =
          new VersionsManager(Config.launcherLocation, null, Config.minecraftDir, Config.slash,
              Config.logger);
      versionsManager.installVersion(new VersionClass(Config.bootstrapDataUrl, "unknown",
          "unknown", "unknown", "unknown", "unknown"), Bootstrap.gui, Config.logger);

      gui.finish();
      Config.logger.info("Ready!");

      try {
        new LaunchJar(Config.launcherJar);
        System.exit(0);
      } catch (Exception e) {
        CrashReport.show(e.toString());
      }

    } catch (IOException | InterruptedException | NoSuchAlgorithmException | ParseException e) {
      CrashReport.show(e.toString());
    }
  }
}
