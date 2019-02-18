package com.iteratrlearning.problems.actors.akkabasics;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class CounterActor extends UntypedActor {

    public static final String STOP = "Stop";
    public static final String COUNT = "Count";
    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private int count;

    @Override
    public void onReceive(Object message) throws Throwable {
        log.info(message.toString());
        if(message.toString().equalsIgnoreCase(STOP)){
            getContext().stop(getSelf());
            return;
        }else if(COUNT.equalsIgnoreCase(message.toString())){
            getSender().tell(count, getSelf());
        }
        count++;
    }

    public int getCount() {
        return count;
    }

}
