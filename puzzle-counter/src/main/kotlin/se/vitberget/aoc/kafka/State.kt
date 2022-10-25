package se.vitberget.aoc.kafka

import java.util.concurrent.ConcurrentHashMap

val memoryLeak = ConcurrentHashMap<String, Int>()