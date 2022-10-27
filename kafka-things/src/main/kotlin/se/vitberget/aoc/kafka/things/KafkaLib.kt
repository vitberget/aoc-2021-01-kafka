package se.vitberget.aoc.kafka.things

import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
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
        this[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        this[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
    }

private val producer = KafkaProducer<String, String>(producerConfig)

fun kafkaTo(topic: String, content: String) {
    kafkaTo(topic, UUID.randomUUID().toString(), content)
}

fun kafkaTo(topic: String, key: String, content: String) {
    println("kafkaTo $topic")
    producer.send(ProducerRecord(topic, key, content))
}

private fun consumerConfig(groudId: String) =
    loadPropFile("/home/k/src/aoc/aoc-kafka/kafka.properties").apply {
        this["group.id"] = groudId
        this[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        this[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        this[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
        // this[ConsumerConfig.ISOLATION_LEVEL_CONFIG] = "read_committed"
    }

fun kafkaFrom(topic: String, groupId: String, action: (ConsumerRecord<String, String>) -> Unit) {
    val consumer = KafkaConsumer<String, String>(consumerConfig(groupId)).apply {
        subscribe(listOf(topic))
    }

    while (true) {
        val records = consumer.poll(Duration.ofSeconds(1))
        records.forEach(action)
    }
}

fun createTopics(vararg topics: String) {
    try {
        println("create topics: ${topics.joinToString()}")
        with(AdminClient.create(cloudConfig)) {
            createTopics(topics.map { NewTopic(it, 1, 1) })
                .all()
                .get()
        }
    } catch (e: ExecutionException) {
        if (e.cause !is TopicExistsException) {
            e.printStackTrace()
            throw e
        }
    }
}