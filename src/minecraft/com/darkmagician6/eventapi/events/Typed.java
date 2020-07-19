package com.darkmagician6.eventapi.events;


/**
 * Simple interface that should be implemented in typed events.
 * A typed event is an event that can be called on multiple places
 * with the type defining where it was called.
 * <p/>
 * The type should be defined in the constructor when the new instance
 * of the event is created.
 *
 * @author DarkMagician6
 * @since August 27, 2013
 */
public interface Typed {

    /**
     * Gets the current type of the event.
     *
     * @return The type ID of the event.
     */
    byte getType();

}
