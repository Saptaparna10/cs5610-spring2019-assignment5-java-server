package com.example.cs5610spring2019assignment5serverjava.services;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class IdGenerator {

    private static final AtomicInteger sequence = new AtomicInteger(1);

    private IdGenerator() {}

    private static final ConcurrentHashMap<Class,AtomicInteger> mapper
    		= new ConcurrentHashMap<Class,AtomicInteger>();

    public static int generateId (Class _class) {
        mapper.putIfAbsent(_class, new AtomicInteger(1));
        return mapper.get(_class).getAndIncrement();
    }

}
