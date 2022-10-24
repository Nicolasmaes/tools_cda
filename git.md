# Git
## Notions de bases
* Working directory : Fichiers dans l'éditeur de code

* Staging area : Fichiers dans l'index

* Repository : Fichier dans le repo git

## Commandes
* ```git init``` : Permet de créer un fichier .git dans le working directory

* ```git status``` : Montre les fichiers en zone de transit et les modifications faites aux fichiers déjà commit

* ```git add <nom du fichier>``` : Permet de passer un fichier dans la staging area

* ```git commit -m "nom du commit"``` : Permet d'envoyer les fichier de la staging area au repository 

  * ```git commit --amend -m "nom du commit"``` : Permet de modifier le nom d'un commit

  * ```git commit --amend -m --no-edit``` : Permet d'ajouter des changements dans le dernier commit sans en créer de nouveaux

* ```git log``` : Permet d'afficher l'historique des commit

  * ```git log --oneline``` : Permet d'afficher l'historique des commit sur une ligne

  * ```git log --author="nom de l'auteur du commit"``` : Permet d'afficher l'historique des commit d'une personne précise

  * ```git log -<un chiffre>``` : Permet d'afficher l'historique des du nombre de dernier commit relatif au chiffre demandé

  * ```git log --graph``` : Permet d'afficher l'historique des commit avec un graph des branches

* ```git diff``` : Permet de voir les différences apporté entre le fichier sur la machine et le repository avant git add

  * ```git diff --staged``` : Permet de voir les différences apporté entre le fichier en staging area et celui présent sur le repository

* ```git rm <nom du fichier>``` : Permet de supprimer un fichier et de le mettre directement en staging area sans passer par un git add

  * ```git rm --catched <nom du fichier>``` : Permet de supprimer un fichier d'un repository tout en le gardant dans le working directory

* ```git blame <nom du fichier>``` : Permet de voir les modifications et l'auteur de ces modifications pour chaques lignes

* ```git checkout <nom du fichier>``` : Permet de revenir à la version du fichier tel qu'elle était dans le commit précédent

  * ```git checkout <id du commit nom du fichier>``` : Permet de revenir à la version du fichier tel qu'elle était dans le commit dont on a renseigné l'id

  * ```git checkout <id du commit>``` : Permet de regarder l'état d'un projet au moment de ce commit ⚠ Il est impossible d'enregistrer de nouveau commit depuis ce stade

  * ```git checkout <nom de la branche>```: Permet de retrouver l'état initial d'un commit après un git checkout ***id du commit*** et également de se déplacer entre les branche (voir branche)

* ```git clean -f``` : Permet de supprimer les fichiers du working directory qui ne sont pas traqué et qui n'est pas mentionné dans un .gitignore

* ```git revert <id du commit>``` : Permet de supprimer les modifications du commit dont on a renseigné l'id et de revenir à l'ancien commit. Un nouveau commit est créé et le commit dont on a renseigné l'id est conservé dans la liste des commit

  * ```git revert --no-commit <id du commit>``` : Permet de supprimer les modifications du commit dont on a renseigné l'id et de revenir à l'ancien commit sans créer de nouveau commit

* ```git reset``` : Permet d'enlever tous les fichiers de la staging area. Vulgairement, ça annule le git add

  * ```git reset <nom du fichier>``` : Permet d'enlever le fichier nommé de la staging area.

  * ```git reset <id du commit> --hard``` : Permet de revenir à un ancien commit en supprimant tous les commit fait depuis ⚠ Il est impossible de récupérer les données des commit supprimé à ce moment

  * ```git reset <id du commit>``` --soft : Version sécurisé de  ```git reset <id du commit>``` qui permet de revenir à l'ancien commit en gardant le working directory et la staging area comme ils étaient avant le git reset

   * ```git reset <id du commit> --mixed``` : Version sécurisé de  ```git reset <id du commit>``` qui permet de revenir à l'ancien commit en gardant le working directory comme il était avant le git reset c'est l'option par defaut 

   * ```git reflog``` : Permet de récupérer l'historique des références pointé par HEAD et par les branches. Très utiles en cas d'erreur durant un git reset par exemple


## Branch
* ```git branch <nom de la branche>``` : Permet de créer une nouvelle branche ⚠ Ne déplace pas sur la branche, les nouveaux commit resterons sur la branche main (master)

  * ```git branch <nom de la branche> -d``` : Permet de supprimer une branche

  * ```git branch -m``` : Permet de renomer la branche sur laquelle on est actuellement

* ```git merge <nom de la branche>``` : Permet de fusionner deux branche. Pour ce faire il faut se rendre sur la branche finale avec git checkout et ensuite faire git merge avec le nom de la branche que l'on veut fusionner avec

  * ```git merge <nom de la branche> --squash``` : Permet de fusionner tous les commit d'une branche au moment du merge pour n'en former qu'un seul

* ```git stash``` : Permet de créer une sauvegarde d'une branche pour pouvoir switcher entre branche sans commit

  * ```git stash apply``` : Permet de récupérer les données une fois revenu sur la branche sans supprimer le stash

  * ```git stash pop``` : Permet de récupérer les données une fois revenu sur la branche en supprimant le stash

  * ```git stash -u``` : Permet de mettre en stash les fichier qui ne sont pas encore traqué

  * ```git stash branch <nom de la branche>``` : Permet de créer une branche directement depuis les modifications d'un stash

## Répertoire distant
* ```git push```: Permet d'envoyer les commit sur le repo distant

  * ```git push -u origin < nom de branche>```: Permet de faire le lien entre le repo local et le repo distant au niveau de la banche. A utilisé que lors du premier push d'une branche

* ```git pull```: Permet de récupérer les commit sur le repo distant

* ```git remote origin prune -n```: Permet de nettoyer les branhces

* ```git gc```: Permet de supprimer les objet inaccessible et inutile du répertoire et de l'optimiser

## LFS
LFS permet de stocker les gros fichiers type image etc sans saturé le repo.

Pour utiliser LFS, il faut :
* l'installer avec ```git lfs install```
* définir un patern d'objet traqué par lfs avec ```git lfs track '*.jpg'``` dans cet exemple, lfs va traqué tous les fichier jpg
* Un fichier .gitattributes dans lequelle vous retrouverez les fichiers traqué
## .gitignore
* Pour ignorer un fichier il faut taper son nom suivit de l'extention de fichier

* Pour ignorer un dossier il faut taper le nom du dossier suivi d'un /

* Pour sélectionner un type de fichier dans un dossier on tape le nom du fichier suivi d'un /*.txt pour les fichiers texte par exemple

* Pour sélectionner un type de fichier dans un dossier et ses sous dossiers on tape le nom du fichier suivi d'un /**/*.txt