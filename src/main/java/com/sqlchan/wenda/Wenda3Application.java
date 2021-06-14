package com.sqlchan.wenda;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//有时间轴，可用
@SpringBootApplication
public class Wenda3Application {

	public static void main(String[] args) {
		SpringApplication.run(Wenda3Application.class, args);
	}
}
