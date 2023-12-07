package example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Servlet {
    public void doService(Request request, Response response) {
        String dir = "./src/resources";
        Path contentPath = Paths.get(dir + request.getUrl());

        if(Files.notExists(contentPath) || Files.isDirectory(contentPath)) {
            response.setStatus("404");
            response.setDescription("Not found");
        } else {
            try {
                String fileContent = new String(Files.readAllBytes(contentPath));
                response.setStatus("200");
                response.setDescription("OK");
                response.setContent(fileContent);
            } catch (IOException e) {
                response.setStatus("500");
                response.setDescription("Can't read file");
                e.printStackTrace();
            }
        }
    }
}
