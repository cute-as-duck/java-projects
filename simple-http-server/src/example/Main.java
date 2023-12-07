package example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new HttpServer(8080).start();
    }
}
