package dgtic.core.maquetado;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql({"/schema.sql", "/data.sql"})
class MaquetadoApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("Conexion exitosa");
	}

}
