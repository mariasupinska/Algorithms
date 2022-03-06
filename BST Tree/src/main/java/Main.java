import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        // Część A
        BinarySearchTree bst = new BinarySearchTree();

        // (a)
        //readDataFromFile(bst);
        //writeBST(bst);

        // (b)
        //randomValues(bst, 1, 10, 5);

        // (c)
        //chooseOperation(bst);

        // Część B
        BinarySearchTree englishBST = new BinarySearchTree();
        //createEnglishBST(englishBST);

        BinarySearchTree polishBST = new BinarySearchTree();
        createBothBSTs(polishBST, englishBST);

        writeEnglishBST(englishBST);

        chooseDictionaryOperation(englishBST, polishBST);
    }

    private static void createEnglishBST(BinarySearchTree englishBST) throws FileNotFoundException {
        File file = new File("src/main/resources/In0502.txt");
        Scanner scan = new Scanner(file);

        while(scan.hasNext()) {
            Node newValue = new Node(scan.next());
            englishBST.insert(newValue);
        }
    }

    private static void createBothBSTs(BinarySearchTree polishBST, BinarySearchTree englishBST) throws FileNotFoundException {
        File file = new File("src/main/resources/Translation.txt");
        Scanner scan = new Scanner(file);

        while(scan.hasNext()) {
            String englishWord = scan.next();
            String polishTranslation = scan.next();

            Node englishNode = new Node(englishWord);
            Node polishNode = new Node(polishTranslation);

            englishNode.translation = polishNode;
            polishNode.translation = englishNode;

            polishBST.insert(polishNode);
            englishBST.insert(englishNode);
        }
    }

    private static void writeEnglishBST(BinarySearchTree englishBST) throws IOException {
        FileWriter fileWriter = new FileWriter("src/main/resources/OutB0502.txt");
        fileWriter.write(englishBST.toKLPStringWithTranslation());
        fileWriter.flush();
    }

    private static void writePolishBST(BinarySearchTree polishBST) throws IOException {
        FileWriter fileWriter = new FileWriter("src/main/resources/OutB0502.txt");
        fileWriter.write(polishBST.toKLPStringWithTranslation());
        fileWriter.flush();
    }

    private static void chooseDictionaryOperation(BinarySearchTree englishBST, BinarySearchTree polishBST) throws IOException {
        Scanner scan = new Scanner(System.in);

        while(true) {
            dictionaryMenu();
            int answer = scan.nextInt();
            switch (answer) {
                case 1:
                    languageChoice();
                    int secondAnswer = scan.nextInt();

                    if ( secondAnswer == 1 ) {
                        writeEnglishBST(englishBST);
                        System.out.println("Pomyślnie wypisano do pliku.");
                    }
                    if ( secondAnswer == 2 ) {
                        writePolishBST(polishBST);
                        System.out.println("Pomyślnie wypisano do pliku.");
                    }
                    break;
                case 2:
                    languageChoice();
                    secondAnswer = scan.nextInt();

                    if ( secondAnswer == 1 ) {
                        System.out.println("Wpisz szukane słowo:");
                        String word = scan.next();

                        Node lookedFor = new Node(word);
                        Node result = englishBST.search(lookedFor);
                        if ( result == null ) System.out.println("Nie znaleziono wyrazu.");
                        else System.out.println(result.value + " " + result.translation.value);
                    }
                    if ( secondAnswer == 2 ) {
                        System.out.println("Wpisz szukane słowo:");
                        String word = scan.next();

                        Node lookedFor = new Node(word);
                        Node result = polishBST.search(lookedFor);
                        if ( result == null ) System.out.println("Nie znaleziono wyrazu.");
                        else System.out.println(result.value + " " + result.translation.value);
                    }
                    break;
                case 3:
                    languageChoice();
                    secondAnswer = scan.nextInt();

                    if ( secondAnswer == 1 ) {
                        System.out.println("Podaj słowo w języku angielskim:");
                        String englishWord = scan.next();
                        System.out.println("Podaj tłumaczenie:");
                        String polishTranslation = scan.next();

                        Node englishNode = new Node(englishWord);
                        Node polishNode = new Node(polishTranslation);

                        englishNode.translation = polishNode;
                        polishNode.translation = englishNode;

                        polishBST.insert(polishNode);
                        englishBST.insert(englishNode);

                        System.out.println("Pomyślnie dodano nowe słowo.");
                    }
                    if ( secondAnswer == 2 ) {
                        System.out.println("Podaj słowo w języku polskim:");
                        String polishWord = scan.next();
                        System.out.println("Podaj tłumaczenie:");
                        String englishTranslation = scan.next();

                        Node polishNode = new Node(polishWord);
                        Node englishNode = new Node(englishTranslation);

                        polishNode.translation = englishNode;
                        englishNode.translation = polishNode;

                        polishBST.insert(polishNode);
                        englishBST.insert(englishNode);

                        System.out.println("Pomyślnie dodano nowe słowo.");
                    }
                    break;
                case 4:
                    System.out.println("Podaj słowo:");
                    String word = scan.next();

                    Node toBeDeleted = new Node(word);
                    /*Node result = polishBST.search(toBeDeleted);
                    if ( result != null ) {
                        englishBST.delete(result.translation.value);
                        polishBST.delete(word);
                        System.out.println("Pomyślnie usunięto słowo.");
                    } else {

                     */
                        Node result = englishBST.search(toBeDeleted);
                        if ( result == null ) System.out.println("Nie znaleziono wyrazu.");
                        else {
                            polishBST.delete(result.translation.value);
                            englishBST.delete(word);
                            System.out.println("Pomyślnie usunięto słowo.");
                        }

                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Niepoprawna opcja. Spróbuj jeszcze raz.");
            }
        }
    }

    private static void dictionaryMenu() {
        System.out.println("1. Wypisz wyrazy wraz z tłumaczeniami w kolejności KLP do pliku.");
        System.out.println("2. Znajdź wyraz z tłumaczeniem.");
        System.out.println("3. Dodaj nowe słowo wraz z tłumaczeniem.");
        System.out.println("4. Usuń słowo wraz z tłumaczeniem.");
        System.out.println("5. Zakończ.");
    }

    private static void languageChoice() {
        System.out.println("Wybrana operacja będzie:");
        System.out.println("1. angielsko - polska");
        System.out.println("2. polsko - angielska");
    }

    private static void readDataFromFile(BinarySearchTree bst) throws FileNotFoundException {
        File file = new File("src/main/resources/InTest1.txt");
        Scanner scan = new Scanner(file);

        while(scan.hasNextInt()) {
            Node newValue = new Node(scan.nextInt());
            bst.insert(newValue);
        }
    }

    private static void writeBST(BinarySearchTree bst) throws IOException {
        FileWriter fileWriter = new FileWriter("src/main/resources/OutTest1.txt");
        fileWriter.write(bst.toKLPString());
        fileWriter.flush();
    }

    private static void randomValues(BinarySearchTree bst, int minValue, int maxValue, int amount) throws IOException {
        FileWriter fileWriter = new FileWriter("src/main/resources/OutTest2.txt");

        Random rand = new Random();

        for ( int i = 1; i <= amount; i++ ) {
            int randValue = rand.nextInt(maxValue - minValue +1) + minValue;
            fileWriter.write(String.valueOf(randValue) + " ");
            fileWriter.flush();
            Node newValue = new Node(randValue);
            bst.insert(newValue);
        }

        fileWriter.write('\n' + "BST: " + bst.toKLPString());
        fileWriter.flush();
    }

    private static void menu() {
        System.out.println("1. Dodaj element do drzewa BST.");
        System.out.println("2. Usuń element do drzewa BST.");
        System.out.println("3. Wypisz elementy drzewa BST.");
        System.out.println("4. Zakończ.");
    }

    private static void chooseOperation(BinarySearchTree bst) {
        Scanner scan = new Scanner(System.in);

        while(true) {
            menu();
            int answer = scan.nextInt();
            switch (answer) {
                case 1:
                    System.out.println("Podaj element do dodania:");
                    int toBeAdded = scan.nextInt();
                    Node newValue = new Node(toBeAdded);
                    bst.insert(newValue);
                    System.out.println("Pomyślnie dodano element.");
                    break;
                case 2:
                    System.out.println("Podaj element do usunięcia:");
                    int toBeDeleted = scan.nextInt();
                    bst.delete(toBeDeleted);
                    System.out.println("Pomyślnie usunięto element.");
                    break;
                case 3:
                    System.out.println(bst.toKLPString());
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Niepoprawna opcja. Spróbuj jeszcze raz.");
            }
        }
    }
}
