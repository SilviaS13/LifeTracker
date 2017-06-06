package Controller;

import com.sun.net.httpserver.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.security.*;
import java.security.cert.CertificateException;

import javax.net.ssl.*;

public class HttpsController {
    void start(int port) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException,
            UnrecoverableKeyException, InterruptedException, KeyManagementException {
        HttpsServer server = HttpsServer.create(new InetSocketAddress(port), 5);
        server.createContext("/api", new ApiHandler());

        char[] storepass = "storepass".toCharArray();
        char[] keypass = "serverpass".toCharArray();

        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(HttpsController.class.getResourceAsStream("server.jks"), storepass);

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, keypass);

        KeyStore ts = KeyStore.getInstance("JKS");
        ts.load(this.getClass().getResourceAsStream("servertrust.jks"), storepass);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ts);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        server.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
            public void configure (HttpsParameters params) {
                params.setWantClientAuth(true);
                params.setNeedClientAuth(true);
            }
        });
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}