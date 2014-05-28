import java.io.IOException;
import java.util.Scanner;

import model.VirtualWifi;

public class Main {
	public static void main(String[] args) throws IOException,
			InterruptedException {
		VirtualWifi wifi = VirtualWifi.getInstance("ccShit", "44444444");
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int query = in.nextInt();
			System.out.println(query);
			switch (query) {
			case 0:
				wifi.start();
				continue;
			case 1:
				wifi.share();
				continue;
			case 2:
				wifi.stop();
				continue;
			default:
				break;
			}
		}
		in.close();
	}

}
