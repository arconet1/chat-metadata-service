package chatmetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatMetadataController {
    
    private static final Pattern MENTIONS_PATTERN = Pattern.compile("(?:\\s|\\A|^)[@]+([A-Za-z]+)(?:\\s|$)");
    private static final Pattern EMOTICONS_PATTERN = Pattern.compile("[(]([A-Za-z]+)[)]");
    private static final Pattern URLS_PATTERN = Pattern.compile("(?:\\s|\\A|^)(http[s]*[\\S]+)");

    @RequestMapping("/chatmetadata")
    public ChatMetadata parse(@RequestParam(value="chatString", defaultValue="") String chatString) {
        return new ChatMetadata(
                parseMentions(chatString),
                parseEmoticons(chatString),
                parseUrls(chatString));
    }
    
    private List<String> parseMentions(String chatString) {
        List<String> allMentions = new ArrayList<>();
        Matcher m = MENTIONS_PATTERN.matcher(chatString);
        while (m.find()) {
            allMentions.add(m.group(1));
        }
        return allMentions;
    }
    
    private List<String> parseEmoticons(String chatString) {
        List<String> allEmoticons = new ArrayList<>();
        Matcher m = EMOTICONS_PATTERN.matcher(chatString);
        while (m.find()) {
            allEmoticons.add(m.group(1));
        }
        return allEmoticons;
    }
    
    private List<Url> parseUrls(String chatString) {
        List<Url> allUrls = new ArrayList<>();
        Matcher m = URLS_PATTERN.matcher(chatString);
        while (m.find()) {
            allUrls.add(new Url(m.group(1)));
        }
        return allUrls;
    }
}
