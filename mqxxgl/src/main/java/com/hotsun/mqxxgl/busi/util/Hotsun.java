package com.hotsun.mqxxgl.busi.util;

import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;


public class Hotsun
{
    public static boolean fileIsExists(String filepath) {

        File f = new File(filepath);
        if (!f.exists()) {
            return false;
        }
        return  true;
    }

            // }

//	// 操作类型查询
//	public final static String OPERTYPE_QUERY = "query";
//
//	// 操作类型增加
//	public final static String OPERTYPE_ADD = "add";
//
//	// 操作类型修改
//	public final static String OPERTYPE_EDIT = "upd";
//
//	// 操作类型注销
//	public final static String OPERTYPE_DEL = "del";
//
//	// 列表查询最大返回记录数
//	public final static Integer QUERY_MAXNUM = 30;
//
//	// 中心服务器注册地行政编码
//	public final static String SERVER_REGXZCODE = "520323";
//
//	public static byte[] blob2ByteArr(Blob blob)
//	{
//
//		byte[] b = null;
//		try
//		{
//			if (blob != null)
//			{
//				long in = 0;
//				b = blob.getBytes(in, (int) (blob.length()));
//			}
//		}
//		catch (Exception e)
//		{
//
//		}
//
//		return b;
//	}
//
//	// static Base64Encoder encoder = new sun.misc.BASE64Encoder();
//	//
//	// public static String getImageBinary(byte[] b) {
//	// String result = "";
//	// result = encoder.encodeBuffer(b);
//	// // 去掉得到的base64编码的换行符号
//	// Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//	// Matcher m = p.matcher(result);
//	// result = m.replaceAll("");
//	// return result;
//	// }
//	/**
//	 * 得到日期时间+6位随机码的时间戳，如：20160324175634839188
//	 *
//	 * @param value
//	 * @return
//
//
//
//
//	/**
//	 * 验证行政编码是否在注册地区域范围内
//	 *
//	 * @param xzcode
//	 *            验证xzCode是否在服务器的注册地区域内
//	 * @return xzCode在注册地内返回true,否则返回false
//	 */
//	public static Boolean xzcodeIsRegArea(String xzcode)
//	{
//
//		// 省级注册
//		if (SERVER_REGXZCODE.substring(2, 6).equals("0000"))
//		{
//			return SERVER_REGXZCODE.substring(0, 2).equals(
//					xzcode.substring(0, 2));
//		}
//
//		// 地级注册
//		if (SERVER_REGXZCODE.substring(4, 6).equals("00"))
//		{
//			return SERVER_REGXZCODE.substring(0, 4).equals(
//					xzcode.substring(0, 4));
//		}
//
//		// 县级注册
//		if (!SERVER_REGXZCODE.substring(4, 6).equals("00"))
//		{
//			return SERVER_REGXZCODE.substring(0, 6).equals(
//					xzcode.substring(0, 6));
//		}
//
//		return false;
//	}
//
//
//	/**
//	 * 将日期字符串转换为日期
//	 *
//	 * @param value
//	 * @return
//	 */
//	public static Date StringToDate(String value)
//	{
//		if (value != null && !value.equals(""))
//		{
//			SimpleDateFormat formatter;
//			formatter = new SimpleDateFormat("yyyy-MM-dd");
//			Date date = null;
//			try
//			{
//				date = formatter.parse(value);
//			}
//			catch (ParseException e)
//			{
//				e.printStackTrace();
//			}
//			return date;
//		}
//		return null;
//	}
//
//	/**
//	 * 将日期时间字符串转换为日期时间 含有时分秒
//	 *
//	 * @param value
//	 * @return
//	 */
//	public static Date StringToDatetime(String value)
//	{
//		if (value != null && !value.equals(""))
//		{
//			SimpleDateFormat formatter;
//			formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//			Date date = null;
//			try
//			{
//				date = formatter.parse(value);
//			}
//			catch (ParseException e)
//			{
//				e.printStackTrace();
//			}
//			return date;
//		}
//		return null;
//	}
//
	/**
	 * 根据参数日期的值，转换为YYYY-MM-DD字符串格式返回
	 *
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String _date = formatter.format(date);
		return _date;
	}

	/**
	 * 根据参数日期的值，转换为yyyy-MM-dd HH:mmss:SSS字符串格式返回
	 *
	 * @param date
	 * @return
	 */
	public static String timestampToString(Date date)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String _date = formatter.format(date);
		return _date;
	}
