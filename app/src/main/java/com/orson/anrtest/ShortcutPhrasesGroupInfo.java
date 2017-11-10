package com.orson.anrtest;

import java.util.ArrayList;
import java.util.List;

public class ShortcutPhrasesGroupInfo {
	
	public static final int SHORTCUTPHRASE_CATEGORY_BASE = 1;
	public static final int SHORTCUTPHRASE_CATEGORY_INTERNAL = 2;
	public static final int SHORTCUTPHRASE_CATEGORY_RECO = 3;
	public static final int SHORTCUTPHRASE_CATEGORY_DOWN = 4;
	public static final int SHORTCUTPHRASE_CATEGORY_GAME = 5;

	public String groupName;
	/**
	 *Tthe shortcut phrases of a group
	 */
	public List<String> phrases;
	/**
	 *	Unique  identify
	 */
	public String groupId;
	/**
	 * Describe
	 */
	public String describe;
	/**
	 * category
	 */
	public int category;

	public String timestamp;

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		if (groupId != null && o instanceof ShortcutPhrasesGroupInfo) {
			return ((ShortcutPhrasesGroupInfo) o).groupId.equals(this.groupId);
		}
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		if (groupId != null) {
			return groupId.hashCode();
		}
		return super.hashCode();
	}
	
	/**
	 * Create a new object copy form this object
	 * @return
	 */
	public ShortcutPhrasesGroupInfo copyInstance()
	{
		ShortcutPhrasesGroupInfo o = new ShortcutPhrasesGroupInfo();
		if (phrases != null) {
			o.phrases = new ArrayList<String>();
			o.phrases.addAll(phrases);
		}
		o.groupId = groupId;
		o.groupName = groupName;
		return o;
	}
	
	@Override
	public String toString() {
		return "[ShortcutPhrasesGroupInfo] groupId="+groupId+" groupName="+groupName;
	}
}
