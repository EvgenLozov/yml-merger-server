package company.scheduler;

/**
 * Created by yevhen on 09.01.16.
 */
public interface QuartzTaskRepository {
    void save(QuartzTask task);
    void delete(QuartzTask task);
}
