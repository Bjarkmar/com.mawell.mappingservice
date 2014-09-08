package com.mawell.mappingservice.batch;

import javax.ejb.Stateless;

@Stateless
public class ConfigurationTest {
	String test = "test";
	
	public String getInfo(){
		return test;
	}
}
