#include <cstdlib>  // Per la funzione system
#include <iostream>

int main() {

    // Esecuzione del comando utilizzando la funzione system
    int result = system("start /B .\\data\\JRE\\bin\\javaw.exe -jar .\\data\\FlyffBot.jar");

    // Verifica del risultato dell'esecuzione del comando
    if (result == 0) {
        // Comando eseguito con successo
        // Aggiungi qui il codice da eseguire dopo l'esecuzione del comando
		std::cout << "FlyffBot running - 0" << std::endl;
        return 0;
    } else {
        // Errore durante l'esecuzione del comando
        // Aggiungi qui il codice per gestire l'errore
		std::cout << "FlyffBot running - 1" << std::endl;
        return 1;
    }
}
