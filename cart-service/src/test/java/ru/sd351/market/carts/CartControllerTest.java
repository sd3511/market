package ru.sd351.market.carts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.sd351.market.api.ProductDto;
import ru.sd351.market.carts.integrations.ProductServiceIntegration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {
    @MockBean
    private ProductServiceIntegration productServiceIntegration;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        ProductDto productDto = new ProductDto(123L, "YABLOKO", BigDecimal.valueOf(150), "FRUKTI");
        Mockito.doReturn(productDto).when(productServiceIntegration).getProductById(123L);
    }

    @Test
    public void addToCartTest() throws Exception {
        mockMvc.perform(get("/api/v1/cart/add/123"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/cart/add/123"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/add/1312323"))
                .andExpect(status().is4xxClientError());
    }
    @Test
    public void getCurrentCartTest() throws Exception {
        mockMvc.perform(get("/api/v1/cart"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray());
    }
}
