import ApiHelpers.ScooterApiBase;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class GetListOfOrdersApiTests extends ScooterApiBase {

    @Test
    @DisplayName("Получение списка со всеми заказами")
    public void getListOfOrdersTest() {
        given()
                .header("Content-type", "application/json")
                .when()
                .get(ORDER)
                .then()
                .statusCode(200)
                .and()
                .assertThat().body("orders", notNullValue());
    }
}
