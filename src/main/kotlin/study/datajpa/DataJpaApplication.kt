package study.datajpa

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
class DataJpaApplication

fun main(args: Array<String>) {
    SpringApplication.run(DataJpaApplication::class.java, *args)
}