import ApiHelpers.CourierApiBase;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreateCourierApiTests extends CourierApiBase {


    @Test
    public void createCourierSuccessTest() {
        Response response = createCourier();
        response.then().assertThat().statusCode(201);
        // .and()
        // .assertThat().body("ok", equalTo("true"));
        cleanUP();

    }

    @Test
    public void createCourierWithExistingLoginUnsuccessfulTest() {
        createCourier();
        Response newCourierWithExistingLogin = createCourier();
        newCourierWithExistingLogin.then().assertThat().statusCode(409)
                .and()
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        cleanUP();
    }

}
