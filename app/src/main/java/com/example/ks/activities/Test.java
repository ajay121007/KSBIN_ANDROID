package com.example.ks.activities;

import androidx.core.content.res.TypedArrayUtils;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
    ArrayList<Integer> list=new ArrayList();
    list.add(-5);
    list.add(9);
    list.add(7);
    list.add(3);
    list.add(-4);
    list.add(7);
        int i = dummyFun(list);
        System.out.println(String.valueOf(i));
    }

    static  int dummyFun(ArrayList<Integer> arr){
        ArrayList<Integer> list;
       if(arr.size()==1) return arr.get(0);
       else if(arr.size()==0) return -1;
      while (true){
             list=new ArrayList<Integer>();
            if(list.isEmpty()){
                list.addAll(arr);
            }
          for (int i: list) {
              if(i%2==0){
                list.remove(i);
              }
          }
          if(list.size()==1) break;

      }
        return list.get(0);
    }
}
