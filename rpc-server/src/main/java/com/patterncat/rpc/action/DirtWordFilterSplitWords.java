package com.patterncat.rpc.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import multi.patt.match.ac.AhoCorasick;
import multi.patt.match.ac.SearchResult;

import com.patterncat.rpc.util.StringTool;

/**
 * 敏感字过滤器, 使用AC多模式匹配算法. 保证敏感字库的大小对匹配算法的速度无影响
 * 
 * @author yuan<cihang.yuan@happyelements.com>
 * 
 */
public class DirtWordFilterSplitWords {

	private AhoCorasick tree;
	/**
	 * 敏感字字典
	 */
	private String dictFile;

	public DirtWordFilterSplitWords(String dictFile) {
		this.dictFile = dictFile;
		tree = new AhoCorasick();
	}

	/**
	 * 初始化
	 * 
	 * @throws IOException
	 */
	public void init() throws IOException {
		BufferedReader br = null;
		String keyword = null;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(dictFile)), "UTF-8"));
			while ((keyword = br.readLine()) != null) {
				if (keyword.trim().length() != 0) {
					tree.add(keyword.trim().getBytes(), keyword.trim());
				}
			}

			tree.prepare();
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}

	/**
	 * 判断该文本是否包含敏感字
	 * 
	 * @param text
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public boolean containsDirtWord(String text) throws UnsupportedEncodingException {
		Iterator<?> it = tree.search(text.toUpperCase().getBytes("UTF-8"));

		return it.hasNext();
	}

	/**
	 * 过滤文本, 敏感字转换为***
	 * 
	 * @param text
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String doFilter(String text) throws UnsupportedEncodingException {
		String filterText = text;

		Iterator<?> it = tree.search(text.getBytes("UTF-8"));
		SearchResult result = null;
		while (it.hasNext()) {
			result = (SearchResult) it.next();
			Iterator<?> outputIt = result.getOutputs().iterator();
			while (outputIt.hasNext()) {
				String dirtWord = (String) outputIt.next();
				filterText = filterText.replaceAll(dirtWord, getMask(dirtWord.length()));
			}
		}

		return filterText;
	}

	private static final String mask1 = "*";
	private static final String mask2 = "**";
	private static final String mask3 = "***";
	private static final String mask4 = "****";

	private String getMask(int length) {
		if (length < 1) {
			throw new IllegalArgumentException(String.valueOf(length));
		}

		if (length <= 4) {
			return getSmallMask(length);
		}

		int count = length / 4;
		StringBuilder maskBuilder = new StringBuilder();
		for (int i = 0; i < count; i++) {
			maskBuilder.append(mask4);
		}

		// 计算余数
		int remainder = length % 4;
		if (remainder != 0) {
			maskBuilder.append(getSmallMask(remainder));
		}

		return maskBuilder.toString();
	}

	private String getSmallMask(int length) {
		switch (length) {
		case 1:
			return mask1;
		case 2:
			return mask2;
		case 3:
			return mask3;
		case 4:
			return mask4;
		}
		return "";
	}

	// set
	public static Set<String> wordSet = new TreeSet<String>();

	// 首先过滤掉无需要的字符,并转换成String[]
	public static String[] filterAndToArray(String word) {
		String str = StringTool.replace("[^\u4e00-\u9fa5]", word, "", 0);
		String[] array = new String[str.length()];
		for (int i = 0; i < str.length(); i++) {
			array[i] = String.valueOf(str.charAt(i));
		}
		return array;
	}

	// 再将所有的字符放到一个set中去
	public static void pushWordToSet(String[] array) {
		for (int i = 1; i <= array.length; i++) {
			String[] res = new String[i];
			combine(array, 0, res, 0);
		}
	}

	/**
	 * 核心算法
	 * 
	 * @param a
	 * @param a_pos
	 * @param rs
	 * @param rs_pos
	 */
	final static public void combine(final Object a[], final int a_pos, final Object rs[], final int rs_pos) {
		if (rs_pos >= rs.length) {
			StringBuilder substr = new StringBuilder();
			for (int i = 0; i < rs.length; i++) {
				substr.append(rs[i]);
			}
			wordSet.add(substr.toString());
		} else {
			for (int ap = a_pos; ap < a.length; ap++) {
				rs[rs_pos] = a[ap];
				combine(a, ap + 1, rs, rs_pos + 1);
			}
		}
	}

	public static boolean containsDirtWordSet(String word) throws IOException {
		DirtWordFilterSplitWords dirtWordService = new DirtWordFilterSplitWords(getResource("resources/key.txt"));
		dirtWordService.init();
		return dirtWordService.containsDirtWord(word);
	}

	private static String getResource(String name) throws IOException {
		return new File(new File("").getCanonicalFile(), name).getAbsolutePath();
	}
}
