package fr.minepod.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import fr.minepod.launcher.CrashReport;
import fr.minepod.launcher.LauncherConfig;

public class BootstrapConfig extends LauncherConfig {
  public static String bootstrapVersionUrl =
      "http://assets.minepod.fr/launcher/versions/bootstrap_name.txt";
  public static String bootstrapDataUrl = "http://assets.minepod.fr/launcher/data/launcher.json";
  public static String bootstrapVersion;
  public static String bootstrapVersionFile;
  public static String launcherJar;

  public static void setBootstrapConfig() {
    bootstrapVersionFile = launcherLocation + slash + "bootstrap.txt";
    launcherJar = launcherLocation + slash + "launcher.jar";

    try {
      InputStream InputStream =
          Bootstrap.class.getProtectionDomain().getCodeSource().getLocation().openStream();
      JarInputStream JarInputStream = new JarInputStream(InputStream);
      Manifest Manifest = JarInputStream.getManifest();
      JarInputStream.close();
      InputStream.close();

      if (Manifest != null) {
        Attributes Attributes = Manifest.getMainAttributes();
        bootstrapVersion = Attributes.getValue("Bootstrap-version");
      }
    } catch (IOException e) {
      CrashReport.show(e.toString());
    }
  }
}
