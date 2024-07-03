import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;


class MainTest {

    @Test
    @Timeout(value = 22)
    @Disabled
    public void testMain_ShouldTimeOut() {
        Main.main(null);
    }
}