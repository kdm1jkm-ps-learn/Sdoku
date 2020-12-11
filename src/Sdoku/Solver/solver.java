package Sdoku.Solver;

import Sdoku.main.manager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class solver {

    public List<HashMap<Integer, Integer>> answers;
    public manager m = new manager();
    HashMap<Integer, List<Integer>> Candidate;
    int x_len;
    int y_len;
    int phase = 0;
    boolean loop = false;
    List<Integer> num;
    HashMap<Integer, Integer> priority;
    HashMap<Integer, Integer> answer;
    List<HashMap<Integer, Integer>> checkPoint;

    public solver(String[] nums, int a, int b) {
        x_len = a;
        y_len = b;
        Candidate = new HashMap<Integer, List<Integer>>();
        priority = new HashMap<Integer, Integer>();
        answer = new HashMap<Integer, Integer>();
        answers = new ArrayList<HashMap<Integer, Integer>>();
        num = new ArrayList<Integer>();
        checkPoint = new ArrayList<HashMap<Integer, Integer>>();
        for (String num : nums) {
            try {
                this.num.add(Integer.parseInt(num));
            } catch (Exception e) {
                this.num.add(0);
            }

        }
        List<Integer> temp = new ArrayList<Integer>();
        for (int j = 1; j <= a * b; j++) {
            temp.add(j);
        }
        for (int i = 0; i < a * a * b * b; i++) {
            if (num.get(i) == null || num.get(i) == 0) Candidate.put(i, new ArrayList<>(temp));
            else {
                Candidate.put(i, new ArrayList<Integer>());
                priority.put(i, num.get(i));
                answer.put(i, num.get(i));
            }
        }
    }

    public void fillBlank() {
        while (answer.size() < x_len * x_len * y_len * y_len) {
            HashMap<Integer, Integer> temp = priority;
            for (Map.Entry<Integer, Integer> entry : temp.entrySet()) {
                setCandidate(entry.getKey(), entry.getValue());
            }
            prevent();
            addPriority();
            if (loop) return;
            loop = true;
        }
    }

    public int[] solve() {
        fillBlank();
        if (answer.size() != x_len * x_len * y_len * y_len) {
            int userChoice = JOptionPane.showConfirmDialog(
                    null,
                    "채워진 경우의 수가 너무 적습니다. 여러 경우의 수를 보시겠습니까?",
                    "경우의 수",
                    JOptionPane.YES_NO_OPTION);
            if (userChoice == JOptionPane.YES_OPTION) {
                System.out.println("시작");
                crossroads();
                System.out.println("끝");
            }
            last();
        }
        if (answer.size() != x_len * x_len * y_len * y_len) answers.add(answer);
        int[] nums = new int[num.size()];
        for (int i = 0; i < num.size(); i++) {
            if (answer.get(i) != null) nums[i] = answer.get(i);
        }
        return nums;
    }

    @SuppressWarnings("unchecked")
    public void crossroads() {
        if (answer.size() == x_len * x_len * y_len * y_len) {
            answers.add((HashMap<Integer, Integer>) answer.clone());
//			System.out.println("answer is full");
            return;
        }
        int cnt = Candidate.size();
        for (Entry e : Candidate.entrySet()) {
            if (Candidate.get(e.getKey()) != null) break;
            cnt--;
        }
        if (cnt == 0) return;
        HashMap<Integer, Integer> temp_answer = new HashMap<Integer, Integer>();
        temp_answer.putAll((HashMap<Integer, Integer>) answer.clone());

        checkPoint.add(phase, temp_answer);
        phase++;        //phase = 0 is start point
//		System.out.println("================#"+phase+"=================");
        int select = 0;
        for (int i = 0; i < Candidate.size(); i++) {
            if (!Candidate.get(i).isEmpty()) {
                select = i;
//				System.out.println("선택된 칸 : "+select);
                break;
            }
        }

        while (!Candidate.get(select).isEmpty()) {
//			System.out.println("선택된 수 : "+Candidate.get(select).get(0));
            priority.put(select, Candidate.get(select).get(0));

            List<Integer> temp = new ArrayList<Integer>();
            for (int i : Candidate.get(select)) {
                temp.add(i);
            }
            temp.remove((Integer) Candidate.get(select).get(0));

            HashMap<Integer, List<Integer>> tempCandidate = new HashMap<Integer, List<Integer>>();
            for (Entry<Integer, List<Integer>> e : Candidate.entrySet()) {
                List<Integer> tempEntry = new ArrayList<Integer>();
                for (int i : e.getValue()) {
                    tempEntry.add(i);
                }
                tempCandidate.put(e.getKey(), tempEntry);
            }

            tempCandidate.put(select, temp);

            Candidate.put(select, new ArrayList<Integer>());

            fillBlank();
            crossroads();
            Candidate.putAll((HashMap<Integer, List<Integer>>) tempCandidate.clone());
//			System.out.println("reset!");
//			System.out.println(Candidate);
            answer.clear();
            answer.putAll((HashMap<Integer, Integer>) checkPoint.get(phase - 1).clone());
//			System.out.println(answer);
        }
        phase--;
        //System.out.println(answers.size()+""+answers);

    }

    public List<Integer> getCandidate(int pos) {
        return Candidate.get(pos);
    }

    public void setCandidate(int pos, List<Integer> li) {
        Candidate.put(pos, li);
    }

    public void setCandidate(int pos, int in) {
        int line_x = pos % (x_len * y_len);
        int line_y = pos / (x_len * y_len);
        int row = pos / (x_len * y_len * y_len);
        List<Integer> change = new ArrayList<Integer>();
        for (int i = 0; i < x_len * y_len; i++) {
            change = getCandidate(x_len * y_len * i + line_x);
            if (change.contains(in)) change.remove((Integer) in);
            setCandidate(x_len * y_len * i + line_x, change);

            change = getCandidate(line_y * x_len * y_len + i);
            if (change.contains(in)) change.remove((Integer) in);
            setCandidate(line_y * x_len * y_len + i, change);

            change = getCandidate(row * x_len * y_len * y_len + (line_x / x_len) * x_len + i % x_len + x_len * y_len * (i / x_len));
            if (change.contains(in)) change.remove((Integer) in);
            setCandidate(row * x_len * y_len * y_len + (line_x / x_len) * x_len + i % x_len + x_len * y_len * (i / x_len), change);
        }

        //printall();
//		System.out.print("\n");
    }

    public void addPriority() {
        for (Map.Entry<Integer, List<Integer>> entry_p : Candidate.entrySet()) {
            if (entry_p.getValue().size() == 1) {
//				System.out.print("\n===========\n "+entry_p.getKey()+"을 제거합니다.\n");
                priority.put(entry_p.getKey(), entry_p.getValue().get(0));
                loop = false;
            } else if (entry_p.getValue().size() == 0) {

            }
        }
        for (Map.Entry<Integer, Integer> entry : priority.entrySet()) {
            Candidate.put(entry.getKey(), new ArrayList<Integer>());
        }
    }

    public void prevent() {
        List<Integer> set = new ArrayList<Integer>();
        for (Entry<Integer, Integer> temp : priority.entrySet()) {
            set.add(temp.getKey());
        }
        for (int temp : set) {
            answer.put(temp, priority.get(temp));
            priority.remove(temp);
        }
//		for(Map.Entry<Integer, Integer> temp:answer.entrySet())
//		{
//			System.out.print("\n"+temp+"\n");
//		}
    }

    public void printall() {
        for (Map.Entry<Integer, List<Integer>> entry : Candidate.entrySet()) {
            System.out.print(entry.getKey() + "" + entry.getValue() + "\n");
        }
        for (Map.Entry<Integer, Integer> entry : priority.entrySet()) {
            System.out.print(entry.getValue() + " ");
        }
        System.out.print("\n");
    }

    public void last() {
        List<HashMap<Integer, Integer>> Remove = new ArrayList<HashMap<Integer, Integer>>();
        for (HashMap<Integer, Integer> hash : answers) {
            HashMap<Integer, Integer> tempHash = new HashMap<>();
            for (Entry<Integer, Integer> temp : hash.entrySet()) {
                tempHash.put(temp.getKey(), temp.getValue());
            }
            Remove.add(tempHash);
        }
        for (HashMap<Integer, Integer> hash : Remove) {
            if (m.SimpleCheck(hash, x_len, y_len)) {
                answers.remove(hash);
            }
        }
    }
}
