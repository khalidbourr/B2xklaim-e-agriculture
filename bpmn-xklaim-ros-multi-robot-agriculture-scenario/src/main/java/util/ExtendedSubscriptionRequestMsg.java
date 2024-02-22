package util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ros.SubscriptionRequestMsg;



import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class ExtendedSubscriptionRequestMsg extends SubscriptionRequestMsg {

    private ObjectMapper mapper = new ObjectMapper();
    private ObjectNode qosNode = mapper.createObjectNode();

    public ExtendedSubscriptionRequestMsg(String topic) {
        super(topic);
    }

    public ExtendedSubscriptionRequestMsg setQoSReliability(String reliability) {
        qosNode.put("reliability", reliability);
        return this;
    }

    public ExtendedSubscriptionRequestMsg setQoSDurability(String durability) {
        qosNode.put("durability", durability);
        return this;
    }

    public ExtendedSubscriptionRequestMsg setQoSHistory(String history, int depth) {
        qosNode.put("history", history);
        qosNode.put("depth", depth);
        return this;
    }

    public ExtendedSubscriptionRequestMsg setBestEffortQoS() {
        this.setQoSReliability("best_effort");
        this.setQoSDurability("volatile");
        this.setQoSHistory("keep_last", 10);
        return this;
    }

    @Override
    public String generateJsonString() {
        this.setKeyValue("qos", qosNode);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(this.keyValues);
            System.out.println("Generated JSON String: " + jsonString); // Print the JSON string
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    @Override
    public ExtendedSubscriptionRequestMsg setType(String type) {
        super.setType(type);
        return this;
    }

    @Override
    public ExtendedSubscriptionRequestMsg setThrottleRate(Integer throttleRate) {
        super.setThrottleRate(throttleRate);
        return this;
    }

    @Override
    public ExtendedSubscriptionRequestMsg setQueueLength(Integer queueLength) {
        super.setQueueLength(queueLength);
        return this;
    }

    // Other necessary methods...

}

    
