import ApiHelpers.ScooterApiBase;
import ApiHelpers.OrderModel;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


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
    private final String[] color;

    public CreateOrderApiParametrizedTests(String firstName, String lastName, String address,
                                           String metroStation, String phone, String rentTime,
                                           String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Создание заказа с данными: {0} {1} {2} {3} {4} {5} {6} {7} {8}")
    public static Object[][] createOrderData() {
        return new Object[][]{
                {"Елена", "Иванова", "Красная прощадь, 1", "5", "+7 800 355 35 35", "3", "2023-12-12", "api",
                        new String[]{"GREY"}},
                {"Олег", "Сергеев", "Дворцовая площадь,1", "10", "+7 813 354 00 00", "1", "2023-11-11", "api",
                        new String[]{"BLACK"}},
                {"Сергей", "Петров", "ул. Бабушкина, 4", "15", "+7 800 305 35 45", "2", "2023-10-10", "api",
                        new String[]{"GREY", "BLACK"}},
                {"Анна", "Васильева", "рп. Энергетиков, 9", "20", "+7 840 000 35 45", "5", "2023-09-09", "api",
                        new String[]{}},
        };
    }

    @Test
    @DisplayName("Создание заказа с разными данными, позитивный кейс")
    public void createOrderSuccessTest() {
        OrderModel orderModel = new OrderModel(firstName, lastName,
                address, metroStation, phone, rentTime, deliveryDate, comment, color);
        Response response = createOrder(orderModel);
        int id = getOrderTruck(response);
        response.then().assertThat().statusCode(201)
                .and()
                .assertThat()
                .body("track", equalTo(id));
        cancelOrder(id);

    }

    private void cancelOrder(int id) {
        given()
                .header("Content-type", "application/json")
                .when()
                .delete(ORDER_CANCEL + "/" + id);
    }

    private int getOrderTruck(Response response) {
        String orderResponse = response.asString();
        orderResponse = orderResponse.substring(9, orderResponse.length() - 1);
        int id = Integer.parseInt(orderResponse);
        return id;
    }

    private Response createOrder(OrderModel orderModel) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(orderModel)
                .when()
                .post(ORDER);
        return response;
    }

}


