/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package chatmetadata;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChatMetadataControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void parseTest_mentions() throws Exception {

        this.mockMvc.perform(get("/chatmetadata").param("chatString", "@Alpha @Beta@Gamma @delTa198 @Epsilon"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"mentions\":[\"Alpha\",\"Epsilon\"],\"emoticons\":[],\"urls\":[]}"));
    }
    
    @Test
    public void parseTest_emoticons() throws Exception {

        this.mockMvc.perform(get("/chatmetadata").param("chatString", "(Alpha) (B(et)a)(@Gamma) (delTa198) (Epsilon)"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"mentions\":[],\"emoticons\":[\"Alpha\",\"et\",\"Epsilon\"],\"urls\":[]}"));
    }
    
    @Test
    public void parseTest_urls() throws Exception {

        this.mockMvc.perform(get("/chatmetadata").param("chatString", "https://www.google.com "
                + "https://twitter.com/jdorfman/status/430511497475670016 https://w.amazon.com/ https://www.atlassian.com/"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"mentions\":[],\"emoticons\":[],\"urls\":[{\"url\":\"https://www.google.com\","
                        + "\"title\":\"Google\"},{\"url\":\"https://twitter.com/jdorfman/status/430511497475670016\","
                        + "\"title\":\"Justin Dorfman; on Twitter: &quot;nice @littlebigdetail from @HipChat "
                        + "(shows hex colors when pasted in chat). http://t.co/7cI6Gjy5pq&quot;\"},{\"url\":"
                        + "\"https://w.amazon.com/\",\"title\":null},{\"url\":\"https://www.atlassian.com/\",\"title\":"
                        + "\"Atlassian | Software Development and Collaboration Tools\"}]}"));
    }

}
