package Lab5;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/*
  Polecenie:
  W USA nastał czas wyborów... a Ty musisz dostarczyć program, który zliczy oddane głosy i wskaże zwycięzcę.
    Można powiedzieć, że od jakości Twojego rozwiązania zależą losy świata!

    Otrzymałeś/aś właśnie spis głosów (stała VOTES w załączonym pliku Elections.java) jako *pojedynczy* napis złożony
    z wielu linii w formacie:
    WYBRANY_KANDYDAT,IMIĘ__I_NAZWISKO_GŁOSUJĄCEGO,NAZWA_STANU

    Każdą linię kończy znak podziału linii (\n).

    Twoje zadania:
    1. Policz wszystkie głosy i wypisz podsumowanie w formacie:
    KANDYDAT LICZBA_GŁOSÓW
    Do zliczania głosów użyj mapy HashMap<String, Integer>

    2. W rzeczywistości wybory w USA nie są bezpośrednie, a sposób wyłaniania zwycięzcy nieco bardziej skomplikowany.
    Bardzo istotne jest kto zdobył najwięcej głosów w danym stanie, a nie w całym państwie. Upraszczając nieco zasady
    możemy przyjąć, że zwycięzcą zostaje ten, kto wygrał w większej liczbie stanów.
    Napisz nową metodę zliczającą głosy według tej zasady i sprawdź, jak teraz wygląda wynik wyborów.

    Podpowiedź: wartościami przechowywanymi w mapie mogą być również... mapy!

    3. Wśród podanych głosów znalazły się duplikaty - należy je koniecznie wyeliminować żeby wybory były ważne!
    Jakiej zmiany w programie należy dokonać by szybko pozbyć się duplikatów?
    Porównaj wyniki wyborów w punktach 1 i 2 po wyeliminowaniu duplikatów.
 */

public class IAmTheSenate {

    private static final String VOTES =
            "Hilary,Sydnie Jernigan,Floryda\n" +
                    "Hilary,Meagan Fleming,Ohio\n" +
                    "Hilary,Denzil Samuelson,Nowy Jork\n" +
                    "Hilary,Noel Dyer,Arizona\n" +
                    "Donald,Ralf Darrell,Nowy Jork\n" +
                    "Donald,Kerr Norwood,Arizona\n" +
                    "Hilary,Desiree Morin,Nowy Jork\n" +
                    "Donald,Christa Jefferson,Ohio\n" +
                    "Donald,Avaline Romilly,Arizona\n" +
                    "Donald,Caelan Aylmer,Floryda\n" +
                    "Hilary,Kaydence Hepburn,Nowy Jork\n" +
                    "Hilary,Tobias Thorburn,Arizona\n" +
                    "Donald,Lester Royceston,Floryda\n" +
                    "Hilary,Hazel Mann,Nowy Jork\n" +
                    "Donald,Christa Jefferson,Ohio\n" +
                    "Hilary,Hilda Herman,Nowy Jork\n" +
                    "Hilary,Berenice Derrickson,Nowy Jork\n" +
                    "Hilary,Patsy Waters,Nowy Jork\n" +
                    "Hilary,Fran Elliott,Ohio\n" +
                    "Hilary,Denzil Samuelson,Nowy Jork\n" +
                    "Donald,Augusta Tasker,Nowy Jork\n" +
                    "Donald,Fox Sherburne,Arizona\n" +
                    "Donald,Christa Jefferson,Ohio\n" +
                    "Hilary,Desiree Morin,Nowy Jork\n" +
                    "Donald,Christa Jefferson,Ohio";
/*    // Bez usuwania duplikatow
    private String[] stringSlicer() {
        return VOTES.split("\\n");
    }*/

    // Z usuwaniem duplikatow
    // Tak jest chyba najlepiej
    // Oczywiscie z uwzglednieniem mojego obecnego poziomu wiedzy
    private HashSet<String> stringSlicer() {
        String[] votesArray =  VOTES.split("\\n");
        return new HashSet<>(Arrays.asList(votesArray));
    }

    private void majoritySystem() {
        HashMap<String, Integer> numberOfVotes = new HashMap<>();
        HashSet<String> votes = stringSlicer();

        for(String vote : votes) {
            String[] votedCandidate = vote.split(",");
            numberOfVotes.putIfAbsent(votedCandidate[0], 0);
            numberOfVotes.put(votedCandidate[0], numberOfVotes.get(votedCandidate[0]) + 1);
        }

        //Porównujemy w taki sposób, gdyż mamy pewność że klucze będą miałe dokładnie taką formę
        //W innym przypadku implementacja porównywania liczby głosów byłaby inna
        if(numberOfVotes.get("Hilary") > numberOfVotes.get("Donald")) {
            System.out.println("Go go Hilary!");
        } else if((numberOfVotes.get("Hilary") < numberOfVotes.get("Donald"))) {
            System.out.println("We'll build the wall!");
        } else {
            System.out.println("A draw? What!?");
        }
    }

    private void electoralCollegeSystem() {
        HashMap<String, HashMap<String, Integer>> numberOfVotes = new HashMap<>();
        HashSet<String> votes = stringSlicer();
        int statesHilary = 0;
        int statesDonald = 0;

        for(String vote : votes) {
           String[] slicedVote = vote.split(",");

           numberOfVotes.putIfAbsent(slicedVote[2], new HashMap<>());
           HashMap<String, Integer> tempMap = numberOfVotes.get(slicedVote[2]);
           tempMap.putIfAbsent(slicedVote[0], 0);
           tempMap.put(slicedVote[0], tempMap.get(slicedVote[0]) + 1);
           // numberOfVotes.put(slicedVote[2], tempMap);
        }

        for(String state : numberOfVotes.keySet()) {
            if(numberOfVotes.get(state).get("Donald") > numberOfVotes.get(state).get("Hilary")) {
                statesDonald++;
            } else if(numberOfVotes.get(state).get("Donald") < numberOfVotes.get(state).get("Hilary")) {
                statesHilary++;
            } else {
                System.out.println("I dunno what to do");
            }
        }

        if(statesDonald > statesHilary) {
            System.out.println("A small loan of million dollars");
        } else if(statesHilary > statesDonald) {
            System.out.println("I'm just chilling");
        } else {
            System.out.println("Excuse me, what the frick?");
        }
    }

    public static void main(String[] args) {
        IAmTheSenate testObject = new IAmTheSenate();

/*        System.out.println("Przed usunieciem duplikatow");
        System.out.println();
        testObject.majoritySystem();
        testObject.electoralCollegeSystem();*/

        System.out.println("Po usunieciu duplikatow");
        System.out.println();
        testObject.majoritySystem();
        testObject.electoralCollegeSystem();
    }
}
