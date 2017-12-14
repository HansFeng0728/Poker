package org.personal.controller;

public class Test {
	public static void main(String[] args){
		String init = "20-1,12-5,13-2";
		String single = "15-9";
		String[] aa = single.split(",");
		String[] bb = init.split(",");
		for(int a = 0 ; a < aa.length;a++){
			System.out.println(aa[a]+"--"+aa.length);
		}
		
		for(int a = 0 ; a < bb.length; a++){
			String[] bbbb = bb[a].split("-");
			System.out.println(bbbb[0]+"--"+bb.length);
		}
	}
}
