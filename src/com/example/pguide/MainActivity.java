package com.example.pguide;

import java.io.FileInputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private String LOGTAG = "SearchActivity";

	private String TargetCity[] = new String[] { "阿巴嘎旗", "阿城", "爱辉", "阿合奇",
			"阿拉尔", "阿勒泰", "安达", "敖汉旗", "安康", "安宁", "安庆", "鞍山", "安顺", "安阳",
			"安县", "巴楚", "百灵庙", "班戈", "百色", "白银", "巴仑台", "保定", "宝鸡", "宝清", "保山",
			"包头", "巴中", "巴塘", "巴音毛道", "巴音布鲁克", "北塔山", "北海", "北京", "蚌埠", "本溪",
			"毕节", "彬县", "波阳", "博克图", "长岛", "长白", "长春", "常德", "昌吉", "长岭", "长平",
			"长沙", "常熟", "长汀", "常州", "朝阳", "承德", "成都", "成山头", "郴州", "赤峰", "重庆",
			"楚雄", "达县", "大柴旦", "大陈岛", "达拉特旗", "大理", "大连", "丹东", "单县", "稻城",
			"Daodi", "Daolin", "Darlag", "大同", "大通", "大悟", "大兴", "德格", "德令哈",
			"丁青", "堆龙德庆", "定海", "定陶", "定西", "定县", "东方", "东沙岛", "东升", "东台",
			"都兰", "敦化", "敦煌", "多伦", "独山", "伊金霍洛旗", "峨眉山", "恩施", "二连浩特", "房县",
			"凤城", "奉节", "丰宁", "丰台", "佛冈", "佛山", "扶绥", "福鼎", "富锦", "抚顺",
			"阜阳(富阳)", "富蕴", "福州", "刚察", "甘谷", "赣榆", "赣州", "高要", "高邑", "甘孜",
			"耿马", "格尔木", "贡嚘", "珙县", "拐子湖", "广安", "广昌", "广华", "广南", "广州", "贵定",
			"桂林", "桂平", "贵阳", "固始", "固阳", "海城", "海口", "海拉尔", "海林", "海伦", "海宁",
			"海晏", "Haliut", "哈密", "汉沽", "杭州", "汉江", "汉中", "哈尔滨", "河池", "合川",
			"合肥", "合江", "河南", "河曲", "河源", "菏泽", "和布克塞尔", "呼和浩特", "香港", "和田",
			"华山", "淮阳", "化德", "桦甸", "怀来", "华家岭", "黄山", "黄陂", "黄石", "会理", "惠民",
			"会泽", "惠州", "呼兰", "虎林", "呼玛", "霍山", "胡四台", "湖州", "吉兰太", "扎鲁特旗",
			"集安", "江城", "江津", "江陵", "江门", "建阳", "胶南", "焦县", "嘉兴", "介休", "吉林",
			"济南", "景德镇", "静海", "精河", "景洪", "靖远", "济宁", "锦州", "九江", "九龙", "酒泉",
			"九台", "九仙山", "久镇", "鸡西", "朱日和", "句容", "哈巴河", "开封", "开阳", "康定",
			"克拉玛依", "喀什", "克山", "King's park", "库尔勒", "库伦旗", "宽甸", "昆明", "昆阳",
			"库车", "澜沧", "阆中", "兰西", "兰州", "冷湖", "乐亭", "拉萨", "乐至", "连县", "梁平",
			"连平", "辽阳", "利津", "临沧", "临东", "零陵", "陵县", "临海", "临河", "临江", "临潼",
			"林西", "灵石", "丽水", "理塘", "浏阳", "柳州", "溧阳", "龙口", "龙里", "龙州", "庐山",
			"漯河", "罗甸", "洛阳", "卢氏", "泸水", "吕泗", "泸溪", "泸州", "澳门", "麻城", "玛多",
			"玛纳斯", "满都拉", "茫崖", "茂名", "马鬃山", "梅县", "眉山", "勐腊", "蒙山", "孟州",
			"蒙自", "绵阳", "民和", "民勤", "闽清", "米泉", "密云", "漠河", "牡丹江", "那曲", "南昌",
			"南城", "南充", "南京", "南宁", "南平", "南沙岛", "南通", "南县", "南阳", "南岳", "那坡",
			"那仁宝力格", "内江", "嫩江", "New Kowloon", "牛庄", "Nyingchi", "鄂托克旗", "帕里",
			"平凉", "平潭", "平武", "平遥", "平阴", "皮山", "勃力", "泊头", "蒲城", "蒲口", "恰卜恰",
			"前郭尔罗斯", "Qijiaojing", "青岛", "清江", "青龙", "清远", "钦州", "琼海", "齐齐哈尔",
			"奇台", "渠县", "泉州", "曲麻莱", "日照", "榕江", "瑞丽", "若尔盖", "若羌", "桑植",
			"珊瑚岛", "三水", "色达", "上蔡", "上川岛", "上海", "尚志", "汕头", "汕尾", "韶关", "邵武",
			"绍兴", "邵阳", "沙市", "嵊泗", "深县", "沈阳", "深圳", "射阳", "石拐", "石家庄", "石楼",
			"石浦", "狮泉河", "双城", "沭阳", "思茅", "思南", "四平", "索县", "松潘", "石炭井",
			"绥芬河", "绥宁", "孙吴", "塔城", "塔河", "泰山", "泰来", "太原", "塘沽", "唐山", "腾冲",
			"天冈", "天津", "天水", "铁岭", "铁干里克", "铁力", "通道", "通河", "通辽", "图里河",
			"托托河", "吐鲁番", "Uliastai", "乌鲁木齐", "乌苏", "万源", "围场", "潍坊", "渭南",
			"威宁", "温州", "伍道梁", "武都", "武冈", "武汉", "芜湖", "武鸣", "乌鞘岭", "五台山",
			"武夷山", "梧州", "申扎", "西乌珠穆沁旗", "西安", "厦门", "湘潭", "咸阳", "小二沟", "孝感",
			"西昌", "西丰", "Xigaze", "西华", "锡林浩特", "新巴尔虎右旗", "新安", "兴仁", "邢台",
			"西宁", "新津", "新县", "新乡", "信阳", "信宜", "西沙岛", "秀水", "寻乌", "徐闻", "徐州",
			"雅安", "延安", "盐池", "盐城", "阳江", "杨柳青", "阳泉", "延吉", "兖州", "叶县", "宜宾",
			"宜昌", "伊春", "伊都", "伊拉溪", "银川", "营口", "伊宁", "义乌", "益阳", "沂源", "永安",
			"永川", "永宁", "永暑礁", "酉阳", "攸县", "元江", "沅陵", "元谋", "原平", "虞城", "榆次",
			"岳阳", "榆林", "玉门镇", "郓城", "榆社", "榆树", "杂多", "枣阳", "漳平", "张家口", "章丘",
			"彰武", "张掖", "漳州", "湛江", "沾益", "昭通", "正定", "郑州", "镇江", "枝江", "中宁",
			"钟祥", "周村", "驻马店", "涿州", "淄博", "遵义" };
	private String Woeid[] = new String[] { "CHXX0243", "CHXX0001", "CHXX0174",
			"CHXX0210", "CHXX0212", "CHXX0196", "CHXX0187", "CHXX0002",
			"CHXX0394", "CHXX0003", "CHXX0452", "CHXX0004", "CHXX0005",
			"CHXX0269", "CHXX0182", "CHXX0211", "CHXX0247", "CHXX0324",
			"CHXX0488", "CHXX0006", "CHXX0204", "CHXX0308", "CHXX0387",
			"CHXX0188", "CHXX0370", "CHXX0007", "CHXX0348", "CHXX0352",
			"CHXX0225", "CHXX0206", "CHXX0201", "CHXX0499", "CHXX0008",
			"CHXX0444", "CHXX0296", "CHXX0418", "CHXX0439", "CHXX0009",
			"CHXX0287", "CHXX0312", "CHXX0299", "CHXX0010", "CHXX0416",
			"CHXX0011", "CHXX0277", "CHXX0012", "CHXX0013", "CHXX0014",
			"CHXX0472", "CHXX0015", "CHXX0294", "CHXX0302", "CHXX0016",
			"CHXX0314", "CHXX0435", "CHXX0286", "CHXX0017", "CHXX0373",
			"CHXX0400", "CHXX0230", "CHXX0464", "CHXX0018", "CHXX0371",
			"CHXX0019", "CHXX0306", "CHXX0505", "CHXX0357", "CHXX0020",
			"CHXX0021", "CHXX0336", "CHXX0251", "CHXX0022", "CHXX0347",
			"CHXX0023", "CHXX0344", "CHXX0231", "CHXX0342", "CHXX0360",
			"CHXX0455", "CHXX0320", "CHXX0024", "CHXX0025", "CHXX0504",
			"CHXX0503", "CHXX0255", "CHXX0445", "CHXX0235", "CHXX0284",
			"CHXX0223", "CHXX0285", "CHXX0432", "CHXX0220", "CHXX0359",
			"CHXX0406", "CHXX0240", "CHXX0395", "CHXX0026", "CHXX0401",
			"CHXX0292", "CHXX0027", "CHXX0483", "CHXX0028", "CHXX0030",
			"CHXX0469", "CHXX0185", "CHXX0029", "CHXX0442", "CHXX0197",
			"CHXX0031", "CHXX0232", "CHXX0032", "CHXX0438", "CHXX0436",
			"CHXX0491", "CHXX0033", "CHXX0345", "CHXX0377", "CHXX0234",
			"CHXX0034", "CHXX0035", "CHXX0222", "CHXX0036", "CHXX0470",
			"CHXX0396", "CHXX0477", "CHXX0037", "CHXX0038", "CHXX0434",
			"CHXX0489", "CHXX0039", "CHXX0443", "CHXX0040", "CHXX0041",
			"CHXX0502", "CHXX0175", "CHXX0244", "CHXX0183", "CHXX0042",
			"CHXX0319", "CHXX0246", "CHXX0219", "CHXX0043", "CHXX0044",
			"CHXX0045", "CHXX0390", "CHXX0046", "CHXX0478", "CHXX0047",
			"CHXX0448", "CHXX0048", "CHXX0337", "CHXX0256", "CHXX0492",
			"CHXX0339", "CHXX0199", "CHXX0249", "CHXX0049", "CHXX0216",
			"CHXX0388", "CHXX0052", "CHXX0248", "CHXX0290", "CHXX0301",
			"CHXX0239", "CHXX0453", "CHXX0050", "CHXX0051", "CHXX0366",
			"CHXX0311", "CHXX0367", "CHXX0053", "CHXX0054", "CHXX0194",
			"CHXX0172", "CHXX0447", "CHXX0055", "CHXX0056", "CHXX0252",
			"CHXX0275", "CHXX0425", "CHXX0384", "CHXX0057", "CHXX0408",
			"CHXX0058", "CHXX0059", "CHXX0060", "CHXX0061", "CHXX0062",
			"CHXX0268", "CHXX0063", "CHXX0064", "CHXX0457", "CHXX0065",
			"CHXX0202", "CHXX0380", "CHXX0066", "CHXX0250", "CHXX0067",
			"CHXX0068", "CHXX0361", "CHXX0226", "CHXX0069", "CHXX0475",
			"CHXX0070", "CHXX0193", "CHXX0245", "CHXX0071", "CHXX0195",
			"CHXX0072", "CHXX0073", "CHXX0358", "CHXX0200", "CHXX0074",
			"CHXX0181", "CHXX0170", "CHXX0209", "CHXX0075", "CHXX0305",
			"CHXX0076", "CHXX0077", "CHXX0208", "CHXX0379", "CHXX0399",
			"CHXX0078", "CHXX0079", "CHXX0227", "CHXX0307", "CHXX0080",
			"CHXX0329", "CHXX0481", "CHXX0405", "CHXX0484", "CHXX0081",
			"CHXX0365", "CHXX0378", "CHXX0276", "CHXX0429", "CHXX0310",
			"CHXX0463", "CHXX0253", "CHXX0297", "CHXX0082", "CHXX0281",
			"CHXX0264", "CHXX0461", "CHXX0353", "CHXX0083", "CHXX0479",
			"CHXX0450", "CHXX0313", "CHXX0084", "CHXX0494", "CHXX0456",
			"CHXX0085", "CHXX0431", "CHXX0086", "CHXX0389", "CHXX0087",
			"CHXX0446", "CHXX0376", "CHXX0088", "CHXX0512", "CHXX0403",
			"CHXX0335", "CHXX0089", "CHXX0242", "CHXX0217", "CHXX0090",
			"CHXX0221", "CHXX0486", "CHXX0091", "CHXX0383", "CHXX0480",
			"CHXX0092", "CHXX0385", "CHXX0351", "CHXX0093", "CHXX0229",
			"CHXX0094", "CHXX0095", "CHXX0096", "CHXX0171", "CHXX0278",
			"CHXX0325", "CHXX0097", "CHXX0465", "CHXX0098", "CHXX0099",
			"CHXX0100", "CHXX0471", "CHXX0511", "CHXX0101", "CHXX0102",
			"CHXX0391", "CHXX0423", "CHXX0487", "CHXX0241", "CHXX0103",
			"CHXX0177", "CHXX0104", "CHXX0105", "CHXX0356", "CHXX0254",
			"CHXX0330", "CHXX0270", "CHXX0476", "CHXX0350", "CHXX0106",
			"CHXX0107", "CHXX0215", "CHXX0108", "CHXX0309", "CHXX0468",
			"CHXX0109", "CHXX0343", "CHXX0190", "CHXX0205", "CHXX0110",
			"CHXX0111", "CHXX0303", "CHXX0289", "CHXX0498", "CHXX0506",
			"CHXX0112", "CHXX0113", "CHXX0460", "CHXX0114", "CHXX0333",
			"CHXX0322", "CHXX0433", "CHXX0375", "CHXX0338", "CHXX0214",
			"CHXX0410", "CHXX0509", "CHXX0426", "CHXX0346", "CHXX0115",
			"CHXX0501", "CHXX0116", "CHXX0192", "CHXX0493", "CHXX0496",
			"CHXX0482", "CHXX0466", "CHXX0117", "CHXX0422", "CHXX0118",
			"CHXX0454", "CHXX0458", "CHXX0119", "CHXX0120", "CHXX0441",
			"CHXX0121", "CHXX0122", "CHXX0123", "CHXX0459", "CHXX0323",
			"CHXX0124", "CHXX0125", "CHXX0381", "CHXX0420", "CHXX0283",
			"CHXX0341", "CHXX0349", "CHXX0126", "CHXX0279", "CHXX0127",
			"CHXX0178", "CHXX0198", "CHXX0128", "CHXX0316", "CHXX0186",
			"CHXX0129", "CHXX0130", "CHXX0131", "CHXX0369", "CHXX0132",
			"CHXX0133", "CHXX0386", "CHXX0134", "CHXX0213", "CHXX0328",
			"CHXX0427", "CHXX0191", "CHXX0282", "CHXX0173", "CHXX0331",
			"CHXX0207", "CHXX0189", "CHXX0135", "CHXX0136", "CHXX0393",
			"CHXX0293", "CHXX0318", "CHXX0137", "CHXX0368", "CHXX0462",
			"CHXX0238", "CHXX0340", "CHXX0428", "CHXX0138", "CHXX0449",
			"CHXX0139", "CHXX0233", "CHXX0257", "CHXX0467", "CHXX0490",
			"CHXX0326", "CHXX0274", "CHXX0141", "CHXX0140", "CHXX0142",
			"CHXX0143", "CHXX0176", "CHXX0144", "CHXX0363", "CHXX0271",
			"CHXX0145", "CHXX0392", "CHXX0280", "CHXX0179", "CHXX0146",
			"CHXX0430", "CHXX0266", "CHXX0236", "CHXX0147", "CHXX0315",
			"CHXX0148", "CHXX0149", "CHXX0495", "CHXX0508", "CHXX0412",
			"CHXX0485", "CHXX0150", "CHXX0437", "CHXX0354", "CHXX0267",
			"CHXX0263", "CHXX0273", "CHXX0500", "CHXX0151", "CHXX0152",
			"CHXX0291", "CHXX0321", "CHXX0507", "CHXX0362", "CHXX0407",
			"CHXX0153", "CHXX0154", "CHXX0155", "CHXX0259", "CHXX0304",
			"CHXX0203", "CHXX0218", "CHXX0156", "CHXX0317", "CHXX0473",
			"CHXX0157", "CHXX0158", "CHXX0510", "CHXX0414", "CHXX0258",
			"CHXX0382", "CHXX0415", "CHXX0372", "CHXX0261", "CHXX0159",
			"CHXX0160", "CHXX0411", "CHXX0260", "CHXX0224", "CHXX0272",
			"CHXX0265", "CHXX0334", "CHXX0332", "CHXX0397", "CHXX0474",
			"CHXX0300", "CHXX0161", "CHXX0288", "CHXX0228", "CHXX0162",
			"CHXX0163", "CHXX0374", "CHXX0364", "CHXX0164", "CHXX0165",
			"CHXX0166", "CHXX0421", "CHXX0262", "CHXX0402", "CHXX0167",
			"CHXX0398", "CHXX0168", "CHXX0169", "CHXX0419" };
	private String HitCity[] = new String[] { "北京", "上海", "杭州", "广州", "成都",
			"深圳", "南京", "苏州" };
	private String FinalCity[];

	private EditText SearchEdit;
	private Button SearchBtn;
	private TextView ResultTitle;
	private ListView CityList;

	private String Wstr;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// Log.d("fjalejfleafeafeafea", Wstr);
				Log.d("STRINGW", Wstr);
				writeFileData("Weather", AnalysisWeather(Wstr));
				Log.d("STRINGW", AnalysisWeather(Wstr));
				break;
			}
		}
	};

	private String AnalysisWeather(String str) {
		int nowcode = 0;
		int st = 0, len = str.length();
		String res = "";
		while (str.charAt(st) >= '0' && str.charAt(st) <= '9') {
			nowcode = nowcode * 10 + str.charAt(st) - '0';
			st++;
		}
		res += WetCodeTrans(nowcode);
		st ++;
		int nowtemp = 0;
		while (str.charAt(st) >= '0' && str.charAt(st) <= '9') {
			nowtemp = nowtemp * 10 + str.charAt(st) - '0';
			st++;
		}
		
		res += " " + nowtemp + "°C";
		st += 2;
		
		int daycode = 0;
		while (str.charAt(st) >= '0' && str.charAt(st) <= '9') {
			daycode = daycode * 10 + str.charAt(st) - '0';
			st++;
		}
		st ++;
		//res += WetCodeTrans(daycode);
		
		int daylow = 0;
		while (str.charAt(st) >= '0' && str.charAt(st) <= '9') {
			daylow = daylow * 10 + str.charAt(st) - '0';
			st++;
		}
		st ++;
		res += "(" + daylow+"°C ~ ";
		int dayhigh = 0;
		while (st < len) {
			dayhigh = dayhigh * 10 + str.charAt(st) - '0';
			st++;
		}
		
		res += dayhigh+"°C)";
		return res;
	}

	private String WetCodeTrans(int cod) {
		switch (cod) {
		case 0:
			return "龙卷风";
		case 1:
			return "热带风暴";
		case 2:
			return "暴风";
		case 3:
			return "大雷雨";
		case 4:
			return "雷阵雨";
		case 5:
			return "雨夹雪";
		case 6:
			return "雨夹雹";
		case 7:
			return "雪夹雹";
		case 8:
			return "冻雾雨";
		case 9:
			return "细雨";
		case 10:
			return "冻雨";
		case 11:
			return "阵雨";
		case 12:
			return "阵雨";
		case 13:
			return "阵雪";
		case 14:
			return "小阵雪";
		case 15:
			return "高吹雪";
		case 16:
			return "雪";
		case 17:
			return "冰雹";
		case 18:
			return "雨淞";
		case 19:
			return "粉尘";
		case 20:
			return "雾";
		case 21:
			return "薄雾";
		case 22:
			return "烟雾";
		case 23:
			return "大风";
		case 24:
			return "风";
		case 25:
			return "冷";
		case 26:
			return "阴";
		case 27:
			return "多云";
		case 28:
			return "多云";
		case 29:
			return "局部多云";
		case 30:
			return "局部多云";
		case 31:
			return "晴";
		case 32:
			return "晴";
		case 33:
			return "转晴";
		case 34:
			return "转晴";
		case 35:
			return "雨夹冰雹";
		case 36:
			return "热";
		case 37:
			return "局部雷雨";
		case 38:
			return "偶有雷雨";
		case 39:
			return "偶有雷雨";
		case 40:
			return "偶有阵雨";
		case 41:
			return "大雪";
		case 42:
			return "零星阵雪";
		case 43:
			return "大雪";
		case 44:
			return "局部多云";
		case 45:
			return "雷阵雨";
		case 46:
			return "阵雪";
		case 47:
			return "局部雷阵雨";
		default:
			return "水深火热";
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SearchEdit = (EditText) findViewById(R.id.SearchText);
		SearchBtn = (Button) findViewById(R.id.SearchBtn);
		ResultTitle = (TextView) findViewById(R.id.ResultTitle);
		CityList = (ListView) findViewById(R.id.CityList);
		String Hcty = getString(R.string.HotCity);
		SetTitle(Hcty);
		// ResultTitle.setText("##");
		FinalCity = HitCity;
		ReFresh();
		CityList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				//MakeToast(FinalCity[arg2]);
				//
				writeFileData("CityName", FinalCity[arg2]);
				Log.d(LOGTAG, "Woeid" + ": " + readFileData(FinalCity[arg2]));
				final String WID = readFileData(FinalCity[arg2]);
				// String Wstr = getWeather(WID);

				new Thread(new Runnable() {
					@Override
					public void run() {
						Wstr = getWeather(WID);
						mHandler.sendEmptyMessage(1);
					}
				}).start();

				Intent intent = new Intent(MainActivity.this,
						PreferencesActivity.class);
				intent.putExtra("City", FinalCity[arg2]);
				startActivity(intent);
				finish();
			}

		});
		for (int i = 0; i < TargetCity.length; i++)
			writeFileData(TargetCity[i], Woeid[i]);
		Arrays.sort(TargetCity);
		SearchBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SetTitle(getString(R.string.SearchRes));
				String UserInput = SearchEdit.getText().toString();
				if (UserInput.length() == 0) {
					MakeToast(getString(R.string.NoCityInput));
					return;
				}
				String Re[] = gaoTheString(UserInput);
				Log.d(LOGTAG, "ResSize" + ": " + Re.length);
				if (Re.length == 0) {
					MakeToast(getString(R.string.NoCityFound));
					return;
				}
				for (int i = 0; i < Re.length; i++) {
					Log.d(LOGTAG, "ResStr" + ": " + Re[i]);
					// FinalCity[i] = Re[i];
				}
				FinalCity = Re;
				ReFresh();
			}

		});

	}

	private void SetTitle(String str) {
		ResultTitle.setText(str);
	}

	private void ReFresh() {
		CityList.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getData()));
	}

	private String[] gaoTheString(String Str) {
		// MakeToast(Str + "." + TargetCity[32] + " , " +
		// Str.compareTo(TargetCity[32]));
		String res[] = new String[8];
		int sz = 0;
		int l = 0, r = TargetCity.length - 1, rp = -1;
		while (l <= r) // 先二分找有没有一样的
		{
			int mid = (l + r) >> 1;
			if (Str.compareTo(TargetCity[mid]) >= 0) {
				l = mid + 1;
				rp = mid;
			} else {
				r = mid - 1;
			}
		}
		if (rp != -1 && Str.compareTo(TargetCity[rp]) == 0) {
			res[sz++] = Str;
			Log.d(LOGTAG, "Found" + ": " + rp);
		}
		for (int i = 0; i < TargetCity.length; i++) {
			if (rp != -1 && i == rp)
				continue;
			Log.d(LOGTAG, "cmp" + ": " + i);
			String sa = Str, sb = TargetCity[i];
			boolean fg = true;
			for (int j = 0; fg && j < sa.length(); j++) {
				for (int k = 0; fg && k < sb.length(); k++) {
					if (sa.charAt(j) == sb.charAt(k)) {
						fg = false;
						res[sz++] = TargetCity[i];
					}
				}
			}
			if (sz >= 8)
				break;
		}
		String fstr[] = new String[sz];
		for (int i = 0; i < sz; i++)
			fstr[i] = res[i];
		return fstr;
	}

	private List<String> getData() {

		List<String> data = new ArrayList<String>();
		for (int i = 0; i < FinalCity.length; i++)
			data.add(FinalCity[i]);

		return data;
	}

	private void MakeToast(String str) {
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void writeFileData(String fileName, String message) {
		try {
			FileOutputStream fout = openFileOutput(fileName, MODE_PRIVATE);
			byte[] bytes = message.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String readFileData(String fileName) {
		String res = "";
		try {
			FileInputStream fin = openFileInput(fileName);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	private Document getWeatherXML(String WOEID) throws Exception {
		URL url = new URL("http://weather.yahooapis.com/forecastrss?p=" + WOEID
				+ "&u=c");
		URLConnection connection = url.openConnection();
		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document document = builder.parse(connection.getInputStream());
		return document;
	}

	public String getWeather(String WOEID) {
		StringBuffer result = new StringBuffer();
		Document doc = null;
		try {
			doc = getWeatherXML(WOEID);
		} catch (Exception e) {
			e.printStackTrace();
			return result.toString();
		}

		NodeList nodeList = doc.getElementsByTagName("channel");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			NodeList nodeList1 = node.getChildNodes();
			for (int j = 0; j < nodeList1.getLength(); j++) {
				Node node1 = nodeList1.item(j);
				if (node1.getNodeName().equalsIgnoreCase("item")) {
					NodeList nodeList2 = node1.getChildNodes();
					for (int k = 0; k < nodeList2.getLength(); k++) {
						Node node2 = nodeList2.item(k);
						if (node2.getNodeName().equalsIgnoreCase(
								"yweather:condition")) {
							result = new StringBuffer();
							NamedNodeMap nodeMap = node2.getAttributes();
							Node textNode = nodeMap.getNamedItem("code");
							Node nowNode = nodeMap.getNamedItem("temp");
							result.append(textNode.getNodeValue()).append("$")
									.append(nowNode.getNodeValue()).append("C")
									.append("$");
						} else if (node2.getNodeName().equalsIgnoreCase(
								"yweather:forecast")) {
							NamedNodeMap nodeMap = node2.getAttributes();
							Node lowNode = nodeMap.getNamedItem("low");
							Node highNode = nodeMap.getNamedItem("high");
							Node textNode = nodeMap.getNamedItem("code");
							result.append(textNode.getNodeValue()).append("$")
									.append(lowNode.getNodeValue()).append("$")
									.append(highNode.getNodeValue());
							return result.toString();
						}
					}
				}
			}
		}
		return result.toString();
	}

}
