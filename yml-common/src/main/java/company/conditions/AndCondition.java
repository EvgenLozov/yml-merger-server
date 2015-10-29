package company.conditions;

import java.util.List;
import java.util.function.Predicate;

public class AndCondition<T> implements Predicate<T> {

    List<Predicate<T>> predicates;

    public AndCondition(List<Predicate<T>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public boolean test(T t) {
        for (Predicate<T> predicate : predicates) {
            if (!predicate.test(t))
                return false;
        }

        return true;
    }
}
