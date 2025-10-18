import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HashSimulatorDriver {

    /*
      从文本文件读取姓名，构造字符串数组。
      文件每行一个名字；跳过空行；两次遍历先计数、再填充，以确保使用数组而非集合。
    */
    private static String[] loadNamesToArray(String path) throws IOException
    {
        int count = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(path)))
        {
            String line;
            while((line = br.readLine()) != null)
            {
                if(!line.trim().isEmpty())
                {
                    count++;
                }
            }
        }
        String[] arr = new String[count];
        int idx = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(path)))
        {
            String line;
            while((line = br.readLine()) != null)
            {
                String s = line.trim();
                if(!s.isEmpty())
                {
                    arr[idx++] = s;
                }
            }
        }
        return arr;
    }

    /*
      打印单行结果的工具方法。
      形参 res 为 runHashSimulation 返回的长度为 6 的结果数组。
    */
    private static void printLine(String file, int tableSize, int[] res)
    {
        System.out.printf(
                "%s\t%d\tH1: collisions=%d probes=%d\tH2: collisions=%d probes=%d\tH3: " +
                        "collisions=%d probes=%d%n",
                file, tableSize, res[0], res[1], res[2], res[3], res[4], res[5]
        );
    }

    /*
      main 仅用于本地自测。
      固定读取附件中的三个文件：
        37names.txt
        798names.txt
        5746names.txt
      每个文件按 N、2N、5N、10N、100N 的表大小运行并打印结果。
    */
    public static void main(String[] args) throws Exception
    {
        String[] files = new String[]{
                "37names.txt",
                "798names.txt",
                "5746names.txt"
        };

        for(String path : files)
        {
            String[] keys = loadNamesToArray(path);
            int n = keys.length;
            int[] sizes = new int[]{n, 2 * n, 5 * n, 10 * n, 100 * n};
            HashSimulator sim = new HashSimulator();

            for(int size : sizes)
            {
                int[] res = sim.runHashSimulation(keys, size);
                printLine(path, size, res);
            }
        }
    }
}
