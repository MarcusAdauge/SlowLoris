import java.util.Random;

public class SlowLoris extends Thread{
    private String host;
    private String method = "GET";
    private int port;
    private int timeout;
    private int tcpTimeout;
    private int connections;
    private boolean cache;
    private int threads = 50;
    private Random random = new Random();
    static int failed = 0, packets = 0, active = 0;

    private String request = "GET / HTTP/1.1\r\n"
            + "Host: " + host + "\r\n"
            + "User-Agent: Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36\r\n"
            + "Content-Length: 42\r\n";

    public SlowLoris(String host, int port, int timeout, int tcpto, int conn){
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.tcpTimeout = tcpto;
        this.connections = conn;
        //	this.cache = cache;
    }

    public void run(){

    }

}
