package utils;

import java.io.File;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

    public static ServerSocket createTempServer() {
        ServerSocket server = null;
        while (server == null) {
            for (int port = 9001; port < 10000; port++) {
                try {
                    server = new ServerSocket(port);
                    break;
                } catch (Exception ex) {
                }
            }
        }

        return server;
    }

    public static boolean searchFiles(String path, String nameFile) {
        Path currentRelativePath = Paths.get("");
        String url = currentRelativePath.toAbsolutePath().toString() + path + nameFile;

        File f = new File(url);
        if (f.exists()) {
            return true;
        }
        return false;

    }

    public static long getFileSize(String path, String nameFile) {
        Path currentRelativePath = Paths.get("");
        String url = currentRelativePath.toAbsolutePath().toString() + path + nameFile;

        System.out.println(url);

        File f = new File(url);
        if (f.exists()) {
            return f.length();
        }
        return 0;

    }

}
