/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package cn.sharesdk.onekeyshare;

import java.util.ArrayList;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;

import cn.sharesdk.onekeyshare.*;
import cn.sharesdk.onekeyshare.CustomerLogo;
import cn.sharesdk.onekeyshare.OnekeyShareThemeImpl;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import com.mob.tools.FakeActivity;

/** 快捷分享的基类 */
public class OnekeySharePage extends FakeActivity {
	private cn.sharesdk.onekeyshare.OnekeyShareThemeImpl impl;

	public OnekeySharePage(cn.sharesdk.onekeyshare.OnekeyShareThemeImpl impl) {
		this.impl = impl;
	}

	/** 分享界面是否弹窗模式 */
	protected final boolean isDialogMode() {
		return impl.dialogMode;
	}

	protected final HashMap<String, Object> getShareParamsMap() {
		return impl.shareParamsMap;
	}

	/** 静默分享开关（没有界面，直接分享 ）*/
	protected final boolean isSilent() {
		return impl.silent;
	}

	protected final ArrayList<cn.sharesdk.onekeyshare.CustomerLogo> getCustomerLogos() {
		return impl.customerLogos;
	}

	protected final HashMap<String, String> getHiddenPlatforms() {
		return impl.hiddenPlatforms;
	}

	protected final PlatformActionListener getCallback() {
		return impl.callback;
	}

	protected final cn.sharesdk.onekeyshare.ShareContentCustomizeCallback getCustomizeCallback() {
		return impl.customizeCallback;
	}

	protected final boolean isDisableSSO() {
		return impl.disableSSO;
	}

	protected final void shareSilently(Platform platform) {
		impl.shareSilently(platform);
	}

	protected final ShareParams formateShareData(Platform platform) {
		if (impl.formateShareData(platform)) {
			return impl.shareDataToShareParams(platform);
		}
		return null;
	}

	protected final boolean isUseClientToShare(Platform platform) {
		return impl.isUseClientToShare(platform);
	}

}
