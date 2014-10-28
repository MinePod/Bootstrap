package fr.minepod.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import fr.minepod.launcher.CrashReport;
import fr.minepod.utils.UtilsLogger;

public class Config {
  public static String bootstrapVersionUrl =
      "http://assets.minepod.fr/launcher/versions/bootstrap_name.txt";
  public static String bootstrapDataUrl = "http://assets.minepod.fr/launcher/data/launcher.json";
  public static String launcherName = "MinePod";
  public static String bootstrapVersion;

  public static String logFile;
  public static String bootstrapVersionFile;
  public static String launcherMd5;
  public static String appDataPath;
  public static String launcherDir;
  public static String minecraftDir;
  public static String minecraft;
  public static String slash;
  public static String launcherLocation;
  public static String launcherJar;

  public static java.util.logging.Logger logger;

  public static void setConfig() {
    String OS = System.getProperty("os.name").toUpperCase();
    if (OS.contains("WIN")) {
      appDataPath = System.getenv("APPDATA");
      launcherDir = "\\." + launcherName;
      minecraft = "\\.minecraft";
    } else if (OS.contains("MAC")) {
      appDataPath = System.getProperty("user.home") + "/Library/Application Support";
      launcherDir = "/" + launcherName;
      minecraft = "/minecraft";
    } else if (OS.contains("NUX")) {
      appDataPath = System.getProperty("user.home");
      launcherDir = "/." + launcherName;
      minecraft = "/.minecraft";
    } else {
      appDataPath = System.getProperty("user.dir");
      launcherDir = "/." + launcherName;
      minecraft = "/.minecraft";
    }

    slash = System.getProperty("file.separator");
    minecraftDir = appDataPath + minecraft;
    launcherLocation = appDataPath + launcherDir;
    launcherJar = launcherLocation + slash + "launcher.jar";
    launcherMd5 = launcherLocation + slash + "launcher.md5";
    bootstrapVersionFile = launcherLocation + slash + "bootstrap.txt";
    logFile = launcherLocation + slash + "bootstrap_logs.txt";

    try {
      logger = UtilsLogger.setLogger(logFile);

      InputStream InputStream =
          Bootstrap.class.getProtectionDomain().getCodeSource().getLocation().openStream();
      JarInputStream JarInputStream = new JarInputStream(InputStream);
      Manifest Manifest = JarInputStream.getManifest();
      JarInputStream.close();
      InputStream.close();

      if (Manifest != null) {
        Attributes Attributes = Manifest.getMainAttributes();
        bootstrapVersion = Attributes.getValue("Bootstrap-Version");
      } else {
        bootstrapVersion = Langage.DEVELOPMENTVERSION.toString();
      }
    } catch (IOException e) {
      CrashReport.show(e.toString());
    }
  }
}
