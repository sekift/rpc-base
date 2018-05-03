package com.patterncat;

import java.io.IOException;

import com.patterncat.rpc.action.DirtWordFilterSplitWords;

public class DirtSplitWordsTest {
	/**
	 * 三个函数：一个过滤、一个全排列、一个是否包含。
	 */
	// 保存的set
	public static void main(String[] args) throws IOException {
		String sourceWord = "日本法涛";
		String[] filterWord = DirtWordFilterSplitWords.filterAndToArray(sourceWord);
		DirtWordFilterSplitWords.pushWordToSet(filterWord);
		
		boolean isFilter = false;
		for (String str : DirtWordFilterSplitWords.wordSet) {
			//System.out.println(str);
			if (DirtWordFilterSplitWords.containsDirtWordSet(str)) {
				isFilter = true;
				break;
			}
		}
		System.out.println(isFilter);
	}

}