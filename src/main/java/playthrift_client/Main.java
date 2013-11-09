package playthrift_client;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import thrift_mathservice.MathService;

public class Main {
    private static final String url = "http://localhost:9000/math";
                                                            s
	public static void main(String args[]) {
        try {

            System.out.println("playthrift client starting");

            HttpClient hc = new DefaultHttpClient();
            THttpClient tHttpClient = new THttpClient(url, hc);
            TProtocol tProtocol = new TBinaryProtocol(tHttpClient);

            MathService.Client client = new MathService.Client(tProtocol);

            System.out.println("10+5=" + client.addFive(10));

            System.out.println("playthrift client done");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
