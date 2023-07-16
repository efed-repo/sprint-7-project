package ApiHelpers;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;

import static io.restassured.RestAssured.given;

public class CourierApiBase {

    protected final String COURIER = "/api/v1/courier";
    private final String LOGIN = "/api/v1/courier/login";

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Step
    public void cleanUP() {
        courierLogin();
        courierDelete();
    }

    @Step
    protected Response createCourier() {
        Courier courier = new Courier("UniqueApiCourier", "123456", "myName");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(COURIER);
        return response;
    }

    public Response courierLogin() {
        String json = "{ \"login\": \"UniqueApiCourier\", \"password\": \"123456\"}";
        Response loginResponse = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(LOGIN);
        return loginResponse;
    }

    public void courierDelete() {
        String loginResponse = courierLogin().asString();
        String id = loginResponse.substring(6, loginResponse.length() - 1);

        given()
                .header("Content-type", "application/json")
                .when()
                .delete(COURIER + "/" + id);
    }

}
