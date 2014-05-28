package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class VirtualWifiUtilies {

	public static void execBatch(String batName) throws IOException,
			InterruptedException {

		System.out.println("Exectuing " + batName);

		List<String> cmdAndArgs = Arrays.asList(new String[] { "cmd", "/c",
				batName });

		ProcessBuilder pb = new ProcessBuilder(cmdAndArgs);
		pb.directory(new File(
				"C:\\Users\\Yasser\\javaworkspace\\NetworksVirtualWifi\\resources"));
		Process p = pb.start();
		p.waitFor();

		System.out.println("**** OUTPUT");
		Scanner in = new Scanner(p.getInputStream());
		while (in.hasNext()) {
			String ln = in.nextLine();
			if (ln.startsWith(GlobalConstants.OUTPUT_TAG))
				System.out.println(ln.substring(GlobalConstants.OUTPUT_TAG
						.length() + 1));
		}
		in.close();

		System.out.println("**** ERROR");
		Scanner err = new Scanner(p.getErrorStream());
		while (err.hasNext()) {
			System.out.println(err.nextLine());
		}
		err.close();

		System.out.println("EXIT_VALUE " + p.exitValue());
		System.out.println("====================================");
	}

	public static void rewriteCreateBatFile(String batFileName,
			String newBatFileName, String ssidName, String password)
			throws IOException {
		// write modified version in tmp file
		Scanner in = new Scanner(new File(batFileName));
		PrintWriter out = new PrintWriter(new File(newBatFileName));
		while (in.hasNext()) {
			String ln = in.nextLine();
			if (ln.startsWith("set _name=")) {
				out.append("set _name=");
				out.append(ssidName);
			} else if (ln.startsWith("set _password=")) {
				out.append("set _password=");
				out.append(password);
			} else {
				out.append(ln);
			}
			out.append('\n');
		}
		in.close();
		out.close();
	}

	private static String getShareString(String connectionName,
			String secuirty_tag) {
		return "if Props.Name = \"" + connectionName
				+ "\" then Connection.EnableSharing " + secuirty_tag;
	}

	private static Set<String> getConnectedNetworks(String interfaceFileName)
			throws FileNotFoundException {
		Set<String> connectedNetworks = new HashSet<>();
		Scanner in = new Scanner(new File(interfaceFileName));
		while (in.hasNext()) {
			String ln = in.nextLine();
			if (ln.contains("Connected")) {
				final String typeTag = GlobalConstants.DEDICATED_INTERFACE_TYPE;
				int index = ln.indexOf(typeTag) + typeTag.length();
				while (ln.charAt(index) == ' ') {
					++index;
				}
				connectedNetworks.add(ln.substring(index));
			}
		}
		in.close();
		return connectedNetworks;
	}

	public static void rewriteShareVbsFile(String shareVbsSource,
			String shareVbsDestination, String interfaceBeforeStart,
			String interfaceAfterStart) throws FileNotFoundException {
		Set<String> publicNetworks = getConnectedNetworks(interfaceBeforeStart);
		Set<String> publicAndPrivateNetworks = getConnectedNetworks(interfaceAfterStart);

		Scanner in = new Scanner(new File(shareVbsSource));
		PrintWriter out = new PrintWriter(new File(shareVbsDestination));

		while (in.hasNext()) {
			String ln = in.nextLine();
			if (ln.startsWith(GlobalConstants.MODIFY_TAG)) {
				for (String connectionName : publicAndPrivateNetworks) {
					if (publicNetworks.contains(connectionName)) {
						// add to public
						out.write(getShareString(connectionName,
								"CONNECTION_PUBLIC") + '\n');
					} else {
						// add to private
						out.write(getShareString(connectionName,
								"CONNECTION_PRIVATE") + '\n');
					}
				}

			} else {
				out.write(ln + "\n");
			}
		}

		in.close();
		out.close();
	}

	public static void main(String[] args) throws FileNotFoundException {
		VirtualWifiUtilies.rewriteShareVbsFile("resources/original_share.vbs",
				"resources/share.vbs", "resources/"
						+ GlobalConstants.INTERFACE_BEFORE_START, "resources/"
						+ GlobalConstants.INTERFACE_AFTER_START);
	}
}
