package com.lhv.sanctionedpeopledetector.core.sanctionedname.usecase;

import com.lhv.sanctionedpeopledetector.spi.UseCase;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.io.FileReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UseCase
@RequiredArgsConstructor
public class UpdateSanctionedNamesInBase {
    private static final String INPUT_FILE_NAME = "src/main/resources/data/SanctionedNames.csv";

    private final SaveSanctionedNames saveSanctionedNames;

    @PostConstruct
    public void execute() {
        Set<String> sanctionedNames = getSanctionedNames();
        saveSanctionedNames.execute(SaveSanctionedNames.Request.builder()
                                                               .names(sanctionedNames)
                                                               .build());
    }

    private Set<String> getSanctionedNames() {
        Set<String> namesSet = new HashSet<>();

        CSVParser parser = new CSVParserBuilder().withSeparator(';')
                                                 .build();
        try (CSVReader reader =
                     new CSVReaderBuilder(new FileReader(INPUT_FILE_NAME)).withCSVParser(parser)
                                                                                     .build()) {
            List<String[]> allRows = reader.readAll();

            String[] header = allRows.getFirst();
            int nameIndex = -1;
            int typeIndex = -1;

            for (int i = 0; i < header.length; i++) {
                if (header[i].equalsIgnoreCase("NameAlias_WholeName")) {
                    nameIndex = i;
                }
                if (header[i].equalsIgnoreCase("Entity_SubjectType_ClassificationCode")) {
                    typeIndex = i;
                }
            }

            if (nameIndex == -1 || typeIndex == -1) {
                System.out.println("Required columns not found.");
                return namesSet;
            }

            for (int i = 1; i < allRows.size(); i++) {
                String name = allRows.get(i)[nameIndex];
                String type = allRows.get(i)[typeIndex];

                if ("person".equalsIgnoreCase(type) && isNameValid(name)) {
                    namesSet.add(name);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return namesSet;
    }

    private boolean isNameValid(String name) {
        Pattern pattern = Pattern.compile("[^a-zA-Z ]");
        Matcher matcher = pattern.matcher(name);
        return !matcher.find();
    }
}
