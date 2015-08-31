import com.company.allowedcategories.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.merger.CategoryRepositoryImpl;
import com.merger.ConfigRepository;
import org.junit.Test;

import java.util.Set;

/**
 * @author Yevhen
 */
public class CategoryRepositoryTest {

    CategoryRepositoryImpl repository = new CategoryRepositoryImpl(new ConfigRepository(new ObjectMapper()));

    @Test
    public void testChildren() throws Exception {
        Set<Category> categories = repository.children("b840c767-35e3-4479-b857-943a9b0c17c5", "0");

        categories.forEach(category -> System.out.println(category.toString()));

    }
}
