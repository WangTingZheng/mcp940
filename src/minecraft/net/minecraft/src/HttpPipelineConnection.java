package net.minecraft.src;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class HttpPipelineConnection
{
    private String host;
    private int port;
    private Proxy proxy;
    private List<HttpPipelineRequest> listRequests;
    private List<HttpPipelineRequest> listRequestsSend;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private HttpPipelineSender httpPipelineSender;
    private HttpPipelineReceiver httpPipelineReceiver;
    private int countRequests;
    private boolean responseReceived;
    private long keepaliveTimeoutMs;
    private int keepaliveMaxCount;
    private long timeLastActivityMs;
    private boolean terminated;
    private static final String LF = "\n";
    public static final int TIMEOUT_CONNECT_MS = 5000;
    public static final int TIMEOUT_READ_MS = 5000;
    private static final Pattern patternFullUrl = Pattern.compile("^[a-zA-Z]+://.*");

    public HttpPipelineConnection(String p_i52_1_, int p_i52_2_)
    {
        this(p_i52_1_, p_i52_2_, Proxy.NO_PROXY);
    }

    public HttpPipelineConnection(String p_i53_1_, int p_i53_2_, Proxy p_i53_3_)
    {
        this.host = null;
        this.port = 0;
        this.proxy = Proxy.NO_PROXY;
        this.listRequests = new LinkedList<HttpPipelineRequest>();
        this.listRequestsSend = new LinkedList<HttpPipelineRequest>();
        this.socket = null;
        this.inputStream = null;
        this.outputStream = null;
        this.httpPipelineSender = null;
        this.httpPipelineReceiver = null;
        this.countRequests = 0;
        this.responseReceived = false;
        this.keepaliveTimeoutMs = 5000L;
        this.keepaliveMaxCount = 1000;
        this.timeLastActivityMs = System.currentTimeMillis();
        this.terminated = false;
        this.host = p_i53_1_;
        this.port = p_i53_2_;
        this.proxy = p_i53_3_;
        this.httpPipelineSender = new HttpPipelineSender(this);
        this.httpPipelineSender.start();
        this.httpPipelineReceiver = new HttpPipelineReceiver(this);
        this.httpPipelineReceiver.start();
    }

    public synchronized boolean addRequest(HttpPipelineRequest p_addRequest_1_)
    {
        if (this.isClosed())
        {
            return false;
        }
        else
        {
            this.addRequest(p_addRequest_1_, this.listRequests);
            this.addRequest(p_addRequest_1_, this.listRequestsSend);
            ++this.countRequests;
            return true;
        }
    }

    private void addRequest(HttpPipelineRequest p_addRequest_1_, List<HttpPipelineRequest> p_addRequest_2_)
    {
        p_addRequest_2_.add(p_addRequest_1_);
        this.notifyAll();
    }

    public synchronized void setSocket(Socket p_setSocket_1_) throws IOException
    {
        if (!this.terminated)
        {
            if (this.socket != null)
            {
                throw new IllegalArgumentException("Already connected");
            }
            else
            {
                this.socket = p_setSocket_1_;
                this.socket.setTcpNoDelay(true);
                this.inputStream = this.socket.getInputStream();
                this.outputStream = new BufferedOutputStream(this.socket.getOutputStream());
                this.onActivity();
                this.notifyAll();
            }
        }
    }

    public synchronized OutputStream getOutputStream() throws IOException, InterruptedException
    {
        while (this.outputStream == null)
        {
            this.checkTimeout();
            this.wait(1000L);
        }

        return this.outputStream;
    }

    public synchronized InputStream getInputStream() throws IOException, InterruptedException
    {
        while (this.inputStream == null)
        {
            this.checkTimeout();
            this.wait(1000L);
        }

        return this.inputStream;
    }

    public synchronized HttpPipelineRequest getNextRequestSend() throws InterruptedException, IOException
    {
        if (this.listRequestsSend.size() <= 0 && this.outputStream != null)
        {
            this.outputStream.flush();
        }

        return this.getNextRequest(this.listRequestsSend, true);
    }

    public synchronized HttpPipelineRequest getNextRequestReceive() throws InterruptedException
    {
        return this.getNextRequest(this.listRequests, false);
    }

    private HttpPipelineRequest getNextRequest(List<HttpPipelineRequest> p_getNextRequest_1_, boolean p_getNextRequest_2_) throws InterruptedException
    {
        while (p_getNextRequest_1_.size() <= 0)
        {
            this.checkTimeout();
            this.wait(1000L);
        }

        this.onActivity();

        if (p_getNextRequest_2_)
        {
            return p_getNextRequest_1_.remove(0);
        }
        else
        {
            return p_getNextRequest_1_.get(0);
        }
    }

    private void checkTimeout()
    {
        if (this.socket != null)
        {
            long i = this.keepaliveTimeoutMs;

            if (this.listRequests.size() > 0)
            {
                i = 5000L;
            }

            long j = System.currentTimeMillis();

            if (j > this.timeLastActivityMs + i)
            {
                this.terminate(new InterruptedException("Timeout " + i));
            }
        }
    }

    private void onActivity()
    {
        this.timeLastActivityMs = System.currentTimeMillis();
    }

    public synchronized void onRequestSent(HttpPipelineRequest p_onRequestSent_1_)
    {
        if (!this.terminated)
        {
            this.onActivity();
        }
    }

    public synchronized void onResponseReceived(HttpPipelineRequest p_onResponseReceived_1_, HttpResponse p_onResponseReceived_2_)
    {
        if (!this.terminated)
        {
            this.responseReceived = true;
            this.onActivity();

            if (this.listRequests.size() > 0 && this.listRequests.get(0) == p_onResponseReceived_1_)
            {
                this.listRequests.remove(0);
                p_onResponseReceived_1_.setClosed(true);
                String s = p_onResponseReceived_2_.getHeader("Location");

                if (p_onResponseReceived_2_.getStatus() / 100 == 3 && s != null && p_onResponseReceived_1_.getHttpRequest().getRedirects() < 5)
                {
                    try
                    {
                        s = this.normalizeUrl(s, p_onResponseReceived_1_.getHttpRequest());
                        HttpRequest httprequest = HttpPipeline.makeRequest(s, p_onResponseReceived_1_.getHttpRequest().getProxy());
                        httprequest.setRedirects(p_onResponseReceived_1_.getHttpRequest().getRedirects() + 1);
                        HttpPipelineRequest httppipelinerequest = new HttpPipelineRequest(httprequest, p_onResponseReceived_1_.getHttpListener());
                        HttpPipeline.addRequest(httppipelinerequest);
                    }
                    catch (IOException ioexception)
                    {
                        p_onResponseReceived_1_.getHttpListener().failed(p_onResponseReceived_1_.getHttpRequest(), ioexception);
                    }
                }
                else
                {
                    HttpListener httplistener = p_onResponseReceived_1_.getHttpListener();
                    httplistener.finished(p_onResponseReceived_1_.getHttpRequest(), p_onResponseReceived_2_);
                }

                this.checkResponseHeader(p_onResponseReceived_2_);
            }
            else
            {
                throw new IllegalArgumentException("Response out of order: " + p_onResponseReceived_1_);
            }
        }
    }

    private String normalizeUrl(String p_normalizeUrl_1_, HttpRequest p_normalizeUrl_2_)
    {
        if (patternFullUrl.matcher(p_normalizeUrl_1_).matches())
        {
            return p_normalizeUrl_1_;
        }
        else if (p_normalizeUrl_1_.startsWith("//"))
        {
            return "http:" + p_normalizeUrl_1_;
        }
        else
        {
            String s = p_normalizeUrl_2_.getHost();

            if (p_normalizeUrl_2_.getPort() != 80)
            {
                s = s + ":" + p_normalizeUrl_2_.getPort();
            }

            if (p_normalizeUrl_1_.startsWith("/"))
            {
                return "http://" + s + p_normalizeUrl_1_;
            }
            else
            {
                String s1 = p_normalizeUrl_2_.getFile();
                int i = s1.lastIndexOf("/");
                return i >= 0 ? "http://" + s + s1.substring(0, i + 1) + p_normalizeUrl_1_ : "http://" + s + "/" + p_normalizeUrl_1_;
            }
        }
    }

    private void checkResponseHeader(HttpResponse p_checkResponseHeader_1_)
    {
        String s = p_checkResponseHeader_1_.getHeader("Connection");

        if (s != null && !s.toLowerCase().equals("keep-alive"))
        {
            this.terminate(new EOFException("Connection not keep-alive"));
        }

        String s1 = p_checkResponseHeader_1_.getHeader("Keep-Alive");

        if (s1 != null)
        {
            String[] astring = Config.tokenize(s1, ",;");

            for (int i = 0; i < astring.length; ++i)
            {
                String s2 = astring[i];
                String[] astring1 = this.split(s2, '=');

                if (astring1.length >= 2)
                {
                    if (astring1[0].equals("timeout"))
                    {
                        int j = Config.parseInt(astring1[1], -1);

                        if (j > 0)
                        {
                            this.keepaliveTimeoutMs = (long)(j * 1000);
                        }
                    }

                    if (astring1[0].equals("max"))
                    {
                        int k = Config.parseInt(astring1[1], -1);

                        if (k > 0)
                        {
                            this.keepaliveMaxCount = k;
                        }
                    }
                }
            }
        }
    }

    private String[] split(String p_split_1_, char p_split_2_)
    {
        int i = p_split_1_.indexOf(p_split_2_);

        if (i < 0)
        {
            return new String[] {p_split_1_};
        }
        else
        {
            String s = p_split_1_.substring(0, i);
            String s1 = p_split_1_.substring(i + 1);
            return new String[] {s, s1};
        }
    }

    public synchronized void onExceptionSend(HttpPipelineRequest p_onExceptionSend_1_, Exception p_onExceptionSend_2_)
    {
        this.terminate(p_onExceptionSend_2_);
    }

    public synchronized void onExceptionReceive(HttpPipelineRequest p_onExceptionReceive_1_, Exception p_onExceptionReceive_2_)
    {
        this.terminate(p_onExceptionReceive_2_);
    }

    private synchronized void terminate(Exception p_terminate_1_)
    {
        if (!this.terminated)
        {
            this.terminated = true;
            this.terminateRequests(p_terminate_1_);

            if (this.httpPipelineSender != null)
            {
                this.httpPipelineSender.interrupt();
            }

            if (this.httpPipelineReceiver != null)
            {
                this.httpPipelineReceiver.interrupt();
            }

            try
            {
                if (this.socket != null)
                {
                    this.socket.close();
                }
            }
            catch (IOException var3)
            {
                ;
            }

            this.socket = null;
            this.inputStream = null;
            this.outputStream = null;
        }
    }

    private void terminateRequests(Exception p_terminateRequests_1_)
    {
        if (this.listRequests.size() > 0)
        {
            if (!this.responseReceived)
            {
                HttpPipelineRequest httppipelinerequest = this.listRequests.remove(0);
                httppipelinerequest.getHttpListener().failed(httppipelinerequest.getHttpRequest(), p_terminateRequests_1_);
                httppipelinerequest.setClosed(true);
            }

            while (this.listRequests.size() > 0)
            {
                HttpPipelineRequest httppipelinerequest1 = this.listRequests.remove(0);
                HttpPipeline.addRequest(httppipelinerequest1);
            }
        }
    }

    public synchronized boolean isClosed()
    {
        if (this.terminated)
        {
            return true;
        }
        else
        {
            return this.countRequests >= this.keepaliveMaxCount;
        }
    }

    public int getCountRequests()
    {
        return this.countRequests;
    }

    public synchronized boolean hasActiveRequests()
    {
        return this.listRequests.size() > 0;
    }

    public String getHost()
    {
        return this.host;
    }

    public int getPort()
    {
        return this.port;
    }

    public Proxy getProxy()
    {
        return this.proxy;
    }
}
