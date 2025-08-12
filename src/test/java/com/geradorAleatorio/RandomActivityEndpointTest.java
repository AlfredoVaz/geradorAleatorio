package com.geradorAleatorio;

import com.geradorAleatorio.dto.RandomActivityDto;
import com.geradorAleatorio.repository.RandomActivityJpaRepository;
import com.geradorAleatorio.repository.RandomActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RandomActivityEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RandomActivityJpaRepository jpaRepository;

    @Autowired
    private RandomActivityRepository externalRepository;

    @TestConfiguration
    static class TestConfig {
      @Bean
      @Primary
      RandomActivityRepository externalRepository() {
        return org.mockito.Mockito.mock(RandomActivityRepository.class);
      }
    }

    @BeforeEach
    void cleanDb() {
        jpaRepository.deleteAll();
    }

    private RandomActivityDto dto(String key) {
        return RandomActivityDto.builder()
                .activity("Test Activity")
                .availability(1)
                .type("test")
                .participants(1)
                .price(0.0)
                .accessibility("Few to no challenges")
                .duration("minutes")
                .kidFriendly(true)
                .link("")
                .key(key)
                .build();
    }

    @Test
    @DisplayName("GET /randomActivities deve retornar uma nova atividade (200)")
    void shouldReturnRandomActivity() throws Exception {
        when(externalRepository.getRandomActivity()).thenReturn(dto("KEY-001"));

        mockMvc.perform(get("/randomActivities")
                        .with(httpBasic("admin", "1234"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.key").value("KEY-001"))
                .andExpect(jsonPath("$.activity").value("Test Activity"));
    }

    @Test
    @DisplayName("GET /randomActivities/list deve listar atividades salvas (200)")
    void shouldListAllActivities() throws Exception {
        when(externalRepository.getRandomActivity()).thenReturn(dto("KEY-002"));

        mockMvc.perform(get("/randomActivities").with(httpBasic("admin", "1234")));

        mockMvc.perform(get("/randomActivities/list")
                        .with(httpBasic("admin", "1234"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].key").value("KEY-002"));
    }

    @Test
    @DisplayName("GET /randomActivities/list/{key} deve retornar item específico (200)")
    void shouldReturnSpecificActivityByKey() throws Exception {
        when(externalRepository.getRandomActivity()).thenReturn(dto("KEY-003"));
        mockMvc.perform(get("/randomActivities").with(httpBasic("admin", "1234")));

        mockMvc.perform(get("/randomActivities/list/KEY-003")
                        .with(httpBasic("admin", "1234")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key").value("KEY-003"));
    }

    @Test
    @DisplayName("GET rota inexistente deve retornar 404 JSON")
    void shouldReturn404ForUnknownRoute() throws Exception {
        mockMvc.perform(get("/rotaNadaHaver").with(httpBasic("admin", "1234")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    @DisplayName("Sem credenciais deve retornar 401 Unauthorized")
    void shouldReturn401WhenNoCredentials() throws Exception {
        mockMvc.perform(get("/randomActivities"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.error").value("Unauthorized"));
    }

    @Test
    @DisplayName("Usuário autenticado sem role ADMIN deve retornar 403 Forbidden")
    void shouldReturn403WhenForbidden() throws Exception {
        mockMvc.perform(get("/randomActivities").with(user("user").roles("USER")))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("GET /randomActivities/list/{key} inexistente deve retornar 404")
    void shouldReturn404WhenKeyNotFound() throws Exception {
        mockMvc.perform(get("/randomActivities/list/KEY-UNKNOWN")
                        .with(httpBasic("admin", "1234")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }
}


