import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HorseTest {
    //    test{Method}_Should{Do}_When{Condition}

    String name = "Bucephalus";
    double speed = 0.2;
    double distance = 0.9;

    @Test
    public void testConstructor_ShouldIAEx_WhenArgsNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, speed));
    }

    @Test
    public void testConstructor_ShouldIAExMessage_WhenArgsNameIsNull() {
        String expectedExceptionMessage = "Name cannot be null.";
        var exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, speed));

        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @ParameterizedTest()
    @ValueSource(strings = {" ", "", "\t", "\n", "\r", "\f"})
    public void testConstructor_ShouldIAEx_WhenArgsSpaceAndTabulations(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed));
    }

    @ParameterizedTest()
    @ValueSource(strings = {" ", "", "\t", "\n", "\r", "\f"})
    public void testConstructor_ShouldIAExMessage_WhenArgsSpaceAndTabulations(String name) {
        String expectedExceptionMessage = "Name cannot be blank.";
        var exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed));
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    public void testConstructor_ShouldIAEx_WhenArgsSecondNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, -speed));
    }

    @Test
    public void testConstructor_ShouldIAExMessage_WhenArgsSecondNegative() {
        String expectedExceptionMessage = "Speed cannot be negative.";
        var exception = assertThrows(IllegalArgumentException.class, () ->
                new Horse(name, -speed));
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    public void testConstructor_ShouldIAEx_WhenArgsThreeNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, -distance));
    }

    @Test
    public void testConstructor_ShouldIAExMessage_WhenArgsThreeNegative() {
        String expectedExceptionMessage = "Distance cannot be negative.";
        var exception = assertThrows(IllegalArgumentException.class, () ->
            new Horse(name, speed, -distance));
        assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    @SneakyThrows
    public void testGetName_ShouldReturnStr_WhenFirstArgs() {
        String expected = "Bucephalus";

        Horse horse = new Horse(name, speed);
        Field field = horse.getClass().getDeclaredField("name");
        field.setAccessible(true);
        String actual = (String) field.get(horse);

        assertEquals(expected, actual);
    }

    @Test
    @SneakyThrows
    public void testGetSpeed_ShouldReturnNumber_WhenSecondArgs() {
        double expected = 0.2;

        Horse horse = new Horse(name, speed);
        Field field = horse.getClass().getDeclaredField("speed");
        field.setAccessible(true);
        double actual = (double) field.get(horse);

        assertEquals(expected, actual);

    }

    @Test
    @SneakyThrows
    public void testGetDistance_ShouldReturnNumber_WhenThreeArgs() {
        double expected = 0.9;

        Horse horse = new Horse(name, speed, distance);
        Field field = horse.getClass().getDeclaredField("distance");
        field.setAccessible(true);
        double actual = (double) field.get(horse);

        assertEquals(expected, actual);

    }

    @Test
    @SneakyThrows
    public void testGetDistance_ShouldReturnZero_WhenThreeArgs() {
        double expected = 0;

        Horse horse = new Horse(name, speed);
        Field field = horse.getClass().getDeclaredField("distance");
        field.setAccessible(true);
        double actual = (double) field.get(horse);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetMove_ShouldCallGetRandomDouble() {
        Horse horse = new Horse(name, speed, distance);
        try (MockedStatic<Horse> mocked = Mockito.mockStatic(Horse.class)) {
            horse.move();
            mocked.verify(() -> Horse.getRandomDouble(ArgumentMatchers.eq(0.2), ArgumentMatchers.eq(0.9)));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 0.2, 0.5, 0.9, 1, 10, 100})
    public void testGetMove_ShouldAssignsValueSpeed(double fakeValue) {
        Horse horse = new Horse(name, speed, distance);
        double expectedDistance = horse.getDistance() + horse.getSpeed() * fakeValue;

        try (MockedStatic<Horse> mocked = Mockito.mockStatic(Horse.class)) {
            mocked.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(fakeValue);
            horse.move();
            double actualDistance = horse.getDistance();
            assertEquals(expectedDistance, actualDistance);
        }
    }
}
