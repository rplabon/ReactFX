package org.reactfx;

import java.util.function.Consumer;

public abstract class EventStreamBase<T> implements EventStream<T> {

    private ListHelper<Consumer<T>> subscribers = null;

    protected void emit(T value) {
        ListHelper.forEach(subscribers, s -> s.accept(value));
    }

    /**
     * Called when the number of subscribers goes from 0 to 1.
     * Overriding this method is a convenient way for subclasses
     * to handle this event.
     */
    protected void firstSubscriber() {
        // default implementation is empty
    }

    /**
     * Called when the number of subscribers goes down to 0.
     * Overriding this method is a convenient way for subclasses
     * to handle this event.
     */
    protected void noSubscribers() {
        // default implementation is empty
    }

    @Override
    public Subscription subscribe(Consumer<T> consumer) {
        subscribers = ListHelper.add(subscribers, consumer);
        if(ListHelper.size(subscribers) == 1) {
            firstSubscriber();
        }
        return () -> unsubscribe(consumer);
    }

    private void unsubscribe(Consumer<T> consumer) {
        subscribers = ListHelper.remove(subscribers, consumer);
        if(ListHelper.isEmpty(subscribers)) {
            noSubscribers();
        }
    }
}
