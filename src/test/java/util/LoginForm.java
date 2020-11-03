package util;
import com.github.javafaker.Faker;
import lombok.Data;

@Data
public class LoginForm {
    private Faker faker = new Faker();
    private final String name = faker.name().username();
    private final String name2 = faker.name().username();
    private final String password = faker.internet().password();
    private final String password2 = faker.internet().password();
}
