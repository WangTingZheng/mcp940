package net.minecraft.util;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import net.minecraft.util.text.translation.I18n;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpUtil
{
    public static final ListeningExecutorService DOWNLOADER_EXECUTOR = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool((new ThreadFactoryBuilder()).setDaemon(true).setNameFormat("Downloader %d").build()));

    /** The number of download threads that we have started so far. */
    private static final AtomicInteger DOWNLOAD_THREADS_STARTED = new AtomicInteger(0);
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Builds an encoded HTTP POST content string from a string map
     */
    public static String buildPostString(Map<String, Object> data)
    {
        StringBuilder stringbuilder = new StringBuilder();

        for (Entry<String, Object> entry : data.entrySet())
        {
            if (stringbuilder.length() > 0)
            {
                stringbuilder.append('&');
            }

            try
            {
                stringbuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            }
            catch (UnsupportedEncodingException unsupportedencodingexception1)
            {
                unsupportedencodingexception1.printStackTrace();
            }

            if (entry.getValue() != null)
            {
                stringbuilder.append('=');

                try
                {
                    stringbuilder.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
                }
                catch (UnsupportedEncodingException unsupportedencodingexception)
                {
                    unsupportedencodingexception.printStackTrace();
                }
            }
        }

        return stringbuilder.toString();
    }

    /**
     * Sends a POST to the given URL using the map as the POST args
     */
    public static String postMap(URL url, Map<String, Object> data, boolean skipLoggingErrors, @Nullable Proxy proxyIn)
    {
        return post(url, buildPostString(data), skipLoggingErrors, proxyIn);
    }

    /**
     * Sends a POST to the given URL
     */
    private static String post(URL url, String content, boolean skipLoggingErrors, @Nullable Proxy p_151225_3_)
    {
        try
        {
            if (p_151225_3_ == null)
            {
                p_151225_3_ = Proxy.NO_PROXY;
            }

            HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection(p_151225_3_);
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpurlconnection.setRequestProperty("Content-Length", "" + content.getBytes().length);
            httpurlconnection.setRequestProperty("Content-Language", "en-US");
            httpurlconnection.setUseCaches(false);
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(true);
            DataOutputStream dataoutputstream = new DataOutputStream(httpurlconnection.getOutputStream());
            dataoutputstream.writeBytes(content);
            dataoutputstream.flush();
            dataoutputstream.close();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
            StringBuffer stringbuffer = new StringBuffer();
            String s;

            while ((s = bufferedreader.readLine()) != null)
            {
                stringbuffer.append(s);
                stringbuffer.append('\r');
            }

            bufferedreader.close();
            return stringbuffer.toString();
        }
        catch (Exception exception)
        {
            if (!skipLoggingErrors)
            {
                LOGGER.error("Could not post to {}", url, exception);
            }

            return "";
        }
    }

    public static ListenableFuture<Object> downloadResourcePack(final File saveFile, final String packUrl, final Map<String, String> p_180192_2_, final int maxSize, @Nullable final IProgressUpdate p_180192_4_, final Proxy p_180192_5_)
    {
        ListenableFuture<?> listenablefuture = DOWNLOADER_EXECUTOR.submit(new Runnable()
        {
            public void run()
            {
                HttpURLConnection httpurlconnection = null;
                InputStream inputstream = null;
                OutputStream outputstream = null;

                if (p_180192_4_ != null)
                {
                    p_180192_4_.resetProgressAndMessage(I18n.translateToLocal("resourcepack.downloading"));
                    p_180192_4_.displayLoadingString(I18n.translateToLocal("resourcepack.requesting"));
                }

                try
                {
                    try
                    {
                        byte[] abyte = new byte[4096];
                        URL url = new URL(packUrl);
                        httpurlconnection = (HttpURLConnection)url.openConnection(p_180192_5_);
                        httpurlconnection.setInstanceFollowRedirects(true);
                        float f = 0.0F;
                        float f1 = (float)p_180192_2_.entrySet().size();

                        for (Entry<String, String> entry : p_180192_2_.entrySet())
                        {
                            httpurlconnection.setRequestProperty(entry.getKey(), entry.getValue());

                            if (p_180192_4_ != null)
                            {
                                p_180192_4_.setLoadingProgress((int)(++f / f1 * 100.0F));
                            }
                        }

                        inputstream = httpurlconnection.getInputStream();
                        f1 = (float)httpurlconnection.getContentLength();
                        int i = httpurlconnection.getContentLength();

                        if (p_180192_4_ != null)
                        {
                            p_180192_4_.displayLoadingString(I18n.translateToLocalFormatted("resourcepack.progress", String.format("%.2f", f1 / 1000.0F / 1000.0F)));
                        }

                        if (saveFile.exists())
                        {
                            long j = saveFile.length();

                            if (j == (long)i)
                            {
                                if (p_180192_4_ != null)
                                {
                                    p_180192_4_.setDoneWorking();
                                }

                                return;
                            }

                            HttpUtil.LOGGER.warn("Deleting {} as it does not match what we currently have ({} vs our {}).", saveFile, Integer.valueOf(i), Long.valueOf(j));
                            FileUtils.deleteQuietly(saveFile);
                        }
                        else if (saveFile.getParentFile() != null)
                        {
                            saveFile.getParentFile().mkdirs();
                        }

                        outputstream = new DataOutputStream(new FileOutputStream(saveFile));

                        if (maxSize > 0 && f1 > (float)maxSize)
                        {
                            if (p_180192_4_ != null)
                            {
                                p_180192_4_.setDoneWorking();
                            }

                            throw new IOException("Filesize is bigger than maximum allowed (file is " + f + ", limit is " + maxSize + ")");
                        }

                        int k;

                        while ((k = inputstream.read(abyte)) >= 0)
                        {
                            f += (float)k;

                            if (p_180192_4_ != null)
                            {
                                p_180192_4_.setLoadingProgress((int)(f / f1 * 100.0F));
                            }

                            if (maxSize > 0 && f > (float)maxSize)
                            {
                                if (p_180192_4_ != null)
                                {
                                    p_180192_4_.setDoneWorking();
                                }

                                throw new IOException("Filesize was bigger than maximum allowed (got >= " + f + ", limit was " + maxSize + ")");
                            }

                            if (Thread.interrupted())
                            {
                                HttpUtil.LOGGER.error("INTERRUPTED");

                                if (p_180192_4_ != null)
                                {
                                    p_180192_4_.setDoneWorking();
                                }

                                return;
                            }

                            outputstream.write(abyte, 0, k);
                        }

                        if (p_180192_4_ != null)
                        {
                            p_180192_4_.setDoneWorking();
                            return;
                        }
                    }
                    catch (Throwable throwable)
                    {
                        throwable.printStackTrace();

                        if (httpurlconnection != null)
                        {
                            InputStream inputstream1 = httpurlconnection.getErrorStream();

                            try
                            {
                                HttpUtil.LOGGER.error(IOUtils.toString(inputstream1));
                            }
                            catch (IOException ioexception)
                            {
                                ioexception.printStackTrace();
                            }
                        }

                        if (p_180192_4_ != null)
                        {
                            p_180192_4_.setDoneWorking();
                            return;
                        }
                    }
                }
                finally
                {
                    IOUtils.closeQuietly(inputstream);
                    IOUtils.closeQuietly(outputstream);
                }
            }
        });
        return (ListenableFuture<Object>) listenablefuture;
    }

    public static int getSuitableLanPort() throws IOException
    {
        ServerSocket serversocket = null;
        int i = -1;

        try
        {
            serversocket = new ServerSocket(0);
            i = serversocket.getLocalPort();
        }
        finally
        {
            try
            {
                if (serversocket != null)
                {
                    serversocket.close();
                }
            }
            catch (IOException var8)
            {
                ;
            }
        }

        return i;
    }
}
