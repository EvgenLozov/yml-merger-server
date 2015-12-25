import com.company.repository.MergerConfigRepository;
import com.company.service.MergeServiceImpl;
import com.company.config.MergerConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.company.scheduler.*;
import com.fasterxml.jackson.databind.SerializationFeature;
import company.config.ConfigRepository;
import company.config.JsonBasedConfigRepository;
import org.junit.Before;
import org.junit.Test;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by yevhenlozov on 03.08.15.
 */
public class SchedulerTest {

    private ConfigRepository<MergerConfig> configRepository;

    @Before
    public void setUp() throws Exception {

        configRepository= new JsonBasedConfigRepository<>("config/config.json",MergerConfig.class,new ObjectMapper());
        configRepository= new MergerConfigRepository(configRepository);
    }

    @Test
    public void testName() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        MergeServiceImpl mergeService = new MergeServiceImpl(configRepository);

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

    public MergerConfig getConfig(String id) {
        return configRepository.get(id);
    }
}
