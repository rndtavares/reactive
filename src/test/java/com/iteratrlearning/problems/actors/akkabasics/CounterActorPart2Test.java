package com.iteratrlearning.problems.actors.akkabasics;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.PatternsCS;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;
import akka.util.Timeout;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import scala.concurrent.duration.Duration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static org.junit.Assert.assertEquals;

public class CounterActorPart2Test {

    private ActorSystem system;

    @Before
    public void setup() {
        system = ActorSystem.create();
    }

    @After
    public void teardown() {
        JavaTestKit.shutdownActorSystem(system);
    }

    @Test(timeout = 2_000)
    public void testTellCount() {
        Props props = Props.create(CounterActor.class);
        TestActorRef<CounterActor> ref = TestActorRef.create(system, props, "test-counter-actor");
        CounterActor actor = ref.underlyingActor();
        final int ITERATIONS = 5;
        for(int i = 0; i < ITERATIONS; i++) {
            ref.tell("Status", null);
        }

        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        CompletionStage<Object> countCompletionState = PatternsCS.ask(ref, "Count", timeout);
        CompletableFuture<Object> ask = countCompletionState.toCompletableFuture() ;

        assertEquals(ITERATIONS, ask.join());
    }

}
