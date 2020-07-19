package net.minecraft.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import net.minecraft.client.Minecraft;

public class HttpUtils
{
    public static final String SERVER_URL = "http://s.optifine.net";
    public static final String POST_URL = "http://optifine.net";

    public static byte[] get(String p_get_0_) throws IOException
    {
        HttpURLConnection httpurlconnection = null;
        byte[] abyte1;

        try
        {
            URL url = new URL(p_get_0_);
            httpurlconnection = (HttpURLConnection)url.openConnection(Minecraft.getMinecraft().getProxy());
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(false);
            httpurlconnection.connect();

            if (httpurlconnection.getResponseCode() / 100 != 2)
            {
                if (httpurlconnection.getErrorStream() != null)
                {
                    Config.readAll(httpurlconnection.getErrorStream());
                }

                throw new IOException("HTTP response: " + httpurlconnection.getResponseCode());
            }

            InputStream inputstream = httpurlconnection.getInputStream();
            byte[] abyte = new byte[httpurlconnection.getContentLength()];
            int i = 0;

            while (true)
            {
                int j = inputstream.read(abyte, i, abyte.length - i);

                if (j < 0)
                {
                    throw new IOException("Input stream closed: " + p_get_0_);
                }

                i += j;

                if (i >= abyte.length)
                {
                    break;
                }
            }

            abyte1 = abyte;
        }
        finally
        {
            if (httpurlconnection != null)
            {
                httpurlconnection.disconnect();
            }
        }

        return abyte1;
    }

    public static String post(String p_post_0_, Map p_post_1_, byte[] p_post_2_) throws IOException
    {
        HttpURLConnection httpurlconnection = null;
        String s3;

        try
        {
            URL url = new URL(p_post_0_);
            httpurlconnection = (HttpURLConnection)url.openConnection(Minecraft.getMinecraft().getProxy());
            httpurlconnection.setRequestMethod("POST");

            if (p_post_1_ != null)
            {
                for (Object s : p_post_1_.keySet())
                {
                    String s1 = "" + p_post_1_.get(s);
                    httpurlconnection.setRequestProperty((String) s, s1);
                }
            }

            httpurlconnection.setRequestProperty("Content-Type", "text/plain");
            httpurlconnection.setRequestProperty("Content-Length", "" + p_post_2_.length);
            httpurlconnection.setRequestProperty("Content-Language", "en-US");
            httpurlconnection.setUseCaches(false);
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(true);
            OutputStream outputstream = httpurlconnection.getOutputStream();
            outputstream.write(p_post_2_);
            outputstream.flush();
            outputstream.close();
            InputStream inputstream = httpurlconnection.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream, "ASCII");
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
            StringBuffer stringbuffer = new StringBuffer();
            String s2;

            while ((s2 = bufferedreader.readLine()) != null)
            {
                stringbuffer.append(s2);
                stringbuffer.append('\r');
            }

            bufferedreader.close();
            s3 = stringbuffer.toString();
        }
        finally
        {
            if (httpurlconnection != null)
            {
                httpurlconnection.disconnect();
            }
        }

        return s3;
    }
}
