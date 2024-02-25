package ru.vlasov.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Util {
    public static final Scanner scanner = new Scanner(System.in);

    public static String readLine() {
        return scanner.nextLine();
    }

    public static List<String> readLines() {
        List<String> lines = new ArrayList<>();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        while (true) {
            try {
                if ((line = in.readLine()) == null || line.equals("end")) break;
            } catch (IOException e) {
                System.out.println("Can't read line: " + e);
            }
            lines.add(line);
        }
        return lines;
    }
}
