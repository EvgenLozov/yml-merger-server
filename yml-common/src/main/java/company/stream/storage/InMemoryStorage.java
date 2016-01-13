package company.stream.storage;

import java.io.*;

public class InMemoryStorage implements Storage {

    ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(output.toByteArray());
    }

    @Override
    public OutputStream getOutputStream() {
        return output;
    }
}
