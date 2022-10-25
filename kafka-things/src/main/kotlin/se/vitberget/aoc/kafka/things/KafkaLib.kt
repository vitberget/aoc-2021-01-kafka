package se.vitberget.aoc.kafka.things

import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.common.errors.TopicExistsException
import java.util.*
import java.util.concurrent.ExecutionException

fun kafkaTo(queue:String, content:String) {
    println("kafkaTo $queue $content")
}

fun createTopic(topic: String,
                partitions: Int,
                replication: Short,
                cloudConfig: Properties
) {
    val newTopic = NewTopic(topic, partitions, replication)

    try {
        with(AdminClient.create(cloudConfig)) {
            createTopics(listOf(newTopic))
                .all()
                .get()
        }
    } catch (e: ExecutionException) {
        if (e.cause !is TopicExistsException) throw e
    }
}