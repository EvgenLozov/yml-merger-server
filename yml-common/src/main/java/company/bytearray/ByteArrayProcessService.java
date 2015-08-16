package company.bytearray;

public class ByteArrayProcessService {

    ByteArraySource source;
    ByteArrayProcessor processor;
    ByteArrayPostProcessor postProcessor;

    public void process() throws Exception {
        byte[] bytes = source.provide();
        bytes = processor.process(bytes);
        postProcessor.process(bytes);
    }
}
