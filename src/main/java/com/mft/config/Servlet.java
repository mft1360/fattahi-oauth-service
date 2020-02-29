package com.mft.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:/config/Servlet.xml" })
public class Servlet {

}
