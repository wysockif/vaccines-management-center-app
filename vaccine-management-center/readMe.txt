Podstawowym wymogiem uruchomienia jest posiadanie zainstalowanego i skonfigurowanego środowiska uruchomieniowego – Java Runtime Environment (13.0.2+8).

Do wywołania można posłużyć się poniższą komendą w terminalu, podstawiając odpowiednie wartości – mogą to być zarówno nazwy plików, jak i ścieżki do nich.
W miejsce pliku wyjściowego należy podać nazwę dla utworzonego pliku wynikowego wraz z rozszerzeniem txt.

java -jar program.jar input.txt output.txt

Argumentów nie można zamieniać miejscami i nie można podać  ich mniejszej liczby.
Można za to podać jeden dodatkowy na końcu, który odpowiada za zniesienie limitu górnego ilości wprowadzonych danych.

java -jar nazwaProgramu.jar input.txt output.txt -upper_limit=false

Limit został ustalony na 1000 aptek i 1000 producentów, co daje łącznie 1 000 000 połączeń.
Niemniej jednak program działa poprawnie, również po zniesieniu limitu (ten ma za zadanie jedynie ograniczać czas wykonania,
aby użytko
