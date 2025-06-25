# Završni Rad: Aplikacija za Mobilno Plaćanje (Revolut SDK)

Prototip Android aplikacije razvijen u sklopu završnog rada na temu "Tehnologije mobilnog plaćanja s implementacijom u Kotlinu".

## O Projektu

Cilj ovog projekta bio je istražiti i praktično demonstrirati integraciju modernog platnog servisa u nativnu Android aplikaciju. Aplikacija simulira jednostavno sučelje za e-trgovinu, a kao studija slučaja koristi se Revolut Merchant SDK za obradu kartičnih plaćanja u testnom (*sandbox*) okruženju.

Projekt je izrađen prateći suvremene prakse razvoja za platformu Android, s naglaskom na čistu i testibilnu arhitekturu.

### Glavne Funkcionalnosti

- Pregled liste proizvoda
- Prikaz detaljnih informacija o pojedinom proizvodu
- Dodavanje i uklanjanje proizvoda iz košarice
- Proces naplate s unosom podataka o kupcu
- Integracija s Revolut sučeljem za unos kartičnih podataka
- Lokalna, enkriptirana pohrana povijesti transakcija u SQLite bazi
- Ekran za pregled povijesti svih transakcija

### Korištene Tehnologije

- **Jezik:** [Kotlin](https://kotlinlang.org/)
- **Arhitektura:** [MVVM (Model-View-ViewModel)](https://developer.android.com/jetpack/guide) + Repository uzorak
- **Korisničko sučelje:** Android XML Layouts & Material Design
- **Asinkrone operacije:** Kotlin Coroutines
- **Mrežni pozivi:** [Retrofit 2](https://square.github.io/retrofit/) & [OkHttp 3](https://square.github.io/okhttp/)
- **Platni servis:** [Revolut Merchant Card Form SDK](https://developer.revolut.com/docs/accept-payments/android/merchant-card-form-sdk-for-android/introduction)
- **Baza podataka:** [SQLite](https://developer.android.com/training/data-storage/sqlite)
- **Testiranje:** JUnit 4, AndroidX Test (Espresso, Espresso Intents)

---

## Pokretanje Projekta

Za pokretanje projekta na lokalnom računalu, slijedite ove korake.

### Preduvjeti

- [Android Studio](https://developer.android.com/studio) (preporučena najnovija stabilna verzija)
- Git

### Instalacija

1.  Klonirajte repozitorij na vaše računalo:
    ```sh
    git clone [https://github.com/jlisjak1/Zavrsni-rad-Android-aplikacija-za-placanje-putem-Revolut-SDK.git](https://github.com/jlisjak1/Zavrsni-rad-Android-aplikacija-za-placanje-putem-Revolut-SDK.git)
    ```
2.  Otvorite projekt u Android Studiju.
3.  Pričekajte da Gradle sinkronizira i preuzme sve potrebne ovisnosti.
4.  Pokrenite aplikaciju na emulatoru ili fizičkom uređaju.

### Konfiguracija

Aplikacija koristi testni (sandbox) autorizacijski ključ za Revolut API koji je radi jednostavnosti upisan direktno u kodu.

-   Datoteka: `app/src/main/java/com/example/zavrsnirad/network/RevolutApiClient.kt`

**Napomena:** U produkcijskom okruženju, API ključevi se nikada ne bi smjeli nalaziti direktno u kodu. Ispravna praksa je pohraniti ih u `local.properties` datoteku koja je isključena iz sustava za kontrolu verzija (Git).

## Testiranje

Projekt sadrži sveobuhvatan paket automatiziranih testova za provjeru ispravnosti aplikacije.
-   Jedinični testovi: Nalaze se u `app/src/test/` direktoriju.
-   Instrumentacijski testovi: Nalaze se u `app/src/androidTest/` direktoriju.

Za pokretanje svih testova, u Android Studiju desnom tipkom miša kliknite na `app` modul i odaberite opciju Run 'Tests' in 'app'. Za instrumentacijske testove potreban je pokrenut emulator ili spojen fizički uređaj.

## Kontakt

Jakov Lisjak - *[jlisjak@student.foi.hr]*

Link na projekt: [https://github.com/jlisjak1/Zavrsni-rad-Android-aplikacija-za-placanje-putem-Revolut-SDK](https://github.com/jlisjak1/Zavrsni-rad-Android-aplikacija-za-placanje-putem-Revolut-SDK)
