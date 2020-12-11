package Sdoku.Number;

import Sdoku.main.manager;

import java.util.Random;

public class creater {
    public manager m = new manager();

    public void main(String[] args) {
        System.out.print(RanNum(2, 2).length);
    }

    public String[] RanNum(int a, int b) {
        Random r = new Random();
        int num;
        boolean[] e = new boolean[a * a * b * b];
        String[] Set = new String[a * b * a * b];
        int[][] set = new int[a * b][a * b];
        for (int i = 0; i < Set.length; i++) {
            Set[i] = "0";
        }
        for (int i = 0; i < a * b; i++) {
            for (int j = 0; j < a * b; j++) {
                num = r.nextInt(a * b);
                Set[a * b * i + j] = String.valueOf(num + 1);
                for (int k = 0; k < j; k++) {
                    if (num + 1 == Integer.parseInt(Set[a * b * i + k])) {
                        j = j - 1;
                    }
                }
                ;
                if (e[j]) {
                    j = j - 1;
                }
            }
        }
        return Set;
    }
}
