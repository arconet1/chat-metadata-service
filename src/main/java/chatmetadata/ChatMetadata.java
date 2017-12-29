package chatmetadata;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatMetadata {

    private final List<String> mentions;
    private final List<String> emoticons;
    private final List<Url> urls;
}
