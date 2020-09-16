package cn.wolfshadow.gs.common.util;

/**
 * 进制间处理
 * @author ServerZhang
 * @date 2015年8月1日
 */
public class NumberSystemUtil {

	/**
	 * 字节数组转换成十六进制字符串
	 * @param bytes
	 * @return
	 * @author ServerZhang
	 * @date 2015年8月1日
	 */
	public static String bytes2HexStr(byte bytes[]) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0,length=bytes.length; i < length; i++) {
			String hexStr = Integer.toHexString(bytes[i] & 0xFF);
			if (hexStr.length() == 1) {
				hexStr = '0' + hexStr;
			}
			sBuffer.append(hexStr.toUpperCase());
		}
		return sBuffer.toString();
	}
	
	/**
	 * 十六进制字符串转换成字节数组
	 * @param hexStr
	 * @return
	 * @author ServerZhang
	 * @date 2015年8月1日
	 */
	public static byte[] hexStr2Bytes(String hexStr){
		if(hexStr == null || hexStr.length() < 1){
			return new byte[0];
		}
		int length = hexStr.length() / 2;
		byte[] bytes = new byte[length];
		for(int i=0; i<length; i++){
			int highBit = Integer.parseInt(hexStr.substring(i*2,i*2+1),16);
			int lowBit = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
			bytes[i] = (byte) (highBit * 16 + lowBit);
		}
		return bytes;
	}
}
