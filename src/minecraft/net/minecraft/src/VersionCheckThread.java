package net.minecraft.src;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import net.minecraft.client.ClientBrandRetriever;

public class VersionCheckThread extends Thread
{
    public void run()
    {
        HttpURLConnection httpurlconnection = null;

        try
        {
            Config.dbg("Checking for new version");
            URL url = new URL("http://optifine.net/version/1.12/HD_U.txt");
            httpurlconnection = (HttpURLConnection)url.openConnection();

            if (Config.getGameSettings().snooperEnabled)
            {
                httpurlconnection.setRequestProperty("OF-MC-Version", "1.12");
                httpurlconnection.setRequestProperty("OF-MC-Brand", "" + ClientBrandRetriever.getClientModName());
                httpurlconnection.setRequestProperty("OF-Edition", "HD_U");
                httpurlconnection.setRequestProperty("OF-Release", "C4");
                httpurlconnection.setRequestProperty("OF-Java-Version", "" + System.getProperty("java.version"));
                httpurlconnection.setRequestProperty("OF-CpuCount", "" + Config.getAvailableProcessors());
                httpurlconnection.setRequestProperty("OF-OpenGL-Version", "" + Config.openGlVersion);
                httpurlconnection.setRequestProperty("OF-OpenGL-Vendor", "" + Config.openGlVendor);
            }

            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(false);
            httpurlconnection.connect();

            try
            {
                InputStream inputstream = httpurlconnection.getInputStream();
                String s = Config.readInputStream(inputstream);
                inputstream.close();
                String[] astring = Config.tokenize(s, "\n\r");

                if (astring.length >= 1)
                {
                    String s1 = astring[0].trim();
                    Config.dbg("Version found: " + s1);

                    if (Config.compareRelease(s1, "C4") <= 0)
                    {
                        return;
                    }

                    Config.setNewRelease(s1);
                    return;
                }
            }
            finally
            {
                if (httpurlconnection != null)
                {
                    httpurlconnection.disconnect();
                }
            }
        }
        catch (Exception exception)
        {
            Config.dbg(exception.getClass().getName() + ": " + exception.getMessage());
        }
    }
}
