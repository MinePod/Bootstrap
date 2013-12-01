package fr.minepod.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import fr.minepod.bootstrap.Gui;

public class Config {
	 public static String LauncherLatestVersionUrl = "http://assets.minepod.fr/launcher/launcher.php";
	 public static String BootstrapVersionUrl = "http://assets.minepod.fr/launcher/versions/bootstrap_name.txt";
	 public static String BootstrapLatestVersionUrl = "http://assets.minepod.fr/launcher/bootstrap.php";
	 public static String GetMd5FileUrl = "http://assets.minepod.fr/launcher/md5.php?file=";
	 public static String LauncherName = "MinePod";
	 public static String BootstrapVersion;
	 
	 public static String LogFile;
	 public static String BootstrapAvaibleVersion;
	 public static String LauncherMd5;
	 public static String DebugFilePath;
	 public static String AppDataPath;
	 public static String LauncherDir;
	 public static String Slash;
	 public static String LauncherLocation;
	 public static String LauncherJar;
	 public static Gui Gui;
	 
	 public static java.util.logging.Logger Logger;
	 
	 public void SetConfig() {
		String OS = System.getProperty("os.name").toUpperCase();
		if(OS.contains("WIN")) {
			Config.AppDataPath = System.getenv("APPDATA");
			Config.LauncherDir = "\\." + LauncherName;
		} else if(OS.contains("MAC")) {
			Config.AppDataPath = System.getProperty("user.home") + "/Library/Application Support";
			Config.LauncherDir = "/" + LauncherName;
		} else if(OS.contains("NUX")) {
			Config.AppDataPath = System.getProperty("user.home");
			Config.LauncherDir = "/." + LauncherName;
		} else {
			Config.AppDataPath =  System.getProperty("user.dir");
			Config.LauncherDir = "/." + LauncherName;
		}
		
		Config.Slash = System.getProperty("file.separator");
		Config.LauncherLocation = AppDataPath + LauncherDir;
		Config.LauncherJar = Config.LauncherLocation + Config.Slash + "Launcher.jar";
		Config.LauncherMd5 = Config.LauncherLocation + Config.Slash + "Launcher.md5";
		Config.DebugFilePath = LauncherLocation + Slash + "debug.json";
		Config.BootstrapAvaibleVersion = Config.LauncherLocation + Config.Slash + "Bootstrap.txt";
		Config.LogFile = LauncherLocation + Slash + "bootstrap_logs.txt";
		
		try {
			Logger = new fr.minepod.Utils.Logger().SetLogger(Config.LogFile);
			
			InputStream InputStream = Start.class.getProtectionDomain().getCodeSource().getLocation().openStream();
	        JarInputStream JarInputStream = new JarInputStream(InputStream);
	        Manifest Manifest = JarInputStream.getManifest();
	        JarInputStream.close();
	        InputStream.close();
	        
	        if(Manifest != null) {
	            Attributes Attributes = Manifest.getMainAttributes();
	            SetBootstrapVersion(Attributes.getValue("Bootstrap-version"));
	        } else {
	        	SetBootstrapVersion(Langage.DEVELOPMENTVERSION.toString());
	        }
		} catch(IOException e) {
			CrashReport.SendReport(e.toString(), Langage.DOINGMAINTHREADTASKS.toString());
		}
		
		Config.Gui = new Gui();
	 }
		    
	 public void SetBootstrapVersion(String Version) {
		 Config.BootstrapVersion = Version;
	 }
}
