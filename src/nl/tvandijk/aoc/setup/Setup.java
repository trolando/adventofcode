package nl.tvandijk.aoc.setup;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Setup {
    private static void createTemplate(int year, int day) throws IOException {
        Path classFilePath = Paths.get(String.format("src/nl/tvandijk/aoc/year%s/day%d/Day%d.java", year, day, day));
        classFilePath.getParent().toFile().mkdirs();
        if (!Files.exists(classFilePath)) {
            String template = Files.readString(Path.of("src/nl/tvandijk/aoc/setup/Day.txt"));
            template = template.replace("%Year%", String.valueOf(year));
            template = template.replace("%Day%", String.valueOf(day));
            Files.writeString(classFilePath, template);
            System.out.printf("Generated class file at: %s", classFilePath.toAbsolutePath());
        } else {
            System.out.printf("Class file exists at: %s", classFilePath.toAbsolutePath());
        }
    }

    private static void downloadInput(String sessionToken, int year, int day) throws IOException, InterruptedException {
        URI uri = URI.create(String.format("https://adventofcode.com/%d/day/%d/input", year, day));
        try (var httpClient = HttpClient.newHttpClient()) {
            var req = HttpRequest.newBuilder(uri).GET().header("cookie", String.format("session=%s", sessionToken)).build();

            // Create the input.txt file if it does not yet exist
            File file = Paths.get(String.format("src/nl/tvandijk/aoc/year%d/day%d/input.txt", year, day)).toFile();
            file.getParentFile().mkdirs();
            if (!file.exists()) {
                httpClient.send(req, HttpResponse.BodyHandlers.ofFile(file.toPath()));
            } else {
                System.out.println("File input.txt already exists!");
                return;
            }

            // Create the example.txt file if it does not yet exist
            File exampleFile = new File(file.getParent(), "example.txt");
            if (!exampleFile.createNewFile()) {
                System.out.println("File example.txt could not be created!");
                return;
            }
        }

        // Write link to stdout
        System.out.printf("Link to puzzle: https://adventofcode.com/%d/day/%d%n", year, day);
    }

    private static int getDay() {
        System.out.print("Setup for day: ");
        var stdin = new Scanner(System.in);
        return stdin.nextInt();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        var sessionToken = Files.readString(Paths.get("resources/session.token")).trim();
        var year = Integer.parseInt(Files.readString(Paths.get("resources/year.txt")).trim());
        var day = getDay();
        downloadInput(sessionToken, year, day);
        createTemplate(year, day);
    }
}
