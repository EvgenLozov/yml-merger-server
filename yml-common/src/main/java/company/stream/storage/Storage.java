package company.stream.storage;

import java.io.InputStream;
import java.io.OutputStream;

public interface Storage {

    InputStream getInputStream();

    OutputStream getOutputStream();
}
