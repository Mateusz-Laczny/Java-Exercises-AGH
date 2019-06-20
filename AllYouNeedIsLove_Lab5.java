package Lab5;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Scanner;

 /*
    Polecenie:
    Napisz program, który pomoże Ci odnaleźć wielką miłość! Przygotuj Andrzejkową wróżbę sprawdzającą dopasowanie dwóch
    osób. Wróżbita pyta o dwa kolejne nazwiska, które podajemy z konsoli. Np:
    Izabela Łęcka
    Stanisław Wokulski

    Następnie wylicza dopasowanie w następujący sposób:
    1. Układa kolejne litery z obu imion i nazwisk obok siebie, spisując w słupku powtórzenia, np:

    i z a b e l ł ę c k s t n w o u
    i...a........l.ł......k..s......w
    i...a.................k .s
    ....a
    ....a

    2. Zlicza wystąpienie każdej z liter:

	3, 1, 5, 1, 1, 2, 2, 1, 1, 3, 3, 1, 1, 2, 1, 1

    3. Sumuje skrajne dwie liczby, następnie dwie kolejne skrajne i tak aż do środka. W przypadku nieparzystej
    liczby elementów środkowy jest po prostu przepisywany:

	4, 2, 7, 2, 2, 5, 5, 2

    4. Powtarza operację 3. tak długo, aż zostaną mu 2 cyfry. Będą one stanowiły % dopasowania sprawdzanej pary.
	6, 7, 1, 2, 4
	1, 0, 9, 1
	2, 9
	Szanse powodzenia: 29% 😕

    Uwaga: jeśli suma dwóch liczb jest większa od 10, rozbijamy ją na dwie cyfry i dopisujemy kolejno do ciągu
    (patrz przykład powyżej)!
 */

public class AllYouNeedIsLove_Lab5 {

    private String[] input() {
        String[] names = new String[2];

        Scanner name = new Scanner(System.in);
        names[0] = name.nextLine();
        names[1] = name.nextLine();

        return names;
    }

    private LinkedList<Integer> numberConverter(int number) {
        // Używam listy zamiast tablicy, bo w teorii suma może być większa od 99,
        // choć jest to mało prawdopodobne.
        // Dzięki użycu listy rozwiązanie jest powinno być odporne na takie przypadki
        LinkedList<Integer> digitList = new LinkedList<>();

        while(number > 0) {
            digitList.push(number % 10);
            number = number/10;
        }
        return digitList;
    }


    private void chanceCalculator(String[] names) {
        LinkedHashMap<Character, Integer> charInstances = new LinkedHashMap<>();

        for (String name : names) {
            for (Character chr : name.toCharArray()) {
                chr = Character.toLowerCase(chr);

                if(Character.isAlphabetic(chr)) {
                    if (charInstances.get(chr) == null) {
                        charInstances.put(chr, 1);
                    } else {
                        charInstances.put(chr, charInstances.get(chr) + 1);
                    }
                }
            }
        }

        ArrayList<Integer> numberList = new ArrayList<>(charInstances.values());
        //LinkedList<Integer> numberList = new LinkedList<>(charInstances.values());

        int listSize = numberList.size();

        // Jest to rozwiązanie również działające, oparte na linked list
        // Nie wiem które jest bardziej wydajne, więc zostawiam oba
        // Wystarczy odkomentować odpowiednie rzeczy

/*      int tempFirst;
        int tempLast;
        int numberToAdd;

        while (listSize > 2) {
            LinkedList<Integer> numberListCopy = new LinkedList<>();

            for(int index = 0; index < listSize/2; index++) {
                tempLast = numberList.removeLast();
                tempFirst = numberList.removeFirst();
                numberToAdd = tempFirst + tempLast;

                if (numberToAdd < 10) {
                    numberListCopy.offerLast(numberToAdd);
                } else {
                    for(int number : numberConverter(numberToAdd)) {
                        numberListCopy.offerLast(number);
                    }
                }
            }

            if(listSize % 2 != 0) {
                numberListCopy.add(numberList.get(0));
            }

            numberList = numberListCopy;
            listSize = numberList.size();
        }*/

        int index = 0;

        while(listSize > 2) {
            ArrayList<Integer> numberListCopy = new ArrayList<>();

            while (index < listSize/2) {
                int tempNumber = numberList.get(index) + numberList.get((listSize - index) - 1);

                if(tempNumber < 10) {
                    numberListCopy.add(tempNumber);
                    index ++;
                } else {
                    numberListCopy.addAll(numberConverter(tempNumber));
                    index ++;
                }
            }

            if(listSize % 2 != 0) {
                numberListCopy.add(numberList.get(listSize/2));
            }

            numberList = numberListCopy;
            listSize = numberListCopy.size();
            index = 0;
        }

        System.out.println("Szanse powodzenia: " + numberList.get(0) + numberList.get(1) + "%");
    }

    public static void main(String[] args) {
        AllYouNeedIsLove_Lab5 testObject = new AllYouNeedIsLove_Lab5();

        String[] testNames = testObject.input();

        testObject.chanceCalculator(testNames);
    }
}