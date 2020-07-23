package com.silan.generator;

import java.util.UUID;

public class LoginIdGenerator {

//Login Id Generator
public static  String loginID() {
		
		//Generate UUID Code
		String code=UUID.randomUUID()
				.toString()
				.replaceAll("-", "")
				.substring(0,8);
		
		return code;
	}


}
