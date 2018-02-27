package com.emcloud.arc.messaging;

import com.emcloud.arc.analysis.service.AlarmService;
import com.emcloud.arc.domain.SmartMeterData;
import com.emcloud.domain.SmartMeterDataMsg;
import com.emcloud.message.event.MeterDataMsgEvent;
import com.emcloud.message.event.MeterStatusMsgEvent;
import com.emcloud.message.event.ObjectMessageEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.uuid.impl.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 */
@Service
public class ConsumerService {
    private final Logger log = LoggerFactory.getLogger(ConsumerService.class);
    ExecutorService service = new ScheduledThreadPoolExecutor(10);
    @Autowired
    AlarmService alarmService ;
//
//    @StreamListener(ConsumerChannel.CHANNEL)
//    public void consume(AbstractMessageEvent messageEvent) {
//        log.info("Received Ser type:{} action:{} message: {}.", messageEvent.getType(), messageEvent.getAction(), messageEvent.getMessage().toString());
//    }

//    @StreamListener(ConsumerChannel.CHANNEL)
//    public void consume(String messageEvent) {
//        log.info("Received Ser type:{} action:{} message: {}.", messageEvent );
//    }

    @StreamListener(ConsumerChannel.CHANNEL)

    public void consume(String msg) {
        log.info("Received Ser msg:{}.", msg );
        ObjectMapper mapper = new ObjectMapper();
        ObjectMessageEvent messageEvent = null;
        try {
            messageEvent = mapper.readValue(msg, ObjectMessageEvent.class);
            Object message = messageEvent.getMessage();
            String type = messageEvent.getType();
            log.info("Received Ser type:{} action:{} seq: {}.", messageEvent.getType(), messageEvent.getAction(), messageEvent.getSeq());

            this.handleData(msg,type);
//            handleData(messageEvent);
//            if( type.equals( MeterDataMsgEvent.METER_DATA_TYPE )){
//                List<SmartMeterData> datas = (List<SmartMeterData> ) message;
//                log.info("message size is {} " , datas.size() );
//            }else if( type.equals( MeterStatusMsgEvent.METER_STATUS_TYPE  )){
//                List<SmartMeterStatus> statuses = (List<SmartMeterStatus> ) message;
//                log.info("message size is {} " , statuses.size() );
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleData(String message, String type) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        if (type.equals(MeterDataMsgEvent.METER_DATA_TYPE)) {
//            mapper.readValues(message, MeterDataMsgEvent.class);
            MeterDataMsgEvent messageEvent = mapper.readValue(message, MeterDataMsgEvent.class );
            List<SmartMeterDataMsg> lists = messageEvent.getMessage();
            for ( SmartMeterDataMsg msg: lists ){
                SmartMeterData data = new SmartMeterData();
                BeanUtils.copyProperties(msg , data );
                data.setId(UUID.fromString( msg.getId() ) );
                data.setMeterId( UUID.fromString(msg.getMeterId() ));
                data.setServerId( UUID.fromString( msg.getServerId() ));
                data.setCompanyId( UUID.fromString( msg.getCompanyId() ));
                service.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            alarmService.analysis(data);
                        }catch(Throwable ex){
                            ex.printStackTrace();
                        }
                    }
                });
            }
            log.info( "size is {}" ,  messageEvent.getMessage().size() );
        } else if (type.equals(MeterStatusMsgEvent.METER_STATUS_TYPE)) {
            MeterStatusMsgEvent messageEvent = mapper.readValue(message, MeterStatusMsgEvent.class);
            log.info( "size is {}" ,  messageEvent.getMessage().size() );
        }

    }
}
