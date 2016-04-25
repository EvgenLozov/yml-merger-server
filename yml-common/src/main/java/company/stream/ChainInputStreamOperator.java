package company.stream;

import java.io.InputStream;
import java.util.List;

public class ChainInputStreamOperator implements InputStreamOperator {

    List<InputStreamOperator> operators;

    public ChainInputStreamOperator(List<InputStreamOperator> operators) {
        this.operators = operators;
    }

    @Override
    public InputStream apply(InputStream inputStream) {

        InputStream operatedInputStream = inputStream;

        for (InputStreamOperator operator : operators) {
            operatedInputStream = operator.apply(operatedInputStream);
        }

        return operatedInputStream;
    }
}
