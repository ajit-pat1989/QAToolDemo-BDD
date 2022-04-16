
import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;


@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
strict = true,
features = {"C:/Users/DELL/2022/src/test/resources/features/QAdemo.feature"},
plugin = {"json:C:/Users/DELL/2022/target/cucumber-parallel/1.json"},
monochrome = false,
tags = "@UI",
glue = {"com.test.qatools"})
public class Parallel01IT {
@ClassRule
public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

@Rule
public final SpringMethodRule springMethodRule = new SpringMethodRule();

}