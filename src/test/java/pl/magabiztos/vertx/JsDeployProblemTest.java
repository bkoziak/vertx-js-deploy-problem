package pl.magabiztos.vertx;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunnerWithParametersFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
@Parameterized.UseParametersRunnerFactory(VertxUnitRunnerWithParametersFactory.class)
public class JsDeployProblemTest {

    private static final Logger logger = LoggerFactory.getLogger(JsDeployProblemTest.class);

    @Rule
    public RunTestOnContext rule = new RunTestOnContext();

    private final String deployString;

    public JsDeployProblemTest(String deployString) {
        this.deployString = deployString;
    }

    @Parameterized.Parameters
    public static Iterable<String> data() {
        return Arrays.asList(
                "js:no-extension",
                "js:noextension",
                "js:with-extension.xx",
                "js:with-extension.js",
                "with-extension.js"
        );
    }

    @Test
    public void deployVerticleTest(TestContext context) {
        Async async = context.async();

        rule.vertx().deployVerticle(deployString, ar -> {
            if (!ar.succeeded()) logger.error(ar.cause(), ar.cause());
            context.assertTrue(ar.succeeded());
            async.complete();
        });
    }
}
