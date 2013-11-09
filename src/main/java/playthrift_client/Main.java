package playthrift_client;

public class Main {
    private static final String url = "http://localhost:9000/math";

	public static void main(String args[]) {
        try {

            System.out.println("playthrift client starting");
            LoadTest lt = new LoadTest(10, 10000, url);
            lt.start();
            lt.join();
            System.out.println("playthrift client done");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
