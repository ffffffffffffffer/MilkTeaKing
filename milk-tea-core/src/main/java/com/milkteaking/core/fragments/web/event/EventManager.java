package com.milkteaking.core.fragments.web.event;

import android.support.annotation.NonNull;

import java.util.HashMap;

/**
 * @author TanJJ
 * @time 2018/5/20 8:54
 * @des 处理动作的管理类
 */

public class EventManager {
    private HashMap<String, Event> events = new HashMap<>();

    private EventManager() {
    }

    private static class Holder {
        private static final EventManager INSTANCE = new EventManager();
    }

    public static EventManager getInstance() {
        return Holder.INSTANCE;
    }

    public EventManager addAction(@NonNull String key, @NonNull Event event) {
        events.put(key, event);
        return this;
    }

    public Event createEvent(@NonNull String action) {
        Event event = events.get(action);
        if (event == null) {
            return new UndefindEvent();
        }
        return event;
    }
}
