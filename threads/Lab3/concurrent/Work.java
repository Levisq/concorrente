import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Work implements Runnable {

    private int index;
    private Long[] resultado;
    private File file;
    private String pattern;

    public Work(int index, Long[] resultado, File file, String pattern){
        this.index = index;
        this.resultado = resultado;
        this.file = file;
        this.pattern = pattern;
    }



    public static long countInFile(File file, String pattern) throws IOException {
        long total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    total += countInSequence(line, pattern);
                }
            }
        }
        return total;    
    }

    public static long countInSequence(String sequence, String pattern) {
        if (sequence == null || pattern == null) {
            return 0;
        }
        int n = sequence.length();
        int m = pattern.length();
        if (m == 0 || n < m) {
            return 0;
        }
        long count = 0;
        for (int i = 0; i <= n - m; i++) {
            if (sequence.regionMatches(false, i, pattern, 0, m)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void run() {
        try {
            resultado[index] = countInFile(file, pattern);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
