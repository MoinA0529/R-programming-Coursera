import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DocCenterWebSecurityConfigTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        DocCenterWebSecurityConfig securityConfig = new DocCenterWebSecurityConfig();
        SecurityFilterChain filterChain = securityConfig.filterChain(new HttpSecurity());
        mockMvc = MockMvcBuilders.standaloneSetup()
                .addFilter(filterChain)
                .build();
    }

    @Test
    public void testAllRequestsArePermitted() throws Exception {
        mockMvc.perform(get("/any-url"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCsrfProtectionIsDisabled() throws Exception {
        mockMvc.perform(get("/any-url"))
                .andExpect(status().isOk());
    }
}
