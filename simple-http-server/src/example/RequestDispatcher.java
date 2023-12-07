package example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class RequestDispatcher {
    private final BufferedReader reader;
    private final BufferedWriter writer;


    public RequestDispatcher(BufferedReader reader, BufferedWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public void dispatch() throws IOException{
        Request request = parseRequest();
        Response response = new Response();

        if (request == null) {
            return;
        }

        try {
            new Servlet().doService(request, response);

            writeStatus(response.getStatus(), response.getDescription());

            if (response.getContent() != null) {
                writer.write("Content-Type: text/html\r\n");
                writer.write("Content-Length: " + response.getContent().length() + "\r\n");
                writer.write("\r\n");
                writer.write(response.getContent() + "\r\n");
            }
        } catch (IOException e) {
            writeStatus("500", "Internal server error");
        }
        writer.flush();
    }

    private Request parseRequest() throws IOException{

        String header = reader.readLine();
        if (header != null) {
            String[] data = header.split(" ");
            return new Request(data[0], data[1]);
        }
        return null;
    }

    private void writeStatus(String status, String message) {
        try {
            writer.write("HTTP/1.1 " + status + " " + message + "\r\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
