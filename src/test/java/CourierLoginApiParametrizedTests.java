import ApiHelpers.ScooterApiBase;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@RunWith(Parameterized.class)
public class CourierLoginApiParametrizedTests extends ScooterApiBase {

    private final String json;

    public CourierLoginApiParametrizedTests(String json) {
        this.json = json;
    }


    @Parameterized.Parameters(name = "Логин курьера без одного обязательного параметра: {0}")
    public static Object[][] courierLoginData() {
        return new Object[][]{
                {"{ \"login\": \"withoutPassword\", \"password\": \"\"}"},
                {"{ \"login\": \"\", \"password\": \"withoutLogin\"}"}
        };
    }

    @Test
    @DisplayName("Логин курьера без одного из параметров, негативный кейс")
    public void courierLoginWithoutRequiredParamsUnsuccessfulTest() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login");
        response.then().assertThat().statusCode(400)
                .and()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

}
