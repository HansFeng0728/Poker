package org.personal.service;

import java.util.ArrayList;
import java.util.List;

public class Test {
	public int[] searchRange(int[] nums, int target) {
        List<Integer> resultList = new ArrayList<>();
        for(int i = 0; i < nums.length;i++){
            if(target == nums[i]){
                resultList.add(i);
            }
        }
        if(resultList.size() == 0){
            int[] aa = {-1,-1};
            return aa;
        }
        if(resultList.size() == 1){
            int[] bb = new int[2];
            bb[0] = resultList.get(0);
            bb[1] = resultList.get(0);
        }
        int[] result = new int[resultList.size()];
        for(int i = 0; i < resultList.size(); i++){
            result[i] = resultList.get(i);
        }
        return result;
    }
	public static void main(String[] args){
		Test tt = new Test();
		int[] a = {1};
		int[] result = tt.searchRange(a, 1);
		System.out.println();
	}
}
