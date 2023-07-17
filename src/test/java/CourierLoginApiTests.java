import ApiHelpers.CourierApiBase;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CourierLoginApiTests extends CourierApiBase {

    @Test
    public void courierLoginSuccessfulTest() {
        createCourier();
        Response response = courierLogin();
        int id = Integer.parseInt(courierGetId());
        response.then().statusCode(200)
                .and()
                .assertThat().body("id", equalTo(id));
        cleanUP();

    }

    @Test
    public void courierLoginWithIncorrectDataUnsuccessfulTest(){
        String json = "{ \"login\": \"IncorrectCourierQw#$Test12\", \"password\": \"1\"}";
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(LOGIN);
        response.then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

}
