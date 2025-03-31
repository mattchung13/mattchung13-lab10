import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class WordCounter {

    public static StringBuffer processFile(String path) throws EmptyFileException {
        Scanner scanner = null;
        File file = new File(path);

        while (true) {
            try {
                scanner = new Scanner(file);
                break;
            } catch (FileNotFoundException e) {
                System.out.print("File not found. Please enter a valid file path:");
                Scanner input = new Scanner(System.in);
                path = input.nextLine();
                file = new File(path);
            }
        }

        StringBuilder content = new StringBuilder();
        while (scanner.hasNextLine()) {
            content.append(scanner.nextLine()).append(" ");
        }
        scanner.close();

        if (content.toString().trim().isEmpty()) {
            throw new EmptyFileException(file.getName() + " was empty");
        }

        return new StringBuffer(content.toString());
    }

    public static int processText(StringBuffer text, String stopword)
            throws InvalidStopwordException, TooSmallText {
        Pattern regex = Pattern.compile("[a-zA-Z0-9']+");
        Matcher matcher = regex.matcher(text);

        int count = 0;
        boolean stopFound = (stopword == null);
        int totalWordsSeen = 0;

        while (matcher.find()) {
            String word = matcher.group();
            totalWordsSeen++;

            if (!stopFound && word.equals(stopword)) {
                stopFound = true;
                break;
            }

            if (stopFound) {
                count++;
            }
        }

        if (stopword != null && !stopFound) {
            throw new InvalidStopwordException("Couldn't find stopword: " + stopword);
        }

        if (stopword != null) {
            count += 1; // include stopword itself
        } else {
            count = totalWordsSeen;
        }

        if (count < 5) {
            throw new TooSmallText("Only found " + count + " words.");
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String mode = "";
        while (!mode.equals("1") && !mode.equals("2")) {
            System.out.println("Enter 1 to process file or 2 to process text:");
            mode = input.nextLine().trim();
        }

        String stopword = null;
        if (args.length > 1) {
            stopword = args[1];
        }

        StringBuffer text = new StringBuffer();

        if (mode.equals("1")) {
            String path = args.length > 0 ? args[0] : "";
            try {
                text = processFile(path);
            } catch (EmptyFileException e) {
                System.out.println(e.toString().trim());
                text = new StringBuffer();
            }
        } else {
            if (args.length > 0) {
                text = new StringBuffer(args[0]);
            } else {
                System.out.println("Enter text to process:");
                text = new StringBuffer(input.nextLine());
            }
        }

        while (true) {
            try {
                int count = processText(text, stopword);
                System.out.println("Found " + count + " words.");
                break;
            } catch (InvalidStopwordException e) {
                System.out.println(e.toString().trim());
                System.out.println("Enter stopword again:");
                stopword = input.nextLine();
            } catch (TooSmallText e) {
                System.out.println(e.toString().trim());
                break;
            }
        }
    }
}
