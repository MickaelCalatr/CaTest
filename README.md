# CA Test

Le test à été réalisé en environ 10h.

## Détails techniques

- Clean architecture, pattern **MVVM** + **Repository**
- **Jetpack Compose** et **Single Actitity**
- Projet multi-module **Core**, **date**, **domain**, **ui-ds**, **app**
- Projet full **Kotlin** + **Coroutine**  + **LiveData** + **Flow**
- Client HTTP et parsing JSON, **Retrofit** + **Gson**
- Dependencies Injection via **Hilt**
- Unit Test via **JUnit**, **Expresso**, **Robolectric**, **Turbine**,
- Mocking via **Mokk** qui facilite le mocking des fonctions suspend
- Gestion du theme clair/sombre

## Détails visuel

L'application se présente sous 2 écrans.
- Un écran qui affiche la liste des comptes, chaque compte est une cellule dépliante.
- Un écran qui affiche le détail des opérations d'un compte.

Vous trouverez les détails du test dans le dossier `./details`.