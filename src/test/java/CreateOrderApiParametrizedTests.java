import ApiHelpers.ScooterApiBase;
import ApiHelpers.OrderModel;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderApiParametrizedTests extends ScooterApiBase {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String rentTime;
    private final String deliveryDate;
    private final String comment;

    public CreateOrderApiParametrizedTests(String firstName, String lastName, String address, String metroStation, String phone,
                                           String rentTime, String deliveryDate, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
    }

    @Parameterized.Parameters(name = "Создание заказа с данными: {0} {1} {2}")
    public static Object[][] createOrderData() {
        return new Object[][]{
                {"Елена", "Иванова", "Красная прощадь, 1", "5", "+7 800 355 35 35", "3", "2023-12-12", "api"},
                {"Олег", "Сергеев", "Дворцовая площадь,1", "10", "+7 813 354 00 00", "1", "2023-11-11", "api"},
        };
    }

    @Test
    @DisplayName("Создание заказа с разными данными, позитивный кейс")
    public void createOrderSuccessTest() {
        OrderModel orderModel = new OrderModel(firstName, lastName,
                address, metroStation, phone, rentTime, deliveryDate, comment);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(orderModel)
                .when()
                .post(ORDER);
        response.then().assertThat().statusCode(201)
                .and()
                .assertThat()
                .body("track", notNullValue());
    }

}


