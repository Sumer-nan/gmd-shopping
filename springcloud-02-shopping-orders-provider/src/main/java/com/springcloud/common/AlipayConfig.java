package com.springcloud.common;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016101100662542";

	// 商户私钥，您的PKCS8格式RSA2私钥
	public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCtWHnwWmt9ycSgWW5WCkhFSimXwsW4G1u+CNicWqCG26ZunQefJYMq9nqO7dCeI66o54XZdEaGkhtC+mKPq93rOKZkk8yBg82tHb0MImuApcdJWhZ41NsErNVpaLG0ykBmhMS5ftWayfZPUKO23Pw/K2NKE2Lz/Sh09YJR13BFOuq7K0ixKH4poTJR9smvW1kNPVeB4t17UNiWqzzoNjYp1o5Jwz3vtag+XPtitrYbweG/j5Om97l+r/vblUI/91jr/FdTzqVHOwoOz8j8aLXgGcomhK89W5dm3dQcDEjmMXeFOZdX/9i/aBVVClXVMaKp+7G4E0WARo28DeXp+PqlAgMBAAECggEAA7wYRHluP3ONbPcATZJDJFsIsmBb6i+MtndyU0wQkVdCKrfvZhpkzRQqZ9Hv4LhcFv+F/r1ml5emRrcmUHofeeXqj9gvW8r/L2+olw5WyprHxGjQeyH65grjPjYnPgHtiUfrBSQabg9T4zmVjWcNeD7zELGCPEeEC6EOgbzmildFhyzCdIhxMXowEXSFmtLK6AXkvYh6AcSGCQwD1VbUAGY7clWyg+JXVBwlCCdOY1L7mb4/r3NLJ82PcPNvWi9kZTctC6cJVG71h8FcQf8Z950jROOhXdbaA26iOZXIv8fXqZQf9ohT69j95cKbLwLguzC8os+S4sodA0e2CDoXMQKBgQDgEm4wFyRHw5qpWTHQTDSyMOOzossCYi+QC6T97q6ZSkakd3XrZpbEe/YkcLOk2sHWYqW/Vg7lgYtqa4Q6YiInwmzr7WDyx0+6wwvKI3NDTlXk3nC5jvRPtpt+53nJbRLrT5XUZroRaxU8Ew453zMIX7g7tOws3VrutOzvorhR/wKBgQDGC62SM/Ye1/8AslEcUPLZHXlpO7MifMAj/2lwxNT3qjd3IpfdfVys7I8+mL/jFrB3Sbj7K2Iw+tZh1/nE2m8xxwJRgT8bBsGd2hu+a5Ff8mF4Iddbq3aJRM9y5j9miD3VMscEdHu5hL9QazsIFkFecpv36Egl/KpjTssmJ1IrWwKBgG32XYDYOoPFaIcnMJ0KaOnxuC0V/Q6kZLcKH+RGWAT+QIDB52qoo+C0/OyRKngCqxGsw66May8STfrh3LhSJHTio1V83xEF4wjCY2zFzPEA5oI075kAoMDl2pWCv2+sSZaVLfBYoYOey4L5zwdzk7XOU7lpZmX2E4QF4ikP7+i9AoGBAK2P8Hav+SOA0mdu3uy8+IdUcGHMQyqXP+CMZ4C/z5CZaXCMLaGbywt1afhG7+v5p39zaZ0doTkhq54mJCvj7w0lxp9dcMujuS04x5WzOb1ncwpQRid9di3I0zF0Hd4iymW80XPTDddBQlUdFKf6sSEvugaiWBCKhVzw4dK3JSj/AoGBANZTT6o9LxzwaLXwMZ0d6Q9qwHHrXi/IMjlkmtzYSgnE12op3mU5+fJ+jMdd19acaf9uxeUn7eRp7XPaaW4lI435psxuyZeufcFFq4EyDX7xDsnOX6Yvxrm5xvTzQa/AM18Ofug24Rwe9f60u03moP1iAXL8bvlvAkO+T0NvoGyS";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArVh58FprfcnEoFluVgpIRUopl8LFuBtbvgjYnFqghtumbp0HnyWDKvZ6ju3QniOuqOeF2XRGhpIbQvpij6vd6zimZJPMgYPNrR29DCJrgKXHSVoWeNTbBKzVaWixtMpAZoTEuX7Vmsn2T1Cjttz8PytjShNi8/0odPWCUddwRTrquytIsSh+KaEyUfbJr1tZDT1XgeLde1DYlqs86DY2KdaOScM977WoPlz7Yra2G8Hhv4+Tpve5fq/725VCP/dY6/xXU86lRzsKDs/I/Gi14BnKJoSvPVuXZt3UHAxI5jF3hTmXV//Yv2gVVQpV1TGiqfuxuBNFgEaNvA3l6fj6pQIDAQAB";

	// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost:9001/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:9001/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";

	// 字符编码格式
	public static String charset = "utf-8";

	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

	// 支付宝网关
	public static String log_path = "C:\\";

//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	/**
	 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	 * 
	 * @param sWord 要写入日志里的文本内容
	 */
	public static void logResult(String sWord) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
