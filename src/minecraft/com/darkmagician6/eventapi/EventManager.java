package com.darkmagician6.eventapi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.darkmagician6.eventapi.events.Event;
import com.darkmagician6.eventapi.events.EventStoppable;
import com.darkmagician6.eventapi.types.Priority;

/**
 * 
 * 
 * @author DarkMagician6
 * @since February 2, 2014
 */
public final class EventManager {
	
	/**
	 * HashMap containing all the registered MethodData sorted on the event parameters of the methods.
	 */
	private static final Map<Class<? extends Event>, List<MethodData>> REGISTRY_MAP = new HashMap<Class<? extends Event>, List<MethodData>>();
	
	/**
	 * All methods in this class are static so there would be no reason to create an object of the EventManager class.
	 */
	private EventManager() {
	}
	
    /**
     * Registers all the methods marked with the EventTarget annotation in the class of the given Object.
     *
     * @param object
     *         Object that you want to register.
     */
    public static void register(Object object) {
        for (final Method method : object.getClass().getDeclaredMethods()) {
            if (isMethodBad(method)) {
                continue;
            }

            register(method, object);
        }
    }

    /**
     * Registers the methods marked with the EventTarget annotation and that require
     * the specified Event as the parameter in the class of the given Object.
     *
     * @param object
     *         Object that contains the Method you want to register.
     * @param Parameter
     *         class for the marked method we are looking for.
     */
    public static void register(Object object, Class<? extends Event> eventClass) {
        for (final Method method : object.getClass().getDeclaredMethods()) {
            if (isMethodBad(method, eventClass)) {
                continue;
            }

            register(method, object);
        }
    }

    /**
     * Unregisters all the methods inside the Object that are marked with the EventTarget annotation.
     *
     * @param object
     *         Object of which you want to unregister all Methods.
     */
    public static void unregister(Object object) {
        for (final List<MethodData> dataList : REGISTRY_MAP.values()) {
            for (final MethodData data : dataList) {
                if (data.getSource().equals(object)) {
                    dataList.remove(data);
                }
            }
        }

        cleanMap(true);
    }

    /**
     * Unregisters all the methods in the given Object that have the specified class as a parameter.
     *
     * @param object
     *         Object that implements the Listener interface.
     * @param Parameter
     *         class for the method to remove.
     */
    public static void unregister(Object object, Class<? extends Event> eventClass) {
        if (REGISTRY_MAP.containsKey(eventClass)) {
            for (final MethodData data : REGISTRY_MAP.get(eventClass)) {
                if (data.getSource().equals(object)) {
                	REGISTRY_MAP.get(eventClass).remove(data);
                }
            }

            cleanMap(true);
        }
    }

    /**
     * Registers a new MethodData to the HashMap.
     * If the HashMap already contains the key of the Method's first argument it will add
     * a new MethodData to key's matching list and sorts it based on Priority. @see com.darkmagician6.eventapi.types.Priority
     * Otherwise it will put a new entry in the HashMap with a the first argument's class
     * and a new CopyOnWriteArrayList containing the new MethodData.
     *
     * @param method
     *         Method to register to the HashMap.
     * @param object
     *         Source object of the method.
     */
    private static void register(Method method, Object object) {
    	Class<? extends Event> indexClass = (Class<? extends Event>) method.getParameterTypes()[0];
    	//New MethodData from the Method we are registering.
    	final MethodData data = new MethodData(object, method, method.getAnnotation(EventTarget.class).value());
    	
    	//Set's the method to accessible so that we can also invoke it if it's protected or private.
    	if (!data.getTarget().isAccessible()) {
    		data.getTarget().setAccessible(true);
    	}
	
    	if (REGISTRY_MAP.containsKey(indexClass)) {
    		if (!REGISTRY_MAP.get(indexClass).contains(data)) {
    			REGISTRY_MAP.get(indexClass).add(data);
    			sortListValue(indexClass);
    		}
    	} else {
    		REGISTRY_MAP.put(indexClass, new CopyOnWriteArrayList<MethodData>() {
    			//Eclipse was bitching about a serialVersionUID.
    			private static final long serialVersionUID = 666L; {
    				add(data);
    			}
    		});
    	}
    }

    /**
     * Removes an entry based on the key value in the map.
     *
     * @param indexClass
     *         They index key in the map of which the entry should be removed.
     */
    public static void removeEntry(Class<? extends Event> indexClass) {
        Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = REGISTRY_MAP.entrySet().iterator();

        while (mapIterator.hasNext()) {
            if (mapIterator.next().getKey().equals(indexClass)) {
                mapIterator.remove();
                break;
            }
        }
    }

    /**
     * Cleans up the map entries.
     * Uses an iterator to make sure that the entry is completely removed.
     *
     * @param onlyEmptyEntries
     *         If true only remove the entries with an empty list, otherwise remove all the entries.
     */
    public static void cleanMap(boolean onlyEmptyEntries) {
        Iterator<Map.Entry<Class<? extends Event>, List<MethodData>>> mapIterator = REGISTRY_MAP.entrySet().iterator();

        while (mapIterator.hasNext()) {
            if (!onlyEmptyEntries || mapIterator.next().getValue().isEmpty()) {
                mapIterator.remove();
            }
        }
    }

