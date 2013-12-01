package fr.minepod.bootstrap;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Start {
	private static Downloader Downloader = new Downloader();
	
	public static void main(String[] args) throws IOException {
		new Config().SetConfig();
		new Debug().SetDebug();
		
		DownloadRequiredFiles();
	}
	
	 public static void DownloadRequiredFiles() {
		 try {
			 Config.Gui.SetLoading();
			 
			 Downloader.DownloadFiles(new URL(Config.BootstrapVersionUrl), Config.BootstrapAvaibleVersion, false);
			 
			 if(!Config.BootstrapVersion.equals(fr.minepod.Utils.Files.ReadFile(Config.BootstrapAvaibleVersion)) && Config.BootstrapVersion != Langage.DEVELOPMENTVERSION.toString()) {
				 javax.swing.JOptionPane.showMessageDialog(null, Langage.ANOTHERVERSIONISAVAIBLE + fr.minepod.Utils.Files.ReadFile(Config.BootstrapAvaibleVersion) + "! " + Config.BootstrapLatestVersionUrl, "Information", javax.swing.JOptionPane.INFORMATION_MESSAGE);
				 System.exit(0);
			 }
			 
			 if(!new File(Config.LauncherLocation).exists())
				 new File(Config.LauncherLocation).mkdir();		
			 
			 Downloader.DownloadFiles(new URL(Config.GetMd5FileUrl + "launcher.php"), Config.LauncherMd5, false);
			
			 String CurrentLauncherMd5 = fr.minepod.Utils.Files.md5(Config.LauncherJar);
			 String ExpectedLauncherMd5 = fr.minepod.Utils.Files.ReadFile(Config.LauncherMd5);
			 
			 Config.Logger.info("Expected md5: " + ExpectedLauncherMd5);
			 Config.Logger.info("Current md5: " + CurrentLauncherMd5);
			 
			 if(CurrentLauncherMd5 == null || !CurrentLauncherMd5.equals(ExpectedLauncherMd5)) {
				 Config.Logger.warning("Detecting modified files, deleting...");
				 Downloader.DownloadFiles(new URL(Config.LauncherLatestVersionUrl), Config.LauncherJar, true);
			 }
				 
		     Config.Gui.Finish();
			 Config.Logger.info("Ready!");
			 
			 try {
				 new LaunchJar(Config.LauncherJar);
				 System.exit(0);
			 } catch (Exception e) {
				 CrashReport.SendReport(e.toString(), "launching MinePod's launcher");
			 }

		 } catch (IOException e) {	 
			CrashReport.SendReport(e.toString(), Langage.DOINGMAINTHREADTASKS.toString());
		 } catch (Exception e) {
			CrashReport.SendReport(e.toString(), Langage.DOINGMAINTHREADTASKS.toString());
		 }
		 
	 }
}
