package java9;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;


public class Java9OptionalAdditions {
    @Test
    public void givenOptional_whenPresent_thenShouldExecuteProperCallback() {
        // given
        Optional<String> value = Optional.of("properValue");
        AtomicInteger successCounter = new AtomicInteger(0);
        AtomicInteger onEmptyOptionalCounter = new AtomicInteger(0);

        // when
        value.ifPresentOrElse(v -> successCounter.incrementAndGet(), onEmptyOptionalCounter::incrementAndGet);

        // then
        assertThat(successCounter.get()).isEqualTo(1);
        assertThat(onEmptyOptionalCounter.get()).isEqualTo(0);
    }

    // Case of optional is empty
    @Test
    public void givenOptional_whenNotPresent_thenShouldExecuteProperCallback() {
        // given
        Optional<String> value = Optional.empty();
        AtomicInteger successCounter = new AtomicInteger(0);
        AtomicInteger onEmptyOptionalCounter = new AtomicInteger(0);

        // when
        value.ifPresentOrElse(
                v -> successCounter.incrementAndGet(),
                onEmptyOptionalCounter::incrementAndGet);

        // then
        assertThat(successCounter.get()).isEqualTo(0);
        assertThat(onEmptyOptionalCounter.get()).isEqualTo(1);
    }

    /*
    Sometimes, when our Optional is empty, we want to execute some other action that also returns an Optional.
    Prior Java 9 the Optional class had only the orElse() and orElseGet() methods but both need to return unwrapped values.
    Java 9 introduces the or() method that returns another Optional lazily if our Optional is empty.
    If our first Optional has a defined value, the lambda passed to the or() method will not be invoked,
    and value will not be calculated and returned:
     */
    @Test
    public void givenOptional_whenPresent_thenShouldTakeAValueFromIt() {
        //given
        String expected = "properValue";
        Optional<String> value = Optional.of(expected);
        Optional<String> defaultValue = Optional.of("default");

        //when
        Optional<String> result = value.or(() -> defaultValue);

        //then
        assertThat(result.get()).isEqualTo(expected);
    }

    // Case of empty optional
    @Test
    public void givenOptional_whenEmpty_thenShouldTakeAValueFromOr() {
        // given
        String defaultString = "default";
        Optional<String> value = Optional.empty();
        Optional<String> defaultValue = Optional.of(defaultString);

        // when
        Optional<String> result = value.or(() -> defaultValue);

        // then
        assertThat(result.get()).isEqualTo(defaultString);
    }
}
