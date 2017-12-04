package demo.marius.es.plugin;

import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.*;

import java.io.IOException;

import static org.elasticsearch.rest.RestRequest.Method.GET;

public class HelloRestAction extends BaseRestHandler {

    private final String prop;

    @Inject
    public HelloRestAction(Settings settings, RestController controller) {
        super(settings);
        controller.registerHandler(GET, "/_hello", this);
        prop = settings.get("hello.prop");

    }

    @Override
    public String getName() {
        return "hello_rest_action";
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest restRequest, NodeClient client) throws IOException {
        String name = restRequest.param("name");

        return channel -> {
            Message message = new Message(name + " - " + prop);
            XContentBuilder builder = channel.newBuilder();
            builder.startObject();
            message.toXContent(builder, restRequest);
            builder.endObject();
            channel.sendResponse(new BytesRestResponse(RestStatus.OK, builder));
        };

    }

    class Message implements ToXContent {

        private final String name;

        public Message(String name) {
            if (name == null) {
                this.name = "World";
            } else {
                this.name = name;
            }
        }

        @Override
        public XContentBuilder toXContent(XContentBuilder builder, ToXContent.Params params) throws IOException {
            return builder.field("message", "Hello " + name + "!");
        }
    }

}
