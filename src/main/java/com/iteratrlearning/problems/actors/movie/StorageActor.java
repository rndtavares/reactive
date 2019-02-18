package com.iteratrlearning.problems.actors.movie;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import javax.swing.text.View;
import java.util.HashMap;
import java.util.Map;

public class StorageActor extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    private Map<String, Integer> movieViews = new HashMap<>();

    public StorageActor() {
        log.info("StorageActor constructor");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        log.info("Received Message: " + message);

        if(message instanceof ViewMovieMessage){
            ViewMovieMessage viewMovieMessage = (ViewMovieMessage) message;
            Integer count = movieViews.containsKey(viewMovieMessage.getMovie()) ? movieViews.get(viewMovieMessage.getMovie()) : 0;
            movieViews.put(viewMovieMessage.getMovie(), ++count);
        }else if(message instanceof InfoMovieMessage){
            InfoMovieMessage infoMovieMessage = (InfoMovieMessage) message;
            Integer count = movieViews.containsKey(infoMovieMessage.getMovie()) ? movieViews.get(infoMovieMessage.getMovie()) : 0;
            getSender().tell(new InfoReplyMovieMessage(infoMovieMessage.getMovie(), count), getSelf());
        }
    }
}
