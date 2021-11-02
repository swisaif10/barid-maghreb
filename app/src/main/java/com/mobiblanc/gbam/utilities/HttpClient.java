package com.mobiblanc.gbam.utilities;

import android.content.Context;
import android.util.Base64;

import com.loopj.android.http.AsyncHttpClient;

public class HttpClient {

    public static String basic;


    public static AsyncHttpClient getClient(Context mContext) {
       AsyncHttpClient asyncClient = new AsyncHttpClient();
        asyncClient.setTimeout(60000);

        String login    = "mobile_user";
        String password = "Orange_2018";

        String credentials = login + ":" + password;

        basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        //final String basic= "Basic bW9iaWxlX3VzZXI6T3JhbmdlXzIwMTg=";

        asyncClient.addHeader("Authorization" , basic);

        return  asyncClient;
    }

/*
    public static AsyncHttpClient getClient(Context mContext)
    {
        AsyncHttpClient asyncClient = new AsyncHttpClient();
        String login = "mobile_user";
        String password = "Orange_2018";

        String credentials = login + ":" + password;

        basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        //final String basic= "Basic bW9iaWxlX3VzZXI6T3JhbmdlXzIwMTg=";

        asyncClient.addHeader("Authorization", basic);

        try {

            // Load CAs from an InputStream
            // (could be from a resource or ByteArrayInputStream or ...)
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            // From https://www.washington.edu/itconnect/security/ca/load-der.crt
            InputStream caInput = new BufferedInputStream(new FileInputStream("load-der.crt"));
            Certificate ca;
            ca = cf.generateCertificate(caInput);
            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Create an SSLContext that uses our TrustManager
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);

            // Tell the URLConnection to use a SocketFactory from our SSLContext
            URL url = new URL("https://certs.cac.washington.edu/CAtest/");
            HttpsURLConnection urlConnection =
                    (HttpsURLConnection) url.openConnection();
            urlConnection.setSSLSocketFactory(context.getSocketFactory());

            MySSLSocketFactory socketFactory = new MySSLSocketFactory(keyStore);
            asyncClient.setSSLSocketFactory(socketFactory);

            //InputStream in = urlConnection.getInputStream();
            //copyInputStreamToOutputStream(in, System.out);


            // String login    = Utils.getFromSharedPreferencesRememberMe(LOGIN_REMEMBER_ME, mContext);
            // String password = Utils.getFromSharedPreferencesRememberMe(PASSWORD_REMEMBER_ME, mContext);

            //return asyncClient;
        }

        catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return asyncClient;

    }
*/
    public static String getBasic() {
        String basicAuth;
        String login = "mobile_user";
        String password = "Orange_2018";

        String credentials = login + ":" + password;
        basicAuth = basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        return basicAuth;
    }

}
