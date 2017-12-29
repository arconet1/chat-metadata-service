package chatmetadata;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import lombok.Getter;

@Getter
public class Url {
    private final String url;
    private final String title;
    
    public Url(String url) {
        this.url = url;
        this.title = getTitle(url);
    }
    
    private String getTitle(String url) {
        String title = null;
        try (InputStream response = new URL(url).openStream()){
            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();
            title = responseBody.substring(responseBody.indexOf("<title>") + 7, responseBody.indexOf("</title>"));
        } catch (IOException ex) {
            // Ignore exceptions - if we cannot retrieve a title, just return null;
        }
        return title;
    }
}
