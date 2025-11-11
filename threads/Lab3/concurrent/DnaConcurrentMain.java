import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.*;

public class DnaConcurrentMain {

    private static Long[] resultado;//[0,0,0,0,0,0,0]

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            System.err.println("Uso: java DnaSerialMain DIRETORIO_ARQUIVOS PADRAO");
            System.err.println("Exemplo: java DnaSerialMain dna_inputs CGTAA");
            System.exit(1);
        }

        String dirName = args[0];
        String pattern = args[1];

        File dir = new File(dirName);
        if (!dir.isDirectory()) {
            System.err.println("Caminho não é um diretório: " + dirName);
            System.exit(2);
        }

        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if (files == null || files.length == 0) {
            System.err.println("Nenhum arquivo .txt encontrado em: " + dirName);
            System.exit(3);
        }
        Thread[] threads = new Thread[files.length];
        resultado = new Long[files.length];


        long total = 0;
        
        for(int i = 0; i < files.length; i++){
            Thread thread = new Thread(new Work(i, resultado, files[i], pattern));
            threads[i] = thread;
            threads[i].start();
        }

        for(int i = 0; i < threads.length; i++){
            threads[i].join();
        }

        for(Long r : resultado){
            total += r;
        }


        System.out.println("Sequência " + pattern + " foi encontrada " + total + " vezes.");
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

}
