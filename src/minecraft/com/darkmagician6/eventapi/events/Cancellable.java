package com.darkmagician6.eventapi.events;

/**
 * Simple interface which should be implemented in events that can be cancelled.
 *
 * @author DarkMagician6
 * @since August 27, 2013
 */
public interface Cancellable {

    /**
     * Gets the current cancelled state of the event.
     *
     * @return True if the event is cancelled.
     */
    boolean isCancelled();

    /**
     * Sets the cancelled state of the event.
     *
     * @param state
     *         Whether the event should be cancelled or not.
     */
    void setCancelled(boolean state);

}
