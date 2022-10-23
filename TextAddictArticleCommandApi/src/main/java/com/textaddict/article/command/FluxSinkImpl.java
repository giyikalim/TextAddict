package com.textaddict.article.command;

import reactor.core.publisher.FluxSink;
import java.util.function.Consumer;

public class FluxSinkImpl implements Consumer<FluxSink<String>> {

    private FluxSink<String> fluxSink;

    @Override
    public void accept(FluxSink<String> fluxSink) {
        this.fluxSink = fluxSink;
    }

    public void publishEvent(String event){
        this.fluxSink.next(event);
    }

}