//
//	public static String timestampToString2(Date date)
//	{
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
//		String _date = formatter.format(date);
//		return _date;
//	}
//
//	/**
//	 * 功能说明：比较两个日期的大小
//	 *
//	 * @param DATE1
//	 * @param DATE2
//	 * @return 如果date1>date2返回1 如果date1=date2返回0 如果date1<date2返回-1
//	 */
//	public static int dec_timestamp(String DATE1, String DATE2)
//	{
//		try
//		{
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Date dt1 = df.parse(DATE1);
//			Date dt2 = df.parse(DATE2);
//			if (dt1.getTime() > dt2.getTime())
//			{
//				return 1;
//			}
//			else if (dt1.getTime() < dt2.getTime())
//			{
//				return -1;
//			}
//			else
//			{
//				return 0;
//			}
//		}
//		catch (Exception exception)
//		{
//			exception.printStackTrace();
//		}
//		return 0;
//	}
//
//	/**
//	 * 功能说明：比较两个日期的大小
//	 *
//	 * @param DATE1
//	 * @param DATE2
//	 * @return 如果date1>date2返回1 如果date1=date2返回0 如果date1<date2返回-1
//	 */
//	public static int dec_date(String DATE1, String DATE2)
//	{
//		try
//		{
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//			Date dt1 = df.parse(DATE1);
//			Date dt2 = df.parse(DATE2);
//			if (dt1.getTime() > dt2.getTime())
//			{
//				return 1;
//			}
//			else if (dt1.getTime() < dt2.getTime())
//			{
//				return -1;
//			}
//			else
//			{
//				return 0;
//			}
//		}
//		catch (Exception exception)
//		{
//			exception.printStackTrace();
//		}
//		return 0;
//	}
//
//	/**
//	 *
//	 * @按照格式YYYY-MM-DD,返回当前系统日期
//	 */
//	public static String getYYYY_MM_DD()
//	{
//		SimpleDateFormat formatter;
//		formatter = new SimpleDateFormat("yyyy-MM-dd");
//		return formatter.format(new Date());
//	}
//
//	/**
//	 * 根据参数日期的值，转换为YYYY-MM-DD字符串格式返回
//	 *
//	 * @param time
//	 * @return
//	 */
//	public static String dateToString(Calendar time)
//	{
//		SimpleDateFormat formatter;
//		formatter = new SimpleDateFormat("yyyy-MM-dd");
//		String ctime = formatter.format(time.getTime());
//		return ctime;
//	}
//
//
//
//	/**
//	 * 对象转换为接送
//	 *
//	 * @param object
//	 * @return String
//	 */
//	public static String objectToJson(Object object)
//	{
//		ObjectMapper mapper = new ObjectMapper();
//		StringWriter sWriter = new StringWriter();
//		JsonGenerator generator = null;
//		String json = "";// json数据
//
//		try
//		{
//			generator = new JsonFactory().createJsonGenerator(sWriter);
//			mapper.writeValue(generator, object);
//			generator.close();
//			json = sWriter.toString();
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//
//		return json;
//	}
//
//
//	/**
//	 * 将json字符串转换为值对象
//	 *
//	 * @param json
//	 * @param objectClass
//	 *            值对象类
//	 * @return Object
//	 */
//	public static Object jsonToObject(String json, Class objectClass)
//	{
//		Object objectInstance = null;
//		ObjectMapper objectMapper = new ObjectMapper();
//		try
//		{
//			objectInstance = objectMapper.readValue(json, objectClass);
//		}
//		catch (JsonParseException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (JsonMappingException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return objectInstance;
//	}
//
//	/**
//	 * 将json字符串转换为指定类名的类
//	 *
//	 * @param json
//	 * @param className
//	 *            值对象类名
//	 * @return Object
//	 */
//	public static Object jsonToObject(String json, String className)
//	{
//		String[] arrStr = className.split(",");
//		Object objectInstance = null;
//		try
//		{
//			Class<?> clazz = Class.forName("com.capinfo.pcmis.valueobjects."
//					+ arrStr[0]);
//			ObjectMapper objectMapper = new ObjectMapper();
//			if (arrStr.length == 1)
//			{
//				objectInstance = objectMapper.readValue(json, clazz);
//			}
//			else
//			{
//				JavaType javaType = objectMapper.getTypeFactory()
//						.constructParametricType(List.class, clazz);
//				objectInstance = objectMapper.readValue(json, javaType);
//			}
//
//		}
//		catch (JsonParseException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (JsonMappingException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (ClassNotFoundException e)
//		{
//			e.printStackTrace();
//		}
//		return objectInstance;
//	}
//
//	public static List<Map<String, Object>> jsonToListMap(String json)
//	{
//		List<Map<String, Object>> objectInstance = null;
//		try
//		{
//			ObjectMapper objectMapper = new ObjectMapper();
//			JavaType javaType = objectMapper.getTypeFactory()
//					.constructParametricType(List.class, Map.class);
//			objectInstance = objectMapper.readValue(json, javaType);
//
//		}
//		catch (JsonParseException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (JsonMappingException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		return objectInstance;
//	}
//
//	/**
//	 * 后一个BigDecimal类型的数据不为空时，与前一个BigDecimal类型数据比较是否发生变化
//	 *
//	 * @param value1
//	 * @param value2
//	 * @return 0未发变化，1发生变化
//	 */
//	public static int compareToBigDecimal(BigDecimal value1, BigDecimal value2)
//	{
//		if (null != value1 && !"".equals(value1))
//		{
//			if (null != value2 && !"".equals(value2))
//			{
//				if (value1.compareTo(value2) != 0)
//				{
//					return 1;
//				}
//			}
//		}
//		else
//		{
//			if (null != value2 && !"".equals(value2))
//			{
//				return 1;
//			}
//		}
//		return 0;
//	}
//
//	/**
//	 * 通过上报时间间隔和统计年月计算报表统计的开始日期和结束日期
//	 *
//	 * @param reportinterval
//	 * @param schedulerDate
//	 * @return
//	 */
//	public static Map<String, String> getBeginAndEndDate(short reportinterval,
//			String reporttimeunit, String schedulerDate)
//	{
//		Map<String, String> map = new HashMap<String, String>();
//		String[] dateArray = schedulerDate.split("-");
//		String syear = dateArray[0];
//		String smonth = dateArray[1];
//		int iyear = Integer.parseInt(syear);
//		int imonth = Integer.parseInt(smonth);
//		String beginWorkTime = null;
//		String endWorkTime = null;
//
//		if (reportinterval == 0)// 0表示每周、每月、每季度、每半年、每年
//		{
//			if ("10".equals(reporttimeunit))// 10：周报
//			{
//				// 需要算出是该周是年度内的第几周，传递给下面的方法后，需要算出该周的星期天-星期六的号数
//			}
//			else if ("20".equals(reporttimeunit))// 月报表
//			{
//				if (imonth == 2)
//				{
//					beginWorkTime = syear + "-02-01";
//					endWorkTime = syear + "-02-28";
//				}
//				else if (imonth == 1 || imonth == 3 || imonth == 5
//						|| imonth == 7 || imonth == 8 || imonth == 10
//						|| imonth == 12)
//				{
//					if (imonth == 10 || imonth == 12)
//					{
//						beginWorkTime = syear + "-" + smonth + "-01";
//						endWorkTime = syear + "-" + smonth + "-31";
//					}
//					else
//					{
//						beginWorkTime = syear + "-0" + smonth + "-01";
//						endWorkTime = syear + "-0" + smonth + "-31";
//					}
//				}
//				else
//				{
//					if (imonth == 11)
//					{
//						beginWorkTime = syear + "-" + smonth + "-01";
//						endWorkTime = syear + "-" + smonth + "-30";
//					}
//					else
//					{
//						beginWorkTime = syear + "-0" + smonth + "-01";
//						endWorkTime = syear + "-0" + smonth + "-30";
//					}
//				}
//			}
//			else if ("30".equals(reporttimeunit))// 季度报表
//			{
//				if (imonth == 1)// 第一季度
//				{
//					beginWorkTime = (iyear - 1) + "-10-01";
//					endWorkTime = syear + "-12-31";
//				}
//				else if (imonth == 2)// 第二季度
//				{
//					beginWorkTime = syear + "-01-01";
//					endWorkTime = syear + "-03-31";
//				}
//				else if (imonth == 3)// 第三季度
//				{
//					beginWorkTime = syear + "-04-01";
//					endWorkTime = syear + "-06-30";
//				}
//				else
//				// 第四季度
//				{
//					beginWorkTime = syear + "-07-01";
//					endWorkTime = syear + "-09-30";
//				}
//
//			}
//			else if ("40".equals(reporttimeunit))// 半年度报表
//			{
//				if (imonth == 1)// 上半年
//				{
//					beginWorkTime = (iyear - 1) + "-10-01";
//					endWorkTime = syear + "-03-31";
//				}
//				else
//				// 下半年
//				{
//					beginWorkTime = syear + "-04-01";
//					endWorkTime = syear + "-09-30";
//				}
//			}
//			else if ("50".equals(reporttimeunit))// 年度报表
//			{
//				beginWorkTime = (iyear - 1) + "-10-01";
//				endWorkTime = syear + "-09-30";
//			}
//		}
//		else
//		// >0表示具体的间隔单位，如=1且上报时间单位是20（月）表示间隔1月上报一次
//		{
//			// 间隔几个月的需要另外计算,留作保留，规则确定后再实现
//		}
//
//		map.put("beginWorkTime", beginWorkTime);
//		map.put("endWorkTime", endWorkTime);
//
//		return map;
//	}
//
//	/**
//	 *将执行月、执行周、执行天 转换为日期时间
//	 *
//	 */
//	public static Date mWDHToDateTime(String runMonth, String runWeek,
//			String runDay, String runHour)
//	{
//
//		String smonth = runMonth;
//		String srunweek = runWeek;
//		String sday = runDay;
//		String shour = runHour;
//
//		Calendar calendar = Calendar.getInstance();
//		int year = calendar.get(Calendar.YEAR);// 自然年度
//		int imonth = Integer.parseInt(smonth);
//		int irunweek = Integer.parseInt(srunweek);
//		int iday = Integer.parseInt(sday);
//		int ihour = Integer.parseInt(shour);
//
//		calendar.set(Calendar.YEAR, year);
//		/**
//		 * t_sys_reportRunParam表中的runMonth（执行月）和runWeek（执行周）的值均为0时，表示调度任务每月执行;
//		 * 当runMonth（执行月）值为1-12中的具体数字时，表示调度任务是某月才执行；
//		 * 当t_sys_reportRunParam表中的runMonth
//		 * （执行月）=0并且runWeek（执行周）的值均为>0时，表示调度任务按周执行，当runWeek（执行月）值为1-52中的具体数字时，
//		 * 表示调度任务是当年的某周才执行， 当runWeek（执行月）值为53时表示调度任务每周执行；
//		 * 当调度任务是月执行时，runDay（执行天）表示执行月的几号执行调度任务
//		 * ；当调度任务是周执行时，runDay（执行天）表示是周几（1-7对应周一至周日）执行调度任务。
//		 */
//		if (imonth == 0 && irunweek == 0)// 每月执行
//		{
//			imonth = calendar.get(Calendar.MONTH);// 月份
//			int day = calendar.get(Calendar.DAY_OF_MONTH);// 自然号数
//			if (day > iday)// 如果指定的执行天小于系统日期的号数，则执行日期为下个月的对应执行天
//			{
//				calendar.set(Calendar.MONTH, imonth + 1);
//			}
//			else
//			// 如果指定的执行天大于系统日期的号数，则执行日期为本月的对应执行天
//			{
//				calendar.set(Calendar.MONTH, imonth);
//			}
//
//			calendar.set(Calendar.DAY_OF_MONTH, iday);
//		}
//		else if (imonth > 0)// 指定月才执行
//		{
//			calendar.set(Calendar.MONTH, Integer.parseInt(smonth) - 1);
//			calendar.set(Calendar.DAY_OF_MONTH, iday);
//		}
//		else if (imonth == 0 && (irunweek >= 1 && irunweek <= 52))// 表示调度任务是当年的某周才执行
//		{
//			// 当执行周的值>0时，执行天介于1-7之间，表示周一至周日
//			Locale.setDefault(Locale.TRADITIONAL_CHINESE);
//			calendar.set(Calendar.WEEK_OF_YEAR, irunweek);
//			calendar.set(Calendar.DAY_OF_WEEK, iday + 1);
//		}
//		else if (irunweek == 53)// 每周执行
//		{
//			// 当执行周的值>0时，执行天介于1-7之间，表示周一至周日
//			Locale.setDefault(Locale.TRADITIONAL_CHINESE);
//			calendar.set(Calendar.DAY_OF_WEEK, iday + 1);
//		}
//
//		calendar.set(Calendar.HOUR_OF_DAY, ihour);
//
//		calendar.set(Calendar.MINUTE, 00);
//		calendar.set(Calendar.SECOND, 00);
//
//		Date time = calendar.getTime();
//
//		return time;
//	}

    }
