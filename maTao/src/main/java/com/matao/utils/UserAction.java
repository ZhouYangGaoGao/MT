package com.matao.utils;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-24 下午2:44:47
 * @Description:用户行为模块
 */

public enum UserAction {
	Action_login(null), //
	Action_Threelogin(null), //
	Action_putFace(null), //
	Action_Login_OUT(null), //
	Action_Get_Code(null), //
	Action_Regiser_Phone(null), //
	Action_Favorable_list(null), //
	Action_Search(null), //
	Action_Reply(null), //
	Action_Experience_list(null), //
	Action_FeedBack(null), //
	Action_UpdaePw(null), //
	Action_UpdaeVersion(null), //
	Action_ResetPw(null), //
	Action_Find_PSD(null), //
	Action_Zan(null), //
	Action_Jian(null), //
	Action_Like(null), //
	Action_DETAIL(null), //
	Action_Upicon(null), //
	Action_Report(null), //
	Action_Myhouse(null), //
	Action_Ziliao(null), //
	Action_AmName(null), //
	Action_FXCG(null), //
	Action_AmSex(null), //
	Action_AmPlace(null), //
	Action_GetCode(null), //
	Action_Band(null), //
	Action_AmState(null), //
	Action_HOME(null), //
	Action_Delete(null), //
	Action_WARNING(null), //
	Action_BabyInfo(null), //
	Action_GetMasgNum(null), //
	Action_GetNewMsg(null), //
	Action_GetXttz(null), //
	Action_GetHfList(null), //
	Action_GetXhList(null), //
	Action_GetJfList(null), //
	Action_GetUserAddress(null), //
	Action_WcDuihuan(null),//
	Action_GetGoodsList(null),//
	Action_IsBought(null);//

	public Object value;

	private UserAction(Object value) {
		this.value = value;
	}

}
