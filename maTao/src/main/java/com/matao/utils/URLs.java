package com.matao.utils;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-27 上午10:26:10
 * @Description:接口类
 */

public class URLs {
	// 三方登陆ID Secret
	public static final String WXAppID = "wx3745de18d0a7688e";
	public static final String WXAppSecret = "280920982b6981aa35340498236240bf";
	public static final String QQAppID = "1103374524";
	public static final String QQAppSecret = "KalLrcVZXiimjCWu";
	public static final String XLAppID = "49072957";
	public static final String XLAppSecret = "8dc2deae173514090fda0354519ded9c";
	// 请求接口所需提交字段
	public static final String USER_ID = "UserId";
	public static final String TAG_ID = "TagId";
	public static final String PAGE_INDEX = "PageIndex";
	public static final String QUERY_TIME = "QueryTime";
	public static final String ACCOUNT = "Account";
	public static final String PASSWORD = "Password";
	public static final String TIME_STAMP = "TimeStamp";
	public static final String SORTID = "sortid";
	public static final String AGEID = "ageid";
	public static final String PRICE_TYPE = "priceType";
	public static final String CHANNEL = "channel";
	public static final String TYPE = "type";
	public static final String JSON_INFO = "JsonInfo";
	public static final String CurrentUserId = "currentuserId";
	public static final String OwnerUserId = "owneruserId";
	public static final String MOBILE_NUMBER = "MobileNumber";
	public static final String KEY = "@jyth@$ttt1986@";
	public static final String http = "http://";
	public static final String ip = http + "119.254.11.178";
	// 测试库数据接口
	// public static final String host = http + "api.jkbaby.cn";
	// 正式库数据接口
	public static final String host = http + "api.matao.com";
	// m站文章数据接口
	public static final String mhost = http + "app.matao.com/app/detail";
	public static final String base_url = host + "/v1/index/getIndexv2/?";
	// public static final String base_url = host + "/v1/index/getIndex/?";
	// 登录
	public static final String LOGIN = host + "/v1/account/logon/";
	// 获取验证码
	public static final String GET_CODE = host + "/v1/account/SendRegisterSMS/";
	// 手机用户注册
	public static final String REGISTER = host + "/v1/Account/MobileRegister/";
	// 三方媒体用户注册
	public static final String REGISTER2 = host
			+ "/v1/Account/MobileThirdPartyRegister";
	// 三方账户登录
	public static final String ThirdPartyLogon = host
			+ "/v1/account/thirdpartylogon/";
	// 获取 app 活动列表（get ）
	public static final String ZHUANTI = host + "/v1/common/GetActivityList?";
	// 获取晒单/经验列表接口(get)
	public static final String SHOW_LIST = host
			+ "/v1/matao/getmataoinfoList/?";
	// 获取妈淘分类数据接口
	public static final String MATAO = host + "/v1/matao/GetSortList/";
	// 获取优惠/海淘列表
	public static final String YOUHUI_LIST = host
			+ "/v1/matao/getyouhuiinfolistv2/?";
	// 标签接口
	public static final String TAG = host + "/v1/matao/gettaglist/?";

	// 反馈
	public static final String FEEDBACK = host + "/v1/common/feekbackcontent/";
	// 搜索接口(优惠/海淘/经验/晒单)(get)
	public static final String SEARCH = host
			+ "/v1/matao/getsearcharticeList/?";
	// APP经验晒单评论列表页接口(get)
	public static final String REPLAYLIST = host
			+ "/v1/matao/getreplylistbytypeid/?";
	// 评论接口(post)
	public static final String REPLAY = host + "/v1/matao/savearticlereply";
	// 经验晒单评论点赞接口(post)
	public static final String ZAN = host + "/v1/matao/savereplyyes";
	// 经验晒单喜欢及取消喜欢接口(post)
	public static final String LIKE = host + "/v1/matao/like";
	// 修改密码
	public static final String UPDATEPW = host + "/v1/user/modifypassword/";
	// 重置密码密码
	public static final String RESETPW = host + "/v1/account/resetpassword/";
	// 优惠商品推荐或不推荐接口(post)
	public static final String JIAN = host + "/v1/matao/recommend";
	// 忘记密码
	public static final String FORGOTPW = host
			+ "/v1/account/sendmodifypasswordsms/";
	// 分享成功后加积分接口 (Post)
	public static final String FXCG = host + "/v1/Common/ AddSharePoint /";
	// 举报
	public static final String REPORT = host + "/v1/Common/Report/";
	// 获取详情页面文章信息接口(get)
	public static final String DETAIL = host + "/v1/matao/GetArticleInfo?";
	// 更新版本
	public static final String GETVERSION = host
			+ "/v1/common/upgradeVersion/?version=";
	// 删除评论
	public static final String DELETE = host + "/v1/matao/deletearticlereply";
	// 上传头像
	public static final String UPICON = host + "/v1/User/UploadUserAvatar/";
	// 我的小窝
	public static final String MYHOUSE = host + "/v1/home/gethomeindex?";
	// 我的资料
	public static final String ZILIAO = host + "/v1/user/getuserinfo?";
	// 修改资料（性别）
	public static final String AMEND = host + "/v1/User/UpdateUserInfo/";
	// 我的资料(绑定手机)
	public static final String GETCODE = host
			+ "/v1/account/SendBindMobileSms/";
	// 我的资料(绑定手机+密码)
	public static final String BAND = host + "/v1/account/BindMobileNumber/";
	// 我的资料(修改状态)
	public static final String STATE = host + "/v1/user/updatebabystate/";
	// 预产期提醒
	public static final String WARING = host + "/v1/index/getIsbabyborn/?";
	// 逛一逛提交宝宝信息
	public static final String BabyInfo = host + "/v1/user/GetUserBabyState?";
	// 小窝--获取我的/Ta 的 发布的 晒单/经验列表接口(get)
	public static final String SENDORLIKE = host
			+ "/v1/matao/GetSendOrLikeMataoArticleList?";
	// 获取用户数量
	public static final String MSgNum = host
			+ "/v1/message/GetUnReadMessageCounts?";
	// 获取用户最新消息
	public static final String NewMsg = host
			+ "/v1/message/ IsExistsUnReadMessage ?";
	// 获取系统通知列表
	public static final String XttzList = host
			+ "/v1/message/GetSystemNoticeList?";
	// 获取回复列表
	public static final String HfList = host
			+ "/v1/message/GetReceiveCommentList?";
	// 获取喜欢列表
	public static final String XhList = host
			+ "/v1/message/GetLikeMyArticleLogList?";
	// 获取积分任务列表
	public static final String JfList = host + "/v1/Common/GetPointTaskList?";
	// 获取收货地址
	public static final String UserAddress = host + "/v1/user/ReadUserAddress?";
	// 提交收货地址完成兑换
	public static final String WcDuihuan = host + "/v1/Shop/buy";
	// 获取兑换产品列表
	public static final String GetGoodsList = host + "/v1/shop/GetGoodsList?";
	// 判断是否符合兑换条件
	public static final String IsBought = host + "/v1/Shop/IsBought";

}
