package br.com.devsrsouza.simpleftpserver;

import com.guichaguri.minimalftp.FTPConnection;
import com.guichaguri.minimalftp.api.IFileSystem;
import com.guichaguri.minimalftp.api.IUserAuthenticator;
import com.guichaguri.minimalftp.impl.NativeFileSystem;

import java.io.File;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.util.Arrays;

public class BasicAuthenticator implements IUserAuthenticator {

    private IFileSystem fileSystem;
    private String username;
    private byte[] password;


    public BasicAuthenticator(IFileSystem fileSystem, String username, String password) {
        this.fileSystem = fileSystem;
        this.username = username;
        this.password = toMD5(password);
    }

    private byte[] toMD5(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(pass.getBytes("UTF-8"));
        } catch(Exception ex) {
            return pass.getBytes();
        }
    }

    @Override
    public boolean needsUsername(FTPConnection con) {
        return true;
    }

    @Override
    public boolean needsPassword(FTPConnection con, String username, InetAddress host) {
        return true;
    }

    @Override
    public IFileSystem authenticate(FTPConnection con, InetAddress host, String username, String password) throws AuthException {
        if(username.equalsIgnoreCase(this.username)) {
            byte[] inputPass = toMD5(password);
            if(Arrays.equals(this.password, inputPass)) {
                return fileSystem;
            }
        }
        throw new AuthException();
    }
}
