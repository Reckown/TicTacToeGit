# TicTacToeGit

Fait par Pierre Landais et Tristan Roux, étudiant à l'ISEN de Nantes,

## Authentification : 
Lorsque l'utilisateur se connecte, il doit rentrer son numéro de téléphone ainsi que l'indicatif de son pays, qu'il peut rentrer avec le spinner.
Une sécurité est présente pour éviter que le numéro soit trop court, et pour empecher les lettres. 

Si la personne est déjà connécté à l'application, alors elle arrivera directement au menu principal. Sinon, il y aura l'envoie d'un code par SMS pour confirmer son numéro de téléphone. 

## Menu principal : 
Sur le menu principal, l'utilisateur a le choix entre 3 mode de jeu, le mode contre un ordinateur, le mode multijoueur local et le mode multijoueur en ligne. 

## Multijoueur contre une IA : 
Lorsque l'on choisit de jouer contre une IA un nouveau menu apparait ou l'on peut choisir la difficulté de l'intélligence artificiel, ainsi que si l'on veut jouer les ronds ou les croix. 
En dificulté facile : l'algorithme choisi des coups aléatoires
En dificulté normale : algorithme de MinMax en profondeur 2
En difficulté maximale : MinMax en profondeur 9 (le max)
L'algorithme de MinMax est un algo très utilisé dans le monde de l'IA et surtout de l'IA en tour par tour. Il est nottament très utilisé pour les IA d'echecs. L'algorithme va aller voir toutes les possibilitées et remonter les coups gagnants/perdants afin que l'IA puisse ensuite choisir le meilleur coup à jouer. https://en.wikipedia.org/wiki/Minimax
Afin de tester le bon fonctionnement de notre IA, nous l'avons fait jouer contre l'IA de tic tac toe de google en mode maximal, et nous n'avons obtenu que des matchs nul, ce qui confirme notre IA marche bien.

## Mutlijoueur en local : 
Dans ce mode de jeu, les deux joueurs sont derriere le meme téléphone, le joueur 1 joue les ronds et le joueur 2 les croix. La partie ce déroule de maniere classique avec la possibilitée de recommancer une partie. 

## Multijoueur en ligne : 
Mode de jeu principale de l'application, il s'agit d'un multijoueur entre deux personnes sur deux téléphones différents, nous utilisons la base de données realTimeDB de firebase pour pouvoir jouer et faire transiter les coups d'un téléphone à l'autre. 
Il existe un mode spéctateur, pour les personnes en trop se connectant à la partie. 
Le boutton rejouer permet de relancer la partie, 
Le boutton retour renvoit au menu précédent,
Le boutton reset efface toute la base de données et relance une nouvelle partie,
En cas d'appui sur le boutton reset il faut que le joueur 1 appui sur reset et le joueur 2 sur rejouer sinon la base sera effacé deux fois et le jeu ne marchera pas !
**# TicTacToeGit

Application Android réalisée par Pierre Landais et Tristan Roux, étudiants à l'ISEN Nantes Yncréa Ouest

## Authentification  
Lorsque l'utilisateur se connecte, il doit rentrer son numéro de téléphone ainsi que l'indicatif de son pays, qu'il peut sélectionner avec le spinner.
Une sécurité est présente afin d'éviter que le numéro soit trop court, et pour empêcher les lettres et carctères spéciaux.

Si la personne est déjà connecté à l'application, alors elle arrivera directement au menu principal. Sinon, il y aura l'envoie d'un code par SMS pour confirmer son numéro de téléphone. 

## Menu principal  
Sur le menu principal, l'utilisateur a le choix entre 3 modes de jeu : en local contre un ordinateur, le mode multijoueur local et le mode multijoueur en ligne. 

## Multijoueur contre une IA  
Lorsque l'on choisit de jouer contre une IA, un nouveau menu apparait où l'on peut choisir la difficulté de l'intelligence artificielle ainsi que si l'on veut jouer les ronds ou les croix. 
En difficulté facile, l'algorithme choisit aléatoirement la case
En difficulté normale, l'IA utilise un algorithme de MinMax en profondeur 2
En difficulté maximale, l'IA utilise un algorithme de MinMax en profondeur 9 (le maximum car 9 cases)
L'algorithme de MinMax est un algo très utilisé dans le monde de l'IA et surtout de l'IA en tour par tour. Il est nottamment très utilisé pour les IA d'échecs. L'algorithme va aller voir toutes les possibilités et remonter les coups gagnantsperdants afin que l'IA puisse ensuite choisir le meilleur coup à jouer. httpsen.wikipedia.orgwikiMinimax
Afin de tester le bon fonctionnement de notre IA, nous l'avons fait jouer contre l'IA de tic tac toe de google en difficultée maximale, et nous n'avons obtenu que des matchs nul, ce qui confirme notre IA marche bien.

## Multijoueur en local  
Dans ce mode de jeu, les deux joueurs sont derrière le même téléphone, le joueur 1 joue les ronds et le joueur 2 les croix. La partie se déroule de manière classique avec la possibilité de recommencer une partie. 

## Multijoueur en ligne  
Mode de jeu principal de l'application, il s'agit d'un multijoueur entre deux personnes sur deux téléphones différents, nous utilisons la base de données realTimeDB de firebase pour pouvoir jouer et faire transiter les coups d'un téléphone à l'autre. 
Il existe un mode spectateur, pour les personnes en trop se connectant à la partie. 
Le bouton rejouer permet de relancer la partie.
Le bouton retour renvoie au menu précédent.
Le bouton reset efface toute la base de données et relance une nouvelle partie.
En cas d'appui sur le bouton reset il faut que le joueur 1 appuie sur reset et le joueur 2 sur rejouer sinon la base sera effacée deux fois et le jeu ne marchera pas !
