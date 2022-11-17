package com.jiang.springbootniosocketserver.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jiang.socket.server")
public class SocketProperties {

    private Integer port;
    private Integer timeOuts;
    private String bindAdds;
}
