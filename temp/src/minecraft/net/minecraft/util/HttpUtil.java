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

public class HttpUtil {
   public static final ListeningExecutorService field_180193_a = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool((new ThreadFactoryBuilder()).setDaemon(true).setNameFormat("Downloader %d").build()));
   private static final AtomicInteger field_151228_a = new AtomicInteger(0);
   private static final Logger field_151227_b = LogManager.getLogger();

   public static String func_76179_a(Map<String, Object> p_76179_0_) {
      StringBuilder stringbuilder = new StringBuilder();

      for(Entry<String, Object> entry : p_76179_0_.entrySet()) {
         if (stringbuilder.length() > 0) {
            stringbuilder.append('&');
         }

         try {
            stringbuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
         } catch (UnsupportedEncodingException unsupportedencodingexception1) {
            unsupportedencodingexception1.printStackTrace();
         }

         if (entry.getValue() != null) {
            stringbuilder.append('=');

            try {
               stringbuilder.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
            } catch (UnsupportedEncodingException unsupportedencodingexception) {
               unsupportedencodingexception.printStackTrace();
            }
         }
      }

      return stringbuilder.toString();
   }

   public static String func_151226_a(URL p_151226_0_, Map<String, Object> p_151226_1_, boolean p_151226_2_, @Nullable Proxy p_151226_3_) {
      return func_151225_a(p_151226_0_, func_76179_a(p_151226_1_), p_151226_2_, p_151226_3_);
   }

   private static String func_151225_a(URL p_151225_0_, String p_151225_1_, boolean p_151225_2_, @Nullable Proxy p_151225_3_) {
      try {
         if (p_151225_3_ == null) {
            p_151225_3_ = Proxy.NO_PROXY;
         }

         HttpURLConnection httpurlconnection = (HttpURLConnection)p_151225_0_.openConnection(p_151225_3_);
         httpurlconnection.setRequestMethod("POST");
         httpurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
         httpurlconnection.setRequestProperty("Content-Length", "" + p_151225_1_.getBytes().length);
         httpurlconnection.setRequestProperty("Content-Language", "en-US");
         httpurlconnection.setUseCaches(false);
         httpurlconnection.setDoInput(true);
         httpurlconnection.setDoOutput(true);
         DataOutputStream dataoutputstream = new DataOutputStream(httpurlconnection.getOutputStream());
         dataoutputstream.writeBytes(p_151225_1_);
         dataoutputstream.flush();
         dataoutputstream.close();
         BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
         StringBuffer stringbuffer = new StringBuffer();

         String s;
         while((s = bufferedreader.readLine()) != null) {
            stringbuffer.append(s);
            stringbuffer.append('\r');
         }

         bufferedreader.close();
         return stringbuffer.toString();
      } catch (Exception exception) {
         if (!p_151225_2_) {
            field_151227_b.error("Could not post to {}", p_151225_0_, exception);
         }

         return "";
      }
   }

   public static ListenableFuture<Object> func_180192_a(final File p_180192_0_, final String p_180192_1_, final Map<String, String> p_180192_2_, final int p_180192_3_, @Nullable final IProgressUpdate p_180192_4_, final Proxy p_180192_5_) {
      ListenableFuture<?> listenablefuture = field_180193_a.submit(new Runnable() {
         public void run() {
            HttpURLConnection httpurlconnection = null;
            InputStream inputstream = null;
            OutputStream outputstream = null;
            if (p_180192_4_ != null) {
               p_180192_4_.func_73721_b(I18n.func_74838_a("resourcepack.downloading"));
               p_180192_4_.func_73719_c(I18n.func_74838_a("resourcepack.requesting"));
            }

            try {
               try {
                  byte[] abyte = new byte[4096];
                  URL url = new URL(p_180192_1_);
                  httpurlconnection = (HttpURLConnection)url.openConnection(p_180192_5_);
                  httpurlconnection.setInstanceFollowRedirects(true);
                  float f = 0.0F;
                  float f1 = (float)p_180192_2_.entrySet().size();

                  for(Entry<String, String> entry : p_180192_2_.entrySet()) {
                     httpurlconnection.setRequestProperty(entry.getKey(), entry.getValue());
                     if (p_180192_4_ != null) {
                        p_180192_4_.func_73718_a((int)(++f / f1 * 100.0F));
                     }
                  }

                  inputstream = httpurlconnection.getInputStream();
                  f1 = (float)httpurlconnection.getContentLength();
                  int i = httpurlconnection.getContentLength();
                  if (p_180192_4_ != null) {
                     p_180192_4_.func_73719_c(I18n.func_74837_a("resourcepack.progress", String.format("%.2f", f1 / 1000.0F / 1000.0F)));
                  }

                  if (p_180192_0_.exists()) {
                     long j = p_180192_0_.length();
                     if (j == (long)i) {
                        if (p_180192_4_ != null) {
                           p_180192_4_.func_146586_a();
                        }

                        return;
                     }

                     HttpUtil.field_151227_b.warn("Deleting {} as it does not match what we currently have ({} vs our {}).", p_180192_0_, Integer.valueOf(i), Long.valueOf(j));
                     FileUtils.deleteQuietly(p_180192_0_);
                  } else if (p_180192_0_.getParentFile() != null) {
                     p_180192_0_.getParentFile().mkdirs();
                  }

                  outputstream = new DataOutputStream(new FileOutputStream(p_180192_0_));
                  if (p_180192_3_ > 0 && f1 > (float)p_180192_3_) {
                     if (p_180192_4_ != null) {
                        p_180192_4_.func_146586_a();
                     }

                     throw new IOException("Filesize is bigger than maximum allowed (file is " + f + ", limit is " + p_180192_3_ + ")");
                  }

                  int k;
                  while((k = inputstream.read(abyte)) >= 0) {
                     f += (float)k;
                     if (p_180192_4_ != null) {
                        p_180192_4_.func_73718_a((int)(f / f1 * 100.0F));
                     }

                     if (p_180192_3_ > 0 && f > (float)p_180192_3_) {
                        if (p_180192_4_ != null) {
                           p_180192_4_.func_146586_a();
                        }

                        throw new IOException("Filesize was bigger than maximum allowed (got >= " + f + ", limit was " + p_180192_3_ + ")");
                     }

                     if (Thread.interrupted()) {
                        HttpUtil.field_151227_b.error("INTERRUPTED");
                        if (p_180192_4_ != null) {
                           p_180192_4_.func_146586_a();
                        }

                        return;
                     }

                     outputstream.write(abyte, 0, k);
                  }

                  if (p_180192_4_ != null) {
                     p_180192_4_.func_146586_a();
                     return;
                  }
               } catch (Throwable throwable) {
                  throwable.printStackTrace();
                  if (httpurlconnection != null) {
                     InputStream inputstream1 = httpurlconnection.getErrorStream();

                     try {
                        HttpUtil.field_151227_b.error(IOUtils.toString(inputstream1));
                     } catch (IOException ioexception) {
                        ioexception.printStackTrace();
                     }
                  }

                  if (p_180192_4_ != null) {
                     p_180192_4_.func_146586_a();
                     return;
                  }
               }

            } finally {
               IOUtils.closeQuietly(inputstream);
               IOUtils.closeQuietly(outputstream);
            }
         }
      });
      return listenablefuture;
   }

   public static int func_76181_a() throws IOException {
      ServerSocket serversocket = null;
      int i = -1;

      try {
         serversocket = new ServerSocket(0);
         i = serversocket.getLocalPort();
      } finally {
         try {
            if (serversocket != null) {
               serversocket.close();
            }
         } catch (IOException var8) {
            ;
         }

      }

      return i;
   }
}
