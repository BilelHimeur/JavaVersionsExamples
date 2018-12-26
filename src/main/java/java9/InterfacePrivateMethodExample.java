package java9;

import com.google.common.base.CharMatcher;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface InterfacePrivateMethodExample {

    private List<String> getUppercaseStrings(List<String> items, Predicate<String> filteringPredicate) {
        return items.stream()
                .filter(item -> CharMatcher.javaUpperCase().matchesAllOf(item))
                .filter(filteringPredicate)
                .collect(Collectors.toList());
    }

    default List<String> getStringsUppercaseCharsMorethan5(List<String> items) {
        return getUppercaseStrings(items, item -> item.length() >= 5);
    }

    default List<String> getStringUppercaseCharsLessThan5(List<String> items) {
        return getUppercaseStrings(items, item -> item.length() < 5);
    }

}