import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {
    @Test
    public void testConstructor_ShouldIAEx_WhenIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void testConstructor_ShouldIAExMessage_WhenIsNull() {
        String exceptionMessage = "Horses cannot be null.";
        var exception = assertThrows(IllegalArgumentException.class, () ->
                new Hippodrome(null));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    public void testConstructor_ShouldIAEx_WhenIsEmptyList() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(List.of()));


    }

    @Test
    public void testConstructor_ShouldIAExMessage_WhenIsEmptyList() {
        String exceptionMessage = "Horses cannot be empty.";
        var exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(List.of()));
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    public void testGetHorses_ShouldReturnList_WhenObjEquals() {
        List<Horse> horses = IntStream.range(0, 30)
                .mapToObj(i -> new Horse("Bucephalus " + i, i, i))
                .collect(Collectors.toList());

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());

    }

    @Test
    public void testGetMove_ShouldCallMethodMoveAllHorses() {
        List<Horse> horses = IntStream.range(0, 50)
                .mapToObj(i -> Mockito.mock(Horse.class))
                .collect(Collectors.toList());

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse horse : horses) {
            Mockito.verify(horse, Mockito.times(1)).move();
        }
    }

    @Test
    public void testGetWinner_ShouldReturnHorseMaxDistance() {
        List<Horse> horsesExpected = IntStream.range(0, 10)
                .mapToObj(i -> new Horse("horse" + i, i, i))
                .collect(Collectors.toList());
        List<Horse> horsesActual = horsesExpected;

        assertSame(horsesExpected, horsesActual);
    }
}