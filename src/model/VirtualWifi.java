package model;

import java.io.IOException;

import utilities.GlobalConstants;
import utilities.VirtualWifiUtilies;

public class VirtualWifi {

	private static VirtualWifi wifi;

	public static VirtualWifi getInstance(String name, String password)
			throws IOException, InterruptedException {
		if (wifi == null) {
			checkCompatibility();
			wifi = new VirtualWifi();
		}

		VirtualWifiUtilies.rewriteCreateBatFile("resources/create.bat",
				"resources/create_tmp.bat", name, password);

		VirtualWifiUtilies.execBatch("create_tmp.bat");
		return wifi;
	}

	private static void checkCompatibility() throws IOException,
			InterruptedException {
		// TODO CHECK COMPATIBLIILTY
		VirtualWifiUtilies.execBatch("check_compatibility.bat");
	}

	public void start() throws IOException, InterruptedException {
		// TODO check if started before then stop it
		stop();
		VirtualWifiUtilies.execBatch("start.bat");
	}

	public void share() throws IOException, InterruptedException {
		VirtualWifiUtilies.rewriteShareVbsFile("resources/original_share.vbs",
				"resources/share.vbs", "resources/"
						+ GlobalConstants.INTERFACE_BEFORE_START, "resources/"
						+ GlobalConstants.INTERFACE_AFTER_START);
		VirtualWifiUtilies.execBatch("share.bat");
	}

	public void stop() throws IOException, InterruptedException {
		VirtualWifiUtilies.execBatch("stop.bat");
	}
}
