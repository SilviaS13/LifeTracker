import Controller.HttpsController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;

import javax.net.ssl.*;

public class HTTPSClient {
    public void testTwoWayAuthentication()
            throws CertificateException, InterruptedException, UnrecoverableKeyException, NoSuchAlgorithmException,
            IOException, KeyManagementException, KeyStoreException {

        URL url = new URL("https://localhost:8000/");
        HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
        con.setRequestMethod( "GET" );

        SSLContext sslContext = SSLContext.getInstance("TLS");

        char[]  passphrase = "storepass".toCharArray();
        char[]  keypass = "serverpass".toCharArray();

        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(HttpsController.class.getResourceAsStream("client.jks"), passphrase);

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, keypass);

        KeyStore ts = KeyStore.getInstance("JKS");
        ts.load(this.getClass().getResourceAsStream("clienttrust.jks"), passphrase);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ts);

        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return s.equals(sslSession.getPeerHost());
            }
        };
        con.setHostnameVerifier(hostnameVerifier);


        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        con.setSSLSocketFactory(sslContext.getSocketFactory());

        int responseCode = con.getResponseCode();
        InputStream inputStream;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            inputStream = con.getInputStream();
        } else {
            inputStream = con.getErrorStream();
        }

        // Process the response
        BufferedReader reader;
        String line = null;
        reader = new BufferedReader( new InputStreamReader( inputStream ) );
        while( ( line = reader.readLine() ) != null )
        {
            System.out.println( line );
        }

        inputStream.close();
    }
}