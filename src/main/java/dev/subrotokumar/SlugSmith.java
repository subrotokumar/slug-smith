package dev.subrotokumar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class SlugSmith {

    private static final int DEFAULT_NUMBER_OF_WORDS = 3;

    public enum Case {
        KEBAB, CAMEL, TITLE, LOWER, SENTENCE
    }

    public static class Options {
        private final List<WordSelector> wordSelector;
        private final Map<WordSelector, List<String>> categories;
        private final Case format;

        public Options(List<WordSelector> wordSelector, 
                       Map<WordSelector, List<String>> categories, 
                       Case format) {
            this.wordSelector = wordSelector;
            this.categories = categories;
            this.format = format;
        }

        public List<WordSelector> getWordSelector() {
            return wordSelector;
        }

        public Map<WordSelector, List<String>> getCategories() {
            return categories;
        }

        public Case getFormat() {
            return format;
        }
    }

    public static List<String> getWordsByCategory(WordSelector partOfSpeech, List<String> categories) {
        Set<String> selectedCategories = new HashSet<>(categories);
        List<String> selectedWords = new ArrayList<>();

        for (WordList.Word word : WordList.wordList.get(partOfSpeech)) {
            if (categories.isEmpty() || word.getCategories().stream().anyMatch(selectedCategories::contains)) {
                selectedWords.add(word.getWord());
            }
        }
        return selectedWords;
    }

    private static String generateSlug(Integer numberOfWords, Options options) {
        int numWords = (numberOfWords != null) ? numberOfWords : DEFAULT_NUMBER_OF_WORDS;
        Options defaultOptions = new Options(
                getDefaultWordSelector(numWords),
                new HashMap<>(),
                Case.KEBAB
        );

        Options opts = (options != null) ? options : defaultOptions;
        List<String> words = new ArrayList<>();
        for (int i = 0; i < numWords; i++) {
            System.out.println(opts.getWordSelector().size());
            WordSelector partOfSpeech = opts.getWordSelector().get(i);
            List<String> candidates = getWordsByCategory(partOfSpeech, opts.getCategories().getOrDefault(partOfSpeech, Collections.emptyList()));
            String rand = candidates.get(new Random().nextInt(candidates.size()));
            words.add(rand);
        }

        return formatter(words, opts.getFormat());
    }

    public static String generateSlug() {
        Integer numberOfWords=null; 
        Options options=null;
        int numWords = (numberOfWords != null) ? numberOfWords : DEFAULT_NUMBER_OF_WORDS;
        Options defaultOptions = new Options(
                getDefaultWordSelector(numWords),
                new HashMap<>(),
                Case.KEBAB
        );

        Options opts = (options != null) ? options : defaultOptions;
        List<String> words = new ArrayList<>();
        for (int i = 0; i < numWords; i++) {
            System.out.println(opts.getWordSelector().size());
            WordSelector partOfSpeech = opts.getWordSelector().get(i);
            List<String> candidates = getWordsByCategory(partOfSpeech, opts.getCategories().getOrDefault(partOfSpeech, Collections.emptyList()));
            String rand = candidates.get(new Random().nextInt(candidates.size()));
            words.add(rand);
        }

        return formatter(words, opts.getFormat());
    }

    private static List<WordSelector> getDefaultWordSelector(int length) {
        List<WordSelector> wordSelector = new ArrayList<>();
        for (int i = 0; i < length - 1; i++) {
            wordSelector.add(WordSelector.ADJECTIVE);
        }
        wordSelector.add(WordSelector.NOUN);
        return wordSelector;
    }

    private static String formatter(List<String> arr, Case format) {
        switch (format) {
            case KEBAB -> {
                return arr.stream().map(String::toLowerCase).collect(Collectors.joining("-"));
            }
            case CAMEL -> {
                return arr.stream().map(String::toLowerCase).collect(Collectors.joining(""));
            }
            case LOWER -> {
                return arr.stream().map(String::toLowerCase).collect(Collectors.joining(" "));
            }
            case SENTENCE -> {
                return arr.stream().map(el -> el.substring(0, 1).toUpperCase() + el.substring(1).toLowerCase())
                        .collect(Collectors.joining(" "));
            }
            case TITLE -> {
                return arr.stream().map(el -> el.substring(0, 1).toUpperCase() + el.substring(1).toLowerCase())
                        .collect(Collectors.joining(" "));
            }
            default -> throw new IllegalArgumentException("Invalid format case");
        }
    }

    private static int totalUniqueSlugs(Integer numberOfWords, Options options) {
        List<String> adjectives = getWordsByCategory(WordSelector.ADJECTIVE, 
                (options != null && options.getCategories() != null) ? options.getCategories().getOrDefault(WordSelector.ADJECTIVE, Collections.emptyList()) : Collections.emptyList());
        List<String> nouns = getWordsByCategory(WordSelector.NOUN, 
                (options != null && options.getCategories() != null) ? options.getCategories().getOrDefault(WordSelector.NOUN, Collections.emptyList()) : Collections.emptyList());

        Map<WordSelector, Integer> nums = new HashMap<>();
        nums.put(WordSelector.ADJECTIVE, adjectives.size());
        nums.put(WordSelector.NOUN, nouns.size());

        int numWords = (numberOfWords != null) ? numberOfWords : DEFAULT_NUMBER_OF_WORDS;
        List<WordSelector> wordSelector = (options != null && options.getWordSelector() != null) ? options.getWordSelector() : getDefaultWordSelector(numWords);

        int combos = 1;
        for (int i = 0; i < numWords; i++) {
            combos *= nums.get(wordSelector.get(i));
        }
        return combos;
    }

    public static int totalUniqueSlugs() {
        Integer numberOfWords=null;
        Options options=null;
        List<String> adjectives = getWordsByCategory(WordSelector.ADJECTIVE, 
                (options != null && options.getCategories() != null) ? options.getCategories().getOrDefault(WordSelector.ADJECTIVE, Collections.emptyList()) : Collections.emptyList());
        List<String> nouns = getWordsByCategory(WordSelector.NOUN, 
                (options != null && options.getCategories() != null) ? options.getCategories().getOrDefault(WordSelector.NOUN, Collections.emptyList()) : Collections.emptyList());

        Map<WordSelector, Integer> nums = new HashMap<>();
        nums.put(WordSelector.ADJECTIVE, adjectives.size());
        nums.put(WordSelector.NOUN, nouns.size());

        int numWords = (numberOfWords != null) ? numberOfWords : DEFAULT_NUMBER_OF_WORDS;
        List<WordSelector> wordSelector = (options != null && options.getWordSelector() != null) ? options.getWordSelector() : getDefaultWordSelector(numWords);

        int combos = 1;
        for (int i = 0; i < numWords; i++) {
            combos *= nums.get(wordSelector.get(i));
        }
        return combos;
    }
    
    public static void main(String[] args) {
        // Options options = new Options(
        //         Arrays.asList(WordSelector.ADJECTIVE, WordSelector.NOUN),
        //         new HashMap<>(),
        //         Case.KEBAB
        // );

        String slug = SlugSmith.generateSlug();
        System.out.println(slug);
        int total = SlugSmith.totalUniqueSlugs();
        System.out.println(total);
    }
}
