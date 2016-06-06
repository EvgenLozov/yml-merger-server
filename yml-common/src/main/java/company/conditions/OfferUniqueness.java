package company.conditions;

import company.util.XmlEventUtil;

import javax.xml.stream.events.XMLEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class OfferUniqueness implements Predicate<List<XMLEvent>> {

    private Set<String> metOfferIds = new HashSet<>();

    @Override
    public boolean test(List<XMLEvent> xmlEvents) {
        Optional<String> id = XmlEventUtil.getAttributeValue(xmlEvents, "offer", "id");

        if (!id.isPresent())
            return true;

        if (metOfferIds.contains(id.get()))
            return false;

        metOfferIds.add(id.get());

        return true;
    }
}
