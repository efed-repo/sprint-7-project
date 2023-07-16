import ApiHelpers.Courier;
import ApiHelpers.CourierApiBase;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateCourierParametrizedTests extends CourierApiBase {

    private final String login;
    private final String password;
    private final String firstName;

    public CreateCourierParametrizedTests(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Parameterized.Parameters(name = "Создание курьера без одного обязательного параметра: {0} {1} {2}")
    public static Object[][] getSumData() {
        return new Object[][]{
                {"", "123456", "withoutLogin"},
                {"withoutPassword", "", "courierName"},
                {"withoutName", "123456", ""},
        };
    }

    @Test
    public void createCourierWithoutRequiredParamsUnsuccessfulTest() {
        Courier courier = new Courier(login, password, firstName);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(COURIER);
        response.then().assertThat().statusCode(400)
                .and()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

}
