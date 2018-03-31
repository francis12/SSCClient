package com.ssc.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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
			case 0: return "7890123";
			case 1: return "8901234";
			case 2: return "9012345";
			case 3: return "0123456";
			case 4: return "1234567";
			case 5: return "2345678";
			case 6: return "3456789";
			case 7: return "4567890";
			case 8: return "5678901";
			case 9: return "6789012";
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

	//a*bc玩法转成通用注数
	public static String convertCha2Normal(String src1, String src2) {
		return "";
	}

 	    public static void main(String args[]) throws Exception {
	    }
}
