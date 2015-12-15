import com.company.service.MergeServiceImpl;
import com.company.config.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.repository.ConfigRepository;
import com.company.scheduler.*;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by yevhenlozov on 03.08.15.
 */
public class SchedulerTest {

    private ConfigRepository configRepository = new ConfigRepository(new ObjectMapper());

    @Test
    public void testName() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        MergeServiceImpl mergeService = new MergeServiceImpl(new ConfigRepository(mapper));

        JobKeyFactory jobKeyFactory = new JobKeyFactory();
        JobDetailFactory jobDetailFactory = new JobDetailFactory(jobKeyFactory);
        TriggerFactory triggerFactory = new MultiTriggerFactory(jobKeyFactory);

        MergeJobFactory mergeJobFactory = new MergeJobFactory(mergeService);
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.setJobFactory(mergeJobFactory);
        scheduler.start();

        SchedulerService service = new SchedulerService(scheduler, jobKeyFactory, jobDetailFactory, triggerFactory);
        service.addTask(getConfig("1"));

        Thread.sleep(5 * 1000);

        service.addTask(getConfig("973897e1-fcfb-4cac-89d8-e45217f3f1f2"));

        Thread.sleep(30 * 1000);


    }

    public Config getConfig(String id) {
        return configRepository.get(id);
    }
}
