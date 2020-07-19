package net.minecraft.src;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Map;

public class HttpPipelineSender extends Thread
{
    private HttpPipelineConnection httpPipelineConnection = null;
    private static final String CRLF = "\r\n";
    private static Charset ASCII = Charset.forName("ASCII");

    public HttpPipelineSender(HttpPipelineConnection p_i56_1_)
    {
        super("HttpPipelineSender");
        this.httpPipelineConnection = p_i56_1_;
    }

    public void run()
    {
        HttpPipelineRequest httppipelinerequest = null;

        try
        {
            this.connect();

            while (!Thread.interrupted())
            {
                httppipelinerequest = this.httpPipelineConnection.getNextRequestSend();
                HttpRequest httprequest = httppipelinerequest.getHttpRequest();
                OutputStream outputstream = this.httpPipelineConnection.getOutputStream();
                this.writeRequest(httprequest, outputstream);
                this.httpPipelineConnection.onRequestSent(httppipelinerequest);
            }
        }
        catch (InterruptedException var4)
        {
            return;
        }
        catch (Exception exception)
        {
            this.httpPipelineConnection.onExceptionSend(httppipelinerequest, exception);
        }
    }

    private void connect() throws IOException
    {
        String s = this.httpPipelineConnection.getHost();
        int i = this.httpPipelineConnection.getPort();
        Proxy proxy = this.httpPipelineConnection.getProxy();
        Socket socket = new Socket(proxy);
        socket.connect(new InetSocketAddress(s, i), 5000);
        this.httpPipelineConnection.setSocket(socket);
    }

    private void writeRequest(HttpRequest p_writeRequest_1_, OutputStream p_writeRequest_2_) throws IOException
    {
        this.write(p_writeRequest_2_, p_writeRequest_1_.getMethod() + " " + p_writeRequest_1_.getFile() + " " + p_writeRequest_1_.getHttp() + "\r\n");
        Map<String, String> map = p_writeRequest_1_.getHeaders();

        for (String s : map.keySet())
        {
            String s1 = (String)p_writeRequest_1_.getHeaders().get(s);
            this.write(p_writeRequest_2_, s + ": " + s1 + "\r\n");
        }

        this.write(p_writeRequest_2_, "\r\n");
    }

    private void write(OutputStream p_write_1_, String p_write_2_) throws IOException
    {
        byte[] abyte = p_write_2_.getBytes(ASCII);
        p_write_1_.write(abyte);
    }
}
