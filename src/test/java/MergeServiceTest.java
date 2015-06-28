import com.merger.Config;
import com.merger.ConfigProvider;
import com.merger.merge.MergeByDOMService;
import com.merger.merge.MergeByObjectsModelService;
import org.junit.Test;

import java.io.FileOutputStream;

/**
 * Created by user50 on 21.06.2015.
 */
public class MergeServiceTest {

    @Test
    public void testMerge() throws Exception {
        Config config = new ConfigProvider().get();
        MergeByObjectsModelService mergeService = new MergeByObjectsModelService(config);

        new FileOutputStream("merged.yml").write(mergeService.getYml());

    }

    @Test
    public void testMergeWithDOM() throws Exception {
        Config config = new ConfigProvider().get();
        MergeByDOMService mergeService = new MergeByDOMService(config);

        new FileOutputStream("merged.yml").write(mergeService.getYml());

    }
}
