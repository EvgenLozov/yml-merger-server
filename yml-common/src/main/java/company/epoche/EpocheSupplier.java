package company.epoche;

import java.util.List;
import java.util.function.Supplier;

public class EpocheSupplier<T> implements Supplier<T> {

    private List<Supplier<T>> suppliers;
    private long start;
    private long period;

    public EpocheSupplier(List<Supplier<T>> suppliers, long start, long period) {
        this.suppliers = suppliers;
        this.start = start;
        this.period = period;
    }

    public T get(){
        int epoches = (int) ((System.currentTimeMillis() - start)/period);
        int providerNumber = epoches % suppliers.size();
        return suppliers.get(providerNumber).get();
    }

}
