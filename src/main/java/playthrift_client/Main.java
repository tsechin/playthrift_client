package playthrift_client;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import thrift_mathservice.MathService;

public class Main {
    private static final String schemeserverport = "http://localhost:9000";
    private static final String url = "/math";

	public static void main(String args[]) {
        try {

            System.out.println("playthrift client starting");

            THttpClient tHttpClient = new THttpClient(schemeserverport+url);
            TProtocol tProtocol = new TBinaryProtocol(tHttpClient);

            MathService.Client client = new MathService.Client(tProtocol);

            System.out.println("10+5=" + client.addFive(10));

            System.out.println("playthrift client done");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
