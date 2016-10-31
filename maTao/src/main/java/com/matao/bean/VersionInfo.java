package com.matao.bean;

import java.io.Serializable;

public class VersionInfo implements Serializable {
	
	private boolean IsLastVersion;
	private String Version;
	private String UpgradeUrl;
	private boolean IsForceUpgrade;
	private String VersionLog;

	public boolean isIsLastVersion() {
		return IsLastVersion;
	}

	public void setIsLastVersion(boolean isLastVersion) {
		IsLastVersion = isLastVersion;
	}

	public String getVersion() {
		return Version;
	}

	@Override
	public String toString() {
		return "Version [IsLastVersion=" + IsLastVersion + ", Version="
				+ Version + ", UpgradeUrl=" + UpgradeUrl + ", IsForceUpgrade="
				+ IsForceUpgrade + ", VersionLog=" + VersionLog + "]";
	}

	public void setVersion(String version) {
		Version = version;
	}

	public String getUpgradeUrl() {
		return UpgradeUrl;
	}

	public void setUpgradeUrl(String upgradeUrl) {
		UpgradeUrl = upgradeUrl;
	}

	public boolean isIsForceUpgrade() {
		return IsForceUpgrade;
	}

	public void setIsForceUpgrade(boolean isForceUpgrade) {
		IsForceUpgrade = isForceUpgrade;
	}

	public String getVersionLog() {
		return VersionLog;
	}

	public void setVersionLog(String versionLog) {
		VersionLog = versionLog;
	}

}