    /**
     * Sorts the List that matches the corresponding Event class based on priority value.
     *
     * @param indexClass
     *         The Event class index in the HashMap of the List to sort.
     */
    private static void sortListValue(Class<? extends Event> indexClass) {
        List<MethodData> sortedList = new CopyOnWriteArrayList<MethodData>();

        for (final byte priority : Priority.VALUE_ARRAY) {
            for (final MethodData data : REGISTRY_MAP.get(indexClass)) {
                if (data.getPriority() == priority) {
                    sortedList.add(data);
                }
            }
        }

        //Overwriting the existing entry.
        REGISTRY_MAP.put(indexClass, sortedList);
    }

    /**
     * Checks if the method does not meet the requirements to be used to receive event calls from the Dispatcher.
     * Performed checks: Checks if the parameter length is not 1 and if the EventTarget annotation is not present.
     *
     * @param method
     *         Method to check.
     *
     * @return True if the method should not be used for receiving event calls from the Dispatcher.
     *
     * @see com.darkmagician6.eventapi.EventTarget
     */
    private static boolean isMethodBad(Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventTarget.class);
    }

    /**
     * Checks if the method does not meet the requirements to be used to receive event calls from the Dispatcher.
     * Performed checks: Checks if the parameter class of the method is the same as the event we want to receive.
     *
     * @param method
     *         Method to check.
     * @param Class
     *         of the Event we want to find a method for receiving it.
     *
     * @return True if the method should not be used for receiving event calls from the Dispatcher.
     *
     * @see com.darkmagician6.eventapi.EventTarget
     */
    private static boolean isMethodBad(Method method, Class<? extends Event> eventClass) {
        return isMethodBad(method) || !method.getParameterTypes()[0].equals(eventClass);
    }
	
    /**
     * Call's an event and invokes the right methods that are listening to the event call.
     * First get's the matching list from the registry map based on the class of the event.
     * Then it checks if the list is not null. After that it will check if the event is an instance of
     * EventStoppable and if so it will add an extra check when looping trough the data.
     * If the Event was an instance of EventStoppable it will check every loop if the EventStoppable is stopped, and if
     * it is it will break the loop, thus stopping the call.
     * For every MethodData in the list it will invoke the Data's method with the Event as the argument.
     * After that is all done it will return the Event.
     *
     * @param event
     *         Event to dispatch.
     *
     * @return Event in the state after dispatching it.
     */
    public static final Event call(final Event event) {
        List<MethodData> dataList = REGISTRY_MAP.get(event.getClass());

        if (dataList != null) {
            if (event instanceof EventStoppable) {
                EventStoppable stoppable = (EventStoppable) event;

                for (final MethodData data : dataList) {
                    invoke(data, event);

                    if (stoppable.isStopped()) {
                        break;
                    }
                }
            } else {
                for (final MethodData data : dataList) {
                    invoke(data, event);
                }
            }
        }

        return event;
    }
    
    /**
     * Invokes a MethodData when an Event call is made.
     *
     * @param data
     *         The data of which the targeted Method should be invoked.
     * @param argument
     *         The called Event which should be used as an argument for the targeted Method.
     *         
     * TODO: Error messages.
     */
    private static void invoke(MethodData data, Event argument) {
        try {
            data.getTarget().invoke(data.getSource(), argument);
        } catch (IllegalAccessException e) {
        } catch (IllegalArgumentException e) {
        } catch (InvocationTargetException e) {
        }
    }
	
	/**
	 * 
	 * @author DarkMagician6
	 * @since January 2, 2014
	 */
	private static final class MethodData {

	    private final Object source;

	    private final Method target;

	    private final byte priority;

	    /**
	     * Sets the values of the data.
	     *
	     * @param source
	     *         The source Object of the data. Used by the VM to
	     *         determine to which object it should send the call to.
	     * @param target
	     *         The targeted Method to which the Event should be send to.
	     * @param priority
	     *         The priority of this Method. Used by the registry to sort
	     *         the data on.
	     */
	    public MethodData(Object source, Method target, byte priority) {
	        this.source = source;
	        this.target = target;
	        this.priority = priority;
	    }

	    /**
	     * Gets the source Object of the data.
	     *
	     * @return Source Object of the targeted Method.
	     */
	    public Object getSource() {
	        return source;
	    }

	    /**
	     * Gets the targeted Method.
	     *
	     * @return The Method that is listening to certain Event calls.
	     */
	    public Method getTarget() {
	        return target;
	    }

	    /**
	     * Gets the priority value of the targeted Method.
	     *
	     * @return The priority value of the targeted Method.
	     */
	    public byte getPriority() {
	        return priority;
	    }

	}

}
