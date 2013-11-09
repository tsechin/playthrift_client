package playthrift_client;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.THttpClient;
import thrift_mathservice.MathService;

public class LoadTest extends Thread {
    private String url = null;
    private int nThreads = 0;
    private int reqPerThread = 0;
    private LoadTestSub[] subThreads = null;

    public LoadTest(int nThreads, int reqPerThread, String url) {
        this.nThreads = nThreads;
        this.reqPerThread = reqPerThread;
        this.url = url;
        this.subThreads = new LoadTestSub[nThreads];
    }

    public void run() {
        // initialize sub-threads
        for (int i=0; i<this.nThreads; i++) {
            this.subThreads[i] = new LoadTestSub(reqPerThread, url);
        }
        long tAllStart = System.currentTimeMillis();
        // start sub-threads
        for (int i=0; i<this.nThreads; i++) {
            this.subThreads[i].start();
        }
        // wait for sub-threads
        for (int i=0; i<this.nThreads; i++) {
            try {
                this.subThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long tAllEnd = System.currentTimeMillis();
        // aggregate statistics
        System.out.println("threads   : " + nThreads);
        System.out.println("req/thread: " + reqPerThread);
        long nReq = 0;
        long nReqSuccess = 0;
        long nReqFailure = 0;
        long totalTimeSuccess = 0;
        long totalTimeFailure = 0;
        for (int i=0; i<nThreads; i++) {
            nReq += subThreads[i].requestSuccess.length;
            for (int j=0; j<subThreads[i].requestSuccess.length; j++) {
                if (subThreads[i].requestSuccess[j]) {
                    nReqSuccess++;
                    totalTimeSuccess += subThreads[i].requestMillisec[j];
                } else {
                    nReqFailure++;
                    totalTimeFailure += subThreads[i].requestMillisec[j];
                }
            }
        }
        System.out.println("requests  : " + nReq);
        System.out.println("  success : " + nReqSuccess);
        System.out.println("  failure : " + nReqFailure);
        long tTotal = tAllEnd - tAllStart;
        System.out.println("total time taken = " + tTotal + " ms");
        System.out.println("success requests/sec = " + ((float)nReqSuccess / ((float)tTotal/1000) ));
        System.out.println(" => apparent msec/request = " + ((float)tTotal / nReqSuccess));


        System.out.println("Per-Request Stats:");
        if (nReqSuccess != 0)
            System.out.println("avg success request time: " + ((double)totalTimeSuccess/nReqSuccess) + " ms");
        if (nReqFailure != 0)
            System.out.println("avg failure request time: " + ((double)totalTimeFailure/nReqFailure) + " ms");

    }

    private class LoadTestSub extends Thread {
        private int reqPerThread = 0;
        private String url = null;
        public boolean[] requestSuccess = null;
        public long[] requestMillisec = null;
        public LoadTestSub(int reqPerThread, String url) {
            this.reqPerThread = reqPerThread;
            this.url = url;
            this.requestSuccess = new boolean[reqPerThread];
            this.requestMillisec = new long[reqPerThread];
        }
        public void run() {
            try {
                HttpClient hc = new DefaultHttpClient();
                THttpClient tHttpClient = new THttpClient(url, hc);
                TProtocol tProtocol = new TBinaryProtocol(tHttpClient);
                MathService.Client client = new MathService.Client(tProtocol);
                for (int iter=0; iter<this.reqPerThread; iter++) {
                    long tStart = System.currentTimeMillis();
                    int result = client.addFive(10);
                    long tEnd = System.currentTimeMillis();
                    requestSuccess[iter] = result == 15;
                    requestMillisec[iter] = tEnd - tStart;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
