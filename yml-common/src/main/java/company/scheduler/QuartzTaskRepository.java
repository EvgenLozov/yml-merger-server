package company.scheduler;

import java.util.List;

/**
 * Created by yevhen on 09.01.16.
 */
public interface QuartzTaskRepository {
    List<QuartzTask> get();
    void save(QuartzTask task);
    void delete(QuartzTask task);
}
