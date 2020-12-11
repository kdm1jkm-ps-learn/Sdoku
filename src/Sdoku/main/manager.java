package Sdoku.main;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class manager {
    public int count = 0;

    public int[][] getBlank(String[] num, int a, int b) {
        int[][] Set = new int[a * b][a * b];
        for (int i = 0; i < a * b; i++) {
            for (int k = a * b * i; k < a * b * i + a * b; k++)//1234
            {
                for (int j = 1; j < a * b + 1; j++) {
                    if (num[k].equalsIgnoreCase(String.valueOf(j))) {
                        Set[i][k - a * b * i] = j;
                    }
                }
            }
        }
        return Set;
    }

    public boolean[] CheckHorizontal(int[][] set, int a, int b, int i, int l) {
        int con[] = new int[a * b];
        boolean[] red = new boolean[a * a * b * b];
        for (int I = 0; I < a * b; I++) {
            con[I] = -1;
        }
        for (int j = 0; j < a; j++) {
            for (int r = 0; r < b; r++) {
                for (int k = 0; k < a * b; k++) {
                    if (set[j + a * l][r + b * i] == k + 1) {
                        if (con[k] == -1) {
                            con[k] = a * b * j + a * a * b * l + r + b * i;
                        } else {
                            red[con[k]] = true;
                            red[a * b * j + a * a * b * l + r + b * i] = true;
                        }
                    }
                }
            }
        }
        return red;
    }

    public void CheckAll(int[][] set, boolean[] red, int a, int b) {
        for (int l = 0; l < b; l++) {
            for (int i = 0; i < a; i++) {
                for (int B = 0; B < CheckHorizontal(set, a, b, i, l).length; B++) {
                    if (CheckHorizontal(set, a, b, i, l)[B]) red[B] = true;
                }
            }
        }
        for (int l = 0; l < a; l++) {
            for (int i = 0; i < b; i++) {
                for (int B = 0; B < CheckVertical(set, a, b, i, l).length; B++) {
                    if (CheckVertical(set, a, b, i, l)[B]) red[B] = true;
                }
            }
        }
    }

    public boolean[] CheckVertical(int[][] set, int a, int b, int i, int l) {
        int con[] = new int[a * b];
        boolean[] red = new boolean[a * a * b * b];
        for (int I = 0; I < a * b; I++) {
            con[I] = -1;
        }
        for (int j = 0; j < b; j++) {
            for (int r = 0; r < a; r++) {
                for (int k = 0; k < a * b; k++) {
                    if (set[a * j + l][b * r + i] == k + 1) {
                        if (con[k] == -1) {
                            con[k] = a * a * b * j + a * b * l + b * r + i;
                        } else {
                            red[con[k]] = true;
                            red[a * a * b * j + a * b * l + b * r + i] = true;
                        }
                    }
                }
            }
        }
        return red;
    }

    public void CheckSqure(int[][] set, boolean[] red, int a, int b) {
        for (int i = 0; i < a * b; i++) {
            for (int k = a * b * i; k < a * b * i + a * b; k++)//1234
            {
                for (int j = 0; j < a * b; j++) {
                    if (k - a * b * i != j && set[i][k - a * b * i] == set[i][j]) {
                        red[k] = true;
                        red[a * b * i + j] = true;
                    }
                }
                if (set[i][k - a * b * i] == 0) {
                    red[k] = true;
                }
            }
        }
    }

    public void manage(String[] num, JTextField[] blank, int a, int b) {
        int[][] set = getBlank(num, a, b);
        boolean red[] = new boolean[a * a * b * b];
        CheckSqure(set, red, a, b);
        CheckAll(set, red, a, b);
        for (int i = 0; i < a * a * b * b; i++) {
            if (red[i]) {
                blank[i].setForeground(Color.RED);
            } else {
                blank[i].setForeground(Color.black);
            }
        }
    }

    public void setblank(String[] Set, JTextField[] blank) {
        for (int i = 0; i < blank.length; i++) {
            if (Set[i] != null) {
                if (Integer.parseInt(Set[i]) > 0 && Integer.parseInt(Set[i]) < Math.sqrt(Set.length) + 1) {
                    blank[i].setText(Set[i] + "");
                }
            }
        }
    }

    public void setblank(int[] Set, JTextField[] blank) {
        for (int i = 0; i < blank.length; i++) {
            if (Set[i] != 0) {
                if (Set[i] > 0 && Set[i] < Math.sqrt(Set.length) + 1) {
                    blank[i].setText(Set[i] + "");
                }
            }
        }
    }

    public void clearblank(JTextField[] blank) {
        for (int i = 0; i < blank.length; i++) {
            blank[i].setText("");
        }
    }

    public int[][] convertInt(String[] Set, int a, int b) {
        int[][] set = new int[a * b][a * b];
        set = getBlank(Set, a, b);
        return set;
    }

    public String[] convertString(int[][] set, int a, int b) {
        String[] Set = new String[a * a * b * b];
        for (int i = 0; i < a * b; i++) {
            for (int j = 0; j < a * b; j++) {
                Set[a * b * i + j] = String.valueOf(set[i][j]);
            }
        }
        return Set;
    }

    public String[] convertSolve(HashMap<Integer, Integer> hashMap, int a, int b) {
        int[] temp = new int[hashMap.size()];
        for (int i = 0; i < hashMap.size(); i++) {
            temp[i] = hashMap.get(i);
        }
        return convertSolve(temp, a, b);
    }

    public String[] convertSolve(int[] sets, int a, int b) {
        String[] convert = new String[a * a * b * b];
        for (int i = 0; i < a * a * b * b; i++) convert[i] = String.valueOf(sets[i]);
        return convertSolve(convert, a, b);
    }

    public String[] convertSolve(String[] sets, int a, int b) {
        String[] solve = new String[a * a * b * b];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                for (int k = 0; k < a * b; k++) {
                    if (k / a == j) solve[a * b * b * i + a * b * j + k] = sets[a * b * b * i + a * b * j + k];
                    else if (k / a == j - 4)
                        solve[a * b * b * i + a * b * j + k - 4 * a * (b - 1)] = sets[a * b * b * i + a * b * j + k];
                    else if (k / a == j - 3)
                        solve[a * b * b * i + a * b * j + k - 3 * a * (b - 1)] = sets[a * b * b * i + a * b * j + k];
                    else if (k / a == j - 2)
                        solve[a * b * b * i + a * b * j + k - 2 * a * (b - 1)] = sets[a * b * b * i + a * b * j + k];
                    else if (k / a == j - 1)
                        solve[a * b * b * i + a * b * j + k - a * (b - 1)] = sets[a * b * b * i + a * b * j + k];
                    else if (k / a == j + 1)
                        solve[a * b * b * i + a * b * j + k + a * (b - 1)] = sets[a * b * b * i + a * b * j + k];
                    else if (k / a == j + 2)
                        solve[a * b * b * i + a * b * j + k + 2 * a * (b - 1)] = sets[a * b * b * i + a * b * j + k];
                    else if (k / a == j + 3)
                        solve[a * b * b * i + a * b * j + k + 3 * a * (b - 1)] = sets[a * b * b * i + a * b * j + k];
                    else if (k / a == j + 4)
                        solve[a * b * b * i + a * b * j + k + 4 * a * (b - 1)] = sets[a * b * b * i + a * b * j + k];
                    else System.out.print("error");
                    ;
                }
            }
        }
        return solve;
    }

    public void PaintBlue(String[] set, JTextField[] blank) {
        for (int i = 0; i < set.length; i++) {
            for (int j = 1; j < Math.sqrt(set.length) + 1; j++) {
                if (set[i].equalsIgnoreCase(String.valueOf(j))) {
                    blank[i].setForeground(Color.BLUE);
                }
            }
        }
    }

    public boolean SimpleCheck(HashMap<Integer, Integer> answer, int a, int b) {
        boolean bool[] = new boolean[a * a * b * b];
        int set[][] = convertInt(convertSolve(answer, a, b), a, b);
        CheckSqure(set, bool, a, b);
        CheckAll(set, bool, a, b);
        return SimpleCheck(bool);
    }

    public boolean SimpleCheck(boolean[] bools) {
        boolean truth = false;
        for (boolean bool : bools) {
            if (bool) truth = true;
        }
        return truth;
    }

}
