package company.conditions;

import java.util.function.Predicate;

/**
 * повертає true лише один раз і при першому виконанні умов описаних в eventCondition
 */
public class OnceTime<T> implements Predicate<T> {

    Predicate<T> eventCondition;

    boolean onceSuitable;

    public OnceTime(Predicate<T> eventCondition) {
        this.eventCondition = eventCondition;
    }

    @Override
    public boolean test(T event)  {
        if (!onceSuitable )
        {
            boolean result = eventCondition.test(event);

            if (result)
                onceSuitable = true;

            return result;
        }

        return false;
    }
}
