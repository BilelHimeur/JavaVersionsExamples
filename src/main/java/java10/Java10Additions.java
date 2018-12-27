package java10;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Java10Additions {

    // ********************************************** var type inference ***************************
    // From java 10 local variables can be declared with the var type
    // I do not provide the type of my local variable but I mark the message as a var,
    // and the compiler infers the type of message from the type of the initializer present
    // on the right-hand side.
    @Test
    public void whenVarInitWithString_thenGetStringTypeVar() {
        var message = "Hello, Java 10";
        assertTrue(message instanceof String);
        // var n; gives error (var needs initializer
        // var n = null; gives error (initializer is null)
        // public var s = "hello!"; error var do not work with member variables
    }

    /*Note that this feature is available only for local variables with the initializer.
    It cannot be used for member variables, method parameters, return types, etc –
    the initializer is required as without which compiler won’t be able to infer the type.*/

    // ********************************************** copyOf() ***************************
    // List, Set or Map .copyOf(---) gives the unmodifiable List, Set or Map of the gives List, Set or Map
    @Test(expected = UnsupportedOperationException.class)
    public void whenModifyCopyOfList_thenThrowsException() {
        List<Integer> integers = new ArrayList<>();
        integers.add(7);
        integers.add(8);
        List<Integer> copyList = List.copyOf(integers);
        copyList.add(9);
    }

    // *********************************************** Collectors.toUnmodifiableList ... ***************
    // java.util.stream.Collectors get additional methods to collect
    // a Stream into unmodifiable List, Map or Set:
    @Test(expected = UnsupportedOperationException.class)
    public void whenModifyToUnmodifiableList_thenThrowsException() {
        List<Integer> integers = new ArrayList<>();
        integers.add(7);
        integers.add(8);
        List<Integer> evenList = integers.stream()
                .filter(i -> i % 2 == 0)
                .collect(Collectors.toUnmodifiableList());
        evenList.add(4);
    }
    // *************************************************  orElseThrow() ********************************
    // if the optional is empty it will throw a NoSuchElementExceptions error at runtime
    @Test(expected = NoSuchElementException.class)
    public void whenListContainsInteger_OrElseThrowReturnsInteger() {
        List<Integer> integers = new ArrayList<>();
        integers.add(7);
        integers.add(9);
        Integer firstEven = integers.stream()
                .filter(i -> i % 2 == 0)
                .findFirst()
                .orElseThrow(); // NoSuchElementException will be thrown here
        assertThat(firstEven).isEqualTo(Integer.valueOf(8));
    }
}
