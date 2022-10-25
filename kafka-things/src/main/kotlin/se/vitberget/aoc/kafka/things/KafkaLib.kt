package se.vitberget.aoc.kafka.things

import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.errors.TopicExistsException
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import java.io.FileInputStream
import java.time.Duration
import java.util.*
import java.util.concurrent.ExecutionException

private val cloudConfig = loadPropFile("/home/k/src/aoc/aoc-kafka/kafka.properties")

private fun loadPropFile(filename: String): Properties =
    Properties().apply {
        FileInputStream(filename).use { fis ->
            load(fis)
        }
    }

private val producerConfig =
    loadPropFile("/home/k/src/aoc/aoc-kafka/kafka.properties").apply {
        this["key.serializer"] = StringSerializer::class.java
        this["value.serializer"] = StringSerializer::class.java
    }

fun kafkaTo(topic: String, content: String) {
    kafkaTo(topic, UUID.randomUUID().toString(), content)
}

fun kafkaTo(topic: String, key: String, content: String) {
    println("kafkaTo $topic")
    KafkaProducer<String, String>(producerConfig)
        .send(ProducerRecord(topic, key, content))
}

private fun consumerConfig(groudId: String) =
    loadPropFile("/home/k/src/aoc/aoc-kafka/kafka.properties").apply {
        this["group.id"] = groudId
        this["key.deserializer"] = StringDeserializer::class.java
        this["value.deserializer"] = StringDeserializer::class.java
    }

fun kafkaFrom(topic: String, groupId: String, action: (ConsumerRecord<String, String>) -> Unit) {
    val consumer = KafkaConsumer<String, String>(consumerConfig(groupId)).apply {
        subscribe(listOf(topic))
    }

    while (true) {
        val records = consumer.poll(Duration.ofSeconds(60))
        records.forEach(action)
    }
}

fun createTopic(topic: String) {
    println("create topic $topic")
    val newTopic = NewTopic(topic, 1, 1)

    try {
        with(AdminClient.create(cloudConfig)) {
            createTopics(listOf(newTopic))
                .all()
                .get()
        }
    } catch (e: ExecutionException) {
        if (e.cause is TopicExistsException)
            println("topic $topic already exists")
        else {
            e.printStackTrace()
            throw e
        }
    }
}