package com.example.feedbackloop.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service    // This annotation is used to mark the class as a service
public class FeedbackService {

    public static void main(String[] args) {
        // This is the main method of the FeedbackService class
        // It is used to test the methods of the FeedbackService class
        FeedbackService feedbackService = new FeedbackService();
        String code ="import java.util.Scanner;\n" +
                "\n" +
                "public class Fibonacci {\n" +
                "    public static void main(String[] args) {\n" +
                "        Scanner scanner = new Scanner(System.in);\n" +
                "        System.out.print(\"Enter the number of terms: \");\n" +
                "        int n = scanner.nextInt();\n" +
                "\n" +
                "        int[] fibSequence = fibonacci(n);\n" +
                "        System.out.print(\"Fibonacci Sequence: \");\n" +
                "        for (int i = 0; i < n; i++) {\n" +
                "            System.out.print(fibSequence[i] + \" \");\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    public static int[] fibonacci(int n) {\n" +
                "        int[] fibSequence = new int[n];\n" +
                "        fibSequence[0] = 0;\n" +
                "        fibSequence[1] = 1;\n" +
                "\n" +
                "        for (int i = 2; i < n; i++) {\n" +
                "            fibSequence[i] = fibSequence[i - 1] + fibSequence[i - 2];\n" +
                "        }\n" +
                "        return fibSequence;\n" +
                "    }\n" +
                "}\n";
//        feedbackService.generateAndVerify(code);
//        feedbackService.generateLlmOutput(code);
//        feedbackService.verifyOutput(code);
//          feedbackService.verifyWithInfer(code);                //DONE
          feedbackService.verifyWithSymbolicExecution(code);

//        feedbackService.verifyWithCheckstyle(code);
    }
    public ResponseEntity<?> generateAndVerify (String code) {
        // This method is used to generate and verify the feedback  for the given url

        HashMap<String, Object> response=new HashMap<>();
        response.put("status", "success");
        response.put("code", code);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> generateLlmOutput (String code) {
        // This method is used to verify the feedback for the given url

        HashMap<String, Object> response=new HashMap<>();
        response.put("status", "success");
        response.put("code", code);

        return ResponseEntity.ok(response);
    }
    public ResponseEntity<?> verifyOutput (String code) {
        // This method is used to verify the feedback for the given url

        HashMap<String, Object> response=new HashMap<>();
        response.put("status", "success");
        response.put("code", code);

        return ResponseEntity.ok(response);
    }
    public ResponseEntity<?> verifyWithInfer(String code) {
        String filePath = "Fibonacci.java"; // Path where the code will be saved

        // Write the submitted code to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(code);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to save the submitted code.");
        }

        // Compile and analyze the code
        try {
            Process compileProcess = Runtime.getRuntime().exec("javac " + filePath);
            int compileResult = compileProcess.waitFor();
            if (compileResult != 0) {
                String errors = readProcessOutput(compileProcess.getErrorStream());
                return ResponseEntity.internalServerError().body("Compilation failed: " + errors);
            }
            String compileLog = readProcessOutput(compileProcess.getInputStream());

            Process inferProcess = Runtime.getRuntime().exec("infer run -- javac " + filePath);
            int inferResult = inferProcess.waitFor();
            if (inferResult != 0) {
                String errors = readProcessOutput(inferProcess.getErrorStream());
                return ResponseEntity.internalServerError().body("Infer analysis failed: " + errors);
            }
            String analysisResult = readProcessOutput(inferProcess.getInputStream());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Code analyzed successfully.");
            response.put("analysisResult", analysisResult); // Return analysis result
            return ResponseEntity.ok(response);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("An error occurred during compilation or analysis.");
        }
    }

    // Helper method to read process output into a String
    private String readProcessOutput(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        }
        return output.toString();
    }
    public ResponseEntity<?> verifyWithCheckstyle (String code) {
        // This method is used to verify the feedback for the given url

        HashMap<String, Object> response=new HashMap<>();
        response.put("status", "success");
        response.put("code", code);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> verifyWithSymbolicExecution(String code) {
        String filePath = "Fibonacci.java";  // Path where the code will be saved
        String className = "Fibonacci";  // Assuming class name is Fibonacci

        // Write the submitted code to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(code);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to save the submitted code.");
        }

        // Compile the code to generate class file
        try {
            Process compileProcess = Runtime.getRuntime().exec("javac " + filePath);
            int compileResult = compileProcess.waitFor();
            if (compileResult != 0) {
                String errors = readProcessOutput(compileProcess.getErrorStream());
                return ResponseEntity.internalServerError().body("Compilation failed: " + errors);
            }

            // Create a .jpf file for Java Pathfinder
            String jpfFilePath = "verification.jpf";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(jpfFilePath))) {
                writer.write("target=" + className);
                writer.newLine();
                writer.write("classpath=.;");
            }

            // Execute Java Pathfinder with the .jpf file
            Process jpfProcess = Runtime.getRuntime().exec("java -jar ./../../jpf-core/build/RunJPF.jar " + jpfFilePath);
            int jpfResult = jpfProcess.waitFor();
            if (jpfResult != 0) {
                String errors = readProcessOutput(jpfProcess.getErrorStream());
                return ResponseEntity.internalServerError().body("Java Pathfinder analysis failed: " + errors);
            }
            String analysisResult = readProcessOutput(jpfProcess.getInputStream());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Code analyzed successfully with Java Pathfinder.");
            response.put("analysisResult", analysisResult);  // Return analysis result
            return ResponseEntity.ok(response);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("An error occurred during compilation or analysis.");
        }
    }

}
