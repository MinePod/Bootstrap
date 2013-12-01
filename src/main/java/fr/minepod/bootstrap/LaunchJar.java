package fr.minepod.bootstrap;

import java.awt.Desktop;
import java.io.File;

public class LaunchJar {
	public LaunchJar(String path) throws Exception {
		String OS = System.getProperty("os.name").toUpperCase();
		if(OS.contains("WIN")) {
			Runtime.getRuntime().exec("java -jar -Xmx1G \"" + path + "\" " + Config.BootstrapVersion);
		} else if(OS.contains("MAC")) {
			Desktop.getDesktop().open(new File(path));
		} else if(OS.contains("NUX")) {
			Runtime.getRuntime().exec("java -jar -Xmx1G \"" + path + "\" " + Config.BootstrapVersion);
		} else {
			Runtime.getRuntime().exec("java -jar -Xmx1G \"" + path + "\" " + Config.BootstrapVersion);
		}
	}
}
