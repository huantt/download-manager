package com.huantt.downloader;

import org.dangnh.xmlconfig.annotation.DefaultValue;
import org.dangnh.xmlconfig.annotation.Key;
import org.dangnh.xmlconfig.annotation.Source;

/**
 * Created by Huan on 7/11/2016.
 */
@Source("file:./config/RMQConfig.xml")  /*URI đến file config*/
public interface RmqExchangeDeclareConfig{
    @Key("rmq-config.consumer.exchange-declare")
    @DefaultValue("tms-topic") /*giá trị mặc định*/
    String name();

    @Key("rmq-config.consumer.exchange-declare.type")
    @DefaultValue("topic")
    String excType();

    @Key("rmq-config.consumer.exchange-declare.durable")
    @DefaultValue("true")
    boolean isDurable();

    @Key("rmq-config.consumer.exchange-declare.auto-delete")
    @DefaultValue("false")
    boolean isAutoDelete();
}
