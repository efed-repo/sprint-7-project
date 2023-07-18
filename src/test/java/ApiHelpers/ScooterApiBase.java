package ApiHelpers;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class ScooterApiBase {

    protected final String COURIER = "/api/v1/courier";
    protected final String LOGIN = "/api/v1/courier/login";
    protected final String ORDER = "/api/v1/orders";
    protected final String ORDER_CANCEL = "/api/v1/orders/cancel";

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Step
    public void courierCleanUp() {
        courierLogin();
        courierDelete();
    }

    @Step
    protected Response createCourier() {
        CourierModel courierModel = new CourierModel("UniqueApiCourier_765Api", "123456", "myName");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courierModel)
                .when()
                .post(COURIER);
        return response;
    }

    @Step
    public Response courierLogin() {
        String json = "{ \"login\": \"UniqueApiCourier_765Api\", \"password\": \"123456\"}";
        Response loginResponse = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(LOGIN);
        return loginResponse;
    }

    @Step
    public void courierDelete() {
        String id = courierGetId();
        given()
                .header("Content-type", "application/json")
                .when()
                .delete(COURIER + "/" + id);
    }

    @Step
    public String courierGetId() {
        String loginResponse = courierLogin().asString();
        loginResponse = loginResponse.substring(6, loginResponse.length() - 1);
        return loginResponse;
    }

    @Step
    public void createOrder(){

    }

    @Step
    public void cancelOrder(){

    }
}
