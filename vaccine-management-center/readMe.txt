Podstawowym wymogiem uruchomienia jest posiadanie zainstalowanego i skonfigurowanego środowiska uruchomieniowego – Java Runtime Environment (13.0.2+8).
Do wywołania można posłużyć się poniższą komendą, podstawiając odpowiednie wartości – mogą to być zarówno nazwy plików, jak i ścieżki do nich.
W miejsce pliku wyjściowego (output.txt) należy  podać nazwę dla utworzonego pliku wynikowego.

java -jar program.jar input.txt output.txt

Argumentów nie można zamieniać miejscami i nie można podać  ich mniejszej liczby.
Można za to podać jeden dodatkowy na końcu, który odpowiada za zniesienie limitu górnego ilości wprowadzonych danych.

java -jar nazwaProgramu.jar input.txt output.txt -upper_limit=false

Limit został oszacowany (na 500 aptek i 500 producentów, co daje łącznie 250 000 połączeń) poprzez dobranie takiej ilości danych,
aby ta nie przekraczała (subiektywnie) racjonalnego czasu wykonania.

Niemniej jednak program działa poprawnie, również po zniesieniu limitu.
Warto mieć na uwadze wówczas, że czas wykonania dla danych przekraczających ten limit może okazać się znacząco dłuższy.
