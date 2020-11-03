import io.restassured.http.ContentType;

import domain.JsonGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.LoginForm;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class PageUiTest {
    String name = new LoginForm().getName();
    String password = new LoginForm().getPassword();

    @BeforeEach
    void setUp() {
        given()
                .baseUri("http://localhost:9999")
                .contentType(ContentType.JSON)
                .body(new JsonGenerator().user(name, password, true))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
        open("http://localhost:9999/");

    }

    @Test
    void shouldSucceedLogin() {
        $("[data-test-id=\"login\"] .input__control").setValue(name);
        $("[data-test-id=\"password\"] .input__control").setValue(password);
        $("[data-test-id=\"action-login\"] .button__text").click();
        $$("h2").findBy(text("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldNotSucceedLoginByName() {
        $("[data-test-id=\"login\"] .input__control").setValue(new LoginForm().getName2());
        $("[data-test-id=\"password\"] .input__control").setValue(password);
        $("[data-test-id=\"action-login\"] .button__text").click();
        $("[data-test-id=\"error-notification\"] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldNotSucceedLoginByPassword() {
        $("[data-test-id=\"login\"] .input__control").setValue(name);
        $("[data-test-id=\"password\"] .input__control").setValue(new LoginForm().getPassword2());
        $("[data-test-id=\"action-login\"] .button__text").click();
        $("[data-test-id=\"error-notification\"] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldNotSucceedLoginByBlock() {
        given()
                .baseUri("http://localhost:9999")
                .contentType(ContentType.JSON)
                .body(new JsonGenerator().user(name, password, false))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);

        $("[data-test-id=\"login\"] .input__control").setValue(name);
        $("[data-test-id=\"password\"] .input__control").setValue(password);
        $("[data-test-id=\"action-login\"] .button__text").click();
        $("[data-test-id=\"error-notification\"] .notification__content").shouldHave(text("Ошибка! Пользователь заблокирован")).shouldBe(visible);
    }
}