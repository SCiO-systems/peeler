package org.peeler.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.peeler.beans.PeelerBean;
import org.peeler.models.Result;
import org.peeler.models.Peel;
import org.peeler.models.KafkaMessage;

public class PeelerRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {


//        from("file:C:/Users/User/Documents/Usefull_Files_Projectwise/CiGi/sieverOutput?fileName=00029f3d99d17a10540604b701d690ed.json&noop=true")
//                .routeId("kafka_consumer")
//                .unmarshal().json(JsonLibrary.Jackson, Result.class)
//                //.bean(PeelerBean.class, "printResult")
//                .bean(PeelerBean.class, "peelResult")
//                .setHeader(KafkaConstants.KEY, simple("882f46dc-9654-474a-940a-9a08ce7bc084"))
//                .split(body())
//                .marshal().json(JsonLibrary.Jackson, Peel.class)
//                .to("kafka:{{KAFKA_OUTPUT_TOPIC}}?brokers={{KAFKA_BROKER}}");
//                //.to("direct:processResult");


//        from("kafka:{{KAFKA_INPUT_TOPIC}}?brokers={{KAFKA_BROKER}}&autoOffsetReset=earliest")
//                .routeId("kafka_consumer")
//                //.unmarshal().json(JsonLibrary.Jackson, KafkaMessage.class)
//                .process(exchange -> {
//                    // Extract key and value from Kafka message
//                    String key = exchange.getIn().getHeader("kafka.KEY", String.class);
//                    String value = exchange.getIn().getBody(String.class);
//
//                    System.out.println("Received Message Key: " + key);
//                    System.out.println("Received Message Value: " + value);
//
//                    // Extract s3FileName from the Kafka message value
//                    String s3FileName = exchange.getContext().getTypeConverter().convertTo(KafkaMessage.class, value).getS3FileName();
//                    System.out.println("S3Filename: " + s3FileName);
//
//                });


        from("kafka:{{KAFKA_INPUT_TOPIC}}?brokers={{KAFKA_BROKER}}&autoOffsetReset=earliest")
                .unmarshal().json(JsonLibrary.Jackson, KafkaMessage.class)
                .process(exchange -> {
                    // Extract key and value from Kafka message
                    String key = exchange.getIn().getHeader("kafka.KEY", String.class);
                    String value = exchange.getIn().getBody(String.class);

                    System.out.println("Received Message Key: " + key);


                    // Extract s3FileName from the Kafka message value
                    String s3FileName = exchange.getIn().getBody(KafkaMessage.class).getS3FileName();
                    System.out.println("Filename"+ s3FileName);
                    // Read the file from AWS S3 bucket
                    String s3ObjectContent =
                        "aws-s3://{{AWS_BUCKET}}?fileName="+ s3FileName +".json" +
                        "&accessKey=RAW({{AWS_S3_ACCESS_KEY}})&" +
                        "secretKey=RAW({{AWS_S3_SECRET_KEY}})&" +
                        "region={{AWS_REGION}}&" +
                        "deleteAfterWrite=false&" +
                        "deleteAfterRead=false&operation=downloadObject";

                    //System.out.println(s3ObjectContent);
                    exchange.getIn().setHeader(KafkaConstants.KEY, simple(key));
                    // Set the file content as the message body
                    //exchange.getIn().setBody(s3ObjectContent, String.class);
                    exchange.getIn().setHeader(s3FileName, String.class);
                })
                .process(exchange -> {
                    System.out.println(exchange.getIn().getBody().toString());
                })
                .to("aws-s3://{{AWS_BUCKET}}?fileName=${in.headers.s3FileName}" +".json" +
                        "&accessKey=RAW({{AWS_S3_ACCESS_KEY}})&" +
                        "secretKey=RAW({{AWS_S3_SECRET_KEY}})&" +
                        "region={{AWS_REGION}}&" +
                        "deleteAfterWrite=false&" +
                        "deleteAfterRead=false&operation=downloadObject")
                .process(exchange -> {
                    System.out.println(exchange.getIn().getBody().toString());
                })
                .unmarshal().json(JsonLibrary.Jackson, Result.class)
                .bean(PeelerBean.class, "peelResult")
                .split(body())
                .marshal().json(JsonLibrary.Jackson, Peel.class)
                .to("kafka:{{KAFKA_OUTPUT_TOPIC}}?brokers={{KAFKA_BROKER}}");


    }
}
