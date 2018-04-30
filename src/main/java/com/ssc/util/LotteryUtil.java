package com.ssc.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class LotteryUtil {

	//public static  final  String noPath =  "C:" + File.separator + "Users"+ File.separator + "zxm" + File.separator + "log" + File.separator;
	public static  final  String noPath = "D:" + File.separator  + "log" + File.separator;

	//定位abc-aed-c转成aac,aec等
	/*public static List<LotteryDetail> convertStr2DetailList(String src) {
		if (StringUtils.isEmpty(src)) {
			return null;
		}
		String[] srcArray = src.split("-");
		List<List<String>> srcList = new ArrayList<List<String>>();
		for (String item : srcArray) {
			List<String> dimValueItem = new ArrayList<String>();
			for (char c : item.toCharArray()) {
				dimValueItem.add(String.valueOf(c));
			}
			srcList.add(dimValueItem);
		}

		List<List<String>> recursiveResult = new ArrayList<List<String>>();
		// 递归实现笛卡尔积
		recursive(srcList, recursiveResult, 0, new ArrayList<String>());

		System.out.println("递归实现笛卡尔乘积: 共 " + recursiveResult.size() + " 个结果");
		for (List<String> list : recursiveResult) {
			for (String string : list) {
				System.out.print(string + " ");
			}
			System.out.println();
		}
		
		List<LotteryDetail> result = new ArrayList<LotteryDetail>();
		if (srcArray.length == 2) {
			//二星
			for (List<String> list : recursiveResult) {
				LotteryDetail detail  = new LotteryDetail();
				detail.setNum2(list.get(0));
				detail.setNum1(list.get(1));
				result.add(detail);
			}
		} else if (srcArray.length == 3) {
			for (List<String> list : recursiveResult) {
				LotteryDetail detail  = new LotteryDetail();
				detail.setNum3(list.get(0));
				detail.setNum2(list.get(1));
				detail.setNum1(list.get(2));
				result.add(detail);
			}
		}
		return result;
	}*/
	/** 
     * 递归实现dimValue中的笛卡尔积，结果放在result中 
     * @param dimValue 原始数据 
     * @param result 结果数据 
     * @param layer dimValue的层数 
     * @param curList 每次笛卡尔积的结果 
     */  
    private static void recursive (List<List<String>> dimValue, List<List<String>> result, int layer, List<String> curList) {  
        if (layer < dimValue.size() - 1) {  
            if (dimValue.get(layer).size() == 0) {  
                recursive(dimValue, result, layer + 1, curList);  
            } else {  
                for (int i = 0; i < dimValue.get(layer).size(); i++) {  
                    List<String> list = new ArrayList<String>(curList);  
                    list.add(dimValue.get(layer).get(i));  
                    recursive(dimValue, result, layer + 1, list);  
                }  
            }  
        } else if (layer == dimValue.size() - 1) {  
            if (dimValue.get(layer).size() == 0) {  
                result.add(curList);  
            } else {  
                for (int i = 0; i < dimValue.get(layer).size(); i++) {  
                    List<String> list = new ArrayList<String>(curList);  
                    list.add(dimValue.get(layer).get(i));  
                    result.add(list);  
                }  
            }  
        }  
    } 
	//排列-定位
	public static void permutation(char[]ss,int i){  
        if(ss==null||i<0 ||i>ss.length){  
            return;  
        }  
        if(i==ss.length){  
            System.out.println(new String(ss));  
        }else{  
            for(int j=i;j<ss.length;j++){  
                char temp=ss[j];//交换前缀,使之产生下一个前缀  
                ss[j]=ss[i];  
                ss[i]=temp;  
                permutation(ss,i+1);  
                temp=ss[j]; //将前缀换回来,继续做上一个的前缀排列.  
                ss[j]=ss[i];  
                ss[i]=temp;  
            }  
        }  
    }  
	//组合-- 任选
	 public static void combiantion(char chs[]){  
	        if(chs==null||chs.length==0){  
	            return ;  
	        }  
	        List<Character> list=new ArrayList();  
	        for(int i=1;i<=chs.length;i++){  
	            combine(chs,0,i,list);  
	        }  
	    }  
	    //从字符数组中第begin个字符开始挑选number个字符加入list中  
	    public static void combine(char []cs,int begin,int number,List<Character> list){  
	        if(number==0){  
	            System.out.println(list.toString());  
	            return ;  
	        }  
	        if(begin==cs.length){  
	            return;  
	        }  
	        list.add(cs[begin]);  
	        combine(cs,begin+1,number-1,list);  
	        list.remove((Character)cs[begin]);  
	        combine(cs,begin+1,number,list);  
	    }
	//cqssc

	//201710200456

	public  static  void writeTmpTxt2PrizeFile(String caipiao, String id){
		try {
			//冲掉上次方案，防止赚投误取
			FileUtils.write(new File(noPath + caipiao + id + ".txt"), "等待前台刷新方案中..."  + "\r",false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static  String genPy3NumStr(int num) {
		String result = "";
		switch (num) {
			case 0: return "0123789";
			case 1: return "0123489";
			case 2: return "0123459";
			case 3: return "0123456";
			case 4: return "1234567";
			case 5: return "2345678";
			case 6: return "3456789";
			case 7: return "0456789";
			case 8: return "0156789";
			case 9: return "0126789";
		}
		return  result;
	}

	//比较两个数相差不超过3
	public static boolean judgeIsmatchBetween3(int src, int dst) {
		String dstStr = dst + "";
		switch (src) {
			case 0 :
				if ("7890123".indexOf(dstStr) > 0) {
					return  true;
				}
				break;

			case 1 :
				if ("8901234".indexOf(dstStr) > 0) {
					return  true;
				}
				break;
			case 2 :
				if ("9012345".indexOf(dstStr) > 0) {
					return  true;
				}
				break;
			case 3 :
				if ("0123456".indexOf(dstStr) > 0) {
					return  true;
				}
				break;
			case 4 :
				if ("1234567".indexOf(dstStr) > 0) {
					return  true;
				}
				break;
			case 5 :
				if ("2345678".indexOf(dstStr) > 0) {
					return  true;
				}
				break;
			case 6 :
				if ("3456789".indexOf(dstStr) > 0) {
					return  true;
				}
				break;
			case 7 :
				if ("4567890".indexOf(dstStr) > 0) {
					return  true;
				}
				break;
			case 8 :
				if ("5678901".indexOf(dstStr) > 0) {
					return  true;
				}
				break;
			case 9 :
				if ("6789012".indexOf(dstStr) > 0) {
					return  true;
				}
				break;
			default: break;
		}
		return  false;
	}

	//二星a*b玩法转成通用注数
	public static String convertCha2Normal(String src1, String src2) {
		if (StringUtils.isEmpty(src1) || StringUtils.isEmpty(src2)) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		for(int i=0; i<src1.length();i++) {
			String item1 =  String.valueOf(src1.charAt(i));
			for (int j = 0; j < src2.length(); j++) {
				String item2 =  String.valueOf(src2.charAt(j));
				sb.append(item1 + item2 + " ");
			}
		}

		return sb.toString();
	}
	//三星a*b玩法转成通用注数
	public static String convertCha3Normal(String src1, String src2, String src3) {
		if (StringUtils.isEmpty(src1) || StringUtils.isEmpty(src2)) {
			return "";
		}

		List<String> result = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<src1.length();i++) {
			String item1 =  String.valueOf(src1.charAt(i));
			for (int j = 0; j < src2.length(); j++) {
				String item2 =  String.valueOf(src2.charAt(j));
				for (int k = 0; k < src3.length(); k++) {
					String item3 =  String.valueOf(src3.charAt(k));
					result.add(item1 + item2 + item3);

				}
			}
		}
		Collections.sort(result);
		result.stream().forEach(
				item -> {
					sb.append(item + " ");
				}
		);
		return sb.toString();
	}


	/**
	 * 时间转化为期数
	 * @param time
	 * @return
	 */
	public static String genNofromTime(Date time) {
		String dateQs = new SimpleDateFormat("yyyyMMdd").format(time);
		String hms = new SimpleDateFormat("HHmmss").format(time);
		String hh = hms.substring(0, 2);
		String mm = hms.substring(2, 4);
		String ss = hms.substring(4, 6);

		int qs = Integer.valueOf(hh) * 60 + Integer.valueOf(mm);
		String timeQs = "";
		if (qs < 10) {
			timeQs = "000" + qs;
		} else if (qs >= 10 && qs < 100) {
			timeQs = "00" + qs;
		} else if (qs >= 100 && qs < 1000) {
			timeQs = "0" + qs;
		} else {
			timeQs = qs + "";
		}
		return dateQs + "-" + timeQs;
	}
	//返回前3后4个数字包括自己共8个数字
	public static  String genPyPost4NumStr(int num) {
		String result = "";
		switch (num) {
			case 0: return "78901234";
			case 1: return "89012345";
			case 2: return "90123456";
			case 3: return "01234567";
			case 4: return "12345678";
			case 5: return "23456789";
			case 6: return "34567890";
			case 7: return "45678901";
			case 8: return "56789012";
			case 9: return "67890123";
		}
		return  result;
	}
	//与genPyPost4NumStr对应
	public static boolean judgeIsmatchBetweenPost4(int src, int dst) {
		String dstStr = dst + "";
		switch (src) {
			case 0 :
				if ("78901234".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;

			case 1 :
				if ("89012345".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 2 :
				if ("90123456".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 3 :
				if ("01234567".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 4 :
				if ("12345678".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 5 :
				if ("23456789".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 6 :
				if ("34567890".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 7 :
				if ("45678901".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 8 :
				if ("56789012".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 9 :
				if ("67890123".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			default: break;
		}
		return  false;
	}

	//返回前3后4个数字包括自己共8个数字
	public static  String genPy4NumStr(int num) {
		String result = "";
		switch (num) {
			case 0: return "678901234";
			case 1: return "789012345";
			case 2: return "890123456";
			case 3: return "901234567";
			case 4: return "012345678";
			case 5: return "123456789";
			case 6: return "234567890";
			case 7: return "345678901";
			case 8: return "456789012";
			case 9: return "567890123";
		}
		return  result;
	}
	//与genPy4NumStr对应
	public static boolean judgeIsmatchBetween4(int src, int dst) {
		String dstStr = dst + "";
		switch (src) {
			case 0 :
				if ("678901234".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;

			case 1 :
				if ("789012345".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 2 :
				if ("890123456".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 3 :
				if ("901234567".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 4 :
				if ("012345678".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 5 :
				if ("123456789".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 6 :
				if ("234567890".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 7 :
				if ("345678901".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 8 :
				if ("456789012".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			case 9 :
				if ("567890123".indexOf(dstStr) >= 0) {
					return  true;
				}
				break;
			default: break;
		}
		return  false;
	}
	public static String getCurNoByOnlineTime() {

		String time = DateUtil.getWebsiteDatetime("http://www.baidu.com");
		Date curTime = DateUtil.String2Date(time, "yyyy-MM-dd HH:mm:ss");
		return LotteryUtil.genNofromTime(curTime);
	}

	public static String getNextNoByOnlineTime() {
		String time = DateUtil.getWebsiteDatetime("http://www.baidu.com");
		Date curTime = DateUtil.String2Date(time, "yyyy-MM-dd HH:mm:ss");
		Date nextMin = DateUtil.addMinutes(1, curTime);
		return LotteryUtil.genNofromTime(nextMin);
	}

 	    public static void main(String args[]) throws Exception {
		System.out.println(LotteryUtil.convertCha3Normal("123", "67", "269"));
	    }
}
