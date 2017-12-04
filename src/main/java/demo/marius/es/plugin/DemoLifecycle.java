package demo.marius.es.plugin;

import org.elasticsearch.common.component.AbstractLifecycleComponent;
import org.elasticsearch.common.settings.Settings;

import java.io.IOException;

public class DemoLifecycle extends AbstractLifecycleComponent  {

    protected DemoLifecycle(Settings settings) {
        super(settings, DemoLifecycle.class);
    }

    @Override
    protected void doStart() {
        logger.info("doStart");
    }

    @Override
    protected void doStop() {
        logger.info("doStop");
    }

    @Override
    protected void doClose() throws IOException {
        logger.info("doClose");
    }
}
