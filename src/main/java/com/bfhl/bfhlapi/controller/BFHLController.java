package com.bfhl.bfhlapi.controller;

import com.bfhl.bfhlapi.dto.RequestDTO;
import com.bfhl.bfhlapi.dto.ResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BFHLController {

    @GetMapping("/health")
    public String health() {
        return "UP";
    }

    @PostMapping("/bfhl")
    public ResponseDTO process(
            @RequestHeader(value = "X-Request-Id", required = false) String requestId,
            @RequestBody RequestDTO request) {

        ResponseDTO response = new ResponseDTO();

        long startTime = System.currentTimeMillis();

        List<String> odd = new ArrayList<>();
        List<String> even = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> special = new ArrayList<>();

        Set<String> uniqueElements = new HashSet<>();

        boolean containsDuplicates = false;

        double sum = 0;
        int alphabetCount = 0;
        int numberCount = 0;
        int specialCount = 0;

        Double largest = null;
        Double smallest = null;

        for (String item : request.getData()) {

            if (item == null || item.trim().isEmpty()) {
                continue;
            }

            if (!uniqueElements.add(item)) {
                containsDuplicates = true;
            }

            // Number (supports negative & decimal)
            if (item.matches("-?\\d+(\\.\\d+)?")) {

                double num = Double.parseDouble(item);

                sum += num;
                numberCount++;

                if (largest == null || num > largest)
                    largest = num;

                if (smallest == null || num < smallest)
                    smallest = num;

                if (num % 2 == 0)
                    even.add(item);
                else
                    odd.add(item);
            }

            // Alphabet only
            else if (item.matches("[a-zA-Z]+")) {

                alphabets.add(item.toUpperCase());
                alphabetCount++;
            }

            // Special character
            else {

                special.add(item);
                specialCount++;
            }
        }

        response.setIs_success(true);
        response.setOdd_numbers(odd);
        response.setEven_numbers(even);
        response.setAlphabets(alphabets);
        response.setSpecial_characters(special);

        response.setSum(String.valueOf(sum));

        response.setLargest_number(
                largest == null ? "" : String.valueOf(largest)
        );

        response.setSmallest_number(
                smallest == null ? "" : String.valueOf(smallest)
        );

        response.setAlphabet_count(alphabetCount);
        response.setNumber_count(numberCount);
        response.setSpecial_character_count(specialCount);

        response.setContains_duplicates(containsDuplicates);
        response.setUnique_element_count(uniqueElements.size());

        long endTime = System.currentTimeMillis();

        System.out.println("Processing Time : "
                + (endTime - startTime) + " ms");

        response.setRequest_id(requestId);
        return response;
    }
}