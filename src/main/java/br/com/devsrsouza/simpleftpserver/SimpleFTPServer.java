package br.com.devsrsouza.simpleftpserver;

import com.guichaguri.minimalftp.FTPServer;
import com.guichaguri.minimalftp.impl.NativeFileSystem;

import java.io.File;
import java.io.IOException;

public class SimpleFTPServer {

    public static void main(String[] args) {

        if (args.length > 2) {

            String folder = args[0];
            String user_name = args[1];
            String password = args[2];

            File file = new File(folder);

            FTPServer server = new FTPServer();
            NativeFileSystem fs = new NativeFileSystem(file);

            BasicAuthenticator auth = new BasicAuthenticator(fs, user_name, password);

            server.setAuthenticator(auth);

            try {
                server.listen(21);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(server.getAddress());

            while (true) {
                try {
                    Thread.currentThread().sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("[folder] [user] [password]");
        }
    }

}
