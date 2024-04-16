<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(DocCenterWebSecurityConfig.class)
public class DocCenterWebSecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAllRequestsArePermitted() throws Exception {
        mockMvc.perform(get("/any-url"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCsrfProtectionIsDisabled() throws Exception {
        mockMvc.perform(get("/any-url")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());
    }
}
