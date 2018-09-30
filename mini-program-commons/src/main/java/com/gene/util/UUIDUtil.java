package com.gene.util;

import java.util.UUID;

/**
 * @author fengjian
 * @date 2017-3-28 下午1:11:31
 */
public class UUIDUtil {
	
	/**
	 * 生成32位uuid
	 * @return
	 * @author fengjian
	 */
	public static String randomUUID32(){
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid;
	}

}
