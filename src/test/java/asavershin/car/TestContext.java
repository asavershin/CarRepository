package asavershin.car;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = PostgreTestContainerConfig.Initializer.class)
public class TestContext {
}
